package com.cloudcontrol.service.terminal;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalLoginResponse;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;

/**
 * 终端认证服务接口
 */
public interface TerminalAuthService {
    
    /**
     * 终端登录
     * @param request 登录请求
     * @return 登录响应
     */
    TerminalLoginResponse login(TerminalLoginRequest request);
    
    /**
     * 终端注册
     * @param request 注册请求
     * @return 注册响应
     */
    TerminalRegisterResponse register(TerminalRegisterRequest request);
} 