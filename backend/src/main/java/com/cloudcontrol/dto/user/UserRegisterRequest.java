package com.cloudcontrol.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 用户注册请求DTO
 * 对应接口：POST /wp-json/wp/v2/users
 */
public class UserRegisterRequest {
    
    /**
     * 注册邮箱 - 必填
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    /**
     * 用户名 - 必填
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    private String username;
    
    /**
     * 用户密码 - 必填
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须在8-20个字符之间")
    private String password;
    
    /**
     * 昵称 - 可选
     */
    private String nickname;
    
    /**
     * 用户绑定到的终端组ID - 必填
     */
    @NotNull(message = "终端组ID不能为空")
    private Integer termId;
    
    /**
     * 注册用户的角色列表 - 必填
     */
    @NotNull(message = "用户角色不能为空")
    private List<String> roles;
    
    /**
     * 注册用户分配的空间，单位是字节数 - 必填
     */
    @NotNull(message = "分配空间不能为空")
    private Long assignedSize;
    
    // 构造方法
    public UserRegisterRequest() {}
    
    public UserRegisterRequest(String email, String username, String password, String nickname, 
                             Integer termId, List<String> roles, Long assignedSize) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.termId = termId;
        this.roles = roles;
        this.assignedSize = assignedSize;
    }
    
    // Getter和Setter方法
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public Long getAssignedSize() {
        return assignedSize;
    }
    
    public void setAssignedSize(Long assignedSize) {
        this.assignedSize = assignedSize;
    }
} 