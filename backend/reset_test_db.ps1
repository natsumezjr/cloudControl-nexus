# 项目路径：CloudControl-Nexus/backend
# 用于测试环境数据库清空和迁移（PostgreSQL，Flyway，test profile）

# 项目路径：CloudControl-Nexus/backend
# 用于测试环境数据库清空和迁移（PostgreSQL，Flyway，test profile）
if (-not (Split-Path -Leaf (Get-Location)) -eq "backend") {
    Set-Location backend
}
mvn flyway:clean -Ptest
mvn flyway:migrate -Ptest 