@echo off
chcp 65001 >nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Dmaven.compiler.encoding=UTF-8

echo 开始清理数据库...
call mvn flyway:clean -Ptest

echo 开始数据库迁移...
call mvn flyway:migrate -Ptest

echo 启动测试服务器...
call mvn -f pom.xml spring-boot:run -Ptest
