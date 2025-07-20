package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.ProgramBatchDeleteRequest;
import com.cloudcontrol.dto.program.ProgramBatchDeleteResponse;
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
 * 批量删除节目控制器测试类
 */
@WebMvcTest(ProgramController.class)
public class ProgramBatchDeleteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testBatchDeleteProgramsSuccess() throws Exception {
        // 准备测试数据
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList(239957, 239958));
        
        List<ProgramBatchDeleteResponse> responses = Arrays.asList(
            createMockBatchDeleteResponse(239957, "PickSystem23333-sgy-test"),
            createMockBatchDeleteResponse(239958, "TestProgram")
        );
        
        // 模拟服务层返回
        when(programService.batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1)))
            .thenReturn(responses);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(239957))
                .andExpect(jsonPath("$[0].title_raw").value("PickSystem23333-sgy-test"))
                .andExpect(jsonPath("$[0].author").value(1))
                .andExpect(jsonPath("$[0].type").value("program"))
                .andExpect(jsonPath("$[1].id").value(239958))
                .andExpect(jsonPath("$[1].title_raw").value("TestProgram"));
    }
    
    @Test
    public void testBatchDeleteProgramsWithEmptyRequest() throws Exception {
        // 准备测试数据 - 空的请求
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList());
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithNullRequest() throws Exception {
        // 执行测试 - null请求
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithInvalidIds() throws Exception {
        // 准备测试数据 - 无效的ID
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList(0, -1));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Invalid program ID: 0"))
            .when(programService).batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithDuplicateIds() throws Exception {
        // 准备测试数据 - 重复的ID
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList(239957, 239957));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("Duplicate program IDs are not allowed"))
            .when(programService).batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithNoPermission() throws Exception {
        // 准备测试数据
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList(239957));
        
        // 模拟服务层抛出权限错误
        doThrow(new RuntimeException("No permission to delete programs"))
            .when(programService).batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithNoProgramsFound() throws Exception {
        // 准备测试数据
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(Arrays.asList(999999));
        
        // 模拟服务层抛出异常
        doThrow(new RuntimeException("No programs found to delete"))
            .when(programService).batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1));
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testBatchDeleteProgramsWithLargeList() throws Exception {
        // 准备测试数据 - 大量节目ID
        List<Integer> largeIdList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ProgramBatchDeleteRequest request = new ProgramBatchDeleteRequest();
        request.setProgramIds(largeIdList);
        
        List<ProgramBatchDeleteResponse> responses = largeIdList.stream()
            .map(id -> createMockBatchDeleteResponse(id, "Program" + id))
            .collect(java.util.stream.Collectors.toList());
        
        // 模拟服务层返回
        when(programService.batchDeletePrograms(any(ProgramBatchDeleteRequest.class), eq(1)))
            .thenReturn(responses);
        
        // 执行测试
        mockMvc.perform(post("/wp-json/wp/v2/programs/delprograms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(org.hamcrest.Matchers.hasSize(10)));
    }
    
    /**
     * 创建模拟的批量删除响应
     */
    private ProgramBatchDeleteResponse createMockBatchDeleteResponse(Integer id, String title) {
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