package com.cloudcontrol.exception;

/**
 * 终端认证异常
 * 用于拦截器中的认证失败情况
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
public class TerminalAuthenticationException extends RuntimeException {
    
    private final String errorCode;
    
    public TerminalAuthenticationException(String message) {
        super(message);
        this.errorCode = "UNAUTHORIZED";
    }
    
    public TerminalAuthenticationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public TerminalAuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNAUTHORIZED";
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
