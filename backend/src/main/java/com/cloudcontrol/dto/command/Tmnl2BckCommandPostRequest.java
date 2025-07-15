package com.cloudcontrol.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tmnl2BckCommandPostRequest implements CommandRequest {
    //@RequestParam("post")
    //private int terminalId;

    @JsonProperty("parent")
    private Integer parent; // 指令id

    @JsonProperty("content")
    private String content; // 设备校验指令结果

    // getter/setter
    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
