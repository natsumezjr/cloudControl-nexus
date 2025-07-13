-- 用户终端组关联表创建脚本
-- 版本: V5
-- 描述: 创建用户终端组关联表，与UserTerminalGroup实体类完全匹配

CREATE TABLE user_terminal_groups (
    user_id BIGINT NOT NULL,                            -- 用户ID，联合主键的一部分
    terminal_group_id BIGINT NOT NULL,                  -- 终端组ID，联合主键的一部分
    permission_type VARCHAR(255),                        -- 权限类型（枚举字符串）
    enabled BOOLEAN NOT NULL DEFAULT TRUE,              -- 是否启用
    created_at TIMESTAMP NOT NULL,                      -- 创建时间
    created_by BIGINT,                                  -- 创建者ID
    remarks VARCHAR(500),                               -- 备注信息
    PRIMARY KEY (user_id, terminal_group_id),           -- 复合主键
    FOREIGN KEY (user_id) REFERENCES users(id),         -- 外键关联users表
    FOREIGN KEY (terminal_group_id) REFERENCES terminal_groups(id) -- 外键关联terminal_groups表
); 