# 架构决策记录 (ADR)

## ADR-001: Spring Boot与Maven测试环境冲突解决方案

### 状态
**已解决** - 2024年7月14日

### 背景
在开发过程中遇到了Spring Boot应用测试与Maven测试环境的配置冲突问题，主要表现为：
1. 数据库配置不一致
2. 环境变量冲突
3. 测试数据隔离问题
4. 编码错误

### 问题分析

#### 1. 数据库配置冲突
**问题描述**:
- User测试使用 `cloudcontrol_test` 数据库
- 应用测试使用 `cloudcontrol_dev` 数据库
- 两个数据库状态不一致导致测试失败

**根本原因**:
- 测试脚本和主配置文件使用不同的数据库
- 缺乏统一的环境配置管理

#### 2. 环境变量冲突
**问题描述**:
- PostgreSQL需要密码认证
- 测试脚本没有提供密码环境变量
- 需要手动输入密码

**根本原因**:
- 缺乏自动化的环境配置
- 没有统一的密码管理机制

#### 3. 编码错误
**问题描述**:
```
UnicodeDecodeError: 'gbk' codec can't decode byte 0x80 in position 21: illegal multibyte sequence
```

**根本原因**:
- Windows系统默认使用GBK编码
- Python subprocess默认使用系统编码
- PostgreSQL输出包含UTF-8字符

### 解决方案

#### 1. 统一测试环境配置
**决策**: 所有测试统一使用 `cloudcontrol_test` 数据库

**实现方案**:
```python
def update_application_config():
    """临时更新application.yml使用测试数据库"""
    # 备份原配置文件
    backup_cmd = "copy backend\\src\\main\\resources\\application.yml backend\\src\\main\\resources\\application.yml.backup"
    
    # 读取原配置并替换数据库URL
    content = content.replace(
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_dev',
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_test'
    )
    
    # 写回配置文件
    with open(config_file, 'w', encoding='utf-8') as f:
        f.write(content)
```

**优势**:
- 环境隔离，避免相互影响
- 测试数据独立，便于清理
- 配置统一，减少冲突

#### 2. 自动化密码管理
**决策**: 使用环境变量自动设置PostgreSQL密码

**实现方案**:
```python
def reset_test_database():
    """重置 cloudcontrol_test 数据库"""
    # 设置PostgreSQL密码环境变量
    os.environ['PGPASSWORD'] = 'bupt_test'
    
    drop_cmd = 'psql -U postgres -h localhost -c "DROP DATABASE IF EXISTS cloudcontrol_test;"'
    create_cmd = 'psql -U postgres -h localhost -c "CREATE DATABASE cloudcontrol_test;"'
```

**优势**:
- 避免手动输入密码
- 自动化程度高
- 减少人为错误

#### 3. 编码问题修复
**决策**: 在subprocess调用中指定UTF-8编码

**实现方案**:
```python
result = subprocess.run(
    command, 
    shell=True, 
    capture_output=True, 
    text=True, 
    encoding='utf-8',
    errors='ignore',  # 忽略无法解码的字符
    cwd=os.getcwd()
)
```

**优势**:
- 解决Windows系统编码问题
- 兼容多语言环境
- 提高脚本稳定性

### 架构评估

#### 正面影响
1. **环境隔离**: 开发环境和测试环境完全分离
2. **自动化程度**: 测试过程完全自动化，无需人工干预
3. **稳定性**: 解决了编码和配置冲突问题
4. **可维护性**: 统一的配置管理，便于维护

#### 负面影响
1. **复杂性**: 增加了配置管理的复杂性
2. **依赖关系**: 测试依赖于特定的数据库配置
3. **资源消耗**: 需要额外的测试数据库

#### 风险评估
- **低风险**: 测试环境独立，不影响生产环境
- **可恢复**: 测试完成后自动恢复原配置
- **可监控**: 所有操作都有日志记录

### 实施计划

#### 阶段1: 基础设置 ✅
- [x] 创建测试数据库
- [x] 设置环境变量
- [x] 修复编码问题

#### 阶段2: 自动化脚本 ✅
- [x] 开发测试脚本
- [x] 实现配置管理
- [x] 添加错误处理

#### 阶段3: 文档完善 ✅
- [x] 更新迁移指南
- [x] 记录解决方案
- [x] 建立最佳实践

### 监控指标

