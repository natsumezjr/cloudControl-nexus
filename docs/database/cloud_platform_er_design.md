# 云平台E-R设计文档（更新版）

## 设计背景

基于播控系统需求，设计完整的E-R图，支持用户管理、终端管理、指令发送、权限控制等核心功能。

---

## 核心实体设计

### 1. 用户实体 (User)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                        User                            │
├─────────────────────────────────────────────────────────┤
│ id (PK)                    BIGINT AUTO_INCREMENT       │
│ username                   VARCHAR(50) UNIQUE NOT NULL │
│ password                   VARCHAR(255) NOT NULL       │
│ email                      VARCHAR(100) UNIQUE NOT NULL│
│ role                       ENUM('ADMIN','MANAGER',...) │
│ status                     ENUM('ACTIVE','INACTIVE',..)│
│ created_at                 TIMESTAMP NOT NULL          │
│ last_login                 TIMESTAMP                   │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `id`: 主键，用户唯一标识
- `username`: 用户名，唯一约束
- `password`: 密码（加密存储）
- `email`: 邮箱地址，唯一约束
- `role`: 用户角色（ADMIN, MANAGER, EDITOR, OPERATOR, MONITOR）
- `status`: 用户状态（ACTIVE, INACTIVE, LOCKED）
- `created_at`: 创建时间
- `last_login`: 最后登录时间

### 2. 终端组实体 (TerminalGroup)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                   TerminalGroup                        │
├─────────────────────────────────────────────────────────┤
│ id (PK)                    BIGINT AUTO_INCREMENT       │
│ name                       VARCHAR(100) NOT NULL       │
│ description                VARCHAR(500)                │
│ parent_id (FK)             BIGINT                     │
│ sort_order                 INT                         │
│ enabled                    BOOLEAN NOT NULL DEFAULT TRUE│
│ created_at                 TIMESTAMP NOT NULL          │
│ updated_at                 TIMESTAMP                   │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `id`: 主键，终端组唯一标识
- `name`: 终端组名称
- `description`: 终端组描述
- `parent_id`: 父级终端组ID，支持层级结构
- `sort_order`: 排序字段
- `enabled`: 是否启用
- `created_at`: 创建时间
- `updated_at`: 更新时间

### 3. 终端实体 (Terminal)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                      Terminal                          │
├─────────────────────────────────────────────────────────┤
│ id (PK)                    BIGINT AUTO_INCREMENT       │
│ title                      VARCHAR(100) NOT NULL       │
│ status                     ENUM('PUBLISH','DRAFT',...) │
│ author (FK)                BIGINT NOT NULL            │
│ date                       TIMESTAMP NOT NULL          │
│ group_id (FK)              BIGINT                     │
│ ip_address                 VARCHAR(45)                 │
│ mac_address                VARCHAR(17)                 │
│ os                         VARCHAR(50)                 │
│ version                    VARCHAR(50)                 │
│ capabilities               TEXT                        │
│ last_heartbeat             TIMESTAMP                   │
│ location                   VARCHAR(200)                │
│ description                VARCHAR(500)                │
│ updated_at                 TIMESTAMP                   │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `id`: 主键，终端唯一标识
- `title`: 终端名称
- `status`: 终端状态（PUBLISH, DRAFT, OFFLINE, ONLINE, MAINTENANCE, ERROR）
- `author`: 创建者ID，外键关联User
- `date`: 创建时间
- `group_id`: 所属终端组ID，外键关联TerminalGroup
- `ip_address`: 终端IP地址
- `mac_address`: 终端MAC地址
- `os`: 终端操作系统
- `version`: 终端版本
- `capabilities`: 终端能力描述（JSON格式）
- `last_heartbeat`: 最后心跳时间
- `location`: 终端位置信息
- `description`: 终端描述
- `updated_at`: 更新时间

### 4. 指令实体 (Command)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                       Command                          │
├─────────────────────────────────────────────────────────┤
│ id (PK)                    BIGINT AUTO_INCREMENT       │
│ post (terminal_id FK)      BIGINT NOT NULL            │
│ content                     TEXT NOT NULL              │
│ act_url                     VARCHAR(500)               │
│ act_method                  INT                        │
│ status                      ENUM('PENDING','EXECUTING',│
│                             'SUCCESS','FAILED')       │
│ created_at                  TIMESTAMP NOT NULL         │
│ author (FK)                 BIGINT NOT NULL           │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `id`: 主键，指令唯一标识
- `post`: 目标终端ID，外键关联Terminal
- `content`: 指令内容（JSON格式）
- `act_url`: 指令执行URL
- `act_method`: HTTP方法（0-GET, 1-POST, 2-PUT, 3-DELETE）
- `status`: 指令状态（PENDING, EXECUTING, SUCCESS, FAILED）
- `created_at`: 创建时间
- `author`: 指令创建者ID，外键关联User

