package com.cloudcontrol.dto.program;

import java.util.Map;

/**
 * 节目创建请求DTO
 */
public class ProgramCreateRequest {
    
    private String title;
    private String status;
    private Map<String, Object> programInfo;
    private ProgramsData programs;
    
    public ProgramCreateRequest() {}
    
    public ProgramCreateRequest(String title, String status, Map<String, Object> programInfo, ProgramsData programs) {
        this.title = title;
        this.status = status;
        this.programInfo = programInfo;
        this.programs = programs;
    }
    
    // getter/setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Map<String, Object> getProgramInfo() { return programInfo; }
    public void setProgramInfo(Map<String, Object> programInfo) { this.programInfo = programInfo; }
    
    public ProgramsData getPrograms() { return programs; }
    public void setPrograms(ProgramsData programs) { this.programs = programs; }
    
    /**
     * 节目数据
     */
    public static class ProgramsData {
        private ProgramInfo program;
        
        public ProgramsData() {}
        
        public ProgramsData(ProgramInfo program) {
            this.program = program;
        }
        
        public ProgramInfo getProgram() { return program; }
        public void setProgram(ProgramInfo program) { this.program = program; }
    }
    
    /**
     * 节目信息
     */
    public static class ProgramInfo {
        private Information information;
        private Page[] pages;
        
        public ProgramInfo() {}
        
        public ProgramInfo(Information information, Page[] pages) {
            this.information = information;
            this.pages = pages;
        }
        
        public Information getInformation() { return information; }
        public void setInformation(Information information) { this.information = information; }
        
        public Page[] getPages() { return pages; }
        public void setPages(Page[] pages) { this.pages = pages; }
    }
    
    /**
     * 节目基本信息
     */
    public static class Information {
        private Integer width;
        private Integer height;
        private Integer scale;
        
        public Information() {}
        
        public Information(Integer width, Integer height, Integer scale) {
            this.width = width;
            this.height = height;
            this.scale = scale;
        }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
        
        public Integer getScale() { return scale; }
        public void setScale(Integer scale) { this.scale = scale; }
    }
    
    /**
     * 节目页
     */
    public static class Page {
        private Long appointDuration;
        private Integer opacity;
        private Integer loopType;
        private String bgColor;
        private Region[] regions;
        
        public Page() {}
        
        public Page(Long appointDuration, Integer opacity, Integer loopType, String bgColor, Region[] regions) {
            this.appointDuration = appointDuration;
            this.opacity = opacity;
            this.loopType = loopType;
            this.bgColor = bgColor;
            this.regions = regions;
        }
        
        public Long getAppointDuration() { return appointDuration; }
        public void setAppointDuration(Long appointDuration) { this.appointDuration = appointDuration; }
        
        public Integer getOpacity() { return opacity; }
        public void setOpacity(Integer opacity) { this.opacity = opacity; }
        
        public Integer getLoopType() { return loopType; }
        public void setLoopType(Integer loopType) { this.loopType = loopType; }
        
        public String getBgColor() { return bgColor; }
        public void setBgColor(String bgColor) { this.bgColor = bgColor; }
        
        public Region[] getRegions() { return regions; }
        public void setRegions(Region[] regions) { this.regions = regions; }
    }
    
    /**
     * 节目区域
     */
    public static class Region {
        private Integer type;
        private Integer layer;
        private String name;
        private Rect rect;
        private Item[] items;
        private Integer isScheduleRegion;
        
        public Region() {}
        
        public Region(Integer type, Integer layer, String name, Rect rect, Item[] items) {
            this.type = type;
            this.layer = layer;
            this.name = name;
            this.rect = rect;
            this.items = items;
        }
        
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public Integer getLayer() { return layer; }
        public void setLayer(Integer layer) { this.layer = layer; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Rect getRect() { return rect; }
        public void setRect(Rect rect) { this.rect = rect; }
        
        public Item[] getItems() { return items; }
        public void setItems(Item[] items) { this.items = items; }
        
        public Integer getIsScheduleRegion() { return isScheduleRegion; }
        public void setIsScheduleRegion(Integer isScheduleRegion) { this.isScheduleRegion = isScheduleRegion; }
    }
    
    /**
     * 区域位置和大小
     */
    public static class Rect {
        private Integer x;
        private Integer y;
        private Integer width;
        private Integer height;
        private Integer borderWidth;
        private String borderColor;
        
        public Rect() {}
        
        public Rect(Integer x, Integer y, Integer width, Integer height, Integer borderWidth, String borderColor) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
        }
        
