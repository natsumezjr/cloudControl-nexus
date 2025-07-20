package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.ProgramCreateRequest;
import com.cloudcontrol.dto.program.ProgramListResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 节目控制器测试类
 */
@WebMvcTest(ProgramController.class)
public class ProgramControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testGetProgramList() throws Exception {
        // 准备测试数据
        List<ProgramListResponse> mockPrograms = Arrays.asList(
            createMockProgramListResponse(1, "Test Program 1"),
            createMockProgramListResponse(2, "Test Program 2")
        );
        
        // 模拟服务层返回
        when(programService.getProgramList(anyInt(), anyInt(), anyString(), anyString(), 
                                          any(), anyString(), anyString(), anyString(), 
                                          anyString(), any())).thenReturn(mockPrograms);
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v2/programs")
                .param("page", "1")
                .param("per_page", "12")
                .param("status", "publish")
                .param("search", "test")
                .param("orderby", "modified"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title.rendered").value("Test Program 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title.rendered").value("Test Program 2"));
    }
    
    @Test
    public void testGetProgramListWithDefaultParams() throws Exception {
        // 准备测试数据
        List<ProgramListResponse> mockPrograms = Arrays.asList(
            createMockProgramListResponse(1, "Default Program")
        );
        
        // 模拟服务层返回
        when(programService.getProgramList(1, 12, null, null, null, null, null, null, null, null))
            .thenReturn(mockPrograms);
        
        // 执行测试 - 不传任何参数
        mockMvc.perform(get("/wp-json/wp/v2/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title.rendered").value("Default Program"));
    }
    
    @Test
    public void testGetProgramListWithInvalidParams() throws Exception {
        // 执行测试 - 无效的分页参数
        mockMvc.perform(get("/wp-json/wp/v2/programs")
                .param("page", "0")
                .param("per_page", "1000"))
                .andExpect(status().isOk()); // 应该使用默认值而不是返回错误
    }
    
    @Test
    public void testGetProgramListWithTimeRange() throws Exception {
        // 准备测试数据
        List<ProgramListResponse> mockPrograms = Arrays.asList(
            createMockProgramListResponse(1, "Time Range Program")
        );
        
        // 模拟服务层返回
        when(programService.getProgramList(anyInt(), anyInt(), anyString(), anyString(), 
                                          any(), anyString(), anyString(), anyString(), 
                                          anyString(), any())).thenReturn(mockPrograms);
        
        // 执行测试 - 时间范围查询
        mockMvc.perform(get("/wp-json/wp/v2/programs")
                .param("after", "2024-01-01T00:00:00")
                .param("before", "2024-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void testGetProgramListWithAuthorFilter() throws Exception {
        // 准备测试数据
        List<ProgramListResponse> mockPrograms = Arrays.asList(
            createMockProgramListResponse(1, "Author Program")
        );
        
        // 模拟服务层返回
        when(programService.getProgramList(anyInt(), anyInt(), anyString(), anyString(), 
                                          eq(1), anyString(), anyString(), anyString(), 
                                          anyString(), any())).thenReturn(mockPrograms);
        
        // 执行测试 - 作者筛选
        mockMvc.perform(get("/wp-json/wp/v2/programs")
                .param("author", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void testGetProgramListWithOrderBy() throws Exception {
        // 准备测试数据
        List<ProgramListResponse> mockPrograms = Arrays.asList(
            createMockProgramListResponse(1, "Ordered Program")
        );
        
        // 模拟服务层返回
        when(programService.getProgramList(anyInt(), anyInt(), anyString(), anyString(), 
                                          any(), anyString(), eq("programName"), anyString(), 
                                          anyString(), any())).thenReturn(mockPrograms);
        
        // 执行测试 - 按节目名排序
        mockMvc.perform(get("/wp-json/wp/v2/programs")
                .param("orderby", "programName"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    /**
     * 创建模拟的节目列表响应对象
     */
    private ProgramListResponse createMockProgramListResponse(Integer id, String title) {
        ProgramListResponse response = new ProgramListResponse();
        response.setId(id);
        response.setTitleRaw(title);
        response.setStatus("publish");
        response.setAuthor(1);
        response.setType("program");
        response.setDate("2024-01-15T10:30:00");
        response.setDateGmt("2024-01-15T10:30:00Z");
        response.setModified("2024-01-15T11:45:00");
        response.setModifiedGmt("2024-01-15T11:45:00Z");
        response.setVsnName("Playlist" + id + "_test.vsn");
        response.setSlug("playlist_" + id + "_test");
        response.setProgramSourceThumbnail("");
        response.setPublishedBySchedule(false);
        response.setProgramInfo(new Object());
        
        // 设置标题对象
        ProgramListResponse.Title titleObj = new ProgramListResponse.Title(title);
        response.setTitle(titleObj);
        
        // 设置作者信息
        ProgramListResponse.Author author = new ProgramListResponse.Author("superroot");
        ProgramListResponse.Embedded embedded = new ProgramListResponse.Embedded(Arrays.asList(author));
        response.setEmbedded(embedded);
        
        // 设置终端组
        response.setTerminalsGroups(Arrays.asList());
        
        return response;
    }
} 