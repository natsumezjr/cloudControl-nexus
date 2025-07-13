# 登录接口分析文档

## 响应实体类设计

### 实体类设计思路

**设计思路：**

1. **维护一个Spring管理的单例ApiResponse实体类**
   - 使用`@Component`注解让Spring管理
   - 定义数据类型为泛型`<T>`，支持任意类型
   - 提供静态工厂方法，便于调用

2. **泛型T的作用**
   - **类型安全** - 编译时检查数据类型匹配
   - **灵活性** - 可以处理String、User、List等任意类型
   - **代码复用** - 一个实体类处理所有响应类型

3. **使用方式**
   ```java
   // 示例片段
   // 返回字符串数据
   ApiResponse<String> response1 = ApiResponse.success("hello");
   
   // 返回用户对象
   ApiResponse<User> response2 = ApiResponse.success(user);
   
   // 返回列表数据
   ApiResponse<List<String>> response3 = ApiResponse.success(userList);
   ```

4. **构造器注入的优势**
   - **不可变性** - 依赖在构造时确定，运行时不可修改
   - **必须性** - 确保依赖必须存在，避免空指针异常
   - **线程安全** - 构造后依赖不可变，线程安全
   - **测试友好** - 便于单元测试时注入Mock对象

5. **简化设计原则**
   - **避免冗余** - 不需要额外的Builder类
   - **静态方法** - 使用静态工厂方法，调用更简洁
   - **职责单一** - ApiResponse只负责响应数据，不承担构建职责

### 推荐的ResponseEntity实体类方案

**建议创建统一的响应实体类：**

```java
// entity/common/ApiResponse.java
// 统一响应实体类
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    
    // 构造函数
    public ApiResponse(Integer code, String message, T data, Long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    // 成功响应静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data, System.currentTimeMillis());
    }
    
    // 错误响应静态方法
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null, System.currentTimeMillis());
    }
    
    // 预定义的错误响应
    public static <T> ApiResponse<T> badRequest(String message) {
        return error(400, message);
    }
    
    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(401, message);
    }
    
    public static <T> ApiResponse<T> forbidden(String message) {
        return error(403, message);
    }
    
    public static <T> ApiResponse<T> serverError(String message) {
        return error(500, message);
    }
    
    // getter方法省略...
}
```

**实体类方案的优势：**
1. **类型安全** - 编译时检查，避免字段名错误
2. **代码提示** - IDE可以提供完整的代码提示
3. **统一格式** - 所有接口返回格式一致
4. **易于维护** - 修改响应格式只需修改实体类
5. **文档友好** - 可以生成标准的API文档

## API错误码说明

### 通用错误码

| 错误码 | 说明 | 排查建议 |
|--------|------|----------|
| 400 Bad request | 1. 参数传入错误 | 检查参数是否在允许的范围内 |
| 401 Unauthorized | 1.账号密码没有传 2.账号密码错误 3.代码问题 | 检查是否有传账号密码以及核实是否正确 |
| 403 Forbidden | 1. 对用户，节目，素材等资源的访问或操作没有权限 | 检查账号密码和传入的用户ID，节目ID，素材ID等是否正确 |
| 500 Server Error | 1. 服务器内部错误 | 联系云服务器技术人员 |

### SpringBoot错误码返回示例
```java
// controller/auth/AuthController.java
// 后端根据错误情况返回相应错误码
@PostMapping("/wp-login.php")
public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
    
    // 400 - 参数传入错误
    if (request.getUsername() == null || request.getPassword() == null) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.badRequest("参数传入错误"));
    }
    
    // 401 - 账号密码错误
    if (!isValidCredentials(request.getUsername(), request.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.unauthorized("账号密码错误"));
    }
    
    // 403 - 权限不足
    if (!hasLoginPermission(request.getUsername())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.forbidden("权限不足"));
    }
    
    // 成功响应
    try {
        String jsessionid = generateJSessionId(request.getUsername());
        Map<String, String> data = Map.of("jsessionid", jsessionid);
        return ResponseEntity.ok(ApiResponse.success(data));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.serverError("服务器内部错误"));
    }
}
```

## 认证方式分析

### Basic认证 vs Token认证

**Basic认证方式：**
- 原理：将用户名和密码用冒号连接，然后进行Base64编码
- 示例：用户名`Aladdin`，密码`open sesame` → `Aladdin:open sesame` → Base64编码 → `QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
- HTTP头：`Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
- 缺点：每次请求都需要传输明文密码的编码形式

**Token认证方式（推荐）：**
- 原理：首次登录获取会话令牌，后续请求使用令牌
- 优点：更安全，避免重复传输密码
- 管理方案：建议使用Redis或内存缓存存储token，设置过期时间

