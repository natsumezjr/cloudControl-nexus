-- 为terminals表添加缺失的字段
-- 版本: V14
-- 描述: 添加GPS坐标字段（lat、lng）

-- 添加GPS坐标字段
ALTER TABLE terminals ADD COLUMN lat FLOAT;
ALTER TABLE terminals ADD COLUMN lng FLOAT;

-- 创建GPS坐标索引
CREATE INDEX idx_terminals_gps_coordinates ON terminals(lat, lng);

-- 添加注释
COMMENT ON COLUMN terminals.lat IS '纬度';
COMMENT ON COLUMN terminals.lng IS '经度'; 