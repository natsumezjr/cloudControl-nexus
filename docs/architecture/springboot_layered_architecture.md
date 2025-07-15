# Spring Boot 分层架构详解

## 概述

本指南以用户登录注册为完整场景，详细展示Spring Boot的分层架构设计。通过一个完整的业务流程，帮助您理解各层的职责分工和协作方式。

## 1. Spring Boot 分层架构概览

### 1.1 各层职责分工

| 层级 | 主要职责 | 核心组件 | 关键注解 |
|------|---------|---------|----------|
| **表现层** | 处理HTTP请求，返回响应 | Controller | `@RestController`, `@RequestMapping` |
| **业务层** | 业务逻辑处理，事务管理 | Service | `@Service`, `@Transactional` |
| **数据访问层** | 数据库操作，数据查询 | Repository | `@Repository`, `@Query` |
| **数据层** | 数据模型定义，ORM映射 | Entity | `@Entity`, `@Table` |

### 1.2 分层架构的优势

#### 1.2.1 职责分离
- **Controller**: 只负责HTTP请求处理
- **Service**: 只负责业务逻辑
- **Repository**: 只负责数据访问
- **Entity**: 只负责数据模型

#### 1.2.2 可维护性
- 各层独立开发
- 易于测试
- 易于扩展

#### 1.2.3 可重用性
- Service可以被多个Controller调用
- Repository可以被多个Service调用
- Entity可以被多个Repository使用

#### 1.2.4 安全性
- 通过DTO保护敏感数据
- 通过Service层进行权限控制
- 通过Repository层进行数据验证

---

## 2. Spring Bean 和依赖注入体系

### 2.1 编程中的依赖问题

**需求场景：** 在开发中，我们经常遇到一个类需要使用另一个类的功能。

**传统方式的问题：**
```java
// 传统方式：直接创建依赖对象
public class UserController {
    // 直接创建依赖，耦合度高
    private UserService userService = new UserService();
    
    public void createUser() {
        userService.createUser();
    }
}
```

**问题分析：**
- 类之间紧密耦合
- 难以测试（无法替换依赖）
- 难以扩展（修改依赖需要改代码）
- 违反开闭原则

### 2.2 依赖注入的思想

**核心思想：** 抽象类依赖抽象类，具体运行时把某个实例变成抽象类的实例。

**抽象概念：**
- **接口（Interface）**：定义行为规范
- **实现类（Implementation）**：提供具体实现
- **依赖注入（Dependency Injection）**：外部提供依赖

**设计原则：**
- 依赖抽象而不是具体实现
- 由外部容器管理对象创建
- 对象被动接收依赖

### 2.3 Spring IoC 容器

**IoC (Inversion of Control)** 控制反转是Spring框架的核心概念。

**核心原理：**
- 传统方式：对象自己创建依赖
- Spring方式：容器负责创建和管理对象，对象被动接收依赖

**Bean生命周期：**
```
1. 实例化 → 2. 属性注入 → 3. 初始化 → 4. 使用 → 5. 销毁
```

### 2.4 依赖注入的具体实现

#### 2.4.1 接口和实现类

**接口（Interface）**：定义行为规范，只包含方法声明，不包含具体实现。

**实现类（Implementation）**：提供接口方法的具体实现。

```java
// 1. 定义接口（抽象）
public interface UserService {
    User createUser(User user);
    User findByUsername(String username);
}

// 2. 实现接口（具体）
public class UserServiceImpl implements UserService {
    public User createUser(User user) {
        // 具体实现
        return user;
    }
    
    public User findByUsername(String username) {
        // 具体实现
        return new User();
    }
}
```

**关键语法：**
- `interface`：定义接口关键字
- `implements`：实现接口关键字
- 实现类必须实现接口中的所有方法

#### 2.4.2 Spring管理Bean

```java
// 3. Spring管理实现类
public class UserServiceImpl implements UserService {
    // 实现方法
}

// 4. 在需要的地方注入
public class UserController {
    
    private UserService userService;  // 依赖抽象接口
    
    // 构造器注入
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public void createUser() {
        userService.createUser(new User());  // 调用具体实现
    }
}
```

### 2.5 三种注入方式详解

#### 2.5.1 构造器注入（推荐）

**思想：** 通过构造函数强制注入依赖，确保依赖不可变。

```java
@RestController
public class UserController {
    
    private final UserService userService;  // final确保不可变
```

**final关键字的特点：**
- 修饰变量：变量只能被赋值一次，之后不能修改
- 修饰方法：方法不能被重写
- 修饰类：类不能被继承
- 在依赖注入中，final确保依赖不可变，提高安全性
    
    // 构造器注入：强制依赖，无法创建对象时没有依赖
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public void createUser() {
        userService.createUser(new User());
    }
}


**优势：**
- 强制依赖，无法创建对象时没有依赖
- 依赖不可变（final）
- 便于单元测试
- 线程安全

#### 2.5.2 Setter注入（可选依赖）

**思想：** 通过setter方法注入可选依赖，依赖可以为空。

```java
@RestController
public class UserController {
    
    private UserService userService;  // 可以为空
    
    // Setter注入：可选依赖
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public void createUser() {
        if (userService != null) {
            userService.createUser(new User());
        }
    }
}
```

**优势：**
- 可选依赖
- 运行时可以改变依赖
- 适合配置类依赖

#### 2.5.3 字段注入（不推荐）

**思想：** 直接在字段上注入依赖，代码简洁但耦合度高。

