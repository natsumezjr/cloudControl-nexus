# 2024-07-14 WebSocket指令交互与终端轮询设计AI对话记录

## 主要内容摘要

1. 回顾并梳理了项目文档体系、开发流程、分层架构、数据库迁移、测试环境等宏观规范。
2. 详细分析了终端指令下发的两种模式（HTTP轮询与WebSocket），并分别设计了接口、伪代码、数据结构和注意事项。
3. 明确了前端DeviceControlPanel.vue仅为云平台指令下发测试界面，不涉及终端轮询或WebSocket流程。
4. 结合流程图，梳理了WebSocket指令交互的完整业务流程，明确了service与terminal的职责分工。
5. 设计了WebSocket指令交互的四层分层结构（Entity/Repository/Service/Handler），并给出详细伪代码和注释。
6. 结合接口字段、前端实现、终端ID生成、session与Redis、实体扩展等问题，给出规范化设计建议。
7. 所有设计、伪代码、注意事项均已同步保存到docs/api/terminal_polling_command_test.md和docs/api/terminal_websocket_command_test.md。

## 关键决策与结论

- 指令实体、终端实体均采用基础字段+扩展点设计，便于后续维护和扩展。
- 指令下发字段与前端保持一致，所有消息均为JSON，结构标准化。
- session与终端ID映射采用Map+Redis自动管理，支持分布式。
- WebSocket配置类采用Spring官方推荐写法，注释详细，便于理解和二次开发。
- 终端ID生成与配置建议唯一且可配置，生产环境用UUID/SN/MAC等。
- 快速入门与对接说明已补充，便于新成员和设备厂商理解。

---

> 本记录为2024-07-14日AI与用户关于WebSocket指令交互与终端轮询设计的完整对话摘要，后续如有补充请在本文件追加。 