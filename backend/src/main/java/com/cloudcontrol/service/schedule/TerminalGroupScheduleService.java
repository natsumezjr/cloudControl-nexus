package com.cloudcontrol.service.schedule;

import com.cloudcontrol.dto.schedule.ApplyTerminalGroupScheduleRequest;
import com.cloudcontrol.dto.schedule.TerminalGroupScheduleResponse;
import com.cloudcontrol.dto.schedule.TerminalGroupSchedulesResponse;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleRequest;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleResponse;

import java.util.Set;

/**
 * 终端组排程服务接口
 */
public interface TerminalGroupScheduleService {
    
    /**
     * 应用终端组排程
     * @param terminalGroupId 终端组ID
     * @param request 应用请求
     * @param currentUserId 当前用户ID
     * @return 终端组信息
     */
    TerminalGroupScheduleResponse applyTerminalGroupSchedule(Integer terminalGroupId, 
                                                           ApplyTerminalGroupScheduleRequest request, 
                                                           Integer currentUserId);
    
    /**
     * 获取终端组排程信息
     * @param terminalGroupIds 终端组ID集合
     * @param currentUserId 当前用户ID
     * @return 终端组排程信息
     */
    TerminalGroupSchedulesResponse getTerminalGroupSchedules(Set<Integer> terminalGroupIds, Integer currentUserId);
    
    /**
     * 简化应用排程（终端组或终端）
     * @param request 简化排程请求
     * @param currentUserId 当前用户ID
     * @return 排程信息
     */
    SimplifiedScheduleResponse applySimplifiedSchedule(SimplifiedScheduleRequest request, Integer currentUserId);
} 