```java
@RestController
public class UserController {
    
    @Autowired  // 字段注入：简洁但不推荐
    private UserService userService;
    
    public void createUser() {
        userService.createUser(new User());
    }
}
```

**问题：**
- 无法注入final字段
- 难以单元测试
- 违反封装原则

### 2.6 数据库操作的基础概念

#### 2.6.1 数据库操作的需求

**需求场景：** 在开发中，我们需要将Java对象保存到数据库，从数据库查询数据。

**传统方式的问题：**
```java
// 传统方式：直接写SQL
public class UserDAO {
    public void saveUser(User user) {
        // 手动写SQL，容易出错
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        // 手动处理数据库连接、参数绑定、结果映射
    }
}
```

**问题分析：**
- 大量重复代码
- SQL语句容易出错
- 数据库操作复杂
- 难以维护

#### 2.6.2 ORM（对象关系映射）思想

**核心思想：** 将数据库表映射为Java对象，将对象操作转换为SQL操作。

**抽象概念：**
- **Entity（实体）**：对应数据库表
- **ORM框架**：自动处理对象和数据库的转换
- **JPA**：Java持久化API标准

**设计原则：**
- 以对象为中心
- 自动生成SQL
- 统一的数据访问接口

#### 2.6.3 JPA和Hibernate

**JPA (Java Persistence API)** 是Java持久化的标准接口。

**Hibernate** 是JPA的具体实现，提供ORM功能。

```java
// 1. 定义实体类（对应数据库表）
public class User {
    private Long id;
    private String username;
    private String email;
    
    // 构造函数、getter和setter
    public User() {}
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

// 2. 定义数据访问接口
public interface UserRepository {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();
}

// 3. 实现数据访问接口
public class UserRepositoryImpl implements UserRepository {
    
    public User save(User user) {
        // 这里会生成INSERT SQL语句
        // 实际实现中会连接数据库执行SQL
        return user;
    }
    
    public User findByUsername(String username) {
        // 这里会生成SELECT SQL语句
        // 实际实现中会连接数据库执行SQL
        return new User(username, "example@email.com");
    }
    
    public List<User> findAll() {
        // 这里会生成SELECT SQL语句
        // 实际实现中会连接数据库执行SQL
        return new ArrayList<>();
    }
```

**List和ArrayList的特点：**
- `List<User>`：用户列表接口，可以包含多个User对象
- `ArrayList<User>()`：List接口的具体实现，动态数组
- 支持添加、删除、查询等操作
}

// 4. 在业务层使用
public class UserService {
    
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createUser(User user) {
        return userRepository.save(user);  // 自动生成INSERT SQL
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // 自动生成SELECT SQL
    }
}
```

### 2.7 事务管理的基础概念

#### 2.7.1 事务的需求

**需求场景：** 在开发中，我们经常需要保证多个数据库操作要么全部成功，要么全部失败。

**具体例子：** 用户注册时需要同时创建用户记录和用户角色记录。

```java
// 没有事务管理的问题
public void registerUser(User user) {
    userRepository.save(user);  // 如果这里成功
    roleRepository.save(userRole);  // 但这里失败，数据不一致
}
```

#### 2.7.2 ACID特性

**事务的四个特性：**
- **原子性（Atomicity）**：事务是不可分割的工作单位
- **一致性（Consistency）**：事务执行前后数据状态一致
- **隔离性（Isolation）**：事务之间相互隔离
- **持久性（Durability）**：事务提交后永久保存

#### 2.7.3 Spring事务管理

```java
public class UserService {
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    
    public void registerUser(User user) {
        // 这些操作在同一个事务中
        User savedUser = userRepository.save(user);
        Role userRole = new Role(savedUser.getId(), "USER");
        roleRepository.save(userRole);
        
        // 如果任何一步失败，所有操作都会回滚
        // 实际项目中会使用事务管理注解来确保原子性
    }
}
```

### 2.8 Web开发的基础概念

#### 2.8.1 HTTP协议

**需求场景：** 前端需要与后端进行数据交互。

**HTTP方法：**
- **GET**：获取数据
- **POST**：创建数据
- **PUT**：更新数据
- **DELETE**：删除数据

#### 2.8.2 HTTP认证机制

**需求场景：** 保护API接口，确保只有授权用户才能访问。

**Basic认证：**
```http
GET /api/users/profile HTTP/1.1
Host: api.example.com
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
```

**Token认证：**
```http
GET /api/users/profile HTTP/1.1
Host: api.example.com
Cookie: JSESSIONID=11E7CF5214352342B36AFA2EC25DFDA3
```

**JWT认证：**
```http
GET /api/users/profile HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**认证流程：**
1. **Basic认证**：用户名密码经过BASE64编码后放在Authorization头中
2. **Session认证**：登录后服务器返回JSESSIONID，后续请求通过Cookie传递
3. **JWT认证**：登录后服务器返回JWT令牌，后续请求通过Authorization头传递

#### 2.8.3 REST API

**REST (Representational State Transfer)** 是一种Web服务架构风格。

**核心思想：**
- 使用HTTP方法表示操作
- 使用URL表示资源
- 使用JSON传输数据

```java
// REST API示例
public class UserController {
    
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // GET /api/users/1
    public User getUser(Long id) {
        return userService.findById(id);
    }
    
    // POST /api/users
    public User createUser(User user) {
        return userService.createUser(user);
    }
    
    // PUT /api/users/1
    public User updateUser(Long id, User user) {
        return userService.updateUser(id, user);
    }
    
    // DELETE /api/users/1
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
```

