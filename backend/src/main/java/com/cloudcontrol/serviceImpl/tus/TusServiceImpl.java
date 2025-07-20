package com.cloudcontrol.serviceImpl.tus;

import com.cloudcontrol.dto.tus.TusUploadResponse;
import com.cloudcontrol.entity.tus.TusUpload;
import com.cloudcontrol.repository.tus.TusUploadRepository;
import com.cloudcontrol.service.tus.TusService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Tus上传服务实现类
 */
@Service
public class TusServiceImpl implements TusService {
    
    private static final Logger logger = LoggerFactory.getLogger(TusServiceImpl.class);
    
    private final TusUploadRepository tusUploadRepository;
    
    @Value("${app.upload.path:/uploads}")
    private String uploadPath;
    
    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;
    
    @Autowired
    public TusServiceImpl(TusUploadRepository tusUploadRepository) {
        this.tusUploadRepository = tusUploadRepository;
    }
    
    @Override
    public String checkFileExists(String checksum) {
        try {
            logger.info("检查文件是否存在: checksum={}", checksum);
            
            // 查找已完成的文件
            var tusUpload = tusUploadRepository.findByChecksumAndCompleted(checksum);
            if (tusUpload.isPresent()) {
                String filePath = tusUpload.get().getFilePath();
                logger.info("文件已存在: checksum={}, filePath={}", checksum, filePath);
                return filePath;
            }
            
            logger.info("文件不存在: checksum={}", checksum);
            return null;
            
        } catch (Exception e) {
            logger.error("检查文件存在失败: checksum={}", checksum, e);
            throw new RuntimeException("Failed to check file existence: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public TusUploadResponse createUpload(String filename, String fileType, String mimeType, 
                                        Long fileSize, String checksum) {
        try {
            logger.info("创建上传会话: filename={}, fileSize={}, checksum={}", filename, fileSize, checksum);
            
            // 生成上传ID
            String uploadId = UUID.randomUUID().toString();
            
            // 创建TusUpload记录
            TusUpload tusUpload = new TusUpload(uploadId, filename, fileType, mimeType, fileSize, checksum);
            tusUpload.setStatus("UPLOADING");
            tusUploadRepository.save(tusUpload);
            
            // 创建响应
            String location = baseUrl + "/wp-content/uploads/Tus/" + uploadId;
            TusUploadResponse response = new TusUploadResponse(uploadId, location);
            response.setFilename(filename);
            response.setFileType(fileType);
            response.setMimeType(mimeType);
            response.setFileSize(fileSize);
            response.setUploadOffset(0L);
            response.setChecksum(checksum);
            response.setStatus("UPLOADING");
            
            logger.info("上传会话创建成功: uploadId={}, location={}", uploadId, location);
            return response;
            
        } catch (Exception e) {
            logger.error("创建上传会话失败: filename={}", filename, e);
            throw new RuntimeException("Failed to create upload: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public Long uploadChunk(String uploadId, Long offset, byte[] data) {
        try {
            logger.info("上传文件块: uploadId={}, offset={}, dataSize={}", uploadId, offset, data.length);
            
            // 查找上传记录
            var tusUpload = tusUploadRepository.findByUploadId(uploadId);
            if (tusUpload.isEmpty()) {
                throw new RuntimeException("Upload not found: " + uploadId);
            }
            
            TusUpload upload = tusUpload.get();
            
            // 验证偏移量
            if (!offset.equals(upload.getUploadOffset())) {
                throw new RuntimeException("Invalid offset: expected " + upload.getUploadOffset() + ", got " + offset);
            }
            
            // 创建临时文件目录
            String tempDir = uploadPath + "/Tus/temp/" + uploadId;
            Path tempPath = Paths.get(tempDir);
            Files.createDirectories(tempPath);
            
            // 写入文件块
            String tempFile = tempDir + "/" + uploadId + ".tmp";
            try (FileOutputStream fos = new FileOutputStream(tempFile, true)) {
                fos.write(data);
            }
            
            // 更新偏移量
            Long newOffset = offset + data.length;
            upload.setUploadOffset(newOffset);
            upload.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否上传完成
            if (newOffset >= upload.getFileSize()) {
                upload.setStatus("COMPLETED");
                // 移动到最终位置
                moveToFinalLocation(upload, tempFile);
            }
            
            tusUploadRepository.save(upload);
            
            logger.info("文件块上传成功: uploadId={}, newOffset={}", uploadId, newOffset);
            return newOffset;
            
        } catch (Exception e) {
            logger.error("上传文件块失败: uploadId={}, offset={}", uploadId, offset, e);
            throw new RuntimeException("Failed to upload chunk: " + e.getMessage());
        }
    }
    
    @Override
    public TusUploadResponse getUploadInfo(String uploadId) {
        try {
            logger.info("获取上传信息: uploadId={}", uploadId);
            
            var tusUpload = tusUploadRepository.findByUploadId(uploadId);
            if (tusUpload.isEmpty()) {
                throw new RuntimeException("Upload not found: " + uploadId);
            }
            
            TusUpload upload = tusUpload.get();
            
            TusUploadResponse response = new TusUploadResponse();
            response.setUploadId(upload.getUploadId());
            response.setFilename(upload.getFilename());
            response.setFileType(upload.getFileType());
            response.setMimeType(upload.getMimeType());
            response.setFileSize(upload.getFileSize());
            response.setUploadOffset(upload.getUploadOffset());
            response.setChecksum(upload.getChecksum());
            response.setFilePath(upload.getFilePath());
            response.setStatus(upload.getStatus());
            response.setLocation(baseUrl + "/wp-content/uploads/Tus/" + uploadId);
            
            return response;
            
        } catch (Exception e) {
            logger.error("获取上传信息失败: uploadId={}", uploadId, e);
            throw new RuntimeException("Failed to get upload info: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public Object completeUpload(String uploadId) {
        try {
            logger.info("完成上传: uploadId={}", uploadId);
            
            var tusUpload = tusUploadRepository.findByUploadId(uploadId);
            if (tusUpload.isEmpty()) {
                throw new RuntimeException("Upload not found: " + uploadId);
            }
            
            TusUpload upload = tusUpload.get();
            
            // 验证上传是否完成
            if (!"COMPLETED".equals(upload.getStatus())) {
                throw new RuntimeException("Upload not completed: " + uploadId);
            }
            
            // 这里可以调用MediaService保存到媒体库
            // 暂时返回基本信息
            return upload;
            
        } catch (Exception e) {
            logger.error("完成上传失败: uploadId={}", uploadId, e);
            throw new RuntimeException("Failed to complete upload: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void deleteUpload(String uploadId) {
        try {
            logger.info("删除上传会话: uploadId={}", uploadId);
            
            // 删除数据库记录
            tusUploadRepository.deleteByUploadId(uploadId);
            
            // 删除临时文件
            String tempDir = uploadPath + "/Tus/temp/" + uploadId;
            Path tempPath = Paths.get(tempDir);
            if (Files.exists(tempPath)) {
                Files.walk(tempPath)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            logger.warn("删除临时文件失败: {}", path, e);
                        }
                    });
            }
            
            logger.info("上传会话删除成功: uploadId={}", uploadId);
            
        } catch (Exception e) {
            logger.error("删除上传会话失败: uploadId={}", uploadId, e);
            throw new RuntimeException("Failed to delete upload: " + e.getMessage());
        }
    }
    
    /**
     * 移动到最终位置
     */
    private void moveToFinalLocation(TusUpload upload, String tempFile) throws IOException {
        // 生成最终文件路径
        String year = String.valueOf(LocalDateTime.now().getYear());
        String month = String.format("%02d", LocalDateTime.now().getMonthValue());
        String finalDir = uploadPath + "/Tus/uploads/" + upload.getUploadId();
        String finalFile = finalDir + "/F_" + upload.getChecksum() + "_" + upload.getFileSize() + "." + upload.getFileType();
        
        // 创建目录
        Path finalPath = Paths.get(finalDir);
        Files.createDirectories(finalPath);
        
        // 移动文件
        Files.move(Paths.get(tempFile), Paths.get(finalFile));
        
        // 更新文件路径
        upload.setFilePath("Tus/uploads/" + upload.getUploadId() + "/F_" + upload.getChecksum() + "_" + upload.getFileSize() + "." + upload.getFileType());
        
        logger.info("文件移动到最终位置: uploadId={}, finalFile={}", upload.getUploadId(), finalFile);
    }
} 