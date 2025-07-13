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

### Terminal模块 ⏳

#### 开发状态
- **Entity**: ✅ 已完成
- **SQL**: ✅ 已完成 (V3__create_terminals.sql)
- **DTO**: ⏳ 待开发
- **Repository**: ⏳ 待开发
- **Service**: ⏳ 待开发
- **Controller**: ⏳ 待开发

#### 需要开发的DTO
```java
// 终端创建请求
public class TerminalCreateRequest {
    private String name;
    private String description;
    private Long groupId;
    private String ipAddress;
    private Integer port;
}

// 终端更新请求
public class TerminalUpdateRequest {
    private String name;
    private String description;
    private Long groupId;
    private String ipAddress;
    private Integer port;
}

// 终端响应
public class TerminalResponse {
    private Long id;
    private String name;
    private String description;
    private TerminalGroup group;
    private String ipAddress;
    private Integer port;
    private TerminalStatus status;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

#### 需要开发的Repository接口
```java
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    
    // 基础查询
    List<Terminal> findByGroupId(Long groupId);
    List<Terminal> findByStatus(TerminalStatus status);
    Optional<Terminal> findByIpAddressAndPort(String ipAddress, Integer port);
    
    // 业务查询
    List<Terminal> findOnlineTerminals();
    List<Terminal> findOfflineTerminals();
    List<Terminal> findByGroupIdAndStatus(Long groupId, TerminalStatus status);
    
    // 统计查询
    long countByGroupId(Long groupId);
    long countByStatus(TerminalStatus status);
    long countByGroupIdAndStatus(Long groupId, TerminalStatus status);
}
```

### TerminalGroup模块 ⏳

#### 开发状态
- **Entity**: ✅ 已完成
- **SQL**: ✅ 已完成 (V2__create_terminal_groups.sql)
- **DTO**: ⏳ 待开发
- **Repository**: ⏳ 待开发
- **Service**: ⏳ 待开发
- **Controller**: ⏳ 待开发

### Command模块 ⏳

#### 开发状态
- **Entity**: ✅ 已完成
- **SQL**: ✅ 已完成 (V4__create_commands.sql)
- **DTO**: ⏳ 待开发
- **Repository**: ⏳ 待开发
- **Service**: ⏳ 待开发
- **Controller**: ⏳ 待开发

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
1. **完成User模块Service层**
   - 实现用户注册服务
   - 实现用户登录服务
   - 实现用户管理服务

2. **开始Terminal模块开发**
   - 完成DTO设计
   - 完成Repository接口
   - 开始Service层开发

### 中期目标 (1个月)
1. **完成所有基础模块**
   - Terminal模块完整开发
   - TerminalGroup模块完整开发
   - Command模块完整开发

2. **完善测试体系**
   - 提高测试覆盖率
   - 完善集成测试
   - 建立自动化测试

### 长期目标 (2-3个月)
1. **系统集成**
   - 模块间集成测试
   - 性能优化
   - 安全加固

2. **生产部署**
   - 部署环境准备
   - 监控告警配置
   - 运维文档完善 