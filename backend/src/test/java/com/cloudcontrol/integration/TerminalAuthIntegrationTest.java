package com.cloudcontrol.integration;

import com.cloudcontrol.util.TmnlHttpBasicAuthUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 终端认证集成测试
 * 验证完整的Basic认证流程
 */
public class TerminalAuthIntegrationTest {

    @Test
    public void testCompleteBasicAuthFlow() {
        // 模拟终端发送的认证信息
        String username = "testdevice";
        String password = "testpass123";
        
        // 1. 模拟客户端编码过程
        String credentials = username + ":" + password;
        String encoded = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
        String authHeader = "Basic " + encoded;
        
        System.out.println("=== 终端认证流程测试 ===");
        System.out.println("1. 原始凭据: " + credentials);
        System.out.println("2. Base64编码: " + encoded);
        System.out.println("3. 完整认证头: " + authHeader);
        
        // 2. 模拟服务端解码过程
        String[] decodedCredentials = TmnlHttpBasicAuthUtil.decodeBasicAuth(authHeader);
        String decodedUsername = decodedCredentials[0];
        String decodedPassword = decodedCredentials[1];
        
        System.out.println("4. 解码用户名: " + decodedUsername);
        System.out.println("5. 解码密码: " + decodedPassword);
        
        // 3. 验证解码结果
        assertEquals(username, decodedUsername);
        assertEquals(password, decodedPassword);
        
        System.out.println("6. 认证信息验证成功！");
        System.out.println("=========================");
    }
    
    @Test
    public void testAuthHeaderValidation() {
        // 测试各种认证头格式
        String[] validHeaders = {
            "Basic dGVzdDp0ZXN0",  // test:test
            "Basic YWRtaW46cGFzc3dvcmQ=",  // admin:password
            "Basic dXNlckBkb21haW4uY29tOnBhc3M6d29yZA=="  // user@domain.com:pass:word
        };
        
        String[] invalidHeaders = {
            null,
            "",
            "Bearer token123",
            "Basic",
            "Basic invalidbase64",
            "Digest username=\"test\""
        };
        
        System.out.println("=== 认证头验证测试 ===");
        
        // 测试有效认证头
        for (String header : validHeaders) {
            assertDoesNotThrow(() -> {
                String[] result = TmnlHttpBasicAuthUtil.decodeBasicAuth(header);
                assertNotNull(result);
                assertEquals(2, result.length);
                System.out.println("有效认证头: " + header + " -> [" + result[0] + ", " + result[1] + "]");
            });
        }
        
        // 测试无效认证头
        for (String header : invalidHeaders) {
            assertThrows(IllegalArgumentException.class, () -> {
                TmnlHttpBasicAuthUtil.decodeBasicAuth(header);
            });
            System.out.println("无效认证头: " + header + " -> 正确抛出异常");
        }
        
        System.out.println("=========================");
    }
}
