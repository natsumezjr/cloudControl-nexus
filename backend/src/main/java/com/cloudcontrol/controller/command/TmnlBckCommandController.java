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
import jakarta.servlet.http.HttpServletRequest;
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
    public List<Bck2TmnlCommandGetResponse> getCommand(
        @RequestParam("clt_type") String cltType,
        @RequestParam("device_num") String deviceNum,
        @RequestHeader(value = "Cookie", required = false) String cookie,
        HttpServletRequest request) {
        // 打印请求URL
        logger.info("请求URL: {}", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        // 打印所有请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            logger.info("Header: {} = {}", name, request.getHeader(name));
        }
        // 打印请求体（GET一般没有，但为调试完整性保留）
        // 如果需要POST体内容，可在POST方法中加
        // 打印收到的Cookie头，便于调试
        logger.info("Cookie Header: {}", cookie);
        // 移除jsessionId相关处理和日志
        // 构造驼峰风格DTO
        Tmnl2BckCommandGetRequest req = new Tmnl2BckCommandGetRequest();
        req.setCltType(cltType);
        req.setDeviceNum(deviceNum);
        return tmnlBckGetCommandService.handleCommand(req);
    }

    @PostMapping("")
    public Bck2TmnlCommandPostResponse postCommand(@RequestBody Tmnl2BckCommandPostRequest commandRequest) {
        try {
            System.out.println("收到POST请求: parent=" + commandRequest.getCommandId() + ", content=" + commandRequest.getContent());
            return tmnlBckPostCommandService.handleCommand(commandRequest);
        } catch (Exception e) {
            System.out.println("postCommand异常: " + e.getMessage());
            e.printStackTrace();
            // 500响应
            return new Bck2TmnlCommandPostResponse(null, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
