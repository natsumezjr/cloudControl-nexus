package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 批量删除节目请求DTO
 */
public class ProgramBatchDeleteRequest {
    
    private List<Integer> programIds;
    
    public ProgramBatchDeleteRequest() {}
    
    public ProgramBatchDeleteRequest(List<Integer> programIds) {
        this.programIds = programIds;
    }
    
    // getter/setter
    public List<Integer> getProgramIds() { return programIds; }
    public void setProgramIds(List<Integer> programIds) { this.programIds = programIds; }
} 