package com.cloudcontrol.dto.program;

/**
 * 简化创建节目响应DTO
 */
public class SimplifyCreateProgramResponse {
    
    private Integer programId;
    
    public SimplifyCreateProgramResponse() {}
    
    public SimplifyCreateProgramResponse(Integer programId) {
        this.programId = programId;
    }
    
    // getter/setter
    public Integer getProgramId() { return programId; }
    public void setProgramId(Integer programId) { this.programId = programId; }
} 