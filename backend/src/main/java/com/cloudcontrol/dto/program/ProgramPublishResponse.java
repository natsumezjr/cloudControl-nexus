package com.cloudcontrol.dto.program;

/**
 * 节目发布响应DTO
 */
public class ProgramPublishResponse {
    
    private Integer id;
    private Integer author;
    private String date;
    private String dateGmt;
    private String status;
    private String modified;
    private String modifiedGmt;
    
    public ProgramPublishResponse() {}
    
    public ProgramPublishResponse(Integer id, Integer author, String date, String dateGmt, 
                                 String status, String modified, String modifiedGmt) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.dateGmt = dateGmt;
        this.status = status;
        this.modified = modified;
        this.modifiedGmt = modifiedGmt;
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getDateGmt() { return dateGmt; }
    public void setDateGmt(String dateGmt) { this.dateGmt = dateGmt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getModified() { return modified; }
    public void setModified(String modified) { this.modified = modified; }
    
    public String getModifiedGmt() { return modifiedGmt; }
    public void setModifiedGmt(String modifiedGmt) { this.modifiedGmt = modifiedGmt; }
} 