#### 2.8.4 JSON序列化

**需求场景：** 前端发送JSON数据，后端需要解析；后端返回数据，前端需要解析。

**序列化：** 将Java对象转换为JSON字符串
**反序列化：** 将JSON字符串转换为Java对象

```java
// Spring Boot自动处理JSON序列化
public class UserController {
    
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public User createUser(User user) {
        // 实际项目中会使用@RequestBody自动将JSON反序列化为User对象
        return userService.createUser(user);
    }
    
    public User getUser(Long id) {
        User user = userService.findById(id);
        return user;  // 实际项目中会自动将User对象序列化为JSON
    }
}
```

### 2.9 Spring Boot 配置体系

#### 2.9.1 配置文件的需求

**需求场景：** 在开发中，我们需要配置数据库连接、服务器端口、日志级别等，这些配置不应该硬编码在代码中。

**配置文件的优势：**
- 环境隔离（开发、测试、生产）
- 配置集中管理
- 无需重新编译代码

#### 2.9.2 application.yml 配置

**YAML格式：** 一种人类可读的数据序列化格式，比XML更简洁。

```yaml
# application.yml - Spring Boot主配置文件
server:
  port: 8080  # 服务器端口

spring:
  application:
    name: cloud-control-nexus  # 应用名称
  
  # 数据库配置
  datasource:
    url: jdbc:postgresql://localhost:5432/cloudcontrol
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  
  # JPA配置
  jpa:
    hibernate:
      ddl-auto: update  # 自动更新数据库表结构
    show-sql: true  # 显示SQL语句
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect  # 数据库方言
        format_sql: true  # 格式化SQL输出
  
  # 日志配置
  logging:
    level:
      root: INFO  # 根日志级别
      com.cloudcontrol: DEBUG  # 项目包日志级别
    pattern:
      console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

# 自定义配置
app:
  jwt:
    secret: your-secret-key
    expiration: 86400000  # 24小时
  file:
    upload-path: /uploads
    max-size: 10MB
```

#### 2.9.3 环境配置分离

**需求场景：** 不同环境需要不同的配置（开发环境用内存数据库，生产环境用真实数据库）。

```yaml
# application-dev.yml - 开发环境配置
spring:
  datasource:
    url: jdbc:h2:mem:testdb  # 内存数据库
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop  # 每次启动重新创建表
    show-sql: true

---
# application-prod.yml - 生产环境配置
spring:
  datasource:
    url: jdbc:postgresql://prod-server:5432/cloudcontrol
    username: ${DB_USERNAME}  # 使用环境变量
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate  # 只验证表结构，不修改
    show-sql: false  # 生产环境不显示SQL
```

#### 2.9.4 配置属性绑定

**需求场景：** 在代码中使用配置文件中的值。

```java
// 配置属性类
public class AppConfig {
    private String jwtSecret;
    private long jwtExpiration;
    private String uploadPath;
    private String maxSize;
    
    // getter和setter方法
    public String getJwtSecret() { return jwtSecret; }
    public void setJwtSecret(String jwtSecret) { this.jwtSecret = jwtSecret; }
    
    public long getJwtExpiration() { return jwtExpiration; }
    public void setJwtExpiration(long jwtExpiration) { this.jwtExpiration = jwtExpiration; }
    
    public String getUploadPath() { return uploadPath; }
    public void setUploadPath(String uploadPath) { this.uploadPath = uploadPath; }
    
    public String getMaxSize() { return maxSize; }
    public void setMaxSize(String maxSize) { this.maxSize = maxSize; }
}

// 在代码中使用配置
public class JwtService {
    
    private AppConfig appConfig;
    
    public JwtService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
    
    public String generateToken(String username) {
        // 使用配置文件中的密钥和过期时间
        String secret = appConfig.getJwtSecret();
        long expiration = appConfig.getJwtExpiration();
        // 生成JWT令牌的逻辑
        return "generated-token";
    }
}
```

#### 2.9.5 数据库连接配置详解

**需求场景：** 配置数据库连接池、连接参数等。

```yaml
spring:
  datasource:
    # 基本连接配置
    url: jdbc:postgresql://localhost:5432/cloudcontrol
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
    
    # 连接池配置（HikariCP）
    hikari:
      maximum-pool-size: 20  # 最大连接数
      minimum-idle: 5  # 最小空闲连接
      connection-timeout: 30000  # 连接超时时间
      idle-timeout: 600000  # 空闲超时时间
      max-lifetime: 1800000  # 连接最大生命周期
    
    # 连接属性
    hibernate:
      dialect: org.hibernate.dialect.PostgreSQLDialect
      format_sql: true
      use_sql_comments: true
```

#### 2.9.6 日志配置详解

**需求场景：** 配置不同级别的日志输出，便于调试和监控。

```yaml
logging:
  # 日志级别配置
  level:
    root: INFO  # 根日志级别
    com.cloudcontrol: DEBUG  # 项目包日志级别
    org.springframework.web: DEBUG  # Spring Web日志
    org.hibernate.SQL: DEBUG  # Hibernate SQL日志
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE  # SQL参数日志
  
  # 日志输出格式
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n"
  
  # 日志文件配置
  file:
    name: logs/cloudcontrol.log  # 日志文件路径
    max-size: 10MB  # 单个日志文件最大大小
    max-history: 30  # 保留的日志文件数量
```

