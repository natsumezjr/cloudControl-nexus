# 指令——终端接口文档（初版）

## 0. WebSocket指令下发接口说明（推荐实现）

### 0.1 终端账号生成与登录流程

1. 指令平台生成终端账号（name）和密码（password），并记录账号与设备的绑定关系。
2. 终端通过配置网站登录，配置网站将账号和密码发送到指令平台进行认证。
3. 登录成功后，终端通过4G等方式自动发起WebSocket连接：
   `ws(s)://{host}:8443/ColorWebSocket/websocket/chat?username={name}&password={password}`
   > 注：host 通常为指令平台/配置网站服务地址，建议可配置。部分终端可能硬编码，实际部署时建议提供可编辑项。

4.登录接口说明（/api/login，account.json）
- Path：`/api/login`
- Method：POST
- accountName：终端账号
- password：终端密码

返回字段（account.json）
| 字段         | 类型    | 说明                   |
|--------------|---------|------------------------|
| name         | string  | 终端账号名称（后端字段为accountName，前端字段为name，DTO编写时需注释说明） |
| password     | string  | 终端账号密码           |
| url          | string  | 云平台地址             |
| devicestatus | string  | 终端状态（如 sleep）   |
| internet     | boolean | 是否联网               |
| login        | boolean | 登录是否成功           |

返回示例
```json
{
  "name": "LsI8pSKplQkOpb",
  "password": "nihaoi",
  "url": "https://www.colorlightcloud.com",
  "devicestatus": "sleep",
  "internet": true,
  "login": true
}
```
### 0.2 WebSocket连接与认证

- 服务端收到WebSocket连接请求后，通过GET参数解析username和password，查找并校验对应终端信息。
- 校验通过后，将账号与WebSocket session/socket进行绑定，维护通信。
- 校验失败时，返回错误信息（如invalid params/invalid account/server error）并关闭连接。

### 0.3 连接应答与心跳机制

- 连接成功后，服务端立即向终端发送 `heartbeat` 消息，表示认证通过、连接建立。
- 终端收到 `heartbeat` 后，认为连接建立成功，可以开始发送/接收消息。
- 终端每55秒发送一次心跳，服务端收到后更新终端最后在线时间。
- 服务端维护终端在线状态：
  - 建立连接即设为在线（不需等待心跳）。
  - 记录每个终端最后一次心跳（或消息）时间。
  - 若当前时间与最后心跳时间间隔大于1分钟，则判定终端离线，断开连接并更新状态。
- 终端开关机状态服务器无法直接感知，只能通过心跳/消息时间判断是否在线。
- 若终端执行关机指令且服务端参与指令确认，可将其标记为关机。

-心跳检测监测机制通过两个函数实现
 -一个在接收到终端的heartbeat信息后更新上次heartbeat时间戳。
 -一个用@Schedule定时检测heartbeat的时间戳，超时则断开连接，将status设置为下线

-配置在application.yml文件中
 -心跳间隔也为检测时间

### 0.4 WebSocket指令下发结构

- 指令下发消息结构：
```json
{
    "data":[
        {
            "id":34035,
            "post":310390,
            "author_url":"api/brightness",
            "content":{
                "raw":" {\"brightness\": 255}"
            },
            "karma":2
        }
    ],
    "led_id":310390
}
```
- 字段说明：
  - `data`：指令集合（数组，每条结构同HTTP指令）
  - `id`：指令确认ID
  - `post`：终端ID
  - `karma`：执行方式 0-get,1-post,2-put,3-delete
  - `content`：指令内容对象
  - `raw`：指令内容JSON
  - `author_url`：指令类型
  - `led_id`：设备业务ID
- 设备收到指令后，需通过HTTP接口 `/wp-json/wp/v2/comments`（POST）进行指令确认，确认成功后才执行指令。

### 0.5 终端在线状态字段（建议）

- 终端实体需新增：
  - `name`（账号）、`password`（密码）
  - `online`（布尔，是否在线）
  - `lastHeartbeat`（时间戳，最后心跳/消息时间）

---

# 以下为HTTP轮询接口与实体设计

## 1. 指令接口（/wp-json/wp/v2/comments）

### 1.1 设备取指令接口（GET）

- **Path**：`/wp-json/wp/v2/comments`
- **Method**：GET
- **请求参数**：
  - `clt_type`（string，必须）：固定为 terminal
  - `device_num`（int，必须）：设备序列号

- **返回数据结构**（单条指令对象）：

