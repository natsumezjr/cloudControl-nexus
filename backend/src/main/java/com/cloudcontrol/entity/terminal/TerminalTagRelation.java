package com.cloudcontrol.entity.terminal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 终端标签关系实体类
 */
@Entity
@Table(name = "terminal_tag_relations")
public class TerminalTagRelation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Integer relationId;
    
    @Column(name = "terminal_id", nullable = false)
    private Integer terminalId;
    
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public TerminalTagRelation() {}
    
    public TerminalTagRelation(Integer terminalId, Integer tagId) {
        this.terminalId = terminalId;
        this.tagId = tagId;
        this.createdAt = LocalDateTime.now();
    }
    
    // getter/setter
    public Integer getRelationId() { return relationId; }
    public void setRelationId(Integer relationId) { this.relationId = relationId; }
    
    public Integer getTerminalId() { return terminalId; }
    public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
    
    public Integer getTagId() { return tagId; }
    public void setTagId(Integer tagId) { this.tagId = tagId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 