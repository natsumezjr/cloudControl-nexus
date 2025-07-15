package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 终端注册请求
 */
public class TerminalRegisterRequest {
    
    @JsonProperty("name")
    private String accountName; // 终端账号（前端字段为name，后端字段为accountName）
    
    private String password;    // 终端密码
    private String url;         // 云平台地址
    @JsonProperty("devicestatus")
    private String status;      // 终端状态（如 sleep）
    private boolean internet;   // 是否联网
    @JsonProperty("deviceName")
    private String deviceName;  // 设备名称（注册时必填）

    // Getters and Setters
    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public boolean isInternet() { return internet; }
    public void setInternet(boolean internet) { this.internet = internet; }
    
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
} 