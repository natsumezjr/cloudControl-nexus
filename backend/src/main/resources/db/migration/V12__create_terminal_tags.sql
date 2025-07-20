-- 创建终端标签表
CREATE TABLE terminal_tags (
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_terminal_tags_tag_name ON terminal_tags(tag_name);
CREATE INDEX idx_terminal_tags_created_at ON terminal_tags(created_at);

-- 添加注释
COMMENT ON TABLE terminal_tags IS '终端标签表';
COMMENT ON COLUMN terminal_tags.tag_id IS '标签ID';
COMMENT ON COLUMN terminal_tags.tag_name IS '标签名称';
COMMENT ON COLUMN terminal_tags.created_at IS '创建时间';
COMMENT ON COLUMN terminal_tags.updated_at IS '更新时间'; 