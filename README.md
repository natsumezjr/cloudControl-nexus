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

## 📚 文档

- [项目结构说明](./docs/project_structure.md)
- [Django到Spring Boot迁移指南](./docs/django_to_springboot_migration.md)
- [Spring Boot分层架构详解](./docs/springboot_layered_architecture.md)

## 🤝 贡献

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- 项目链接: [https://github.com/natsumezjr/cloudControl-nexus](https://github.com/natsumezjr/cloudControl-nexus)
