-- 终端表创建脚本
-- 版本: V3
-- 描述: 创建终端表，字段与Terminal实体类完全一致

CREATE TABLE terminals (
    serial_no INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,  -- 设备序列号，自增主键
    device_name VARCHAR(255) NOT NULL, -- 设备名称（注册时必填）
    password VARCHAR(255) NOT NULL, -- 终端账号密码
    account_name VARCHAR(255), -- 终端账号名称
    led_description VARCHAR(255), -- 设备描述
    last_heartbeat TIMESTAMP, -- 最后心跳/消息时间
    status VARCHAR(64), -- 终端状态（如online/sleep/rebooting）
    power_status INTEGER, -- 休眠/唤醒
    brightness INTEGER, -- 亮度
    colortemp INTEGER, -- 色温
    volume INTEGER, -- 音量
    input_mode VARCHAR(64), -- 信号源
    language VARCHAR(64), -- 语言
    country VARCHAR(64), -- 地区
    timezone_id VARCHAR(64), -- 时区ID
    timezone FLOAT, -- 时区偏移
    is_auto_time INTEGER, -- 是否自动同步时间
    program VARCHAR(255), -- 当前节目名
    program_type VARCHAR(64), -- 节目来源类型
    gps_report_interval INTEGER, -- GPS上报间隔
    sensor_report_interval INTEGER, -- 监控上报间隔
    content_report_status INTEGER, -- 内容上报开关
    log_report VARCHAR(64), -- 日志上报开关
    rotate_program_vsns_report VARCHAR(64), -- 轮播节目名上报开关
    extra TEXT -- 预留扩展字段
); 