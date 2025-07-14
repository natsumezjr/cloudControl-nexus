# 终端状态上报接口与代码设计说明

## 1. 接口定义

- **接口路径**：`PUT /wp-json/screen/v1/status`
- **接口描述**：终端设备通过该接口上报自身状态信息到服务器
- **请求体**：JSON，字段结构详见下表（所有字段均为对象或数组，支持嵌套）

| 字段名                  | 类型    | 说明                   |
|------------------------|--------|------------------------|
| terminal               | Object | 设备名称和描述信息      |
| powerstatus            | Object | 设备当前状态           |
| info                   | Object | 设备通用信息           |
| vsns                   | Object | 设备节目列表           |
| dimension              | Object | 输出分辨率参数         |
| volume                 | Object | 音量等级               |
| inputmode              | Object | 输入模式               |
| brightnessandcolortemp | Object | 亮度和色温             |
| reporttime             | Object | 上报时间配置           |
| reportswitch           | Object | 上报开关               |
| newrtc                 | Object | 新版时间时区           |
| inboundfirewall        | Object | 防火墙入站规则         |
| websocketstatus        | Object | WebSocket连接状态      |
| allbrightnessinfo      | Object | 亮度相关信息           |
| brightcurve            | Object | 亮度曲线               |
| brightnessversion      | Object | 亮度版本               |
| cameraconfig           | Object | 摄像头配置             |
| contentreport          | Object | 播放统计开关           |
| contentreportinterval  | Object | 播放统计上报间隔       |
| ifstatus               | Object | 网络接口配置           |
| locale                 | Object | 设备语言/国家          |
| rtc                    | Object | 旧版时间时区           |
| sync_program_mode      | Object | 同步节目模式           |
| x16_brightness         | Object | x16亮度（弃用）        |
| x16_preset             | Object | x16预设信息（弃用）    |
| screen_orientation     | Object | 屏幕方向               |
| programautoscale       | Object | 自动缩放               |
| _4ginfo                | Object | 4G信息                 |
| cmdinterval            | Object | 指令间隔               |
| gps                    | Object | GPS信息                |
| terminalId             | String | 终端唯一标识           |
| remark                 | String | 备注                   |

## 2. 代码设计思路

### （1）实体类设计
- 所有上报字段均以jsonb类型存储，Java侧用String类型接收（存储原始JSON字符串）。
- 这样做可以极大提升字段扩展和兼容性，适合物联网终端场景。

### （2）DTO设计
- DTO层字段全部为Object类型，直接映射设备端上报的原始结构，便于Spring自动反序列化。

### （3）Repository设计
- 继承JpaRepository，具备标准的增删查改能力。
- 提供按终端ID查询最新上报的便捷方法，便于后续业务扩展。

### （4）数据库设计
- 采用PostgreSQL的jsonb类型，支持高效存储、索引和查询复杂JSON结构。

## 3. 典型用法举例

- 设备上报数据时，后端无需关心字段细节，直接存储原始JSON。
- 查询终端最新状态时，可通过Repository接口快速获取。
- 后续如需扩展新字段，仅需前端/设备端和DTO/实体类同步增加即可，数据库无需变更。 

## 4. Service 层接口设计

```java
public interface TerminalStatusReportService {
    // 保存终端上报状态
    TerminalStatusReport saveStatusReport(TerminalStatusReportRequest request);

    // 查询指定终端最新一次上报
    TerminalStatusReport getLatestStatusByTerminalId(String terminalId);

    // 查询某终端的全部上报历史
    List<TerminalStatusReport> getAllStatusByTerminalId(String terminalId);

    // 查询所有终端的最新上报
    List<TerminalStatusReport> getAllLatestStatus();
}
```

- **saveStatusReport**：用于保存设备上报的状态数据。
- **getLatestStatusByTerminalId**：获取某终端最新一次上报。
- **getAllStatusByTerminalId**：获取某终端所有历史上报。
- **getAllLatestStatus**：获取所有终端的最新上报（可扩展分页、筛选等）。 