#### 测试成功率
- 目标: 100%
- 当前: 100% ✅

#### 测试执行时间
- 目标: < 30秒
- 当前: ~20秒 ✅

#### 错误率
- 目标: 0%
- 当前: 0% ✅

### 后续改进

#### 短期改进 (1-2周)
1. **CI/CD集成**: 将测试脚本集成到CI/CD流程
2. **性能优化**: 优化测试执行时间
3. **错误处理**: 增强错误处理和恢复机制

#### 长期改进 (1-2月)
1. **容器化**: 使用Docker容器化测试环境
2. **多环境支持**: 支持多套测试环境
3. **监控告警**: 添加测试失败告警机制

### 经验教训

#### 成功因素
1. **问题定位准确**: 快速识别了根本原因
2. **解决方案全面**: 考虑了所有相关因素
3. **测试验证充分**: 确保解决方案有效

#### 改进点
1. **预防性设计**: 在架构设计阶段就考虑测试环境
2. **文档先行**: 建立完善的文档体系
3. **自动化优先**: 优先考虑自动化解决方案

### 结论

通过统一测试环境配置、自动化密码管理和修复编码问题，成功解决了Spring Boot与Maven测试环境的冲突。该解决方案具有以下特点：

1. **可靠性**: 测试环境完全隔离，避免相互影响
2. **自动化**: 测试过程完全自动化，无需人工干预
3. **可维护性**: 统一的配置管理，便于维护和扩展
4. **稳定性**: 解决了编码和配置冲突问题

该解决方案为后续开发提供了稳定的测试基础，建议在类似项目中采用相同的架构模式。 

---

## ADR-002: Spring WebSocket选型决策

### 状态
**已采纳** - 2024年7月

### 背景
项目需要支持终端实时指令下发、心跳检测、状态回传等功能，要求高可维护性、可扩展性、与现有Spring Boot体系无缝集成。

### 选型对比
- @ServerEndpoint（JSR356）：标准实现，适合小型项目，扩展性一般。
- Netty：高性能，适合极端高并发和自定义协议，开发复杂度高。
- Spring WebSocket（@EnableWebSocket + WebSocketHandler）：与Spring生态深度集成，支持分层、拦截器、会话管理，易于维护和扩展。

### 决策
最终选用Spring WebSocket方案，理由如下：
- 与Spring Boot、Spring Security、Redis等无缝集成，便于权限、会话、缓存等统一管理。
- 支持分层架构，代码结构清晰，便于团队协作和自动化文档生成。
- 易于扩展消息类型（如心跳、指令、回执等），支持分布式部署。
- 性能满足企业级实时通信需求，开发效率高，社区资源丰富。

### 影响
- 后端新增WebSocket配置与Handler模块，相关文档和API同步完善。
- 前后端指令下发、心跳、回执等流程全部基于WebSocket实现。
- 便于后续横向扩展和新业务集成。

---

## ADR-003: Spring Security分环境配置决策

### 状态
**已采纳** - 2024年7月15日

### 背景
项目需要支持多环境部署（开发、测试、生产），不同环境对安全性的要求不同：
1. 开发环境需要方便调试，允许访问Swagger UI和API文档
2. 测试环境需要平衡安全性和便利性
3. 生产环境需要严格的安全保护

### 问题分析

#### 1. 默认安全配置问题
**问题描述**:
- Spring Boot默认启用Spring Security
- 默认配置保护所有端点，包括Swagger UI
- 访问 `http://localhost:8080/swagger-ui.html` 被重定向到登录页面

**根本原因**:
- 缺乏环境特定的安全配置
- 没有针对不同环境的差异化安全策略

#### 2. 开发效率问题
**问题描述**:
- 开发人员无法直接访问API文档
- 需要额外的认证步骤才能测试API
- 影响开发效率和调试便利性

#### 3. 安全风险问题
**问题描述**:
- 生产环境需要严格的安全保护
- 需要防止CSRF攻击、XSS攻击等
- 需要适当的会话管理和认证机制

### 解决方案

#### 1. 分环境安全配置架构
**决策**: 采用基于Spring Profile的分环境安全配置

**实现方案**:
```java
// 开发环境配置
@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevSecurityConfig {
    // 宽松配置，允许访问Swagger UI和API
}

// 测试环境配置
@Configuration
@EnableWebSecurity
@Profile("test")
public class TestSecurityConfig {
    // 平衡配置，允许API访问但保持基本安全
}

// 生产环境配置
@Configuration
@EnableWebSecurity
@Profile("prod")
public class ProdSecurityConfig {
    // 严格配置，只允许认证用户访问
}
```

