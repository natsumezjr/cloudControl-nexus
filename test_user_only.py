#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
User实体、仓库、DTO三合一测试脚本
使用主配置文件 application.yml 和 pom.xml
统一使用 cloudcontrol_test 数据库
不使用任何环境配置
"""

import os
import sys
import subprocess
import time
import json
from pathlib import Path

def run_command(command, description):
    """执行命令并返回结果"""
    print(f"\n=== {description} ===")
    print(f"执行命令: {command}")
    
    try:
        # 修复编码问题：指定encoding和errors参数
        result = subprocess.run(
            command, 
            shell=True, 
            capture_output=True, 
            text=True, 
            encoding='utf-8',
            errors='ignore',  # 忽略无法解码的字符
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
    """检查环境配置"""
    print("=== 环境检查 ===")
    
    # 检查Java版本
    if not run_command("java -version", "检查Java版本"):
        print("❌ Java未安装或配置错误")
        return False
    
    # 检查Maven版本
    if not run_command("mvn -version", "检查Maven版本"):
        print("❌ Maven未安装或配置错误")
        return False
    
    # 检查PostgreSQL连接 - 跳过环境检查脚本，直接测试数据库连接
    print("=== 直接检查PostgreSQL连接 ===")
    os.environ['PGPASSWORD'] = 'bupt_test'
    if not run_command('psql -U postgres -h localhost -c "SELECT version();"', "检查PostgreSQL连接"):
        print("❌ PostgreSQL连接失败")
        return False
    
    print("✅ 环境检查通过")
    return True

def reset_test_database():
    """重置 cloudcontrol_test 数据库"""
    print("=== 重置 cloudcontrol_test 数据库 ===")
    
    # 设置PostgreSQL密码环境变量
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
    """清空Flyway迁移状态"""
    print("=== 清空Flyway迁移状态 ===")
    
    # 设置PostgreSQL密码环境变量
    os.environ['PGPASSWORD'] = 'bupt_test'
    
    # 清空所有数据库的迁移状态
    databases = ['cloudcontrol_dev', 'cloudcontrol_test']
    
    for db in databases:
        print(f"清空数据库 {db} 的迁移状态...")
        
        # 删除flyway_schema_history表
        drop_table_cmd = f'psql -U postgres -h localhost -d {db} -c "DROP TABLE IF EXISTS flyway_schema_history;"'
        run_command(drop_table_cmd, f"删除 {db} 的迁移历史表")
        
        # 删除所有用户表（除了系统表）
        drop_tables_cmd = f'''psql -U postgres -h localhost -d {db} -c "
        DROP TABLE IF EXISTS command_results CASCADE;
        DROP TABLE IF EXISTS user_terminal_groups CASCADE;
        DROP TABLE IF EXISTS commands CASCADE;
        DROP TABLE IF EXISTS terminal_groups CASCADE;
        DROP TABLE IF EXISTS terminals CASCADE;
        DROP TABLE IF EXISTS users CASCADE;
        "'''
        run_command(drop_tables_cmd, f"删除 {db} 的所有用户表")

def update_application_config():
    """临时更新application.yml使用测试数据库"""
    print("=== 更新应用配置使用测试数据库 ===")
    
    # 备份原配置文件
    backup_cmd = "copy backend\\src\\main\\resources\\application.yml backend\\src\\main\\resources\\application.yml.backup"
    run_command(backup_cmd, "备份原配置文件")
    
    # 读取原配置
    config_file = "backend/src/main/resources/application.yml"
    with open(config_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 替换数据库URL为测试数据库
    content = content.replace(
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_dev',
        'url: jdbc:postgresql://localhost:5432/cloudcontrol_test'
    )
    
    # 写回配置文件
    with open(config_file, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print("✅ 配置文件已更新为使用测试数据库")

def restore_application_config():
    """恢复原配置文件"""
    print("=== 恢复原配置文件 ===")
    restore_cmd = "copy backend\\src\\main\\resources\\application.yml.backup backend\\src\\main\\resources\\application.yml"
    run_command(restore_cmd, "恢复原配置文件")

def run_user_tests():
    """运行User相关测试"""
    print("\n=== 运行User实体、仓库、DTO测试 ===")
    
    # 使用测试数据库配置运行测试，不使用任何环境配置
    test_commands = [
        # 编译项目
        "cd backend && mvn clean compile -DskipTests",
        
        # 运行User相关测试
        "cd backend && mvn test -Dtest=UserRepositoryIntegrationTest",
        
        # 运行所有测试
        "cd backend && mvn test"
    ]
    
    for i, command in enumerate(test_commands, 1):
        print(f"\n--- 步骤 {i}: {command.split()[0]} ---")
        if not run_command(command, f"执行测试步骤 {i}"):
            print(f"❌ 步骤 {i} 失败")
            return False
        print(f"✅ 步骤 {i} 完成")
    
    return True

def main():
    """主函数"""
    print("=" * 60)
    print("User实体、仓库、DTO三合一测试")
    print("统一使用 cloudcontrol_test 数据库")
    print("不使用任何环境配置")
    print("=" * 60)
    
    # 检查当前目录
    current_dir = os.getcwd()
    print(f"当前工作目录: {current_dir}")
    
    # 检查必要文件
    required_files = [
        "backend/pom.xml",
        "backend/src/main/resources/application.yml",
        "backend/src/test/java/com/cloudcontrol/repository/user/UserRepositoryIntegrationTest.java"
    ]
    
    for file_path in required_files:
        if not os.path.exists(file_path):
            print(f"❌ 缺少必要文件: {file_path}")
            return False
        print(f"✅ 文件存在: {file_path}")
    
    # 环境检查
    if not check_environment():
        print("❌ 环境检查失败，退出测试")
        return False
    
    try:
        # 清空所有迁移状态
        clear_flyway_migration()
        
        # 自动清空测试数据库
        reset_test_database()
        
        # 更新配置文件使用测试数据库
        update_application_config()
        
        # 运行测试
        if run_user_tests():
            print("\n" + "=" * 60)
            print("✅ 所有测试完成")
            print("=" * 60)
        else:
            print("\n" + "=" * 60)
            print("❌ 测试失败")
            print("=" * 60)
            return False
    finally:
        # 恢复原配置文件
        restore_application_config()
        print("✅ 配置文件已恢复")

if __name__ == "__main__":
    main() 