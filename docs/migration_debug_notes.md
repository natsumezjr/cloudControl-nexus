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