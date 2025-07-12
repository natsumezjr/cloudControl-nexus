# CloudControl-Nexus 项目结构说明

## 整体架构

```
CloudControl-Nexus/
├── backend/                 # Spring Boot后端服务
├── frontend/               # Vue.js前端应用
├── infrastructure/         # 基础设施配置
├── docs/                  # 项目文档
└── check_environment.py   # 环境检查脚本
```

## 后端结构 (Spring Boot)

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/cloudcontrol/
│   │   │   ├── CloudControlApplication.java    # 主启动类
│   │   │   ├── config/                        # 配置类
│   │   │   │   ├── SecurityConfig.java        # 安全配置
│   │   │   │   ├── DatabaseConfig.java        # 数据库配置
│   │   │   │   └── WebSocketConfig.java       # WebSocket配置
│   │   │   ├── controller/                    # 控制器层
│   │   │   │   ├── UserController.java        # 用户管理
│   │   │   │   ├── DeviceController.java      # 设备管理
│   │   │   │   ├── ContentController.java     # 内容管理
│   │   │   │   └── PlayController.java        # 播放控制
│   │   │   ├── service/                       # 业务逻辑层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── DeviceService.java
│   │   │   │   ├── ContentService.java
│   │   │   │   └── PlayService.java
│   │   │   ├── repository/                    # 数据访问层
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── DeviceRepository.java
│   │   │   │   ├── ContentRepository.java
│   │   │   │   └── PlayRepository.java
│   │   │   ├── entity/                        # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── Device.java
│   │   │   │   ├── Content.java
│   │   │   │   └── Play.java
│   │   │   ├── dto/                          # 数据传输对象
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── DeviceDTO.java
│   │   │   │   └── ContentDTO.java
│   │   │   ├── exception/                    # 异常处理
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── CustomException.java
│   │   │   └── util/                         # 工具类
│   │   │       ├── JwtUtil.java
│   │   │       ├── FileUtil.java
│   │   │       └── ValidationUtil.java
│   │   └── resources/
│   │       ├── application.yml               # 主配置文件
│   │       ├── application-dev.yml           # 开发环境配置
│   │       ├── application-prod.yml          # 生产环境配置
│   │       └── db/
│   │           └── migration/                # 数据库迁移脚本
│   └── test/                                # 测试代码
│       └── java/com/cloudcontrol/
│           ├── controller/                   # 控制器测试
│           ├── service/                      # 服务测试
│           └── repository/                   # 仓库测试
├── pom.xml                                  # Maven配置文件
└── Dockerfile                              # Docker构建文件
```

## 前端结构 (Vue 3 + TypeScript)

```
frontend/
├── public/
│   ├── index.html
│   └── favicon.ico
├── src/
│   ├── main.ts                             # 应用入口
│   ├── App.vue                             # 根组件
│   ├── router/                             # 路由配置
│   │   ├── index.ts
│   │   └── routes/
│   │       ├── auth.ts                     # 认证路由
│   │       ├── device.ts                   # 设备管理路由
│   │       ├── content.ts                  # 内容管理路由
│   │       └── play.ts                     # 播放控制路由
│   ├── store/                              # Pinia状态管理
│   │   ├── index.ts
│   │   ├── auth.ts                         # 认证状态
│   │   ├── device.ts                       # 设备状态
│   │   ├── content.ts                      # 内容状态
│   │   └── play.ts                         # 播放状态
│   ├── views/                              # 页面组件
│   │   ├── auth/
│   │   │   ├── Login.vue                   # 登录页面
│   │   │   └── Register.vue                # 注册页面
│   │   ├── dashboard/
│   │   │   ├── Dashboard.vue               # 仪表板
│   │   │   └── components/
│   │   │       ├── DeviceStatus.vue        # 设备状态组件
│   │   │       ├── ContentStats.vue        # 内容统计组件
│   │   │       └── PlayStatus.vue          # 播放状态组件
│   │   ├── device/
│   │   │   ├── DeviceList.vue              # 设备列表
│   │   │   ├── DeviceDetail.vue            # 设备详情
│   │   │   └── DeviceForm.vue              # 设备表单
│   │   ├── content/
│   │   │   ├── ContentList.vue             # 内容列表
│   │   │   ├── ContentUpload.vue           # 内容上传
│   │   │   └── ContentDetail.vue           # 内容详情
│   │   └── play/
│   │       ├── PlayControl.vue             # 播放控制
│   │       ├── PlaySchedule.vue            # 播放计划
│   │       └── PlayMonitor.vue             # 播放监控
│   ├── components/                         # 通用组件
│   │   ├── common/
│   │   │   ├── Header.vue                  # 头部组件
│   │   │   ├── Sidebar.vue                 # 侧边栏组件
│   │   │   ├── Footer.vue                  # 底部组件
│   │   │   └── Loading.vue                 # 加载组件
│   │   ├── ui/
│   │   │   ├── Button.vue                  # 按钮组件
│   │   │   ├── Modal.vue                   # 模态框组件
│   │   │   ├── Table.vue                   # 表格组件
│   │   │   └── Form.vue                    # 表单组件
│   │   └── charts/
│   │       ├── LineChart.vue               # 折线图
│   │       ├── BarChart.vue                # 柱状图
│   │       └── PieChart.vue                # 饼图
│   ├── api/                               # API接口
│   │   ├── index.ts                       # API配置
│   │   ├── auth.ts                        # 认证API
│   │   ├── device.ts                      # 设备API
│   │   ├── content.ts                     # 内容API
│   │   └── play.ts                        # 播放API
│   ├── utils/                             # 工具函数
│   │   ├── request.ts                     # HTTP请求封装
│   │   ├── auth.ts                        # 认证工具
│   │   ├── storage.ts                     # 存储工具
│   │   └── validate.ts                    # 验证工具
│   ├── types/                             # TypeScript类型定义
│   │   ├── user.ts                        # 用户类型
│   │   ├── device.ts                      # 设备类型
│   │   ├── content.ts                     # 内容类型
│   │   └── play.ts                        # 播放类型
│   ├── styles/                            # 样式文件
│   │   ├── main.scss                      # 主样式
│   │   ├── variables.scss                 # 变量定义
│   │   ├── mixins.scss                    # 混入
│   │   └── components/                    # 组件样式
│   └── assets/                            # 静态资源
│       ├── images/                        # 图片资源
│       ├── icons/                         # 图标资源
│       └── fonts/                         # 字体资源
├── package.json                           # 依赖配置
├── vite.config.ts                         # Vite配置
├── tsconfig.json                          # TypeScript配置
├── tailwind.config.js                     # Tailwind CSS配置
└── Dockerfile                            # Docker构建文件
```

## 基础设施结构

```
infrastructure/
├── docker/                               # Docker配置
│   ├── docker-compose.yml               # 开发环境编排
│   ├── docker-compose.prod.yml          # 生产环境编排
│   └── nginx/
│       └── nginx.conf                   # Nginx配置
├── kubernetes/                          # Kubernetes配置
│   ├── namespace.yml                    # 命名空间
│   ├── configmap.yml                   # 配置映射
│   ├── secret.yml                      # 密钥
│   ├── deployment.yml                  # 部署配置
│   ├── service.yml                     # 服务配置
│   └── ingress.yml                     # 入口配置
├── terraform/                          # Terraform配置
│   ├── main.tf                         # 主配置
│   ├── variables.tf                    # 变量定义
│   ├── outputs.tf                      # 输出定义
│   └── modules/                        # 模块
│       ├── vpc/                        # VPC模块
│       ├── ecs/                        # ECS模块
│       └── rds/                        # RDS模块
└── scripts/                            # 部署脚本
    ├── deploy.sh                       # 部署脚本
    ├── backup.sh                       # 备份脚本
    └── monitor.sh                      # 监控脚本