        public Integer getX() { return x; }
        public void setX(Integer x) { this.x = x; }
        
        public Integer getY() { return y; }
        public void setY(Integer y) { this.y = y; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
        
        public Integer getBorderWidth() { return borderWidth; }
        public void setBorderWidth(Integer borderWidth) { this.borderWidth = borderWidth; }
        
        public String getBorderColor() { return borderColor; }
        public void setBorderColor(String borderColor) { this.borderColor = borderColor; }
    }
    
    /**
     * 节目素材项
     */
    public static class Item {
        private Integer type;
        private String backColor;
        private Long duration;
        private String textColor;
        private String text;
        private Integer centralAlign;
        private Integer verticalAlign;
        private Integer isScroll;
        private Double speed;
        private Integer isHeadConnectTail;
        private Integer repeatCount;
        private Integer isScrollByTime;
        private Long playLength;
        private LogFont logFont;
        private MultiPicInfo multiPicInfo;
        private FileSource fileSource;
        private Integer reserveAS;
        private String url;
        private Integer volume;
        private Integer playTimes;
        private Integer loop;
        private InEffect inEffect;
        private Trigger trigger;
        private Integer isAnolog;
        private Double timeZone;
        private DigitalClock digitalClock;
        private AnologClock anologClock;
        private HhourScale hhourScale;
        private MinuteScale minuteScale;
        private Integer isLocal;
        
        // 计时节目属性
        private Integer beToEndTime;
        private Integer style;
        private Integer isMultiLine;
        private String prefix;
        private String endDateTime;
        private String showFormat;
        private Integer isShowDayCount;
        private String dayCountColor;
        private Integer isShowHourCount;
        private String hourCountColor;
        private Integer isShowMinuteCount;
        private String minuteCountColor;
        private Integer isShowSecondCount;
        private String secondCountColor;
        private LogFont unitFont;
        private String time;
        private String date;
        private Integer opacityBg;
        private Integer countdownPlayTimes;
        private Boolean showBengali;
        
        // 天气节目属性
        private Integer moveType;
        private Integer moveSpeed;
        private Integer remainTime;
        private String regionName;
        private Integer isShowWeather;
        private String weatherPrefix;
        private Integer isShowTemperature;
        private String temperaturePrefix;
        private Integer isShowWind;
        private String windPrefix;
        private Integer isShowAir;
        private String airPrefix;
        private Integer isShowUltraviolet;
        private String ultraviolet;
        private Integer isShowMovementIndex;
        private String movementIndex;
        private Integer isShowColdIndex;
        private String coldIndex;
        private Integer isShowHumidity;
        private String humidity;
        private Integer isShowHeatstroke;
        private String heatstroke;
        private Integer serverType;
        private String regionCode;
        private Double longitud;
        private Double latitude;
        private Double timezone;
        private String language;
        private Integer isShowPic;
        private Integer showStyle;
        private Integer ifSpeedByFrame;
        private Integer speedByFrame;
        private String buttonTemp;
        private Integer bShowAsFahrenheit;
        private Integer weatherSource;
        private Integer playTime;
        
        // 环境节目属性
        private String name;
        private String id;
        private Integer beGlaring;
        private Integer isNeedUpdate;
        private Integer updateInterval;
        private Integer mirrorOrHandstand;
        private Effect effect;
        private OutEffect outEffect;
        private Integer bTransparent;
        private String prevfix;
        private String suffix;
        private Integer sourceType;
        private Integer sourceIndex;
        private Integer portIndex;
        private Integer scIndex;
        private Integer alignment;
        private Integer tem1;
        private Integer tem2;
        private Integer tem3;
        private String temColor1;
        private String temColor2;
        private String temColor3;
        private String temColor4;
        private Integer bShowPic;
        private Integer bShowText;
        private Integer textX;
        private Integer textY;
        private Integer temperatureOffset;
        private Integer standard;
        
        // 新闻节目属性
        private String newsUrl;
        private String alhpa;
        
        public Item() {}
        
        // getter/setter
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public String getBackColor() { return backColor; }
        public void setBackColor(String backColor) { this.backColor = backColor; }
        
        public Long getDuration() { return duration; }
        public void setDuration(Long duration) { this.duration = duration; }
        
