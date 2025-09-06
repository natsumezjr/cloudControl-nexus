package com.cloudcontrol.service.auth;

import com.cloudcontrol.dto.terminal.TerminalLoginRequest;
import com.cloudcontrol.dto.terminal.TerminalLoginResponse;
import com.cloudcontrol.dto.terminal.TerminalRegisterRequest;
import com.cloudcontrol.dto.terminal.TerminalRegisterResponse;
import com.cloudcontrol.entity.terminal.Terminal;
import java.util.Optional;

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


    /**
     * 验证终端Basic认证
     * @param username 用户名（设备账号名）
     * @param password 密码
     * @return 认证成功返回终端信息，失败返回空
     */
    Optional<Terminal> authenticateTerminal(String username, String password);    
} 