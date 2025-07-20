package com.cloudcontrol.controller.schedule;

import com.cloudcontrol.dto.schedule.SimplifiedScheduleRequest;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleResponse;
import com.cloudcontrol.dto.schedule.ScheduleErrorResponse;
import com.cloudcontrol.service.schedule.TerminalGroupScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 简化应用排程控制器测试类
 */
@WebMvcTest(TerminalGroupScheduleController.class)
public class SimplifiedScheduleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TerminalGroupScheduleService terminalGroupScheduleService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testApplySimplifiedSchedule() throws Exception {
        // 准备测试数据
        SimplifiedScheduleRequest request = createTestRequest();
        SimplifiedScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.applySimplifiedSchedule(eq(request), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Schedule applied successfully"))
                .andExpect(jsonPath("$.data").value("排程信息"));
    }
    
    @Test
    public void testApplySimplifiedScheduleWithNullRequest() throws Exception {
        // 执行测试 - 空请求体
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testApplySimplifiedScheduleWithNoPermission() throws Exception {
        // 准备测试数据
        SimplifiedScheduleRequest request = createTestRequest();
        
        // 模拟服务层抛出权限异常
        when(terminalGroupScheduleService.applySimplifiedSchedule(eq(request), eq(1)))
            .thenThrow(new RuntimeException("No Permission"));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("No Permission"));
    }
    
    @Test
    public void testApplySimplifiedScheduleWithInvalidCommandType() throws Exception {
        // 准备测试数据 - 无效的指令类型
        SimplifiedScheduleRequest request = createTestRequestWithInvalidCommandType();
        
        // 模拟服务层抛出异常
        when(terminalGroupScheduleService.applySimplifiedSchedule(eq(request), eq(1)))
            .thenThrow(new RuntimeException("Invalid command type: INVALID_TYPE"));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid command type: INVALID_TYPE"));
    }
    
