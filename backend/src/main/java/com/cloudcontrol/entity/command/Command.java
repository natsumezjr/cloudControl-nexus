package com.cloudcontrol.entity.command;

import com.cloudcontrol.enums.command.CommandStatus;
import com.cloudcontrol.enums.command.CommandMethod;
import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.entity.terminal.Terminal;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 指令实体类
 * 用于管理播控指令的创建、执行、状态跟踪等
 */
@Entity
@Table(name = "commands")
public class Command {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 目标终端ID - 外键关联
     */
    @ManyToOne
    @JoinColumn(name = "post", nullable = false)
    private Terminal terminal;
    
    /**
     * 指令内容（JSON格式）
     */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
    
    /**
     * 指令执行URL
     */
    @Column(name = "act_url", length = 500)
    private String actUrl;
    
    /**
     * HTTP方法（0-GET, 1-POST, 2-PUT, 3-DELETE）
     */
    @Enumerated(EnumType.ORDINAL)
    private CommandMethod actMethod;
    
    /**
     * 指令状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommandStatus status;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 指令创建者ID - 外键关联
     */
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;
    
    // 构造方法
    public Command() {}
    
    public Command(Terminal terminal, String content, String actUrl, CommandMethod actMethod, User author) {
        this.terminal = terminal;
        this.content = content;
        this.actUrl = actUrl;
        this.actMethod = actMethod;
        this.author = author;
        this.status = CommandStatus.PENDING; // 默认状态
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Terminal getTerminal() {
        return terminal;
    }
    
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getActUrl() {
        return actUrl;
    }
    
    public void setActUrl(String actUrl) {
        this.actUrl = actUrl;
    }
    
    public CommandMethod getActMethod() {
        return actMethod;
    }
    
    public void setActMethod(CommandMethod actMethod) {
        this.actMethod = actMethod;
    }
    
    public CommandStatus getStatus() {
        return status;
    }
    
    public void setStatus(CommandStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public User getAuthor() {
        return author;
    }
    
    public void setAuthor(User author) {
        this.author = author;
    }
}