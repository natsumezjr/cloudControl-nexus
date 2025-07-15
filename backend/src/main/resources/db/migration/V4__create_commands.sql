-- 创建 commands 表，字段与 Command 实体严格一致
CREATE TABLE commands (
    id BIGSERIAL PRIMARY KEY,
    terminal_id BIGINT NOT NULL,
    author_url VARCHAR(255) NOT NULL,
    content_raw TEXT NOT NULL,
    karma INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_command_terminal
        FOREIGN KEY (terminal_id) REFERENCES terminals(serial_no)
        ON DELETE CASCADE
);