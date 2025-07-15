package com.cloudcontrol.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tmnl2BckCommandGetRequest {
    @JsonProperty("clt_type")
    private String cltType;

    @JsonProperty("device_num")
    private int serialNo;

    // getter/setter
    public String getCltType() { return cltType; }
    public void setCltType(String cltType) { this.cltType = cltType; }

    public int getSerialNo() { return serialNo; }
    public void setSerialNo(int serialNo) { this.serialNo = serialNo; }
}
