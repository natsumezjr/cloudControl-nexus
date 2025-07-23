package com.cloudcontrol.controller.command;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.cloudcontrol.dto.command.Ftd2BckCommandHttpRequest;
import com.cloudcontrol.serviceImpl.command.FtdBckCommandServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("wp-json/wp/v2/comments")
public class FtdBckCommandController {

    private static final Logger logger = LoggerFactory.getLogger(FtdBckCommandController.class);

    @Autowired
    private FtdBckCommandServiceImpl ftdBckCommandService;

    public FtdBckCommandController() {
        logger.info("FtdBckCommandController 构造方法被调用，控制器已实例化");
    }

    @PostMapping("{cmdType}Command")
    public ResponseEntity<Object> bck2ftdCommand(@RequestBody Ftd2BckCommandHttpRequest request, @PathVariable String cmdType) {
        logger.info("收到POST请求: /wp-json/wp/v2/comments/{}Command", cmdType);
        logger.info("请求体: {}", request);
        ResponseEntity<Object> resp = ftdBckCommandService.handleCommand(request, cmdType);
        logger.info("返回响应: {}", resp);
        return resp;
    }
}