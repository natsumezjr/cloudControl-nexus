package com.cloudcontrol.controller.program;

import com.cloudcontrol.dto.program.SimplifyCreateProgramRequest;
import com.cloudcontrol.dto.program.SimplifyCreateProgramResponse;
import com.cloudcontrol.service.program.SimplifyCreateProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 节目信息控制器
 */
@RestController
@RequestMapping("/wp-json/wp/v2/programs")
public class ProgramInfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProgramInfoController.class);
    
    private final SimplifyCreateProgramService simplifyCreateProgramService;
    
    @Autowired
    public ProgramInfoController(SimplifyCreateProgramService simplifyCreateProgramService) {
        this.simplifyCreateProgramService = simplifyCreateProgramService;
    }
    
    /**
     * 创建网页节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/web
     * Method: POST
     */
    @PostMapping("/simplifyCreate/web")
    public ResponseEntity<SimplifyCreateProgramResponse> createWebProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建网页节目请求: programName={}", request.getProgramName());
        
        try {
            if (request == null || request.getProgramName() == null || request.getUrl() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createWebProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建网页节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建网页节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建文件节目或同步节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/document
     * Method: POST
     */
    @PostMapping("/simplifyCreate/document")
    public ResponseEntity<SimplifyCreateProgramResponse> createDocumentProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建文件节目请求: programName={}, mediaId={}", request.getProgramName(), request.getMediaId());
        
        try {
            if (request == null || request.getProgramName() == null || request.getMediaId() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createDocumentProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建文件节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建文件节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建单行文本或多行文本节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/lineText
     * Method: POST
     */
    @PostMapping("/simplifyCreate/lineText")
    public ResponseEntity<SimplifyCreateProgramResponse> createLineTextProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建文本节目请求: programName={}, lineText={}", request.getProgramName(), request.getLineText());
        
        try {
            if (request == null || request.getProgramName() == null || request.getLineText() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createLineTextProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建文本节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建文本节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建时钟节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/clock
     * Method: POST
     */
    @PostMapping("/simplifyCreate/clock")
    public ResponseEntity<SimplifyCreateProgramResponse> createClockProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建时钟节目请求: programName={}, clockType={}", request.getProgramName(), request.getClockType());
        
        try {
            if (request == null || request.getProgramName() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createClockProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建时钟节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建时钟节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建天气节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/weather
     * Method: POST
     */
    @PostMapping("/simplifyCreate/weather")
    public ResponseEntity<SimplifyCreateProgramResponse> createWeatherProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建天气节目请求: programName={}, regionName={}", request.getProgramName(), request.getRegionName());
        
        try {
            if (request == null || request.getProgramName() == null || request.getRegionName() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createWeatherProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建天气节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建天气节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建倒计时节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/countDown
     * Method: POST
     */
    @PostMapping("/simplifyCreate/countDown")
    public ResponseEntity<SimplifyCreateProgramResponse> createCountDownProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建倒计时节目请求: programName={}, endDateTime={}", request.getProgramName(), request.getEndDateTime());
        
        try {
            if (request == null || request.getProgramName() == null || request.getEndDateTime() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createCountDownProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建倒计时节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建倒计时节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建环境节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/environment
     * Method: POST
     */
    @PostMapping("/simplifyCreate/environment")
    public ResponseEntity<SimplifyCreateProgramResponse> createEnvironmentProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建环境节目请求: programName={}, environmentType={}", request.getProgramName(), request.getEnvironmentType());
        
        try {
            if (request == null || request.getProgramName() == null || request.getEnvironmentType() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createEnvironmentProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建环境节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建环境节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建新闻节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate/rss
     * Method: POST
     */
    @PostMapping("/simplifyCreate/rss")
    public ResponseEntity<SimplifyCreateProgramResponse> createRssProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建新闻节目请求: programName={}, rssUrl={}", request.getProgramName(), request.getRssUrl());
        
        try {
            if (request == null || request.getProgramName() == null || request.getRssUrl() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createRssProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建新闻节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建新闻节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
    
    /**
     * 创建多窗口节目
     * URL: /wp-json/wp/v2/programs/simplifyCreate
     * Method: POST
     */
    @PostMapping("/simplifyCreate")
    public ResponseEntity<SimplifyCreateProgramResponse> createMultiWindowProgram(@RequestBody SimplifyCreateProgramRequest request) {
        logger.info("收到创建多窗口节目请求: programName={}, regions={}", 
                   request.getProgramName(), request.getRegions() != null ? request.getRegions().size() : 0);
        
        try {
            if (request == null || request.getProgramName() == null || request.getRegions() == null || request.getRegions().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            SimplifyCreateProgramResponse response = simplifyCreateProgramService.createMultiWindowProgram(request);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("创建多窗口节目失败: programName={}", request.getProgramName(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("创建多窗口节目异常: programName={}", request.getProgramName(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
} 