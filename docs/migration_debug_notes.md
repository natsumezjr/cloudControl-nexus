# 数据库迁移和调试注意事项

## 测试脚本和配置文件说明

### 保留的测试脚本

#### 1. `test_user_only.py` - User功能三合一测试
- **作用**: User实体、仓库、DTO三合一测试脚本
- **配置文件**: 使用主配置文件 `application.yml` 和 `pom.xml`
- **功能流程**:
  1. 环境检查 (Java, Maven, PostgreSQL, Redis)
  2. 编译项目 (`mvn clean compile`)
  3. 运行User测试 (`mvn test -Dspring.profiles.active=dev`)
  4. 生成测试报告
- **使用场景**: User功能测试，作为其他功能的基础
- **运行命令**: `python test_user_only.py`

#### 2. `check_sql_issues.py` - SQL问题检查
- **作用**: 检查SQL文件与实体类字段匹配
- **特点**: 自动发现所有SQL和实体文件，避免硬编码
- **检查内容**:
  - 字段映射一致性
  - 外键约束完整性
  - 数据类型匹配
  - 重复字段检查
- **使用场景**: 数据库迁移前检查，确保SQL与实体一致
- **运行命令**: `python check_sql_issues.py`

#### 3. `check_environment.py` - 环境检查
- **作用**: 检查开发环境是否就绪
- **检查项目**:
  - Java版本 (要求17+)
  - Maven版本 (要求3.6+)
  - PostgreSQL连接
  - Redis连接
- **使用场景**: 运行测试前环境验证
- **运行命令**: `python check_environment.py`

### 配置文件说明

#### 主配置文件: `backend/src/main/resources/application.yml`
- **作用**: 完整的开发环境配置
- **数据库**: PostgreSQL (localhost:5432/cloudcontrol_dev)
- **缓存**: Redis (localhost:6379)
- **迁移**: Flyway (启用)
- **安全**: Spring Security + JWT
- **文档**: Swagger UI
- **监控**: Actuator

#### 构建配置: `backend/pom.xml`
- **Java版本**: 17
- **Spring Boot**: 3.2.0
- **数据库**: PostgreSQL驱动
- **迁移**: Flyway插件
- **测试**: Spring Boot Test + Security Test

## 迁移调试注意事项

### 1. ER依赖关系参考

**重要**: 数据库迁移必须严格按照ER设计文档中的依赖关系执行！

#### 参考文档
- **ER设计文档**: `docs/cloud_platform_er_design.md`
- **依赖关系**: 查看"数据库迁移依赖关系"章节

#### 迁移顺序（必须遵守）
1. **V1__create_users.sql** - 基础表，无依赖
2. **V2__create_terminal_groups.sql** - 自引用，无外部依赖  
3. **V3__create_terminals.sql** - 依赖users和terminal_groups
4. **V4__create_commands.sql** - 依赖users和terminals
5. **V5__create_user_terminal_groups.sql** - 依赖users和terminal_groups
6. **V6__create_command_results.sql** - 依赖commands

#### 依赖图
```
users (V1) → terminal_groups (V2) → terminals (V3) → commands (V4) → command_results (V6)
     ↓              ↓
user_terminal_groups (V5)
```

### 2. 环境准备
```bash
# 1. 检查环境
python check_environment.py

# 2. 确保PostgreSQL和Redis运行
# PostgreSQL: localhost:5432
# Redis: localhost:6379
```

### 3. 数据库迁移
```bash
# 1. 进入backend目录
cd backend

# 2. 检查Flyway状态
mvn flyway:info

# 3. 修复迁移问题（如有）
mvn flyway:repair

# 4. 执行迁移
mvn flyway:migrate
```

### 4. 测试执行
```bash
# 1. 编译项目
mvn clean compile -DskipTests

# 2. 运行User测试（使用主配置）
mvn test -Dtest=UserRepositoryIntegrationTest -Dspring.profiles.active=dev

# 3. 运行所有测试
mvn test -Dspring.profiles.active=dev
```

### 5. 常见问题解决

#### 问题1: Flyway迁移失败
**现象**: `V4__create_commands.sql` 中引用的表不存在
**解决**: 
1. 检查SQL文件顺序是否正确
2. 确保依赖表已创建
3. 使用 `mvn flyway:repair` 修复

#### 问题2: Spring Boot上下文加载失败
**现象**: 应用启动失败，配置加载错误
**解决**:
1. 检查 `application.yml` 配置
2. 确保数据库和Redis连接正常
3. 使用 `-Dspring.profiles.active=dev` 指定环境

#### 问题3: 测试依赖外部服务
**现象**: 测试需要PostgreSQL和Redis
**解决**:
1. 使用主配置文件 `application.yml`
2. 确保外部服务运行
3. 使用 `-Dspring.profiles.active=dev` 激活配置

## 新增：Spring Boot集成测试数据库问题

### 问题现象
- 使用 @DataJpaTest 进行集成测试时，Spring Boot 默认会尝试用内存数据库（如H2、HSQL、Derby）替换真实数据库。
- 如果项目未引入内存数据库依赖，或需要直接用 cloudcontrol_test 真实数据库，会导致如下报错：

```
Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.
```

### 解决方案（最佳实践，参考user模块）
- 在测试类上加注解：

