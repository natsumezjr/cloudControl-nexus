package com.cloudcontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.config.Customizer;

/**
 * 默认环境安全配置
 * 当没有指定profile时使用此配置
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    // 仅在dev和test环境下允许分号
    @Profile({"dev", "test"})
    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true); // 允许分号
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 你的安全配置（如有其它配置请补充）
        return http.build();
    }

    // 用Customizer方式注入firewall，兼容Spring Security 6
    @Profile({"dev", "test"})
    @Bean
    public Customizer<WebSecurity> webSecurityCustomizer(HttpFirewall allowSemicolonHttpFirewall) {
        return (web) -> web.httpFirewall(allowSemicolonHttpFirewall);
    }
} 