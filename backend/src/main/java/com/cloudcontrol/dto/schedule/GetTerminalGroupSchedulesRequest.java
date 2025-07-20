package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/**
 * 获取终端组排程信息请求DTO
 */
public class GetTerminalGroupSchedulesRequest {
    
    @JsonProperty("terminalGroupIds")
    private Set<Integer> terminalGroupIds;
    
    // 构造函数
    public GetTerminalGroupSchedulesRequest() {}
    
    public GetTerminalGroupSchedulesRequest(Set<Integer> terminalGroupIds) {
        this.terminalGroupIds = terminalGroupIds;
    }
    
    // Getter和Setter方法
    public Set<Integer> getTerminalGroupIds() {
        return terminalGroupIds;
    }
    
    public void setTerminalGroupIds(Set<Integer> terminalGroupIds) {
        this.terminalGroupIds = terminalGroupIds;
    }
} 