### 2.10 数据库迁移配置

#### 2.10.1 数据库版本管理的需求

**项目管理场景：** 在团队开发中，数据库结构经常需要变更。

**具体问题：**
1. **开发环境混乱**：每个开发者的数据库结构不一致
2. **测试数据丢失**：手动修改数据库导致测试数据丢失
3. **部署失败**：生产环境数据库结构与代码不匹配
4. **回滚困难**：数据库变更无法回滚到之前的版本
5. **团队协作困难**：无法追踪谁修改了数据库结构

**解决方案：** 使用数据库迁移工具，将数据库结构变更纳入版本控制。

#### 2.10.2 Flyway数据库迁移

**核心思想：** 将数据库结构变更脚本化，实现版本控制和自动化部署。

**项目管理优势：**
- **版本控制**：数据库变更与代码变更同步
- **自动化部署**：CI/CD流程中自动执行数据库迁移
- **环境一致性**：确保所有环境数据库结构一致
- **回滚支持**：支持数据库结构回滚
- **团队协作**：所有开发者使用相同的数据库结构

```yaml
# application.yml - Flyway配置
spring:
  flyway:
    enabled: true  # 启用Flyway
    locations: classpath:db/migration  # 迁移脚本位置
    baseline-on-migrate: true  # 基线迁移
    validate-on-migrate: true  # 迁移前验证
    out-of-order: false  # 按顺序执行
    table: flyway_schema_history  # 版本记录表
```

**迁移脚本示例：**
```sql
-- V1__Create_users_table.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- V2__Add_user_status.sql
ALTER TABLE users ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';
```

#### 2.10.3 Liquibase数据库迁移

**项目管理场景：** 大型项目需要更复杂的数据库变更管理。

**具体需求：**
1. **多环境管理**：开发、测试、预生产、生产环境需要不同的数据库变更
2. **数据迁移**：不仅变更结构，还需要迁移历史数据
3. **变更回滚**：复杂的数据库变更需要精确的回滚策略
4. **变更验证**：确保数据库变更不会破坏现有数据
5. **变更审计**：记录每次数据库变更的详细信息

**Liquibase优势：**
- **XML配置**：更结构化的变更定义
- **变更集**：将多个相关变更组织在一起
- **上下文支持**：根据环境执行不同的变更
- **变更验证**：执行前验证变更的安全性
- **详细日志**：记录每次变更的详细信息

**变更日志示例：**
```xml
<!-- db.changelog-master.xml -->
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog">
    <include file="db/changelog/changes/001-create-users-table.xml"/>
    <include file="db/changelog/changes/002-add-user-status.xml"/>
</databaseChangeLog>
```

### 2.11 Maven依赖管理

#### 2.11.1 项目依赖管理的需求

**项目管理场景：** 在大型项目中，依赖管理变得非常复杂。

**具体问题：**
1. **依赖冲突**：不同模块使用相同依赖的不同版本
2. **版本不一致**：开发、测试、生产环境使用不同版本
3. **构建失败**：依赖缺失或版本不兼容导致构建失败
4. **安全漏洞**：使用过时的依赖版本存在安全风险
5. **构建缓慢**：重复下载依赖，构建时间过长
6. **部署问题**：生产环境缺少必要的依赖

**解决方案：** 使用Maven进行统一的依赖管理和构建配置。

#### 2.11.2 pom.xml配置

