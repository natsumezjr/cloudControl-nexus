-- 为terminals表添加created_at字段和terminal_group_id外键
-- 版本: V7
-- 描述: 添加终端创建时间和终端组关联

-- 添加created_at字段
ALTER TABLE terminals ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 添加terminal_group_id外键字段
ALTER TABLE terminals ADD COLUMN terminal_group_id BIGINT;

-- 添加外键约束
ALTER TABLE terminals 
ADD CONSTRAINT fk_terminal_group 
FOREIGN KEY (terminal_group_id) REFERENCES terminal_groups(id) 
ON DELETE SET NULL; 