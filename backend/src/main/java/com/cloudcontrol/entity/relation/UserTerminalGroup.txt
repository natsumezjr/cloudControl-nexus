package com.cloudcontrol.entity.relation;

import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.entity.terminal.TerminalGroup;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.cloudcontrol.enums.user.UserRole;

/**
 * 用户终端组关联实体类
 * 用于管理用户与终端组的多对多关系（权限分配）
 */
@Entity
@Table(name = "user_terminal_groups")
public class UserTerminalGroup {
    
    @EmbeddedId
    private UserTerminalGroupId id;
    
    /**
     * 用户实体 - 多对一关联
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * 终端组实体 - 多对一关联
     */
    @ManyToOne
    @MapsId("terminalGroupId")
    @JoinColumn(name = "terminal_group_id", nullable = false)
    private TerminalGroup terminalGroup;
    
    /**
     * 权限类型 - 引用UserRole
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", length = 20)
    private UserRole permissionType;
    
    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 创建者ID
     */
    @Column(name = "created_by")
    private Long createdBy;
    
    /**
     * 备注信息
     */
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    // 构造方法
    public UserTerminalGroup() {}
    
    public UserTerminalGroup(User user, TerminalGroup terminalGroup) {
        this.user = user;
        this.terminalGroup = terminalGroup;
        this.id = new UserTerminalGroupId(user.getId(), terminalGroup.getId());
    }
    
    public UserTerminalGroup(User user, TerminalGroup terminalGroup, UserRole permissionType) {
        this.user = user;
        this.terminalGroup = terminalGroup;
        this.permissionType = permissionType;
        this.id = new UserTerminalGroupId(user.getId(), terminalGroup.getId());
    }
    
    // Getter和Setter方法
    public UserTerminalGroupId getId() {
        return id;
    }
    
    public void setId(UserTerminalGroupId id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public TerminalGroup getTerminalGroup() {
        return terminalGroup;
    }
    
    public void setTerminalGroup(TerminalGroup terminalGroup) {
        this.terminalGroup = terminalGroup;
    }
    
    public UserRole getPermissionType() {
        return permissionType;
    }
    
    public void setPermissionType(UserRole permissionType) {
        this.permissionType = permissionType;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

 