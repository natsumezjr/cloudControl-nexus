package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * 终端注册请求
 * 使用@JsonIgnoreProperties忽略未知字段，避免JSON解析错误
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TerminalRegisterRequest {
    
    @JsonProperty("name")
    @NotBlank(message = "账号名称不能为空")
    private String accountName; // 终端账号（前端字段为name，后端字段为accountName）
    
    @NotBlank(message = "密码不能为空")
    private String password;    // 终端密码
    
    private String url;         // 云平台地址（可选）
    
    @JsonProperty("devicestatus")
    private String status;      // 终端状态（如 sleep，可选）
    
    private boolean internet = true;   // 是否联网（默认值：true）
    
    @JsonProperty("deviceName")
    @JsonSetter(nulls = Nulls.SKIP)
    private String deviceName = generateDefaultDeviceName();  // 设备名称（可选，为空时自动生成UUID）

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
    public void setDeviceName(String deviceName) { 
        System.out.println("DTO setDeviceName被调用，传入值: " + deviceName);
        // 如果传入的值为null或空字符串，使用默认值
        this.deviceName = (deviceName == null || deviceName.trim().isEmpty()) ? 
                         generateDefaultDeviceName() : deviceName; 
        System.out.println("DTO setDeviceName设置后的值: " + this.deviceName);
    }
    
    /**
     * 生成默认的设备名称
     */
    private static String generateDefaultDeviceName() {
        String shortUuid = UUID.randomUUID().toString().substring(0, 8);
        String generatedName = "CLCA_DEFAULT_" + shortUuid;
        System.out.println("DTO generateDefaultDeviceName生成: " + generatedName);
        return generatedName;
    }
    
} 