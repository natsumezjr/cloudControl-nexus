package com.cloudcontrol.util;

import com.cloudcontrol.entity.terminal.Terminal;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

/**
 * 请求上下文工具类
 * 用于在请求级别存储和获取认证信息
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
public class RequestContextUtil {
    
    /**
     * 认证终端在请求上下文中的属性名
     */
    private static final String AUTHENTICATED_TERMINAL_ATTRIBUTE = "authenticatedTerminal";
    
    /**
     * 设置当前请求的认证终端信息
     * 
     * @param terminal 已认证的终端对象
     */
    public static void setAuthenticatedTerminal(Terminal terminal) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.setAttribute(
            AUTHENTICATED_TERMINAL_ATTRIBUTE, 
            terminal, 
            RequestAttributes.SCOPE_REQUEST
        );
    }
    
    /**
     * 获取当前请求的认证终端信息
     * 
     * @return 已认证的终端对象，如果不存在则返回空Optional
     */
    public static Optional<Terminal> getAuthenticatedTerminal() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Terminal terminal = (Terminal) requestAttributes.getAttribute(
            AUTHENTICATED_TERMINAL_ATTRIBUTE, 
            RequestAttributes.SCOPE_REQUEST
        );
        return Optional.ofNullable(terminal);
    }
    
    /**
     * 清除当前请求的认证终端信息
     */
    public static void clearAuthenticatedTerminal() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.removeAttribute(
            AUTHENTICATED_TERMINAL_ATTRIBUTE, 
            RequestAttributes.SCOPE_REQUEST
        );
    }
    
    /**
     * 检查当前请求是否有认证终端信息
     * 
     * @return 如果存在认证终端信息返回true，否则返回false
     */
    public static boolean hasAuthenticatedTerminal() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Terminal terminal = (Terminal) requestAttributes.getAttribute(
            AUTHENTICATED_TERMINAL_ATTRIBUTE, 
            RequestAttributes.SCOPE_REQUEST
        );
        return terminal != null;
    }
}
