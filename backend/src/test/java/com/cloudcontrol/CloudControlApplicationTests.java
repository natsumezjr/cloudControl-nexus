package com.cloudcontrol;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;

import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class CloudControlApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ 数据库连接成功: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("❌ 数据库连接失败: " + e.getMessage());
            assert false;
        }
    }

    @Test
    public void testRedisConnection() {
        try {
            String testKey = "test:connect";
            redisTemplate.opsForValue().set(testKey, "ok");
            String value = redisTemplate.opsForValue().get(testKey);
            System.out.println("✅ Redis连接成功，写入值: " + value);
            assert "ok".equals(value);
        } catch (Exception e) {
            System.err.println("❌ Redis连接失败: " + e.getMessage());
            assert false;
        }
    }
} 