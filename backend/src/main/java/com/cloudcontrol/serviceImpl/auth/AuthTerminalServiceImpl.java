package com.cloudcontrol.serviceImpl.auth;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalLoginResponse;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import com.cloudcontrol.service.terminal.TerminalAuthService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthTerminalServiceImpl implements TerminalAuthService {

    private final TerminalRepository terminalRepository;

    public AuthTerminalServiceImpl(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    @Override
    public TerminalLoginResponse login(TerminalLoginRequest request) {
        try {
            // 移除jsessionId相关处理、JsessionIdParser、setJsessionId等逻辑
            Optional<Terminal> optionalTerminal = terminalRepository.findByAccountNameAndPassword(
                request.getAccountName(), 
                request.getPassword()
            );
            
            TerminalLoginResponse response = new TerminalLoginResponse();
            response.setAccountName(request.getAccountName());
            response.setPassword(request.getPassword());
            response.setUrl(request.getUrl());
            response.setStatus(request.getStatus());
            response.setInternet(request.isInternet());
            
            if (optionalTerminal.isPresent()) {
                response.setLogin(true);
                Terminal terminal = optionalTerminal.get();
                terminalRepository.save(terminal);
                // 已移除JSESSIONID相关日志
            } else {
                response.setLogin(false);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public TerminalRegisterResponse register(TerminalRegisterRequest request) {
        try {
            Terminal terminal = new Terminal();
            terminal.setAccountName(request.getAccountName());
            terminal.setPassword(request.getPassword());
            terminal.setDeviceName(request.getDeviceName()); // 设置设备名称（必填）
            terminal.setStatus(request.getStatus()); // 设置状态
            terminalRepository.save(terminal);
            TerminalRegisterResponse response = new TerminalRegisterResponse();
            response.setAccountName(request.getAccountName());
            response.setPassword(request.getPassword());
            response.setUrl(request.getUrl());
            response.setStatus(request.getStatus());
            response.setInternet(request.isInternet());
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}