## 登录接口

**接口地址：** `POST https://{{domainName}}/wp-login.php`

### 1. 基本接口说明

**授权角色：** 所有用户

**描述：** 用户登录接口，验证用户名密码，返回JSESSIONID用于后续认证

### 2. 项目结构位置

```
backend/
├── src/main/java/
│   └── com/cloudcontrol/
│       ├── entity/
│       │   ├── user/
│       │   │   ├── User.java                    // 用户实体类
│       │   │   ├── UserRole.java                // 用户角色枚举
│       │   │   └── UserStatus.java              // 用户状态枚举
│       │   └── common/
│       │       └── ApiResponse.java             // 统一响应实体类
│       ├── service/
│       │   ├── auth/
│       │   │   ├── AuthService.java             // 认证服务
│       │   │   └── TokenService.java            // Token管理服务
│       │   └── user/
│       │       └── UserService.java             // 用户服务
│       ├── controller/
│       │   └── auth/
│       │       └── AuthController.java          // 认证控制器
│       ├── repository/
│       │   └── user/
│       │       └── UserRepository.java          // 用户数据访问层
│       ├── config/
│       │   └── RedisConfig.java                 // Redis配置
│       └── interceptor/
│           └── TokenInterceptor.java            // Token拦截器
```

**项目结构说明：**
- **entity/user包**：存放用户相关的实体类和枚举
- **entity/common包**：存放公共实体类
- **service/auth包**：存放认证相关的业务逻辑
- **service/user包**：存放用户相关的业务逻辑
- **controller/auth包**：存放认证相关的HTTP控制器
- **repository/user包**：存放用户相关的数据访问层
- **config包**：存放Redis等配置类
- **interceptor包**：存放拦截器，用于token验证

### 3. 实体类和服务构造思路

#### 实体类设计思路

**User实体类：**
- 包含id、username、password、email等字段
- 密码字段使用@JsonIgnore避免序列化
- 使用@Valid注解进行参数校验

#### 服务层设计思路

**AuthService服务类：**
- 处理用户认证逻辑
- 验证用户名密码
- 生成JSESSIONID
- 调用TokenService存储token

**TokenService服务类：**
- 管理Redis中的token存储
- 提供token的增删改查操作
- 处理token过期逻辑

### 4. 推荐代码实现方案

#### 代码实现方案

**1. 用户实体类：**
```java
// entity/user/User.java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @JsonIgnore // 避免密码被序列化
    public String getPassword() {
        return password;
    }
    
    // getter、setter方法省略...
}
```

**2. 用户角色枚举：**
```java
// entity/user/UserRole.java
public enum UserRole {
    ADMINISTRATOR("管理员"),    // 最高权限，可执行所有操作
    MANAGER("经理"),           // 高级管理权限
    EDITOR("编辑"),           // 编辑权限
    OPERATOR("操作员"),       // 操作权限
    MONITOR("监控员");        // 只读权限
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
```

**3. 用户状态枚举：**
```java
// entity/user/UserStatus.java
public enum UserStatus {
    ACTIVE("激活"),      // 正常状态
    INACTIVE("禁用"),    // 禁用状态
    LOCKED("锁定");      // 锁定状态
    
    private final String description;
    
    UserStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
```

**4. 统一响应实体类：**
```java
// entity/common/ApiResponse.java
// 统一响应实体类
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    
    // 构造函数
    public ApiResponse(Integer code, String message, T data, Long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    // 成功响应静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data, System.currentTimeMillis());
    }
    
    // 错误响应静态方法
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null, System.currentTimeMillis());
    }
    
    // 预定义的错误响应
    public static <T> ApiResponse<T> badRequest(String message) {
        return error(400, message);
    }
    
    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(401, message);
    }
    
    public static <T> ApiResponse<T> forbidden(String message) {
        return error(403, message);
    }
    
    public static <T> ApiResponse<T> serverError(String message) {
        return error(500, message);
    }
    
    // getter方法省略...
}
```

**5. 认证服务：**
```java
// service/auth/AuthService.java
@Service
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, 
                      TokenService tokenService,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public ApiResponse<Object> login(LoginRequest request) {
        // 1. 验证用户名密码
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        
        // 2. 生成JSESSIONID
        String jsessionid = generateJSessionId();
        
        // 3. 存储到Redis（使用用户ID作为主键）
        tokenService.storeToken(user.getId(), jsessionid);
        
        // 4. 返回响应
        Map<String, String> data = Map.of("jsessionid", jsessionid);
        return ApiResponse.success(data);
    }
    
    private String generateJSessionId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
```

