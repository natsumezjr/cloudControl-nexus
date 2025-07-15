package com.cloudcontrol.entity.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "terminal_id", nullable = false)
    @JsonProperty("post")
    private int terminalId;

    @Column(name = "cmd_num", nullable = false)
    private int cmdNum;

    // value 字段仅用于业务逻辑和缓存，不做数据库持久化
    @Transient
    private Optional<Object> value;


    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    // getter/setter 省略

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTerminalId() { return terminalId; }
    public void setTerminalId(int terminalId) { this.terminalId = terminalId; }

    public int getCmdNum() { return cmdNum; }
    public void setCmdNum(int cmdNum) { this.cmdNum = cmdNum; }

    public Optional<Object> getValue() { return value; }
    public void setValue(Optional<Object> value) { this.value = value; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
