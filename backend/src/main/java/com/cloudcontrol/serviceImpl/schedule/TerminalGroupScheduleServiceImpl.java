package com.cloudcontrol.serviceImpl.schedule;

import com.cloudcontrol.dto.schedule.ApplyTerminalGroupScheduleRequest;
import com.cloudcontrol.dto.schedule.TerminalGroupScheduleResponse;
import com.cloudcontrol.dto.schedule.TerminalGroupSchedulesResponse;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleRequest;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleResponse;
import com.cloudcontrol.service.schedule.TerminalGroupScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 终端组排程服务实现类
 */
@Service
public class TerminalGroupScheduleServiceImpl implements TerminalGroupScheduleService {
    
    private static final Logger logger = LoggerFactory.getLogger(TerminalGroupScheduleServiceImpl.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    @Transactional
    public TerminalGroupScheduleResponse applyTerminalGroupSchedule(Integer terminalGroupId, 
                                                                   ApplyTerminalGroupScheduleRequest request, 
                                                                   Integer currentUserId) {
        logger.info("应用终端组排程: terminalGroupId={}, currentUserId={}", terminalGroupId, currentUserId);
        
        try {
            // 验证请求参数
            validateRequest(terminalGroupId, request);
            
            // 验证用户权限
            validateUserPermission(terminalGroupId, currentUserId);
            
            // 验证排程内容
            validateScheduleContent(request);
            
            // 应用排程到终端组
            TerminalGroupScheduleResponse response = applyScheduleToTerminalGroup(terminalGroupId, request);
            
            // 记录应用日志
            logScheduleApplication(terminalGroupId, request, currentUserId);
            
            logger.info("应用终端组排程成功: terminalGroupId={}", terminalGroupId);
            return response;
            
        } catch (Exception e) {
            logger.error("应用终端组排程失败: terminalGroupId={}", terminalGroupId, e);
            throw new RuntimeException("Failed to apply terminal group schedule: " + e.getMessage());
        }
    }
    
    /**
     * 验证请求参数
     */
    private void validateRequest(Integer terminalGroupId, ApplyTerminalGroupScheduleRequest request) {
        if (terminalGroupId == null || terminalGroupId <= 0) {
            throw new RuntimeException("Invalid terminal group ID");
        }
        
        if (request == null) {
            throw new RuntimeException("Request cannot be null");
        }
        
        if (request.getSchedules() == null) {
            throw new RuntimeException("Schedules cannot be null");
        }
        
        // 验证节目排程
        if (request.getSchedules().getContentsSchedule() != null) {
            for (ApplyTerminalGroupScheduleRequest.ContentsScheduleRequest contentSchedule : 
                 request.getSchedules().getContentsSchedule()) {
                validateContentsSchedule(contentSchedule);
            }
        }
        
        // 验证指令排程
        if (request.getSchedules().getCommandSchedule() != null) {
            for (ApplyTerminalGroupScheduleRequest.CommandScheduleRequest commandSchedule : 
                 request.getSchedules().getCommandSchedule()) {
                validateCommandSchedule(commandSchedule);
            }
        }
    }
    
    /**
     * 验证节目排程
     */
    private void validateContentsSchedule(ApplyTerminalGroupScheduleRequest.ContentsScheduleRequest contentSchedule) {
        if (contentSchedule.getOperation() == null) {
            throw new RuntimeException("Content schedule operation cannot be null");
        }
        
        if (contentSchedule.getOperation().getId() == null) {
            throw new RuntimeException("Content schedule operation ID cannot be null");
        }
        
        if (contentSchedule.getType() == null || contentSchedule.getType().trim().isEmpty()) {
            throw new RuntimeException("Content schedule type cannot be null or empty");
        }
        
        if (contentSchedule.getType_priority() == null) {
            throw new RuntimeException("Content schedule type priority cannot be null");
        }
        
        // 验证日期限制
        if (Boolean.TRUE.equals(contentSchedule.getIf_limit_date())) {
            if (contentSchedule.getLimit_date() == null) {
                throw new RuntimeException("Date limit is enabled but limit_date is null");
            }
            if (contentSchedule.getLimit_date().getStart() == null || 
                contentSchedule.getLimit_date().getEnd() == null) {
                throw new RuntimeException("Date limit start and end cannot be null");
            }
        }
        
        // 验证时间限制
        if (Boolean.TRUE.equals(contentSchedule.getIf_limit_time())) {
            if (contentSchedule.getLimit_time() == null) {
                throw new RuntimeException("Time limit is enabled but limit_time is null");
            }
            if (contentSchedule.getLimit_time().getStart_time() == null || 
                contentSchedule.getLimit_time().getEnd_time() == null) {
                throw new RuntimeException("Time limit start_time and end_time cannot be null");
            }
        }
        
        // 验证星期限制
        if (Boolean.TRUE.equals(contentSchedule.getIf_limit_weekday())) {
            if (contentSchedule.getLimit_weekday() == null || contentSchedule.getLimit_weekday().size() != 7) {
                throw new RuntimeException("Weekday limit must have exactly 7 elements");
            }
        }
    }
    
    /**
     * 验证指令排程
     */
    private void validateCommandSchedule(ApplyTerminalGroupScheduleRequest.CommandScheduleRequest commandSchedule) {
        if (commandSchedule.getOperation() == null) {
            throw new RuntimeException("Command schedule operation cannot be null");
        }
        
        if (commandSchedule.getOp_time() == null || commandSchedule.getOp_time().isEmpty()) {
            throw new RuntimeException("Command schedule op_time cannot be null or empty");
        }
        
        if (commandSchedule.getType() == null || !"command".equals(commandSchedule.getType())) {
            throw new RuntimeException("Command schedule type must be 'command'");
        }
        
        // 验证日期限制
        if (Boolean.TRUE.equals(commandSchedule.getIf_limit_date())) {
            if (commandSchedule.getLimit_date() == null) {
                throw new RuntimeException("Date limit is enabled but limit_date is null");
            }
            if (commandSchedule.getLimit_date().getStart() == null || 
                commandSchedule.getLimit_date().getEnd() == null) {
                throw new RuntimeException("Date limit start and end cannot be null");
            }
        }
        
        // 验证星期限制
        if (Boolean.TRUE.equals(commandSchedule.getIf_limit_weekday())) {
            if (commandSchedule.getLimit_weekday() == null || commandSchedule.getLimit_weekday().size() != 7) {
                throw new RuntimeException("Weekday limit must have exactly 7 elements");
            }
        }
        
        // 验证指令类型
        validateCommandType(commandSchedule);
    }
    
    /**
     * 验证指令类型
     */
    private void validateCommandType(ApplyTerminalGroupScheduleRequest.CommandScheduleRequest commandSchedule) {
        String commandName = commandSchedule.getName();
        if (commandName == null || commandName.trim().isEmpty()) {
            return; // 指令名称可以为空
        }
        
        // 验证特定指令类型的参数
        switch (commandName) {
            case "Brightness_Control":
            case "Volume_Control":
            case "Colortemp_Control":
                if (commandSchedule.getContent() == null) {
                    throw new RuntimeException(commandName + " requires content parameter");
                }
                break;
            case "Relay":
            case "Board_Relay":
                if (commandSchedule.getContent() == null) {
                    throw new RuntimeException(commandName + " requires content parameter");
                }
                break;
            case "Sleep":
            case "Wakeup":
            case "Reboot":
            case "Clear_Cache":
            case "Switch_Signal_Source":
                // 这些指令不需要content参数
                break;
            default:
                // 未知指令类型，允许通过
                break;
        }
    }
    
    /**
     * 验证用户权限
     */
    private void validateUserPermission(Integer terminalGroupId, Integer currentUserId) {
        // 这里应该实现实际的权限验证逻辑
        // 检查用户是否有权限操作指定的终端组
        // 暂时返回true，实际应用中需要根据业务逻辑实现
        
        // 模拟权限验证
        if (currentUserId == null || currentUserId <= 0) {
            throw new RuntimeException("No Permission");
        }
        
        // 这里可以添加更详细的权限检查逻辑
        // 比如检查用户是否属于指定的终端组，或者是否有管理员权限等
    }
    
    /**
     * 验证排程内容
     */
    private void validateScheduleContent(ApplyTerminalGroupScheduleRequest request) {
        // 验证节目ID列表
        if (request.getProgram_ids() != null && !request.getProgram_ids().isEmpty()) {
            for (Integer programId : request.getProgram_ids()) {
                if (programId == null || programId <= 0) {
                    throw new RuntimeException("Invalid program ID: " + programId);
                }
            }
        }
        
        // 验证缩略图
        if (request.getThumbnails() != null) {
            for (ApplyTerminalGroupScheduleRequest.ThumbnailRequest thumbnail : request.getThumbnails()) {
                if (thumbnail.getId() == null || thumbnail.getId() <= 0) {
                    throw new RuntimeException("Invalid thumbnail ID");
                }
            }
        }
    }
    
    /**
     * 应用排程到终端组
     */
    private TerminalGroupScheduleResponse applyScheduleToTerminalGroup(Integer terminalGroupId, 
                                                                      ApplyTerminalGroupScheduleRequest request) {
        try {
            // 这里应该实现实际的排程应用逻辑
            // 比如将排程信息保存到数据库，或者发送到终端设备等
            
            // 构建响应数据
            TerminalGroupScheduleResponse response = new TerminalGroupScheduleResponse();
            response.setParent(1);
            response.setSchedule_time(Instant.now().getEpochSecond());
            response.setMeta("");
            response.setMembers(0);
            response.setCount(0);
            response.setLink("");
            response.setName("测试终端47");
            response.setDescription("");
            response.setId(terminalGroupId);
            response.setTaxonomy("terminalgroup");
            response.setSlug("测试终端47");
            
            // 构建终端信息列表
            List<TerminalGroupScheduleResponse.LedInfo> leds = new ArrayList<>();
            TerminalGroupScheduleResponse.LedInfo ledInfo = new TerminalGroupScheduleResponse.LedInfo();
            ledInfo.setLed_id(24607);
            ledInfo.setLed_name("zora");
            ledInfo.set_led_latest_report_time("0");
            ledInfo.setLed_description("");
            leds.add(ledInfo);
            response.setLeds(leds);
            
            return response;
            
        } catch (Exception e) {
            logger.error("应用排程到终端组失败", e);
            throw new RuntimeException("Failed to apply schedule to terminal group: " + e.getMessage());
        }
    }
    
    /**
     * 记录排程应用日志
     */
    private void logScheduleApplication(Integer terminalGroupId, ApplyTerminalGroupScheduleRequest request, Integer currentUserId) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            logger.info("排程应用日志: terminalGroupId={}, userId={}, request={}", 
                       terminalGroupId, currentUserId, requestJson);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize request for logging", e);
        }
        
