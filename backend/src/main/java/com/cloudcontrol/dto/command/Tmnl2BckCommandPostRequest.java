package com.cloudcontrol.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tmnl2BckCommandPostRequest implements CommandRequest {
    //@RequestParam("post")
    //private int terminalId;

    /* 
     * eg:
     * {
     * "parent": 999,
     * "content": "Executable comment"
     * }
     */

    @JsonProperty("parent")
    private Integer commandId; // 指令id

    @JsonProperty("content")
    private String content; // 设备校验指令结果

    // getter/setter
    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
