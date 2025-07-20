package com.cloudcontrol.dto.media;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 媒体信息DTO
 */
public class MediaInfo {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("size")
    private Long size;
    
    @JsonProperty("type")
    private String type;
    
    public MediaInfo() {}
    
    public MediaInfo(Integer id, String name, String url, Long size, String type) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
        this.type = type;
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
} 