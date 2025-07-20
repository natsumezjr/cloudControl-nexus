package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 获取终端列表请求DTO
 */
public class TerminalListRequest {
    
    private List<Integer> terminalIds;
    private Integer page;
    private Integer perPage;
    private Boolean downloadStatus;
    private Integer termParents;
    private Boolean withCoordinate;
    private String tagIds;
    private Integer authorId;
    private Integer regionTaskId;
    private Integer count;
    private Integer terminalGroup;
    private String search;
    
    public TerminalListRequest() {}
    
    public TerminalListRequest(List<Integer> terminalIds) {
        this.terminalIds = terminalIds;
    }
    
    // getter/setter
    public List<Integer> getTerminalIds() { return terminalIds; }
    public void setTerminalIds(List<Integer> terminalIds) { this.terminalIds = terminalIds; }
    
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    
    public Integer getPerPage() { return perPage; }
    public void setPerPage(Integer perPage) { this.perPage = perPage; }
    
    public Boolean getDownloadStatus() { return downloadStatus; }
    public void setDownloadStatus(Boolean downloadStatus) { this.downloadStatus = downloadStatus; }
    
    public Integer getTermParents() { return termParents; }
    public void setTermParents(Integer termParents) { this.termParents = termParents; }
    
    public Boolean getWithCoordinate() { return withCoordinate; }
    public void setWithCoordinate(Boolean withCoordinate) { this.withCoordinate = withCoordinate; }
    
    public String getTagIds() { return tagIds; }
    public void setTagIds(String tagIds) { this.tagIds = tagIds; }
    
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    
    public Integer getRegionTaskId() { return regionTaskId; }
    public void setRegionTaskId(Integer regionTaskId) { this.regionTaskId = regionTaskId; }
    
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    
    public Integer getTerminalGroup() { return terminalGroup; }
    public void setTerminalGroup(Integer terminalGroup) { this.terminalGroup = terminalGroup; }
    
    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }
} 