        // 这里可以添加更详细的日志记录
        // 比如记录具体的排程内容、应用时间等
    }
    
    @Override
    @Transactional(readOnly = true)
    public TerminalGroupSchedulesResponse getTerminalGroupSchedules(Set<Integer> terminalGroupIds, Integer currentUserId) {
        logger.info("获取终端组排程信息: terminalGroupIds={}, currentUserId={}", terminalGroupIds, currentUserId);
        
        try {
            // 验证参数
            validateGetRequest(terminalGroupIds);
            
            // 验证用户权限
            validateUserPermissionForGet(terminalGroupIds, currentUserId);
            
            // 获取终端组排程信息
            TerminalGroupSchedulesResponse response = buildGetSchedulesResponse(terminalGroupIds);
            
            logger.info("获取终端组排程信息成功: terminalGroupIds={}", terminalGroupIds);
            return response;
            
        } catch (Exception e) {
            logger.error("获取终端组排程信息失败: terminalGroupIds={}", terminalGroupIds, e);
            throw new RuntimeException("List group schedules error: " + e.getMessage());
        }
    }
    
    /**
     * 验证获取请求参数
     */
    private void validateGetRequest(Set<Integer> terminalGroupIds) {
        if (terminalGroupIds == null || terminalGroupIds.isEmpty()) {
            throw new RuntimeException("Terminal group IDs cannot be null or empty");
        }
        
        for (Integer terminalGroupId : terminalGroupIds) {
            if (terminalGroupId == null || terminalGroupId <= 0) {
                throw new RuntimeException("Invalid terminal group ID: " + terminalGroupId);
            }
        }
    }
    
    /**
     * 验证用户权限（获取操作）
     */
    private void validateUserPermissionForGet(Set<Integer> terminalGroupIds, Integer currentUserId) {
        // 这里应该实现实际的权限验证逻辑
        // 检查用户是否有权限查看指定的终端组排程
        // 暂时返回true，实际应用中需要根据业务逻辑实现
        
        // 模拟权限验证
        if (currentUserId == null || currentUserId <= 0) {
            throw new RuntimeException("No Permission");
        }
        
        // 这里可以添加更详细的权限检查逻辑
        // 比如检查用户是否属于指定的终端组，或者是否有查看权限等
    }
    
    /**
     * 构建获取排程响应
     */
    private TerminalGroupSchedulesResponse buildGetSchedulesResponse(Set<Integer> terminalGroupIds) {
        try {
            Map<String, TerminalGroupSchedulesResponse.ScheduleJsonBean> data = new HashMap<>();
            
            for (Integer terminalGroupId : terminalGroupIds) {
                // 构建每个终端组的排程信息
                TerminalGroupSchedulesResponse.ScheduleJsonBean scheduleJsonBean = buildScheduleJsonBean(terminalGroupId);
                data.put(terminalGroupId.toString(), scheduleJsonBean);
            }
            
            return new TerminalGroupSchedulesResponse(200, "Success.", data);
            
        } catch (Exception e) {
            logger.error("构建获取排程响应失败", e);
            throw new RuntimeException("Failed to build get schedules response: " + e.getMessage());
        }
    }
    
    /**
     * 构建排程JSON Bean
     */
    private TerminalGroupSchedulesResponse.ScheduleJsonBean buildScheduleJsonBean(Integer terminalGroupId) {
        TerminalGroupSchedulesResponse.ScheduleJsonBean scheduleJsonBean = new TerminalGroupSchedulesResponse.ScheduleJsonBean();
        scheduleJsonBean.setTo_children(false);
        scheduleJsonBean.setProgram_ids(new ArrayList<>());
        scheduleJsonBean.setThumbnails(new ArrayList<>());
        
        // 构建排程信息
        TerminalGroupSchedulesResponse.Schedules schedules = new TerminalGroupSchedulesResponse.Schedules();
        schedules.setContentsSchedule(new ArrayList<>());
        
        // 构建指令排程（示例数据）
        List<TerminalGroupSchedulesResponse.CommandSchedule> commandSchedules = new ArrayList<>();
        
        // 添加示例重启指令
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
        
        return scheduleJsonBean;
    }
    
    @Override
    @Transactional
    public SimplifiedScheduleResponse applySimplifiedSchedule(SimplifiedScheduleRequest request, Integer currentUserId) {
        logger.info("简化应用排程: request={}, currentUserId={}", request, currentUserId);
        
        try {
            // 验证请求参数
            validateSimplifiedRequest(request);
            
            // 验证用户权限
            validateSimplifiedUserPermission(request, currentUserId);
            
            // 验证排程内容
            validateSimplifiedScheduleContent(request);
            
            // 应用简化排程
            SimplifiedScheduleResponse response = applySimplifiedScheduleToTarget(request);
            
            // 记录应用日志
            logSimplifiedScheduleApplication(request, currentUserId);
            
            logger.info("简化应用排程成功");
            return response;
            
        } catch (Exception e) {
            logger.error("简化应用排程失败", e);
            throw new RuntimeException("Failed to apply simplified schedule: " + e.getMessage());
        }
    }
    
    /**
     * 验证简化请求参数
     */
    private void validateSimplifiedRequest(SimplifiedScheduleRequest request) {
        if (request == null) {
            throw new RuntimeException("Request cannot be null");
        }
        
        // 验证终端组ID或终端ID至少有一个
        if (request.getTerminalGroupId() == null && request.getTerminalId() == null) {
            throw new RuntimeException("Either terminalGroupId or terminalId must be provided");
        }
        
        // 验证指令排程
        if (request.getCommandSchedules() != null) {
            for (SimplifiedScheduleRequest.CommandScheduleRequest commandSchedule : request.getCommandSchedules()) {
                validateSimplifiedCommandSchedule(commandSchedule);
            }
        }
        
        // 验证节目排程
        if (request.getProgramSchedules() != null) {
            for (SimplifiedScheduleRequest.ProgramScheduleRequest programSchedule : request.getProgramSchedules()) {
                validateSimplifiedProgramSchedule(programSchedule);
            }
        }
    }
    
    /**
     * 验证简化指令排程
     */
    private void validateSimplifiedCommandSchedule(SimplifiedScheduleRequest.CommandScheduleRequest commandSchedule) {
        if (commandSchedule.getStartDate() == null || commandSchedule.getStartDate().trim().isEmpty()) {
            throw new RuntimeException("Command schedule startDate cannot be null or empty");
        }
        
        if (commandSchedule.getEndDate() == null || commandSchedule.getEndDate().trim().isEmpty()) {
            throw new RuntimeException("Command schedule endDate cannot be null or empty");
        }
        
        if (commandSchedule.getOperationTime() == null || commandSchedule.getOperationTime().trim().isEmpty()) {
            throw new RuntimeException("Command schedule operationTime cannot be null or empty");
        }
        
        if (commandSchedule.getWeeks() == null || commandSchedule.getWeeks().size() != 7) {
            throw new RuntimeException("Command schedule weeks must have exactly 7 elements");
        }
        
        if (commandSchedule.getCommandType() == null || commandSchedule.getCommandType().trim().isEmpty()) {
            throw new RuntimeException("Command schedule commandType cannot be null or empty");
        }
        
        // 验证指令类型
        try {
            SimplifiedScheduleRequest.CommandScheduleType.fromString(commandSchedule.getCommandType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid command type: " + commandSchedule.getCommandType());
        }
        
        // 验证指令值
        validateCommandValue(commandSchedule.getCommandType(), commandSchedule.getValue());
    }
    
    /**
     * 验证简化节目排程
     */
    private void validateSimplifiedProgramSchedule(SimplifiedScheduleRequest.ProgramScheduleRequest programSchedule) {
        if (programSchedule.getStartDate() == null || programSchedule.getStartDate().trim().isEmpty()) {
            throw new RuntimeException("Program schedule startDate cannot be null or empty");
        }
        
        if (programSchedule.getEndDate() == null || programSchedule.getEndDate().trim().isEmpty()) {
            throw new RuntimeException("Program schedule endDate cannot be null or empty");
        }
        
        if (programSchedule.getStartTime() == null || programSchedule.getStartTime().trim().isEmpty()) {
            throw new RuntimeException("Program schedule startTime cannot be null or empty");
        }
        
        if (programSchedule.getEndTime() == null || programSchedule.getEndTime().trim().isEmpty()) {
            throw new RuntimeException("Program schedule endTime cannot be null or empty");
        }
        
        if (programSchedule.getWeeks() == null || programSchedule.getWeeks().size() != 7) {
            throw new RuntimeException("Program schedule weeks must have exactly 7 elements");
        }
        
        if (programSchedule.getProgramPlayForm() == null) {
            throw new RuntimeException("Program schedule programPlayForm cannot be null");
        }
        
        if (programSchedule.getProgramPlayForm() < 0 || programSchedule.getProgramPlayForm() > 1) {
            throw new RuntimeException("Program schedule programPlayForm must be 0 or 1");
        }
        
        if (programSchedule.getProgramId() == null || programSchedule.getProgramId() <= 0) {
            throw new RuntimeException("Program schedule programId must be positive");
        }
        
        if (programSchedule.getProgramPriority() == null || programSchedule.getProgramPriority() < 0) {
            throw new RuntimeException("Program schedule programPriority must be non-negative");
        }
    }
    
    /**
     * 验证指令值
     */
    private void validateCommandValue(String commandType, String value) {
        SimplifiedScheduleRequest.CommandScheduleType type = SimplifiedScheduleRequest.CommandScheduleType.fromString(commandType);
        
        switch (type) {
            case BOARD_RELAY:
                if (value == null || (!value.equals("0") && !value.equals("1"))) {
                    throw new RuntimeException("BOARD_RELAY value must be '0' or '1'");
                }
                break;
            case SWITCH_SIGNAL_SOURCE:
                if (value == null || (!value.equals("0") && !value.equals("1"))) {
                    throw new RuntimeException("SWITCH_SIGNAL_SOURCE value must be '0' or '1'");
                }
                break;
            case RELAY:
                if (value == null || !value.matches("\\[\\d+,\\s*\\d+,\\s*\\d+\\]")) {
                    throw new RuntimeException("RELAY value must be in format '[0,1,0]'");
                }
                break;
            case VOLUME:
                if (value == null || !value.matches("\\d+")) {
                    throw new RuntimeException("VOLUME value must be a number");
                }
                int volume = Integer.parseInt(value);
                if (volume < 0 || volume > 100) {
                    throw new RuntimeException("VOLUME value must be between 0 and 100");
                }
                break;
            case BRIGHTNESS:
                if (value == null || !value.matches("\\d+")) {
                    throw new RuntimeException("BRIGHTNESS value must be a number");
                }
                int brightness = Integer.parseInt(value);
                if (brightness < 0 || brightness > 100) {
                    throw new RuntimeException("BRIGHTNESS value must be between 0 and 100");
                }
                break;
            case COLORTEMP:
                if (value == null || !value.matches("\\d+")) {
                    throw new RuntimeException("COLORTEMP value must be a number");
                }
                int colortemp = Integer.parseInt(value);
                if (colortemp < 2000 || colortemp > 10000) {
                    throw new RuntimeException("COLORTEMP value must be between 2000 and 10000");
                }
                break;
            case SLEEP:
            case WAKEUP:
            case REBOOT:
            case CLEAR_CACHE:
                // 这些指令不需要value参数
                break;
        }
    }
    
    /**
     * 验证用户权限（简化应用）
     */
    private void validateSimplifiedUserPermission(SimplifiedScheduleRequest request, Integer currentUserId) {
        // 这里应该实现实际的权限验证逻辑
        // 检查用户是否有权限操作指定的终端组或终端
        // 暂时返回true，实际应用中需要根据业务逻辑实现
        
        // 模拟权限验证
        if (currentUserId == null || currentUserId <= 0) {
            throw new RuntimeException("No Permission");
        }
        
        // 这里可以添加更详细的权限检查逻辑
        // 比如检查用户是否属于指定的终端组，或者是否有操作权限等
    }
    
    /**
     * 验证简化排程内容
     */
    private void validateSimplifiedScheduleContent(SimplifiedScheduleRequest request) {
        // 验证至少有一个排程
        if ((request.getCommandSchedules() == null || request.getCommandSchedules().isEmpty()) &&
            (request.getProgramSchedules() == null || request.getProgramSchedules().isEmpty())) {
            throw new RuntimeException("At least one schedule (command or program) must be provided");
        }
    }
    
    /**
     * 应用简化排程到目标
     */
    private SimplifiedScheduleResponse applySimplifiedScheduleToTarget(SimplifiedScheduleRequest request) {
        try {
            // 这里应该实现实际的简化排程应用逻辑
            // 根据terminalGroupId和terminalId决定应用目标
            // 将简化排程转换为标准排程格式并应用
            
            // 构建响应数据
            SimplifiedScheduleResponse response = new SimplifiedScheduleResponse();
            response.setStatus(200);
            response.setMessage("Schedule applied successfully");
            response.setData("排程信息");
            
            return response;
            
        } catch (Exception e) {
            logger.error("应用简化排程到目标失败", e);
            throw new RuntimeException("Failed to apply simplified schedule to target: " + e.getMessage());
        }
    }
    
    /**
     * 记录简化排程应用日志
     */
    private void logSimplifiedScheduleApplication(SimplifiedScheduleRequest request, Integer currentUserId) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            logger.info("简化排程应用日志: userId={}, request={}", currentUserId, requestJson);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize simplified request for logging", e);
        }
        
        // 这里可以添加更详细的日志记录
        // 比如记录具体的排程内容、应用时间等
    }
} 