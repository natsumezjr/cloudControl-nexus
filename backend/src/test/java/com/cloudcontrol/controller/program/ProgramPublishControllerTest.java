package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.ProgramPublishRequest;
import com.cloudcontrol.dto.program.ProgramPublishResponse;
import com.cloudcontrol.dto.program.ProgramPublishErrorResponse;
import com.cloudcontrol.service.program.ProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 节目发布控制器测试类
 */
@WebMvcTest(ProgramController.class)
public class ProgramPublishControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testPublishProgramSuccess() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        ProgramPublishResponse response = createMockPublishResponse(1);
        
        // 模拟服务层返回
        when(programService.publishProgram(eq(1), any(ProgramPublishRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.author").value(1))
                .andExpect(jsonPath("$.status").value("publish"));
    }
    
    @Test
    public void testPublishProgramWithoutFlag() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        
        // 执行测试 - 没有flag参数，应该走更新逻辑
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testPublishProgramWithInvalidFlag() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        
        // 执行测试 - 无效的flag参数
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testPublishProgramWithInvalidProgramId() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        
        // 执行测试 - 无效的节目ID
        mockMvc.perform(put("/wp-json/wp/v2/programs/0")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testPublishProgramWithGroupOutOfControl() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        
        // 模拟服务层抛出权限错误
        doThrow(new RuntimeException("some group out of control"))
            .when(programService).publishProgram(eq(1), any(ProgramPublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg").value("some group out of control"))
                .andExpect(jsonPath("$.errorCode").value(20000))
                .andExpect(jsonPath("$.httpCode").value(500));
    }
    
    @Test
    public void testPublishProgramWithNoPermission() throws Exception {
        // 准备测试数据
        ProgramPublishRequest request = createMockPublishRequest();
        
        // 模拟服务层抛出权限错误
        doThrow(new RuntimeException("No permission to control program"))
            .when(programService).publishProgram(eq(1), any(ProgramPublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg").value("No permission to control program"))
                .andExpect(jsonPath("$.errorCode").value(20001))
                .andExpect(jsonPath("$.httpCode").value(403));
    }
    
    @Test
    public void testPublishProgramWithInvalidRequest() throws Exception {
        // 准备测试数据 - 无效的请求体
        String invalidRequest = "{\"invalid\": \"data\"}";
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg").value("Invalid request body for publishing"))
                .andExpect(jsonPath("$.errorCode").value(40005))
                .andExpect(jsonPath("$.httpCode").value(400));
    }
    
    @Test
    public void testPublishProgramWithEmptyTerminalGroups() throws Exception {
        // 准备测试数据 - 空的终端组
        ProgramPublishRequest request = new ProgramPublishRequest();
        request.setWhat("assign_program_to_terminal_group");
        request.setTo(new ProgramPublishRequest.To());
        request.getTo().setTerminalsGroups(Arrays.asList());
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/programs/1")
                .param("flag", "terminalgroup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    /**
     * 创建模拟的发布请求
     */
    private ProgramPublishRequest createMockPublishRequest() {
        ProgramPublishRequest request = new ProgramPublishRequest();
        request.setWhat("assign_program_to_terminal_group");
        
        ProgramPublishRequest.To to = new ProgramPublishRequest.To();
        ProgramPublishRequest.TerminalGroup group = new ProgramPublishRequest.TerminalGroup();
        group.setAll(false);
        group.setId(1);
        group.setTerminals(Arrays.asList(325056));
        
        to.setTerminalsGroups(Arrays.asList(group));
        request.setTo(to);
        
        return request;
    }
    
    /**
     * 创建模拟的发布响应
     */
    private ProgramPublishResponse createMockPublishResponse(Integer programId) {
        return new ProgramPublishResponse(
            programId,
            1,
            "2024-01-15T10:30:00",
            "2024-01-15T10:30:00Z",
            "publish",
            "2024-01-15T11:45:00",
            "2024-01-15T11:45:00Z"
        );
    }
} 