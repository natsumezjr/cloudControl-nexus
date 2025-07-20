package com.cloudcontrol.service.tus;

import com.cloudcontrol.dto.tus.TusUploadResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Tus上传服务接口
 */
public interface TusService {
    
    /**
     * 秒传检查 - 根据MD5校验和检查文件是否已上传
     * @param checksum MD5校验和
     * @return 如果已上传返回文件路径，否则返回null
     */
    String checkFileExists(String checksum);
    
    /**
     * 创建上传会话
     * @param filename 文件名
     * @param fileType 文件类型
     * @param mimeType MIME类型
     * @param fileSize 文件大小
     * @param checksum MD5校验和
     * @return 上传响应
     */
    TusUploadResponse createUpload(String filename, String fileType, String mimeType, 
                                 Long fileSize, String checksum);
    
    /**
     * 上传文件块
     * @param uploadId 上传ID
     * @param offset 偏移量
     * @param data 文件数据
     * @return 新的偏移量
     */
    Long uploadChunk(String uploadId, Long offset, byte[] data);
    
    /**
     * 获取上传信息
     * @param uploadId 上传ID
     * @return 上传响应
     */
    TusUploadResponse getUploadInfo(String uploadId);
    
    /**
     * 完成上传并保存到媒体库
     * @param uploadId 上传ID
     * @return 媒体信息
     */
    Object completeUpload(String uploadId);
    
    /**
     * 删除上传会话
     * @param uploadId 上传ID
     */
    void deleteUpload(String uploadId);
} 