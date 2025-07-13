package com.cloudcontrol.enums.user;

/**
 * 用户状态枚举
 * 根据播控系统接口文档定义
 */
public enum UserStatus {
    ACTIVE(0, "激活"),
    BANNED(1, "封禁"),
    TRASH(2, "回收站");
    
    private final Integer code;
    private final String description;
    
    UserStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据状态码获取枚举
     */
    public static UserStatus fromCode(Integer code) {
        for (UserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
