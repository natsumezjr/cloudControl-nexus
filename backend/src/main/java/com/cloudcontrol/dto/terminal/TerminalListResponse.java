package com.cloudcontrol.dto.terminal;

/**
 * 终端列表响应DTO
 */
public class TerminalListResponse {
    
    private String date;
    private Integer author;
    private ExtraInfo extra;
    private TitleInfo title;
    private PostMeta post_meta;
    private Integer id;
    private String date_gmt;
    private String status;
    private Integer total;
    private java.util.List<TerminalInfo> data;
    
    public TerminalListResponse() {}
    
    public TerminalListResponse(String date, Integer author, ExtraInfo extra, TitleInfo title, 
                               PostMeta post_meta, Integer id, String date_gmt, String status) {
        this.date = date;
        this.author = author;
        this.extra = extra;
        this.title = title;
        this.post_meta = post_meta;
        this.id = id;
        this.date_gmt = date_gmt;
        this.status = status;
    }
    
    public TerminalListResponse(java.util.List<TerminalInfo> data, String status) {
        this.data = data;
        this.status = status;
    }
    
    // getter/setter
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public ExtraInfo getExtra() { return extra; }
    public void setExtra(ExtraInfo extra) { this.extra = extra; }
    
    public TitleInfo getTitle() { return title; }
    public void setTitle(TitleInfo title) { this.title = title; }
    
    public PostMeta getPost_meta() { return post_meta; }
    public void setPost_meta(PostMeta post_meta) { this.post_meta = post_meta; }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getDate_gmt() { return date_gmt; }
    public void setDate_gmt(String date_gmt) { this.date_gmt = date_gmt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
    
    public java.util.List<TerminalInfo> getData() { return data; }
    public void setData(java.util.List<TerminalInfo> data) { this.data = data; }
    
    /**
     * 额外信息
     */
    public static class ExtraInfo {
        private String author_display_name;
        
        public ExtraInfo() {}
        
        public ExtraInfo(String author_display_name) {
            this.author_display_name = author_display_name;
        }
        
        // getter/setter
        public String getAuthor_display_name() { return author_display_name; }
        public void setAuthor_display_name(String author_display_name) { this.author_display_name = author_display_name; }
        
        public String getAuthorDisplayName() { return author_display_name; }
        public void setAuthorDisplayName(String authorDisplayName) { this.author_display_name = authorDisplayName; }
    }
    
    /**
     * 标题信息
     */
    public static class TitleInfo {
        private String rendered;
        private String raw;
        
        public TitleInfo() {}
        
        public TitleInfo(String rendered) {
            this.rendered = rendered;
        }
        
        // getter/setter
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
        
        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }
    }
    
    /**
     * 文章元数据
     */
    public static class PostMeta {
        private TerminalStatus _led_status;
        
        public PostMeta() {}
        
        public PostMeta(TerminalStatus _led_status) {
            this._led_status = _led_status;
        }
        
        // getter/setter
        public TerminalStatus get_led_status() { return _led_status; }
        public void set_led_status(TerminalStatus _led_status) { this._led_status = _led_status; }
    }
    
    /**
     * 终端信息
     */
    public static class TerminalInfo {
        private Integer id;
        private String name;
        private String description;
        private TerminalGroupInfo terminalGroup;
        private String status;
        private String lastHeartbeat;
        private GeoCoordinateInfo geoCoordinate;
        private LedStatusInfo ledStatus;
        private Integer author;
        private String date;
        private String dateGmt;
        private TitleInfo title;
        private ExtraInfo extra;
        private PostMetaInfo postMeta;
        
        public TerminalInfo() {}
        
