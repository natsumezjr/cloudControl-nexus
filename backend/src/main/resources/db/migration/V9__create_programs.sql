-- 创建节目表
CREATE TABLE programs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    program_info TEXT,
    programs_data TEXT,
    author INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_programs_title ON programs(title);
CREATE INDEX idx_programs_status ON programs(status);
CREATE INDEX idx_programs_author ON programs(author);
CREATE INDEX idx_programs_created_at ON programs(created_at);

-- 添加注释
COMMENT ON TABLE programs IS '节目表';
COMMENT ON COLUMN programs.id IS '主键ID';
COMMENT ON COLUMN programs.title IS '节目标题';
COMMENT ON COLUMN programs.status IS '节目状态';
COMMENT ON COLUMN programs.program_info IS '节目信息JSON';
COMMENT ON COLUMN programs.programs_data IS '节目数据JSON';
COMMENT ON COLUMN programs.author IS '作者ID';
COMMENT ON COLUMN programs.created_at IS '创建时间';
COMMENT ON COLUMN programs.updated_at IS '更新时间'; 