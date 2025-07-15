package com.cloudcontrol.controller.command;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.cloudcontrol.dto.command.Ftd2BckCommandHttpRequest;
import com.cloudcontrol.serviceImpl.command.FtdBckCommandServiceImpl;

@RestController
@RequestMapping("wp-json/wp/v2/comments")
public class FtdBckCommandController {

    @Autowired
    private FtdBckCommandServiceImpl ftdBckCommandService;

    @PostMapping("{cmdType}Command")
    public ResponseEntity<Object> bck2ftdCommand(@RequestBody Ftd2BckCommandHttpRequest request, @PathVariable String cmdType) {
        return ftdBckCommandService.handleCommand(request, cmdType);
    }
}