#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Terminal实体、仓库三合一测试脚本
统一使用 cloudcontrol_test 数据库
不依赖任何其它表
"""

import os
import subprocess

def run_command(command, description):
    print(f"\n=== {description} ===")
    print(f"执行命令: {command}")
    try:
        result = subprocess.run(
            command,
            shell=True,
            capture_output=True,
            text=True,
            encoding='utf-8',
            errors='ignore',
            cwd=os.getcwd()
        )
        print(f"返回码: {result.returncode}")
        if result.stdout:
            print("标准输出:")
            print(result.stdout)
        if result.stderr:
            print("错误输出:")
            print(result.stderr)
        return result.returncode == 0
    except Exception as e:
        print(f"命令执行失败: {e}")
        return False

def check_environment():
    print("=== 环境检查 ===")
    if not run_command("java -version", "检查Java版本"):
        print("❌ Java未安装或配置错误")
        return False
    if not run_command("mvn -version", "检查Maven版本"):
        print("❌ Maven未安装或配置错误")
        return False
    os.environ['PGPASSWORD'] = 'bupt_test'
    if not run_command('psql -U postgres -h localhost -c "SELECT version();"', "检查PostgreSQL连接"):
        print("❌ PostgreSQL连接失败")
        return False
    print("✅ 环境检查通过")
    return True

def reset_test_database():
    print("=== 重置 cloudcontrol_test 数据库 ===")
    os.environ['PGPASSWORD'] = 'bupt_test'
    drop_cmd = (
        'psql -U postgres -h localhost -c "DROP DATABASE IF EXISTS cloudcontrol_test;"'
    )
    create_cmd = (
        'psql -U postgres -h localhost -c "CREATE DATABASE cloudcontrol_test;"'
    )
    run_command(drop_cmd, "删除测试数据库")
    run_command(create_cmd, "创建测试数据库")

def clear_flyway_migration():
    print("=== 清空Flyway迁移状态 ===")
    os.environ['PGPASSWORD'] = 'bupt_test'
    for db in ['cloudcontrol_test']:
        drop_table_cmd = f'psql -U postgres -h localhost -d {db} -c "DROP TABLE IF EXISTS flyway_schema_history;"'
        run_command(drop_table_cmd, f"删除 {db} 的迁移历史表")
        drop_tables_cmd = f'''psql -U postgres -h localhost -d {db} -c "DROP TABLE IF EXISTS terminals CASCADE;"'''
        run_command(drop_tables_cmd, f"删除 {db} 的终端表")

def update_application_config():
    print("=== 更新应用配置使用测试数据库 ===")
    backup_cmd = "copy backend\\src\\main\\resources\\application.yml backend\\src\\main\\resources\\application.yml.backup"
    run_command(backup_cmd, "备份原配置文件")
    config_file = "backend/src/main/resources/application.yml"
    with open(config_file, 'r', encoding='utf-8') as f:
        content = f.read()
    content = content.replace(
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_dev',
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_test'
    )
    with open(config_file, 'w', encoding='utf-8') as f:
        f.write(content)
    print("✅ 配置文件已更新为使用测试数据库")

def restore_application_config():
    print("=== 恢复原配置文件 ===")
    restore_cmd = "copy backend\\src\\main\\resources\\application.yml.backup backend\\src\\main\\resources\\application.yml"
    run_command(restore_cmd, "恢复原配置文件")

def run_terminal_tests():
    print("\n=== 运行Terminal实体、仓库测试 ===")
    test_commands = [
        "cd backend && mvn clean compile -DskipTests",
        "cd backend && mvn test -Dtest=TerminalRepositoryOnlyTest"
    ]
    for i, command in enumerate(test_commands, 1):
        print(f"\n--- 步骤 {i}: {command.split()[0]} ---")
        if not run_command(command, f"执行测试步骤 {i}"):
            print(f"❌ 步骤 {i} 失败")
            return False
        print(f"✅ 步骤 {i} 完成")
    return True

def main():
    print("=" * 60)
    print("Terminal实体、仓库三合一测试")
    print("统一使用 cloudcontrol_test 数据库")
    print("不依赖任何其它表")
    print("=" * 60)
    current_dir = os.getcwd()
    print(f"当前工作目录: {current_dir}")
    required_files = [
        "backend/pom.xml",
        "backend/src/main/resources/application.yml",
        "backend/src/test/java/com/cloudcontrol/repository/terminal/TerminalRepositoryOnlyTest.java"
    ]
    for file_path in required_files:
        if not os.path.exists(file_path):
            print(f"❌ 缺少必要文件: {file_path}")
            return False
        print(f"✅ 文件存在: {file_path}")
    if not check_environment():
        print("❌ 环境检查失败，退出测试")
        return False
    try:
        clear_flyway_migration()
        reset_test_database()
        update_application_config()
        if run_terminal_tests():
            print("\n" + "=" * 60)
            print("✅ 所有测试完成")
            print("=" * 60)
        else:
            print("\n" + "=" * 60)
            print("❌ 测试失败")
            print("=" * 60)
            return False
    finally:
        restore_application_config()
        print("✅ 配置文件已恢复")

if __name__ == "__main__":
    main() 