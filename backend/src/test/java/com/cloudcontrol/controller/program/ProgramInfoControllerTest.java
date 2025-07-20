package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.SimplifyCreateProgramRequest;
import com.cloudcontrol.dto.program.SimplifyCreateProgramResponse;
import com.cloudcontrol.service.program.SimplifyCreateProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 节目信息控制器测试类
 */
@WebMvcTest(ProgramInfoController.class)
public class ProgramInfoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SimplifyCreateProgramService simplifyCreateProgramService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testCreateWebProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestWebProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setUrl("https://www.baidu.com/");
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16989);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createWebProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16989));
    }
    
    @Test
    public void testCreateDocumentProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestDocumentProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setMediaId(16110);
        request.setSyncProgram(1);
        request.setConstrainedProportion(1);
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16990);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createDocumentProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/document")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16990));
    }
    
    @Test
    public void testCreateLineTextProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestTextProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setFontSize(16);
        request.setLineText("测试文本内容");
        request.setLineTextType(1);
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16991);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createLineTextProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/lineText")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16991));
    }
    
    @Test
    public void testCreateClockProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestClockProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setFontSize(16);
        request.setClockType(1);
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16992);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createClockProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/clock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16992));
    }
    
    @Test
    public void testCreateWeatherProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestWeatherProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setFontSize(16);
        request.setRegionName("北京");
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16993);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createWeatherProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16993));
    }
    
    @Test
    public void testCreateCountDownProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestCountDownProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setFontSize(16);
        request.setEndDateTime("2025-01-19 20:43:54");
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16994);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createCountDownProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/countDown")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16994));
    }
    
    @Test
    public void testCreateEnvironmentProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestEnvironmentProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(8000);
        request.setFontSize(16);
        request.setPrefix("当前温度: ");
        request.setSuffix("°C");
        request.setEnvironmentType(22);
        request.setTemperatureShowAsFahrenheit(0);
        request.setTemperatureOffset(5);
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16995);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createEnvironmentProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/environment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16995));
    }
    
    @Test
    public void testCreateRssProgram() throws Exception {
        // 准备测试数据
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestRssProgram");
        request.setScreenWidth(580);
        request.setScreenHeight(256);
        request.setDurationMillis(9000);
        request.setFontSize(18);
        request.setRssUrl("https://www.yahoo.com/news/rss");
        request.setRefreshIntervalMs(600000);
        request.setScrollSpeed(60);
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16996);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createRssProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/rss")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16996));
    }
    
    @Test
    public void testCreateMultiWindowProgram() throws Exception {
        // 准备测试数据 - 多窗口节目
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestMultiWindowProgram");
        request.setScreenWidth(1280);
        request.setScreenHeight(512);
        
        // 这里需要构建regions数组，但为了简化测试，我们使用一个简单的结构
        // 实际测试中应该构建完整的regions对象
        
        SimplifyCreateProgramResponse response = new SimplifyCreateProgramResponse(16997);
        
        // 模拟服务层返回
        when(simplifyCreateProgramService.createMultiWindowProgram(any(SimplifyCreateProgramRequest.class)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.programId").value(16997));
    }
    
    @Test
    public void testCreateWebProgramWithNullRequest() throws Exception {
        // 执行测试 - null请求
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testCreateWebProgramWithMissingRequiredFields() throws Exception {
        // 准备测试数据 - 缺少必填字段
        SimplifyCreateProgramRequest request = new SimplifyCreateProgramRequest();
        request.setProgramName("TestProgram");
        // 缺少url字段
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/simplifyCreate/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 