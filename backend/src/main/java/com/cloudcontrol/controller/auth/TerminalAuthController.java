package com.cloudcontrol.controller.auth;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalLoginResponse;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;
import com.cloudcontrol.service.terminal.TerminalAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TerminalAuthController {

    private final TerminalAuthService terminalAuthService;

    public TerminalAuthController(TerminalAuthService terminalAuthService) {
        this.terminalAuthService = terminalAuthService;
    }

    @PostMapping("/login")
    public TerminalLoginResponse login(@RequestBody TerminalLoginRequest request) {
        try {
            return terminalAuthService.login(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
