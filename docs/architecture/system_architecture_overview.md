# 系统架构概览

## 概述

CloudControl-Nexus是一个基于云平台的智能设备控制系统，采用现代化的微服务架构设计，提供设备管理、内容分发、播放控制等功能。本文档从整体架构角度，全面描述系统的设计理念、技术栈、模块组织和部署架构。

## 1. 系统架构设计理念

### 1.1 设计原则

#### 分层解耦原则
- **职责分离**: 每层只负责自己的核心职责
- **依赖倒置**: 上层依赖下层的抽象接口
- **接口隔离**: 通过接口定义清晰的契约

#### 可扩展性原则
- **模块化设计**: 功能按模块组织，便于独立开发和部署
- **插件化架构**: 支持新功能的动态扩展
- **水平扩展**: 支持多实例部署和负载均衡

#### 高可用性原则
- **故障隔离**: 单个模块故障不影响整体系统
- **数据备份**: 关键数据多重备份机制
- **监控告警**: 完善的监控和告警体系

### 1.2 技术选型策略

#### 后端技术栈
- **Spring Boot 3.x**: 主框架，提供快速开发能力
- **Spring Security**: 安全认证和授权
- **Spring Data JPA**: 数据访问层抽象
- **PostgreSQL**: 主数据库，保证数据一致性
- **Redis**: 缓存和会话存储，提升性能
- **WebSocket**: 实时通信支持

#### 前端技术栈
- **Vue 3**: 现代化前端框架
- **TypeScript**: 类型安全
- **Pinia**: 状态管理
- **Tailwind CSS**: 样式框架

#### 基础设施
- **Docker**: 容器化部署
- **Nginx**: 反向代理和负载均衡

## 2. 整体架构视图

### 2.1 系统架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        前端层 (Frontend)                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Vue 3     │  │  TypeScript │  │ Tailwind CSS│        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP/HTTPS
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     网关层 (Gateway)                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │    Nginx    │  │   CORS      │  │   SSL/TLS   │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ 路由分发
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    应用层 (Application)                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │ Controller  │  │   Service   │  │ Repository  │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │    DTO      │  │   Entity    │  │   Config    │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ 数据访问
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    数据层 (Data Layer)                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │ PostgreSQL  │  │    Redis    │  │   File      │        │
│  │   (主数据库) │  │   (缓存)    │  │  (文件存储) │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ WebSocket
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    终端层 (Terminal)                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │  设备终端   │  │  播放器     │  │  传感器     │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 模块架构图

```
┌─────────────────────────────────────────────────────────────┐
│                    CloudControl-Nexus                       │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   User      │  │  Terminal   │  │  Program    │        │
│  │  模块       │  │  模块       │  │  模块       │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Media     │  │  Schedule   │  │  Command    │        │
│  │  模块       │  │  模块       │  │  模块       │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │    TUS      │  │   Auth      │  │   Config    │        │
│  │  模块       │  │  模块       │  │  模块       │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

## 3. 分层架构详解

### 3.1 表现层 (Presentation Layer)

#### 职责
- 处理HTTP请求和响应
- 参数验证和格式化
- 错误处理和响应封装

#### 核心组件
```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        // 参数验证
        // 调用服务层
        // 返回响应
    }
}
```

#### 设计特点
- **RESTful API**: 遵循REST设计原则
- **统一响应格式**: 标准化的响应结构
- **参数验证**: 使用Bean Validation进行参数校验
- **异常处理**: 全局异常处理器统一处理错误

### 3.2 业务层 (Business Layer)

#### 职责
- 业务逻辑处理
- 事务管理
- 权限控制
- 数据验证

#### 核心组件
```java
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponse createUser(UserCreateRequest request) {
        // 业务逻辑验证
        // 数据处理
        // 事务管理
    }
}
```

#### 设计特点
- **事务管理**: 使用@Transactional注解管理事务
- **业务规则**: 集中处理业务逻辑和规则
- **服务组合**: 支持服务间的组合调用
- **缓存策略**: 集成Redis缓存提升性能

### 3.3 数据访问层 (Data Access Layer)

#### 职责
- 数据库操作
- 数据查询
- 数据持久化

#### 核心组件
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") UserRole role);
}
```

#### 设计特点
- **JPA规范**: 使用Spring Data JPA简化数据访问
- **自定义查询**: 支持复杂查询和原生SQL
- **分页支持**: 内置分页和排序功能
- **缓存集成**: 支持查询结果缓存

### 3.4 数据层 (Data Layer)

#### 职责
- 数据存储
- 数据模型定义
- 数据迁移管理

#### 核心组件
```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    // getter/setter
}
```

#### 设计特点
- **ORM映射**: 使用JPA注解进行对象关系映射
- **数据验证**: 实体级别的数据验证
- **关系映射**: 支持一对一、一对多、多对多关系
- **版本控制**: 使用Flyway管理数据库版本

