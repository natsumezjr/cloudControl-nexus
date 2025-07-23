# 开发连续性指南

## 概述

本指南强调开发的连续性，确保从Entity到Controller的完整开发流程。**前四个层次（Entity、SQL、DTO、Repository）必须一起开发**，然后为Service层提供接口。

## 开发层次架构

```
┌─────────────────┐
│   Controller    │ ← 最后开发，依赖Service
├─────────────────┤
│     Service     │ ← 依赖Repository接口
├─────────────────┤
│   Repository    │ ← 必须与Entity、SQL、DTO一起开发
├─────────────────┤
│      DTO        │ ← 必须与Entity、SQL、Repository一起开发
├─────────────────┤
│      SQL        │ ← 必须与Entity、DTO、Repository一起开发
├─────────────────┤
│     Entity      │ ← 必须与SQL、DTO、Repository一起开发
└─────────────────┘
```

## 开发原则

### 1. 四层联动开发
**Entity、SQL、DTO、Repository必须一起开发**，确保：
- 数据模型一致性
- 字段映射正确性
- 查询方法匹配性
- 类型安全保证

### 2. 接口先行
每个Repository必须为Service层提供清晰的接口定义，包括：
- 基础CRUD操作
- 业务查询方法
- 批量操作方法
- 事务处理方法

### 3. 测试驱动
每个层次开发完成后必须进行测试：
- Entity层：字段映射测试
- SQL层：迁移脚本测试
- DTO层：数据传输测试
- Repository层：数据库操作测试

## 已完成模块记录

### User模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成
- **SQL**: ✅ 已完成 (V1__create_users.sql)
- **DTO**: ✅ 已完成
- **Repository**: ✅ 已完成
- **Service**: ⏳ 待开发
- **Controller**: ⏳ 待开发

#### Repository接口定义

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 基础查询方法
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // 业务查询方法
    List<User> findByRole(UserRole role);
    List<User> findByStatus(UserStatus status);
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    
    // 分页查询
    Page<User> findByRole(UserRole role, Pageable pageable);
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    
    // 模糊查询
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByNicknameContainingIgnoreCase(String nickname);
    
    // 统计查询
    long countByRole(UserRole role);
    long countByStatus(UserStatus status);
    long countByRoleAndStatus(UserRole role, UserStatus status);
}
```

#### Service层需要的接口

```java
public interface UserService {
    
    // 用户注册
    UserRegisterResponse registerUser(UserRegisterRequest request);
    
    // 用户登录
    UserLoginResponse loginUser(UserLoginRequest request);
    
    // 用户信息查询
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    
    // 用户信息更新
    User updateUser(Long id, UserUpdateRequest request);
    
    // 用户状态管理
    User activateUser(Long id);
    User deactivateUser(Long id);
    
    // 用户角色管理
    User updateUserRole(Long id, UserRole role);
    
    // 用户列表查询
    Page<User> getUsersByRole(UserRole role, Pageable pageable);
    Page<User> getUsersByStatus(UserStatus status, Pageable pageable);
    
    // 用户搜索
    Page<User> searchUsers(String keyword, Pageable pageable);
    
    // 用户统计
    UserStatistics getUserStatistics();
    
