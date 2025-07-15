package com.cloudcontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 开发环境安全配置
 * 允许访问Swagger UI、API文档和所有API端点
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // 允许所有请求
            )
            .csrf(csrf -> csrf.disable()) // 禁用CSRF
            .headers(headers -> headers.frameOptions().disable()) // 允许iframe
            .formLogin(form -> form.disable()) // 禁用表单登录
            .httpBasic(httpBasic -> httpBasic.disable()) // 禁用HTTP Basic
            .logout(logout -> logout.disable()); // 禁用登出
        return http.build();
    }
} 