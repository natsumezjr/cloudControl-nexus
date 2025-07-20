package com.cloudcontrol.dto.terminal;

/**
 * 查询终端轨迹请求DTO
 */
public class TerminalTrackRequest {
    
    private String terminalId;
    private String startTime;
    private String endTime;
    
    public TerminalTrackRequest() {}
    
    public TerminalTrackRequest(String terminalId, String startTime, String endTime) {
        this.terminalId = terminalId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    // getter/setter
    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }
    
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
} 