        public TerminalInfo(Integer id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
        
        // getter/setter
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public TerminalGroupInfo getTerminalGroup() { return terminalGroup; }
        public void setTerminalGroup(TerminalGroupInfo terminalGroup) { this.terminalGroup = terminalGroup; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getLastHeartbeat() { return lastHeartbeat; }
        public void setLastHeartbeat(String lastHeartbeat) { this.lastHeartbeat = lastHeartbeat; }
        
        public GeoCoordinateInfo getGeoCoordinate() { return geoCoordinate; }
        public void setGeoCoordinate(GeoCoordinateInfo geoCoordinate) { this.geoCoordinate = geoCoordinate; }
        
        public LedStatusInfo getLedStatus() { return ledStatus; }
        public void setLedStatus(LedStatusInfo ledStatus) { this.ledStatus = ledStatus; }
        
        public Integer getAuthor() { return author; }
        public void setAuthor(Integer author) { this.author = author; }
        
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        
        public String getDateGmt() { return dateGmt; }
        public void setDateGmt(String dateGmt) { this.dateGmt = dateGmt; }
        
        public TitleInfo getTitle() { return title; }
        public void setTitle(TitleInfo title) { this.title = title; }
        
        public ExtraInfo getExtra() { return extra; }
        public void setExtra(ExtraInfo extra) { this.extra = extra; }
        
        public PostMetaInfo getPostMeta() { return postMeta; }
        public void setPostMeta(PostMetaInfo postMeta) { this.postMeta = postMeta; }
    }
    
    /**
     * 终端组信息
     */
    public static class TerminalGroupInfo {
        private Integer id;
        private String name;
        private String description;
        
        public TerminalGroupInfo() {}
        
        public TerminalGroupInfo(Integer id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
        
        // getter/setter
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    /**
     * 文章元数据信息
     */
    public static class PostMetaInfo {
        private String key;
        private String value;
        private GeoCoordinateInfo geoCoordinate;
        private LedStatusInfo ledStatus;
        private Object updateStatus;
        private DownloadStatusInfo downloadStatus;
        
        public PostMetaInfo() {}
        
        public PostMetaInfo(String key, String value) {
            this.key = key;
            this.value = value;
        }
        
        // getter/setter
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        
        public GeoCoordinateInfo getGeoCoordinate() { return geoCoordinate; }
        public void setGeoCoordinate(GeoCoordinateInfo geoCoordinate) { this.geoCoordinate = geoCoordinate; }
        
        public LedStatusInfo getLedStatus() { return ledStatus; }
        public void setLedStatus(LedStatusInfo ledStatus) { this.ledStatus = ledStatus; }
        
        public Object getUpdateStatus() { return updateStatus; }
        public void setUpdateStatus(Object updateStatus) { this.updateStatus = updateStatus; }
        
        public DownloadStatusInfo getDownloadStatus() { return downloadStatus; }
        public void setDownloadStatus(DownloadStatusInfo downloadStatus) { this.downloadStatus = downloadStatus; }
    }
    
    /**
     * 地理坐标信息
     */
    public static class GeoCoordinateInfo {
        private Double latitude;
        private Double longitude;
        
        public GeoCoordinateInfo() {}
        
        public GeoCoordinateInfo(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        // getter/setter
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
    }
    
    /**
     * LED状态信息
     */
    public static class LedStatusInfo {
        private String status;
        private String lastUpdate;
        private Object terminal;
        private Object powerStatus;
        private Object info;
        private Object vsns;
        private Object dimension;
        private Object volume;
        private Object inputMode;
        private Object brightnessAndColorTemp;
        private Object reportTime;
        private Object reportSwitch;
        private Object newRtc;
        private Object inboundFirewall;
        private Object webSocketStatus;
        private Object allBrightnessInfo;
        private Object brightCurve;
        private Object brightnessVersion;
        private Object cameraConfig;
        private Object contentReport;
        private Object contentReportInterval;
        private Object ifStatus;
        private Object locale;
        private Object rtc;
        private Object syncProgramMode;
        
        public LedStatusInfo() {}
        
        public LedStatusInfo(String status, String lastUpdate) {
            this.status = status;
            this.lastUpdate = lastUpdate;
        }
        
        // getter/setter
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getLastUpdate() { return lastUpdate; }
        public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }
        
        public Object getTerminal() { return terminal; }
        public void setTerminal(Object terminal) { this.terminal = terminal; }
        
        public Object getPowerStatus() { return powerStatus; }
        public void setPowerStatus(Object powerStatus) { this.powerStatus = powerStatus; }
        
        public Object getInfo() { return info; }
        public void setInfo(Object info) { this.info = info; }
        
        public Object getVsns() { return vsns; }
        public void setVsns(Object vsns) { this.vsns = vsns; }
        
        public Object getDimension() { return dimension; }
        public void setDimension(Object dimension) { this.dimension = dimension; }
        
        public Object getVolume() { return volume; }
        public void setVolume(Object volume) { this.volume = volume; }
        
        public Object getInputMode() { return inputMode; }
        public void setInputMode(Object inputMode) { this.inputMode = inputMode; }
        
        public Object getBrightnessAndColorTemp() { return brightnessAndColorTemp; }
        public void setBrightnessAndColorTemp(Object brightnessAndColorTemp) { this.brightnessAndColorTemp = brightnessAndColorTemp; }
        
        public Object getReportTime() { return reportTime; }
        public void setReportTime(Object reportTime) { this.reportTime = reportTime; }
        
        public Object getReportSwitch() { return reportSwitch; }
        public void setReportSwitch(Object reportSwitch) { this.reportSwitch = reportSwitch; }
        
        public Object getNewRtc() { return newRtc; }
        public void setNewRtc(Object newRtc) { this.newRtc = newRtc; }
        
        public Object getInboundFirewall() { return inboundFirewall; }
        public void setInboundFirewall(Object inboundFirewall) { this.inboundFirewall = inboundFirewall; }
        
        public Object getWebSocketStatus() { return webSocketStatus; }
        public void setWebSocketStatus(Object webSocketStatus) { this.webSocketStatus = webSocketStatus; }
        
        public Object getAllBrightnessInfo() { return allBrightnessInfo; }
        public void setAllBrightnessInfo(Object allBrightnessInfo) { this.allBrightnessInfo = allBrightnessInfo; }
        
        public Object getBrightCurve() { return brightCurve; }
        public void setBrightCurve(Object brightCurve) { this.brightCurve = brightCurve; }
        
        public Object getBrightnessVersion() { return brightnessVersion; }
        public void setBrightnessVersion(Object brightnessVersion) { this.brightnessVersion = brightnessVersion; }
        
        public Object getCameraConfig() { return cameraConfig; }
        public void setCameraConfig(Object cameraConfig) { this.cameraConfig = cameraConfig; }
        
        public Object getContentReport() { return contentReport; }
        public void setContentReport(Object contentReport) { this.contentReport = contentReport; }
        
        public Object getContentReportInterval() { return contentReportInterval; }
        public void setContentReportInterval(Object contentReportInterval) { this.contentReportInterval = contentReportInterval; }
        
        public Object getIfStatus() { return ifStatus; }
        public void setIfStatus(Object ifStatus) { this.ifStatus = ifStatus; }
        
        public Object getLocale() { return locale; }
        public void setLocale(Object locale) { this.locale = locale; }
        
        public Object getRtc() { return rtc; }
        public void setRtc(Object rtc) { this.rtc = rtc; }
        
        public Object getSyncProgramMode() { return syncProgramMode; }
        public void setSyncProgramMode(Object syncProgramMode) { this.syncProgramMode = syncProgramMode; }
    }

    /**
     * 下载状态信息
     */
    public static class DownloadStatusInfo {
        private String status;
        private String progress;
        
        public DownloadStatusInfo() {}
        
        public DownloadStatusInfo(String status, String progress) {
            this.status = status;
            this.progress = progress;
        }
        
        // getter/setter
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getProgress() { return progress; }
        public void setProgress(String progress) { this.progress = progress; }
    }
} 