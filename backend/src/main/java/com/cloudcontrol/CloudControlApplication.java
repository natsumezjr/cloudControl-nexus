package com.cloudcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 云平台控制系统启动类
 * 
 * @author CloudControl Team
 * @version 1.0.0
 */
@SpringBootApplication
public class CloudControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudControlApplication.class, args);
        System.out.println("🚀 云平台控制系统启动成功！");
        System.out.println("📖 API文档地址: http://localhost:8080/swagger-ui.html");
        System.out.println("🔍 健康检查地址: http://localhost:8080/actuator/health");
    }
} 