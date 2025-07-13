# CloudControl-Nexus 项目结构说明

## 整体架构

```
CloudControl-Nexus/
├── backend/                    # 后端服务目录
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cloudcontrol/
│   │   │   │       ├── controller/     # 控制器层 - 处理HTTP请求响应
│   │   │   │       ├── service/        # 服务层 - 业务逻辑处理
│   │   │   │       ├── repository/     # 数据访问层 - 数据库操作
│   │   │   │       ├── entity/         # 实体类 - 数据库表映射
│   │   │   │       ├── dto/            # 数据传输对象 - 请求响应封装
│   │   │   │       ├── config/         # 配置类 - 应用配置
│   │   │   │       ├── util/           # 工具类 - 通用工具方法
│   │   │   │       └── exception/      # 异常处理 - 自定义异常
│   │   │   └── resources/
│   │   │       ├── application.yml     # 应用配置文件
│   │   │       └── application-dev.yml # 开发环境配置
│   │   └── test/                       # 测试目录
│   │       └── java/
│   │           └── com/cloudcontrol/
│   │               ├── controller/      # 控制器测试
│   │               ├── service/         # 服务层测试
│   │               └── repository/      # 数据访问层测试
│   ├── pom.xml                         # Maven依赖配置
│   └── README.md                       # 后端说明文档
├── frontend/                   # 前端应用目录
│   ├── src/
│   │   ├── components/         # 组件目录
│   │   ├── pages/             # 页面目录
│   │   ├── services/          # 服务层 - API调用
│   │   ├── utils/             # 工具类
│   │   └── assets/            # 静态资源
│   ├── package.json           # 前端依赖配置
│   └── README.md              # 前端说明文档
├── infrastructure/            # 基础设施目录
│   ├── docker/               # Docker配置
│   ├── kubernetes/           # K8s配置
│   └── scripts/              # 部署脚本
├── docs/                     # 项目文档目录
│   ├── api/                  # API文档
│   ├── design/               # 设计文档
│   └── deployment/           # 部署文档
└── README.md                 # 项目总体说明
```

## 后端详细结构

### 控制器层 (Controller)
```
controller/
├── AuthController.java        # 用户认证控制器 - 登录、登出、Token管理
├── UserController.java        # 用户管理控制器 - 用户CRUD操作
├── TerminalController.java    # 终端管理控制器 - 终端列表、状态管理
├── CommandController.java     # 指令管理控制器 - 指令下发、状态查询
└── SystemController.java      # 系统管理控制器 - 系统配置、监控
```

### 服务层 (Service)
```
service/
├── AuthService.java          # 认证服务接口
├── AuthServiceImpl.java      # 认证服务实现 - 登录验证、Token生成
├── UserService.java          # 用户服务接口
├── UserServiceImpl.java      # 用户服务实现 - 用户管理业务逻辑
├── TerminalService.java      # 终端服务接口
├── TerminalServiceImpl.java  # 终端服务实现 - 终端状态管理
├── CommandService.java       # 指令服务接口
├── CommandServiceImpl.java   # 指令服务实现 - 指令下发逻辑
└── SystemService.java        # 系统服务 - 系统监控、配置管理
```

### 数据访问层 (Repository)
```
repository/
├── UserRepository.java       # 用户数据访问 - 用户表操作
├── TerminalRepository.java   # 终端数据访问 - 终端表操作
├── TerminalGroupRepository.java # 终端组数据访问 - 终端组表操作
├── CommandRepository.java    # 指令数据访问 - 指令表操作
└── SystemRepository.java     # 系统数据访问 - 系统配置表操作
```

### 实体类 (Entity)
```
entity/
├── User.java                 # 用户实体 - 用户基本信息
├── Terminal.java             # 终端实体 - 终端设备信息
├── TerminalGroup.java        # 终端组实体 - 终端分组管理
├── Command.java              # 指令实体 - 指令信息
└── SystemConfig.java         # 系统配置实体 - 系统参数
```

### 数据传输对象 (DTO)
```
dto/
├── auth/                     # 认证相关DTO
│   ├── LoginRequest.java     # 登录请求DTO
│   ├── LoginResponse.java    # 登录响应DTO
│   └── TokenRequest.java     # Token请求DTO
├── user/                     # 用户相关DTO
│   ├── UserCreateRequest.java # 用户创建请求
│   ├── UserUpdateRequest.java # 用户更新请求
│   └── UserResponse.java     # 用户响应DTO
├── terminal/                 # 终端相关DTO
│   ├── TerminalListRequest.java # 终端列表请求
│   ├── TerminalListResponse.java # 终端列表响应
│   ├── TerminalStatusRequest.java # 终端状态请求
│   └── TerminalStatusResponse.java # 终端状态响应
└── command/                  # 指令相关DTO
    ├── CommandRequest.java   # 指令请求DTO
    ├── CommandResponse.java  # 指令响应DTO
    └── CommandStatusRequest.java # 指令状态请求
```

