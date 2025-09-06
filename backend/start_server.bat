@echo off
chcp 65001 >nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Dmaven.compiler.encoding=UTF-8

if "%1"=="dev" (
    echo 启动开发服务器...
    call mvn -f pom.xml spring-boot:run -Pdev
) else if "%1"=="test" (
    echo 开始清理数据库...
    call mvn flyway:clean -Ptest
    echo 开始数据库迁移...
    call mvn flyway:migrate -Ptest
    echo 启动测试服务器...
    call mvn -f pom.xml spring-boot:run -Ptest
) else if "%1"=="test-noclean" (
    echo 启动测试服务器（不清理数据库）...
    call mvn -f pom.xml spring-boot:run -Ptest
) else (
    echo 使用方法:
    echo   start_server.bat dev          - 启动开发服务器
    echo   start_server.bat test         - 启动测试服务器（清理数据库）
    echo   start_server.bat test-noclean - 启动测试服务器（不清理数据库）
)