    // 批量操作
    List<User> batchUpdateStatus(List<Long> userIds, UserStatus status);
    void batchDeleteUsers(List<Long> userIds);
}
```

#### 待开发的Service方法

1. **用户注册服务**
   - 验证用户名唯一性
   - 验证邮箱唯一性
   - 密码加密
   - 创建用户记录

2. **用户登录服务**
   - 验证用户名密码
   - 生成JWT Token
   - 更新最后登录时间

3. **用户信息管理**
   - 查询用户信息
   - 更新用户信息
   - 状态管理

4. **用户权限管理**
   - 角色分配
   - 权限验证
   - 状态控制

### Program模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (Program.java)
- **SQL**: ✅ 已完成 (V9__create_programs.sql, V15__add_missing_program_fields.sql)
- **DTO**: ✅ 已完成 (ProgramCreateRequest, ProgramResponse, ProgramListResponse等)
- **Repository**: ✅ 已完成 (ProgramRepository.java)
- **Service**: ✅ 已完成 (ProgramService.java, SimplifyCreateProgramService.java)
- **Controller**: ✅ 已完成 (ProgramController.java, ProgramInfoController.java)

#### 主要功能
1. **节目管理**
   - 创建节目 (POST /wp-json/wp/v2/programs)
   - 获取节目列表 (GET /wp-json/wp/v2/programs)
   - 获取单个节目 (GET /wp-json/wp/v2/programs/{programId})
   - 更新节目 (PUT /wp-json/wp/v2/programs/{programId})
   - 删除节目 (DELETE /wp-json/wp/v2/programs/{programId})

2. **节目发布管理**
   - 批量删除节目 (POST /wp-json/wp/v2/programs/delprograms)
   - 取消发布节目 (DELETE /wp-json/wp/v2/programPublishRecords)

3. **节目信息管理**
   - 节目详细信息查询
   - 节目状态管理
   - 节目作者关联

#### Repository接口定义
```java
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    
    // 基础查询
    List<Program> findByAuthor(Integer author);
    List<Program> findByStatus(String status);
    Optional<Program> findByTitle(String title);
    
    // 业务查询
    List<Program> findByAuthorAndStatus(Integer author, String status);
    List<Program> findByTitleContainingIgnoreCase(String title);
    
    // 分页查询
    Page<Program> findByStatus(String status, Pageable pageable);
    Page<Program> findByAuthor(Integer author, Pageable pageable);
    
    // 统计查询
    long countByAuthor(Integer author);
    long countByStatus(String status);
}
```

### Media模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (Media.java)
- **SQL**: ✅ 已完成 (V10__create_media.sql)
- **DTO**: ✅ 已完成 (MediaUploadResponse, MediaDeleteResponse, MediaInfo等)
- **Repository**: ✅ 已完成 (MediaRepository.java)
- **Service**: ✅ 已完成 (MediaService.java)
- **Controller**: ✅ 已完成 (MediaController.java)

#### 主要功能
1. **媒体文件管理**
   - 媒体文件上传
   - 媒体文件删除
   - 媒体文件信息查询
   - 媒体文件列表获取

2. **媒体类型支持**
   - 图片文件 (image)
   - 视频文件 (video)
   - 音频文件 (audio)
   - 其他文件 (file)

3. **文件属性管理**
   - 文件大小
   - 文件路径
   - 缩略图路径
   - 媒体尺寸 (宽高)
   - 媒体时长

#### Repository接口定义
```java
public interface MediaRepository extends JpaRepository<Media, Integer> {
    
    // 基础查询
    List<Media> findByAuthor(Integer author);
    List<Media> findByMediaType(String mediaType);
    List<Media> findByPostStatus(String postStatus);
    
    // 业务查询
    List<Media> findByAuthorAndMediaType(Integer author, String mediaType);
    List<Media> findByFilePath(String filePath);
    
    // 统计查询
    long countByAuthor(Integer author);
    long countByMediaType(String mediaType);
    long countByPostStatus(String postStatus);
}
```

### Schedule模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (相关实体类)
- **SQL**: ✅ 已完成 (相关迁移脚本)
- **DTO**: ✅ 已完成 (UpdateTerminalScheduleRequest, TerminalScheduleResponse等)
- **Repository**: ✅ 已完成 (相关Repository接口)
- **Service**: ✅ 已完成 (TerminalScheduleService.java, TerminalGroupScheduleService.java)
- **Controller**: ✅ 已完成 (ScheduleController.java, TerminalGroupScheduleController.java)

#### 主要功能
1. **终端排程管理**
   - 更新终端排程 (PUT /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules)
   - 获取终端排程 (GET /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules)

2. **终端组排程管理**
   - 获取终端组排程 (GET /wp-json/wp/v3/schedules/terminalGroupSchedules)
   - 应用终端组排程 (POST /wp-json/wp/v3/schedules/applyTerminalGroupSchedules)

3. **排程状态管理**
   - 排程状态查询
   - 排程错误处理
   - 权限验证

#### Repository接口定义
```java
// 终端排程相关Repository接口
public interface TerminalScheduleRepository extends JpaRepository<TerminalSchedule, Integer> {
    
