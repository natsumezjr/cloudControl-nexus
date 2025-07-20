package com.cloudcontrol.dto.program;

/**
 * 删除播放盒节目错误响应DTO
 */
public class ProgramUnpublishErrorResponse {
    
    private Integer code;
    private String message;
    private String data;
    
    public ProgramUnpublishErrorResponse() {}
    
    public ProgramUnpublishErrorResponse(Integer code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    // getter/setter
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
} 