package com.cloudcontrol.controller.terminal;

import com.cloudcontrol.dto.terminal.TerminalCreateRequest;
import com.cloudcontrol.dto.terminal.TerminalCreateResponse;
import com.cloudcontrol.dto.terminal.TerminalListRequest;
import com.cloudcontrol.dto.terminal.TerminalListResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestMultipleRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsV2Response;
import com.cloudcontrol.dto.terminal.TerminalTrackResponse;
import com.cloudcontrol.dto.terminal.TerminalTrackRequest;
import com.cloudcontrol.dto.terminal.TerminalMileageResponse;
import com.cloudcontrol.dto.terminal.TerminalMileageRequest;
import com.cloudcontrol.dto.terminal.TerminalUpdateRequest;
import com.cloudcontrol.dto.terminal.TerminalUpdateResponse;
import com.cloudcontrol.dto.terminal.TerminalDeleteResponse;
import com.cloudcontrol.service.terminal.TerminalService;
import com.cloudcontrol.service.terminal.TerminalTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 终端管理控制器
 * 提供终端的增删改查功能
 */
@RestController
@RequestMapping("wp-json/wp/v2/leds")
public class TerminalController {

    private final TerminalService terminalService;
    private final TerminalTagService terminalTagService;
    private static final Logger logger = LoggerFactory.getLogger(TerminalController.class);

    public TerminalController(TerminalService terminalService, TerminalTagService terminalTagService) {
        this.terminalService = terminalService;
        this.terminalTagService = terminalTagService;
    }

