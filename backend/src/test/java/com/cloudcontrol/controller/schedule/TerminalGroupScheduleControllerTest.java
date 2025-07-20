package com.cloudcontrol.controller.schedule;

import com.cloudcontrol.dto.schedule.ApplyTerminalGroupScheduleRequest;
import com.cloudcontrol.dto.schedule.ScheduleErrorResponse;
import com.cloudcontrol.dto.schedule.TerminalGroupScheduleResponse;
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
 * 终端组排程控制器测试类
 */
@WebMvcTest(TerminalGroupScheduleController.class)
public class TerminalGroupScheduleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TerminalGroupScheduleService terminalGroupScheduleService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testApplyTerminalGroupSchedule() throws Exception {
        // 准备测试数据
        Integer terminalGroupId = 11924;
        ApplyTerminalGroupScheduleRequest request = createTestRequest();
        TerminalGroupScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.applyTerminalGroupSchedule(eq(terminalGroupId), eq(request), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/{terminalGroupId}", terminalGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(11924))
                .andExpect(jsonPath("$.name").value("测试终端47"))
                .andExpect(jsonPath("$.taxonomy").value("terminalgroup"))
                .andExpect(jsonPath("$.leds").isArray())
                .andExpect(jsonPath("$.leds[0].led_id").value(24607))
                .andExpect(jsonPath("$.leds[0].led_name").value("zora"));
    }
    
