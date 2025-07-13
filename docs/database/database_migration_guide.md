# 数据库迁移指南

## 当前数据库配置

### 开发环境配置
- **数据库类型**: PostgreSQL 17.5
- **主机**: localhost:5432
- **开发数据库**: cloudcontrol_dev
- **测试数据库**: cloudcontrol_test
- **用户名**: postgres
- **密码**: bupt_test
- **连接URL**: jdbc:postgresql://localhost:5432/cloudcontrol_dev

### 配置文件位置
- **主配置**: `backend/src/main/resources/application.yml`
- **迁移脚本**: `backend/src/main/resources/db/migration/`

## 关键报错点及解决方案

### 1. Flyway迁移校验失败
**错误现象**: 
```
Flyway migration validation failed
```

**根本原因**: 
- 迁移脚本执行顺序错误
- 外键约束依赖的表不存在
- 数据库状态不一致

**解决方案**:
1. 清空所有迁移状态
2. 重新创建测试数据库
3. 按正确顺序执行迁移脚本

### 2. 数据库密码认证失败
**错误现象**:
```
psql: error: connection to server at "localhost" (::1), port 5432 failed: 致命错误: 数据库认证失败
```

**根本原因**:
- PostgreSQL需要密码认证
- 测试脚本没有提供密码

**解决方案**:
```python
# 设置环境变量避免手动输入密码
os.environ['PGPASSWORD'] = 'bupt_test'
```

### 3. 数据库配置冲突
**错误现象**:
- User测试使用cloudcontrol_test数据库
- 应用测试使用cloudcontrol_dev数据库
- 两个数据库状态不一致

**根本原因**:
- 测试环境配置不统一
- 主配置文件连接的是cloudcontrol_dev

**解决方案**:
1. 统一使用cloudcontrol_test数据库进行测试
2. 临时修改application.yml中的数据库URL
3. 测试完成后恢复原配置

### 4. GBK编码错误
**错误现象**:
```
UnicodeDecodeError: 'gbk' codec can't decode byte 0x80 in position 21: illegal multibyte sequence
```

**根本原因**:
- Windows系统默认使用GBK编码
- PostgreSQL输出包含UTF-8字符
- Python subprocess默认使用系统编码解码

**解决方案**:
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

## 迁移脚本顺序

### 正确的执行顺序
1. `V1__create_users.sql` - 创建用户表
2. `V2__create_terminal_groups.sql` - 创建终端组表
3. `V3__create_terminals.sql` - 创建终端表（依赖终端组）
4. `V4__create_commands.sql` - 创建命令表（依赖终端）
5. `V5__create_user_terminal_groups.sql` - 创建用户终端组关系表
6. `V6__create_command_results.sql` - 创建命令结果表（依赖命令）

### 外键依赖关系
- `terminals` 依赖 `terminal_groups`
- `commands` 依赖 `terminals`
- `user_terminal_groups` 依赖 `users` 和 `terminal_groups`
- `command_results` 依赖 `commands`

## 测试环境设置

### 自动清空数据库脚本
```python
def reset_test_database():
    """重置 cloudcontrol_test 数据库"""
    os.environ['PGPASSWORD'] = 'bupt_test'
    
    drop_cmd = 'psql -U postgres -h localhost -c "DROP DATABASE IF EXISTS cloudcontrol_test;"'
    create_cmd = 'psql -U postgres -h localhost -c "CREATE DATABASE cloudcontrol_test;"'
    
    run_command(drop_cmd, "删除测试数据库")
    run_command(create_cmd, "创建测试数据库")
```

### 清空迁移状态
```python
def clear_flyway_migration():
    """清空Flyway迁移状态"""
    databases = ['cloudcontrol_dev', 'cloudcontrol_test']
    
    for db in databases:
        # 删除迁移历史表
        drop_table_cmd = f'psql -U postgres -h localhost -d {db} -c "DROP TABLE IF EXISTS flyway_schema_history;"'
        run_command(drop_table_cmd, f"删除 {db} 的迁移历史表")
        
        # 删除所有用户表
        drop_tables_cmd = f'''psql -U postgres -h localhost -d {db} -c "
        DROP TABLE IF EXISTS command_results CASCADE;
        DROP TABLE IF EXISTS user_terminal_groups CASCADE;
        DROP TABLE IF EXISTS commands CASCADE;
        DROP TABLE IF EXISTS terminal_groups CASCADE;
        DROP TABLE IF EXISTS terminals CASCADE;
        DROP TABLE IF EXISTS users CASCADE;
        "'''
        run_command(drop_tables_cmd, f"删除 {db} 的所有用户表")
```

## 最佳实践

### 1. 测试前准备
- 清空所有迁移状态
- 重新创建测试数据库
- 统一使用测试数据库配置

### 2. 编码处理
- 所有脚本使用UTF-8编码
- subprocess调用指定encoding='utf-8'
- 使用errors='ignore'处理解码错误

### 3. 环境隔离
- 开发环境和测试环境使用不同数据库
- 测试完成后恢复原配置
- 避免环境配置冲突

### 4. 错误处理
- 记录详细的错误信息和解决方案
- 建立错误处理机制
- 定期清理测试数据

## 常见问题排查

### 1. 迁移失败
1. 检查迁移脚本顺序
2. 验证外键依赖关系
3. 清空迁移状态重新开始

### 2. 连接失败
1. 检查PostgreSQL服务状态
2. 验证用户名密码
3. 确认数据库存在

### 3. 编码错误
1. 检查文件编码格式
2. 修改subprocess参数
3. 使用UTF-8编码

### 4. 测试失败
1. 检查数据库配置
2. 验证表结构
3. 确认数据一致性 