### 配置类 (Config)
```
config/
├── RedisConfig.java          # Redis配置 - 缓存和会话管理
├── SecurityConfig.java       # 安全配置 - 认证授权配置
├── SwaggerConfig.java        # API文档配置 - Swagger文档
├── CorsConfig.java           # 跨域配置 - 前后端跨域处理
└── DatabaseConfig.java       # 数据库配置 - 数据源配置
```

### 工具类 (Util)
```
util/
├── JwtUtil.java              # JWT工具类 - Token生成验证
├── PasswordUtil.java         # 密码工具类 - 密码加密验证
├── RedisUtil.java            # Redis工具类 - 缓存操作封装
├── ResponseUtil.java         # 响应工具类 - 统一响应格式
└── ValidationUtil.java       # 验证工具类 - 数据验证
```

### 异常处理 (Exception)
```
exception/
├── GlobalExceptionHandler.java # 全局异常处理器
├── BusinessException.java     # 业务异常类
├── AuthException.java         # 认证异常类
└── ValidationException.java   # 验证异常类
```

## 前端详细结构

### 组件层 (Components)
```
components/
├── common/                   # 通用组件
│   ├── Header.jsx           # 页面头部组件
│   ├── Sidebar.jsx          # 侧边栏组件
│   └── Footer.jsx           # 页面底部组件
├── auth/                    # 认证组件
│   ├── LoginForm.jsx        # 登录表单组件
│   └── UserProfile.jsx      # 用户信息组件
├── terminal/                # 终端组件
│   ├── TerminalList.jsx     # 终端列表组件
│   ├── TerminalDetail.jsx   # 终端详情组件
│   └── TerminalStatus.jsx   # 终端状态组件
└── command/                 # 指令组件
    ├── CommandForm.jsx      # 指令表单组件
    └── CommandHistory.jsx   # 指令历史组件
```

### 页面层 (Pages)
```
pages/
├── Login.jsx                # 登录页面
├── Dashboard.jsx            # 仪表板页面
├── TerminalManagement.jsx   # 终端管理页面
├── CommandCenter.jsx        # 指令中心页面
└── SystemSettings.jsx       # 系统设置页面
```

### 服务层 (Services)
```
services/
├── api.js                   # API基础配置
├── authService.js           # 认证服务
├── userService.js           # 用户服务
├── terminalService.js       # 终端服务
└── commandService.js        # 指令服务
```

## 基础设施结构

### Docker配置
```
docker/
├── Dockerfile.backend       # 后端Docker镜像配置
├── Dockerfile.frontend      # 前端Docker镜像配置
├── docker-compose.yml       # 本地开发环境配置
└── docker-compose.prod.yml  # 生产环境配置
```

### Kubernetes配置
```
kubernetes/
├── namespace.yaml           # 命名空间配置
├── configmap.yaml          # 配置映射
├── secret.yaml             # 密钥配置
├── deployment.yaml         # 部署配置
└── service.yaml            # 服务配置
```

## 文档结构

### API文档
```
docs/api/
├── auth-api.md              # 认证接口文档
├── user-api.md              # 用户接口文档
├── terminal-api.md          # 终端接口文档
└── command-api.md           # 指令接口文档
```

### 设计文档
```
docs/design/
├── database-design.md       # 数据库设计文档
├── api-design.md            # API设计文档
├── security-design.md       # 安全设计文档
└── deployment-design.md     # 部署设计文档
```

## 分层架构说明

### 控制器层 (Controller)
- 职责：处理HTTP请求和响应
- 功能：参数验证、调用服务层、返回响应
- 特点：无业务逻辑，只负责请求路由

### 服务层 (Service)
- 职责：业务逻辑处理
- 功能：数据验证、业务规则、事务管理
- 特点：核心业务逻辑，可复用

### 数据访问层 (Repository)
- 职责：数据库操作
- 功能：CRUD操作、数据查询
- 特点：与具体数据库技术绑定

### 实体层 (Entity)
- 职责：数据模型定义
- 功能：数据库表映射、字段验证
- 特点：与数据库表结构对应

### DTO层 (DTO)
- 职责：数据传输封装
- 功能：请求响应数据结构
- 特点：与API接口对应，独立于数据库结构 