## 4. 核心模块架构

### 4.1 User模块 (用户管理)

#### 功能特性
- 用户注册和登录
- 用户信息管理
- 角色权限控制
- 用户状态管理

#### 架构设计
```
UserController → UserService → UserRepository → User Entity
     ↓              ↓              ↓              ↓
  HTTP API      业务逻辑       数据访问        数据模型
```

#### 关键接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/users/{id}` - 获取用户信息
- `PUT /api/users/{id}` - 更新用户信息

### 4.2 Terminal模块 (终端管理)

#### 功能特性
- 终端注册和认证
- 终端状态监控
- 终端分组管理
- 终端标签管理

#### 架构设计
```
TerminalController → TerminalService → TerminalRepository → Terminal Entity
       ↓                  ↓                  ↓                  ↓
    HTTP API           业务逻辑           数据访问            数据模型
       ↓
  WebSocket Handler → TerminalSessionManager → Redis
```

#### 关键接口
- `POST /api/terminals/register` - 终端注册
- `GET /api/terminals` - 获取终端列表
- `PUT /api/terminals/{id}/status` - 更新终端状态
- `WebSocket /ws/terminal` - 实时通信

### 4.3 Program模块 (节目管理)

#### 功能特性
- 节目创建和编辑
- 节目发布管理
- 节目状态跟踪
- 节目内容分发

#### 架构设计
```
ProgramController → ProgramService → ProgramRepository → Program Entity
       ↓                ↓                ↓                ↓
    HTTP API          业务逻辑          数据访问          数据模型
       ↓
  MediaService → FileStorage → File System
```

#### 关键接口
- `POST /api/programs` - 创建节目
- `GET /api/programs` - 获取节目列表
- `PUT /api/programs/{id}` - 更新节目
- `DELETE /api/programs/{id}` - 删除节目

### 4.4 Media模块 (媒体管理)

#### 功能特性
- 媒体文件上传
- 媒体文件管理
- 媒体类型支持
- 缩略图生成

#### 架构设计
```
MediaController → MediaService → MediaRepository → Media Entity
      ↓              ↓              ↓              ↓
   HTTP API        业务逻辑        数据访问        数据模型
      ↓
  FileService → TUSService → File Storage
```

#### 关键接口
- `POST /api/media/upload` - 媒体上传
- `GET /api/media/{id}` - 获取媒体信息
- `DELETE /api/media/{id}` - 删除媒体
- `GET /api/media/{id}/thumbnail` - 获取缩略图

### 4.5 Schedule模块 (排程管理)

#### 功能特性
- 终端排程管理
- 终端组排程
- 排程状态跟踪
- 排程冲突检测

#### 架构设计
```
ScheduleController → ScheduleService → ScheduleRepository → Schedule Entity
        ↓                ↓                ↓                ↓
     HTTP API           业务逻辑          数据访问          数据模型
        ↓
  TerminalService → Redis → Real-time Status
```

#### 关键接口
- `PUT /api/schedules/{terminalId}` - 更新终端排程
- `GET /api/schedules/{terminalId}` - 获取终端排程
- `POST /api/schedules/group` - 应用终端组排程
- `GET /api/schedules/group` - 获取终端组排程

### 4.6 Command模块 (命令管理)

#### 功能特性
- 命令创建和发送
- 命令状态跟踪
- 命令结果收集
- 命令历史记录

#### 架构设计
```
CommandController → CommandService → CommandRepository → Command Entity
       ↓                ↓                ↓                ↓
    HTTP API           业务逻辑          数据访问          数据模型
       ↓
  WebSocket Handler → Redis → Real-time Command Queue
```

#### 关键接口
- `POST /api/commands` - 创建命令
- `GET /api/commands/{id}/status` - 获取命令状态
- `GET /api/commands/{id}/result` - 获取命令结果
- `WebSocket /ws/command` - 实时命令下发

### 4.7 TUS模块 (大文件上传)

#### 功能特性
- 分片上传支持
- 断点续传
- 上传进度跟踪
- 上传状态管理

#### 架构设计
```
TusController → TusService → TusRepository → TusUpload Entity
      ↓            ↓            ↓              ↓
   HTTP API      业务逻辑      数据访问        数据模型
      ↓
  FileService → File Storage → File System
```

#### 关键接口
- `POST /api/tus/upload` - 创建上传会话
- `PATCH /api/tus/upload/{id}` - 上传分片
- `HEAD /api/tus/upload/{id}` - 获取上传状态
- `DELETE /api/tus/upload/{id}` - 删除上传

## 5. 数据架构设计

### 5.1 数据库设计原则

#### 规范化设计
- **第一范式**: 确保每个字段都是原子性的
- **第二范式**: 确保非主键字段完全依赖于主键
- **第三范式**: 确保非主键字段不依赖于其他非主键字段

