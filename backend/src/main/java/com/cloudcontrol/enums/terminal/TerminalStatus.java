package com.cloudcontrol.enums.terminal;

/**
 * 终端状态枚举
 */
public enum TerminalStatus {
    PUBLISH("已发布"),
    DRAFT("草稿"),
    OFFLINE("离线"),
    ONLINE("在线"),
    MAINTENANCE("维护中"),
    ERROR("错误");
    
    private final String description;
    
    TerminalStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 