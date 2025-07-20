package com.cloudcontrol.entity.terminal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 节目终端关系实体类
 */
@Entity
@Table(name = "program_terminal_relations")
public class ProgramTerminalRelation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Integer relationId;
    
    @Column(name = "program_id", nullable = false)
    private Integer programId;
    
    @Column(name = "terminal_id", nullable = false)
    private Integer terminalId;
    
    @Column(name = "publish_status")
    private String publishStatus; // published, pending, failed
    
    @Column(name = "publish_time")
    private LocalDateTime publishTime;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public ProgramTerminalRelation() {}
    
    public ProgramTerminalRelation(Integer programId, Integer terminalId) {
        this.programId = programId;
        this.terminalId = terminalId;
        this.publishStatus = "published";
        this.publishTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // getter/setter
    public Integer getRelationId() { return relationId; }
    public void setRelationId(Integer relationId) { this.relationId = relationId; }
    
    public Integer getProgramId() { return programId; }
    public void setProgramId(Integer programId) { this.programId = programId; }
    
    public Integer getTerminalId() { return terminalId; }
    public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
    
    public String getPublishStatus() { return publishStatus; }
    public void setPublishStatus(String publishStatus) { this.publishStatus = publishStatus; }
    
    public LocalDateTime getPublishTime() { return publishTime; }
    public void setPublishTime(LocalDateTime publishTime) { this.publishTime = publishTime; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 