        public String getTextColor() { return textColor; }
        public void setTextColor(String textColor) { this.textColor = textColor; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        
        public Integer getCentralAlign() { return centralAlign; }
        public void setCentralAlign(Integer centralAlign) { this.centralAlign = centralAlign; }
        
        public Integer getVerticalAlign() { return verticalAlign; }
        public void setVerticalAlign(Integer verticalAlign) { this.verticalAlign = verticalAlign; }
        
        public Integer getIsScroll() { return isScroll; }
        public void setIsScroll(Integer isScroll) { this.isScroll = isScroll; }
        
        public Double getSpeed() { return speed; }
        public void setSpeed(Double speed) { this.speed = speed; }
        
        public Integer getIsHeadConnectTail() { return isHeadConnectTail; }
        public void setIsHeadConnectTail(Integer isHeadConnectTail) { this.isHeadConnectTail = isHeadConnectTail; }
        
        public Integer getRepeatCount() { return repeatCount; }
        public void setRepeatCount(Integer repeatCount) { this.repeatCount = repeatCount; }
        
        public Integer getIsScrollByTime() { return isScrollByTime; }
        public void setIsScrollByTime(Integer isScrollByTime) { this.isScrollByTime = isScrollByTime; }
        
        public Long getPlayLength() { return playLength; }
        public void setPlayLength(Long playLength) { this.playLength = playLength; }
        
        public LogFont getLogFont() { return logFont; }
        public void setLogFont(LogFont logFont) { this.logFont = logFont; }
        
        public MultiPicInfo getMultiPicInfo() { return multiPicInfo; }
        public void setMultiPicInfo(MultiPicInfo multiPicInfo) { this.multiPicInfo = multiPicInfo; }
        
        public FileSource getFileSource() { return fileSource; }
        public void setFileSource(FileSource fileSource) { this.fileSource = fileSource; }
        
        public Integer getReserveAS() { return reserveAS; }
        public void setReserveAS(Integer reserveAS) { this.reserveAS = reserveAS; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        
        public Integer getVolume() { return volume; }
        public void setVolume(Integer volume) { this.volume = volume; }
        
        public Integer getPlayTimes() { return playTimes; }
        public void setPlayTimes(Integer playTimes) { this.playTimes = playTimes; }
        
        public Integer getLoop() { return loop; }
        public void setLoop(Integer loop) { this.loop = loop; }
        
        public InEffect getInEffect() { return inEffect; }
        public void setInEffect(InEffect inEffect) { this.inEffect = inEffect; }
        
        public Trigger getTrigger() { return trigger; }
        public void setTrigger(Trigger trigger) { this.trigger = trigger; }
        
        public Integer getIsAnolog() { return isAnolog; }
        public void setIsAnolog(Integer isAnolog) { this.isAnolog = isAnolog; }
        
        public Double getTimeZone() { return timeZone; }
        public void setTimeZone(Double timeZone) { this.timeZone = timeZone; }
        
        public DigitalClock getDigitalClock() { return digitalClock; }
        public void setDigitalClock(DigitalClock digitalClock) { this.digitalClock = digitalClock; }
        
        public AnologClock getAnologClock() { return anologClock; }
        public void setAnologClock(AnologClock anologClock) { this.anologClock = anologClock; }
        
        public HhourScale getHhourScale() { return hhourScale; }
        public void setHhourScale(HhourScale hhourScale) { this.hhourScale = hhourScale; }
        
        public MinuteScale getMinuteScale() { return minuteScale; }
        public void setMinuteScale(MinuteScale minuteScale) { this.minuteScale = minuteScale; }
        
        public Integer getIsLocal() { return isLocal; }
        public void setIsLocal(Integer isLocal) { this.isLocal = isLocal; }
        
        // 计时节目属性 getter/setter
        public Integer getBeToEndTime() { return beToEndTime; }
        public void setBeToEndTime(Integer beToEndTime) { this.beToEndTime = beToEndTime; }
        
        public Integer getStyle() { return style; }
        public void setStyle(Integer style) { this.style = style; }
        
        public Integer getIsMultiLine() { return isMultiLine; }
        public void setIsMultiLine(Integer isMultiLine) { this.isMultiLine = isMultiLine; }
        
        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }
        
        public String getEndDateTime() { return endDateTime; }
        public void setEndDateTime(String endDateTime) { this.endDateTime = endDateTime; }
        
        public String getShowFormat() { return showFormat; }
        public void setShowFormat(String showFormat) { this.showFormat = showFormat; }
        
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
        
        public LogFont getUnitFont() { return unitFont; }
        public void setUnitFont(LogFont unitFont) { this.unitFont = unitFont; }
        
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        
        public Integer getOpacityBg() { return opacityBg; }
        public void setOpacityBg(Integer opacityBg) { this.opacityBg = opacityBg; }
        
        public Integer getCountdownPlayTimes() { return countdownPlayTimes; }
        public void setCountdownPlayTimes(Integer countdownPlayTimes) { this.countdownPlayTimes = countdownPlayTimes; }
        
        public Boolean getShowBengali() { return showBengali; }
        public void setShowBengali(Boolean showBengali) { this.showBengali = showBengali; }
        
        // 天气节目属性 getter/setter
        public Integer getMoveType() { return moveType; }
        public void setMoveType(Integer moveType) { this.moveType = moveType; }
        
        public Integer getMoveSpeed() { return moveSpeed; }
        public void setMoveSpeed(Integer moveSpeed) { this.moveSpeed = moveSpeed; }
        
        public Integer getRemainTime() { return remainTime; }
        public void setRemainTime(Integer remainTime) { this.remainTime = remainTime; }
        
        public String getRegionName() { return regionName; }
        public void setRegionName(String regionName) { this.regionName = regionName; }
        
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
        
        public Integer getIsShowAir() { return isShowAir; }
        public void setIsShowAir(Integer isShowAir) { this.isShowAir = isShowAir; }
        
        public String getAirPrefix() { return airPrefix; }
        public void setAirPrefix(String airPrefix) { this.airPrefix = airPrefix; }
        
        public Integer getIsShowUltraviolet() { return isShowUltraviolet; }
        public void setIsShowUltraviolet(Integer isShowUltraviolet) { this.isShowUltraviolet = isShowUltraviolet; }
        
        public String getUltraviolet() { return ultraviolet; }
        public void setUltraviolet(String ultraviolet) { this.ultraviolet = ultraviolet; }
        
        public Integer getIsShowMovementIndex() { return isShowMovementIndex; }
        public void setIsShowMovementIndex(Integer isShowMovementIndex) { this.isShowMovementIndex = isShowMovementIndex; }
        
        public String getMovementIndex() { return movementIndex; }
        public void setMovementIndex(String movementIndex) { this.movementIndex = movementIndex; }
        
        public Integer getIsShowColdIndex() { return isShowColdIndex; }
        public void setIsShowColdIndex(Integer isShowColdIndex) { this.isShowColdIndex = isShowColdIndex; }
        
        public String getColdIndex() { return coldIndex; }
        public void setColdIndex(String coldIndex) { this.coldIndex = coldIndex; }
        
        public Integer getIsShowHumidity() { return isShowHumidity; }
        public void setIsShowHumidity(Integer isShowHumidity) { this.isShowHumidity = isShowHumidity; }
        
        public String getHumidity() { return humidity; }
        public void setHumidity(String humidity) { this.humidity = humidity; }
        
        public Integer getIsShowHeatstroke() { return isShowHeatstroke; }
        public void setIsShowHeatstroke(Integer isShowHeatstroke) { this.isShowHeatstroke = isShowHeatstroke; }
        
        public String getHeatstroke() { return heatstroke; }
        public void setHeatstroke(String heatstroke) { this.heatstroke = heatstroke; }
        
        public Integer getServerType() { return serverType; }
        public void setServerType(Integer serverType) { this.serverType = serverType; }
        
        public String getRegionCode() { return regionCode; }
        public void setRegionCode(String regionCode) { this.regionCode = regionCode; }
        
        public Double getLongitud() { return longitud; }
        public void setLongitud(Double longitud) { this.longitud = longitud; }
        
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        
        public Double getTimezone() { return timezone; }
        public void setTimezone(Double timezone) { this.timezone = timezone; }
        
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        
        public Integer getIsShowPic() { return isShowPic; }
        public void setIsShowPic(Integer isShowPic) { this.isShowPic = isShowPic; }
        
        public Integer getShowStyle() { return showStyle; }
        public void setShowStyle(Integer showStyle) { this.showStyle = showStyle; }
        
        public Integer getIfSpeedByFrame() { return ifSpeedByFrame; }
        public void setIfSpeedByFrame(Integer ifSpeedByFrame) { this.ifSpeedByFrame = ifSpeedByFrame; }
        
        public Integer getSpeedByFrame() { return speedByFrame; }
        public void setSpeedByFrame(Integer speedByFrame) { this.speedByFrame = speedByFrame; }
        
        public String getButtonTemp() { return buttonTemp; }
        public void setButtonTemp(String buttonTemp) { this.buttonTemp = buttonTemp; }
        
        public Integer getBShowAsFahrenheit() { return bShowAsFahrenheit; }
        public void setBShowAsFahrenheit(Integer bShowAsFahrenheit) { this.bShowAsFahrenheit = bShowAsFahrenheit; }
        
        public Integer getWeatherSource() { return weatherSource; }
        public void setWeatherSource(Integer weatherSource) { this.weatherSource = weatherSource; }
        
        public Integer getPlayTime() { return playTime; }
        public void setPlayTime(Integer playTime) { this.playTime = playTime; }
        
        // 环境节目属性 getter/setter
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public Integer getBeGlaring() { return beGlaring; }
        public void setBeGlaring(Integer beGlaring) { this.beGlaring = beGlaring; }
        
        public Integer getIsNeedUpdate() { return isNeedUpdate; }
        public void setIsNeedUpdate(Integer isNeedUpdate) { this.isNeedUpdate = isNeedUpdate; }
        
        public Integer getUpdateInterval() { return updateInterval; }
        public void setUpdateInterval(Integer updateInterval) { this.updateInterval = updateInterval; }
        
        public Integer getMirrorOrHandstand() { return mirrorOrHandstand; }
        public void setMirrorOrHandstand(Integer mirrorOrHandstand) { this.mirrorOrHandstand = mirrorOrHandstand; }
        
        public Effect getEffect() { return effect; }
        public void setEffect(Effect effect) { this.effect = effect; }
        
        public OutEffect getOutEffect() { return outEffect; }
        public void setOutEffect(OutEffect outEffect) { this.outEffect = outEffect; }
        
        public Integer getBTransparent() { return bTransparent; }
        public void setBTransparent(Integer bTransparent) { this.bTransparent = bTransparent; }
        
        public String getPrevfix() { return prevfix; }
        public void setPrevfix(String prevfix) { this.prevfix = prevfix; }
        
        public String getSuffix() { return suffix; }
        public void setSuffix(String suffix) { this.suffix = suffix; }
        
        public Integer getSourceType() { return sourceType; }
        public void setSourceType(Integer sourceType) { this.sourceType = sourceType; }
        
        public Integer getSourceIndex() { return sourceIndex; }
        public void setSourceIndex(Integer sourceIndex) { this.sourceIndex = sourceIndex; }
        
        public Integer getPortIndex() { return portIndex; }
        public void setPortIndex(Integer portIndex) { this.portIndex = portIndex; }
        
        public Integer getScIndex() { return scIndex; }
        public void setScIndex(Integer scIndex) { this.scIndex = scIndex; }
        
        public Integer getAlignment() { return alignment; }
        public void setAlignment(Integer alignment) { this.alignment = alignment; }
        
        public Integer getTem1() { return tem1; }
        public void setTem1(Integer tem1) { this.tem1 = tem1; }
        
        public Integer getTem2() { return tem2; }
        public void setTem2(Integer tem2) { this.tem2 = tem2; }
        
        public Integer getTem3() { return tem3; }
        public void setTem3(Integer tem3) { this.tem3 = tem3; }
        
        public String getTemColor1() { return temColor1; }
        public void setTemColor1(String temColor1) { this.temColor1 = temColor1; }
        
        public String getTemColor2() { return temColor2; }
        public void setTemColor2(String temColor2) { this.temColor2 = temColor2; }
        
        public String getTemColor3() { return temColor3; }
        public void setTemColor3(String temColor3) { this.temColor3 = temColor3; }
        
        public String getTemColor4() { return temColor4; }
        public void setTemColor4(String temColor4) { this.temColor4 = temColor4; }
        
        public Integer getBShowPic() { return bShowPic; }
        public void setBShowPic(Integer bShowPic) { this.bShowPic = bShowPic; }
        
        public Integer getBShowText() { return bShowText; }
        public void setBShowText(Integer bShowText) { this.bShowText = bShowText; }
        
        public Integer getTextX() { return textX; }
        public void setTextX(Integer textX) { this.textX = textX; }
        
        public Integer getTextY() { return textY; }
        public void setTextY(Integer textY) { this.textY = textY; }
        
        public Integer getTemperatureOffset() { return temperatureOffset; }
        public void setTemperatureOffset(Integer temperatureOffset) { this.temperatureOffset = temperatureOffset; }
        
        public Integer getStandard() { return standard; }
        public void setStandard(Integer standard) { this.standard = standard; }
        
        // 新闻节目属性 getter/setter
        public String getNewsUrl() { return newsUrl; }
        public void setNewsUrl(String newsUrl) { this.newsUrl = newsUrl; }
        
        public String getAlhpa() { return alhpa; }
        public void setAlhpa(String alhpa) { this.alhpa = alhpa; }
    }
    
