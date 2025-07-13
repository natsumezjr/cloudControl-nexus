-- 指令结果表创建脚本
-- 版本: V6
-- 描述: 创建指令结果表，与CommandResult实体类完全匹配

CREATE TABLE command_results (
    id BIGSERIAL PRIMARY KEY,                           -- 结果ID，自增主键
    command_id BIGINT NOT NULL,                         -- 关联的指令ID，外键关联commands表
    exit_code INTEGER,                                  -- 退出码
    stdout TEXT,                                        -- 标准输出
    stderr TEXT,                                        -- 标准错误输出
    started_at TIMESTAMP,                               -- 开始执行时间
    finished_at TIMESTAMP,                              -- 完成执行时间
    duration_ms BIGINT,                                 -- 执行耗时（毫秒）
    error_message VARCHAR(1000),                        -- 错误信息
    environment TEXT,                                   -- 执行环境信息
    created_at TIMESTAMP NOT NULL,                      -- 创建时间
    FOREIGN KEY (command_id) REFERENCES commands(id)    -- 外键关联commands表
); 