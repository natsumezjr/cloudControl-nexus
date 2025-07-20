package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 删除播放盒节目请求DTO
 */
public class ProgramUnpublishRequest {
    
    private Integer programId;
    private String vsnMd5;
    private List<Integer> terminalIds;
    
    public ProgramUnpublishRequest() {}
    
    public ProgramUnpublishRequest(Integer programId, String vsnMd5, List<Integer> terminalIds) {
        this.programId = programId;
        this.vsnMd5 = vsnMd5;
        this.terminalIds = terminalIds;
    }
    
    // getter/setter
    public Integer getProgramId() { return programId; }
    public void setProgramId(Integer programId) { this.programId = programId; }
    
    public String getVsnMd5() { return vsnMd5; }
    public void setVsnMd5(String vsnMd5) { this.vsnMd5 = vsnMd5; }
    
    public List<Integer> getTerminalIds() { return terminalIds; }
    public void setTerminalIds(List<Integer> terminalIds) { this.terminalIds = terminalIds; }
} 