package com.cloudcontrol.entity.tus;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tus上传文件实体类
 */
@Entity
@Table(name = "tus_upload")
public class TusUpload {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "upload_id", unique = true, nullable = false)
    private String uploadId;
    
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "file_type")
    private String fileType;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Column(name = "upload_offset")
    private Long uploadOffset;
    
    @Column(name = "checksum")
    private String checksum;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "status")
    private String status; // PENDING, UPLOADING, COMPLETED, FAILED
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public TusUpload() {}
    
    public TusUpload(String uploadId, String filename, String fileType, String mimeType, 
                    Long fileSize, String checksum) {
        this.uploadId = uploadId;
        this.filename = filename;
        this.fileType = fileType;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.checksum = checksum;
        this.uploadOffset = 0L;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 