**6. Token管理服务：**
```java
// service/auth/TokenService.java
@Service
public class TokenService {
    
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TOKEN_PREFIX = "session:";
    private static final long TOKEN_EXPIRE_TIME = 30 * 60; // 30分钟
    
    public TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    // 存储token - 使用用户ID作为主键
    public void storeToken(Long userId, String jsessionid) {
        String key = TOKEN_PREFIX + userId;
        redisTemplate.opsForValue().set(key, jsessionid, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }
    
    // 获取token
    public String getToken(Long userId) {
        String key = TOKEN_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }
    
    // 删除token
    public void removeToken(Long userId) {
        String key = TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
```

**7. 用户服务：**
```java
// service/user/UserService.java
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // 获取当前用户
    public User getCurrentUser() {
        // 从SecurityContext获取当前用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException(401, "用户未找到"));
    }
    
    // 检查用户权限
    public boolean hasPermission(User user, String operation) {
        switch (user.getRole()) {
            case ADMINISTRATOR:
                return true; // 管理员拥有所有权限
            case MANAGER:
                return !operation.equals("DELETE_USER"); // 经理不能删除用户
            case EDITOR:
                return Arrays.asList("VIEW", "EDIT", "SEND_COMMAND").contains(operation);
            case OPERATOR:
                return Arrays.asList("VIEW", "SEND_BASIC_COMMAND").contains(operation);
            case MONITOR:
                return operation.equals("VIEW"); // 监控员只能查看
            default:
                return false;
        }
    }
    
    // 获取用户可访问的终端组
    public Set<TerminalGroup> getUserAccessibleGroups(User user) {
        return user.getAccessibleTerminalGroups();
    }
    
    // 检查用户是否可以访问指定终端
    public boolean canAccessTerminal(User user, Long terminalId) {
        // 管理员可以访问所有终端
        if (user.getRole() == UserRole.ADMINISTRATOR) {
            return true;
        }
        
        // 其他用户只能访问其终端组内的终端
        return user.getAccessibleTerminalGroups().stream()
            .anyMatch(group -> group.getTerminals().stream()
                .anyMatch(terminal -> terminal.getId().equals(terminalId)));
    }
}
```

**8. 认证控制器：**
```java
// controller/auth/AuthController.java
@RestController
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/wp-login.php")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginRequest request,
                                                   HttpServletResponse response) {
        
        try {
            ApiResponse<Object> apiResponse = authService.login(request);
            
            // 设置HttpOnly Cookie
            String jsessionid = (String) ((Map<String, String>) apiResponse.getData()).get("jsessionid");
            Cookie cookie = new Cookie("JSESSIONID", jsessionid);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // HTTPS环境下
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60); // 30分钟
            response.addCookie(cookie);
            
            return ResponseEntity.ok(apiResponse);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.serverError("服务器内部错误"));
        }
    }
}
```

**9. 用户数据访问层：**
```java
// repository/user/UserRepository.java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 根据用户名查找用户
    Optional<User> findByUsername(String username);
    
    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);
    
    // 根据角色查找用户
    List<User> findByRole(UserRole role);
    
    // 根据状态查找用户
    List<User> findByStatus(UserStatus status);
    
    // 检查用户名是否存在
    boolean existsByUsername(String username);
    
    // 检查邮箱是否存在
    boolean existsByEmail(String email);
}
```

**10. Redis配置：**
```java
// config/RedisConfig.java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
```

**11. Token拦截器：**
```java
// interceptor/TokenInterceptor.java
@Component
public class TokenInterceptor implements HandlerInterceptor {
    
    private final TokenService tokenService;
    
    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler) throws Exception {
        
        // 获取Cookie中的JSESSIONID
        Cookie[] cookies = request.getCookies();
        String jsessionid = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionid = cookie.getValue();
                    break;
                }
            }
        }
        
        if (jsessionid == null) {
            // 没有token，重定向到登录页
            response.sendRedirect("/login");
            return false;
        }
        
        // 验证token有效性
        String username = getCurrentUsername(); // 从当前会话获取用户名
        String storedToken = tokenService.getToken(username);
        
        if (storedToken == null || !storedToken.equals(jsessionid)) {
            // token无效，清除Cookie并重定向到登录页
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}
```

### 请求数据结构
```json
{
  "username": "用户名",  // 根据原文"用户在向云平台API发送请求时，需要依照HTTP基础认证的规范"
  "password": "密码"     // 原文提到"在HTTP请求头中带上API账号和口令信息"
}
```

> **安全建议：** 当前接口使用明文密码传输，建议后续版本改为使用密码hash值（如SHA256）进行传输，以提高安全性。虽然当前接口保持不变，但建议在客户端实现时对密码进行hash处理后再发送。

