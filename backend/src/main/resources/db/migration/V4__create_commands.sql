-- 指令表创建脚本
-- 版本: V4
-- 描述: 创建指令表，与Command实体类完全匹配

CREATE TABLE commands (
    id BIGSERIAL PRIMARY KEY,                           -- 指令ID，自增主键
    post BIGINT NOT NULL,                               -- 目标终端ID，外键关联terminals表
    content TEXT NOT NULL,                              -- 指令内容（JSON格式）
    act_url VARCHAR(500),                               -- 指令执行URL
    act_method SMALLINT,                                -- HTTP方法（0-GET, 1-POST, 2-PUT, 3-DELETE）
    status VARCHAR(255) NOT NULL,                       -- 指令状态（枚举字符串）
    created_at TIMESTAMP NOT NULL,                      -- 创建时间
    author BIGINT NOT NULL,                             -- 指令创建者ID，外键关联users表
    FOREIGN KEY (post) REFERENCES terminals(id),         -- 外键约束：目标终端
    FOREIGN KEY (author) REFERENCES users(id)            -- 外键约束：创建者
); 