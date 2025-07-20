package com.cloudcontrol.dto.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * 素材上传响应DTO
 */
public class MediaUploadResponse {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("title_raw")
    private String titleRaw;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("file_type")
    private String fileType;
    
    @JsonProperty("mime_type")
    private String mimeType;
    
    @JsonProperty("media_type")
    private String mediaType;
    
    @JsonProperty("attachment_filesize")
    private Long attachmentFilesize;
    
    @JsonProperty("author")
    private Integer author;
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("date_gmt")
    private String dateGmt;
    
    @JsonProperty("modified")
    private String modified;
    
    @JsonProperty("modified_gmt")
    private String modifiedGmt;
    
    @JsonProperty("guid")
    private String guid;
    
    @JsonProperty("slug")
    private String slug;
    
    @JsonProperty("source_url")
    private String sourceUrl;
    
    @JsonProperty("src")
    private String src;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("post_status")
    private String postStatus;
    
    @JsonProperty("comment_status")
    private String commentStatus;
    
    @JsonProperty("ping_status")
    private String pingStatus;
    
    @JsonProperty("parent")
    private Integer parent;
    
    @JsonProperty("fullSize")
    private FullSizeInfo fullSize;
    
    @JsonProperty("media_details")
    private MediaDetailsInfo mediaDetails;
    
    @JsonProperty("title")
    private TitleInfo title;
    
    public MediaUploadResponse() {}
    
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
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getDateGmt() { return dateGmt; }
    public void setDateGmt(String dateGmt) { this.dateGmt = dateGmt; }
    
    public String getModified() { return modified; }
    public void setModified(String modified) { this.modified = modified; }
    
    public String getModifiedGmt() { return modifiedGmt; }
    public void setModifiedGmt(String modifiedGmt) { this.modifiedGmt = modifiedGmt; }
    
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getPostStatus() { return postStatus; }
    public void setPostStatus(String postStatus) { this.postStatus = postStatus; }
    
    public String getCommentStatus() { return commentStatus; }
    public void setCommentStatus(String commentStatus) { this.commentStatus = commentStatus; }
    
    public String getPingStatus() { return pingStatus; }
    public void setPingStatus(String pingStatus) { this.pingStatus = pingStatus; }
    
    public Integer getParent() { return parent; }
    public void setParent(Integer parent) { this.parent = parent; }
    
    public FullSizeInfo getFullSize() { return fullSize; }
    public void setFullSize(FullSizeInfo fullSize) { this.fullSize = fullSize; }
    
    public MediaDetailsInfo getMediaDetails() { return mediaDetails; }
    public void setMediaDetails(MediaDetailsInfo mediaDetails) { this.mediaDetails = mediaDetails; }
    
    public TitleInfo getTitle() { return title; }
    public void setTitle(TitleInfo title) { this.title = title; }
    
    /**
     * 完整尺寸信息
     */
    public static class FullSizeInfo {
        @JsonProperty("width")
        private Integer width;
        
        @JsonProperty("height")
        private Integer height;
        
        public FullSizeInfo() {}
        
        public FullSizeInfo(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }
        
        // getter/setter
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
    }
    
    /**
     * 媒体详情信息
     */
    public static class MediaDetailsInfo {
        @JsonProperty("duration")
        private Integer duration;
        
        @JsonProperty("file")
        private String file;
        
        @JsonProperty("width")
        private Integer width;
        
        @JsonProperty("height")
        private Integer height;
        
        @JsonProperty("sizes")
        private SizesInfo sizes;
        
        @JsonProperty("image_meta")
        private ImageMetaInfo imageMeta;
        
        public MediaDetailsInfo() {}
        
        // getter/setter
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
        
        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
        
        public SizesInfo getSizes() { return sizes; }
        public void setSizes(SizesInfo sizes) { this.sizes = sizes; }
        
        public ImageMetaInfo getImageMeta() { return imageMeta; }
        public void setImageMeta(ImageMetaInfo imageMeta) { this.imageMeta = imageMeta; }
    }
    
