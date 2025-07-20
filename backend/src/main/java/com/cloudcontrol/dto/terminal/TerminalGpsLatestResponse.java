package com.cloudcontrol.dto.terminal;

/**
 * 终端最新GPS信息响应DTO (v3)
 */
public class TerminalGpsLatestResponse {
    
    private Integer terminalId;
    private String terminalName;
    private String reportTime;
    private String serverTime;
    private String clientTime;
    private Integer sensorId;
    private Float longitude;
    private Float latitude;
    private Float accuracy;
    private Float altitude;
    private Float speed;
    private Float direct;
    private Integer satellites;
    private Object cellInfo;
    private Object gsv;
    private Object manual;
    private String latestReportTime;
    
    public TerminalGpsLatestResponse() {}
    
    public TerminalGpsLatestResponse(Integer terminalId, String terminalName, String reportTime, 
                                   String serverTime, String clientTime, Integer sensorId,
                                   Float longitude, Float latitude, Float accuracy, Float altitude,
                                   Float speed, Float direct, Integer satellites, Object cellInfo,
                                   Object gsv, Object manual, String latestReportTime) {
        this.terminalId = terminalId;
        this.terminalName = terminalName;
        this.reportTime = reportTime;
        this.serverTime = serverTime;
        this.clientTime = clientTime;
        this.sensorId = sensorId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.speed = speed;
        this.direct = direct;
        this.satellites = satellites;
        this.cellInfo = cellInfo;
        this.gsv = gsv;
        this.manual = manual;
        this.latestReportTime = latestReportTime;
    }
    
    // getter/setter
    public Integer getTerminalId() { return terminalId; }
    public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
    
    public String getTerminalName() { return terminalName; }
    public void setTerminalName(String terminalName) { this.terminalName = terminalName; }
    
    public String getReportTime() { return reportTime; }
    public void setReportTime(String reportTime) { this.reportTime = reportTime; }
    
    public String getServerTime() { return serverTime; }
    public void setServerTime(String serverTime) { this.serverTime = serverTime; }
    
    public String getClientTime() { return clientTime; }
    public void setClientTime(String clientTime) { this.clientTime = clientTime; }
    
    public Integer getSensorId() { return sensorId; }
    public void setSensorId(Integer sensorId) { this.sensorId = sensorId; }
    
    public Float getLongitude() { return longitude; }
    public void setLongitude(Float longitude) { this.longitude = longitude; }
    
    public Float getLatitude() { return latitude; }
    public void setLatitude(Float latitude) { this.latitude = latitude; }
    
    public Float getAccuracy() { return accuracy; }
    public void setAccuracy(Float accuracy) { this.accuracy = accuracy; }
    
    public Float getAltitude() { return altitude; }
    public void setAltitude(Float altitude) { this.altitude = altitude; }
    
    public Float getSpeed() { return speed; }
    public void setSpeed(Float speed) { this.speed = speed; }
    
    public Float getDirect() { return direct; }
    public void setDirect(Float direct) { this.direct = direct; }
    
    public Integer getSatellites() { return satellites; }
    public void setSatellites(Integer satellites) { this.satellites = satellites; }
    
    public Object getCellInfo() { return cellInfo; }
    public void setCellInfo(Object cellInfo) { this.cellInfo = cellInfo; }
    
    public Object getGsv() { return gsv; }
    public void setGsv(Object gsv) { this.gsv = gsv; }
    
    public Object getManual() { return manual; }
    public void setManual(Object manual) { this.manual = manual; }
    
    public String getLatestReportTime() { return latestReportTime; }
    public void setLatestReportTime(String latestReportTime) { this.latestReportTime = latestReportTime; }
} 