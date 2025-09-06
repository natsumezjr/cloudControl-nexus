package com.cloudcontrol.controller.command;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cloudcontrol.service.command.TmnlBckGetCommandService;
import com.cloudcontrol.dto.command.Bck2TmnlCommandGetResponse;
import com.cloudcontrol.dto.command.Tmnl2BckCommandGetRequest;
import com.cloudcontrol.serviceImpl.command.TmnlBckPostCommandService;
import com.cloudcontrol.dto.command.Bck2TmnlCommandPostResponse;
import com.cloudcontrol.dto.command.Tmnl2BckCommandPostRequest;
import com.cloudcontrol.dto.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("wp-json/wp/v2/comments") //测试其它bug，先注释
public class TmnlBckCommandController {
    private TmnlBckGetCommandService<Tmnl2BckCommandGetRequest, Bck2TmnlCommandGetResponse> tmnlBckGetCommandService;
    private TmnlBckPostCommandService tmnlBckPostCommandService;
    public TmnlBckCommandController(TmnlBckGetCommandService<Tmnl2BckCommandGetRequest, Bck2TmnlCommandGetResponse> tmnlBckGetCommandService, TmnlBckPostCommandService tmnlBckPostCommandService) {
        this.tmnlBckGetCommandService = tmnlBckGetCommandService;
        this.tmnlBckPostCommandService = tmnlBckPostCommandService;
    }

    // 在类内添加logger
    private static final Logger logger = LoggerFactory.getLogger(TmnlBckCommandController.class);

    @GetMapping("")
    public ResponseEntity<?> getCommand(
        @RequestParam("clt_type") String cltType,
        @RequestParam("device_num") String deviceNum,
        @RequestHeader(value = "Cookie", required = false) String cookie,
        HttpServletRequest request) {
        
        // 获取认证的终端信息（拦截器已处理认证）
        String terminalDeviceName = (String) request.getAttribute("terminalDeviceName");
        String terminalAccountName = (String) request.getAttribute("terminalAccountName");
        
        logger.info("认证终端信息: 设备名={}, 账号名={}", terminalDeviceName, terminalAccountName);
        
        // 打印请求URL
        logger.info("请求URL: {}", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        // 打印所有请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            logger.info("Header: {} = {}", name, request.getHeader(name));
        }
        // 打印收到的Cookie头，便于调试
        logger.info("Cookie Header: {}", cookie);
        
        // 构造驼峰风格DTO
        Tmnl2BckCommandGetRequest req = new Tmnl2BckCommandGetRequest();
        req.setCltType(cltType);
        req.setDeviceNum(deviceNum);
        List<Bck2TmnlCommandGetResponse> result = tmnlBckGetCommandService.handleCommand(req);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> postCommand(
        @RequestBody Tmnl2BckCommandPostRequest commandRequest,
        HttpServletRequest request) {
        
        // 获取认证的终端信息（拦截器已处理认证）
        String terminalDeviceName = (String) request.getAttribute("terminalDeviceName");
        String terminalAccountName = (String) request.getAttribute("terminalAccountName");
        
        logger.info("认证终端信息: 设备名={}, 账号名={}", terminalDeviceName, terminalAccountName);
        
        try {
            logger.info("收到POST请求: parent=" + commandRequest.getCommandId() + ", content=" + commandRequest.getContent());
            Bck2TmnlCommandPostResponse result = tmnlBckPostCommandService.handleCommand(commandRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("postCommand异常: " + e.getMessage(), e);
            // 让全局异常处理器处理
            throw new RuntimeException("处理POST请求时发生异常", e);
        }
    }

}
