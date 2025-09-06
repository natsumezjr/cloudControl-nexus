package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.ProgramCreateRequest;
import com.cloudcontrol.dto.program.ProgramResponse;
import com.cloudcontrol.dto.program.ProgramListResponse;
import com.cloudcontrol.dto.program.ProgramPublishRequest;
import com.cloudcontrol.dto.program.ProgramPublishResponse;
import com.cloudcontrol.dto.program.ProgramPublishErrorResponse;
import com.cloudcontrol.dto.program.ProgramBatchDeleteRequest;
import com.cloudcontrol.dto.program.ProgramBatchDeleteResponse;
import com.cloudcontrol.dto.program.ProgramUnpublishRequest;
import com.cloudcontrol.dto.program.ProgramUnpublishErrorResponse;
import com.cloudcontrol.service.program.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节目控制器
 */
@RestController
//@RequestMapping("/wp-json/wp/v2")
public class ProgramController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);
    
    private final ProgramService programService;
    
    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }
    
    /**
     * 创建节目
     * URL: /wp-json/wp/v2/programs
     * HTTP请求方式: POST
     */
    @PostMapping("/programs")
    public ResponseEntity<ProgramResponse> createProgram(@RequestBody ProgramCreateRequest request) {
        logger.info("收到创建节目请求: title={}", request.getTitle());
        
        try {
            if (request == null || request.getTitle() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            ProgramResponse response = programService.createProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("创建节目失败: title={}", request.getTitle(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 获取节目列表
     * URL: /wp-json/wp/v2/programs
     * HTTP请求方式: GET
     */
    @GetMapping("/programs")
    public ResponseEntity<List<ProgramListResponse>> getProgramList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "12") int perPage,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "mime_type", required = false) String mimeType,
            @RequestParam(value = "author", required = false) Integer author,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderby", required = false) String orderby,
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "after", required = false) String after,
            @RequestParam(value = "terminalGroupId", required = false) Integer terminalGroupId) {
        
        logger.info("收到获取节目列表请求: page={}, perPage={}, status={}, author={}, search={}, orderby={}, before={}, after={}", 
                   page, perPage, status, author, search, orderby, before, after);
        
        try {
            // 参数验证
            if (page < 1) page = 1;
            if (perPage < 1) perPage = 12;
            if (perPage > 100) perPage = 100;
            
            List<ProgramListResponse> response = programService.getProgramList(
                page, perPage, status, mimeType, author, search, orderby, before, after, terminalGroupId);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("获取节目列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 获取单个节目
     * URL: /wp-json/wp/v2/programs/{programId}
     * HTTP请求方式: GET
     */
    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramResponse> getProgram(@PathVariable Integer programId) {
        logger.info("收到获取节目请求: id={}", programId);
        
        try {
            if (programId == null || programId <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            ProgramResponse response = programService.getProgramById(programId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("获取节目失败: id={}", programId, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 更新节目或发布节目
     * URL: /wp-json/wp/v2/programs/{programId} 或 /wp-json/wp/v2/programs/{programId}?flag=terminalgroup
     * HTTP请求方式: PUT
     */
    @PutMapping("/programs/{programId}")
    public ResponseEntity<?> updateOrPublishProgram(
            @PathVariable Integer programId,
            @RequestParam(value = "flag", required = false) String flag,
            @RequestBody Object request) {
        
        logger.info("收到更新/发布节目请求: id={}, flag={}", programId, flag);
        
        try {
            if (programId == null || programId <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // 根据flag参数判断是更新还是发布
            if ("terminalgroup".equals(flag)) {
                // 发布节目
                if (request instanceof ProgramPublishRequest) {
                    ProgramPublishRequest publishRequest = (ProgramPublishRequest) request;
                    Integer currentUserId = getCurrentUserId();
                    ProgramPublishResponse response = programService.publishProgram(programId, publishRequest, currentUserId);
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.badRequest().body(new ProgramPublishErrorResponse(
                        "Invalid request body for publishing", 40005, 400));
                }
            } else {
                // 更新节目
                if (request instanceof ProgramCreateRequest) {
                    ProgramCreateRequest updateRequest = (ProgramCreateRequest) request;
                    ProgramResponse response = programService.updateProgram(programId, updateRequest);
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            
        } catch (RuntimeException e) {
            logger.error("更新/发布节目失败: id={}", programId, e);
            
            // 处理发布相关的错误
            if ("some group out of control".equals(e.getMessage())) {
                return ResponseEntity.status(500).body(new ProgramPublishErrorResponse(
                    "some group out of control", 20000, 500));
            } else if ("No permission to control program".equals(e.getMessage())) {
                return ResponseEntity.status(403).body(new ProgramPublishErrorResponse(
                    "No permission to control program", 20001, 403));
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            logger.error("更新/发布节目异常: id={}", programId, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 删除节目
     * URL: /wp-json/wp/v2/programs/{programId}
     * HTTP请求方式: DELETE
     */
    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<Boolean> deleteProgram(@PathVariable Integer programId) {
        logger.info("收到删除节目请求: id={}", programId);
        
        try {
            if (programId == null || programId <= 0) {
                return ResponseEntity.badRequest().body(false);
            }
            
            boolean result = programService.deleteProgram(programId);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("删除节目失败: id={}", programId, e);
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    /**
     * 批量删除节目
     * URL: /wp-json/wp/v2/programs/delprograms
     * HTTP请求方式: POST
     */
    @PostMapping("/programs/delprograms")
    public ResponseEntity<List<ProgramBatchDeleteResponse>> batchDeletePrograms(@RequestBody ProgramBatchDeleteRequest request) {
        logger.info("收到批量删除节目请求: programIds={}", request.getProgramIds());
        
        try {
            if (request == null || request.getProgramIds() == null || request.getProgramIds().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 批量删除节目
            List<ProgramBatchDeleteResponse> response = programService.batchDeletePrograms(request, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("批量删除节目失败: programIds={}", request.getProgramIds(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("批量删除节目异常: programIds={}", request.getProgramIds(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 删除播放盒节目（取消发布）
     * URL: /wp-json/wp/v2/programPublishRecords
     * HTTP请求方式: DELETE
     */
    @DeleteMapping("/programPublishRecords")
    public ResponseEntity<?> unpublishProgram(@RequestBody ProgramUnpublishRequest request) {
        logger.info("收到删除播放盒节目请求: programId={}, vsnMd5={}, terminalIds={}", 
                   request.getProgramId(), request.getVsnMd5(), request.getTerminalIds());
        
        try {
            if (request == null || request.getProgramId() == null) {
                return ResponseEntity.badRequest().body(new ProgramUnpublishErrorResponse(
                    400, "Request body cannot be null", null));
            }
            
            // 获取当前用户ID（这里应该从认证信息中获取）
            Integer currentUserId = getCurrentUserId();
            
            // 删除播放盒节目
            ProgramBatchDeleteResponse response = programService.unpublishProgram(request, currentUserId);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("删除播放盒节目失败: programId={}", request.getProgramId(), e);
            
            // 根据错误类型返回不同的错误响应
            if ("No permission to unpublish program".equals(e.getMessage())) {
                return ResponseEntity.status(401).body(new ProgramUnpublishErrorResponse(
                    401, "Unauthorized", null));
            } else if (e.getMessage().contains("No permission")) {
                return ResponseEntity.status(403).body(new ProgramUnpublishErrorResponse(
                    50000, "No permission.", null));
            } else {
                return ResponseEntity.badRequest().body(new ProgramUnpublishErrorResponse(
                    400, e.getMessage(), null));
            }
        } catch (Exception e) {
            logger.error("删除播放盒节目异常: programId={}", request.getProgramId(), e);
            return ResponseEntity.status(500).body(new ProgramUnpublishErrorResponse(
                500, "Internal server error", null));
        }
    }
    
    /**
     * 获取当前用户ID
     * 在实际应用中，这里应该从认证信息中获取
     */
    private Integer getCurrentUserId() {
        // 这里应该从JWT token或session中获取用户ID
        // 暂时返回默认值1，实际应用中需要实现真实的用户认证
        return 1;
    }
    
    /**
     * 测试端点
     * URL: /wp-json/wp/v2/programs/test
     * HTTP请求方式: GET
     */
    @GetMapping("/programs/test")
    public ResponseEntity<String> test() {
        logger.info("收到测试请求");
        return ResponseEntity.ok("Program API is working!");
    }
} 