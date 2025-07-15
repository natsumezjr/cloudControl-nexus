# CloudControl-Nexus

一个基于云平台的智能设备控制系统，提供设备管理、内容分发、播放控制等功能。

## 🚀 技术栈

### 后端
- **Java 17** - 主要开发语言
- **Spring Boot 3.x** - 应用框架
- **Spring Security** - 安全认证
- **Spring Data JPA** - 数据访问
- **PostgreSQL** - 主数据库
- **Redis** - 缓存和会话存储
- **WebSocket** - 实时通信

### 前端
- **Vue 3** - 前端框架
- **TypeScript** - 类型安全
- **Pinia** - 状态管理
- **Vue Router** - 路由管理
- **Tailwind CSS** - 样式框架
- **Vite** - 构建工具

### 基础设施
- **Docker** - 容器化
- **Kubernetes** - 容器编排
- **Terraform** - 基础设施即代码
- **Nginx** - 反向代理

## 📁 项目结构

```
CloudControl-Nexus/
├── backend/                 # Spring Boot后端服务
├── frontend/               # Vue.js前端应用
├── infrastructure/         # 基础设施配置
├── docs/                  # 项目文档
└── check_environment.py   # 环境检查脚本
```

## 🛠️ 环境要求

- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Docker Desktop
- Git
- Redis

## 🚀 快速开始

### 1. 环境检查

```bash
python check_environment.py
```

### 2. 克隆项目

```bash
git clone https://github.com/natsumezjr/cloudControl-nexus.git
cd cloudControl-nexus
```

### 3. 后端启动

```bash
cd backend
mvn spring-boot:run
```

### 4. 前端启动

```bash
cd frontend
npm install
npm run dev
```

## 启动后端服务器（开发环境）

开发环境下，使用如下命令启动Spring Boot后端服务：

```bash
mvn -f backend/pom.xml spring-boot:run -Pdev
```

- `-f backend/pom.xml`：指定后端Maven项目路径
- `-Pdev`：激活开发环境配置（application.yml、pom.xml、Spring配置等均会使用dev profile）

如需切换到测试环境，使用 `-Ptest`，生产环境为 `-Pprod`。

## 📚 文档

- [项目结构说明](./docs/project_structure.md)
- [Django到Spring Boot迁移指南](./docs/django_to_springboot_migration.md)
- [Spring Boot分层架构详解](./docs/springboot_layered_architecture.md)

## 📚 重要文档索引

本项目所有详细文档均位于 `docs/` 目录，核心文档及作用如下：

- `docs/documentation_overview.md`：文档体系总览，分类导航入口。
- `docs/development/project_structure_guide.md`：项目结构说明，后端/前端/基础设施/文档分层与职责。
- `docs/architecture/architecture_decision_record.md`：架构决策记录，记录关键技术选型与变更原因。
- `docs/architecture/springboot_layered_architecture.md`：Spring Boot分层架构设计与分工。
- `docs/architecture/django_to_springboot_migration.md`：Django到Spring Boot迁移策略与经验。
- `docs/database/database_migration_guide.md`：数据库迁移与环境配置说明。
- `docs/database/cloud_platform_er_design.md`：云平台ER设计，数据库实体关系图。
- `docs/api/command_terminal_api.md`：终端指令下发与管理API说明。
- `docs/api/terminal_status_report_api.md`：终端状态上报API说明。
- `docs/api/terminal_websocket_command_test.md`：WebSocket指令下发与测试说明。
- `docs/api/terminal_polling_command_test.md`：轮询指令下发与测试说明。
- `docs/development/development_continuity_guide.md`：开发连贯性与规范。
- `docs/development/development_checklist.md`：开发自查清单。
- `docs/records/ai_chat_history.md`：AI对话与决策历史。
- `docs/environment_setup.md`：开发环境配置与依赖说明。
- `docs/api/frontend_api_reference.md`：前端接口说明，涵盖主要API、参数、用途及与后端接口的关系。

> 更多详细文档请参见 `docs/`