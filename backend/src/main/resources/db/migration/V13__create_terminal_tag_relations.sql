-- 创建终端标签关系表
CREATE TABLE terminal_tag_relations (
    relation_id SERIAL PRIMARY KEY,
    terminal_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_terminal_tag_relations_terminal_id ON terminal_tag_relations(terminal_id);
CREATE INDEX idx_terminal_tag_relations_tag_id ON terminal_tag_relations(tag_id);
CREATE UNIQUE INDEX idx_terminal_tag_relations_unique ON terminal_tag_relations(terminal_id, tag_id);

-- 添加外键约束
ALTER TABLE terminal_tag_relations 
ADD CONSTRAINT fk_terminal_tag_relations_terminal_id 
FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no) ON DELETE CASCADE;

ALTER TABLE terminal_tag_relations 
ADD CONSTRAINT fk_terminal_tag_relations_tag_id 
FOREIGN KEY (tag_id) REFERENCES terminal_tags(tag_id) ON DELETE CASCADE;

-- 添加注释
COMMENT ON TABLE terminal_tag_relations IS '终端标签关系表';
COMMENT ON COLUMN terminal_tag_relations.relation_id IS '关系ID';
COMMENT ON COLUMN terminal_tag_relations.terminal_id IS '终端ID';
COMMENT ON COLUMN terminal_tag_relations.tag_id IS '标签ID';
COMMENT ON COLUMN terminal_tag_relations.created_at IS '创建时间';
COMMENT ON COLUMN terminal_tag_relations.updated_at IS '更新时间'; 