    /**
     * 特效
     */
    public static class Effect {
        private Integer isStatic;
        private Integer stayType;
        
        public Effect() {}
        
        public Effect(Integer isStatic, Integer stayType) {
            this.isStatic = isStatic;
            this.stayType = stayType;
        }
        
        public Integer getIsStatic() { return isStatic; }
        public void setIsStatic(Integer isStatic) { this.isStatic = isStatic; }
        
        public Integer getStayType() { return stayType; }
        public void setStayType(Integer stayType) { this.stayType = stayType; }
    }
    
    /**
     * 出场特效
     */
    public static class OutEffect {
        private Integer type;
        private Integer time;
        private Integer repeatX;
        private Integer repeatY;
        private Integer isTran;
        
        public OutEffect() {}
        
        public OutEffect(Integer type, Integer time, Integer repeatX, Integer repeatY, Integer isTran) {
            this.type = type;
            this.time = time;
            this.repeatX = repeatX;
            this.repeatY = repeatY;
            this.isTran = isTran;
        }
        
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public Integer getTime() { return time; }
        public void setTime(Integer time) { this.time = time; }
        
        public Integer getRepeatX() { return repeatX; }
        public void setRepeatX(Integer repeatX) { this.repeatX = repeatX; }
        
        public Integer getRepeatY() { return repeatY; }
        public void setRepeatY(Integer repeatY) { this.repeatY = repeatY; }
        
