package com.cloudcontrol.dto.user;

/**
 * 用户注册响应DTO
 * 对应接口：POST /wp-json/wp/v2/users 的成功响应
 */
public class UserRegisterResponse {
    
    /**
     * 创建的用户ID
     */
    private Integer id;
    
    /**
     * 返回结果，固定为"success"
     */
    private String result;
    
    /**
     * 返回码，固定为"200"
     */
    private String code;
    
    // 构造方法
    public UserRegisterResponse() {}
    
    public UserRegisterResponse(Integer id) {
        this.id = id;
        this.result = "success";
        this.code = "200";
    }
    
    public UserRegisterResponse(Integer id, String result, String code) {
        this.id = id;
        this.result = result;
        this.code = code;
    }
    
    // Getter和Setter方法
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
} 