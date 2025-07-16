package com.cloudcontrol.entity.command;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 指令结果实体类
 * 用于存储指令执行的详细结果信息
 */
@Entity
@Table(name = "command_results")
public class CommandResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * 关联的指令ID - 外键关联
     */
    @ManyToOne
    @JoinColumn(name = "command_id", nullable = false)
    private Command command;
    
    /**
     * 退出码
     */
    @Column(name = "exit_code")
    private Integer exitCode;
    
    /**
     * 标准输出
     */
    @Column(name = "stdout", columnDefinition = "TEXT")
    private String stdout;
    
    /**
     * 标准错误输出
     */
    @Column(name = "stderr", columnDefinition = "TEXT")
    private String stderr;
    
    /**
     * 开始执行时间
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    /**
     * 完成执行时间
     */
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;
    
    /**
     * 执行耗时（毫秒）
     */
    @Column(name = "duration_ms")
    private Long durationMs;
    
    /**
     * 错误信息
     */
    @Column(name = "error_message", length = 1000)
    private String errorMessage;
    
    /**
     * 执行环境信息
     */
    @Column(name = "environment", columnDefinition = "TEXT")
    private String environment;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // 构造方法
    public CommandResult() {}
    
    public CommandResult(Command command) {
        this.command = command;
        this.startedAt = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Command getCommand() {
        return command;
    }
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public Integer getExitCode() {
        return exitCode;
    }
    
    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }
    
    public String getStdout() {
        return stdout;
    }
    
    public void setStdout(String stdout) {
        this.stdout = stdout;
    }
    
    public String getStderr() {
        return stderr;
    }
    
    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }
    
    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
    
    public Long getDurationMs() {
        return durationMs;
    }
    
    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 完成执行
     */
    public void finish(Integer exitCode, String stdout, String stderr) {
        this.exitCode = exitCode;
        this.stdout = stdout;
        this.stderr = stderr;
        this.finishedAt = LocalDateTime.now();
        
        if (this.startedAt != null && this.finishedAt != null) {
            this.durationMs = java.time.Duration.between(this.startedAt, this.finishedAt).toMillis();
        }
    }
    
    /**
     * 设置错误信息
     */
    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.exitCode = -1;
        this.finishedAt = LocalDateTime.now();
        
        if (this.startedAt != null && this.finishedAt != null) {
            this.durationMs = java.time.Duration.between(this.startedAt, this.finishedAt).toMillis();
        }
    }
} 