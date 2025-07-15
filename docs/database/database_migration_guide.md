# CloudControl-Nexus 数据库迁移与自动化测试指南

## 1. 环境与配置
- **数据库类型**：PostgreSQL（严禁使用H2内存数据库）
- **开发库**：cloudcontrol_dev
- **测试库**：cloudcontrol_test
- **配置文件**：backend/src/main/resources/application.yml
- **迁移脚本目录**：backend/src/main/resources/db/migration/

## 2. 迁移与测试流程
1. **环境检查**：确保 Java、Maven、PostgreSQL 均已安装，推荐用自动化脚本一键检测。
2. **清空数据库**：删除 cloudcontrol_test 及所有表，清空 flyway_schema_history。
3. **执行迁移**：只保留本次测试所需 SQL 脚本，按 V1~V6 顺序迁移。
4. **自动化测试**：
   - 禁用 H2，强制使用 PostgreSQL。
   - 测试脚本自动切换 application.yml 到 test 数据库，测试后恢复。
   - 只依赖本次测试相关表，临时剔除其它表的实体、外键、Repository、DTO等依赖。
5. **恢复环境**：测试完成后自动恢复 application.yml。

## 3. 自动化测试脚本模板（推荐结构）
- 环境检查：自动检测 Java、Maven、PostgreSQL
- 数据库重置：自动删除并新建 cloudcontrol_test
- 迁移清理：自动清空 flyway_schema_history 及所有表
- 配置切换：自动切换 application.yml 到 test 数据库
- 编译与测试：自动编译并运行指定的 Repository 测试类
- 恢复配置：测试后自动恢复 application.yml

> 参考脚本：test_template/user_only.py、test_terminal_only.py

## 4. 关键注意事项
- **禁用 H2**：所有 @DataJpaTest 必须加 `@AutoConfigureTestDatabase(replace = Replace.NONE)`。
- **剔除表依赖**：如只测 terminals 表，需将 TerminalGroup、User 等相关实体/Repository/DTO 临时移除或改名为 .txt，避免 JPA 扫描。（后期需要全部测量请无视）
- **遇到实体映射异常**：优先注释掉所有 mappedBy、外键、联合查询等依赖。
- **遇到迁移/连接/编码等报错**：先查本指南，文档没有的解决方案及时补充。
- **application.yml 切换**：测试时务必切换到 cloudcontrol_test，测试后恢复。
- **编码问题**：Windows 下需指定 encoding='utf-8'。

## 5. 常见错误与排查
- H2依赖未剔除：导致测试用内存数据库，数据源不一致。
- 表依赖未剔除：Hibernate/JPA 启动报 mappedBy、外键等异常。
- 迁移脚本顺序/内容不一致：Flyway 校验失败。
- application.yml 未切换：测试用错数据库。
- 环境变量/编码问题：Windows 下需指定 encoding='utf-8'。

---

如遇新问题，优先补充本指南，保持团队知识闭环。 