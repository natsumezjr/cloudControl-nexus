package com.cloudcontrol.dto.program;

/**
 * 节目发布错误响应DTO
 */
public class ProgramPublishErrorResponse {
    
    private String msg;
    private Integer errorCode;
    private Integer httpCode;
    
    public ProgramPublishErrorResponse() {}
    
    public ProgramPublishErrorResponse(String msg, Integer errorCode, Integer httpCode) {
        this.msg = msg;
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }
    
    // getter/setter
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    
    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }
    
    public Integer getHttpCode() { return httpCode; }
    public void setHttpCode(Integer httpCode) { this.httpCode = httpCode; }
} 