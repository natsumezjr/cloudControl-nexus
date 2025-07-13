package com.cloudcontrol.enums.command;

/**
 * 指令状态枚举
 */
public enum CommandStatus {
    PENDING("待执行"),
    EXECUTING("执行中"),
    SUCCESS("执行成功"),
    FAILED("执行失败"),
    CANCELLED("已取消");
    
    private final String description;
    
    CommandStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}