| 字段         | 类型      | 必须 | 说明                         |
|--------------|-----------|------|------------------------------|
| id           | integer   | 是   | 指令确认ID                   |
| post         | integer   | 否   | 终端ID（可选）               |
| karma        | integer   | 是   | 执行方式 0-get,1-post,2-put,3-delete |
| ContentDTO   | object    | 是   | 指令对象                     |
| raw          | string    | 是   | 指令内容JSON                 |
| author_url   | string    | 是   | 指令类型，如api/brightness   |

- **典型指令类型**（均已支持）：
  - 唤醒、休眠、重启、截屏、清理缓存
  - 亮度、色温、信号源切换、音量
  - 继电器、板载继电器
  - 升级、语言和地区、时区设置
  - GPS/监控/内容/日志/轮播节目名上报开关
  - 节目管理（更新、切换、清空、删除）
  - 运行日志上报、网络接口配置

- **示例**（亮度指令）：
```json
{
  "id": 1,
  "post": 1,
  "author_url": "api/brightness",
  "content": {
    "raw": "{\"brightness\":88}"
  },
  "karma": 2
}
```

---

### 1.2 指令应答接口（POST）

- **Path**：`/wp-json/wp/v2/comments`
- **Method**：POST
- **请求参数**：
  - Header: Content-Type: application/json
  - Params: post（终端id，必须）
  - Body:
    - parent（指令id，必须）
    - content（设备校验指令结果，必须）

- **响应**：状态码200-300为允许执行，其他为不执行。

---

## 2. 指令设计

```java
@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 指令ID

    private Long terminalId;        // 目标终端ID（外键）

    private Integer karma;          // 执行方式 0-get,1-post,2-put,3-delete

    private String authorUrl;       // 指令类型（如api/brightness）

    @Column(columnDefinition = "TEXT")
    private String content;         // 指令内容（JSON）

    @Column(columnDefinition = "TEXT")
    private String raw;             // 指令原始内容（JSON）

    private Timestamp createdAt;    // 创建时间

    //private String status;          // 指令状态（PENDING, EXECUTING, SUCCESS, FAILED）
                                   // 目前没有终端回传执行结果，status仅供后端流程使用，后期需完善回传机制

    // 指令重试、幂等性、执行日志等暂未实现，后续可扩展
}
```

---

### 2.1 指令下发与管理接口设计

##### 2.1.1 指令下发接口（WebSocket/HTTP）

- 指令下发无需持久化数据库，采用DTO封装，service层组装JSON，通过WebSocket或HTTP直接推送至终端。
- 推荐接口示例（支持单条与批量）：

```java
// 单条指令下发
POST /api/terminal/command/send
RequestBody: CommandSendRequestDTO
Response: CommandSendResponseDTO

// 批量指令下发
POST /api/terminal/command/batchSend
RequestBody: List<CommandSendRequestDTO>
Response: BatchCommandSendResponseDTO
```

- CommandSendRequestDTO 示例字段：
  - terminalId: Long
  - commandType: CommandTypeEnum（如 BRIGHTNESS, VOLUME, SOURCE 等，建议用枚举集中管理）
  - content: Map<String, Object>（指令参数，通用DTO模式，便于扩展）
  - meta: Map<String, Object>（可选，指令元数据）

- service 层采用工厂模式，根据 commandType 组装具体指令 JSON，便于后续扩展新指令类型。

##### 2.1.2 DTO、枚举与工厂模式示例

```java
// CommandTypeEnum.java
public enum CommandTypeEnum {
    BRIGHTNESS, VOLUME, SOURCE, CUSTOM
}

// CommandSendRequestDTO.java
public class CommandSendRequestDTO {
    private Long terminalId;
    private CommandTypeEnum commandType;
    private Map<String, Object> content;
    private Map<String, Object> meta;
    // getter/setter
}

// CommandFactory.java
public class CommandFactory {
    public static String buildCommandJson(CommandSendRequestDTO dto) {
        switch (dto.getCommandType()) {
            case BRIGHTNESS:
                // 组装亮度指令JSON
                return "{\"type\":\"brightness\",\"value\":" + dto.getContent().get("value") + "}";
            case VOLUME:
                // 组装音量指令JSON
                return "{\"type\":\"volume\",\"value\":" + dto.getContent().get("value") + "}";
            case SOURCE:
                // 组装信号源指令JSON
                return "{\"type\":\"source\",\"value\":\"" + dto.getContent().get("value") + "\"}";
            case CUSTOM:
            default:
                // 通用自定义指令
                return new ObjectMapper().writeValueAsString(dto.getContent());
        }
    }
}
```

