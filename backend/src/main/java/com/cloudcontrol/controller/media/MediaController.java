package com.cloudcontrol.controller.media;

import com.cloudcontrol.dto.media.MediaDeleteResponse;
import com.cloudcontrol.dto.media.MediaUploadResponse;
import com.cloudcontrol.service.media.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 素材控制器
 */
@RestController
@RequestMapping("wp-json/wp/v2")
public class MediaController {
    
    private final MediaService mediaService;
    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);
    
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    
    /**
     * 上传素材文件
     * URL: /wp-json/wp/v2/media
     * Method: POST
     * Content-Type: multipart/form-data
     */
    @PostMapping("/media")
    public ResponseEntity<MediaUploadResponse> uploadMedia(@RequestParam("file") MultipartFile file) {
        logger.info("收到素材上传请求: filename={}, size={}", file.getOriginalFilename(), file.getSize());
        
        try {
            // 验证文件
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // 上传素材
            MediaUploadResponse response = mediaService.uploadMedia(file);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("素材上传失败: filename={}", file.getOriginalFilename(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 测试素材上传API端点
     * URL: /wp-json/wp/v2/media/test
     * Method: GET
     */
    @GetMapping("/media/test")
    public ResponseEntity<String> testMediaUpload() {
        return ResponseEntity.ok("Media Upload API is working!");
    }
    
    /**
     * 查询素材接口
     * URL: /wp-content/upload/2021/6/F_DDE1D44923F524FFBF02326DFEC5F87F_19024183.mp4
     * Method: GET
     * 授权角色：管理员账号、操作员账号
     */
    @GetMapping("/wp-content/upload/**")
    public ResponseEntity<MediaUploadResponse> getMediaByPath(HttpServletRequest request) {
        String filePath = null;
        try {
            // 从请求路径中提取文件路径
            String requestURI = request.getRequestURI();
            filePath = requestURI.replace("/wp-content/", "");
            
            logger.info("收到查询素材请求: filePath={}", filePath);
            
            // 验证文件路径
            if (filePath == null || filePath.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // 查询素材
            MediaUploadResponse response = mediaService.getMediaByPath(filePath);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("查询素材失败: filePath={}", filePath, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 测试素材查询API端点
     * URL: /wp-json/wp/v2/media/test-query
     * Method: GET
     */
    @GetMapping("/media/test-query")
    public ResponseEntity<String> testMediaQuery() {
        return ResponseEntity.ok("Media Query API is working!");
    }
    
    /**
     * 获取素材列表接口
     * URL: /wp-json/wp/v2/media?page=1&per_page=12&flag=filter
     * Method: GET
     * 授权角色：'administrator', 'editor', 'operator', 'contributor', 'auditor', 'subscriber', 'supervisor', 'invitee', 'monitor', 'manager'
     */
    @GetMapping("/media")
    public ResponseEntity<List<MediaUploadResponse>> getMediaList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "12") int perPage,
            @RequestParam(value = "flag", required = true) String flag) {
        
        logger.info("收到获取素材列表请求: page={}, per_page={}, flag={}", page, perPage, flag);
        
        try {
            // 验证参数
            if (page < 1) {
                page = 1;
            }
            if (perPage < 1) {
                perPage = 12;
            }
            if (perPage > 100) {
                perPage = 100; // 限制最大每页数量
            }
            
            // 获取素材列表
            List<MediaUploadResponse> mediaList = mediaService.getMediaList(page, perPage, flag);
            return ResponseEntity.ok(mediaList);
            
        } catch (Exception e) {
            logger.error("获取素材列表失败: page={}, per_page={}, flag={}", page, perPage, flag, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 测试素材列表API端点
     * URL: /wp-json/wp/v2/media/test-list
     * Method: GET
     */
    @GetMapping("/media/test-list")
    public ResponseEntity<String> testMediaList() {
        return ResponseEntity.ok("Media List API is working!");
    }
    
    /**
     * 根据素材ID删除素材
     * URL: /wp-json/wp/v2/media/{mediaId}?force=true
     * Method: DELETE
     * 授权角色：'administrator', 'manager', 'editor', 'assembler'
     */
    @DeleteMapping("/media/{mediaId}")
    public ResponseEntity<MediaDeleteResponse> deleteMediaById(
            @PathVariable Integer mediaId,
            @RequestParam(value = "force", defaultValue = "false") boolean force) {
        
        logger.info("收到删除素材请求: mediaId={}, force={}", mediaId, force);
        
        try {
            // 验证参数
            if (mediaId == null || mediaId <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // 删除素材
            MediaDeleteResponse response = mediaService.deleteMediaById(mediaId, force);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("删除素材失败: mediaId={}", mediaId, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * 测试素材删除API端点
     * URL: /wp-json/wp/v2/media/test-delete
     * Method: GET
     */
    @GetMapping("/media/test-delete")
    public ResponseEntity<String> testMediaDelete() {
        return ResponseEntity.ok("Media Delete API is working!");
    }
} 