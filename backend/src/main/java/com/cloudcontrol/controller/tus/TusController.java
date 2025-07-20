package com.cloudcontrol.controller.tus;

import com.cloudcontrol.dto.tus.TusUploadResponse;
import com.cloudcontrol.service.tus.TusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * Tus上传控制器
 */
@RestController
public class TusController {
    
    private static final Logger logger = LoggerFactory.getLogger(TusController.class);
    
    private final TusService tusService;
    
    public TusController(TusService tusService) {
        this.tusService = tusService;
    }
    
    /**
     * 1. 秒传接口 - 判断是否上传过
     * URL: /wp-content/uploads/TusFileSearchByChecksum
     * Method: GET
     */
    @GetMapping("/wp-content/uploads/TusFileSearchByChecksum")
    public ResponseEntity<String> checkFileExists(@RequestHeader("Upload-Checksum") String checksumHeader) {
        try {
            logger.info("收到秒传检查请求: checksumHeader={}", checksumHeader);
            
            // 解析MD5校验和
            String checksum = extractChecksum(checksumHeader);
            if (checksum == null) {
                return ResponseEntity.badRequest().body("Invalid checksum format");
            }
            
            // 检查文件是否存在
            String filePath = tusService.checkFileExists(checksum);
            
            if (filePath != null) {
                // 文件已存在，返回文件路径
                logger.info("文件已存在: checksum={}, filePath={}", checksum, filePath);
                return ResponseEntity.ok(filePath);
            } else {
                // 文件不存在
                logger.info("文件不存在: checksum={}", checksum);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            
        } catch (Exception e) {
            logger.error("秒传检查失败: checksumHeader={}", checksumHeader, e);
            return ResponseEntity.badRequest().body("Check failed: " + e.getMessage());
        }
    }
    
    /**
     * 2. 服务器登记文件，返回文件信息
     * URL: /wp-content/uploads/Tus
     * Method: POST
     */
    @PostMapping("/wp-content/uploads/Tus")
    public ResponseEntity<Void> createUpload(@RequestHeader Map<String, String> headers) {
        try {
            logger.info("收到创建上传请求: headers={}", headers);
            
            // 验证Tus协议版本
            String tusResumable = headers.get("tus-resumable");
            if (!"1.0.0".equals(tusResumable)) {
                return ResponseEntity.badRequest().build();
            }
            
            // 获取文件大小
            String uploadLength = headers.get("upload-length");
            if (uploadLength == null) {
                return ResponseEntity.badRequest().build();
            }
            Long fileSize = Long.parseLong(uploadLength);
            
            // 解析元数据
            String uploadMetadata = headers.get("upload-metadata");
            Map<String, String> metadata = parseMetadata(uploadMetadata);
            
            String filename = metadata.get("filename");
            String filetype = metadata.get("filetype");
            
            if (filename == null || filetype == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // 解码文件名和文件类型
            filename = new String(Base64.getDecoder().decode(filename));
            filetype = new String(Base64.getDecoder().decode(filetype));
            
            // 生成MD5校验和（这里简化处理，实际应该从客户端获取）
            String checksum = generateChecksum(filename + fileSize);
            
            // 创建上传会话
            TusUploadResponse response = tusService.createUpload(filename, filetype, 
                getMimeType(filetype), fileSize, checksum);
            
            // 设置响应头
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", response.getLocation());
            responseHeaders.set("Tus-Resumable", "1.0.0");
            
            logger.info("上传会话创建成功: uploadId={}", response.getUploadId());
            return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
            
        } catch (Exception e) {
            logger.error("创建上传会话失败", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 3. 上传文件块
     * URL: /wp-content/uploads/Tus/{uploadId}
     * Method: PATCH
     */
    @PatchMapping("/wp-content/uploads/Tus/{uploadId}")
    public ResponseEntity<Void> uploadChunk(@PathVariable String uploadId,
                                          @RequestHeader Map<String, String> headers,
                                          @RequestBody byte[] data) {
        try {
            logger.info("收到文件块上传请求: uploadId={}, dataSize={}", uploadId, data.length);
            
            // 验证Tus协议版本
            String tusResumable = headers.get("tus-resumable");
            if (!"1.0.0".equals(tusResumable)) {
                return ResponseEntity.badRequest().build();
            }
            
            // 获取偏移量
            String uploadOffset = headers.get("upload-offset");
            if (uploadOffset == null) {
                return ResponseEntity.badRequest().build();
            }
            Long offset = Long.parseLong(uploadOffset);
            
            // 上传文件块
            Long newOffset = tusService.uploadChunk(uploadId, offset, data);
            
            // 设置响应头
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Upload-Offset", String.valueOf(newOffset));
            responseHeaders.set("Tus-Resumable", "1.0.0");
            
            logger.info("文件块上传成功: uploadId={}, newOffset={}", uploadId, newOffset);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(responseHeaders).build();
            
        } catch (Exception e) {
            logger.error("文件块上传失败: uploadId={}", uploadId, e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 4. 获取文件上传信息
     * URL: /wp-content/uploads/Tus/{uploadId}
     * Method: HEAD
     */
    @RequestMapping(value = "/wp-content/uploads/Tus/{uploadId}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getUploadInfo(@PathVariable String uploadId,
                                            @RequestHeader Map<String, String> headers) {
        try {
            logger.info("收到获取上传信息请求: uploadId={}", uploadId);
            
            // 验证Tus协议版本
            String tusResumable = headers.get("tus-resumable");
            if (!"1.0.0".equals(tusResumable)) {
                return ResponseEntity.badRequest().build();
            }
            
            // 获取上传信息
            TusUploadResponse uploadInfo = tusService.getUploadInfo(uploadId);
            
            // 设置响应头
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Upload-Length", String.valueOf(uploadInfo.getFileSize()));
            responseHeaders.set("Upload-Offset", String.valueOf(uploadInfo.getUploadOffset()));
            responseHeaders.set("Tus-Resumable", "1.0.0");
            
            logger.info("获取上传信息成功: uploadId={}, offset={}", uploadId, uploadInfo.getUploadOffset());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(responseHeaders).build();
            
        } catch (Exception e) {
            logger.error("获取上传信息失败: uploadId={}", uploadId, e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 5. 在云平台中保存上传记录
     * URL: /wp-json/wp/v2/tus-media
     * Method: POST
     */
    @PostMapping("/wp-json/wp/v2/tus-media")
    public ResponseEntity<Object> saveToMediaLibrary(@RequestParam("uploadURI") String uploadURI) {
        try {
            logger.info("收到保存到媒体库请求: uploadURI={}", uploadURI);
            
            // 从uploadURI中提取uploadId
            String uploadId = extractUploadId(uploadURI);
            if (uploadId == null) {
                return ResponseEntity.badRequest().body("Invalid uploadURI");
            }
            
            // 完成上传并保存到媒体库
            Object mediaInfo = tusService.completeUpload(uploadId);
            
            logger.info("保存到媒体库成功: uploadId={}", uploadId);
            return ResponseEntity.status(HttpStatus.CREATED).body(mediaInfo);
            
        } catch (Exception e) {
            logger.error("保存到媒体库失败: uploadURI={}", uploadURI, e);
            return ResponseEntity.badRequest().body("Save failed: " + e.getMessage());
        }
    }
    
    /**
     * 测试Tus API端点
     */
    @GetMapping("/tus/test")
    public ResponseEntity<String> testTus() {
        return ResponseEntity.ok("Tus API is working!");
    }
    
    /**
     * 解析校验和
     */
    private String extractChecksum(String checksumHeader) {
        if (checksumHeader == null || !checksumHeader.startsWith("md5 ")) {
            return null;
        }
        return checksumHeader.substring(4);
    }
    
    /**
     * 解析元数据
     */
    private Map<String, String> parseMetadata(String uploadMetadata) {
        Map<String, String> metadata = new java.util.HashMap<>();
        if (uploadMetadata != null) {
            String[] pairs = uploadMetadata.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.trim().split(" ");
                if (keyValue.length == 2) {
                    metadata.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return metadata;
    }
    
    /**
     * 获取MIME类型
     */
    private String getMimeType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "mp4":
                return "video/mp4";
            case "mp3":
                return "audio/mpeg";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * 生成校验和（简化实现）
     */
    private String generateChecksum(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "00000000000000000000000000000000";
        }
    }
    
    /**
     * 从uploadURI中提取uploadId
     */
    private String extractUploadId(String uploadURI) {
        if (uploadURI == null || uploadURI.isEmpty()) {
            return null;
        }
        
        // 假设uploadURI格式为: 16bffa64-17c3-48ad-9293-3cf981147641
        // 或者: /wp-content/uploads/Tus/16bffa64-17c3-48ad-9293-3cf981147641
        String[] parts = uploadURI.split("/");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1];
            // 验证是否为UUID格式
            if (lastPart.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")) {
                return lastPart;
            }
        }
        return null;
    }
} 