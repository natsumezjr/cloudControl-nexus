package com.cloudcontrol.config;

import com.cloudcontrol.interceptor.TerminalAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 注册拦截器和其他Web相关配置
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TerminalAuthInterceptor terminalAuthInterceptor;

    @Autowired
    public WebConfig(TerminalAuthInterceptor terminalAuthInterceptor) {
        this.terminalAuthInterceptor = terminalAuthInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {

        registry.addInterceptor(terminalAuthInterceptor)
                .addPathPatterns(
                    "/wp-json/wp/v2/comments",  // WordPress API路径
                    "/terminal/**"               // 终端相关路径
                )
                .excludePathPatterns(
                    "/api/**",           // 排除API路径（前端用户接口）
                    "/actuator/**",      // 排除健康检查
                    "/error",            // 排除错误页面
                    "/favicon.ico",      // 排除图标
                    "/swagger-ui/**",    // 排除Swagger UI
                    "/v3/api-docs/**"    // 排除API文档
                );
        
    }
}