##### 2.1.3 指令流程与存储说明

- Command 实体仅用于后端流程追踪，status 字段目前无终端回传，后续可扩展回传机制。
- 实体设计支持后续扩展指令重试、幂等性、执行日志等企业级需求。
- 实时状态（如亮度、音量等）建议存储于 Redis，重要变更归档至 PostgreSQL，兼顾性能与数据安全。

##### 2.1.4 可维护性与可扩展性建议

- 指令类型、参数、元数据均采用枚举+工厂+通用DTO模式集中管理，便于团队协作和自动文档生成。
- 所有DTO、枚举、工厂、接口建议分包管理，注释清晰，接口与实现分层解耦。
- 支持 JSON 格式传输，兼容性强，便于调试与日志追踪。
- 代码、文档、测试、配置、部署等全流程规范，支持长期演进和企业级维护。

##### 2.1.5 典型流程示意

1. 前端/管理端通过接口下发指令（单条/批量）。
2. service 层根据指令类型组装 JSON，通过 WebSocket/HTTP 推送至终端。
3. 指令无需存库，仅关键流程或异常归档 Command 实体。
4. 后续可扩展终端回传执行结果、重试、幂等、日志等机制。

---

> 以上为指令下发与管理接口的企业级实现建议与流程说明，具体代码实现可参考 service 层工厂模式与 DTO 设计，便于后续扩展和维护。

## 3. 终端实体设计（Terminal Entity，指令与流程相关字段）

```java
@Entity
@Table(name = "terminals")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 主键（psql）

    @Column(nullable = false, unique = true)
    private String serialNo;        // 设备序列号/唯一标识（如device_num/terminalId，psql）

    @Column(nullable = false, unique = true)
    private String name;            // 设备名称，用户显示用（psql）

    @Column(nullable = false)
    private String password;        // 终端账号密码（后端自动生成，通过线下等渠道传递给用户，用于认证，psql）

    // 设备基础信息
    private String accountName;      // 终端账号名称（后端自动生成，通过线下等渠道传递给用户，用于认证，psql）
    private String ledDescription;  // 设备描述（terminal.leddescription，psql）

    // 终端在线与心跳（流程相关，redis）
    private Timestamp lastHeartbeat;// 最后心跳/消息时间（redis，实时状态）

    // 终端状态（psql+redis，状态变更时同步）
    private String status;          // 终端状态（如online/sleep/rebooting，redis，重要状态可归档psql）
    private Integer powerStatus;    // 休眠/唤醒（redis，重要状态可归档psql）

    // 指令相关字段（redis，实时属性，重要变更可归档psql）
    private Integer brightness;     // 亮度（redis）
    private Integer colortemp;      // 色温（redis）
    private Integer volume;         // 音量（redis）
    private String inputMode;       // 信号源（redis）
    private String language;        // 语言（redis）
    private String country;         // 地区（redis）
    private String timezoneId;      // 时区ID（redis）
    private Float timezone;         // 时区偏移（redis）
    private Integer isAutoTime;     // 是否自动同步时间（redis）
    private String program;         // 当前节目名（redis）
    private String programType;     // 节目来源类型（redis）
    private Integer gpsReportInterval;      // GPS上报间隔（redis）
    private Integer sensorReportInterval;   // 监控上报间隔（redis）
    private Integer contentReportStatus;    // 内容上报开关（redis）
    private String logReport;               // 日志上报开关（redis）
    private String rotateProgramVsnsReport; // 轮播节目名上报开关（redis）

    // 暂不实现，仅注释（后期扩展，建议用redis或json/text字段）
    // private String relayStatus;     // 继电器状态
    // private String updateUrl;       // 升级包地址
    // private String currentLog;      // 运行日志
    // private String ifStatus;        // 网络接口信息
    // private String vsns;            // 节目列表/管理
    // private String contentReportInterval; // 播放统计上报间隔
    // private String cameraConfig;    // 摄像头参数

    @Column(columnDefinition = "TEXT")
    private String extra;           // 预留扩展字段（psql，兼容未来扩展）
}
```

---

## 4. 设计说明

- **所有接口指令均可通过终端字段控制实现**，如有新指令类型，可通过扩展字段或结构体支持。
- **复杂结构**（如节目列表、网络、摄像头等）暂不实现，仅保留外键或JSON引用，后续如需支持可扩展。
- **接口与实体字段一一对应**，便于前后端联调和自动化测试。

---