    /**
     * 获取终端列表
     * URL: /wp-json/wp/v2/leds
     * Method: GET
     */
    @GetMapping("")
    public ResponseEntity<?> getTerminalList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "12") int perPage,
            @RequestParam(value = "download_status", defaultValue = "true") Boolean downloadStatus,
            @RequestParam(value = "term_parents", defaultValue = "1") int termParents,
            @RequestParam(value = "terminalgroup", required = false) Integer terminalGroup,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "count", defaultValue = "0") int count,
            @RequestParam(value = "withCoordinate", defaultValue = "false") Boolean withCoordinate,
            @RequestParam(value = "tagIds", required = false) String tagIds,
            @RequestParam(value = "authorId", required = false) Integer authorId,
            @RequestParam(value = "regionTaskId", required = false) Integer regionTaskId,
            @RequestParam(value = "gpsonly", required = false) String gpsonly,
            @RequestParam(value = "program", required = false) Integer program) {
        
        logger.info("收到获取终端列表请求: page={}, perPage={}, search={}, gpsonly={}, program={}", 
                   page, perPage, search, gpsonly, program);
        
        try {
            // 如果请求节目发布到的终端列表
            if (program != null && program > 0) {
                TerminalListResponse response = terminalService.getTerminalsByProgram(program, page, perPage);
                return ResponseEntity.ok(response);
            }
            
            // 如果请求GPS坐标专用接口
            if ("gpsonly".equals(gpsonly)) {
                return getGpsTerminalList(terminalGroup);
            }
            
            // 如果提供了tagIds参数，则按标签查询
            if (tagIds != null && !tagIds.trim().isEmpty()) {
                TerminalListResponse response = terminalTagService.getTerminalsByTagIds(tagIds);
                return ResponseEntity.ok(response);
            }
            
            // 原有的终端列表逻辑
            TerminalListRequest request = new TerminalListRequest();
            request.setPage(page);
            request.setPerPage(perPage);
            request.setDownloadStatus(downloadStatus);
            request.setTermParents(termParents);
            request.setTerminalGroup(terminalGroup);
            request.setSearch(search);
            request.setCount(count);
            request.setWithCoordinate(withCoordinate);
            request.setTagIds(tagIds);
            request.setAuthorId(authorId);
            request.setRegionTaskId(regionTaskId);
            
            TerminalListResponse response = terminalService.getTerminalList(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取终端列表失败", e);
            return ResponseEntity.badRequest().body(
                new TerminalListResponse(null, e.getMessage())
            );
        }
    }
    
    /**
     * 获取带有GPS坐标的终端列表
     * @param terminalGroup 终端组ID
     * @return GPS终端列表响应
     */
    private ResponseEntity<List<TerminalGpsResponse>> getGpsTerminalList(Integer terminalGroup) {
        try {
            List<TerminalGpsResponse> gpsTerminals = terminalService.getGpsTerminalList(terminalGroup);
            return ResponseEntity.ok(gpsTerminals);
        } catch (Exception e) {
            logger.error("获取GPS终端列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取单个终端的最新GPS信息 (v3)
     * URL: /wp-json/led/v3/monitor/query/latest/single
     * Method: POST
     */
    @PostMapping("/wp-json/led/v3/monitor/query/latest/single")
    public ResponseEntity<TerminalGpsLatestResponse> getLatestGpsSingle(@RequestBody TerminalGpsLatestRequest request) {
        logger.info("收到获取单个终端最新GPS信息请求: terminalId={}", request.getTerminalId());
        
        try {
            TerminalGpsLatestResponse response = terminalService.getLatestGpsSingle(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取单个终端最新GPS信息失败: terminalId={}", request.getTerminalId(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 获取多个终端的最新GPS信息 (v3)
     * URL: /wp-json/led/v3/monitor/query/latest
     * Method: POST
     */
    @PostMapping("/wp-json/led/v3/monitor/query/latest")
    public ResponseEntity<List<TerminalGpsLatestResponse>> getLatestGpsMultiple(@RequestBody TerminalGpsLatestMultipleRequest request) {
        logger.info("收到获取多个终端最新GPS信息请求: terminalIds={}", request.getTerminalIds());
        
        try {
            List<TerminalGpsLatestResponse> responses = terminalService.getLatestGpsMultiple(request);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            logger.error("获取多个终端最新GPS信息失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 获取单个终端的最新GPS信息 (v2兼容版本)
     * URL: /wp-json/wp/v2/leds/{ledId}?gpsonly=gpsonly
     * Method: GET
     */
    @GetMapping("/{ledId}")
    public ResponseEntity<?> getTerminalById(
            @PathVariable("ledId") Integer ledId,
            @RequestParam(value = "download_status", defaultValue = "true") Boolean downloadStatus,
            @RequestParam(value = "gpsonly", required = false) String gpsonly) {
        
        logger.info("收到获取单个终端请求: ledId={}, downloadStatus={}, gpsonly={}", ledId, downloadStatus, gpsonly);
        
        try {
            // 如果是GPS专用请求
            if ("gpsonly".equals(gpsonly)) {
                TerminalGpsV2Response response = terminalService.getLatestGpsV2(ledId);
                return ResponseEntity.ok(response);
            }
            
            // 原有的单个终端查询逻辑
            if (ledId == null || ledId <= 0) {
                return ResponseEntity.badRequest().body(
                    new TerminalListResponse(null, "Invalid terminal ID")
                );
            }
            
            TerminalListRequest request = new TerminalListRequest();
            request.setDownloadStatus(downloadStatus);
            request.setTerminalIds(List.of(ledId));
            
            TerminalListResponse response = terminalService.getTerminalById(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取单个终端失败: ledId={}", ledId, e);
            return ResponseEntity.badRequest().body(
                new TerminalListResponse(null, e.getMessage())
            );
        }
    }

    /**
     * 根据终端ID数组获取终端信息
     * URL: /wp-json/wp/v2/leds/terminals
     * Method: GET
     */
    @GetMapping("/terminals")
    public ResponseEntity<TerminalListResponse> getTerminalsByIds(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "12") int perPage,
            @RequestParam(value = "download_status", defaultValue = "true") Boolean downloadStatus,
            @RequestParam(value = "term_parents", defaultValue = "1") int termParents,
            @RequestParam(value = "withCoordinate", defaultValue = "false") Boolean withCoordinate,
            @RequestParam(value = "tagIds", required = false) String tagIds,
            @RequestParam(value = "authorId", required = false) Integer authorId,
            @RequestParam(value = "regionTaskId", required = false) Integer regionTaskId,
            @RequestParam("terminalIds") String terminalIds) {
        
        logger.info("收到根据终端ID获取终端信息请求: terminalIds={}", terminalIds);
        
        try {
            // 解析终端ID数组
            String[] idStrings = terminalIds.split(",");
            List<Integer> ids = new ArrayList<>();
            for (String idStr : idStrings) {
                try {
                    ids.add(Integer.parseInt(idStr.trim()));
                } catch (NumberFormatException e) {
                    logger.warn("无效的终端ID: {}", idStr);
                }
            }
            
            if (ids.isEmpty()) {
                return ResponseEntity.badRequest().body(
                    new TerminalListResponse(null, "No valid terminal IDs provided")
                );
            }
            
            TerminalListRequest request = new TerminalListRequest();
            request.setPage(page);
            request.setPerPage(perPage);
            request.setDownloadStatus(downloadStatus);
            request.setTermParents(termParents);
            request.setWithCoordinate(withCoordinate);
            request.setTagIds(tagIds);
            request.setAuthorId(authorId);
            request.setRegionTaskId(regionTaskId);
            request.setTerminalIds(ids);
            
            TerminalListResponse response = terminalService.getTerminalsByIds(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("根据终端ID获取终端信息失败", e);
            return ResponseEntity.badRequest().body(
                new TerminalListResponse(null, e.getMessage())
            );
        }
    }

    /**
     * 创建终端并创建终端账号
     * URL: /wp-json/wp/v2/leds/account
     * Method: POST
     */
    @PostMapping("/account")
    public ResponseEntity<TerminalCreateResponse> createTerminalWithAccount(@RequestBody TerminalCreateRequest request) {
        logger.info("收到创建终端请求: {}", request.getTerminalModel().getTitle());
        
        try {
            TerminalCreateResponse response = terminalService.createTerminalWithAccount(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("创建终端失败", e);
            // 根据异常类型返回不同的错误响应
            return ResponseEntity.badRequest().body(
                new TerminalCreateResponse(null, e.getMessage())
            );
        }
    }
    
    /**
     * 修改终端信息或移动终端
     * URL: /wp-json/wp/v3/terminals
     * Method: PUT
     */
    @PutMapping("/wp-json/wp/v3/terminals")
    public ResponseEntity<TerminalUpdateResponse> updateTerminal(@RequestBody TerminalUpdateRequest request) {
        logger.info("收到修改终端信息请求: terminalId={}, title={}, terminalGroupId={}", 
                   request.getTerminal().getId(), request.getTerminal().getTitle(), 
                   request.getTerminal().getTerminalGroupId());
        
        try {
            TerminalUpdateResponse response = terminalService.updateTerminal(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("修改终端信息失败: terminalId={}", request.getTerminal().getId(), e);
            return ResponseEntity.badRequest().body(
                new TerminalUpdateResponse("500", e.getMessage(), null)
            );
        }
    }
    
    /**
     * 测试端点 - 用于验证API是否正常工作
     * URL: /wp-json/wp/v2/leds/test
     * Method: GET
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Terminal API is working!");
    }
    
    /**
     * 测试终端列表端点
     * URL: /wp-json/wp/v2/leds/test-list
     * Method: GET
     */
    @GetMapping("/test-list")
    public ResponseEntity<String> testList() {
        return ResponseEntity.ok("Terminal List API is working!");
    }
    
    /**
     * 测试根据ID获取终端端点
     * URL: /wp-json/wp/v2/leds/test-terminals
     * Method: GET
     */
    @GetMapping("/test-terminals")
    public ResponseEntity<String> testTerminals() {
        return ResponseEntity.ok("Terminal by IDs API is working!");
    }
    
    /**
     * 测试单个终端端点
     * URL: /wp-json/wp/v2/leds/test-single
     * Method: GET
     */
    @GetMapping("/test-single")
    public ResponseEntity<String> testSingle() {
        return ResponseEntity.ok("Single Terminal API is working!");
    }
    
    /**
     * 测试GPS终端列表端点
     * URL: /wp-json/wp/v2/leds/test-gps
     * Method: GET
     */
    @GetMapping("/test-gps")
    public ResponseEntity<String> testGps() {
        return ResponseEntity.ok("GPS Terminal List API is working!");
    }
    
    /**
     * 测试GPS最新信息端点
     * URL: /wp-json/led/v3/monitor/test
     * Method: GET
     */
    @GetMapping("/wp-json/led/v3/monitor/test")
    public ResponseEntity<String> testGpsLatest() {
        return ResponseEntity.ok("GPS Latest Info API is working!");
    }
    
    /**
     * 测试轨迹和里程数端点
     * URL: /wp-json/led/v3/monitor/test-track
     * Method: GET
     */
    @GetMapping("/wp-json/led/v3/monitor/test-track")
    public ResponseEntity<String> testTrackAndMileage() {
        return ResponseEntity.ok("Track and Mileage API is working!");
    }
    
    /**
     * 测试终端更新端点
     * URL: /wp-json/wp/v3/terminals/test
     * Method: GET
     */
    @GetMapping("/wp-json/wp/v3/terminals/test")
    public ResponseEntity<String> testTerminalUpdate() {
        return ResponseEntity.ok("Terminal Update API is working!");
    }
    
    /**
     * 测试终端删除端点
     * URL: /wp-json/wp/v2/leds/test-delete
     * Method: GET
     */
    @GetMapping("/test-delete")
    public ResponseEntity<String> testTerminalDelete() {
        return ResponseEntity.ok("Terminal Delete API is working!");
    }

    /**
     * 查询终端轨迹
     * URL: /wp-json/led/v3/monitor/query/track
     * Method: POST
     */
    @PostMapping("/wp-json/led/v3/monitor/query/track")
    public ResponseEntity<TerminalTrackResponse> getTerminalTrack(@RequestBody TerminalTrackRequest request) {
        logger.info("收到查询终端轨迹请求: terminalId={}, startTime={}, endTime={}", 
                   request.getTerminalId(), request.getStartTime(), request.getEndTime());
        
        try {
            TerminalTrackResponse response = terminalService.getTerminalTrack(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("查询终端轨迹失败: terminalId={}", request.getTerminalId(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 获取终端里程数
     * URL: /wp-json/led/v3/monitor/query/mileage/duration
     * Method: POST
     */
    @PostMapping("/wp-json/led/v3/monitor/query/mileage/duration")
    public ResponseEntity<TerminalMileageResponse> getTerminalMileage(@RequestBody TerminalMileageRequest request) {
        logger.info("收到获取终端里程数请求: startTime={}, endTime={}, terminalIds={}", 
                   request.getStartTime(), request.getEndTime(), request.getTerminalIds());
        
        try {
            TerminalMileageResponse response = terminalService.getTerminalMileage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取终端里程数失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 根据终端ID强制删除终端
     * URL: /wp-json/wp/v2/leds/{ledId}
     * Method: DELETE
     */
    @DeleteMapping("/{ledId}")
    public ResponseEntity<TerminalDeleteResponse> deleteTerminal(@PathVariable("ledId") Integer ledId) {
        logger.info("收到删除终端请求: ledId={}", ledId);
        
        try {
            // 验证终端ID
            if (ledId == null || ledId <= 0) {
                return ResponseEntity.badRequest().body(
                    new TerminalDeleteResponse(null, "Invalid terminal ID")
                );
            }
            
            TerminalDeleteResponse response = terminalService.deleteTerminal(ledId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("删除终端失败: ledId={}", ledId, e);
            return ResponseEntity.badRequest().body(
                new TerminalDeleteResponse(null, e.getMessage())
            );
        }
    }
    
    /**
     * 测试节目终端查询端点
     * URL: /wp-json/wp/v2/leds/test-program
     * Method: GET
     */
    @GetMapping("/test-program")
    public ResponseEntity<String> testProgramTerminal() {
        return ResponseEntity.ok("Program Terminal API is working!");
    }
} 