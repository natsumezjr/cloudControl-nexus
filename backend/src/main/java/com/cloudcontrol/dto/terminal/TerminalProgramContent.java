package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 终端节目内容DTO
 */
public class TerminalProgramContent {
    
    private List<ProgramContent> content;
    private String type;
    private Integer ressize;
    
    public TerminalProgramContent() {}
    
    public TerminalProgramContent(List<ProgramContent> content, String type, Integer ressize) {
        this.content = content;
        this.type = type;
        this.ressize = ressize;
    }
    
    // getter/setter
    public List<ProgramContent> getContent() { return content; }
    public void setContent(List<ProgramContent> content) { this.content = content; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getRessize() { return ressize; }
    public void setRessize(Integer ressize) { this.ressize = ressize; }
    
    /**
     * 节目内容详情
     */
    public static class ProgramContent {
        private String name;
        private Integer size;
        
        public ProgramContent() {}
        
        public ProgramContent(String name, Integer size) {
            this.name = name;
            this.size = size;
        }
        
        // getter/setter
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Integer getSize() { return size; }
        public void setSize(Integer size) { this.size = size; }
    }
} 