        public Integer getIsTran() { return isTran; }
        public void setIsTran(Integer isTran) { this.isTran = isTran; }
    }
    
    /**
     * 字体信息
     */
    public static class LogFont {
        private Integer lfHeight;
        private Integer lfWidth;
        private Integer lfWeight;
        private Integer lfItalic;
        private Integer lfUnderline;
        private Integer lfStrikeOut;
        private Integer lfPitchAndFamily;
        private String lfFaceName;
        private Integer ifLeft;
        private Integer ifRight;
        private Integer ifCenter;
        
        public LogFont() {}
        
        // getter/setter
        public Integer getLfHeight() { return lfHeight; }
        public void setLfHeight(Integer lfHeight) { this.lfHeight = lfHeight; }
        
        public Integer getLfWidth() { return lfWidth; }
        public void setLfWidth(Integer lfWidth) { this.lfWidth = lfWidth; }
        
        public Integer getLfWeight() { return lfWeight; }
        public void setLfWeight(Integer lfWeight) { this.lfWeight = lfWeight; }
        
        public Integer getLfItalic() { return lfItalic; }
        public void setLfItalic(Integer lfItalic) { this.lfItalic = lfItalic; }
        
        public Integer getLfUnderline() { return lfUnderline; }
        public void setLfUnderline(Integer lfUnderline) { this.lfUnderline = lfUnderline; }
        
