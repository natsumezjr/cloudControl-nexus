package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 终端轨迹查询响应DTO
 */
public class TerminalTrackResponse {
    
    private Integer terminalId;
    private String terminalName;
    private String startTime;
    private String endTime;
    private List<TrackData> data;
    
    public TerminalTrackResponse() {}
    
    public TerminalTrackResponse(Integer terminalId, String terminalName, String startTime, 
                               String endTime, List<TrackData> data) {
        this.terminalId = terminalId;
        this.terminalName = terminalName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.data = data;
    }
    
    // getter/setter
    public Integer getTerminalId() { return terminalId; }
    public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
    
    public String getTerminalName() { return terminalName; }
    public void setTerminalName(String terminalName) { this.terminalName = terminalName; }
    
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    
    public List<TrackData> getData() { return data; }
    public void setData(List<TrackData> data) { this.data = data; }
    
    /**
     * 轨迹数据内部类
     */
    public static class TrackData {
        private Float longitude;
        private Float latitude;
        private String serverTime;
        private String clientTime;
        
        public TrackData() {}
        
        public TrackData(Float longitude, Float latitude, String serverTime, String clientTime) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.serverTime = serverTime;
            this.clientTime = clientTime;
        }
        
        // getter/setter
        public Float getLongitude() { return longitude; }
        public void setLongitude(Float longitude) { this.longitude = longitude; }
        
        public Float getLatitude() { return latitude; }
        public void setLatitude(Float latitude) { this.latitude = latitude; }
        
        public String getServerTime() { return serverTime; }
        public void setServerTime(String serverTime) { this.serverTime = serverTime; }
        
        public String getClientTime() { return clientTime; }
        public void setClientTime(String clientTime) { this.clientTime = clientTime; }
    }
} 