#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
开发环境检查脚本
检查Java、Node.js、PostgreSQL、Redis等开发环境是否正确安装
"""

import subprocess
import sys
import os
import platform

def run_command(command):
    """执行命令并返回结果"""
    try:
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        return result.returncode == 0, result.stdout.strip(), result.stderr.strip()
    except Exception as e:
        return False, "", str(e)

def check_java():
    """检查Java环境"""
    print("=" * 50)
    print("检查Java环境...")
    
    # 检查Java版本
    success, output, error = run_command("java -version")
    if success:
        print("✅ Java已安装")
        print(f"版本信息: {output}")
    else:
        print("❌ Java未安装或配置错误")
        print(f"错误信息: {error}")
        return False
    
    # 检查Maven
    success, output, error = run_command("mvn -version")
    if success:
        print("✅ Maven已安装")
        print(f"版本信息: {output.split('Maven home:')[0] if 'Maven home:' in output else output}")
    else:
        print("❌ Maven未安装")
        return False
    
    return True

def check_nodejs():
    """检查Node.js环境"""
    print("=" * 50)
    print("检查Node.js环境...")
    
    # 检查Node.js版本 - 先尝试直接命令，再尝试完整路径
    success, output, error = run_command("node --version")
    if not success:
        # 尝试使用完整路径
        success, output, error = run_command('"D:\\Program Files\\node.exe" --version')
    
    if success:
        print("✅ Node.js已安装")
        print(f"版本: {output}")
    else:
        print("❌ Node.js未安装")
        print(f"错误信息: {error}")
        return False
    
    # 检查npm
    success, output, error = run_command("npm --version")
    if success:
        print("✅ npm已安装")
        print(f"版本: {output}")
    else:
        print("❌ npm未安装")
        return False
    
    return True

def check_postgresql():
    """检查PostgreSQL环境"""
    print("=" * 50)
    print("检查PostgreSQL环境...")
    
    # 检查PostgreSQL服务
    success, output, error = run_command("psql --version")
    if success:
        print("✅ PostgreSQL客户端已安装")
        print(f"版本信息: {output}")
    else:
        print("❌ PostgreSQL客户端未安装")
        print(f"错误信息: {error}")
        return False
    
    # 检查PostgreSQL服务状态
    if platform.system() == "Windows":
        success, output, error = run_command("sc query postgresql")
    else:
        success, output, error = run_command("systemctl status postgresql")
    
    if success:
        print("✅ PostgreSQL服务正在运行")
    else:
        print("⚠️ PostgreSQL服务可能未运行，请手动检查")
    
    # 检查数据库连接
    print("\n📋 PostgreSQL配置说明:")
    print("1. 数据库名称: cloudcontrol_dev")
    print("2. 用户名: postgres")
    print("3. 密码: bupt_test (在application.yml中配置)")
    print("4. 端口: 5432")
    print("5. 创建数据库命令: CREATE DATABASE cloudcontrol_dev;")
    print("6. 修改密码命令: ALTER USER postgres PASSWORD 'your_password';")
    print("7. 配置文件位置: backend/src/main/resources/application.yml")
    print("8. 连接测试: psql -U postgres -h localhost -d cloudcontrol_dev")
    
    return True

def check_redis():
    """检查Redis环境"""
    print("=" * 50)
    print("检查Redis环境...")
    
    # 检查Docker是否运行
    success, output, error = run_command("docker info")
    if not success:
        print("❌ Docker未运行，无法检查Redis")
        return False
    
    # 检查Redis容器是否运行
    success, output, error = run_command("docker ps --filter name=redis-cloudcontrol --format '{{.Names}}'")
    if success and "redis-cloudcontrol" in output:
        print("✅ Redis容器正在运行")
        
        # 检查Redis端口是否可访问
        success, output, error = run_command("docker exec redis-cloudcontrol redis-cli ping")
        if success and "PONG" in output:
            print("✅ Redis服务响应正常")
            return True
        else:
            print("❌ Redis服务无响应")
            return False
    else:
        print("❌ Redis容器未运行")
        print("💡 启动Redis容器命令: docker run -d --name redis-cloudcontrol -p 6379:6379 redis:latest")
        return False

def check_docker():
    """检查Docker环境"""
    print("=" * 50)
    print("检查Docker环境...")
    
    success, output, error = run_command("docker --version")
    if success:
        print("✅ Docker已安装")
        print(f"版本信息: {output}")
    else:
        print("❌ Docker未安装")
        print(f"错误信息: {error}")
        return False
    
    # 检查Docker是否运行
    success, output, error = run_command("docker info")
    if success:
        print("✅ Docker服务正在运行")
    else:
        print("❌ Docker服务未运行")
        return False
    
    return True

def check_ide_tools():
    """检查IDE和开发工具"""
    print("=" * 50)
    print("检查开发工具...")
    
    # 检查Git
    success, output, error = run_command("git --version")
    if success:
        print("✅ Git已安装")
        print(f"版本: {output}")
    else:
        print("❌ Git未安装")
    
    # 检查VS Code
    if platform.system() == "Windows":
        success, output, error = run_command("code --version")
        if success:
            print("✅ VS Code已安装")
        else:
            print("⚠️ VS Code未安装或未添加到PATH")
    
    return True

def main():
    """主函数"""
    print("CloudControl-Nexus 开发环境检查")
    print("=" * 50)
    
    java_ok = check_java()
    nodejs_ok = check_nodejs()
    postgresql_ok = check_postgresql()
    redis_ok = check_redis()
    docker_ok = check_docker()
    ide_ok = check_ide_tools()
    
    print("=" * 50)
    print("环境检查总结:")
    print(f"Java环境: {'✅ 正常' if java_ok else '❌ 需要安装'}")
    print(f"Node.js环境: {'✅ 正常' if nodejs_ok else '❌ 需要安装'}")
    print(f"PostgreSQL环境: {'✅ 正常' if postgresql_ok else '❌ 需要安装'}")
    print(f"Redis环境: {'✅ 正常' if redis_ok else '❌ 需要启动'}")
    print(f"Docker环境: {'✅ 正常' if docker_ok else '❌ 需要安装'}")
    print(f"开发工具: {'✅ 正常' if ide_ok else '⚠️ 建议安装'}")
    
    print("\n" + "=" * 50)
    print("Redis配置说明:")
    print("1. Redis服务通过Docker容器运行")
    print("2. 容器名称: redis-cloudcontrol")
    print("3. 端口映射: 6379:6379")
    print("4. 启动命令: docker run -d --name redis-cloudcontrol -p 6379:6379 redis:latest")
    print("5. 停止命令: docker stop redis-cloudcontrol")
    print("6. 删除容器: docker rm redis-cloudcontrol")
    print("7. 查看日志: docker logs redis-cloudcontrol")
    
    if all([java_ok, nodejs_ok, postgresql_ok, redis_ok, docker_ok]):
        print("\n🎉 所有必需环境都已正确安装！可以开始开发了。")
    else:
        print("\n⚠️ 请先安装缺失的开发环境，然后重新运行此脚本。")

if __name__ == "__main__":
    main() 