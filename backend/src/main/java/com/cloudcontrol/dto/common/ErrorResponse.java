package com.cloudcontrol.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 统一错误响应DTO
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
public class ErrorResponse {
    
    @JsonProperty("error")
    private String error;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("timestamp")
    private long timestamp;
    
    @JsonProperty("path")
    private String path;
    
    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public ErrorResponse(String error, String message) {
        this();
        this.error = error;
        this.message = message;
    }
    
    public ErrorResponse(String error, String message, String path) {
        this(error, message);
        this.path = path;
    }
    
    // Getters and Setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
