package com.cloudcontrol.dto.program;

import java.util.List;

/**
 * 节目列表响应DTO
 */
public class ProgramListResponse {
    
    private String date;
    private String vsnName;
    private String modifiedGmt;
    private Integer author;
    private Boolean publishedBySchedule;
    private String programSourceThumbnail;
    private Title title;
    private String type;
    private Embedded embedded;
    private Object programInfo;
    private String modified;
    private String titleRaw;
    private Integer id;
    private List<TerminalGroup> terminalsGroups;
    private String dateGmt;
    private String slug;
    private String status;
    
    public ProgramListResponse() {}
    
    public ProgramListResponse(String date, String vsnName, String modifiedGmt, Integer author, 
                              String programSourceThumbnail, Title title, String type, 
                              Embedded embedded, Object programInfo, String modified, 
                              String titleRaw, Integer id, List<TerminalGroup> terminalsGroups, 
                              String dateGmt, String slug, String status) {
        this.date = date;
        this.vsnName = vsnName;
        this.modifiedGmt = modifiedGmt;
        this.author = author;
        this.programSourceThumbnail = programSourceThumbnail;
        this.title = title;
        this.type = type;
        this.embedded = embedded;
        this.programInfo = programInfo;
        this.modified = modified;
        this.titleRaw = titleRaw;
        this.id = id;
        this.terminalsGroups = terminalsGroups;
        this.dateGmt = dateGmt;
        this.slug = slug;
        this.status = status;
    }
    
    // getter/setter
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getVsnName() { return vsnName; }
    public void setVsnName(String vsnName) { this.vsnName = vsnName; }
    
    public String getModifiedGmt() { return modifiedGmt; }
    public void setModifiedGmt(String modifiedGmt) { this.modifiedGmt = modifiedGmt; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public Boolean getPublishedBySchedule() { return publishedBySchedule; }
    public void setPublishedBySchedule(Boolean publishedBySchedule) { this.publishedBySchedule = publishedBySchedule; }
    
    public String getProgramSourceThumbnail() { return programSourceThumbnail; }
    public void setProgramSourceThumbnail(String programSourceThumbnail) { this.programSourceThumbnail = programSourceThumbnail; }
    
    public Title getTitle() { return title; }
    public void setTitle(Title title) { this.title = title; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Embedded getEmbedded() { return embedded; }
    public void setEmbedded(Embedded embedded) { this.embedded = embedded; }
    
    public Object getProgramInfo() { return programInfo; }
    public void setProgramInfo(Object programInfo) { this.programInfo = programInfo; }
    
    public String getModified() { return modified; }
    public void setModified(String modified) { this.modified = modified; }
    
    public String getTitleRaw() { return titleRaw; }
    public void setTitleRaw(String titleRaw) { this.titleRaw = titleRaw; }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public List<TerminalGroup> getTerminalsGroups() { return terminalsGroups; }
    public void setTerminalsGroups(List<TerminalGroup> terminalsGroups) { this.terminalsGroups = terminalsGroups; }
    
    public String getDateGmt() { return dateGmt; }
    public void setDateGmt(String dateGmt) { this.dateGmt = dateGmt; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    /**
     * 标题对象
     */
    public static class Title {
        private String rendered;
        
        public Title() {}
        
        public Title(String rendered) {
            this.rendered = rendered;
        }
        
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
    }
    
    /**
     * 嵌入对象
     */
    public static class Embedded {
        private List<Author> author;
        
        public Embedded() {}
        
        public Embedded(List<Author> author) {
            this.author = author;
        }
        
        public List<Author> getAuthor() { return author; }
        public void setAuthor(List<Author> author) { this.author = author; }
    }
    
    /**
     * 作者对象
     */
    public static class Author {
        private String name;
        
        public Author() {}
        
        public Author(String name) {
            this.name = name;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    /**
     * 终端组对象
     */
    public static class TerminalGroup {
        private Boolean all;
        private Integer parent;
        private String name;
        private Integer id;
        private List<Object> terminals;
        
        public TerminalGroup() {}
        
        public TerminalGroup(Boolean all, Integer parent, String name, Integer id, List<Object> terminals) {
            this.all = all;
            this.parent = parent;
            this.name = name;
            this.id = id;
            this.terminals = terminals;
        }
        
        public Boolean getAll() { return all; }
        public void setAll(Boolean all) { this.all = all; }
        
        public Integer getParent() { return parent; }
        public void setParent(Integer parent) { this.parent = parent; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public List<Object> getTerminals() { return terminals; }
        public void setTerminals(List<Object> terminals) { this.terminals = terminals; }
    }
} 