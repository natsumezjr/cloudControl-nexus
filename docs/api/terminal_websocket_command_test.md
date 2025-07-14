# WebSocket终端指令交互测试设计文档

## 1. 场景说明
终端与服务端通过WebSocket建立长连接，服务端主动推送指令，终端收到后确认并执行。

## 2. 流程图参考
详见设计流程图：service下发指令→session管理→WebSocket消息→终端处理→指令确认（POST）→执行。

## 3. 伪代码算法
```java
// ==========================
// Service（服务端）部分
// ==========================

// 1. 终端建立WebSocket连接
onWebSocketConnect(Session session, String terminalId) {
    // 保存session与终端ID的映射
    sessionMap.put(terminalId, session);
}

// 2. 下发指令
sendCommandToTerminal(String terminalId, Command command) {
    Session session = sessionMap.get(terminalId);
    if (session != null && session.isOpen()) {
        // 发送指令消息（JSON格式）
        session.sendText(command.toJson());
    } else {
        // 终端不在线，记录待发送或报错
    }
}

// 3. 处理终端指令确认
onCommandConfirm(String terminalId, Long commandId) {
    // 更新指令状态为“已确认”
    commandRepository.updateStatus(commandId, "CONFIRMED");
    // 记录日志或通知业务
}

// ==========================
// Terminal（终端）部分
// ==========================

// 1. 连接WebSocket
connectToServer(String wsUrl, String terminalId) {
    // 建立WebSocket连接，带上terminalId
    ws = new WebSocket(wsUrl + "?terminalId=" + terminalId);
    ws.onMessage = onMessageReceived;
}

// 2. 接收并处理指令
onMessageReceived(String message) {
    Command command = Command.fromJson(message);
    // 处理指令内容
    handleCommand(command);
    // 指令确认（HTTP或WebSocket均可，这里用HTTP POST）
    confirmCommand(command.id);
    // 执行指令
    executeCommand(command);
}

// 3. 指令确认
confirmCommand(Long commandId) {
    // POST确认接口
    http.post("/wp-json/wp/v2/comments", {
        "parent": commandId,
        "content": "confirmed"
    });
}

// 4. 执行指令
executeCommand(Command command) {
    // 根据command内容执行实际操作
    // ...
}
```

## 4. 设计规范与注意事项
- 所有消息均为JSON格式，字段命名与接口文档保持一致
- session管理采用Map<terminalId, Session>结构，便于扩展和维护
- 指令确认采用HTTP POST，后续可扩展为WebSocket消息
- 关键节点均有注释，便于团队协作和后续维护
- 文件路径、命名、注释等均符合项目规范

---

> 详细流程与接口字段请参考后端API文档和业务需求说明。 

## 5. 详细设计与实现要点（2024-07-14补充）

### 5.1 指令实体结构（Command Entity）
- 字段：
  - id：指令确认ID，自增主键
  - post：终端ID（可选，设备不处理）
  - karma：终端屏执行方式（0-get, 1-post, 2-put, 3-delete）
  - content：指令对象（ContentDTO，结构可扩展）
  - raw：指令对应的json字符串
  - author_url：指令操作类型（如api/brightness）
- ContentDTO建议用@Embeddable实现，便于后续扩展不同指令参数

### 5.2 终端实体结构（Terminal Entity）
- 字段：
  - id：自增主键
  - terminalId：终端唯一标识（可配置/自动生成）
  - name、ip、mac、os、version等基础字段
  - 预留扩展字段（如capabilities、programs、brightness、volume等）
- 建议：基础字段先实现，复杂对象用JSON或单独表扩展

### 5.3 指令下发字段与前端一致性
- 下发指令时，字段结构与前端保持一致（id, post, author_url, content(raw), karma）
- 指令内容通过content和raw灵活扩展，便于兼容多种指令类型

### 5.4 session与终端id的映射与Redis
- Spring Session已配置Redis自动存储session，无需手动管理
- WebSocket连接时，将terminalId与WebSocketSession映射（如Map<String, WebSocketSession>）
- Redis保证分布式环境下session一致性和高可用

### 5.5 WebSocket配置类讲解
- @EnableWebSocket：启用WebSocket支持
- WebSocketConfigurer：注册端点
- addHandler：注册自定义Handler（如TerminalWebSocketHandler）
- setAllowedOrigins：允许跨域连接
- 连接流程：终端连接ws端点→Handler建立映射→消息收发

### 5.6 终端ID生成与配置
- 终端ID建议唯一且可配置，测试可用自增ID，生产建议用UUID/SN/MAC等
- 终端配置URL时应填写服务器实际IP或域名，不能用localhost

### 5.7 Terminal实体的完整性与扩展
- 先实现基础字段，复杂对象用JSON或单独表扩展，便于维护
- 预留扩展点，后续可补充节目、亮度等业务字段

### 5.8 快速入门与对接说明
- 实现指令下发接口即可与设备交互
- 设备配置好服务器地址和终端ID即可接入
- 认证、分组、终端组等可后续扩展 