**核心思想：** 将项目配置、依赖管理、构建流程统一管理。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- 项目基本信息 -->
    <groupId>com.cloudcontrol</groupId>
    <artifactId>cloud-control-nexus</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <name>Cloud Control Nexus</name>
    <description>云平台控制系统 - 终端管理和指令下发平台</description>
    
    <!-- Spring Boot父项目 - 提供依赖管理和版本控制 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <!-- 项目属性配置 -->
    <properties>
        <!-- Java版本配置 -->
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        
        <!-- 项目编码配置 -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        
        <!-- 依赖版本管理 -->
        <jwt.version>0.11.5</jwt.version>
        <springdoc.version>2.1.0</springdoc.version>
    </properties>
    
    <!-- 项目依赖管理 -->
    <dependencies>
        
        <!-- ==================== 核心Web框架 ==================== -->
        <!-- Spring Boot Web Starter - 提供REST API支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- 包含：Spring MVC, Tomcat, Jackson, 验证器等 -->
        </dependency>
        
        <!-- ==================== 数据访问层 ==================== -->
        <!-- Spring Boot Data JPA - 提供ORM和数据库操作支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <!-- 包含：Hibernate, JPA, 事务管理等 -->
        </dependency>
        
        <!-- PostgreSQL数据库驱动 -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope> <!-- 运行时依赖，编译时不需要 -->
        </dependency>
        
        <!-- Flyway数据库迁移工具 - 管理数据库版本 -->
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <!-- 自动执行SQL脚本，管理数据库结构变更 -->
        </dependency>
        
        <!-- ==================== 缓存层 ==================== -->
        <!-- Redis缓存支持 - 用户会话管理和终端状态缓存 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <!-- 包含：Redis连接池, RedisTemplate, 序列化器等 -->
        </dependency>
        
        <!-- ==================== 安全认证层 ==================== -->
        <!-- Spring Security - 提供认证授权功能 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <!-- 包含：认证, 授权, 密码加密, 会话管理等 -->
        </dependency>
        
        <!-- JWT Token支持 - 无状态认证 -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jwt.version}</version>
            <!-- JWT Token生成和验证API -->
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
            <!-- JWT实现类，运行时加载 -->
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
            <!-- JWT Jackson序列化支持 -->
        </dependency>
        
        <!-- ==================== 数据验证层 ==================== -->
        <!-- 参数验证支持 - 请求参数校验 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <!-- 包含：@Valid, @NotNull, @Size等验证注解 -->
        </dependency>
        
        <!-- ==================== API文档层 ==================== -->
        <!-- OpenAPI 3.0文档支持 - 自动生成API文档 -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
            <!-- 自动生成Swagger UI文档，支持在线测试 -->
        </dependency>
        
        <!-- ==================== 监控运维层 ==================== -->
        <!-- Spring Boot Actuator - 应用监控和健康检查 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <!-- 提供：健康检查, 指标监控, 环境信息等端点 -->
        </dependency>
        
        <!-- ==================== 日志层 ==================== -->
        <!-- 日志支持 - 应用日志记录 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
            <!-- 包含：Logback, 日志配置, 日志级别控制 -->
        </dependency>
        
        <!-- ==================== 测试层 ==================== -->
        <!-- Spring Boot测试支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <!-- 包含：JUnit 5, Mockito, Spring Test等 -->
        </dependency>
        
        <!-- Spring Security测试支持 -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
            <!-- 安全相关的测试工具和注解 -->
        </dependency>
        
    </dependencies>
    
    <!-- ==================== 构建配置 ==================== -->
    <build>
        <plugins>
            <!-- Spring Boot Maven插件 - 打包可执行JAR -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!-- 排除开发工具，生产环境不需要 -->
                    <excludes>
                        <exclude>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-devtools</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            
            <!-- Maven编译插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            
            <!-- Maven资源插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
    
    <!-- ==================== 依赖管理 ==================== -->
    <dependencyManagement>
        <dependencies>
            <!-- 统一管理依赖版本 -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${parent.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
</project>
```

#### 2.11.3 依赖版本管理

**项目管理场景：** 多模块项目中，依赖版本管理变得至关重要。

**具体需求：**
1. **版本统一**：确保所有模块使用相同版本的依赖
2. **冲突解决**：自动解决依赖版本冲突
3. **安全更新**：及时更新存在安全漏洞的依赖
4. **兼容性测试**：确保新版本依赖与现有代码兼容
5. **回滚机制**：依赖版本问题时的快速回滚
6. **依赖审计**：定期检查项目依赖的安全性和许可证

**版本管理策略：**
- **BOM（Bill of Materials）**：统一管理Spring生态版本
- **依赖锁定**：锁定特定版本，避免意外更新
- **安全扫描**：集成安全扫描工具，及时发现漏洞

```xml
<!-- 在properties中定义版本 -->
<properties>
    <spring-boot.version>3.2.0</spring-boot.version>
    <postgresql.version>42.7.1</postgresql.version>
    <flyway.version>10.8.1</flyway.version>
</properties>

<!-- 在dependencyManagement中管理版本 -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>${postgresql.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 2.12 Docker容器化配置

#### 2.12.1 容器化部署的需求

**项目管理场景：** 传统部署方式在大型项目中遇到严重挑战。

**具体问题：**
1. **环境差异**：开发、测试、生产环境配置不一致
2. **部署复杂**：手动部署容易出错，难以重复
3. **扩展困难**：水平扩展需要重新配置每台服务器
4. **资源浪费**：每台服务器需要安装完整的运行环境
5. **故障排查**：环境问题难以复现和排查
6. **团队协作**：运维团队和开发团队职责不清

**解决方案：** 使用Docker容器化，实现"一次构建，到处运行"。

#### 2.12.2 Dockerfile配置

**核心思想：** 将应用和运行环境打包成镜像，确保环境一致性。

```dockerfile
# Dockerfile - 多阶段构建
FROM openjdk:17-jdk-slim AS builder

# 设置工作目录
WORKDIR /app

# 复制Maven配置文件
COPY pom.xml .
COPY src ./src

# 安装Maven并构建应用
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:17-jre-slim

# 设置工作目录
WORKDIR /app

# 复制构建的jar文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置JVM参数
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

#### 2.12.3 docker-compose配置

**项目管理场景：** 微服务架构中，需要协调多个服务的部署和运行。

**具体需求：**
1. **服务编排**：协调应用、数据库、缓存、消息队列等多个服务
2. **网络管理**：服务间通信的网络配置
3. **数据持久化**：数据库和缓存数据的持久化存储
4. **服务发现**：服务间的自动发现和注册
5. **负载均衡**：多个应用实例的负载均衡
6. **监控集成**：统一的日志收集和监控

**编排优势：**
- **一键启动**：所有服务通过一个命令启动
- **环境隔离**：每个服务运行在独立的容器中
- **配置管理**：统一的配置文件管理所有服务
- **扩展灵活**：可以独立扩展某个服务

```yaml
# docker-compose.yml - 开发环境
version: '3.8'

services:
  # 应用服务
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/cloudcontrol
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=password
    depends_on:
      - db
    volumes:
      - ./logs:/app/logs
  
  # PostgreSQL数据库
  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=cloudcontrol
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
  
  # Redis缓存
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data

volumes:
  postgres_data:
  redis_data:
```

### 2.13 IDE配置

#### 2.13.1 开发环境标准化的需求

**项目管理场景：** 团队开发中，开发环境不一致导致严重问题。

**具体问题：**
1. **代码风格不统一**：不同开发者使用不同的代码格式
2. **调试困难**：本地环境问题难以复现
3. **新人上手慢**：新成员需要很长时间配置开发环境
4. **代码质量参差不齐**：缺乏统一的代码规范和检查
5. **构建失败**：本地构建成功，CI/CD构建失败
6. **团队协作效率低**：代码审查困难，合并冲突频繁

**解决方案：** 统一开发环境配置，建立代码规范和自动化检查。

#### 2.13.2 IntelliJ IDEA配置

**核心思想：** 通过IDE配置实现团队开发环境的标准化。

**项目配置：**
1. **导入Maven项目**：File → Open → 选择pom.xml
2. **配置JDK**：File → Project Structure → Project SDK
3. **配置Maven**：File → Settings → Build Tools → Maven

**代码风格配置：**
```xml
<!-- .editorconfig -->
root = true

[*]
charset = utf-8
end_of_line = lf
insert_final_newline = true
trim_trailing_whitespace = true

[*.java]
indent_style = space
indent_size = 4

[*.xml]
indent_style = space
indent_size = 2

[*.yml]
indent_style = space
indent_size = 2
```

#### 2.13.3 VS Code配置

**项目管理场景：** 团队中有不同偏好的开发者，需要提供多种IDE选择。

**具体需求：**
1. **轻量级选择**：部分开发者偏好轻量级编辑器
2. **跨平台支持**：支持Windows、Mac、Linux多个平台
3. **插件生态**：丰富的插件支持各种开发需求
4. **团队协作**：支持实时协作和代码审查
5. **性能优化**：大型项目的性能优化
6. **成本控制**：开源免费，降低团队成本

**VS Code优势：**
- **轻量快速**：启动速度快，资源占用少
- **插件丰富**：Java、Spring Boot、Docker等插件完善
- **Git集成**：内置Git支持，代码管理方便
- **调试支持**：强大的调试功能

**扩展推荐：**
- Extension Pack for Java
- Spring Boot Extension Pack
- Docker
- PostgreSQL

**工作区配置：**
```json
// .vscode/settings.json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
    "java.format.settings.profile": "GoogleStyle"
}
```

### 2.14 环境变量配置

#### 2.14.1 环境变量管理的需求

**项目管理场景：** 多环境部署中，配置管理变得复杂且容易出错。

**具体问题：**
1. **配置泄露**：敏感配置（密码、密钥）硬编码在代码中
2. **环境混乱**：不同环境使用相同的配置导致问题
3. **部署错误**：手动修改配置容易出错
4. **安全风险**：生产环境配置泄露到开发环境
5. **维护困难**：配置分散在多个文件中，难以维护
6. **团队协作**：配置变更需要通知所有团队成员

**解决方案：** 使用环境变量和配置文件分离，实现配置的集中管理。

#### 2.14.2 系统环境变量

**核心思想：** 将工具路径和基础配置通过环境变量管理。

**Windows环境变量：**
```batch
# JAVA_HOME
JAVA_HOME=C:\Program Files\Java\jdk-17

# MAVEN_HOME
MAVEN_HOME=C:\Program Files\Apache\maven\apache-maven-3.9.10

# PATH
PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
```

**Linux/Mac环境变量：**
```bash
# ~/.bashrc 或 ~/.zshrc
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export MAVEN_HOME=/opt/apache-maven-3.9.10
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

#### 2.14.3 应用环境变量

**项目管理场景：** 应用在不同环境需要不同的配置参数。

**具体需求：**
1. **环境隔离**：开发、测试、生产环境使用不同配置
2. **安全隔离**：敏感信息（数据库密码、API密钥）与代码分离
3. **动态配置**：运行时可以修改配置，无需重启应用
4. **配置验证**：确保配置的完整性和正确性
5. **配置审计**：记录配置变更的历史和原因
6. **故障恢复**：配置问题时的快速回滚机制

**配置管理策略：**
- **分层配置**：系统级、应用级、用户级配置分离
- **配置加密**：敏感配置的加密存储
- **配置验证**：启动时验证配置的完整性
- **配置监控**：实时监控配置变更的影响

```bash
# 开发环境
export SPRING_PROFILES_ACTIVE=dev
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cloudcontrol
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=password

# 生产环境
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-server:5432/cloudcontrol
export SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
export SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
```

---

## 3. 注解解耦合详解（按耦合性从低到高）

### 3.1 数据层注解（最低耦合）

#### 3.1.1 @Entity 注解

**作用：** 标记一个类为JPA实体，表示这个类对应数据库中的一张表。

**特点：**
- 不依赖其他组件
- 纯数据模型定义
- 独立存在

```java
// User.java - 用户实体类
@Entity  // 标记这是一个JPA实体，对应数据库中的user表
public class User {
    
    @Id  // 标记主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自增
    private Long id;
    
    @Column(unique = true, nullable = false)  // 唯一且不能为空
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    // 构造函数、getter、setter方法
    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

**@Entity注解的作用：**
- ✅ 告诉Spring这是一个JPA实体
- ✅ 自动创建对应的数据库表
- ✅ 启用ORM映射功能
- ❌ 不包含业务逻辑
- ❌ 不依赖其他组件

#### 3.1.2 @Table 注解

**作用：** 指定实体对应的数据库表名和表结构。

```java
@Entity
@Table(name = "users")  // 指定表名为users，而不是默认的user
public class User {
    // 实体内容
}
```

**@Table注解的作用：**
- ✅ 指定数据库表名
- ✅ 配置表结构（如索引、约束）
- ✅ 独立于业务逻辑
- ❌ 不依赖其他组件

#### 3.1.3 @Column 注解

**作用：** 指定实体字段对应的数据库列名和列属性。

```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", unique = true, nullable = false, length = 50)
    private String username;  // 对应数据库列名user_name，唯一，不能为空，最大长度50
    
    @Column(name = "email_address", nullable = false)
    private String email;  // 对应数据库列名email_address，不能为空
    
    @Column(name = "password_hash", nullable = false)
    private String password;  // 对应数据库列名password_hash，不能为空
}
```

**@Column注解的作用：**
- ✅ 指定数据库列名
- ✅ 配置列属性（长度、是否为空、是否唯一）
- ✅ 独立于业务逻辑
- ❌ 不依赖其他组件

### 3.2 数据访问层注解（低耦合）

#### 3.2.1 @Repository 注解

**作用：** 标记一个类为数据访问组件，Spring会自动管理这个Bean。

**特点：**
- 只依赖Entity层
- 负责数据库操作
- 被Service层调用

```java
// UserRepository.java - 用户数据访问层
@Repository  // 标记这是一个数据访问组件，Spring会自动管理
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 根据用户名查找用户
    Optional<User> findByUsername(String username);
    
    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);
