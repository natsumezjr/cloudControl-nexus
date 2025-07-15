package com.cloudcontrol.service.auth;

/**
 * 通用认证服务接口
 * @param <T> 实体类型
 * @param <R> 请求类型
 * @param <S> 响应类型
 */
public interface AuthService<T, R, S> {
    
    /**
     * 登录认证
     * @param request 登录请求
     * @return 登录响应
     */
    S login(R request);
    
    /**
     * 注册
     * @param request 注册请求
     * @return 注册响应
     */
    S register(R request);
}

