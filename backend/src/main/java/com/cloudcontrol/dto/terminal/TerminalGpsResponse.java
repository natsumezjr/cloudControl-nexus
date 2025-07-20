package com.cloudcontrol.dto.terminal;

/**
 * GPS终端列表响应DTO
 */
public class TerminalGpsResponse {
    
    private String terminalgroupId;
    private String name;
    private String last_report_time;
    private String _led_latest_report_time;
    private Integer id;
    private Position position;
    
    public TerminalGpsResponse() {}
    
    public TerminalGpsResponse(String terminalgroupId, String name, String last_report_time, 
                             String _led_latest_report_time, Integer id, Position position) {
        this.terminalgroupId = terminalgroupId;
        this.name = name;
        this.last_report_time = last_report_time;
        this._led_latest_report_time = _led_latest_report_time;
        this.id = id;
        this.position = position;
    }
    
    // getter/setter
    public String getTerminalgroupId() { return terminalgroupId; }
    public void setTerminalgroupId(String terminalgroupId) { this.terminalgroupId = terminalgroupId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLast_report_time() { return last_report_time; }
    public void setLast_report_time(String last_report_time) { this.last_report_time = last_report_time; }
    
    public String get_led_latest_report_time() { return _led_latest_report_time; }
    public void set_led_latest_report_time(String _led_latest_report_time) { this._led_latest_report_time = _led_latest_report_time; }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    
    /**
     * 位置信息内部类
     */
    public static class Position {
        private Integer ledId;
        private Float latitude;
        private Integer id;
        private Integer sensorId;
        private Float longitude;
        
        public Position() {}
        
        public Position(Integer ledId, Float latitude, Integer id, Integer sensorId, Float longitude) {
            this.ledId = ledId;
            this.latitude = latitude;
            this.id = id;
            this.sensorId = sensorId;
            this.longitude = longitude;
        }
        
        // getter/setter
        public Integer getLedId() { return ledId; }
        public void setLedId(Integer ledId) { this.ledId = ledId; }
        
        public Float getLatitude() { return latitude; }
        public void setLatitude(Float latitude) { this.latitude = latitude; }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public Integer getSensorId() { return sensorId; }
        public void setSensorId(Integer sensorId) { this.sensorId = sensorId; }
        
        public Float getLongitude() { return longitude; }
        public void setLongitude(Float longitude) { this.longitude = longitude; }
    }
} 