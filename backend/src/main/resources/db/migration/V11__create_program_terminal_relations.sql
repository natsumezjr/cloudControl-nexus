-- 创建节目终端关系表
CREATE TABLE program_terminal_relations (
    relation_id SERIAL PRIMARY KEY,
    program_id INTEGER NOT NULL,
    terminal_id INTEGER NOT NULL,
    publish_status VARCHAR(50) DEFAULT 'published',
    publish_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_program_terminal_relations_program_id ON program_terminal_relations(program_id);
CREATE INDEX idx_program_terminal_relations_terminal_id ON program_terminal_relations(terminal_id);
CREATE INDEX idx_program_terminal_relations_publish_status ON program_terminal_relations(publish_status);
CREATE UNIQUE INDEX idx_program_terminal_relations_unique ON program_terminal_relations(program_id, terminal_id);

-- 添加外键约束
ALTER TABLE program_terminal_relations 
ADD CONSTRAINT fk_program_terminal_relations_program_id 
FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE;

ALTER TABLE program_terminal_relations 
ADD CONSTRAINT fk_program_terminal_relations_terminal_id 
FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no) ON DELETE CASCADE;

-- 添加注释
COMMENT ON TABLE program_terminal_relations IS '节目终端关系表';
COMMENT ON COLUMN program_terminal_relations.relation_id IS '关系ID';
COMMENT ON COLUMN program_terminal_relations.program_id IS '节目ID';
COMMENT ON COLUMN program_terminal_relations.terminal_id IS '终端ID';
COMMENT ON COLUMN program_terminal_relations.publish_status IS '发布状态(published/pending/failed)';
COMMENT ON COLUMN program_terminal_relations.publish_time IS '发布时间';
COMMENT ON COLUMN program_terminal_relations.created_at IS '创建时间';
COMMENT ON COLUMN program_terminal_relations.updated_at IS '更新时间'; 