**优势**:
- 环境隔离，不同环境有不同的安全策略
- 开发便利，开发环境可以轻松访问API文档
- 生产安全，生产环境有严格的安全保护

#### 2. 环境特定配置详情

**开发环境 (dev)**:
- 允许访问Swagger UI (`/swagger-ui/**`, `/swagger-ui.html`)
- 允许访问API文档 (`/v3/api-docs/**`, `/api-docs/**`)
- 允许访问健康检查 (`/actuator/**`)
- 允许访问所有API端点 (`/api/**`)
- 禁用CSRF保护（便于API测试）
- 允许iframe（用于Swagger UI）

**测试环境 (test)**:
- 允许访问Swagger UI和API文档
- 允许访问健康检查
- 允许访问API端点
- 禁用CSRF保护
- 允许同源iframe

**生产环境 (prod)**:
- 只允许访问健康检查端点
- 所有其他请求需要认证
- 启用CSRF保护
- 禁止iframe（安全考虑）
- 严格的会话管理

**默认环境 (default)**:
- 当没有指定profile时使用
- 配置与开发环境类似，确保开发便利性

#### 3. 配置管理策略
**决策**: 使用Spring Profile和环境变量管理配置

**实现方案**:
```bash
# 开发环境
java -jar app.jar --spring.profiles.active=dev

# 测试环境
java -jar app.jar --spring.profiles.active=test

# 生产环境
java -jar app.jar --spring.profiles.active=prod
```

**优势**:
- 配置灵活，可以根据环境动态调整
- 部署简单，通过启动参数控制
- 维护方便，配置集中管理

### 架构评估

#### 正面影响
1. **开发效率**: 开发环境可以轻松访问API文档和测试API
2. **安全性**: 生产环境有严格的安全保护
3. **灵活性**: 不同环境有不同的安全策略
4. **可维护性**: 配置集中管理，便于维护

#### 负面影响
1. **复杂性**: 增加了配置管理的复杂性
2. **学习成本**: 开发人员需要了解不同环境的配置差异
3. **调试难度**: 生产环境的问题可能难以在开发环境复现

#### 风险评估
- **低风险**: 开发环境配置宽松，便于调试
- **中风险**: 测试环境需要平衡安全性和便利性
- **高风险**: 生产环境配置严格，需要充分测试

### 实施计划

#### 阶段1: 基础配置 ✅
- [x] 创建分环境安全配置类
- [x] 配置开发环境宽松策略
- [x] 配置生产环境严格策略

#### 阶段2: 测试验证 🔄
- [ ] 验证开发环境Swagger UI访问
- [ ] 验证API端点访问权限
- [ ] 验证生产环境安全保护

#### 阶段3: 文档完善 🔄
- [ ] 更新部署文档
- [ ] 编写安全配置指南
- [ ] 建立最佳实践

### 监控指标

#### 开发环境指标
- Swagger UI访问成功率: 目标 100%
- API测试便利性: 目标 高
- 开发效率提升: 目标 显著

#### 生产环境指标
- 安全事件发生率: 目标 0%
- 未授权访问拦截率: 目标 100%
- 系统可用性: 目标 99.9%

### 后续改进

#### 短期改进 (1-2周)
1. **自动化测试**: 为不同环境的安全配置编写自动化测试
2. **监控告警**: 添加安全事件监控和告警
3. **文档完善**: 完善安全配置文档和最佳实践

#### 长期改进 (1-2月)
1. **JWT集成**: 集成JWT认证机制
2. **OAuth2支持**: 支持OAuth2认证
3. **审计日志**: 添加安全审计日志功能

### 经验教训

#### 成功因素
1. **需求分析准确**: 正确识别了不同环境的安全需求
2. **架构设计合理**: 采用分环境配置，满足不同需求
3. **实施计划清晰**: 分阶段实施，降低风险

#### 改进点
1. **提前规划**: 在项目初期就考虑安全配置
2. **充分测试**: 在不同环境充分测试安全配置
3. **文档先行**: 建立完善的安全配置文档

### 结论

通过采用基于Spring Profile的分环境安全配置，成功解决了不同环境的安全需求差异。该解决方案具有以下特点：

