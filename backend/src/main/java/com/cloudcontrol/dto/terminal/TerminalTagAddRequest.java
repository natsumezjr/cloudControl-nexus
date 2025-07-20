package com.cloudcontrol.dto.terminal;

import java.util.List;
import java.util.Set;

/**
 * 终端标签添加请求DTO
 */
public class TerminalTagAddRequest {
    
    private List<TerminalTagInfo> terminalTags;
    
    public TerminalTagAddRequest() {}
    
    public TerminalTagAddRequest(List<TerminalTagInfo> terminalTags) {
        this.terminalTags = terminalTags;
    }
    
    // getter/setter
    public List<TerminalTagInfo> getTerminalTags() { return terminalTags; }
    public void setTerminalTags(List<TerminalTagInfo> terminalTags) { this.terminalTags = terminalTags; }
    
    /**
     * 终端标签信息内部类
     */
    public static class TerminalTagInfo {
        private Integer terminalId;
        private Set<String> tagNames;
        
        public TerminalTagInfo() {}
        
        public TerminalTagInfo(Integer terminalId, Set<String> tagNames) {
            this.terminalId = terminalId;
            this.tagNames = tagNames;
        }
        
        // getter/setter
        public Integer getTerminalId() { return terminalId; }
        public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
        
        public Set<String> getTagNames() { return tagNames; }
        public void setTagNames(Set<String> tagNames) { this.tagNames = tagNames; }
    }
} 