    // 基础查询
    List<TerminalSchedule> findByTerminalId(Integer terminalId);
    List<TerminalSchedule> findByStatus(String status);
    
    // 业务查询
    List<TerminalSchedule> findByTerminalIdAndStatus(Integer terminalId, String status);
    
    // 统计查询
    long countByTerminalId(Integer terminalId);
    long countByStatus(String status);
}
```

### Terminal模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (Terminal.java, TerminalGroup.java等)
- **SQL**: ✅ 已完成 (V2__create_terminal_groups.sql, V3__create_terminals.sql, V14__add_missing_terminal_fields.sql)
- **DTO**: ✅ 已完成 (TerminalCreateRequest, TerminalResponse等)
- **Repository**: ✅ 已完成 (TerminalRepository.java, TerminalGroupRepository.java)
- **Service**: ✅ 已完成 (TerminalService.java, TerminalTagService.java)
- **Controller**: ✅ 已完成 (TerminalController.java, TerminalTagController.java)

#### 主要功能
1. **终端管理**
   - 终端创建和更新
   - 终端状态管理
   - 终端分组管理
   - 终端标签管理

2. **终端认证**
   - 终端认证接口
   - 终端心跳检测
   - 终端状态报告

3. **终端组管理**
   - 终端组创建
   - 终端组成员管理
   - 终端组权限控制

#### Repository接口定义
```java
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
    
    // 基础查询
    List<Terminal> findByGroupId(Integer groupId);
    List<Terminal> findByStatus(TerminalStatus status);
    Optional<Terminal> findBySerialNo(String serialNo);
    
    // 业务查询
    List<Terminal> findOnlineTerminals();
    List<Terminal> findOfflineTerminals();
    List<Terminal> findByGroupIdAndStatus(Integer groupId, TerminalStatus status);
    
    // 统计查询
    long countByGroupId(Integer groupId);
    long countByStatus(TerminalStatus status);
}
```

### Command模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (Command.java, CommandResult.java)
- **SQL**: ✅ 已完成 (V4__create_commands.sql, V6__create_command_results.sql)
- **DTO**: ✅ 已完成 (CommandRequest, CommandResponse等)
- **Repository**: ✅ 已完成 (CommandRepository.java, CommandResultRepository.java)
- **Service**: ✅ 已完成 (FtdBckCommandService.java, TmnlBckGetCommandService.java)
- **Controller**: ✅ 已完成 (FtdBckCommandController.java, TmnlBckCommandController.java)

#### 主要功能
1. **命令管理**
   - 命令创建和发送
   - 命令状态跟踪
   - 命令结果查询
   - 命令历史记录

2. **命令类型支持**
   - 终端控制命令
   - 系统管理命令
   - 数据同步命令

3. **命令执行状态**
   - 待执行 (PENDING)
   - 执行中 (EXECUTING)
   - 执行成功 (SUCCESS)
   - 执行失败 (FAILED)

#### Repository接口定义
```java
public interface CommandRepository extends JpaRepository<Command, Integer> {
    
    // 基础查询
    List<Command> findByTerminalId(Integer terminalId);
    List<Command> findByStatus(CommandStatus status);
    List<Command> findByType(CommandType type);
    
    // 业务查询
    List<Command> findByTerminalIdAndStatus(Integer terminalId, CommandStatus status);
    List<Command> findPendingCommands();
    
