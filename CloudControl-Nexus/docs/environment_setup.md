# CloudControl-Nexus 开发环境安装指南

## 当前环境状态
- ✅ Java已安装
- ❌ Maven未安装
- ❌ Node.js未安装
- ❌ PostgreSQL未安装
- ✅ Docker已安装但服务未运行
- ❌ Git未安装
- ✅ VS Code已安装

## 1. 安装Maven

### 方法一：使用Chocolatey（推荐）
```powershell
# 安装Chocolatey（如果未安装）
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# 安装Maven
choco install maven
```

### 方法二：手动安装
1. 下载Maven：https://maven.apache.org/download.cgi
2. 解压到 `C:\Program Files\Apache\maven`
3. 添加环境变量：
   - `MAVEN_HOME`: `C:\Program Files\Apache\maven`
   - `PATH`: 添加 `%MAVEN_HOME%\bin`

## 2. 安装Node.js

### 使用Chocolatey
```powershell
choco install nodejs
```

### 或从官网下载
1. 访问：https://nodejs.org/
2. 下载LTS版本
3. 运行安装程序

## 3. 安装PostgreSQL

### 使用Chocolatey
```powershell
choco install postgresql
```

### 或从官网下载
1. 访问：https://www.postgresql.org/download/windows/
2. 下载安装程序
3. 安装时记住设置的密码

## 4. 启动Docker服务

```powershell
# 启动Docker Desktop
# 或者使用命令行启动Docker服务
Start-Service docker
```

## 5. 安装Git

### 使用Chocolatey
```powershell
choco install git
```

### 或从官网下载
1. 访问：https://git-scm.com/download/win
2. 下载并安装

## 验证安装

安装完成后，重新运行环境检查脚本：
```powershell
python check_environment.py
```

## 推荐的IDE插件

### VS Code插件
- Java Extension Pack
- Spring Boot Extension Pack
- Vue Language Features (Volar)
- PostgreSQL
- Docker
- GitLens

### IntelliJ IDEA（可选）
- Spring Boot插件
- Vue.js插件
- Docker插件 