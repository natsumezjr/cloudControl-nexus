package com.cloudcontrol.entity.terminal;

import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.terminal.TerminalStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 终端实体类
 * 用于管理播控终端设备的基本信息、状态和分组
 */
@Entity
@Table(name = "terminals")
public class Terminal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 终端名称
     */
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    /**
     * 终端状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TerminalStatus status;
    
    /**
     * 创建者ID - 外键关联
     */
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;
    
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime date;
    
    /**
     * 所属终端组ID - 外键关联
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    private TerminalGroup terminalGroup;
    
    /**
     * 终端IP地址
     */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    /**
     * 终端MAC地址
     */
    @Column(name = "mac_address", length = 17)
    private String macAddress;
    
    /**
     * 终端操作系统
     */
    @Column(name = "os", length = 50)
    private String os;
    
    /**
     * 终端版本
     */
    @Column(name = "version", length = 50)
    private String version;
    
    /**
     * 终端能力描述（JSON格式）
     */
    @Column(name = "capabilities", columnDefinition = "TEXT")
    private String capabilities;
    
    /**
     * 最后心跳时间
     */
    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;
    
    /**
     * 终端位置信息
     */
    @Column(name = "location", length = 200)
    private String location;
    
    /**
     * 终端描述
     */
    @Column(name = "description", length = 500)
    private String description;
    
    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 构造方法
    public Terminal() {}
    
    public Terminal(String title, User author, TerminalGroup terminalGroup) {
        this.title = title;
        this.author = author;
        this.terminalGroup = terminalGroup;
        this.status = TerminalStatus.PUBLISH; // 默认状态
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public TerminalStatus getStatus() {
        return status;
    }
    
    public void setStatus(TerminalStatus status) {
        this.status = status;
    }
    
    public User getAuthor() {
        return author;
    }
    
    public void setAuthor(User author) {
        this.author = author;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public TerminalGroup getTerminalGroup() {
        return terminalGroup;
    }
    
    public void setTerminalGroup(TerminalGroup terminalGroup) {
        this.terminalGroup = terminalGroup;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getMacAddress() {
        return macAddress;
    }
    
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    
    public String getOs() {
        return os;
    }
    
    public void setOs(String os) {
        this.os = os;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getCapabilities() {
        return capabilities;
    }
    
    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }
    
    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
    
    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
