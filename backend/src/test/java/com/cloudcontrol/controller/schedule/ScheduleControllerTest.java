package com.cloudcontrol.controller.schedule;

import com.cloudcontrol.dto.schedule.*;
import com.cloudcontrol.service.schedule.TerminalScheduleService;
import com.cloudcontrol.service.schedule.TerminalGroupScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 排程控制器测试类
 */
@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TerminalScheduleService terminalScheduleService;
    
    @MockBean
    private TerminalGroupScheduleService terminalGroupScheduleService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testUpdateTerminalSchedule() throws Exception {
        // 准备测试数据
        Integer terminalId = 211;
        UpdateTerminalScheduleRequest request = createTestRequest();
        TerminalScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalScheduleService.updateTerminalSchedule(eq(terminalId), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", terminalId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.msg").value("Success."))
                .andExpect(jsonPath("$.data.updateTerminalScheduleTime").exists())
                .andExpect(jsonPath("$.data.scheduleJsonBean.to_children").value(false))
                .andExpect(jsonPath("$.data.scheduleJsonBean.program_ids[0]").value(7085240));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithNullRequest() throws Exception {
        // 执行测试 - null请求
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Request body cannot be null"));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithEmptySchedules() throws Exception {
        // 准备测试数据 - 空的排程
        UpdateTerminalScheduleRequest request = new UpdateTerminalScheduleRequest();
        request.setProgram_ids(Arrays.asList(7085240));
        request.setTo_children(false);
        request.setSchedules(new UpdateTerminalScheduleRequest.SchedulesRequest());
        
        // 模拟服务层抛出异常
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenThrow(new RuntimeException("Schedules can not be empty."));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Schedules can not be empty."));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithNoPermission() throws Exception {
        // 准备测试数据
        UpdateTerminalScheduleRequest request = createTestRequest();
        
        // 模拟服务层抛出权限异常
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenThrow(new RuntimeException("No Permission"));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("No Permission"));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithProgramModified() throws Exception {
        // 准备测试数据
        UpdateTerminalScheduleRequest request = createTestRequest();
        
        // 模拟服务层抛出节目修改异常
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenThrow(new RuntimeException("Invalid operation, the program has been modified!"));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("Invalid operation, the program has been modified!"));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithBrightnessControl() throws Exception {
        // 准备测试数据 - 亮度控制指令
        UpdateTerminalScheduleRequest request = createBrightnessControlRequest();
        TerminalScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithVolumeControl() throws Exception {
        // 准备测试数据 - 音量控制指令
        UpdateTerminalScheduleRequest request = createVolumeControlRequest();
        TerminalScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
    
    @Test
    public void testUpdateTerminalScheduleWithRelayControl() throws Exception {
        // 准备测试数据 - 继电器控制指令
        UpdateTerminalScheduleRequest request = createRelayControlRequest();
        TerminalScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalScheduleService.updateTerminalSchedule(eq(211), any(UpdateTerminalScheduleRequest.class), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 211)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
    
    /**
     * 创建测试请求
     */
    private UpdateTerminalScheduleRequest createTestRequest() {
        UpdateTerminalScheduleRequest request = new UpdateTerminalScheduleRequest();
        request.setTo_children(false);
        request.setProgram_ids(Arrays.asList(7085240));
        
        // 创建缩略图
        UpdateTerminalScheduleRequest.ThumbnailRequest thumbnail = new UpdateTerminalScheduleRequest.ThumbnailRequest();
        thumbnail.setId(7085240);
        thumbnail.setSrc("https://cdn.colorlightcloud.com/wp-content/playList/thumbnails/1717486311197_168d5d9d61e4039903ce5898bb75aa98.png");
        request.setThumbnails(Arrays.asList(thumbnail));
        
        // 创建排程
        UpdateTerminalScheduleRequest.SchedulesRequest schedules = new UpdateTerminalScheduleRequest.SchedulesRequest();
        
        // 创建节目排程
        UpdateTerminalScheduleRequest.ContentsScheduleRequest contentsSchedule = new UpdateTerminalScheduleRequest.ContentsScheduleRequest();
        contentsSchedule.setIf_limit_date(true);
        contentsSchedule.setIf_limit_time(true);
        contentsSchedule.setIf_limit_weekday(true);
        
        // 日期限制
        UpdateTerminalScheduleRequest.LimitDateRequest limitDate = new UpdateTerminalScheduleRequest.LimitDateRequest();
        limitDate.setStart("2024-07-08");
        limitDate.setStart_time("00:00:00");
        limitDate.setEnd("2024-07-08");
        limitDate.setEnd_time("23:59:59");
        contentsSchedule.setLimit_date(limitDate);
        
        // 时间限制
        UpdateTerminalScheduleRequest.LimitTimeRequest limitTime = new UpdateTerminalScheduleRequest.LimitTimeRequest();
        limitTime.setStart_time("00:00:00");
        limitTime.setEnd_time("23:59:59");
        contentsSchedule.setLimit_time(limitTime);
        
        // 星期限制
        contentsSchedule.setLimit_weekday(Arrays.asList(true, true, false, false, true, true, true));
        contentsSchedule.setName("Play_Program");
        contentsSchedule.setPriority("0");
        contentsSchedule.setType("rotation");
        contentsSchedule.setType_priority(200);
        
        // 节目操作
        UpdateTerminalScheduleRequest.ContentsOperationRequest operation = new UpdateTerminalScheduleRequest.ContentsOperationRequest();
        operation.setId(7085240);
        operation.setName("Playlist8093");
        operation.setVsn("Playlist8093_2d1a007943e7de0c83b002795aef2d5b_37095.vsn");
        operation.setSource("internet");
        contentsSchedule.setOperation(operation);
        
        schedules.setContentsSchedule(Arrays.asList(contentsSchedule));
        schedules.setCommandSchedule(new ArrayList<>());
        request.setSchedules(schedules);
        
        return request;
    }
    
    /**
     * 创建亮度控制请求
     */
    private UpdateTerminalScheduleRequest createBrightnessControlRequest() {
        UpdateTerminalScheduleRequest request = createTestRequest();
        
        // 添加亮度控制指令
        UpdateTerminalScheduleRequest.CommandScheduleRequest commandSchedule = new UpdateTerminalScheduleRequest.CommandScheduleRequest();
        commandSchedule.setContent(new UpdateTerminalScheduleRequest.CommandContentRequest("Value", 50));
        commandSchedule.setIf_limit_date(true);
        commandSchedule.setIf_limit_weekday(true);
        
        UpdateTerminalScheduleRequest.LimitDateRequest limitDate = new UpdateTerminalScheduleRequest.LimitDateRequest();
        limitDate.setStart("2024-07-08");
        limitDate.setEnd("2024-07-08");
        commandSchedule.setLimit_date(limitDate);
        
        commandSchedule.setLimit_weekday(Arrays.asList(true, true, true, true, true, true, true));
        commandSchedule.setName("Brightness_Control");
        commandSchedule.setOp_time(Arrays.asList("17:29:06"));
        commandSchedule.setType("command");
        
        UpdateTerminalScheduleRequest.CommandOperationRequest operation = new UpdateTerminalScheduleRequest.CommandOperationRequest();
        operation.setAuthor_url("api/brightness");
        operation.setKarma(2);
        operation.setContent("{\"brightness\":\"127\"}");
        commandSchedule.setOperation(operation);
        
        request.getSchedules().setCommandSchedule(Arrays.asList(commandSchedule));
        
        return request;
    }
    
    /**
     * 创建音量控制请求
     */
    private UpdateTerminalScheduleRequest createVolumeControlRequest() {
        UpdateTerminalScheduleRequest request = createTestRequest();
        
        // 添加音量控制指令
        UpdateTerminalScheduleRequest.CommandScheduleRequest commandSchedule = new UpdateTerminalScheduleRequest.CommandScheduleRequest();
        commandSchedule.setContent(new UpdateTerminalScheduleRequest.CommandContentRequest("Value", 30));
        commandSchedule.setIf_limit_date(false);
        commandSchedule.setIf_limit_weekday(false);
        commandSchedule.setName("Volume_Control");
        commandSchedule.setOp_time(Arrays.asList("09:00:00"));
        commandSchedule.setType("command");
        
        UpdateTerminalScheduleRequest.CommandOperationRequest operation = new UpdateTerminalScheduleRequest.CommandOperationRequest();
        operation.setAuthor_url("api/volume");
        operation.setKarma(2);
        operation.setContent("{\"musicvolume\":\"30\"}");
        commandSchedule.setOperation(operation);
        
        request.getSchedules().setCommandSchedule(Arrays.asList(commandSchedule));
        
        return request;
    }
    
    /**
     * 创建继电器控制请求
     */
    private UpdateTerminalScheduleRequest createRelayControlRequest() {
        UpdateTerminalScheduleRequest request = createTestRequest();
        
        // 添加继电器控制指令
        UpdateTerminalScheduleRequest.CommandScheduleRequest commandSchedule = new UpdateTerminalScheduleRequest.CommandScheduleRequest();
        commandSchedule.setContent(new UpdateTerminalScheduleRequest.CommandContentRequest("Relay", "[0,1,0]"));
        commandSchedule.setIf_limit_date(false);
        commandSchedule.setIf_limit_weekday(false);
        commandSchedule.setName("Relay");
        commandSchedule.setOp_time(Arrays.asList("12:00:00"));
        commandSchedule.setType("command");
        
        UpdateTerminalScheduleRequest.CommandOperationRequest operation = new UpdateTerminalScheduleRequest.CommandOperationRequest();
        operation.setAuthor_url("api/relay");
        operation.setKarma(2);
        operation.setContent("[{\"relay\":1,\"delay\":0,\"status\":0},{\"relay\":2,\"delay\":0,\"status\":1},{\"relay\":3,\"delay\":0,\"status\":0}]");
        commandSchedule.setOperation(operation);
        
        request.getSchedules().setCommandSchedule(Arrays.asList(commandSchedule));
        
        return request;
    }
    
    /**
     * 创建测试响应
     */
    private TerminalScheduleResponse createTestResponse() {
        TerminalScheduleResponse response = new TerminalScheduleResponse();
        response.setStatus(200);
        response.setMsg("Success.");
        
        TerminalScheduleResponse.TerminalScheduleData data = new TerminalScheduleResponse.TerminalScheduleData();
        data.setUpdateTerminalScheduleTime(1720669491L);
        
        TerminalScheduleResponse.ScheduleJsonBean scheduleJsonBean = new TerminalScheduleResponse.ScheduleJsonBean();
        scheduleJsonBean.setTo_children(false);
        scheduleJsonBean.setProgram_ids(Arrays.asList(7085240));
        scheduleJsonBean.setThumbnails(new ArrayList<>());
        
        TerminalScheduleResponse.SchedulesResponse schedules = new TerminalScheduleResponse.SchedulesResponse();
        schedules.setCommandSchedule(new ArrayList<>());
        schedules.setContentsSchedule(new ArrayList<>());
        scheduleJsonBean.setSchedules(schedules);
        
        data.setScheduleJsonBean(scheduleJsonBean);
        response.setData(data);
        
        return response;
    }
    
    @Test
    public void testGetTerminalSchedule() throws Exception {
        // 准备测试数据
        Integer terminalId = 123;
        TerminalScheduleResponse response = createGetScheduleResponse();
        
        // 模拟服务层返回
        when(terminalScheduleService.getTerminalSchedule(eq(terminalId), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", terminalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.msg").value("Success."))
                .andExpect(jsonPath("$.data.updateTerminalScheduleTime").exists())
                .andExpect(jsonPath("$.data.scheduleJsonBean.to_children").value(false))
                .andExpect(jsonPath("$.data.scheduleJsonBean.program_ids").isArray())
                .andExpect(jsonPath("$.data.scheduleJsonBean.schedules.commandSchedule[0].name").value("Sleep"))
                .andExpect(jsonPath("$.data.scheduleJsonBean.schedules.commandSchedule[0].op_time[0]").value("05:00:00"));
    }
    
    @Test
    public void testGetTerminalScheduleWithInvalidTerminalId() throws Exception {
        // 执行测试 - 无效的终端ID
        mockMvc.perform(get("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", 0))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Bad Request."));
    }
    
    @Test
    public void testGetTerminalScheduleWithNoPermission() throws Exception {
        // 准备测试数据
        Integer terminalId = 123;
        
        // 模拟服务层抛出权限异常
        when(terminalScheduleService.getTerminalSchedule(eq(terminalId), eq(1)))
            .thenThrow(new RuntimeException("No Permission"));
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v3/schedules/{terminalId}/terminalSchedules", terminalId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("No Permission"));
    }
    
    /**
     * 创建获取排程响应
     */
    private TerminalScheduleResponse createGetScheduleResponse() {
        TerminalScheduleResponse response = new TerminalScheduleResponse();
        response.setStatus(200);
        response.setMsg("Success.");
        
        TerminalScheduleResponse.TerminalScheduleData data = new TerminalScheduleResponse.TerminalScheduleData();
        data.setUpdateTerminalScheduleTime(1695453795L);
        
        TerminalScheduleResponse.ScheduleJsonBean scheduleJsonBean = new TerminalScheduleResponse.ScheduleJsonBean();
        scheduleJsonBean.setTo_children(false);
        scheduleJsonBean.setProgram_ids(new ArrayList<>());
        scheduleJsonBean.setThumbnails(new ArrayList<>());
        
        TerminalScheduleResponse.SchedulesResponse schedules = new TerminalScheduleResponse.SchedulesResponse();
        schedules.setContentsSchedule(new ArrayList<>());
        
        // 创建指令排程
        List<TerminalScheduleResponse.CommandScheduleResponse> commandSchedules = new ArrayList<>();
        
        TerminalScheduleResponse.CommandScheduleResponse sleepCommand = new TerminalScheduleResponse.CommandScheduleResponse();
        sleepCommand.setIf_limit_date(false);
        sleepCommand.setIf_limit_weekday(false);
        
        TerminalScheduleResponse.LimitDateResponse limitDate = new TerminalScheduleResponse.LimitDateResponse();
        limitDate.setStart("2023-09-23");
        limitDate.setEnd("2023-09-23");
        sleepCommand.setLimit_date(limitDate);
        
        sleepCommand.setLimit_weekday(Arrays.asList(true, true, true, true, true, true, true));
        sleepCommand.setName("Sleep");
        sleepCommand.setOp_time(Arrays.asList("05:00:00"));
        sleepCommand.setType("command");
        
        TerminalScheduleResponse.CommandOperationResponse operation = new TerminalScheduleResponse.CommandOperationResponse();
        operation.setAuthor_url("api/action");
        operation.setKarma(1);
        operation.setContent("{\"command\":\"sleep\"}");
        sleepCommand.setOperation(operation);
        
        commandSchedules.add(sleepCommand);
        schedules.setCommandSchedule(commandSchedules);
        
        scheduleJsonBean.setSchedules(schedules);
        data.setScheduleJsonBean(scheduleJsonBean);
        response.setData(data);
        
        return response;
    }

    @Test
    public void testGetTerminalGroupSchedules() throws Exception {
        // 准备测试数据
        Set<Integer> terminalGroupIds = Set.of(21);
        TerminalGroupSchedulesResponse response = createGetTerminalGroupSchedulesResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.getTerminalGroupSchedules(eq(terminalGroupIds), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v3/schedules/terminalGroupSchedules")
                .param("terminalGroupIds", "21"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.msg").value("Success."))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.21.to_children").value(false))
                .andExpect(jsonPath("$.data.21.program_ids").isArray())
                .andExpect(jsonPath("$.data.21.schedules.commandSchedule[0].name").value("Reboot"))
                .andExpect(jsonPath("$.data.21.schedules.commandSchedule[0].op_time[0]").value("11:00:00"));
    }
    
    @Test
    public void testGetTerminalGroupSchedulesWithEmptyIds() throws Exception {
        // 执行测试 - 空的终端组ID
        mockMvc.perform(get("/wp-json/wp/v3/schedules/terminalGroupSchedules")
                .param("terminalGroupIds", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Terminal group IDs cannot be null or empty"));
    }
    
    @Test
    public void testGetTerminalGroupSchedulesWithInvalidIds() throws Exception {
        // 执行测试 - 无效的终端组ID
        mockMvc.perform(get("/wp-json/wp/v3/schedules/terminalGroupSchedules")
                .param("terminalGroupIds", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid terminal group ID: 0"));
    }
    
    @Test
    public void testGetTerminalGroupSchedulesWithNoPermission() throws Exception {
        // 准备测试数据
        Set<Integer> terminalGroupIds = Set.of(21);
        
        // 模拟服务层抛出权限异常
        when(terminalGroupScheduleService.getTerminalGroupSchedules(eq(terminalGroupIds), eq(1)))
            .thenThrow(new RuntimeException("No Permission"));
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v3/schedules/terminalGroupSchedules")
                .param("terminalGroupIds", "21"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("No Permission"));
    }
    
    @Test
    public void testGetTerminalGroupSchedulesWithListError() throws Exception {
        // 准备测试数据
        Set<Integer> terminalGroupIds = Set.of(21);
        
        // 模拟服务层抛出列表错误
        when(terminalGroupScheduleService.getTerminalGroupSchedules(eq(terminalGroupIds), eq(1)))
            .thenThrow(new RuntimeException("List group schedules error"));
        
        // 执行测试
        mockMvc.perform(get("/wp-json/wp/v3/schedules/terminalGroupSchedules")
                .param("terminalGroupIds", "21"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("List group schedules error."));
    }
    
    /**
     * 创建获取终端组排程响应
     */
    private TerminalGroupSchedulesResponse createGetTerminalGroupSchedulesResponse() {
        TerminalGroupSchedulesResponse response = new TerminalGroupSchedulesResponse();
        response.setStatus(200);
        response.setMsg("Success.");
        
        Map<String, TerminalGroupSchedulesResponse.ScheduleJsonBean> data = new HashMap<>();
        
        // 创建终端组21的排程信息
        TerminalGroupSchedulesResponse.ScheduleJsonBean scheduleJsonBean = new TerminalGroupSchedulesResponse.ScheduleJsonBean();
        scheduleJsonBean.setTo_children(false);
        scheduleJsonBean.setProgram_ids(new ArrayList<>());
        scheduleJsonBean.setThumbnails(new ArrayList<>());
        
        // 创建排程信息
        TerminalGroupSchedulesResponse.Schedules schedules = new TerminalGroupSchedulesResponse.Schedules();
        schedules.setContentsSchedule(new ArrayList<>());
        
        // 创建指令排程
        List<TerminalGroupSchedulesResponse.CommandSchedule> commandSchedules = new ArrayList<>();
        
        // 添加重启指令
        TerminalGroupSchedulesResponse.CommandSchedule rebootCommand = new TerminalGroupSchedulesResponse.CommandSchedule();
        rebootCommand.setIf_limit_date(false);
        rebootCommand.setIf_limit_weekday(false);
        
        // 设置日期限制
        TerminalGroupSchedulesResponse.LimitDate limitDate = new TerminalGroupSchedulesResponse.LimitDate();
        limitDate.setStart("2023-09-23");
        limitDate.setEnd("2023-09-23");
        rebootCommand.setLimit_date(limitDate);
        
        // 设置星期限制
        rebootCommand.setLimit_weekday(Arrays.asList(true, true, true, true, true, true, true));
        rebootCommand.setName("Reboot");
        rebootCommand.setOp_time(Arrays.asList("11:00:00"));
        rebootCommand.setType("command");
        
        // 设置操作
        TerminalGroupSchedulesResponse.CommandOperation operation = new TerminalGroupSchedulesResponse.CommandOperation();
        operation.setAuthor_url("api/action");
        operation.setKarma(1);
        operation.setContent("{\"command\":\"reboot\"}");
        rebootCommand.setOperation(operation);
        
        commandSchedules.add(rebootCommand);
        schedules.setCommandSchedule(commandSchedules);
        
        scheduleJsonBean.setSchedules(schedules);
        data.put("21", scheduleJsonBean);
        
        response.setData(data);
        
        return response;
    }
} 