```

**Optional类的特点：**
- 用于处理可能为空的值，避免空指针异常
- `Optional<User>`表示可能包含User对象，也可能为空
- 提供安全的方法来访问值（如map、orElse等）
    
    // 检查用户名是否存在
    boolean existsByUsername(String username);
    
    // 检查邮箱是否存在
    boolean existsByEmail(String email);
}


**JpaRepository的特点：**
- 继承自Spring Data JPA，提供基础的CRUD操作
- 自动实现常用的数据库操作方法（save、findById、findAll等）
- 支持方法名查询（如findByUsername会自动生成SELECT语句）
- 泛型参数：`<User, Long>`表示实体类型和主键类型

**@Repository注解的作用：**
- ✅ 告诉Spring这是一个数据访问组件
- ✅ 自动注册为Spring Bean
- ✅ 启用数据访问功能
- ✅ 只依赖Entity层
- ❌ 不包含业务逻辑

#### 3.2.2 @Query 注解

**作用：** 自定义SQL查询语句，用于复杂查询。

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 使用@Query自定义查询
    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE' AND u.createdAt > :date")
    List<User> findActiveUsersAfterDate(@Param("date") LocalDateTime date);
    
    // 使用原生SQL查询
    @Query(value = "SELECT * FROM users WHERE username LIKE %:keyword%", nativeQuery = true)
    List<User> findUsersByKeyword(@Param("keyword") String keyword);
}
```

