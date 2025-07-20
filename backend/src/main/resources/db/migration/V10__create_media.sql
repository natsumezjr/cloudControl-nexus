-- 创建媒体表
CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title_raw VARCHAR(255),
    description TEXT,
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    media_type VARCHAR(50),
    attachment_filesize BIGINT,
    file_path VARCHAR(500),
    thumbnail_path VARCHAR(500),
    width INTEGER,
    height INTEGER,
    duration INTEGER,
    author INTEGER,
    guid VARCHAR(255),
    slug VARCHAR(255),
    source_url VARCHAR(500),
    src VARCHAR(500),
    post_status VARCHAR(50) DEFAULT 'inherit',
    comment_status VARCHAR(50) DEFAULT '',
    ping_status VARCHAR(50) DEFAULT '',
    parent INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_media_author ON media(author);
CREATE INDEX idx_media_file_path ON media(file_path);
CREATE INDEX idx_media_created_at ON media(created_at);
CREATE INDEX idx_media_media_type ON media(media_type);

-- 添加注释
COMMENT ON TABLE media IS '媒体文件表';
COMMENT ON COLUMN media.id IS '主键ID';
COMMENT ON COLUMN media.title_raw IS '原始标题';
COMMENT ON COLUMN media.description IS '描述';
COMMENT ON COLUMN media.file_type IS '文件类型';
COMMENT ON COLUMN media.mime_type IS 'MIME类型';
COMMENT ON COLUMN media.media_type IS '媒体类型(image/video/audio/file)';
COMMENT ON COLUMN media.attachment_filesize IS '文件大小';
COMMENT ON COLUMN media.file_path IS '文件路径';
COMMENT ON COLUMN media.thumbnail_path IS '缩略图路径';
COMMENT ON COLUMN media.width IS '宽度';
COMMENT ON COLUMN media.height IS '高度';
COMMENT ON COLUMN media.duration IS '时长(秒)';
COMMENT ON COLUMN media.author IS '作者ID';
COMMENT ON COLUMN media.guid IS '全局唯一标识符';
COMMENT ON COLUMN media.slug IS '别名';
COMMENT ON COLUMN media.source_url IS '源URL';
COMMENT ON COLUMN media.src IS '源文件路径';
COMMENT ON COLUMN media.post_status IS '发布状态';
COMMENT ON COLUMN media.comment_status IS '评论状态';
COMMENT ON COLUMN media.ping_status IS 'Ping状态';
COMMENT ON COLUMN media.parent IS '父级ID';
COMMENT ON COLUMN media.created_at IS '创建时间';
COMMENT ON COLUMN media.updated_at IS '更新时间'; 