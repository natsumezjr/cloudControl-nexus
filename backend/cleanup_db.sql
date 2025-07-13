
-- 清理数据库，只保留users表
DROP TABLE IF EXISTS command_results CASCADE;
DROP TABLE IF EXISTS commands CASCADE;
DROP TABLE IF EXISTS user_terminal_groups CASCADE;
DROP TABLE IF EXISTS terminals CASCADE;
DROP TABLE IF EXISTS terminal_groups CASCADE;
-- 保留users表
