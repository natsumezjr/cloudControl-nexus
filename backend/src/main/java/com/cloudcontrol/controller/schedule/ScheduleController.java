package com.cloudcontrol.controller.schedule;

import com.cloudcontrol.dto.schedule.UpdateTerminalScheduleRequest;
import com.cloudcontrol.dto.schedule.TerminalScheduleResponse;
import com.cloudcontrol.dto.schedule.ScheduleErrorResponse;
import com.cloudcontrol.service.schedule.TerminalScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import com.cloudcontrol.dto.schedule.TerminalGroupSchedulesResponse;
import com.cloudcontrol.service.schedule.TerminalGroupScheduleService;

/**
 * 排程控制器
 */
@RestController
@RequestMapping("/wp-json/wp/v3/schedules")
public class ScheduleController {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    
    private final TerminalScheduleService terminalScheduleService;
    private final TerminalGroupScheduleService terminalGroupScheduleService;
    
    @Autowired
    public ScheduleController(TerminalScheduleService terminalScheduleService, TerminalGroupScheduleService terminalGroupScheduleService) {
        this.terminalScheduleService = terminalScheduleService;
        this.terminalGroupScheduleService = terminalGroupScheduleService;
    }
    
    /**
     * 更新终端排程
     * URL: /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules
     * Method: PUT
     */
    @PutMapping("/{terminalId}/terminalSchedules")
    public ResponseEntity<?> updateTerminalSchedule(@PathVariable Integer terminalId, 
                                                   @RequestBody UpdateTerminalScheduleRequest request) {
        logger.info("收到更新终端排程请求: terminalId={}", terminalId);
        
        try {
            if (request == null) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Request body cannot be null", null));
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 更新终端排程
            TerminalScheduleResponse response = terminalScheduleService.updateTerminalSchedule(terminalId, request, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("更新终端排程失败: terminalId={}", terminalId, e);
            
            // 根据错误类型返回不同的错误响应
            if ("No Permission".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "No Permission", null));
            } else if ("Schedules can not be empty.".equals(e.getMessage())) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Schedules can not be empty.", null));
            } else if ("Invalid operation, the program has been modified!".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "Invalid operation, the program has been modified!", null));
            } else {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("更新终端排程异常: terminalId={}", terminalId, e);
            return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                500, "Internal server error", null));
        }
    }
    
    /**
     * 获取终端排程信息
     * URL: /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules
     * Method: GET
     */
    @GetMapping("/{terminalId}/terminalSchedules")
    public ResponseEntity<?> getTerminalSchedule(@PathVariable Integer terminalId) {
        logger.info("收到获取终端排程信息请求: terminalId={}", terminalId);
        
        try {
            // 验证终端ID格式
            if (terminalId == null || terminalId <= 0) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Bad Request.", null));
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 获取终端排程信息
            TerminalScheduleResponse response = terminalScheduleService.getTerminalSchedule(terminalId, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("获取终端排程信息失败: terminalId={}", terminalId, e);
            
            // 根据错误类型返回不同的错误响应
            if ("No Permission".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "No Permission", null));
            } else {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("获取终端排程信息异常: terminalId={}", terminalId, e);
            return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                500, "Internal server error", null));
        }
    }
    
    /**
     * 获取终端组排程信息
     * URL: /wp-json/wp/v3/schedules/terminalGroupSchedules
     * Method: GET
     */
    @GetMapping("/terminalGroupSchedules")
    public ResponseEntity<?> getTerminalGroupSchedules(@RequestParam Set<Integer> terminalGroupIds) {
        logger.info("收到获取终端组排程信息请求: terminalGroupIds={}", terminalGroupIds);
        
        try {
            // 验证终端组ID参数
            if (terminalGroupIds == null || terminalGroupIds.isEmpty()) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Terminal group IDs cannot be null or empty", null));
            }
            
            // 验证终端组ID格式
            for (Integer terminalGroupId : terminalGroupIds) {
                if (terminalGroupId == null || terminalGroupId <= 0) {
                    return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                        400, "Invalid terminal group ID: " + terminalGroupId, null));
                }
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 获取终端组排程信息
            TerminalGroupSchedulesResponse response = terminalGroupScheduleService.getTerminalGroupSchedules(
                terminalGroupIds, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("获取终端组排程信息失败: terminalGroupIds={}", terminalGroupIds, e);
            
            // 根据错误类型返回不同的错误响应
            if ("No Permission".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "No Permission", null));
            } else if (e.getMessage().contains("List group schedules error")) {
                return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                    500, "List group schedules error.", null));
            } else {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("获取终端组排程信息异常: terminalGroupIds={}", terminalGroupIds, e);
            return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                500, "Internal server error", null));
        }
    }
    
    /**
     * 获取当前用户ID
     * 在实际应用中，这里应该从认证信息中获取
     */
    private Integer getCurrentUserId() {
        // 这里应该从JWT token或session中获取当前用户ID
        // 暂时返回默认值
        return 1;
    }
} 