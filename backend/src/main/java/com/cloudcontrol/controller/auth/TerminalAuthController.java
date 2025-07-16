package com.cloudcontrol.controller.auth;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;
import com.cloudcontrol.service.terminal.TerminalAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api")
public class TerminalAuthController {

    private final TerminalAuthService terminalAuthService;
    private static final Logger logger = LoggerFactory.getLogger(TerminalAuthController.class);

    public TerminalAuthController(TerminalAuthService terminalAuthService) {
        this.terminalAuthService = terminalAuthService;
    }

    @PostMapping("/login") //测试其它bug，先注释
    public ResponseEntity<?> terminalLogin(@RequestBody TerminalLoginRequest request, HttpServletRequest httpRequest) {
        // 打印分隔线，便于日志快速定位
        logger.info("==================== /api/login 被访问 ====================");
        // 打印所有请求头
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} = {}", headerName, httpRequest.getHeader(headerName));
        }
        // 获取Cookie头
        String cookieHeader = httpRequest.getHeader("Cookie");
        logger.info("Cookie Header: {}", cookieHeader);

        // 移除jsessionId相关处理和日志
        // ...后续用jsessionId做认证
        // ...
        return null;
    }

    @PostMapping("/register")
    public TerminalRegisterResponse register(@RequestBody TerminalRegisterRequest request) {
        try {
            return terminalAuthService.register(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
