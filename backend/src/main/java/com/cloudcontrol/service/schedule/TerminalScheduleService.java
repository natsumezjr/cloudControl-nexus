package com.cloudcontrol.service.schedule;

import com.cloudcontrol.dto.schedule.UpdateTerminalScheduleRequest;
import com.cloudcontrol.dto.schedule.TerminalScheduleResponse;

/**
 * 终端排程服务接口
 */
public interface TerminalScheduleService {
    
    /**
     * 更新终端排程
     * @param terminalId 终端ID
     * @param request 更新请求
     * @param currentUserId 当前用户ID
     * @return 更新后的排程信息
     */
    TerminalScheduleResponse updateTerminalSchedule(Integer terminalId, UpdateTerminalScheduleRequest request, Integer currentUserId);
    
    /**
     * 获取终端排程信息
     * @param terminalId 终端ID
     * @param currentUserId 当前用户ID
     * @return 终端排程信息
     */
    TerminalScheduleResponse getTerminalSchedule(Integer terminalId, Integer currentUserId);
} 