### 5. 指令结果实体 (CommandResult)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                   CommandResult                        │
├─────────────────────────────────────────────────────────┤
│ id (PK)                    BIGINT AUTO_INCREMENT       │
│ command_id (FK)            BIGINT NOT NULL            │
│ exit_code                  INT                         │
│ stdout                      TEXT                       │
│ stderr                      TEXT                       │
│ started_at                  TIMESTAMP                  │
│ finished_at                 TIMESTAMP                  │
│ duration_ms                 BIGINT                     │
│ error_message               VARCHAR(1000)              │
│ environment                 TEXT                       │
│ created_at                  TIMESTAMP NOT NULL         │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `id`: 主键，结果唯一标识
- `command_id`: 关联的指令ID，外键关联Command
- `exit_code`: 退出码
- `stdout`: 标准输出
- `stderr`: 标准错误输出
- `started_at`: 开始执行时间
- `finished_at`: 完成执行时间
- `duration_ms`: 执行耗时（毫秒）
- `error_message`: 错误信息
- `environment`: 执行环境信息
- `created_at`: 创建时间

### 6. 用户终端组关联实体 (UserTerminalGroup)

**实体定义：**
```
┌─────────────────────────────────────────────────────────┐
│                UserTerminalGroup                       │
├─────────────────────────────────────────────────────────┤
│ user_id (PK,FK)           BIGINT NOT NULL            │
│ terminal_group_id (PK,FK)  BIGINT NOT NULL            │
│ permission_type            ENUM('READ','WRITE','ADMIN')│
│ enabled                    BOOLEAN NOT NULL DEFAULT TRUE│
│ created_at                 TIMESTAMP NOT NULL          │
│ created_by                 BIGINT                     │
│ remarks                    VARCHAR(500)                │
└─────────────────────────────────────────────────────────┘
```

**字段说明：**
- `user_id`: 用户ID，联合主键的一部分，外键关联User
- `terminal_group_id`: 终端组ID，联合主键的一部分，外键关联TerminalGroup
- `permission_type`: 权限类型（READ, WRITE, ADMIN）
- `enabled`: 是否启用
- `created_at`: 创建时间
- `created_by`: 创建者ID
- `remarks`: 备注信息

---

## 实体关系设计

### 1. 用户-终端组关系 (M:N)
**关系表：** UserTerminalGroup
- 一个用户可以对多个终端组有权限
- 一个终端组可以被多个用户访问
- 通过UserTerminalGroup表实现多对多关系

### 2. 终端组-终端关系 (1:N)
**关系：** TerminalGroup (1) ←→ (N) Terminal
- 一个终端组可以包含多个终端
- 一个终端只能属于一个终端组

### 3. 终端组层级关系 (1:N)
**关系：** TerminalGroup (1) ←→ (N) TerminalGroup
- 一个父组可以有多个子组
- 一个子组只能有一个父组
- 支持无限层级结构

### 4. 终端-指令关系 (1:N)
**关系：** Terminal (1) ←→ (N) Command
- 一个终端可以接收多个指令
- 一个指令只能发送给一个终端

### 5. 指令-指令结果关系 (1:1)
**关系：** Command (1) ←→ (1) CommandResult
- 一个指令对应一个执行结果
- 一个结果对应一个指令

### 6. 用户-指令关系 (1:N)
**关系：** User (1) ←→ (N) Command
- 一个用户可以创建多个指令
- 一个指令只能有一个创建者

---

## 数据库迁移依赖关系

### 迁移脚本执行顺序

基于实体间的依赖关系，数据库迁移脚本必须按以下顺序执行：

#### 1. V1__create_users.sql
**依赖：** 无
**说明：** 用户表是基础表，其他表都依赖用户ID作为外键

#### 2. V2__create_terminal_groups.sql  
**依赖：** 无（自引用外键在创建时允许为空）
**说明：** 终端组表支持层级结构，parent_id自引用，创建时可为空

#### 3. V3__create_terminals.sql
**依赖：** 
- `users` 表 (author字段)
- `terminal_groups` 表 (group_id字段)
**说明：** 终端表依赖用户表和终端组表

#### 4. V4__create_commands.sql
**依赖：**
- `users` 表 (author字段)  
- `terminals` 表 (post字段)
**说明：** 指令表依赖用户表和终端表

#### 5. V5__create_user_terminal_groups.sql
**依赖：**
- `users` 表 (user_id字段)
- `terminal_groups` 表 (terminal_group_id字段)
**说明：** 用户终端组关联表依赖用户表和终端组表

#### 6. V6__create_command_results.sql
**依赖：**
- `commands` 表 (command_id字段)
**说明：** 指令结果表依赖指令表

### 外键约束依赖图

```
users (V1)
    ↓
terminal_groups (V2) ←→ self (parent_id)
    ↓
terminals (V3) ←→ users (author)
    ↓  
commands (V4) ←→ users (author), terminals (post)
    ↓
command_results (V6) ←→ commands (command_id)

user_terminal_groups (V5) ←→ users (user_id), terminal_groups (terminal_group_id)
```

### 迁移注意事项

1. **顺序重要性：** 严格按照V1→V2→V3→V4→V5→V6顺序执行
2. **外键约束：** 确保被引用的表已存在，外键字段类型匹配
3. **自引用外键：** V2中的parent_id允许为空，支持根级终端组
4. **联合主键：** V5使用(user_id, terminal_group_id)联合主键
5. **级联删除：** 根据业务需求设置适当的级联删除规则