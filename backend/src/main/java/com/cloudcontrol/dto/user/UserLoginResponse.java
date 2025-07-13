package com.cloudcontrol.dto.user;

import java.util.List;

/**
 * 用户登录成功响应DTO
 * 对应接口：POST /wp-login.php 的成功响应
 */
public class UserLoginResponse {
    
    /**
     * 返回结果，固定为"success"
     */
    private String result;
    
    /**
     * 登录的用户对象信息
     */
    private UserData user;
    
    // 构造方法
    public UserLoginResponse() {}
    
    public UserLoginResponse(String result, UserData user) {
        this.result = result;
        this.user = user;
    }
    
    // Getter和Setter方法
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public UserData getUser() {
        return user;
    }
    
    public void setUser(UserData user) {
        this.user = user;
    }
    
    /**
     * 用户数据对象
     */
    public static class UserData {
        private UserInfo data;
        private Integer ID;
        
        public UserData() {}
        
        public UserData(UserInfo data, Integer ID) {
            this.data = data;
            this.ID = ID;
        }
        
        public UserInfo getData() {
            return data;
        }
        
        public void setData(UserInfo data) {
            this.data = data;
        }
        
        public Integer getID() {
            return ID;
        }
        
        public void setID(Integer ID) {
            this.ID = ID;
        }
    }
    
    /**
     * 用户详细信息
     */
    public static class UserInfo {
        private String role;
        private Integer userStatus;
        private List<String> roles;
        private String password;
        private Integer enabled2FA;
        private Integer loginTime;
        private Integer id;
        private String email;
        private List<Object> authorities;
        private Integer terminalgroup_id;
        private String mainRole;
        private String name;
        private String registeredFormatTime;
        private Integer boundToMobile;
        
        public UserInfo() {}
        
        // Getter和Setter方法
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        public Integer getUserStatus() {
            return userStatus;
        }
        
        public void setUserStatus(Integer userStatus) {
            this.userStatus = userStatus;
        }
        
        public List<String> getRoles() {
            return roles;
        }
        
        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
        
        public Integer getEnabled2FA() {
            return enabled2FA;
        }
        
        public void setEnabled2FA(Integer enabled2FA) {
            this.enabled2FA = enabled2FA;
        }
        
        public Integer getLoginTime() {
            return loginTime;
        }
        
        public void setLoginTime(Integer loginTime) {
            this.loginTime = loginTime;
        }
        
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public List<Object> getAuthorities() {
            return authorities;
        }
        
        public void setAuthorities(List<Object> authorities) {
            this.authorities = authorities;
        }
        
        public Integer getTerminalgroup_id() {
            return terminalgroup_id;
        }
        
        public void setTerminalgroup_id(Integer terminalgroup_id) {
            this.terminalgroup_id = terminalgroup_id;
        }
        
        public String getMainRole() {
            return mainRole;
        }
        
        public void setMainRole(String mainRole) {
            this.mainRole = mainRole;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getRegisteredFormatTime() {
            return registeredFormatTime;
        }
        
        public void setRegisteredFormatTime(String registeredFormatTime) {
            this.registeredFormatTime = registeredFormatTime;
        }
        
        public Integer getBoundToMobile() {
            return boundToMobile;
        }
        
        public void setBoundToMobile(Integer boundToMobile) {
            this.boundToMobile = boundToMobile;
        }
    }
} 