```java
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/cloudcontrol_test",
    "spring.datasource.username=postgres",
    "spring.datasource.password=bupt_test",
    "spring.jpa.hibernate.ddl-auto=validate",
    "spring.flyway.enabled=true"
})
```
- 这样可强制Spring Boot测试用真实数据库（如cloudcontrol_test），避免自动替换为内存数据库，并确保所有关键参数不会被其它配置覆盖。
- 配合脚本自动切换 application.yml，确保测试环境一致。

---

## 终端状态上报模块设计与迁移注意事项（2024-07-14更新）

### 1. 设计思路

- 终端状态上报涉及大量动态、嵌套的字典型数据（如 powerstatus、info、terminal 等），每个字段内部结构复杂且随设备升级可能扩展。
- 传统的 jsonb/text 方案虽然灵活，但不利于后期查询、统计、维护和系统化管理。
- 采用“标准化字段表”方案，将所有上报的嵌套字段拆解为扁平化的 path/value 结构，便于扩展、查询和统计。

### 2. 方案结构

#### 主表
```sql
CREATE TABLE terminal_status_report (
    id BIGSERIAL PRIMARY KEY,
    terminal_id VARCHAR(64),
    report_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW()
);
```

#### 字段表
```sql
CREATE TABLE terminal_status_report_fields (
    id BIGSERIAL PRIMARY KEY,
    report_id BIGINT REFERENCES terminal_status_report(id),
    field_name VARCHAR(100),      -- 一级字段名，如 'powerstatus'
    field_path VARCHAR(200),      -- 完整路径，如 'powerstatus.battery'
    field_value TEXT,             -- 具体值
    field_type VARCHAR(20),       -- 'string', 'number', 'boolean', 'object'
    UNIQUE(report_id, field_path)
);
CREATE INDEX idx_report_fields ON terminal_status_report_fields(report_id, field_name);
CREATE INDEX idx_field_path ON terminal_status_report_fields(field_path, field_value);
```

### 3. 典型数据入库示例

原始上报：
```json
{
  "powerstatus": {"status": "on", "battery": 85, "ac_power": true},
  "info": {"version": "1.2.3", "serial": "SN123456789"}
}
```
入库拆解：
| report_id | field_name  | field_path           | field_value   | field_type |
|-----------|-------------|---------------------|--------------|------------|
| 1         | powerstatus | powerstatus.status  | on           | string     |
| 1         | powerstatus | powerstatus.battery | 85           | number     |
| 1         | powerstatus | powerstatus.ac_power| true         | boolean    |
| 1         | info        | info.version        | 1.2.3        | string     |
| 1         | info        | info.serial         | SN123456789  | string     |

### 4. 查询与维护优势
- 可对任意子字段高效查询、统计、分组
- 新增字段无需改表结构，直接插入新 path
- 便于数据治理、权限控制、数据分析
- 维护简单，结构清晰

### 5. 迁移注意事项
- 删除原有的 jsonb/text 方案相关表、实体、DTO、Repository、测试类
- 新建标准化主表和字段表，严格按上述结构迁移
- 所有上报数据入库前需递归拆解为 path/value 结构
- 迁移脚本需保证外键、索引完整
- 相关文档、API、测试脚本需同步更新

### 6. 今日对话要点总结
- 详细分析了 jsonb/text 方案的局限性
- 结合系统化、可扩展、易维护的需求，提出标准化字段表方案
- 给出完整表结构、数据入库、查询、维护的最佳实践
- 明确所有原有终端上报相关文件全部删除，统一采用新方案

### 6. 测试策略

#### 简化测试（推荐）
- 使用 `test_user_only.py`
- 只测试User相关功能
- 使用主配置文件
- 避免复杂的外部依赖

#### 完整测试
- 需要完整的PostgreSQL和Redis环境
- 运行所有实体和仓库测试
- 使用完整的数据库迁移

### 7. 调试技巧

#### 日志配置
```yaml
# application.yml 中的日志配置
logging:
  level:
    com.cloudcontrol: DEBUG
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
```

#### 测试调试
```bash
# 启用详细日志
mvn test -Dspring.profiles.active=dev -Dlogging.level.com.cloudcontrol=DEBUG

# 跳过测试编译
mvn test -DskipTests=false -Dspring.profiles.active=dev
```

### 8. 文件清理

#### 已删除的文件
- `test_entity_repository_dto.py` - 被 `test_user_only.py` 替代
- `backend/src/test/resources/application-test.yml` - 使用主配置文件

#### 保留的文件
- `test_user_only.py` - User功能测试
- `check_sql_issues.py` - SQL问题检查
- `check_environment.py` - 环境检查
- `backend/src/main/resources/application.yml` - 主配置文件
- `backend/pom.xml` - 构建配置

### 9. 最佳实践

1. **测试前检查环境**: 始终运行 `check_environment.py`
2. **使用主配置文件**: 避免测试配置不一致
3. **简化测试范围**: 优先测试User功能
4. **记录调试过程**: 保存错误信息和解决方案
5. **定期清理**: 删除过时的测试脚本和配置

## 总结

当前测试架构采用简化策略：
- **主配置文件**: `application.yml` + `pom.xml`
- **核心测试**: `test_user_only.py` (User功能)
- **辅助工具**: `check_sql_issues.py` + `check_environment.py`
- **测试策略**: 环境检查 → 编译 → 测试 → 报告

这种架构确保了测试的一致性和可维护性，避免了复杂的配置管理问题。 