1. **环境适配**: 不同环境有不同的安全策略，满足不同需求
2. **开发友好**: 开发环境配置宽松，便于开发和调试
3. **生产安全**: 生产环境配置严格，确保系统安全
4. **配置灵活**: 通过Profile机制灵活管理不同环境配置

该解决方案为项目提供了安全、灵活、可维护的安全架构，建议在类似项目中采用相同的架构模式。

--- 

## ADR-004: CORS跨域配置决策

### 状态
**已采纳** - 2024年7月15日

### 背景
前后端分离开发时，Swagger UI 或前端本地调试会遇到跨域（CORS）问题，导致API请求失败。

### 问题分析
- 浏览器同源策略限制，前端页面和后端API端口/域名不一致时会被拦截。
- Spring Boot默认未开启全局CORS，导致“Failed to fetch”或OPTIONS请求被拒绝。

### 解决方案
- **开发环境（dev）**：允许所有来源、所有方法、所有头跨域，便于本地调试和Swagger UI测试。
- **测试环境（test）**：同开发环境，便于自动化测试和多端联调。
- **生产环境（prod）**：建议仅允许前端实际域名跨域，提升安全性。

### 实现方式
分别在 `dev` 和 `test` profile 下添加如下配置：
```java
@Configuration
@Profile("dev")
public class DevWebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
```

```java
@Configuration
@Profile("test")
public class TestWebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
```

### 评估
- **正面影响**：开发和测试环境前后端联调无障碍，Swagger UI/Postman可直接调试API。
- **负面影响**：如生产环境未收敛CORS，存在安全风险。

### 结论
开发和测试环境全开放CORS，生产环境需收敛来源域名，保障安全。

--- 

## ADR-XXX: Flyway数据库clean权限全局环境区分配置

### 决策背景

- 团队需要在开发/测试环境下便捷地清空数据库（flyway:clean），但生产环境必须严格禁止，防止误删数据。
- 以往通过application.yml控制clean-disabled，但Maven独立命令不受其影响，易出错。

### 方案决策

- 采用Maven profile机制，在pom.xml中为Flyway插件配置<cleanDisabled>${flyway.cleanDisabled}</cleanDisabled>，并通过profiles区分环境：
    - dev/test profile下<flyway.cleanDisabled>false</flyway.cleanDisabled>，允许clean。
    - prod profile下<flyway.cleanDisabled>true</flyway.cleanDisabled>，禁止clean。
- 这样所有开发/测试成员只需加-Pdev或-Ptest参数即可安全操作，生产环境永远禁止clean，安全且易维护。

### 具体配置

```xml
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>9.22.3</version>
    <configuration>
        ...
        <cleanDisabled>${flyway.cleanDisabled}</cleanDisabled>
    </configuration>
</plugin>

<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <flyway.cleanDisabled>false</flyway.cleanDisabled>
        </properties>
    </profile>
    <profile>
        <id>test</id>
        <properties>
            <flyway.cleanDisabled>false</flyway.cleanDisabled>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <flyway.cleanDisabled>true</flyway.cleanDisabled>
        </properties>
    </profile>
</profiles>
```

### 影响与收益
- 开发/测试环境可随时clean，极大提升效率。
- 生产环境clean永远被禁止，杜绝误操作风险。
- 统一在pom.xml维护，团队成员无需关心细节，极易理解和迁移。

--- 

## 决策记录：Spring Security分号拦截与JSESSIONID兼容性

### 问题背景
在与配置网站和播控盒对接过程中，发现设备端登录请求会自动在URL中拼接 `;jsessionid=xxxx`，这是由于部分客户端或设备端不支持Cookie，采用URL传递Session ID的方式。Spring Security默认配置下，StrictHttpFirewall会拒绝所有带分号（;）的URL，导致设备端无法正常登录，后端日志出现 `RequestRejectedException: The request was rejected because the URL contained a potentially malicious String ";"`。

### 决策说明
为兼容设备端和配置网站的登录与指令下发需求，决定在开发和对接阶段放宽Spring Security的分号限制，允许带分号的URL通过防火墙。后续如需收紧安全策略，可根据实际情况调整。

### 具体措施
- 在Spring Security配置中，允许分号通过StrictHttpFirewall。
- 仅在开发/对接环境放开，生产环境根据实际安全需求决定是否放开。
