package com.cloudcontrol.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Base64;

/**
 * 终端HTTP Basic认证工具类测试
 * 验证Basic认证的解码功能
 */
public class TmnlHttpBasicAuthUtilTest {

    @Test
    public void testDecodeBasicAuth_Success() {
        // 测试用例：用户名=testuser，密码=testpass
        String username = "testuser";
        String password = "testpass";
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        String authHeader = "Basic " + encoded;
        
        // 解码验证
        String[] result = TmnlHttpBasicAuthUtil.decodeBasicAuth(authHeader);
        
        assertEquals(2, result.length);
        assertEquals(username, result[0]);
        assertEquals(password, result[1]);
        
        System.out.println("原始凭据: " + credentials);
        System.out.println("Base64编码: " + encoded);
        System.out.println("解码结果: [" + result[0] + ", " + result[1] + "]");
    }
    
    @Test
    public void testDecodeBasicAuth_WithSpecialCharacters() {
        // 测试包含特殊字符的凭据
        String username = "user@domain.com";
        String password = "pass:word#123";
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        String authHeader = "Basic " + encoded;
        
        String[] result = TmnlHttpBasicAuthUtil.decodeBasicAuth(authHeader);
        
        assertEquals(username, result[0]);
        assertEquals(password, result[1]);
        
        System.out.println("特殊字符凭据: " + credentials);
        System.out.println("Base64编码: " + encoded);
        System.out.println("解码结果: [" + result[0] + ", " + result[1] + "]");
    }
    
    @Test
    public void testDecodeBasicAuth_InvalidHeader() {
        // 测试无效的认证头
        assertThrows(IllegalArgumentException.class, () -> {
            TmnlHttpBasicAuthUtil.decodeBasicAuth(null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TmnlHttpBasicAuthUtil.decodeBasicAuth("InvalidHeader");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TmnlHttpBasicAuthUtil.decodeBasicAuth("Bearer token123");
        });
    }
    
    @Test
    public void testDecodeBasicAuth_InvalidFormat() {
        // 测试无效的格式
        assertThrows(IllegalArgumentException.class, () -> {
            TmnlHttpBasicAuthUtil.decodeBasicAuth("Basic invalidbase64");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TmnlHttpBasicAuthUtil.decodeBasicAuth("Basic " + Base64.getEncoder().encodeToString("invalidformat".getBytes()));
        });
    }
    
    @Test
    public void testDecodeBasicAuth_RealWorldExample() {
        // 真实世界的测试用例
        String authHeader = "Basic dGVzdDp0ZXN0"; // "test:test"的Base64编码
        
        String[] result = TmnlHttpBasicAuthUtil.decodeBasicAuth(authHeader);
        
        assertEquals("test", result[0]);
        assertEquals("test", result[1]);
        
        System.out.println("真实测试用例:");
        System.out.println("认证头: " + authHeader);
        System.out.println("解码结果: [" + result[0] + ", " + result[1] + "]");
    }
}
