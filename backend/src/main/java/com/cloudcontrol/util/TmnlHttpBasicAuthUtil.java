package com.cloudcontrol.util;

import java.util.Base64;

/**
 * 终端HTTP Basic认证工具类
 * 
 * @author CloudControl-Nexus-Bupt zjr
 * @version 1.0.0
 * @since 2025-09-06
 */
public class TmnlHttpBasicAuthUtil {


    private static final String BASIC_PREFIX = "Basic ";
    private static final String CONNECTOR = ":";

    /**
     * 解码Basic认证信息
     * 
     * @param authHeader 认证头
     * @return 解码后的用户名和密码 [0]用户名, [1]密码
     */
    public static String[] decodeBasicAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            throw new IllegalArgumentException("无效的认证头");
        }

        String base64Encoded = authHeader.substring(BASIC_PREFIX.length());

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
            String decodedString = new String(decodedBytes);
            String[] parts = decodedString.split(CONNECTOR);
            if (parts.length != 2) {
                throw new IllegalArgumentException("无效的认证头，用户名和密码必须用冒号分隔");
            }
            return parts;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无效的认证头");
        }
    }
}