```

## 文档结构

```
docs/
├── README.md                           # 项目说明
├── environment_setup.md                # 环境安装指南
├── project_structure.md                # 项目结构说明
├── django_to_springboot_migration.md   # Django到Spring Boot迁移指南
├── api/                               # API文档
│   ├── auth.md                        # 认证API
│   ├── device.md                      # 设备API
│   ├── content.md                     # 内容API
│   └── play.md                        # 播放API
├── deployment/                        # 部署文档
│   ├── local.md                       # 本地部署
│   ├── docker.md                      # Docker部署
│   └── kubernetes.md                  # Kubernetes部署
└── development/                       # 开发文档
    ├── coding_standards.md            # 编码规范
    ├── git_workflow.md                # Git工作流
    └── testing.md                     # 测试指南
```

## 技术栈对应关系

| Django概念 | Spring Boot概念 | Vue概念 |
|-----------|----------------|---------|
| Model | Entity | TypeScript Interface |
| View | Controller | Vue Component |
| Template | Thymeleaf | Vue Template |
| URL配置 | @RequestMapping | Vue Router |
| Form | @Valid | Vue Form |
| Admin | 自定义管理界面 | Vue Admin |
| ORM | JPA/Hibernate | - |
| 中间件 | Filter/Interceptor | Vue Router Guard |
| 信号 | Event | Vue Event Bus |
| 缓存 | @Cacheable | Pinia |
| 任务队列 | @Async | Web Worker | 