-- 为programs表添加缺失的字段
-- 版本: V15
-- 描述: 添加slug和vsnName字段

-- 添加slug字段
ALTER TABLE programs ADD COLUMN slug VARCHAR(255);

-- 添加vsnName字段
ALTER TABLE programs ADD COLUMN vsn_name VARCHAR(255);

-- 创建索引
CREATE INDEX idx_programs_slug ON programs(slug);
CREATE INDEX idx_programs_vsn_name ON programs(vsn_name);

-- 添加注释
COMMENT ON COLUMN programs.slug IS '节目别名/标识符';
COMMENT ON COLUMN programs.vsn_name IS '版本名称'; 