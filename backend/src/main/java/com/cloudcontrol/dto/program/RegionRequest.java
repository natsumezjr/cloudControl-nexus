package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 区域请求DTO
 */
public class RegionRequest {
    
    private String regionType;
    private Integer durationMillis;
    private RectRequest rect;
    
    // 网页窗口参数
    private String url;
    
    // 文件窗口参数
    private Integer syncProgram;
    private List<MediumRequest> medium;
    private Integer constrainedProportion;
    
    // 文本窗口参数
    private String text;
    private Integer textType;
    private String fontColor;
    private Integer fontSize;
    private String fontName;
    private Integer effect;
    private Integer isHeadConnectTail;
    private Integer centeralAlign;
    
    // 时钟窗口参数
    private Integer clockType;
    private Integer isMultiLine;
    private String textPrefix;
    
    // 天气窗口参数
    private String regionName;
    private Integer moveType;
    private Integer moveSpeed;
    private Integer isShowWeather;
    private String weatherPrefix;
    private Integer isShowTemperature;
    private String temperaturePrefix;
    private Integer isShowWind;
    private String windPrefix;
    private Integer isShowHumidity;
    private String humidityPrefix;
    private Integer isShowAir;
    private String airPrefix;
    private Integer isShowColdIndex;
    private String coldIndexPrefix;
    
    // 倒计时窗口参数
    private String endDateTime;
    private String prefix;
    private Integer displayStyle;
    private Integer isShowDayCount;
    private String dayCountColor;
    private Integer isShowHourCount;
    private String hourCountColor;
    private Integer isShowMinuteCount;
    private String minuteCountColor;
    private Integer isShowSecondCount;
    private String secondCountColor;
    
    // 环境窗口参数
    private Integer environmentType;
    private String suffix;
    private Integer temperatureShowAsFahrenheit;
    private Integer temperatureOffset;
    private Integer smokeStandard;
    private String textColor;
    private String backColor;
    
    // 新闻窗口参数
    private String rssUrl;
    private Integer refreshIntervalMs;
    private Integer scrollSpeed;
    
    public RegionRequest() {}
    
    // getter/setter
    public String getRegionType() { return regionType; }
    public void setRegionType(String regionType) { this.regionType = regionType; }
    
    public Integer getDurationMillis() { return durationMillis; }
    public void setDurationMillis(Integer durationMillis) { this.durationMillis = durationMillis; }
    
