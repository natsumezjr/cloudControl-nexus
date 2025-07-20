package com.cloudcontrol.dto.tus;

/**
 * Tus上传响应DTO
 */
public class TusUploadResponse {
    
    private String uploadId;
    private String filename;
    private String fileType;
    private String mimeType;
    private Long fileSize;
    private Long uploadOffset;
    private String checksum;
    private String filePath;
    private String status;
    private String location;
    
    public TusUploadResponse() {}
    
    public TusUploadResponse(String uploadId, String location) {
        this.uploadId = uploadId;
        this.location = location;
    }
    
    // getter/setter
    public String getUploadId() { return uploadId; }
    public void setUploadId(String uploadId) { this.uploadId = uploadId; }
    
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public Long getUploadOffset() { return uploadOffset; }
    public void setUploadOffset(Long uploadOffset) { this.uploadOffset = uploadOffset; }
    
    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
} 