**@Query注解的作用：**
- ✅ 自定义复杂查询
- ✅ 支持JPQL和原生SQL
- ✅ 参数绑定
- ✅ 只依赖Repository层
- ❌ 不包含业务逻辑

### 3.3 业务层注解（中等耦合）

#### 3.3.1 @Service 注解

**作用：** 标记一个类为业务服务组件，Spring会自动管理这个Bean。

**特点：**
- 依赖Repository层
- 包含业务逻辑
- 被Controller层调用

```java
// UserService.java - 用户业务服务
@Service  // 标记这是一个业务服务组件，Spring会自动管理
public class UserService {
    
    // 这里会注入UserRepository，但暂时不讲解@Autowired
    private UserRepository userRepository;
    
    // 创建用户
    public User createUser(User user) {
        // 业务逻辑：检查用户名是否已存在
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 业务逻辑：检查邮箱是否已存在
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }
        
        // 保存用户到数据库
        return userRepository.save(user);
    }
    
    // 根据用户名查找用户
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // 检查用户名是否存在
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
```

**@Service注解的作用：**
- ✅ 告诉Spring这是一个业务服务组件
- ✅ 自动注册为Spring Bean
- ✅ 包含业务逻辑
- ✅ 依赖Repository层
- ❌ 不直接处理HTTP请求

#### 3.3.2 @Transactional 注解

**作用：** 启用事务管理，确保数据操作的原子性、一致性、隔离性和持久性。

```java
@Service
public class UserService {
    
    private UserRepository userRepository;
    
    @Transactional  // 启用事务管理，如果方法抛出异常，所有数据库操作都会回滚
    public User createUser(User user) {
        // 这些操作在同一个事务中
        User savedUser = userRepository.save(user);
        
        // 如果这里抛出异常，上面的save操作也会回滚
        if (savedUser.getUsername().equals("admin")) {
            throw new RuntimeException("不能创建admin用户");
        }
        
        return savedUser;
    }
    
    @Transactional(readOnly = true)  // 只读事务，提高性能
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
```

**@Transactional注解的作用：**
- ✅ 启用事务管理
- ✅ 自动回滚异常操作
- ✅ 支持只读事务
- ✅ 依赖Service层
- ❌ 不包含业务逻辑

### 3.4 表现层注解（高耦合）

#### 3.4.1 @RestController 注解

**作用：** 标记一个类为REST控制器，自动处理JSON序列化和反序列化。

**特点：**
- 依赖Service层
- 处理HTTP请求
- 返回JSON响应