    @Test
    public void testApplySimplifiedScheduleWithBrightnessCommand() throws Exception {
        // 准备测试数据 - 亮度控制指令
        SimplifiedScheduleRequest request = createBrightnessCommandRequest();
        SimplifiedScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.applySimplifiedSchedule(eq(request), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Schedule applied successfully"));
    }
    
    @Test
    public void testApplySimplifiedScheduleWithProgramSchedule() throws Exception {
        // 准备测试数据 - 节目排程
        SimplifiedScheduleRequest request = createProgramScheduleRequest();
        SimplifiedScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.applySimplifiedSchedule(eq(request), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/simplifiedSchedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Schedule applied successfully"));
    }
    
    /**
     * 创建测试请求
     */
    private SimplifiedScheduleRequest createTestRequest() {
        SimplifiedScheduleRequest request = new SimplifiedScheduleRequest();
        request.setTerminalGroupId(3);
        request.setToChildren(false);
        request.setTerminalId(6377);
        
        // 创建指令排程
        List<SimplifiedScheduleRequest.CommandScheduleRequest> commandSchedules = new ArrayList<>();
        SimplifiedScheduleRequest.CommandScheduleRequest commandSchedule = new SimplifiedScheduleRequest.CommandScheduleRequest();
        commandSchedule.setStartDate("2024-01-01");
        commandSchedule.setEndDate("2024-02-23");
        commandSchedule.setOperationTime("20:50:00");
        commandSchedule.setWeeks(Arrays.asList(true, true, true, true, true, true, true));
        commandSchedule.setCommandType("BRIGHTNESS");
        commandSchedule.setValue("99");
        commandSchedules.add(commandSchedule);
        request.setCommandSchedules(commandSchedules);
        
        // 创建节目排程
        List<SimplifiedScheduleRequest.ProgramScheduleRequest> programSchedules = new ArrayList<>();
        SimplifiedScheduleRequest.ProgramScheduleRequest programSchedule = new SimplifiedScheduleRequest.ProgramScheduleRequest();
        programSchedule.setStartDate("2024-01-01");
        programSchedule.setEndDate("2024-02-23");
        programSchedule.setStartTime("20:50:00");
        programSchedule.setEndTime("20:50:00");
        programSchedule.setWeeks(Arrays.asList(true, true, true, true, true, true, true));
        programSchedule.setProgramId(6440);
        programSchedule.setProgramPlayForm(1);
        programSchedule.setProgramPriority(1);
        programSchedules.add(programSchedule);
        request.setProgramSchedules(programSchedules);
        
        return request;
    }
    
    /**
     * 创建无效指令类型的测试请求
     */
    private SimplifiedScheduleRequest createTestRequestWithInvalidCommandType() {
        SimplifiedScheduleRequest request = new SimplifiedScheduleRequest();
        request.setTerminalGroupId(3);
        request.setToChildren(false);
        
        // 创建无效指令排程
        List<SimplifiedScheduleRequest.CommandScheduleRequest> commandSchedules = new ArrayList<>();
        SimplifiedScheduleRequest.CommandScheduleRequest commandSchedule = new SimplifiedScheduleRequest.CommandScheduleRequest();
        commandSchedule.setStartDate("2024-01-01");
        commandSchedule.setEndDate("2024-02-23");
        commandSchedule.setOperationTime("20:50:00");
        commandSchedule.setWeeks(Arrays.asList(true, true, true, true, true, true, true));
        commandSchedule.setCommandType("INVALID_TYPE");
        commandSchedule.setValue("99");
        commandSchedules.add(commandSchedule);
        request.setCommandSchedules(commandSchedules);
        
        request.setProgramSchedules(new ArrayList<>());
        
        return request;
    }
    
    /**
     * 创建亮度控制指令请求
     */
    private SimplifiedScheduleRequest createBrightnessCommandRequest() {
        SimplifiedScheduleRequest request = new SimplifiedScheduleRequest();
        request.setTerminalGroupId(3);
        request.setToChildren(false);
        
        // 创建亮度控制指令排程
        List<SimplifiedScheduleRequest.CommandScheduleRequest> commandSchedules = new ArrayList<>();
        SimplifiedScheduleRequest.CommandScheduleRequest commandSchedule = new SimplifiedScheduleRequest.CommandScheduleRequest();
        commandSchedule.setStartDate("2024-01-01");
        commandSchedule.setEndDate("2024-02-23");
        commandSchedule.setOperationTime("20:50:00");
        commandSchedule.setWeeks(Arrays.asList(true, true, true, true, true, true, true));
        commandSchedule.setCommandType("BRIGHTNESS");
        commandSchedule.setValue("99");
        commandSchedules.add(commandSchedule);
        request.setCommandSchedules(commandSchedules);
        
        request.setProgramSchedules(new ArrayList<>());
        
        return request;
    }
    
    /**
     * 创建节目排程请求
     */
    private SimplifiedScheduleRequest createProgramScheduleRequest() {
        SimplifiedScheduleRequest request = new SimplifiedScheduleRequest();
        request.setTerminalGroupId(3);
        request.setToChildren(false);
        
        request.setCommandSchedules(new ArrayList<>());
        
        // 创建节目排程
        List<SimplifiedScheduleRequest.ProgramScheduleRequest> programSchedules = new ArrayList<>();
        SimplifiedScheduleRequest.ProgramScheduleRequest programSchedule = new SimplifiedScheduleRequest.ProgramScheduleRequest();
        programSchedule.setStartDate("2024-01-01");
        programSchedule.setEndDate("2024-02-23");
        programSchedule.setStartTime("20:50:00");
        programSchedule.setEndTime("20:50:00");
        programSchedule.setWeeks(Arrays.asList(true, true, true, true, true, true, true));
        programSchedule.setProgramId(6440);
        programSchedule.setProgramPlayForm(1);
        programSchedule.setProgramPriority(1);
        programSchedules.add(programSchedule);
        request.setProgramSchedules(programSchedules);
        
        return request;
    }
    
    /**
     * 创建测试响应
     */
    private SimplifiedScheduleResponse createTestResponse() {
        SimplifiedScheduleResponse response = new SimplifiedScheduleResponse();
        response.setStatus(200);
        response.setMessage("Schedule applied successfully");
        response.setData("排程信息");
        
        return response;
    }
} 