    /**
     * 尺寸信息
     */
    public static class SizesInfo {
        @JsonProperty("thumbnail")
        private ThumbnailInfo thumbnail;
        
        public SizesInfo() {}
        
        // getter/setter
        public ThumbnailInfo getThumbnail() { return thumbnail; }
        public void setThumbnail(ThumbnailInfo thumbnail) { this.thumbnail = thumbnail; }
    }
    
    /**
     * 缩略图信息
     */
    public static class ThumbnailInfo {
        @JsonProperty("file")
        private String file;
        
        @JsonProperty("mime_type")
        private String mimeType;
        
        @JsonProperty("width")
        private Integer width;
        
        @JsonProperty("height")
        private Integer height;
        
        public ThumbnailInfo() {}
        
        public ThumbnailInfo(String file, String mimeType, Integer width, Integer height) {
            this.file = file;
            this.mimeType = mimeType;
            this.width = width;
            this.height = height;
        }
        
        // getter/setter
        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }
        
        public String getMimeType() { return mimeType; }
        public void setMimeType(String mimeType) { this.mimeType = mimeType; }
        
        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }
        
        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
    }
    
    /**
     * 图片元数据信息
     */
    public static class ImageMetaInfo {
        @JsonProperty("aperture")
        private String aperture;
        
        @JsonProperty("copyright")
        private String copyright;
        
        @JsonProperty("orientation")
        private String orientation;
        
        @JsonProperty("focal_length")
        private String focalLength;
        
        @JsonProperty("iso")
        private String iso;
        
        @JsonProperty("keywords")
        private String[] keywords;
        
        @JsonProperty("caption")
        private String caption;
        
        @JsonProperty("created_timestamp")
        private String createdTimestamp;
        
        @JsonProperty("credit")
        private String credit;
        
        @JsonProperty("camera")
        private String camera;
        
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("shutter_speed")
        private String shutterSpeed;
        
        public ImageMetaInfo() {
            this.aperture = "0";
            this.copyright = "";
            this.orientation = "0";
            this.focalLength = "0";
            this.iso = "0";
            this.keywords = new String[0];
            this.caption = "";
            this.createdTimestamp = "0";
            this.credit = "";
            this.camera = "";
            this.title = "";
            this.shutterSpeed = "0";
        }
        
        // getter/setter
        public String getAperture() { return aperture; }
        public void setAperture(String aperture) { this.aperture = aperture; }
        
        public String getCopyright() { return copyright; }
        public void setCopyright(String copyright) { this.copyright = copyright; }
        
        public String getOrientation() { return orientation; }
        public void setOrientation(String orientation) { this.orientation = orientation; }
        
        public String getFocalLength() { return focalLength; }
        public void setFocalLength(String focalLength) { this.focalLength = focalLength; }
        
        public String getIso() { return iso; }
        public void setIso(String iso) { this.iso = iso; }
        
        public String[] getKeywords() { return keywords; }
        public void setKeywords(String[] keywords) { this.keywords = keywords; }
        
        public String getCaption() { return caption; }
        public void setCaption(String caption) { this.caption = caption; }
        
        public String getCreatedTimestamp() { return createdTimestamp; }
        public void setCreatedTimestamp(String createdTimestamp) { this.createdTimestamp = createdTimestamp; }
        
        public String getCredit() { return credit; }
        public void setCredit(String credit) { this.credit = credit; }
        
        public String getCamera() { return camera; }
        public void setCamera(String camera) { this.camera = camera; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getShutterSpeed() { return shutterSpeed; }
        public void setShutterSpeed(String shutterSpeed) { this.shutterSpeed = shutterSpeed; }
    }
    
    /**
     * 标题信息
     */
    public static class TitleInfo {
        @JsonProperty("rendered")
        private String rendered;
        
        public TitleInfo() {}
        
        public TitleInfo(String rendered) {
            this.rendered = rendered;
        }
        
        // getter/setter
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
    }
} 