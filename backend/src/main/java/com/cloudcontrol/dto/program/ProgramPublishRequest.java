package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 节目发布请求DTO
 */
public class ProgramPublishRequest {
    
    private String what;
    private To to;
    
    public ProgramPublishRequest() {}
    
    public ProgramPublishRequest(String what, To to) {
        this.what = what;
        this.to = to;
    }
    
    // getter/setter
    public String getWhat() { return what; }
    public void setWhat(String what) { this.what = what; }
    
    public To getTo() { return to; }
    public void setTo(To to) { this.to = to; }
    
    /**
     * 发布目标对象
     */
    public static class To {
        private List<TerminalGroup> terminalsGroups;
        
        public To() {}
        
        public To(List<TerminalGroup> terminalsGroups) {
            this.terminalsGroups = terminalsGroups;
        }
        
        public List<TerminalGroup> getTerminalsGroups() { return terminalsGroups; }
        public void setTerminalsGroups(List<TerminalGroup> terminalsGroups) { this.terminalsGroups = terminalsGroups; }
    }
    
    /**
     * 终端组对象
     */
    public static class TerminalGroup {
        private Boolean all;
        private Integer id;
        private List<Integer> terminals;
        
        public TerminalGroup() {}
        
        public TerminalGroup(Boolean all, Integer id, List<Integer> terminals) {
            this.all = all;
            this.id = id;
            this.terminals = terminals;
        }
        
        public Boolean getAll() { return all; }
        public void setAll(Boolean all) { this.all = all; }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public List<Integer> getTerminals() { return terminals; }
        public void setTerminals(List<Integer> terminals) { this.terminals = terminals; }
    }
} 