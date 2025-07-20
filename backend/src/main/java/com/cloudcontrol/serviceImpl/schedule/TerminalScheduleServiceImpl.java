package com.cloudcontrol.serviceImpl.schedule;

import com.cloudcontrol.dto.schedule.*;
import com.cloudcontrol.service.schedule.TerminalScheduleService;
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
import java.util.List;

/**
 * 终端排程服务实现类
 */
@Service
public class TerminalScheduleServiceImpl implements TerminalScheduleService {
    
    private static final Logger logger = LoggerFactory.getLogger(TerminalScheduleServiceImpl.class);
    
    private final ObjectMapper objectMapper;
    
    @Autowired
    public TerminalScheduleServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    @Transactional
    public TerminalScheduleResponse updateTerminalSchedule(Integer terminalId, UpdateTerminalScheduleRequest request, Integer currentUserId) {
        logger.info("更新终端排程: terminalId={}, currentUserId={}", terminalId, currentUserId);
        
        try {
            // 验证请求参数
            validateUpdateRequest(request);
            
            // 验证用户权限
            validateUserPermission(terminalId, currentUserId);
            
            // 验证排程信息
            validateScheduleData(request);
            
            // 检查节目是否被修改
            validateProgramModification(request);
            
            // 更新排程
            TerminalScheduleResponse response = buildScheduleResponse(request);
            
            // 记录更新日志
            logScheduleUpdate(terminalId, request, currentUserId);
            
            logger.info("终端排程更新成功: terminalId={}", terminalId);
            return response;
            
        } catch (Exception e) {
            logger.error("更新终端排程失败: terminalId={}", terminalId, e);
            throw new RuntimeException("Failed to update terminal schedule: " + e.getMessage());
        }
    }
    
    /**
     * 验证更新请求
     */
    private void validateUpdateRequest(UpdateTerminalScheduleRequest request) {
        if (request == null) {
            throw new RuntimeException("Update request cannot be null");
        }
        
        if (request.getSchedules() == null) {
            throw new RuntimeException("Schedules can not be empty.");
        }
        
        // 验证节目排程
        if (request.getSchedules().getContentsSchedule() != null) {
            for (UpdateTerminalScheduleRequest.ContentsScheduleRequest schedule : request.getSchedules().getContentsSchedule()) {
                validateContentsSchedule(schedule);
            }
        }
        
        // 验证指令排程
        if (request.getSchedules().getCommandSchedule() != null) {
            for (UpdateTerminalScheduleRequest.CommandScheduleRequest schedule : request.getSchedules().getCommandSchedule()) {
                validateCommandSchedule(schedule);
            }
        }
    }
    
    /**
     * 验证节目排程
     */
    private void validateContentsSchedule(UpdateTerminalScheduleRequest.ContentsScheduleRequest schedule) {
        if (schedule.getOperation() == null || schedule.getOperation().getId() == null) {
            throw new RuntimeException("Program operation ID cannot be null");
        }
        
        if (schedule.getPriority() == null) {
            throw new RuntimeException("Program priority cannot be null");
        }
        
        if (schedule.getType() == null) {
            throw new RuntimeException("Program type cannot be null");
        }
        
        if (schedule.getType_priority() == null) {
            throw new RuntimeException("Program type priority cannot be null");
        }
        
        // 验证优先级唯一性
        // 这里应该检查同一终端下是否有重复的优先级
    }
    
    /**
     * 验证指令排程
     */
    private void validateCommandSchedule(UpdateTerminalScheduleRequest.CommandScheduleRequest schedule) {
        if (schedule.getName() == null) {
            throw new RuntimeException("Command name cannot be null");
        }
        
        if (schedule.getOperation() == null) {
            throw new RuntimeException("Command operation cannot be null");
        }
        
        if (schedule.getOp_time() == null || schedule.getOp_time().isEmpty()) {
            throw new RuntimeException("Command operation time cannot be null or empty");
        }
        
        // 验证指令类型
        validateCommandType(schedule.getName(), schedule.getContent());
    }
    
