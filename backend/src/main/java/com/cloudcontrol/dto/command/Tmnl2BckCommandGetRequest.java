package com.cloudcontrol.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 设备获取指令接口请求参数DTO
 * 注意：为兼容接口参数风格，字段名采用下划线命名，违反Java驼峰规范
 */
public class Tmnl2BckCommandGetRequest implements CommandRequest {
    private String cltType;
    private String deviceNum;

    public String getCltType() { return cltType; }
    public void setCltType(String cltType) { this.cltType = cltType; }
    public String getDeviceNum() { return deviceNum; }
    public void setDeviceNum(String deviceNum) { this.deviceNum = deviceNum; }
}