        public Integer getLfStrikeOut() { return lfStrikeOut; }
        public void setLfStrikeOut(Integer lfStrikeOut) { this.lfStrikeOut = lfStrikeOut; }
        
        public Integer getLfPitchAndFamily() { return lfPitchAndFamily; }
        public void setLfPitchAndFamily(Integer lfPitchAndFamily) { this.lfPitchAndFamily = lfPitchAndFamily; }
        
        public String getLfFaceName() { return lfFaceName; }
        public void setLfFaceName(String lfFaceName) { this.lfFaceName = lfFaceName; }
        
        public Integer getIfLeft() { return ifLeft; }
        public void setIfLeft(Integer ifLeft) { this.ifLeft = ifLeft; }
        
        public Integer getIfRight() { return ifRight; }
        public void setIfRight(Integer ifRight) { this.ifRight = ifRight; }
        
        public Integer getIfCenter() { return ifCenter; }
        public void setIfCenter(Integer ifCenter) { this.ifCenter = ifCenter; }
    }
    
    /**
     * 多图片信息
     */
    public static class MultiPicInfo {
        private Long onePicDuration;
        private Integer picCount;
        
        public MultiPicInfo() {}
        
        public MultiPicInfo(Long onePicDuration, Integer picCount) {
            this.onePicDuration = onePicDuration;
            this.picCount = picCount;
        }
        
        public Long getOnePicDuration() { return onePicDuration; }
        public void setOnePicDuration(Long onePicDuration) { this.onePicDuration = onePicDuration; }
        
