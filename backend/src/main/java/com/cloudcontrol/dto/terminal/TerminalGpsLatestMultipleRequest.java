package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 终端GPS最新信息批量请求DTO
 */
public class TerminalGpsLatestMultipleRequest {
    
    @JsonProperty("terminalIds")
    private List<Integer> terminalIds;
    
    @JsonProperty("deviceNums")
    private List<String> deviceNums;
    
    public TerminalGpsLatestMultipleRequest() {}
    
    public TerminalGpsLatestMultipleRequest(List<Integer> terminalIds, List<String> deviceNums) {
        this.terminalIds = terminalIds;
        this.deviceNums = deviceNums;
    }
    
    // getter/setter
    public List<Integer> getTerminalIds() { return terminalIds; }
    public void setTerminalIds(List<Integer> terminalIds) { this.terminalIds = terminalIds; }
    
    public List<String> getDeviceNums() { return deviceNums; }
    public void setDeviceNums(List<String> deviceNums) { this.deviceNums = deviceNums; }
} 