package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.ProgramUnpublishRequest;
import com.cloudcontrol.dto.program.ProgramBatchDeleteResponse;
import com.cloudcontrol.dto.program.ProgramUnpublishErrorResponse;
import com.cloudcontrol.service.program.ProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 删除播放盒节目控制器测试类
 */
@WebMvcTest(ProgramController.class)
public class ProgramUnpublishControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testUnpublishProgramSuccess() throws Exception {
        // 准备测试数据
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList(91));
        
        ProgramBatchDeleteResponse response = createMockUnpublishResponse(917, "TestProgram");
        
        // 模拟服务层返回
        when(programService.unpublishProgram(any(ProgramUnpublishRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(917))
                .andExpect(jsonPath("$.title_raw").value("TestProgram"))
                .andExpect(jsonPath("$.author").value(1))
                .andExpect(jsonPath("$.type").value("program"));
    }
    
    @Test
    public void testUnpublishProgramWithVsnMd5() throws Exception {
        // 准备测试数据 - 使用vsnMd5而不是programId
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setVsnMd5("b4fb664df98ec39d8ddf30d72ca85ad3");
        request.setTerminalIds(Arrays.asList(91, 92));
        
        ProgramBatchDeleteResponse response = createMockUnpublishResponse(917, "TestProgram");
        
        // 模拟服务层返回
        when(programService.unpublishProgram(any(ProgramUnpublishRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(917));
    }
    
    @Test
    public void testUnpublishProgramWithNullRequest() throws Exception {
        // 执行测试 - null请求
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Request body cannot be null"));
    }
    
    @Test
    public void testUnpublishProgramWithEmptyTerminalIds() throws Exception {
        // 准备测试数据 - 空的终端ID
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList());
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Terminal IDs cannot be null or empty"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400));
    }
    
    @Test
    public void testUnpublishProgramWithInvalidTerminalIds() throws Exception {
        // 准备测试数据 - 无效的终端ID
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList(0, -1));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Invalid terminal ID: 0"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400));
    }
    
    @Test
    public void testUnpublishProgramWithNoProgramIdAndVsnMd5() throws Exception {
        // 准备测试数据 - 既没有programId也没有vsnMd5
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setTerminalIds(Arrays.asList(91));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Either programId or vsnMd5 must be provided"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400));
    }
    
    @Test
    public void testUnpublishProgramWithUnauthorized() throws Exception {
        // 准备测试数据
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList(91));
        
        // 模拟服务层抛出权限错误
        doThrow(new RuntimeException("No permission to unpublish program"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Unauthorized"));
    }
    
    @Test
    public void testUnpublishProgramWithNoPermission() throws Exception {
        // 准备测试数据
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList(91));
        
        // 模拟服务层抛出权限错误
        doThrow(new RuntimeException("No permission to control program"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(50000))
                .andExpect(jsonPath("$.message").value("No permission."));
    }
    
    @Test
    public void testUnpublishProgramWithProgramNotFound() throws Exception {
        // 准备测试数据
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(999999);
        request.setTerminalIds(Arrays.asList(91));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Program not found"))
            .when(programService).unpublishProgram(any(ProgramUnpublishRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400));
    }
    
    @Test
    public void testUnpublishProgramWithMultipleTerminals() throws Exception {
        // 准备测试数据 - 多个终端
        ProgramUnpublishRequest request = new ProgramUnpublishRequest();
        request.setProgramId(917);
        request.setTerminalIds(Arrays.asList(91, 92, 93, 94, 95));
        
        ProgramBatchDeleteResponse response = createMockUnpublishResponse(917, "TestProgram");
        
        // 模拟服务层返回
        when(programService.unpublishProgram(any(ProgramUnpublishRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(delete("/wp-json/wp/v2/programPublishRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(917));
    }
    
    /**
     * 创建模拟的取消发布响应
     */
    private ProgramBatchDeleteResponse createMockUnpublishResponse(Integer id, String title) {
        return new ProgramBatchDeleteResponse(
            id,
            1,
            "2020-12-26 02:48:25",
            "2020-12-26 02:48:25",
            "",
            title,
            "",
            "publish",
            "",
            title,
            "",
            "",
            "2022-08-15 02:34:39",
            "2022-08-15 02:34:39",
            "",
            "program",
            title + "_e11cd93be3d8454db846b5fe1e0b8c5a_1975",
            null,
            0,
            "archives/program/" + title.toLowerCase().replaceAll("\\s+", "-"),
            "",
            null,
            new Object[0],
            new ProgramBatchDeleteResponse.Embedded(new Object[0]),
            new ProgramBatchDeleteResponse.Title(null),
            null
        );
    }
} 