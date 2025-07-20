package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 创建终端响应DTO
 */
public class TerminalCreateResponse {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("message")
    private String message;
    
    public TerminalCreateResponse() {}
    
    public TerminalCreateResponse(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
} 