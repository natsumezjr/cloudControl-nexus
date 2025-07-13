package com.cloudcontrol.dto.user;

import java.util.List;

/**
 * 用户登录失败响应DTO
 * 对应接口：POST /wp-login.php 的异常响应
 */
public class UserLoginErrorResponse {
    
    /**
     * 错误消息，固定为"Bad credentials"
     */
    private String msg;
    
    /**
     * 错误数据对象
     */
    private ErrorData data;
    
    /**
     * 状态码，401表示认证失败
     */
    private Integer status;
    
    // 构造方法
    public UserLoginErrorResponse() {}
    
    public UserLoginErrorResponse(String msg, ErrorData data, Integer status) {
        this.msg = msg;
        this.data = data;
        this.status = status;
    }
    
    // Getter和Setter方法
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public ErrorData getData() {
        return data;
    }
    
    public void setData(ErrorData data) {
        this.data = data;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 错误数据对象
     */
    public static class ErrorData {
        /**
         * 剩余可尝试登录次数
         */
        private Integer numberOfRemainingAttempts;
        
        /**
         * 错误对象
         */
        private ErrorInfo errors;
        
        public ErrorData() {}
        
        public ErrorData(Integer numberOfRemainingAttempts, ErrorInfo errors) {
            this.numberOfRemainingAttempts = numberOfRemainingAttempts;
            this.errors = errors;
        }
        
        public Integer getNumberOfRemainingAttempts() {
            return numberOfRemainingAttempts;
        }
        
        public void setNumberOfRemainingAttempts(Integer numberOfRemainingAttempts) {
            this.numberOfRemainingAttempts = numberOfRemainingAttempts;
        }
        
        public ErrorInfo getErrors() {
            return errors;
        }
        
        public void setErrors(ErrorInfo errors) {
            this.errors = errors;
        }
    }
    
    /**
     * 错误信息对象
     */
    public static class ErrorInfo {
        /**
         * 错误信息数组
         */
        private List<String> wrong;
        
        public ErrorInfo() {}
        
        public ErrorInfo(List<String> wrong) {
            this.wrong = wrong;
        }
        
        public List<String> getWrong() {
            return wrong;
        }
        
        public void setWrong(List<String> wrong) {
            this.wrong = wrong;
        }
    }
} 