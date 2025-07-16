-- 用户表创建脚本
-- 版本: V1
-- 描述: 创建用户表，与User实体类完全匹配

CREATE TABLE users (
    id SERIAL PRIMARY KEY,                              -- 用户ID，自增主键，类型为integer
    username VARCHAR(255) NOT NULL UNIQUE,              -- 用户名，唯一约束
    password VARCHAR(255) NOT NULL,                     -- 加密后的密码
    email VARCHAR(255),                                 -- 邮箱地址
    nickname VARCHAR(100),                              -- 昵称
    role VARCHAR(255),                                  -- 用户角色（枚举字符串）
    status VARCHAR(255),                                -- 用户状态（枚举字符串）
    created_at TIMESTAMP,                               -- 创建时间
    last_login TIMESTAMP,                               -- 最后登录时间
    term_id INTEGER,                                    -- 用户绑定的终端组ID
    assigned_size BIGINT                                -- 用户分配的空间大小（字节）
); 