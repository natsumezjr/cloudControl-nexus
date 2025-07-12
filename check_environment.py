#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
开发环境检查脚本
检查Java、Node.js、PostgreSQL等开发环境是否正确安装
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
    
    return True

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
    docker_ok = check_docker()
    ide_ok = check_ide_tools()
    
    print("=" * 50)
    print("环境检查总结:")
    print(f"Java环境: {'✅ 正常' if java_ok else '❌ 需要安装'}")
    print(f"Node.js环境: {'✅ 正常' if nodejs_ok else '❌ 需要安装'}")
    print(f"PostgreSQL环境: {'✅ 正常' if postgresql_ok else '❌ 需要安装'}")
    print(f"Docker环境: {'✅ 正常' if docker_ok else '❌ 需要安装'}")
    print(f"开发工具: {'✅ 正常' if ide_ok else '⚠️ 建议安装'}")
    
    if all([java_ok, nodejs_ok, postgresql_ok, docker_ok]):
        print("\n🎉 所有必需环境都已正确安装！可以开始开发了。")
    else:
        print("\n⚠️ 请先安装缺失的开发环境，然后重新运行此脚本。")

if __name__ == "__main__":
    main() 