# WebServer 设计文档（分层解耦方案）

## 一、设计目标
- 支持终端通过WebSocket与服务端实时通信，实现认证、心跳、指令下发、状态管理。
- 采用分层解耦架构，便于扩展、维护和团队协作。
- 关键实时状态（如心跳、在线）后续可落地Redis，当前以内存Map实现，相关注释已预留。

## 二、架构分层

1. **WebSocketConfig**
   - 负责注册WebSocket端点和跨域配置。
2. **TerminalWebSocketHandler**
   - 只负责协议适配、消息分发。
   - 连接建立、关闭、消息接收等事件处理。
3. **TerminalSessionManager**
   - 负责管理终端session、心跳、在线状态。
   - 维护内存Map，后续可切换为Redis。
4. **TerminalAuthService**
   - 负责终端账号认证。
   - 校验username、password。
5. **CommandService/CommandFactory**
   - 负责指令组装与下发。
   - 采用DTO+枚举+工厂模式，便于扩展。
6. **定时任务（@Scheduled）**
   - 定期检测心跳超时，超时则断开连接并更新状态。

## 三、主要流程

1. 终端发起WebSocket连接，带username/password参数。
2. Handler调用AuthService校验，认证通过后SessionManager保存session，发送heartbeat。
3. 终端定时发送心跳，Handler收到后SessionManager更新lastHeartbeat。
4. 定时任务检测心跳超时，超时则SessionManager断开连接并更新状态。
5. Controller接收指令下发请求，组装DTO，CommandFactory生成JSON，通过SessionManager推送到终端。
6. 终端收到指令后通过HTTP接口确认，服务端处理确认逻辑。

## 四、可扩展点
- Session/心跳/状态管理后续可切换为Redis实现，支持分布式和高可用。
- 可集成Spring Security增强认证安全。
- 指令回传、日志、幂等、断线重连等可逐步完善。

---

> Redis相关实现与注释已预留，待后续落地时补充。 