package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 获取终端里程数请求DTO
 */
public class TerminalMileageRequest {
    
    private String startTime;
    private String endTime;
    private List<Integer> terminalIds;
    
    public TerminalMileageRequest() {}
    
    public TerminalMileageRequest(String startTime, String endTime, List<Integer> terminalIds) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.terminalIds = terminalIds;
    }
    
    // getter/setter
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    
    public List<Integer> getTerminalIds() { return terminalIds; }
    public void setTerminalIds(List<Integer> terminalIds) { this.terminalIds = terminalIds; }
} 