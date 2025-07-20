package com.cloudcontrol.controller.terminal;

import com.cloudcontrol.dto.terminal.TerminalTagResponse;
import com.cloudcontrol.dto.terminal.TerminalTagAddRequest;
import com.cloudcontrol.dto.terminal.TerminalListResponse;
import com.cloudcontrol.service.terminal.TerminalTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

/**
 * 终端标签控制器
 */
@RestController
@RequestMapping("wp-json/wp/v2/leds/tag")
public class TerminalTagController {
    
    private final TerminalTagService terminalTagService;
    private static final Logger logger = LoggerFactory.getLogger(TerminalTagController.class);
    
    public TerminalTagController(TerminalTagService terminalTagService) {
        this.terminalTagService = terminalTagService;
    }
    
    /**
     * 查询所有标签
     * URL: /wp-json/wp/v2/leds/tag/tags
     * Method: GET
     */
    @GetMapping("/tags")
    public ResponseEntity<TerminalTagResponse> getAllTags(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        
        logger.info("收到查询所有标签请求: pageNum={}, pageSize={}", pageNum, pageSize);
        
        try {
            TerminalTagResponse response = terminalTagService.getAllTags(pageNum, pageSize);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("查询所有标签失败", e);
            return ResponseEntity.badRequest().body(
                new TerminalTagResponse(new ArrayList<>(), 0)
            );
        }
    }
    
    /**
     * 根据标签ID查询终端
     * URL: /wp-json/wp/v2/leds/tag?tagIds=7,6
     * Method: GET
     */
    @GetMapping("")
    public ResponseEntity<TerminalListResponse> getTerminalsByTagIds(
            @RequestParam(value = "tagIds", required = false) String tagIds,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "12") int perPage,
            @RequestParam(value = "download_status", defaultValue = "true") Boolean downloadStatus,
            @RequestParam(value = "term_parents", defaultValue = "1") int termParents,
            @RequestParam(value = "terminalgroup", required = false) Integer terminalGroup,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "count", defaultValue = "0") int count,
            @RequestParam(value = "withCoordinate", defaultValue = "false") Boolean withCoordinate,
            @RequestParam(value = "authorId", required = false) Integer authorId,
            @RequestParam(value = "regionTaskId", required = false) Integer regionTaskId,
            @RequestParam(value = "gpsonly", required = false) String gpsonly) {
        
        logger.info("收到根据标签查询终端请求: tagIds={}", tagIds);
        
        try {
            // 如果提供了tagIds参数，则按标签查询
            if (tagIds != null && !tagIds.trim().isEmpty()) {
                TerminalListResponse response = terminalTagService.getTerminalsByTagIds(tagIds);
                return ResponseEntity.ok(response);
            }
            
            // 否则返回空结果（这里应该调用原有的终端列表查询逻辑）
            return ResponseEntity.ok(new TerminalListResponse(new ArrayList<>(), "No tag IDs provided"));
            
        } catch (Exception e) {
            logger.error("根据标签查询终端失败: tagIds={}", tagIds, e);
            return ResponseEntity.badRequest().body(
                new TerminalListResponse(new ArrayList<>(), e.getMessage())
            );
        }
    }
    
    /**
     * 获取终端标签列表
     * URL: /wp-json/wp/v2/leds/tag/{ledId}/tags
     * Method: GET
     */
    @GetMapping("/{ledId}/tags")
    public ResponseEntity<TerminalTagResponse> getTerminalTags(
            @PathVariable("ledId") Integer ledId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "12") Integer pageSize) {
        
        logger.info("收到获取终端标签请求: ledId={}, pageNum={}, pageSize={}", ledId, pageNum, pageSize);
        
        try {
            // 验证终端ID
            if (ledId == null || ledId <= 0) {
                return ResponseEntity.badRequest().body(
                    new TerminalTagResponse(new ArrayList<>(), 0)
                );
            }
            
            TerminalTagResponse response = terminalTagService.getTerminalTags(ledId, pageNum, pageSize);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取终端标签失败: ledId={}", ledId, e);
            return ResponseEntity.badRequest().body(
                new TerminalTagResponse(new ArrayList<>(), 0)
            );
        }
    }
    
    /**
     * 添加终端标签
     * URL: /wp-json/wp/v2/leds/tag/tags
     * Method: POST
     */
    @PostMapping("/tags")
    public ResponseEntity<String> addTerminalTags(@RequestBody TerminalTagAddRequest request) {
        logger.info("收到添加终端标签请求: {}", request.getTerminalTags());
        
        try {
            terminalTagService.addTerminalTags(request);
            return ResponseEntity.ok("Tags added successfully");
        } catch (Exception e) {
            logger.error("添加终端标签失败", e);
            return ResponseEntity.badRequest().body("Failed to add tags: " + e.getMessage());
        }
    }
    
    /**
     * 删除终端标签
     * URL: /wp-json/wp/v2/leds/tag/{ledId}/tags
     * Method: DELETE
     */
    @DeleteMapping("/{ledId}/tags")
    public ResponseEntity<String> deleteTerminalTags(
            @PathVariable("ledId") Integer ledId,
            @RequestBody List<Integer> tagIds) {
        
        logger.info("收到删除终端标签请求: ledId={}, tagIds={}", ledId, tagIds);
        
        try {
            // 验证终端ID
            if (ledId == null || ledId <= 0) {
                return ResponseEntity.badRequest().body("Invalid terminal ID");
            }
            
            // 验证标签ID列表
            if (tagIds == null || tagIds.isEmpty()) {
                return ResponseEntity.badRequest().body("Tag IDs cannot be empty");
            }
            
            terminalTagService.deleteTerminalTags(ledId, tagIds);
            return ResponseEntity.ok("Tags deleted successfully");
        } catch (Exception e) {
            logger.error("删除终端标签失败: ledId={}", ledId, e);
            return ResponseEntity.badRequest().body("Failed to delete tags: " + e.getMessage());
        }
    }
    
    /**
     * 测试标签API端点
     * URL: /wp-json/wp/v2/leds/tag/tags/test
     * Method: GET
     */
    @GetMapping("/tags/test")
    public ResponseEntity<String> testTagApi() {
        return ResponseEntity.ok("Terminal Tag API is working!");
    }
} 