```java
// UserController.java - 用户控制器
@RestController  // 标记这是一个REST控制器，自动处理JSON
public class UserController {
    
    // 这里会注入UserService，但暂时不讲解@Autowired
    private UserService userService;
    
    // 创建用户接口
    public ResponseEntity<User> createUser(User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // 获取用户列表接口
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
```

**ResponseEntity的特点：**
- 封装HTTP响应，包含状态码、响应头和响应体
- `ResponseEntity<User>`表示返回User对象的HTTP响应
- 提供静态方法创建不同状态的响应（ok、badRequest、created等）
}


**@RestController注解的作用：**
- ✅ 标记为REST控制器
- ✅ 自动处理JSON序列化
- ✅ 处理HTTP请求
- ✅ 依赖Service层
- ❌ 不包含业务逻辑

#### 3.4.2 @RequestMapping 注解

**作用：** 指定控制器的基础URL路径。

```java
@RestController
@RequestMapping("/api/users")  // 设置基础URL路径：/api/users
public class UserController {
    
    private UserService userService;
    
    // 完整URL：/api/users/create
    public ResponseEntity<User> createUser(User user) {
        // 处理逻辑
    }
    
    // 完整URL：/api/users/list
    public ResponseEntity<List<User>> getAllUsers() {
        // 处理逻辑
    }
}
```

**@RequestMapping注解的作用：**
- ✅ 设置基础URL路径
- ✅ 统一管理URL前缀
- ✅ 依赖Controller层
- ❌ 不包含业务逻辑

#### 3.4.3 @PostMapping 和 @GetMapping 注解

**作用：** 指定HTTP请求方法和具体路径。

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private UserService userService;
    
    @PostMapping("/create")  // 处理POST请求：/api/users/create
    public ResponseEntity<User> createUser(User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @GetMapping("/list")  // 处理GET请求：/api/users/list
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")  // 处理GET请求：/api/users/{id}
    public ResponseEntity<User> getUserById(Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
}
```

**@PostMapping和@GetMapping注解的作用：**
- ✅ 指定HTTP请求方法
- ✅ 设置具体URL路径
- ✅ 支持路径参数
- ✅ 依赖Controller层
- ❌ 不包含业务逻辑

### 3.5 依赖注入注解（最高耦合）

#### 3.5.1 @Autowired 注解

**作用：** 自动注入Spring容器中的Bean，实现依赖注入。

**特点：**
- 依赖Spring IoC容器
- 自动查找匹配的Bean
- 支持多种注入方式

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired  // 自动注入UserService的实例
    private UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<User> createUser(User user) {
        // 现在可以使用userService了
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
```

**@Autowired注解的作用：**
- ✅ 自动注入依赖
- ✅ 支持字段注入
- ✅ 支持构造器注入
- ✅ 支持setter注入
- ✅ 依赖Spring IoC容器
- ❌ 不包含业务逻辑

**三种注入方式对比：**

```java
// 1. 字段注入（不推荐）
@Autowired
private UserService userService;

// 2. 构造器注入（推荐）
private final UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}

// 3. setter注入（可选依赖）
private UserService userService;

@Autowired
public void setUserService(UserService userService) {
    this.userService = userService;
}
```

---

## 4. 完整业务流程示例

### 4.1 用户注册流程

```
1. 前端发送注册请求
   POST /api/auth/register
   {
     "username": "zhangsan",
     "email": "zhangsan@example.com",
     "password": "123456",
     "confirmPassword": "123456"
   }

2. Controller层处理
   - 接收请求数据
   - 参数验证
   - 调用AuthService.register()

3. Service层处理
   - 业务验证（用户名、邮箱是否已存在）
   - 密码加密
   - 创建用户实体
   - 调用UserService.createUser()

4. Repository层处理
   - 保存用户到数据库
   - 返回保存后的用户实体

5. 返回响应
   {
     "success": true,
     "message": "注册成功",
     "data": {
       "id": 1,
       "username": "zhangsan",
       "email": "zhangsan@example.com",
       "status": "ACTIVE",
       "roles": ["USER"],
       "createdAt": "2024-01-15T10:30:00"
     }
   }
```

### 4.2 用户登录流程

```
1. 前端发送登录请求
   POST /api/auth/login
   {
     "username": "zhangsan",
     "password": "123456"
   }

2. Controller层处理
   - 接收请求数据
   - 参数验证
   - 调用AuthService.login()

3. Service层处理
   - 根据用户名查找用户
   - 验证密码
   - 检查用户状态
   - 生成JWT令牌
   - 记录登录日志

4. Repository层处理
   - 查询用户信息
   - 更新最后登录时间

5. 返回响应
   {
     "success": true,
     "message": "登录成功",
     "data": {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "user": {
         "id": 1,
         "username": "zhangsan",
         "email": "zhangsan@example.com",
         "status": "ACTIVE",
         "roles": ["USER"],
         "lastLoginAt": "2024-01-15T10:30:00"
       }
     }
   }
```

---

## 5. 分层架构的优势

### 5.1 职责分离
- **Controller**: 只负责HTTP请求处理
- **Service**: 只负责业务逻辑
- **Repository**: 只负责数据访问
- **Entity**: 只负责数据模型

### 5.2 可维护性
- 各层独立开发
- 易于测试
- 易于扩展

### 5.3 可重用性
- Service可以被多个Controller调用
- Repository可以被多个Service调用
- Entity可以被多个Repository使用

### 5.4 安全性
- 通过DTO保护敏感数据
- 通过Service层进行权限控制
- 通过Repository层进行数据验证

