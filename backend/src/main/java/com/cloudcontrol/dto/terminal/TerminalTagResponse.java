package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 终端标签响应DTO
 */
public class TerminalTagResponse {
    
    private List<TagVo> tagVos;
    private Integer totalNum;
    
    public TerminalTagResponse() {}
    
    public TerminalTagResponse(List<TagVo> tagVos, Integer totalNum) {
        this.tagVos = tagVos;
        this.totalNum = totalNum;
    }
    
    // getter/setter
    public List<TagVo> getTagVos() { return tagVos; }
    public void setTagVos(List<TagVo> tagVos) { this.tagVos = tagVos; }
    
    public Integer getTotalNum() { return totalNum; }
    public void setTotalNum(Integer totalNum) { this.totalNum = totalNum; }
    
    /**
     * 标签信息内部类
     */
    public static class TagVo {
        private Integer tagId;
        private String tagName;
        
        public TagVo() {}
        
        public TagVo(Integer tagId, String tagName) {
            this.tagId = tagId;
            this.tagName = tagName;
        }
        
        // getter/setter
        public Integer getTagId() { return tagId; }
        public void setTagId(Integer tagId) { this.tagId = tagId; }
        
        public String getTagName() { return tagName; }
        public void setTagName(String tagName) { this.tagName = tagName; }
    }
} 