package com.cloudcontrol.interceptor;

import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.exception.TerminalAuthenticationException;
import com.cloudcontrol.service.auth.TerminalAuthService;
import com.cloudcontrol.util.TmnlHttpBasicAuthUtil;
import com.cloudcontrol.util.RequestContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import java.util.Optional;

/**
 * 终端认证拦截器
 * 处理终端设备的Basic认证
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
@Component
public class TerminalAuthInterceptor implements HandlerInterceptor {

    private final TerminalAuthService terminalAuthService;

    public TerminalAuthInterceptor(TerminalAuthService terminalAuthService) {
        this.terminalAuthService = terminalAuthService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 检查是否为终端请求（所有终端相关路径）
        if (!isTerminalRequest(request)) {
            return true; // 不是终端请求，直接放行
        }

        // 获取Authorization头
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || authHeader.trim().isEmpty()) {
            throw new TerminalAuthenticationException("缺少认证头");
        }

        try {
            // 使用工具类解码Basic认证
            String[] credentials = TmnlHttpBasicAuthUtil.decodeBasicAuth(authHeader);
            String username = credentials[0];  // 设备账号名
            String password = credentials[1];  // 密码

            // 验证终端身份
            Optional<Terminal> terminalOpt = terminalAuthService.authenticateTerminal(username, password);
            
            if (terminalOpt.isPresent()) {
                // 认证成功，将终端信息存储到请求属性和请求上下文中
                System.out.println("Interceptor：认证成功");
                Terminal terminal = terminalOpt.get();
                // 同时存储到请求上下文中，供服务层使用
                RequestContextUtil.setAuthenticatedTerminal(terminal);
                
                System.out.println("终端认证成功: " + terminal.getDeviceName() + " (" + terminal.getAccountName() + ")");
                return true;
            } else {
                // 认证失败，抛出异常
                throw new TerminalAuthenticationException("认证失败");
            }
            
        } catch (TerminalAuthenticationException e) {
            // 重新抛出认证异常
            throw e;
        } catch (Exception e) {
            // 其他异常包装为认证异常
            throw new TerminalAuthenticationException("认证处理异常: " + e.getMessage(), e);
        }
    }
    
    /**
     * 判断是否为终端请求
     * 所有终端相关的HTTP请求都需要认证
     */
    private boolean isTerminalRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        
        // 终端请求的特征：
        // 1. 路径包含终端相关标识
        // 2. 或者有特定的请求头标识
        // 3. 或者有特定的参数标识
        
        return requestURI.contains("wp-json/wp/v2/comments") ||
               requestURI.contains("/terminal/") ||
               "terminal".equals(request.getParameter("clt_type")) ||
               request.getHeader("X-Client-Type") != null;
    }
}
