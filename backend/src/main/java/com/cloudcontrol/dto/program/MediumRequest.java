package com.cloudcontrol.dto.program;

/**
 * 素材请求DTO
 */
public class MediumRequest {
    
    private Integer mediaId;
    private Integer durationMillis;
    private Integer constrainedProportion;
    
    public MediumRequest() {}
    
    public MediumRequest(Integer mediaId, Integer durationMillis, Integer constrainedProportion) {
        this.mediaId = mediaId;
        this.durationMillis = durationMillis;
        this.constrainedProportion = constrainedProportion;
    }
    
    // getter/setter
    public Integer getMediaId() { return mediaId; }
    public void setMediaId(Integer mediaId) { this.mediaId = mediaId; }
    
    public Integer getDurationMillis() { return durationMillis; }
    public void setDurationMillis(Integer durationMillis) { this.durationMillis = durationMillis; }
    
    public Integer getConstrainedProportion() { return constrainedProportion; }
    public void setConstrainedProportion(Integer constrainedProportion) { this.constrainedProportion = constrainedProportion; }
} 