        public Integer getPicCount() { return picCount; }
        public void setPicCount(Integer picCount) { this.picCount = picCount; }
    }
    
    /**
     * 文件源
     */
    public static class FileSource {
        private Integer isRelative;
        private String filePath;
        private Integer resourceId;
        private String originName;
        
        public FileSource() {}
        
        public FileSource(Integer isRelative, String filePath, Integer resourceId, String originName) {
            this.isRelative = isRelative;
            this.filePath = filePath;
            this.resourceId = resourceId;
            this.originName = originName;
        }
        
        public Integer getIsRelative() { return isRelative; }
        public void setIsRelative(Integer isRelative) { this.isRelative = isRelative; }
        
        public String getFilePath() { return filePath; }
        public void setFilePath(String filePath) { this.filePath = filePath; }
        
        public Integer getResourceId() { return resourceId; }
        public void setResourceId(Integer resourceId) { this.resourceId = resourceId; }
        
        public String getOriginName() { return originName; }
        public void setOriginName(String originName) { this.originName = originName; }
    }
    
    /**
     * 进场特效
     */
    public static class InEffect {
        private Double webTime;
        private String name;
        private Integer type;
        private Integer time;
        private Integer repeatX;
        private Integer repeatY;
        private Integer isTran;
        
        public InEffect() {}
        
        // getter/setter
        public Double getWebTime() { return webTime; }
        public void setWebTime(Double webTime) { this.webTime = webTime; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public Integer getTime() { return time; }
        public void setTime(Integer time) { this.time = time; }
        
        public Integer getRepeatX() { return repeatX; }
        public void setRepeatX(Integer repeatX) { this.repeatX = repeatX; }
        
        public Integer getRepeatY() { return repeatY; }
        public void setRepeatY(Integer repeatY) { this.repeatY = repeatY; }
        
        public Integer getIsTran() { return isTran; }
        public void setIsTran(Integer isTran) { this.isTran = isTran; }
    }
    
    /**
     * 触发器
     */
    public static class Trigger {
        private String type;
        private String value;
        
        public Trigger() {}
        
        public Trigger(String type, String value) {
            this.type = type;
            this.value = value;
        }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
    
    /**
     * 数字时钟
     */
    public static class DigitalClock {
        private Integer type;
        private Integer flags;
        private String name;
        private Integer ftSize;
        private Integer isStrikeOut;
        private Integer weight;
        private Integer bBold;
        private Long ftColor;
        private Integer bItalic;
        private Integer bUnderline;
        
        public DigitalClock() {}
        
        // getter/setter
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public Integer getFlags() { return flags; }
        public void setFlags(Integer flags) { this.flags = flags; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Integer getFtSize() { return ftSize; }
        public void setFtSize(Integer ftSize) { this.ftSize = ftSize; }
        
        public Integer getIsStrikeOut() { return isStrikeOut; }
        public void setIsStrikeOut(Integer isStrikeOut) { this.isStrikeOut = isStrikeOut; }
        
        public Integer getWeight() { return weight; }
        public void setWeight(Integer weight) { this.weight = weight; }
        
        public Integer getBBold() { return bBold; }
        public void setBBold(Integer bBold) { this.bBold = bBold; }
        
        public Long getFtColor() { return ftColor; }
        public void setFtColor(Long ftColor) { this.ftColor = ftColor; }
        
        public Integer getBItalic() { return bItalic; }
        public void setBItalic(Integer bItalic) { this.bItalic = bItalic; }
        
        public Integer getBUnderline() { return bUnderline; }
        public void setBUnderline(Integer bUnderline) { this.bUnderline = bUnderline; }
    }
    
    /**
     * 模拟时钟
     */
    public static class AnologClock {
        private Integer shape;
        private Integer flags;
        private ClockFont clockFont;
        private String hourPinClr;
        private String minutePinClr;
        private String secondPinClr;
        private String textOffset;
        private String dateOffset;
        private String lunarOffset;
        private String weekOffset;
        
        public AnologClock() {}
        
        // getter/setter
        public Integer getShape() { return shape; }
        public void setShape(Integer shape) { this.shape = shape; }
        
        public Integer getFlags() { return flags; }
        public void setFlags(Integer flags) { this.flags = flags; }
        
        public ClockFont getClockFont() { return clockFont; }
        public void setClockFont(ClockFont clockFont) { this.clockFont = clockFont; }
        
        public String getHourPinClr() { return hourPinClr; }
        public void setHourPinClr(String hourPinClr) { this.hourPinClr = hourPinClr; }
        