### 响应数据结构
```json
{
  "status": "success",   // 根据原文"成功登陆后，服务器会返回一个token认证信息"
  "jsessionid": "11E7CF5214352342B36AFA2EC25DFDA3",  // 原文明确说明"以JCESSIONID形成呈现"，注意这里只返回ID值，不包含"JSESSIONID="前缀
  "message": "登录成功"  // 状态描述信息
}
```

> **说明：** 根据原文分析，服务器返回的是JSESSIONID值，客户端需要构造完整的Cookie格式：`Cookie: JSESSIONID=11E7CF5214352342B36AFA2EC25DFDA3`

### Token管理最佳实践

#### 1. 存储方式
使用Redis存储token，设置合理的过期时间（如30分钟）

**Redis简介：**
- Redis是一个开源的内存数据存储系统，支持多种数据结构
- 特点：高性能、支持过期时间、持久化、分布式
- 在Spring Boot中，RedisTemplate是操作Redis的模板类
- 适合存储session、缓存、临时数据等
- **重要：Redis是内存存储，不是数据库，数据存储在程序内存中**

#### 2. 刷新机制
在token即将过期时自动刷新

#### 3. 安全考虑
token不应存储在客户端本地存储中，建议使用HttpOnly Cookie

#### 4. 错误处理
当token失效时，自动重新登录获取新token

### 用户相关定义

#### 用户实体类设计

**User实体类：**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "用户名不能为空")
    @Column(unique = true)
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    // 用户角色 - 使用枚举类型
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    
    // 用户状态
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status = UserStatus.ACTIVE;
    
    // 创建时间
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // 最后登录时间
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    // 关联的终端组权限 - 多对多关系
    @ManyToMany
    @JoinTable(
        name = "user_terminal_groups",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "terminal_group_id")
    )
    private Set<TerminalGroup> accessibleTerminalGroups = new HashSet<>();
    
    // getter、setter方法省略...
}
```

#### 用户角色枚举

**UserRole枚举：**
```java
public enum UserRole {
    ADMINISTRATOR("管理员"),    // 最高权限，可执行所有操作
    MANAGER("经理"),           // 高级管理权限
    EDITOR("编辑"),           // 编辑权限
    OPERATOR("操作员"),       // 操作权限
    MONITOR("监控员");        // 只读权限
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
```

#### 用户状态枚举

**UserStatus枚举：**
```java
public enum UserStatus {
    ACTIVE("激活"),      // 正常状态
    INACTIVE("禁用"),    // 禁用状态
    LOCKED("锁定");      // 锁定状态
    
    private final String description;
    
    UserStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
```

#### 用户服务类

**UserService服务类：**
```java
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // 获取当前用户
    public User getCurrentUser() {
        // 从SecurityContext获取当前用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new BusinessException(401, "用户未找到"));
    }
    
    // 检查用户权限
    public boolean hasPermission(User user, String operation) {
        switch (user.getRole()) {
            case ADMINISTRATOR:
                return true; // 管理员拥有所有权限
            case MANAGER:
                return !operation.equals("DELETE_USER"); // 经理不能删除用户
            case EDITOR:
                return Arrays.asList("VIEW", "EDIT", "SEND_COMMAND").contains(operation);
            case OPERATOR:
                return Arrays.asList("VIEW", "SEND_BASIC_COMMAND").contains(operation);
            case MONITOR:
                return operation.equals("VIEW"); // 监控员只能查看
            default:
                return false;
        }
    }
    
    // 获取用户可访问的终端组
    public Set<TerminalGroup> getUserAccessibleGroups(User user) {
        return user.getAccessibleTerminalGroups();
    }
    
    // 检查用户是否可以访问指定终端
    public boolean canAccessTerminal(User user, Long terminalId) {
        // 管理员可以访问所有终端
        if (user.getRole() == UserRole.ADMINISTRATOR) {
            return true;
        }
        
        // 其他用户只能访问其终端组内的终端
        return user.getAccessibleTerminalGroups().stream()
            .anyMatch(group -> group.getTerminals().stream()
                .anyMatch(terminal -> terminal.getId().equals(terminalId)));
    }
}
```

#### 用户Repository

**UserRepository接口：**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 根据用户名查找用户
    Optional<User> findByUsername(String username);
    
    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);
    
    // 根据角色查找用户
    List<User> findByRole(UserRole role);
    
    // 根据状态查找用户
    List<User> findByStatus(UserStatus status);
    
    // 检查用户名是否存在
    boolean existsByUsername(String username);
    
    // 检查邮箱是否存在
    boolean existsByEmail(String email);
}
```




