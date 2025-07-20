package com.cloudcontrol.dto.terminal;

/**
 * 终端GPS信息响应DTO (v2兼容版本)
 */
public class TerminalGpsV2Response {
    
    private Integer id;
    private String name;
    private String description;
    private String lastReportTime;
    private LatestGps latestGps;
    private Object gsv;
    
    public TerminalGpsV2Response() {}
    
    public TerminalGpsV2Response(Integer id, String name, String description, 
                               String lastReportTime, LatestGps latestGps, Object gsv) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastReportTime = lastReportTime;
        this.latestGps = latestGps;
        this.gsv = gsv;
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLastReportTime() { return lastReportTime; }
    public void setLastReportTime(String lastReportTime) { this.lastReportTime = lastReportTime; }
    
    public LatestGps getLatestGps() { return latestGps; }
    public void setLatestGps(LatestGps latestGps) { this.latestGps = latestGps; }
    
    public Object getGsv() { return gsv; }
    public void setGsv(Object gsv) { this.gsv = gsv; }
    
    /**
     * 最新GPS信息内部类
     */
    public static class LatestGps {
        private LatestGpsCoordinate latestGpsCoordinate;
        private Long latestGpsReportTime;
        
        public LatestGps() {}
        
        public LatestGps(LatestGpsCoordinate latestGpsCoordinate, Long latestGpsReportTime) {
            this.latestGpsCoordinate = latestGpsCoordinate;
            this.latestGpsReportTime = latestGpsReportTime;
        }
        
        // getter/setter
        public LatestGpsCoordinate getLatestGpsCoordinate() { return latestGpsCoordinate; }
        public void setLatestGpsCoordinate(LatestGpsCoordinate latestGpsCoordinate) { this.latestGpsCoordinate = latestGpsCoordinate; }
        
        public Long getLatestGpsReportTime() { return latestGpsReportTime; }
        public void setLatestGpsReportTime(Long latestGpsReportTime) { this.latestGpsReportTime = latestGpsReportTime; }
    }
    
    /**
     * 最新GPS坐标信息内部类
     */
    public static class LatestGpsCoordinate {
        private Integer id;
        private Integer ledId;
        private Integer sensorId;
        private String sensorType;
        private Float longitude;
        private Float latitude;
        private Float accuracy;
        private Float altitude;
        private Float speed;
        private String date;
        private Float direct;
        private Integer satellites;
        private Object cellInfo;
        private Object gsv;
        private String serverTime;
        
        public LatestGpsCoordinate() {}
        
        public LatestGpsCoordinate(Integer id, Integer ledId, Integer sensorId, String sensorType,
                                 Float longitude, Float latitude, Float accuracy, Float altitude,
                                 Float speed, String date, Float direct, Integer satellites,
                                 Object cellInfo, Object gsv, String serverTime) {
            this.id = id;
            this.ledId = ledId;
            this.sensorId = sensorId;
            this.sensorType = sensorType;
            this.longitude = longitude;
            this.latitude = latitude;
            this.accuracy = accuracy;
            this.altitude = altitude;
            this.speed = speed;
            this.date = date;
            this.direct = direct;
            this.satellites = satellites;
            this.cellInfo = cellInfo;
            this.gsv = gsv;
            this.serverTime = serverTime;
        }
        
        // getter/setter
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public Integer getLedId() { return ledId; }
        public void setLedId(Integer ledId) { this.ledId = ledId; }
        
        public Integer getSensorId() { return sensorId; }
        public void setSensorId(Integer sensorId) { this.sensorId = sensorId; }
        
        public String getSensorType() { return sensorType; }
        public void setSensorType(String sensorType) { this.sensorType = sensorType; }
        
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
        
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        
        public Float getDirect() { return direct; }
        public void setDirect(Float direct) { this.direct = direct; }
        
        public Integer getSatellites() { return satellites; }
        public void setSatellites(Integer satellites) { this.satellites = satellites; }
        
        public Object getCellInfo() { return cellInfo; }
        public void setCellInfo(Object cellInfo) { this.cellInfo = cellInfo; }
        
        public Object getGsv() { return gsv; }
        public void setGsv(Object gsv) { this.gsv = gsv; }
        
        public String getServerTime() { return serverTime; }
        public void setServerTime(String serverTime) { this.serverTime = serverTime; }
    }
} 