        public String getMinutePinClr() { return minutePinClr; }
        public void setMinutePinClr(String minutePinClr) { this.minutePinClr = minutePinClr; }
        
        public String getSecondPinClr() { return secondPinClr; }
        public void setSecondPinClr(String secondPinClr) { this.secondPinClr = secondPinClr; }
        
        public String getTextOffset() { return textOffset; }
        public void setTextOffset(String textOffset) { this.textOffset = textOffset; }
        
        public String getDateOffset() { return dateOffset; }
        public void setDateOffset(String dateOffset) { this.dateOffset = dateOffset; }
        
        public String getLunarOffset() { return lunarOffset; }
        public void setLunarOffset(String lunarOffset) { this.lunarOffset = lunarOffset; }
        
        public String getWeekOffset() { return weekOffset; }
        public void setWeekOffset(String weekOffset) { this.weekOffset = weekOffset; }
    }
    
    /**
     * 时钟字体
     */
    public static class ClockFont {
        private FontInfo fixedText;
        private Long fixedTextColor;
        private FontInfo date;
        private Long dateColor;
        private FontInfo week;
        private Long weekColor;
        private FontInfo time;
        
        public ClockFont() {}
        
        // getter/setter
        public FontInfo getFixedText() { return fixedText; }
        public void setFixedText(FontInfo fixedText) { this.fixedText = fixedText; }
        
        public Long getFixedTextColor() { return fixedTextColor; }
        public void setFixedTextColor(Long fixedTextColor) { this.fixedTextColor = fixedTextColor; }
        
        public FontInfo getDate() { return date; }
        public void setDate(FontInfo date) { this.date = date; }
        
        public Long getDateColor() { return dateColor; }
        public void setDateColor(Long dateColor) { this.dateColor = dateColor; }
        
        public FontInfo getWeek() { return week; }
        public void setWeek(FontInfo week) { this.week = week; }
        
        public Long getWeekColor() { return weekColor; }
        public void setWeekColor(Long weekColor) { this.weekColor = weekColor; }
        
        public FontInfo getTime() { return time; }
        public void setTime(FontInfo time) { this.time = time; }
    }
    
    /**
     * 字体信息
     */
    public static class FontInfo {
        private String lfFaceName;
        private Integer lfHeight;
        private Integer lfWeight;
        private Integer lfItalic;
        private Integer lfUnderline;
        private Integer opacity;
        
        public FontInfo() {}
        
        // getter/setter
        public String getLfFaceName() { return lfFaceName; }
        public void setLfFaceName(String lfFaceName) { this.lfFaceName = lfFaceName; }
        
        public Integer getLfHeight() { return lfHeight; }
        public void setLfHeight(Integer lfHeight) { this.lfHeight = lfHeight; }
        
        public Integer getLfWeight() { return lfWeight; }
        public void setLfWeight(Integer lfWeight) { this.lfWeight = lfWeight; }
        
        public Integer getLfItalic() { return lfItalic; }
        public void setLfItalic(Integer lfItalic) { this.lfItalic = lfItalic; }
        
        public Integer getLfUnderline() { return lfUnderline; }
        public void setLfUnderline(Integer lfUnderline) { this.lfUnderline = lfUnderline; }
        
        public Integer getOpacity() { return opacity; }
        public void setOpacity(Integer opacity) { this.opacity = opacity; }
    }
    
    /**
     * 时标
     */
    public static class HhourScale {
        private String clr;
        private Integer shape;
        private Integer width;
        private Integer height;
        
        public HhourScale() {}
        
        public HhourScale(String clr, Integer shape, Integer width, Integer height) {
            this.clr = clr;
            this.shape = shape;
            this.width = width;
            this.height = height;
        }
        
        public String getClr() { return clr; }
        public void setClr(String clr) { this.clr = clr; }
        
        public Integer getShape() { return shape; }
        public void setShape(Integer shape) { this.shape = shape; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
    }
    
    /**
     * 分标
     */
    public static class MinuteScale {
        private String clr;
        private Integer shape;
        private Integer width;
        private Integer height;
        
        public MinuteScale() {}
        
        public MinuteScale(String clr, Integer shape, Integer width, Integer height) {
            this.clr = clr;
            this.shape = shape;
            this.width = width;
            this.height = height;
        }
        
        public String getClr() { return clr; }
        public void setClr(String clr) { this.clr = clr; }
        
        public Integer getShape() { return shape; }
        public void setShape(Integer shape) { this.shape = shape; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
    }
} 