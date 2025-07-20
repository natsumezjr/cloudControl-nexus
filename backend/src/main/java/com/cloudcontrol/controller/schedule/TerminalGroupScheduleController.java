package com.cloudcontrol.controller.schedule;

import com.cloudcontrol.dto.schedule.ApplyTerminalGroupScheduleRequest;
import com.cloudcontrol.dto.schedule.ScheduleErrorResponse;
import com.cloudcontrol.dto.schedule.TerminalGroupScheduleResponse;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleRequest;
import com.cloudcontrol.dto.schedule.SimplifiedScheduleResponse;
import com.cloudcontrol.service.schedule.TerminalGroupScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 终端组排程控制器
 */
@RestController
@RequestMapping("/wp-json/wp/v2/terminalgroup")
public class TerminalGroupScheduleController {
    
    private static final Logger logger = LoggerFactory.getLogger(TerminalGroupScheduleController.class);
    
    @Autowired
    private TerminalGroupScheduleService terminalGroupScheduleService;
    
    /**
     * 应用终端组排程
     * URL: /wp-json/wp/v2/terminalgroup/{terminalGroupId}
     * Method: PUT
     */
    @PutMapping("/{terminalGroupId}")
    public ResponseEntity<?> applyTerminalGroupSchedule(@PathVariable Integer terminalGroupId,
                                                       @RequestBody ApplyTerminalGroupScheduleRequest request) {
        logger.info("收到应用终端组排程请求: terminalGroupId={}", terminalGroupId);
        
        try {
            // 验证终端组ID格式
            if (terminalGroupId == null || terminalGroupId <= 0) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Bad Request.", null));
            }
            
            // 验证请求体
            if (request == null) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Request body cannot be null", null));
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 应用终端组排程
            TerminalGroupScheduleResponse response = terminalGroupScheduleService.applyTerminalGroupSchedule(
                terminalGroupId, request, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("应用终端组排程失败: terminalGroupId={}", terminalGroupId, e);
            
            // 根据错误类型返回不同的错误响应
            if ("No Permission".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "No Permission", null));
            } else if (e.getMessage().contains("Invalid") || e.getMessage().contains("cannot be null")) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, e.getMessage(), null));
            } else {
                return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                    500, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("应用终端组排程异常: terminalGroupId={}", terminalGroupId, e);
            return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                500, "Internal server error", null));
        }
    }
    
    /**
     * 简化应用排程（终端组或终端）
     * URL: /wp-json/wp/v2/terminalgroup/simplifiedSchedule
     * Method: PUT
     */
    @PutMapping("/simplifiedSchedule")
    public ResponseEntity<?> applySimplifiedSchedule(@RequestBody SimplifiedScheduleRequest request) {
        logger.info("收到简化应用排程请求: request={}", request);
        
        try {
            // 验证请求体
            if (request == null) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, "Request body cannot be null", null));
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 应用简化排程
            SimplifiedScheduleResponse response = terminalGroupScheduleService.applySimplifiedSchedule(request, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("简化应用排程失败", e);
            
            // 根据错误类型返回不同的错误响应
            if ("No Permission".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ScheduleErrorResponse(
                    403, "No Permission", null));
            } else if (e.getMessage().contains("Invalid") || e.getMessage().contains("cannot be null")) {
                return ResponseEntity.badRequest().body(new ScheduleErrorResponse(
                    400, e.getMessage(), null));
            } else {
                return ResponseEntity.status(500).body(new ScheduleErrorResponse(
                    500, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("简化应用排程异常", e);
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