### 5.5 说明
- 该接口用于指令发送网页与配置终端网页对接，认证成功后返回终端账号、密码等信息，供后续WebSocket连接和指令下发使用。
- **注意：后端字段为accountName，前端字段为name，DTO编写时需加注释，避免混淆。**

如需进一步细化或补充，请随时告知！ 

## 当前测试实现的局限与后续建议

1. **账号分发与注册**：仅用于测试，终端账号（accountName）和密码（password）由服务器人工创建，手动输入到终端。
2. **终端注册与绑定**：服务器先创建终端对象，终端通过线下方式获取账号密码，无自动注册流程。
3. **指令执行结果回传**：目前终端未回传指令执行结果，指令实体的status字段仅供后端流程使用，后期建议完善回传与日志。
4. **指令重试与幂等性**：暂未实现，后续可根据max-command-retry配置扩展。
5. **Redis与psql同步策略**：目前未做自动同步，实时状态仅存redis，重要变更可手动归档psql，后续可完善。
6. **断线重连与异常处理**：暂未实现自动重连与异常处理机制，后续可扩展。
7. **设备基础信息变更**：设备名称、描述等变更流程未实现，后续可扩展。
8. **日志与监控**：系统操作日志、指令日志、异常日志等未实现，后续建议补充。
9. **安全性与权限控制**：接口权限、数据加密、认证失效等安全细节未实现，后续建议完善。
10. **扩展字段/功能**：如relayStatus、updateUrl、currentLog、ifStatus、vsns等暂未实现，后续可根据需求扩展。

如需详细设计或补充，请指定具体项！ 

---

## 附录：指令下发流程简介（以唤醒指令为例）

1. **前端操作**  
   用户在前端页面点击“唤醒”按钮，前端组装如下指令请求体：

   ```json
   {
     "post": 1,
     "metadata": {
       "act_url": "api/action",
       "act_method": 1
     },
     "content": "{\"command\":\"wakeup\"}"
   }
   ```

2. **接口调用**  
   前端通过 HTTP POST 请求，将上述 JSON 数据发送到后端接口：  
   `POST /wp-json/wp/v2/comments`

3. **后端处理流程**
   - **Controller 层**：接收指令下发请求，路由到对应的控制接口（如 `/api/terminal/command/send` 或 `/wp-json/wp/v2/comments`）。
   - **Service 层**：根据请求参数，生成标准化的指令对象（DTO），并通过工厂模式组装指令内容。
   - **指令下发**：Service 层将组装好的指令通过 WebSocket 或 HTTP 推送到目标终端。
   - **终端处理**：终端收到指令后，解析内容并执行唤醒操作。

4. **流程总结**
   - 用户点击按钮 → 前端组装指令 → 调用后端接口 → Controller 路由 → Service 组装DTO/指令 → 下发到终端 → 终端执行

---

### 终端（Terminal）实体说明

当前终端（Terminal）实体只需通过 Repository 层进行数据库的增删查改（CRUD）操作，满足基本的数据存取需求。暂时**不需要**为终端单独创建 Service 和 Controller 层，后续如有专门业务逻辑或接口需求可再扩展。 

---

### 终端（Terminal）DTO与Repository设计思路

> **注释：终端SQL迁移脚本（如V3__create_terminals.sql）必须严格按照终端实体字段编写，字段名、类型、约束与实体类保持一致，确保ORM与数据库结构同步。**

#### 1. DTO设计建议
- DTO（如TerminalDTO）字段应与终端实体一一对应，包括：
  - serialNo（物理唯一标识）
  - accountName（账号）
  - password（密码）
  - name（设备名称）
  - ledDescription（描述）
  - status（状态，建议用枚举）
  - lastHeartbeat（心跳时间）
- 字段注释清晰，便于前后端、文档自动生成。
- DTO可根据业务扩展，建议采用分层DTO（如TerminalCreateDTO、TerminalUpdateDTO等）。

#### 2. Repository设计建议
- 仅需基础的CRUD（增删查改）方法，如findById、findAll、save、delete等。
- 可根据业务扩展自定义查询（如findBySerialNo、findByStatus等）。
- 推荐使用Spring Data JPA等ORM框架，接口式开发，便于维护和扩展。

#### 3. SQL迁移脚本规范
- 终端表SQL（如V3__create_terminals.sql）字段、类型、主键、唯一约束等必须与实体类完全一致。
- 字段变更需同步更新实体、DTO、SQL脚本，保持一致性。

> 以上为终端DTO、Repository及SQL设计的企业级规范建议，便于团队协作和长期维护。 