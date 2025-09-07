package com.cloudcontrol.serviceImpl.auth;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalLoginResponse;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import com.cloudcontrol.service.auth.TerminalAuthService;

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
            String accountName = request.getAccountName();
            String password = request.getPassword();
            String deviceName = request.getDeviceName();
            String status = request.getStatus();
            boolean internet = request.isInternet();
            String url = request.getUrl();


            // 调试日志
            if (terminalRepository.findByDeviceName(deviceName).isPresent() || terminalRepository.findByAccountNameAndPassword(accountName, password).isPresent()) {
                throw new RuntimeException("终端已存在，请勿重复注册");
            }

            Terminal terminal = new Terminal();
            terminal.setAccountName(accountName);
            terminal.setPassword(password);
            terminal.setDeviceName(deviceName); // 设置设备名称（必填）
            terminal.setStatus(status); // 设置状态
            terminalRepository.save(terminal);
            TerminalRegisterResponse response = new TerminalRegisterResponse();
            response.setAccountName(accountName);
            response.setPassword(password);
            response.setUrl(url);
            response.setStatus(status);
            response.setInternet(internet);
            response.setSuccess(true);
            response.setSerialNo(terminal.getSerialNo());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<Terminal> authenticateTerminal(String username, String password) {
        return terminalRepository.findByAccountNameAndPassword(username, password);
    }
}