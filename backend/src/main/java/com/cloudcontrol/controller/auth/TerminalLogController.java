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
public class TerminalLogController {

    private final TerminalAuthService terminalAuthService;

    public TerminalLogController(TerminalAuthService terminalAuthService) {
        this.terminalAuthService = terminalAuthService;
    }

    @PostMapping("/login")
    public TerminalLoginResponse login(@RequestBody TerminalLoginRequest request) {
        return terminalAuthService.login(request);
    }

    @PostMapping("/register")
    public TerminalRegisterResponse register(@RequestBody TerminalRegisterRequest request) {
        return terminalAuthService.register(request);
    }
}
