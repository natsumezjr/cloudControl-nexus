package com.cloudcontrol.dto.program;

import java.time.LocalDateTime;

/**
 * 节目响应DTO
 */
public class ProgramResponse {
    
    private Integer id;
    private String title;
    private String status;
    private Object programInfo;
    private Object programs;
    private Integer author;
    private String date;
    private String dateGmt;
    private String modified;
    private String modifiedGmt;
    
    public ProgramResponse() {}
    
    public ProgramResponse(Integer id, String title, String status, Object programInfo, Object programs, Integer author) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.programInfo = programInfo;
        this.programs = programs;
        this.author = author;
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Object getProgramInfo() { return programInfo; }
    public void setProgramInfo(Object programInfo) { this.programInfo = programInfo; }
    
    public Object getPrograms() { return programs; }
    public void setPrograms(Object programs) { this.programs = programs; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getDateGmt() { return dateGmt; }
    public void setDateGmt(String dateGmt) { this.dateGmt = dateGmt; }
    
    public String getModified() { return modified; }
    public void setModified(String modified) { this.modified = modified; }
    
    public String getModifiedGmt() { return modifiedGmt; }
    public void setModifiedGmt(String modifiedGmt) { this.modifiedGmt = modifiedGmt; }
} 