package com.cloudcontrol.entity.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "terminals")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("serialNo")
    private int serialNo; // 设备序列号（自增主键）

    @Column(nullable = false)
    @JsonProperty("deviceName")
    private String deviceName; // 设备名称（注册时必填）

    @Column(nullable = false)
    private String password; // 终端账号密码

    @JsonProperty("accountName")
    private String accountName; // 终端账号名称
    private String ledDescription; // 设备描述
    private Timestamp lastHeartbeat; // 最后心跳/消息时间
    private String status; // 终端状态（如online/sleep/rebooting）
    private Integer powerStatus; // 休眠/唤醒
    private Integer brightness; // 亮度
    private Integer colortemp; // 色温
    private Integer volume; // 音量
    private String inputMode; // 信号源
    private String language; // 语言
    private String country; // 地区
    private String timezoneId; // 时区ID
    private Float timezone; // 时区偏移
    private Integer isAutoTime; // 是否自动同步时间
    private String program; // 当前节目名
    private String programType; // 节目来源类型
    private Integer gpsReportInterval; // GPS上报间隔
    private Integer sensorReportInterval; // 监控上报间隔
    private Integer contentReportStatus; // 内容上报开关
    private String logReport; // 日志上报开关
    private String rotateProgramVsnsReport; // 轮播节目名上报开关
    @Column(columnDefinition = "TEXT")
    private String extra; // 预留扩展字段

    // getter and setter
    public int getSerialNo() { return serialNo; }
    public void setSerialNo(int serialNo) { this.serialNo = serialNo; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }
    public String getLedDescription() { return ledDescription; }
    public void setLedDescription(String ledDescription) { this.ledDescription = ledDescription; }
    public Timestamp getLastHeartbeat() { return lastHeartbeat; }
    public void setLastHeartbeat(Timestamp lastHeartbeat) { this.lastHeartbeat = lastHeartbeat; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getPowerStatus() { return powerStatus; }
    public void setPowerStatus(Integer powerStatus) { this.powerStatus = powerStatus; }
    public Integer getBrightness() { return brightness; }
    public void setBrightness(Integer brightness) { this.brightness = brightness; }
    public Integer getColortemp() { return colortemp; }
    public void setColortemp(Integer colortemp) { this.colortemp = colortemp; }
    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; }
    public String getInputMode() { return inputMode; }
    public void setInputMode(String inputMode) { this.inputMode = inputMode; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getTimezoneId() { return timezoneId; }
    public void setTimezoneId(String timezoneId) { this.timezoneId = timezoneId; }
    public Float getTimezone() { return timezone; }
    public void setTimezone(Float timezone) { this.timezone = timezone; }
    public Integer getIsAutoTime() { return isAutoTime; }
    public void setIsAutoTime(Integer isAutoTime) { this.isAutoTime = isAutoTime; }
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    public String getProgramType() { return programType; }
    public void setProgramType(String programType) { this.programType = programType; }
    public Integer getGpsReportInterval() { return gpsReportInterval; }
    public void setGpsReportInterval(Integer gpsReportInterval) { this.gpsReportInterval = gpsReportInterval; }
    public Integer getSensorReportInterval() { return sensorReportInterval; }
    public void setSensorReportInterval(Integer sensorReportInterval) { this.sensorReportInterval = sensorReportInterval; }
    public Integer getContentReportStatus() { return contentReportStatus; }
    public void setContentReportStatus(Integer contentReportStatus) { this.contentReportStatus = contentReportStatus; }
    public String getLogReport() { return logReport; }
    public void setLogReport(String logReport) { this.logReport = logReport; }
    public String getRotateProgramVsnsReport() { return rotateProgramVsnsReport; }
    public void setRotateProgramVsnsReport(String rotateProgramVsnsReport) { this.rotateProgramVsnsReport = rotateProgramVsnsReport; }
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
}