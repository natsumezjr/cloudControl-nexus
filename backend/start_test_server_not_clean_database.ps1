# 项目路径：CloudControl-Nexus/backend
# 用于测试环境启动服务器（PostgreSQL，Flyway，test profile）
if (-not (Split-Path -Leaf (Get-Location)) -eq "backend") {
    Set-Location backend
}
mvn -f pom.xml spring-boot:run -Ptest