#### 性能优化
- **索引策略**: 为常用查询字段创建索引
- **分区策略**: 大表按时间或ID进行分区
- **查询优化**: 使用合适的查询语句和连接方式

### 5.2 核心数据模型

#### 用户相关表
```sql
-- 用户表
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户终端组关系表
CREATE TABLE user_terminal_groups (
    user_id BIGINT NOT NULL,
    terminal_group_id BIGINT NOT NULL,
    permission_type VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    created_by BIGINT,
    remarks VARCHAR(500),
    PRIMARY KEY (user_id, terminal_group_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (terminal_group_id) REFERENCES terminal_groups(id)
);
```

#### 终端相关表
```sql
-- 终端组表
CREATE TABLE terminal_groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 终端表
CREATE TABLE terminals (
    serial_no SERIAL PRIMARY KEY,
    device_name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    account_name VARCHAR(100),
    terminal_group_id INTEGER REFERENCES terminal_groups(id),
    led_description TEXT,
    last_heartbeat TIMESTAMP,
    status VARCHAR(50),
    power_status INTEGER,
    brightness INTEGER,
    colortemp INTEGER,
    volume INTEGER,
    input_mode VARCHAR(50),
    language VARCHAR(10),
    country VARCHAR(10),
    timezone_id VARCHAR(50),
    timezone FLOAT,
    is_auto_time INTEGER,
    program VARCHAR(255),
    program_type VARCHAR(50),
    gps_report_interval INTEGER,
    sensor_report_interval INTEGER,
    content_report_status INTEGER,
    log_report VARCHAR(10),
    rotate_program_vsns_report VARCHAR(10),
    extra TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lat FLOAT,
    lng FLOAT
);

-- 终端标签表
CREATE TABLE terminal_tags (
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 终端标签关系表
CREATE TABLE terminal_tag_relations (
    relation_id SERIAL PRIMARY KEY,
    terminal_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES terminal_tags(tag_id) ON DELETE CASCADE,
    UNIQUE(terminal_id, tag_id)
);
```

#### 节目相关表
```sql
-- 节目表
CREATE TABLE programs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    program_info TEXT,
    programs_data TEXT,
    author INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    slug VARCHAR(255),
    vsn_name VARCHAR(255)
);

-- 节目终端关系表
CREATE TABLE program_terminal_relations (
    relation_id SERIAL PRIMARY KEY,
    program_id INTEGER NOT NULL,
    terminal_id INTEGER NOT NULL,
    publish_status VARCHAR(50) DEFAULT 'published',
    publish_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE,
    FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no) ON DELETE CASCADE,
    UNIQUE(program_id, terminal_id)
);
```

#### 媒体相关表
```sql
-- 媒体表
CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title_raw VARCHAR(255),
    description TEXT,
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    media_type VARCHAR(50),
    attachment_filesize BIGINT,
    file_path VARCHAR(500),
    thumbnail_path VARCHAR(500),
    width INTEGER,
    height INTEGER,
    duration INTEGER,
    author INTEGER REFERENCES users(id),
    guid VARCHAR(255),
    slug VARCHAR(255),
    source_url VARCHAR(500),
    src VARCHAR(500),
    post_status VARCHAR(50) DEFAULT 'inherit',
    comment_status VARCHAR(50) DEFAULT '',
    ping_status VARCHAR(50) DEFAULT '',
    parent INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 命令相关表
```sql
-- 命令表
CREATE TABLE commands (
    id SERIAL PRIMARY KEY,
    terminal_id INTEGER NOT NULL,
    cmd_num INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no) ON DELETE CASCADE
);

