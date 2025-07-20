package com.cloudcontrol.dto.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 媒体删除响应DTO
 */
public class MediaDeleteResponse {
    
    @JsonProperty("success")
    private Boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("deletedCount")
    private Integer deletedCount;
    
    @JsonProperty("deletedMedia")
    private List<MediaInfo> deletedMedia;
    
    public MediaDeleteResponse() {}
    
    public MediaDeleteResponse(Boolean success, String message, Integer deletedCount) {
        this.success = success;
        this.message = message;
        this.deletedCount = deletedCount;
    }
    
    public MediaDeleteResponse(Boolean success, String message, Integer deletedCount, List<MediaInfo> deletedMedia) {
        this.success = success;
        this.message = message;
        this.deletedCount = deletedCount;
        this.deletedMedia = deletedMedia;
    }
    
    // getter/setter
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Integer getDeletedCount() { return deletedCount; }
    public void setDeletedCount(Integer deletedCount) { this.deletedCount = deletedCount; }
    
    public List<MediaInfo> getDeletedMedia() { return deletedMedia; }
    public void setDeletedMedia(List<MediaInfo> deletedMedia) { this.deletedMedia = deletedMedia; }
    
    /**
     * 标题信息
     */
    public static class TitleInfo {
        private String rendered;
        private String raw;
        
        public TitleInfo() {}
        
        public TitleInfo(String rendered, String raw) {
            this.rendered = rendered;
            this.raw = raw;
        }
        
        // getter/setter
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
        
        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }
    }
    
    /**
     * 全尺寸信息
     */
    public static class FullSizeInfo {
        private String file;
        private Integer width;
        private Integer height;
        private String mimeType;
        private String sourceUrl;
        
        public FullSizeInfo() {}
        
        public FullSizeInfo(String file, Integer width, Integer height, String mimeType, String sourceUrl) {
            this.file = file;
            this.width = width;
            this.height = height;
            this.mimeType = mimeType;
            this.sourceUrl = sourceUrl;
        }
        
        // getter/setter
        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
        
        public String getMimeType() { return mimeType; }
        public void setMimeType(String mimeType) { this.mimeType = mimeType; }
        
        public String getSourceUrl() { return sourceUrl; }
        public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    }
} 