    /**
     * 验证指令类型
     */
    private void validateCommandType(String commandName, UpdateTerminalScheduleRequest.CommandContentRequest content) {
        switch (commandName) {
            case "Brightness_Control":
            case "Volume_Control":
            case "Colortemp_Control":
                if (content == null || content.getValue() == null) {
                    throw new RuntimeException("Value is required for " + commandName);
                }
                break;
            case "Switch_Signal_Source":
                if (content == null || content.getValue() == null) {
                    throw new RuntimeException("Switch value is required for " + commandName);
                }
                String switchValue = content.getValue().toString();
                if (!"async".equals(switchValue) && !"sync".equals(switchValue)) {
                    throw new RuntimeException("Invalid switch value: " + switchValue);
                }
                break;
            case "Relay":
            case "Board_Relay":
                if (content == null || content.getValue() == null) {
                    throw new RuntimeException("Relay value is required for " + commandName);
                }
                break;
            case "Sleep":
            case "Wakeup":
            case "Reboot":
            case "Clear_Cache":
                // 这些指令不需要content参数
                break;
            default:
                logger.warn("Unknown command type: {}", commandName);
        }
    }
    
    /**
     * 验证用户权限
     */
    private void validateUserPermission(Integer terminalId, Integer currentUserId) {
        if (currentUserId == null) {
            throw new RuntimeException("No Permission");
        }
        
        // 检查用户是否有权限操作该终端
        // 在实际应用中，这里应该检查用户的角色和权限
        logger.info("Validating user permission for terminal: {}, user: {}", terminalId, currentUserId);
        
        // 这里可以添加更复杂的权限检查逻辑
        // 比如检查用户是否是管理员、经理、编辑者、操作员等
    }
    
    /**
     * 验证排程数据
     */
    private void validateScheduleData(UpdateTerminalScheduleRequest request) {
        if (request.getSchedules() == null) {
            throw new RuntimeException("Schedules can not be empty.");
        }
        
        // 检查是否有排程内容
        boolean hasContents = request.getSchedules().getContentsSchedule() != null && !request.getSchedules().getContentsSchedule().isEmpty();
        boolean hasCommands = request.getSchedules().getCommandSchedule() != null && !request.getSchedules().getCommandSchedule().isEmpty();
        
        if (!hasContents && !hasCommands) {
            throw new RuntimeException("Schedules can not be empty.");
        }
    }
    
    /**
     * 验证节目修改
     */
    private void validateProgramModification(UpdateTerminalScheduleRequest request) {
        // 检查节目是否被修改
        // 在实际应用中，这里应该检查节目的修改状态
        logger.info("Validating program modification");
        
        // 这里可以添加节目修改检查逻辑
        // 如果节目已被修改，抛出异常
        // throw new RuntimeException("Invalid operation, the program has been modified!");
    }
    
    /**
     * 构建排程响应
     */
    private TerminalScheduleResponse buildScheduleResponse(UpdateTerminalScheduleRequest request) {
        try {
            // 构建响应数据
            TerminalScheduleResponse.TerminalScheduleData data = new TerminalScheduleResponse.TerminalScheduleData();
            data.setUpdateTerminalScheduleTime(Instant.now().getEpochSecond());
            
            // 构建ScheduleJsonBean
            TerminalScheduleResponse.ScheduleJsonBean scheduleJsonBean = new TerminalScheduleResponse.ScheduleJsonBean();
            scheduleJsonBean.setTo_children(request.getTo_children());
            scheduleJsonBean.setProgram_ids(request.getProgram_ids());
            
            // 构建缩略图响应
            List<TerminalScheduleResponse.ThumbnailResponse> thumbnails = new ArrayList<>();
            if (request.getThumbnails() != null) {
                for (UpdateTerminalScheduleRequest.ThumbnailRequest thumbnail : request.getThumbnails()) {
                    thumbnails.add(new TerminalScheduleResponse.ThumbnailResponse(thumbnail.getId(), thumbnail.getSrc()));
                }
            }
            scheduleJsonBean.setThumbnails(thumbnails);
            
            // 构建排程响应
            TerminalScheduleResponse.SchedulesResponse schedules = new TerminalScheduleResponse.SchedulesResponse();
            
            // 构建指令排程响应
            List<TerminalScheduleResponse.CommandScheduleResponse> commandSchedules = new ArrayList<>();
            if (request.getSchedules().getCommandSchedule() != null) {
                for (UpdateTerminalScheduleRequest.CommandScheduleRequest commandSchedule : request.getSchedules().getCommandSchedule()) {
                    commandSchedules.add(buildCommandScheduleResponse(commandSchedule));
                }
            }
            schedules.setCommandSchedule(commandSchedules);
            
            // 构建节目排程响应
            List<TerminalScheduleResponse.ContentsScheduleResponse> contentsSchedules = new ArrayList<>();
            if (request.getSchedules().getContentsSchedule() != null) {
                for (UpdateTerminalScheduleRequest.ContentsScheduleRequest contentsSchedule : request.getSchedules().getContentsSchedule()) {
                    contentsSchedules.add(buildContentsScheduleResponse(contentsSchedule));
                }
            }
            schedules.setContentsSchedule(contentsSchedules);
            
            scheduleJsonBean.setSchedules(schedules);
            data.setScheduleJsonBean(scheduleJsonBean);
            
            return new TerminalScheduleResponse(200, "Success.", data);
            
        } catch (Exception e) {
            logger.error("构建排程响应失败", e);
            throw new RuntimeException("Failed to build schedule response: " + e.getMessage());
        }
    }
    
