-- 创建Tus上传表
CREATE TABLE tus_upload (
    id BIGSERIAL PRIMARY KEY,
    upload_id VARCHAR(255) NOT NULL UNIQUE,
    filename VARCHAR(500),
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    file_size BIGINT,
    upload_offset BIGINT DEFAULT 0,
    checksum VARCHAR(32),
    file_path VARCHAR(1000),
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_tus_upload_checksum ON tus_upload(checksum);
CREATE INDEX idx_tus_upload_status ON tus_upload(status);
CREATE INDEX idx_tus_upload_created_at ON tus_upload(created_at);

-- 添加注释
COMMENT ON TABLE tus_upload IS 'Tus断点续传上传文件表';
COMMENT ON COLUMN tus_upload.id IS '主键ID';
COMMENT ON COLUMN tus_upload.upload_id IS '上传会话ID';
COMMENT ON COLUMN tus_upload.filename IS '文件名';
COMMENT ON COLUMN tus_upload.file_type IS '文件类型';
COMMENT ON COLUMN tus_upload.mime_type IS 'MIME类型';
COMMENT ON COLUMN tus_upload.file_size IS '文件大小';
COMMENT ON COLUMN tus_upload.upload_offset IS '已上传偏移量';
COMMENT ON COLUMN tus_upload.checksum IS 'MD5校验和';
COMMENT ON COLUMN tus_upload.file_path IS '文件路径';
COMMENT ON COLUMN tus_upload.status IS '上传状态：PENDING/UPLOADING/COMPLETED/FAILED';
COMMENT ON COLUMN tus_upload.created_at IS '创建时间';
COMMENT ON COLUMN tus_upload.updated_at IS '更新时间'; 