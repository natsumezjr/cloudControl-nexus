package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 简化应用排程响应DTO
 */
public class SimplifiedScheduleResponse {
    
    @JsonProperty("status")
    private Integer status;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("data")
    private Object data;
    
    // 构造函数
    public SimplifiedScheduleResponse() {}
    
    public SimplifiedScheduleResponse(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    // Getter和Setter方法
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
} 