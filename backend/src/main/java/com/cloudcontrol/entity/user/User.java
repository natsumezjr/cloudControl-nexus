package com.cloudcontrol.entity.user;

import com.cloudcontrol.enums.user.UserRole;
import com.cloudcontrol.enums.user.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * @author CloudControl-Nexus-Bupt zjr
 * @version 1.0.0
 * @since 2025-07-13
 */
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Email(message = "邮箱格式不正确")  // 会自动校验邮箱格式，如果格式不正确，会抛出异常
    private String email;

    /**
     * 昵称
     */
    @Column(name = "nickname", length = 100)
    private String nickname;

    /**
     * 用户角色
     */
    @Enumerated(EnumType.STRING) // 数据库中存储的是字符串，比如枚举值MANAGER
    private UserRole role;

    @Enumerated(EnumType.STRING) // 数据库中存储的是字符串，比如枚举值ACTIVE
    private UserStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * 用户绑定的终端组ID
     */
    @Column(name = "term_id")
    private Integer termId;

    /**
     * 用户分配的空间大小（字节）
     */
    @Column(name = "assigned_size")
    private Long assignedSize;

    // JPA和测试需要无参构造器
    public User() {
    }

    public User(String username, String password, String email, LocalDateTime createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

        public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public Long getAssignedSize() {
        return assignedSize;
    }

    public void setAssignedSize(Long assignedSize) {
        this.assignedSize = assignedSize;
    }
}