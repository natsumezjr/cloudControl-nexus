package com.cloudcontrol.dto.terminal;

/**
 * 节目终端查询请求DTO
 */
public class ProgramTerminalRequest {
    
    private Integer program;
    private Integer page;
    private Integer perPage;
    
    public ProgramTerminalRequest() {}
    
    public ProgramTerminalRequest(Integer program, Integer page, Integer perPage) {
        this.program = program;
        this.page = page;
        this.perPage = perPage;
    }
    
    // getter/setter
    public Integer getProgram() { return program; }
    public void setProgram(Integer program) { this.program = program; }
    
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    
    public Integer getPerPage() { return perPage; }
    public void setPerPage(Integer perPage) { this.perPage = perPage; }
} 