-- 命令结果表
CREATE TABLE command_results (
    id SERIAL PRIMARY KEY,
    command_id INTEGER NOT NULL,
    exit_code INTEGER,
    stdout TEXT,
    stderr TEXT,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    duration_ms BIGINT,
    error_message VARCHAR(1000),
    environment TEXT,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (command_id) REFERENCES commands(id)
);
```

#### 文件上传相关表
```sql
-- TUS上传表
CREATE TABLE tus_upload (
    id BIGSERIAL PRIMARY KEY,
    upload_id VARCHAR(255) NOT NULL UNIQUE,
    filename VARCHAR(500),
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    file_size BIGINT,
    upload_offset BIGINT DEFAULT 0,
    checksum VARCHAR(32),
    file_path VARCHAR(1000),
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 5.3 数据库索引设计

#### 核心索引
```sql
-- 用户表索引
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);

-- 终端表索引
CREATE INDEX idx_terminals_device_name ON terminals(device_name);
CREATE INDEX idx_terminals_terminal_group_id ON terminals(terminal_group_id);
CREATE INDEX idx_terminals_status ON terminals(status);
CREATE INDEX idx_terminals_last_heartbeat ON terminals(last_heartbeat);
CREATE INDEX idx_terminals_gps_coordinates ON terminals(lat, lng);

-- 终端标签索引
CREATE INDEX idx_terminal_tags_tag_name ON terminal_tags(tag_name);
CREATE INDEX idx_terminal_tag_relations_terminal_id ON terminal_tag_relations(terminal_id);
CREATE INDEX idx_terminal_tag_relations_tag_id ON terminal_tag_relations(tag_id);

-- 节目表索引
CREATE INDEX idx_programs_title ON programs(title);
CREATE INDEX idx_programs_status ON programs(status);
CREATE INDEX idx_programs_author ON programs(author);
CREATE INDEX idx_programs_slug ON programs(slug);
CREATE INDEX idx_programs_vsn_name ON programs(vsn_name);

-- 节目终端关系索引
CREATE INDEX idx_program_terminal_relations_program_id ON program_terminal_relations(program_id);
CREATE INDEX idx_program_terminal_relations_terminal_id ON program_terminal_relations(terminal_id);
CREATE INDEX idx_program_terminal_relations_publish_status ON program_terminal_relations(publish_status);

-- 媒体表索引
CREATE INDEX idx_media_title_raw ON media(title_raw);
CREATE INDEX idx_media_file_type ON media(file_type);
CREATE INDEX idx_media_media_type ON media(media_type);
CREATE INDEX idx_media_author ON media(author);
CREATE INDEX idx_media_guid ON media(guid);

-- 命令表索引
CREATE INDEX idx_commands_terminal_id ON commands(terminal_id);
CREATE INDEX idx_commands_cmd_num ON commands(cmd_num);
CREATE INDEX idx_commands_created_at ON commands(created_at);

-- 命令结果表索引
CREATE INDEX idx_command_results_command_id ON command_results(command_id);
CREATE INDEX idx_command_results_exit_code ON command_results(exit_code);
CREATE INDEX idx_command_results_started_at ON command_results(started_at);

-- TUS上传表索引
CREATE INDEX idx_tus_upload_checksum ON tus_upload(checksum);
CREATE INDEX idx_tus_upload_status ON tus_upload(status);
CREATE INDEX idx_tus_upload_created_at ON tus_upload(created_at);
```

### 5.4 数据库关系图

```
用户管理模块:
users (用户表)
├── user_terminal_groups (用户终端组关系表)
    └── terminal_groups (终端组表)

终端管理模块:
terminal_groups (终端组表)
├── terminals (终端表)
    ├── terminal_tag_relations (终端标签关系表)
    │   └── terminal_tags (终端标签表)
    ├── commands (命令表)
    │   └── command_results (命令结果表)
    └── program_terminal_relations (节目终端关系表)

内容管理模块:
programs (节目表)
├── program_terminal_relations (节目终端关系表)
    └── terminals (终端表)

media (媒体表)
└── programs (节目表) [通过programs_data字段关联]

文件上传模块:
tus_upload (TUS上传表)
└── media (媒体表) [通过file_path字段关联]
```

### 5.5 缓存策略

#### Redis缓存设计
```java
// 用户会话缓存
@Cacheable(value = "userSessions", key = "#userId")
public UserSession getUserSession(Long userId) {
    // 从数据库获取用户会话
}

// 终端状态缓存
@Cacheable(value = "terminalStatus", key = "#terminalId")
public TerminalStatus getTerminalStatus(Long terminalId) {
    // 从数据库获取终端状态
}

// 命令队列缓存
@Cacheable(value = "commandQueue", key = "#terminalId")
public List<Command> getCommandQueue(Long terminalId) {
    // 从Redis获取命令队列
}
```

#### 缓存更新策略
- **写入策略**: 先更新数据库，再更新缓存
- **失效策略**: 数据变更时主动失效相关缓存
- **过期策略**: 设置合理的缓存过期时间



## 6. 总结
CloudControl-Nexus采用现代化的分层架构设计，具有以下特点：

### 6.1 架构优势
- **分层清晰**: 职责分离，便于维护和扩展
- **模块化**: 功能模块独立，支持独立开发和部署
- **可扩展**: 支持水平扩展和垂直扩展
- **高可用**: 故障隔离，数据备份，监控告警

### 6.2 技术特点
- **现代化技术栈**: Spring Boot 3.x, Vue 3, TypeScript
- **数据安全**: PostgreSQL主数据库，Redis缓存
- **实时通信**: WebSocket支持实时数据交互
- **容器化部署**: Docker + Kubernetes支持

### 6.3 业务价值
- **设备管理**: 完整的终端生命周期管理
- **内容分发**: 支持多种媒体类型的内容分发
- **播放控制**: 灵活的排程和播放控制
- **实时监控**: 实时状态监控和告警
