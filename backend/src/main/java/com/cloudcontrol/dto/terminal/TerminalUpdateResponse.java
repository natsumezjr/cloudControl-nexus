package com.cloudcontrol.dto.terminal;

/**
 * 修改终端信息响应DTO
 */
public class TerminalUpdateResponse {
    
    private String code;
    private String message;
    private Object data;
    
    public TerminalUpdateResponse() {}
    
    public TerminalUpdateResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    // getter/setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
} 