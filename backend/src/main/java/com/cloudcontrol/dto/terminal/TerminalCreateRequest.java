package com.cloudcontrol.dto.terminal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 终端创建请求DTO
 */
public class TerminalCreateRequest {
    
    @JsonProperty("force")
    private Boolean force;
    
    @JsonProperty("terminalModel")
    private TerminalModel terminalModel;
    
    @JsonProperty("accountModel")
    private AccountModel accountModel;
    
    public TerminalCreateRequest() {}
    
    // getter/setter
    public Boolean getForce() { return force; }
    public void setForce(Boolean force) { this.force = force; }
    
    public TerminalModel getTerminalModel() { return terminalModel; }
    public void setTerminalModel(TerminalModel terminalModel) { this.terminalModel = terminalModel; }
    
    public AccountModel getAccountModel() { return accountModel; }
    public void setAccountModel(AccountModel accountModel) { this.accountModel = accountModel; }
    
    /**
     * 终端模型
     */
    public static class TerminalModel {
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("status")
        private String status;
        
        @JsonProperty("terminalGroup")
        private List<Integer> terminalGroup;
        
        @JsonProperty("roles")
        private List<String> roles;
        
        public TerminalModel() {}
        
        // getter/setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public List<Integer> getTerminalGroup() { return terminalGroup; }
        public void setTerminalGroup(List<Integer> terminalGroup) { this.terminalGroup = terminalGroup; }
        
        public List<String> getRoles() { return roles; }
        public void setRoles(List<String> roles) { this.roles = roles; }
    }
    
    /**
     * 账号模型
     */
    public static class AccountModel {
        @JsonProperty("email")
        private String email;
        
        @JsonProperty("username")
        private String username;
        
        @JsonProperty("password")
        private String password;
        
        @JsonProperty("roles")
        private List<String> roles;
        
        public AccountModel() {}
        
        // getter/setter
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public List<String> getRoles() { return roles; }
        public void setRoles(List<String> roles) { this.roles = roles; }
    }
} 