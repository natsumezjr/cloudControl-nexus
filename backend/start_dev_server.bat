@echo off
chcp 65001 >nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Dmaven.compiler.encoding=UTF-8

echo 启动开发服务器...
call mvn -f pom.xml spring-boot:run -Pdev
