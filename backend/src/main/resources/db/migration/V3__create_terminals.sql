-- 终端表创建脚本
-- 版本: V2
-- 描述: 创建终端表，与Terminal实体类完全匹配

CREATE TABLE terminals (
    id BIGSERIAL PRIMARY KEY,                           -- 终端ID，自增主键
    title VARCHAR(100) NOT NULL,                        -- 终端名称
    status VARCHAR(255) NOT NULL,                       -- 终端状态（枚举字符串）
    author BIGINT NOT NULL,                             -- 创建者ID，外键关联users表
    date TIMESTAMP NOT NULL,                            -- 创建时间
    group_id BIGINT,                                    -- 所属终端组ID，外键关联terminal_groups表
    ip_address VARCHAR(45),                             -- 终端IP地址
    mac_address VARCHAR(17),                            -- 终端MAC地址
    os VARCHAR(50),                                     -- 终端操作系统
    version VARCHAR(50),                                -- 终端版本
    capabilities TEXT,                                  -- 终端能力描述（JSON格式）
    last_heartbeat TIMESTAMP,                           -- 最后心跳时间
    location VARCHAR(200),                              -- 终端位置信息
    description VARCHAR(500),                           -- 终端描述
    updated_at TIMESTAMP,                               -- 更新时间
    FOREIGN KEY (author) REFERENCES users(id),          -- 外键约束：创建者
    FOREIGN KEY (group_id) REFERENCES terminal_groups(id) -- 外键约束：终端组
); 