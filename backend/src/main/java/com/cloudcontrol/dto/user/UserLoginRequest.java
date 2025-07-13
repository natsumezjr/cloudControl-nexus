package com.cloudcontrol.dto.user;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录请求DTO
 * 对应接口：POST /wp-login.php
 */
public class UserLoginRequest {
    
    /**
     * 用户名 - 必填
     */
    @NotBlank(message = "用户名不能为空")
    private String log;
    
    /**
     * 用户密码 - 必填
     */
    @NotBlank(message = "密码不能为空")
    private String pwd;
    
    // 构造方法
    public UserLoginRequest() {}
    
    public UserLoginRequest(String log, String pwd) {
        this.log = log;
        this.pwd = pwd;
    }
    
    // Getter和Setter方法
    public String getLog() {
        return log;
    }
    
    public void setLog(String log) {
        this.log = log;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
} 