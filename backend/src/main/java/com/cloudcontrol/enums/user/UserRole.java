package com.cloudcontrol.enums.user;

/**
 * 用户角色枚举
 * 根据播控系统接口文档定义
 */
public enum UserRole {
    MANAGER("管理员"),
    EDITOR("编辑者"),
    OPERATOR("操作员"),
    CONTRIBUTOR("节目制作员"),
    AUDITOR("审核员"),
    SUBSCRIBER("订阅者"),
    SUPERVISOR("监控者"),
    INVITEE("受邀人"),
    GUEST("未知角色"),
    MONITOR("监控者"),
    STATISTICIAN("统计员");
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}