    @Test
    public void testApplyTerminalGroupScheduleWithNullRequest() throws Exception {
        // 执行测试 - 空请求体
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/{terminalGroupId}", 11924)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testApplyTerminalGroupScheduleWithInvalidTerminalGroupId() throws Exception {
        // 准备测试数据
        ApplyTerminalGroupScheduleRequest request = createTestRequest();
        
        // 执行测试 - 无效的终端组ID
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/{terminalGroupId}", 0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Bad Request."));
    }
    
    @Test
    public void testApplyTerminalGroupScheduleWithNoPermission() throws Exception {
        // 准备测试数据
        Integer terminalGroupId = 11924;
        ApplyTerminalGroupScheduleRequest request = createTestRequest();
        
        // 模拟服务层抛出权限异常
        when(terminalGroupScheduleService.applyTerminalGroupSchedule(eq(terminalGroupId), eq(request), eq(1)))
            .thenThrow(new RuntimeException("No Permission"));
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/{terminalGroupId}", terminalGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("No Permission"));
    }
    
    @Test
    public void testApplyTerminalGroupScheduleWithBrightnessControl() throws Exception {
        // 准备测试数据
        Integer terminalGroupId = 11924;
        ApplyTerminalGroupScheduleRequest request = createBrightnessControlRequest();
        TerminalGroupScheduleResponse response = createTestResponse();
        
        // 模拟服务层返回
        when(terminalGroupScheduleService.applyTerminalGroupSchedule(eq(terminalGroupId), eq(request), eq(1)))
            .thenReturn(response);
        
        // 执行测试
        mockMvc.perform(put("/wp-json/wp/v2/terminalgroup/{terminalGroupId}", terminalGroupId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11924))
                .andExpect(jsonPath("$.name").value("测试终端47"));
    }
    
    /**
     * 创建测试请求
     */
    private ApplyTerminalGroupScheduleRequest createTestRequest() {
        ApplyTerminalGroupScheduleRequest request = new ApplyTerminalGroupScheduleRequest();
        request.setTo_children(false);
        request.setProgram_ids(Arrays.asList(24585));
        
        // 创建缩略图
        List<ApplyTerminalGroupScheduleRequest.ThumbnailRequest> thumbnails = new ArrayList<>();
        ApplyTerminalGroupScheduleRequest.ThumbnailRequest thumbnail = new ApplyTerminalGroupScheduleRequest.ThumbnailRequest();
        thumbnail.setId(24585);
        thumbnail.setSrc("http://192.168.1.30:80/wp-content/playList/thumbnails/1719993542936_9fc6299b9f6fc213063cf638d833a806.png");
        thumbnails.add(thumbnail);
        request.setThumbnails(thumbnails);
        
        // 创建排程
        ApplyTerminalGroupScheduleRequest.SchedulesRequest schedules = new ApplyTerminalGroupScheduleRequest.SchedulesRequest();
        
        // 创建节目排程
        List<ApplyTerminalGroupScheduleRequest.ContentsScheduleRequest> contentsSchedules = new ArrayList<>();
        ApplyTerminalGroupScheduleRequest.ContentsScheduleRequest contentsSchedule = new ApplyTerminalGroupScheduleRequest.ContentsScheduleRequest();
        contentsSchedule.setIf_limit_date(true);
        contentsSchedule.setIf_limit_time(true);
        contentsSchedule.setIf_limit_weekday(true);
        
        // 日期限制
        ApplyTerminalGroupScheduleRequest.LimitDateRequest limitDate = new ApplyTerminalGroupScheduleRequest.LimitDateRequest();
        limitDate.setStart("2024-07-04");
        limitDate.setStart_time("00:00:00");
        limitDate.setEnd("2024-07-04");
        limitDate.setEnd_time("23:59:59");
        contentsSchedule.setLimit_date(limitDate);
        
        // 时间限制
        ApplyTerminalGroupScheduleRequest.LimitTimeRequest limitTime = new ApplyTerminalGroupScheduleRequest.LimitTimeRequest();
        limitTime.setStart_time("00:00:00");
        limitTime.setEnd_time("23:59:59");
        contentsSchedule.setLimit_time(limitTime);
        
        // 星期限制
        contentsSchedule.setLimit_weekday(Arrays.asList(true, true, true, false, false, true, true));
        contentsSchedule.setName("Play_Program");
        contentsSchedule.setPriority("0");
        contentsSchedule.setType("rotation");
        contentsSchedule.setType_priority(200);
        
        // 节目操作
        ApplyTerminalGroupScheduleRequest.ContentsOperationRequest operation = new ApplyTerminalGroupScheduleRequest.ContentsOperationRequest();
        operation.setId(24585);
        operation.setName("Playlist123");
        operation.setVsn("Playlist123_e41b5d853d35329072b75dd77ac54810_2033.vsn");
        operation.setSource("internet");
        contentsSchedule.setOperation(operation);
        
        contentsSchedules.add(contentsSchedule);
        schedules.setContentsSchedule(contentsSchedules);
        schedules.setCommandSchedule(new ArrayList<>());
        request.setSchedules(schedules);
        
        return request;
    }
    
    /**
     * 创建亮度控制请求
     */
    private ApplyTerminalGroupScheduleRequest createBrightnessControlRequest() {
        ApplyTerminalGroupScheduleRequest request = createTestRequest();
        
        // 添加亮度控制指令
        ApplyTerminalGroupScheduleRequest.CommandScheduleRequest commandSchedule = new ApplyTerminalGroupScheduleRequest.CommandScheduleRequest();
        commandSchedule.setContent(new ApplyTerminalGroupScheduleRequest.CommandContentRequest("Value", 72));
        commandSchedule.setIf_limit_date(true);
        commandSchedule.setIf_limit_weekday(false);
        
        ApplyTerminalGroupScheduleRequest.LimitDateRequest limitDate = new ApplyTerminalGroupScheduleRequest.LimitDateRequest();
        limitDate.setStart("2024-07-04");
        limitDate.setEnd("2024-07-04");
        commandSchedule.setLimit_date(limitDate);
        
        commandSchedule.setLimit_weekday(Arrays.asList(true, true, true, true, true, true, true));
        commandSchedule.setName("Brightness_Control");
        commandSchedule.setOp_time(Arrays.asList("15:11:05"));
        commandSchedule.setType("command");
        
        ApplyTerminalGroupScheduleRequest.CommandOperationRequest operation = new ApplyTerminalGroupScheduleRequest.CommandOperationRequest();
        operation.setAuthor_url("api/brightness");
        operation.setKarma(2);
        operation.setContent("{\"brightness\":\"184\"}");
        commandSchedule.setOperation(operation);
        
        request.getSchedules().setCommandSchedule(Arrays.asList(commandSchedule));
        
        return request;
    }
    
    /**
     * 创建测试响应
     */
    private TerminalGroupScheduleResponse createTestResponse() {
        TerminalGroupScheduleResponse response = new TerminalGroupScheduleResponse();
        response.setParent(1);
        response.setSchedule_time(1720077073L);
        response.setMeta("");
        response.setMembers(0);
        response.setCount(0);
        response.setLink("");
        response.setName("测试终端47");
        response.setDescription("");
        response.setId(11924);
        response.setTaxonomy("terminalgroup");
        response.setSlug("测试终端47");
        
        // 创建终端信息列表
        List<TerminalGroupScheduleResponse.LedInfo> leds = new ArrayList<>();
        TerminalGroupScheduleResponse.LedInfo ledInfo = new TerminalGroupScheduleResponse.LedInfo();
        ledInfo.setLed_id(24607);
        ledInfo.setLed_name("zora");
        ledInfo.set_led_latest_report_time("0");
        ledInfo.setLed_description("");
        leds.add(ledInfo);
        response.setLeds(leds);
        
        return response;
    }
} 