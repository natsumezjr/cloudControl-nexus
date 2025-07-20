package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 终端更新请求DTO
 */
public class TerminalUpdateRequest {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("force")
    private Boolean force;
    
    @JsonProperty("terminal")
    private TerminalInfo terminal;
    
    @JsonProperty("roles")
    private List<String> roles;
    
    public TerminalUpdateRequest() {}
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Boolean getForce() { return force; }
    public void setForce(Boolean force) { this.force = force; }
    
    public TerminalInfo getTerminal() { return terminal; }
    public void setTerminal(TerminalInfo terminal) { this.terminal = terminal; }
    
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    
    /**
     * 终端信息
     */
    public static class TerminalInfo {
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("status")
        private String status;
        
        @JsonProperty("terminalGroup")
        private List<Integer> terminalGroup;
        
        @JsonProperty("roles")
        private List<String> roles;
        
        @JsonProperty("id")
        private Integer id;
        
        @JsonProperty("terminalGroupId")
        private Integer terminalGroupId;
        
        @JsonProperty("installDateUTC")
        private String installDateUTC;
        
        public TerminalInfo() {}
        
        // getter/setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public List<Integer> getTerminalGroup() { return terminalGroup; }
        public void setTerminalGroup(List<Integer> terminalGroup) { this.terminalGroup = terminalGroup; }
        
        public List<String> getRoles() { return roles; }
        public void setRoles(List<String> roles) { this.roles = roles; }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public Integer getTerminalGroupId() { return terminalGroupId; }
        public void setTerminalGroupId(Integer terminalGroupId) { this.terminalGroupId = terminalGroupId; }
        
        public String getInstallDateUTC() { return installDateUTC; }
        public void setInstallDateUTC(String installDateUTC) { this.installDateUTC = installDateUTC; }
    }
} 