    /**
     * 构建指令排程响应
     */
    private TerminalScheduleResponse.CommandScheduleResponse buildCommandScheduleResponse(UpdateTerminalScheduleRequest.CommandScheduleRequest request) {
        TerminalScheduleResponse.CommandScheduleResponse response = new TerminalScheduleResponse.CommandScheduleResponse();
        
        // 构建指令内容响应
        if (request.getContent() != null) {
            response.setContent(new TerminalScheduleResponse.CommandContentResponse(
                request.getContent().getName(), request.getContent().getValue()));
        }
        
        response.setIf_limit_date(request.getIf_limit_date());
        
        // 构建日期限制响应
        if (request.getLimit_date() != null) {
            response.setLimit_date(new TerminalScheduleResponse.LimitDateResponse(
                request.getLimit_date().getStart(), request.getLimit_date().getEnd()));
        }
        
        response.setIf_limit_weekday(request.getIf_limit_weekday());
        response.setLimit_weekday(request.getLimit_weekday());
        response.setName(request.getName());
        response.setOp_time(request.getOp_time());
        response.setType(request.getType());
        
        // 构建指令操作响应
        if (request.getOperation() != null) {
            response.setOperation(new TerminalScheduleResponse.CommandOperationResponse(
                request.getOperation().getAuthor_url(),
                request.getOperation().getKarma(),
                request.getOperation().getContent()));
        }
        
        return response;
    }
    
    /**
     * 构建节目排程响应
     */
    private TerminalScheduleResponse.ContentsScheduleResponse buildContentsScheduleResponse(UpdateTerminalScheduleRequest.ContentsScheduleRequest request) {
        TerminalScheduleResponse.ContentsScheduleResponse response = new TerminalScheduleResponse.ContentsScheduleResponse();
        
        response.setIf_limit_date(request.getIf_limit_date());
        
        // 构建日期限制响应
        if (request.getLimit_date() != null) {
            response.setLimit_date(new TerminalScheduleResponse.LimitDateResponse(
                request.getLimit_date().getStart(),
                request.getLimit_date().getEnd(),
                request.getLimit_date().getStart_time(),
                request.getLimit_date().getEnd_time()));
        }
        
        response.setIf_limit_time(request.getIf_limit_time());
        
        // 构建时间限制响应
        if (request.getLimit_time() != null) {
            response.setLimit_time(new TerminalScheduleResponse.LimitTimeResponse(
                request.getLimit_time().getStart_time(),
                request.getLimit_time().getEnd_time()));
        }
        
        response.setIf_limit_weekday(request.getIf_limit_weekday());
        response.setLimit_weekday(request.getLimit_weekday());
        response.setName(request.getName());
        response.setPriority(request.getPriority());
        response.setType(request.getType());
        response.setType_priority(request.getType_priority());
        
        // 构建节目操作响应
        if (request.getOperation() != null) {
            response.setOperation(new TerminalScheduleResponse.ContentsOperationResponse(
                request.getOperation().getId(),
                request.getOperation().getName(),
                request.getOperation().getVsn(),
                request.getOperation().getSource()));
        }
        
        return response;
    }
    
