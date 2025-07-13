-- 终端组表创建脚本
-- 版本: V3
-- 描述: 创建终端组表，与TerminalGroup实体类完全匹配

CREATE TABLE terminal_groups (
    id BIGSERIAL PRIMARY KEY,                           -- 终端组ID，自增主键
    name VARCHAR(100) NOT NULL,                         -- 终端组名称
    description VARCHAR(500),                           -- 终端组描述
    parent_id BIGINT,                                   -- 父级终端组ID，外键关联自身
    created_at TIMESTAMP NOT NULL,                      -- 创建时间
    updated_at TIMESTAMP,                               -- 更新时间
    sort_order INTEGER,                                 -- 排序字段
    enabled BOOLEAN NOT NULL DEFAULT TRUE,              -- 是否启用
    FOREIGN KEY (parent_id) REFERENCES terminal_groups(id) -- 外键约束：父级终端组
); 