    public RectRequest getRect() { return rect; }
    public void setRect(RectRequest rect) { this.rect = rect; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Integer getSyncProgram() { return syncProgram; }
    public void setSyncProgram(Integer syncProgram) { this.syncProgram = syncProgram; }
    
    public List<MediumRequest> getMedium() { return medium; }
    public void setMedium(List<MediumRequest> medium) { this.medium = medium; }
    
    public Integer getConstrainedProportion() { return constrainedProportion; }
    public void setConstrainedProportion(Integer constrainedProportion) { this.constrainedProportion = constrainedProportion; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public Integer getTextType() { return textType; }
    public void setTextType(Integer textType) { this.textType = textType; }
    
    public String getFontColor() { return fontColor; }
    public void setFontColor(String fontColor) { this.fontColor = fontColor; }
    
    public Integer getFontSize() { return fontSize; }
    public void setFontSize(Integer fontSize) { this.fontSize = fontSize; }
    
    public String getFontName() { return fontName; }
    public void setFontName(String fontName) { this.fontName = fontName; }
    
    public Integer getEffect() { return effect; }
    public void setEffect(Integer effect) { this.effect = effect; }
    
    public Integer getIsHeadConnectTail() { return isHeadConnectTail; }
    public void setIsHeadConnectTail(Integer isHeadConnectTail) { this.isHeadConnectTail = isHeadConnectTail; }
    
    public Integer getCenteralAlign() { return centeralAlign; }
    public void setCenteralAlign(Integer centeralAlign) { this.centeralAlign = centeralAlign; }
    
    public Integer getClockType() { return clockType; }
    public void setClockType(Integer clockType) { this.clockType = clockType; }
    
    public Integer getIsMultiLine() { return isMultiLine; }
    public void setIsMultiLine(Integer isMultiLine) { this.isMultiLine = isMultiLine; }
    
    public String getTextPrefix() { return textPrefix; }
    public void setTextPrefix(String textPrefix) { this.textPrefix = textPrefix; }
    
    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    
    public Integer getMoveType() { return moveType; }
    public void setMoveType(Integer moveType) { this.moveType = moveType; }
    
    public Integer getMoveSpeed() { return moveSpeed; }
    public void setMoveSpeed(Integer moveSpeed) { this.moveSpeed = moveSpeed; }
    
    public Integer getIsShowWeather() { return isShowWeather; }
    public void setIsShowWeather(Integer isShowWeather) { this.isShowWeather = isShowWeather; }
    
    public String getWeatherPrefix() { return weatherPrefix; }
    public void setWeatherPrefix(String weatherPrefix) { this.weatherPrefix = weatherPrefix; }
    
    public Integer getIsShowTemperature() { return isShowTemperature; }
    public void setIsShowTemperature(Integer isShowTemperature) { this.isShowTemperature = isShowTemperature; }
    
    public String getTemperaturePrefix() { return temperaturePrefix; }
    public void setTemperaturePrefix(String temperaturePrefix) { this.temperaturePrefix = temperaturePrefix; }
    
    public Integer getIsShowWind() { return isShowWind; }
    public void setIsShowWind(Integer isShowWind) { this.isShowWind = isShowWind; }
    
    public String getWindPrefix() { return windPrefix; }
    public void setWindPrefix(String windPrefix) { this.windPrefix = windPrefix; }
    
    public Integer getIsShowHumidity() { return isShowHumidity; }
    public void setIsShowHumidity(Integer isShowHumidity) { this.isShowHumidity = isShowHumidity; }
    
    public String getHumidityPrefix() { return humidityPrefix; }
    public void setHumidityPrefix(String humidityPrefix) { this.humidityPrefix = humidityPrefix; }
    
    public Integer getIsShowAir() { return isShowAir; }
    public void setIsShowAir(Integer isShowAir) { this.isShowAir = isShowAir; }
    
    public String getAirPrefix() { return airPrefix; }
    public void setAirPrefix(String airPrefix) { this.airPrefix = airPrefix; }
    
    public Integer getIsShowColdIndex() { return isShowColdIndex; }
    public void setIsShowColdIndex(Integer isShowColdIndex) { this.isShowColdIndex = isShowColdIndex; }
    
    public String getColdIndexPrefix() { return coldIndexPrefix; }
    public void setColdIndexPrefix(String coldIndexPrefix) { this.coldIndexPrefix = coldIndexPrefix; }
    
    public String getEndDateTime() { return endDateTime; }
    public void setEndDateTime(String endDateTime) { this.endDateTime = endDateTime; }
    
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    
    public Integer getDisplayStyle() { return displayStyle; }
    public void setDisplayStyle(Integer displayStyle) { this.displayStyle = displayStyle; }
    
    public Integer getIsShowDayCount() { return isShowDayCount; }
    public void setIsShowDayCount(Integer isShowDayCount) { this.isShowDayCount = isShowDayCount; }
    
    public String getDayCountColor() { return dayCountColor; }
    public void setDayCountColor(String dayCountColor) { this.dayCountColor = dayCountColor; }
    
    public Integer getIsShowHourCount() { return isShowHourCount; }
    public void setIsShowHourCount(Integer isShowHourCount) { this.isShowHourCount = isShowHourCount; }
    
    public String getHourCountColor() { return hourCountColor; }
    public void setHourCountColor(String hourCountColor) { this.hourCountColor = hourCountColor; }
    
    public Integer getIsShowMinuteCount() { return isShowMinuteCount; }
    public void setIsShowMinuteCount(Integer isShowMinuteCount) { this.isShowMinuteCount = isShowMinuteCount; }
    
    public String getMinuteCountColor() { return minuteCountColor; }
    public void setMinuteCountColor(String minuteCountColor) { this.minuteCountColor = minuteCountColor; }
    
    public Integer getIsShowSecondCount() { return isShowSecondCount; }
    public void setIsShowSecondCount(Integer isShowSecondCount) { this.isShowSecondCount = isShowSecondCount; }
    
    public String getSecondCountColor() { return secondCountColor; }
    public void setSecondCountColor(String secondCountColor) { this.secondCountColor = secondCountColor; }
    
    public Integer getEnvironmentType() { return environmentType; }
    public void setEnvironmentType(Integer environmentType) { this.environmentType = environmentType; }
    
    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    
    public Integer getTemperatureShowAsFahrenheit() { return temperatureShowAsFahrenheit; }
    public void setTemperatureShowAsFahrenheit(Integer temperatureShowAsFahrenheit) { this.temperatureShowAsFahrenheit = temperatureShowAsFahrenheit; }
    
    public Integer getTemperatureOffset() { return temperatureOffset; }
    public void setTemperatureOffset(Integer temperatureOffset) { this.temperatureOffset = temperatureOffset; }
    
    public Integer getSmokeStandard() { return smokeStandard; }
    public void setSmokeStandard(Integer smokeStandard) { this.smokeStandard = smokeStandard; }
    
    public String getTextColor() { return textColor; }
    public void setTextColor(String textColor) { this.textColor = textColor; }
    
    public String getBackColor() { return backColor; }
    public void setBackColor(String backColor) { this.backColor = backColor; }
    
    public String getRssUrl() { return rssUrl; }
    public void setRssUrl(String rssUrl) { this.rssUrl = rssUrl; }
    
    public Integer getRefreshIntervalMs() { return refreshIntervalMs; }
    public void setRefreshIntervalMs(Integer refreshIntervalMs) { this.refreshIntervalMs = refreshIntervalMs; }
    
    public Integer getScrollSpeed() { return scrollSpeed; }
    public void setScrollSpeed(Integer scrollSpeed) { this.scrollSpeed = scrollSpeed; }
} 