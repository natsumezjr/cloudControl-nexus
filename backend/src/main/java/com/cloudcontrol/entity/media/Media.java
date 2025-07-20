package com.cloudcontrol.entity.media;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 素材文件实体类
 */
@Entity
@Table(name = "media")
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "title_raw")
    private String titleRaw;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "file_type")
    private String fileType;
    
    @Column(name = "mime_type")
    private String mimeType;
    
    @Column(name = "media_type")
    private String mediaType;
    
    @Column(name = "attachment_filesize")
    private Long attachmentFilesize;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "thumbnail_path")
    private String thumbnailPath;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "duration")
    private Integer duration;
    
    @Column(name = "author")
    private Integer author;
    
    @Column(name = "guid")
    private String guid;
    
    @Column(name = "slug")
    private String slug;
    
    @Column(name = "source_url")
    private String sourceUrl;
    
    @Column(name = "src")
    private String src;
    
    @Column(name = "post_status")
    private String postStatus;
    
    @Column(name = "comment_status")
    private String commentStatus;
    
    @Column(name = "ping_status")
    private String pingStatus;
    
    @Column(name = "parent")
    private Integer parent;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Media() {}
    
    public Media(String titleRaw, String fileType, String mimeType, String mediaType, 
                Long attachmentFilesize, String filePath, Integer author) {
        this.titleRaw = titleRaw;
        this.fileType = fileType;
        this.mimeType = mimeType;
        this.mediaType = mediaType;
        this.attachmentFilesize = attachmentFilesize;
        this.filePath = filePath;
        this.author = author;
        this.postStatus = "inherit";
        this.commentStatus = "";
        this.pingStatus = "";
        this.parent = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitleRaw() { return titleRaw; }
    public void setTitleRaw(String titleRaw) { this.titleRaw = titleRaw; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    
    public Long getAttachmentFilesize() { return attachmentFilesize; }
    public void setAttachmentFilesize(Long attachmentFilesize) { this.attachmentFilesize = attachmentFilesize; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getThumbnailPath() { return thumbnailPath; }
    public void setThumbnailPath(String thumbnailPath) { this.thumbnailPath = thumbnailPath; }
    
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }
    
    public String getPostStatus() { return postStatus; }
    public void setPostStatus(String postStatus) { this.postStatus = postStatus; }
    
    public String getCommentStatus() { return commentStatus; }
    public void setCommentStatus(String commentStatus) { this.commentStatus = commentStatus; }
    
    public String getPingStatus() { return pingStatus; }
    public void setPingStatus(String pingStatus) { this.pingStatus = pingStatus; }
    
    public Integer getParent() { return parent; }
    public void setParent(Integer parent) { this.parent = parent; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 