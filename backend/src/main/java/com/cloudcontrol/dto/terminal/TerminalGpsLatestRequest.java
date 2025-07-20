package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 终端GPS最新信息请求DTO
 */
public class TerminalGpsLatestRequest {
    
    @JsonProperty("terminalId")
    private Integer terminalId;
    
    @JsonProperty("deviceNum")
    private String deviceNum;
    
    public TerminalGpsLatestRequest() {}
    
    public TerminalGpsLatestRequest(Integer terminalId, String deviceNum) {
        this.terminalId = terminalId;
        this.deviceNum = deviceNum;
    }
    
    // getter/setter
    public Integer getTerminalId() { return terminalId; }
    public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
    
    public String getDeviceNum() { return deviceNum; }
    public void setDeviceNum(String deviceNum) { this.deviceNum = deviceNum; }
} 