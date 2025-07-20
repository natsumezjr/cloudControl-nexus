package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 简化创建节目请求DTO
 */
public class SimplifyCreateProgramRequest {
    
    private String programName;
    private Integer screenWidth;
    private Integer screenHeight;
    private List<RegionRequest> regions;
    
    // 单窗口类型参数
    private Integer durationMillis;
    private String url;
    private Integer mediaId;
    private Integer syncProgram;
    private Integer constrainedProportion;
    private Integer fontSize;
    private String lineText;
    private Integer lineTextType;
    private Integer clockType;
    private String regionName;
    private String endDateTime;
    private String prefix;
    private String suffix;
    private Integer environmentType;
    private Integer temperatureShowAsFahrenheit;
    private Integer temperatureOffset;
    private Integer smokeStandard;
    private String rssUrl;
    private Integer refreshIntervalMs;
    private Integer scrollSpeed;
    
    public SimplifyCreateProgramRequest() {}
    
    // getter/setter
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    
    public Integer getScreenWidth() { return screenWidth; }
    public void setScreenWidth(Integer screenWidth) { this.screenWidth = screenWidth; }
    
    public Integer getScreenHeight() { return screenHeight; }
    public void setScreenHeight(Integer screenHeight) { this.screenHeight = screenHeight; }
    
    public List<RegionRequest> getRegions() { return regions; }
    public void setRegions(List<RegionRequest> regions) { this.regions = regions; }
    
    public Integer getDurationMillis() { return durationMillis; }
    public void setDurationMillis(Integer durationMillis) { this.durationMillis = durationMillis; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Integer getMediaId() { return mediaId; }
    public void setMediaId(Integer mediaId) { this.mediaId = mediaId; }
    
    public Integer getSyncProgram() { return syncProgram; }
    public void setSyncProgram(Integer syncProgram) { this.syncProgram = syncProgram; }
    
    public Integer getConstrainedProportion() { return constrainedProportion; }
    public void setConstrainedProportion(Integer constrainedProportion) { this.constrainedProportion = constrainedProportion; }
    
    public Integer getFontSize() { return fontSize; }
    public void setFontSize(Integer fontSize) { this.fontSize = fontSize; }
    
    public String getLineText() { return lineText; }
    public void setLineText(String lineText) { this.lineText = lineText; }
    
    public Integer getLineTextType() { return lineTextType; }
    public void setLineTextType(Integer lineTextType) { this.lineTextType = lineTextType; }
    
    public Integer getClockType() { return clockType; }
    public void setClockType(Integer clockType) { this.clockType = clockType; }
    
    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    
    public String getEndDateTime() { return endDateTime; }
    public void setEndDateTime(String endDateTime) { this.endDateTime = endDateTime; }
    
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    
    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    
    public Integer getEnvironmentType() { return environmentType; }
    public void setEnvironmentType(Integer environmentType) { this.environmentType = environmentType; }
    
    public Integer getTemperatureShowAsFahrenheit() { return temperatureShowAsFahrenheit; }
    public void setTemperatureShowAsFahrenheit(Integer temperatureShowAsFahrenheit) { this.temperatureShowAsFahrenheit = temperatureShowAsFahrenheit; }
    
    public Integer getTemperatureOffset() { return temperatureOffset; }
    public void setTemperatureOffset(Integer temperatureOffset) { this.temperatureOffset = temperatureOffset; }
    
    public Integer getSmokeStandard() { return smokeStandard; }
    public void setSmokeStandard(Integer smokeStandard) { this.smokeStandard = smokeStandard; }
    
    public String getRssUrl() { return rssUrl; }
    public void setRssUrl(String rssUrl) { this.rssUrl = rssUrl; }
    
    public Integer getRefreshIntervalMs() { return refreshIntervalMs; }
    public void setRefreshIntervalMs(Integer refreshIntervalMs) { this.refreshIntervalMs = refreshIntervalMs; }
    
    public Integer getScrollSpeed() { return scrollSpeed; }
    public void setScrollSpeed(Integer scrollSpeed) { this.scrollSpeed = scrollSpeed; }
} 