    /**
     * 记录排程更新日志
     */
    private void logScheduleUpdate(Integer terminalId, UpdateTerminalScheduleRequest request, Integer currentUserId) {
        logger.info("排程更新日志: terminalId={}, userId={}, programIds={}, toChildren={}",
                   terminalId, currentUserId, request.getProgram_ids(), request.getTo_children());
        
        // 这里可以添加更详细的日志记录
        // 比如记录具体的排程内容、时间等
    }
    
    @Override
    @Transactional(readOnly = true)
    public TerminalScheduleResponse getTerminalSchedule(Integer terminalId, Integer currentUserId) {
        logger.info("获取终端排程信息: terminalId={}, currentUserId={}", terminalId, currentUserId);
        
        try {
            // 验证用户权限
            validateUserPermission(terminalId, currentUserId);
            
            // 获取终端排程信息
            TerminalScheduleResponse response = buildGetScheduleResponse(terminalId);
            
            logger.info("获取终端排程信息成功: terminalId={}", terminalId);
            return response;
            
        } catch (Exception e) {
            logger.error("获取终端排程信息失败: terminalId={}", terminalId, e);
            throw new RuntimeException("Failed to get terminal schedule: " + e.getMessage());
        }
    }
    
    /**
     * 构建获取排程响应
     */
    private TerminalScheduleResponse buildGetScheduleResponse(Integer terminalId) {
        try {
            // 构建响应数据
            TerminalScheduleResponse.TerminalScheduleData data = new TerminalScheduleResponse.TerminalScheduleData();
            data.setUpdateTerminalScheduleTime(Instant.now().getEpochSecond());
            
            // 构建ScheduleJsonBean
            TerminalScheduleResponse.ScheduleJsonBean scheduleJsonBean = new TerminalScheduleResponse.ScheduleJsonBean();
            scheduleJsonBean.setTo_children(false);
            scheduleJsonBean.setProgram_ids(new ArrayList<>());
            
            // 构建缩略图响应
            scheduleJsonBean.setThumbnails(new ArrayList<>());
            
            // 构建排程响应
            TerminalScheduleResponse.SchedulesResponse schedules = new TerminalScheduleResponse.SchedulesResponse();
            
            // 构建节目排程响应（示例数据）
            List<TerminalScheduleResponse.ContentsScheduleResponse> contentsSchedules = new ArrayList<>();
            schedules.setContentsSchedule(contentsSchedules);
            
            // 构建指令排程响应（示例数据）
            List<TerminalScheduleResponse.CommandScheduleResponse> commandSchedules = new ArrayList<>();
            
            // 添加示例休眠指令
            TerminalScheduleResponse.CommandScheduleResponse sleepCommand = new TerminalScheduleResponse.CommandScheduleResponse();
            sleepCommand.setIf_limit_date(false);
            sleepCommand.setIf_limit_weekday(false);
            
            // 设置日期限制
            TerminalScheduleResponse.LimitDateResponse limitDate = new TerminalScheduleResponse.LimitDateResponse();
            limitDate.setStart("2023-09-23");
            limitDate.setEnd("2023-09-23");
            sleepCommand.setLimit_date(limitDate);
            
            // 设置星期限制
            sleepCommand.setLimit_weekday(Arrays.asList(true, true, true, true, true, true, true));
            sleepCommand.setName("Sleep");
            sleepCommand.setOp_time(Arrays.asList("05:00:00"));
            sleepCommand.setType("command");
            
            // 设置操作
            TerminalScheduleResponse.CommandOperationResponse operation = new TerminalScheduleResponse.CommandOperationResponse();
            operation.setAuthor_url("api/action");
            operation.setKarma(1);
            operation.setContent("{\"command\":\"sleep\"}");
            sleepCommand.setOperation(operation);
            
            commandSchedules.add(sleepCommand);
            schedules.setCommandSchedule(commandSchedules);
            
            scheduleJsonBean.setSchedules(schedules);
            data.setScheduleJsonBean(scheduleJsonBean);
            
            return new TerminalScheduleResponse(200, "Success.", data);
            
        } catch (Exception e) {
            logger.error("构建获取排程响应失败", e);
            throw new RuntimeException("Failed to build get schedule response: " + e.getMessage());
        }
    }
} 