    // 统计查询
    long countByTerminalId(Integer terminalId);
    long countByStatus(CommandStatus status);
    long countByType(CommandType type);
}
```

### TUS模块 ✅

#### 开发状态
- **Entity**: ✅ 已完成 (TusUpload.java)
- **SQL**: ✅ 已完成 (V8__create_tus_upload.sql)
- **DTO**: ✅ 已完成 (TusUploadResponse等)
- **Repository**: ✅ 已完成 (TusUploadRepository.java)
- **Service**: ✅ 已完成 (TusService.java)
- **Controller**: ✅ 已完成 (TusController.java)

#### 主要功能
1. **大文件上传支持**
   - 分片上传
   - 断点续传
   - 上传进度跟踪
   - 上传状态管理

2. **TUS协议实现**
   - 符合TUS协议标准
   - 支持多种上传方式
   - 错误处理和重试机制

## 开发流程规范

### 1. 新模块开发流程

#### 步骤1: 四层联动开发
1. **Entity设计**
   - 定义实体类字段
   - 设置JPA注解
   - 定义关联关系

2. **SQL迁移脚本**
   - 创建表结构
   - 设置索引和约束
   - 验证外键关系

3. **DTO设计**
   - 创建请求DTO
   - 创建响应DTO
   - 定义字段映射

4. **Repository接口**
   - 定义基础CRUD方法
   - 定义业务查询方法
   - 定义统计方法

#### 步骤2: 测试验证
1. **单元测试**
   - Entity字段映射测试
   - Repository方法测试
   - DTO转换测试

2. **集成测试**
   - 数据库操作测试
   - 迁移脚本测试
   - 完整流程测试

#### 步骤3: Service层开发
1. **接口定义**
   - 定义业务方法接口
   - 定义事务边界
   - 定义异常处理

2. **实现开发**
   - 实现业务逻辑
   - 调用Repository方法
   - 处理业务异常

#### 步骤4: Controller层开发
1. **接口定义**
   - 定义REST API
   - 设置请求映射
   - 定义响应格式

2. **实现开发**
   - 实现API逻辑
   - 调用Service方法
   - 处理HTTP异常

### 2. 测试规范

#### 测试层次
1. **Entity测试**: 验证字段映射和关联关系
2. **Repository测试**: 验证数据库操作
3. **Service测试**: 验证业务逻辑
4. **Controller测试**: 验证API接口

#### 测试数据
- 使用测试数据库
- 每个测试独立数据
- 测试完成后清理

### 3. 文档规范

#### 必须记录的文档
1. **接口文档**: Repository和Service接口定义
2. **测试文档**: 测试用例和结果
3. **迁移文档**: SQL脚本说明
4. **API文档**: Controller接口说明

## 质量保证

### 1. 代码质量
- 遵循编码规范
- 添加必要注释
- 进行代码审查

### 2. 测试覆盖
- 单元测试覆盖率 > 80%
- 集成测试覆盖主要流程
- 端到端测试覆盖关键路径

### 3. 文档完整性
- 接口文档完整
- 测试文档详细
- 部署文档清晰

## 下一步计划

### 短期目标 (1-2周)
1. **完善现有模块**
   - 补充单元测试
   - 完善API文档
   - 优化错误处理

2. **系统集成测试**
   - 模块间集成测试
   - 端到端功能测试
   - 性能压力测试

### 中期目标 (1个月)
1. **功能完善**
   - 用户权限系统
   - 日志记录系统
   - 监控告警系统

2. **性能优化**
   - 数据库查询优化
   - 缓存机制实现
   - 并发处理优化

### 长期目标 (2-3个月)
1. **系统集成**
   - 模块间集成测试
   - 性能优化
   - 安全加固

2. **生产部署**
   - 部署环境准备
   - 监控告警配置
   - 运维文档完善 