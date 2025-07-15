-- 创建 commands 表，字段与 Command 实体严格一致
CREATE TABLE commands (
    id BIGSERIAL PRIMARY KEY,
    terminal_id BIGINT NOT NULL,
    cmd_num INTEGER NOT NULL, -- 对应 CommandType 枚举的编号
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_command_terminal
        FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no)
        ON DELETE CASCADE
);