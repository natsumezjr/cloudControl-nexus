# 前端接口说明（API Reference）

## 1. 设备指令下发接口

- 方法：`sendDeviceCommand(command: SingleCommand | BatchCommand): Promise<DeviceResponse>`
- 位置：`frontend/src/api/deviceApi.ts`
- 说明：用于向后端 `/wp-json/wp/v2/comments` 接口发送单条或批量终端指令，支持所有业务指令类型。
- 参数：
  - `command`：单条或批量指令对象，结构如下：
    - `SingleCommand`：
      - `post`：终端ID（number）
      - `metadata`：指令元数据（如`act_url`、`act_method`）
      - `content`：指令内容（string，JSON字符串）
    - `BatchCommand`：
      - `post`：终端ID（number）
      - `CommentData`：`SingleCommand[]` 批量指令数组
- 返回值：
  - `DeviceResponse`：统一响应结构，含`success`、`code`、`message`、`data`、`timestamp`等字段
- 示例：
```js
// 单条指令
sendDeviceCommand({
  post: 1,
  metadata: { act_url: 'api/action', act_method: 1 },
  content: '{"command":"wakeup"}'
})

// 批量指令
sendDeviceCommand({
  post: 1,
  CommentData: [
    { post: 1, metadata: { act_url: 'api/action', act_method: 1 }, content: '{"command":"wakeup"}' },
    { post: 1, metadata: { act_url: 'api/action', act_method: 1 }, content: '{"command":"sleep"}' }
  ]
})
```

## 2. 设备信息与控制接口

- `getDeviceList()`：获取设备列表
- `getDeviceDetail(deviceId)`：获取设备详情
- `connectDevice(deviceId)`：连接设备
- `disconnectDevice(deviceId)`：断开设备连接
- `getDeviceStatus(deviceId)`：获取设备状态

> 所有接口均基于axios，统一异常处理，具体参数和返回结构详见`frontend/src/api/deviceApi.ts`。

## 3. 与后端接口的关系

- 所有指令下发、设备控制相关API均与后端 `/wp-json/wp/v2/comments` 及相关REST接口对接，参数结构与后端DTO保持一致，便于联调和扩展。 