package com.cloudcontrol.dto.command;


public class Bck2TmnlCommandPostResponse implements CommandResponse {
    //终端根据状态码判断是否执行，200-300之间为允许执行。其他为不执行。
    // 成功则 return ResponseEntity.ok().build();
    // 失败则 return ResponseEntity.status(400).build();
}
