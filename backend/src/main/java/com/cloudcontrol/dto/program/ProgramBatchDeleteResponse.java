package com.cloudcontrol.dto.program;

/**
 * 批量删除节目响应DTO
 */
public class ProgramBatchDeleteResponse {
    
    private Integer id;
    private Integer author;
    private String date;
    private String dateGmt;
    private String content;
    private String titleRaw;
    private String excerpt;
    private String status;
    private String password;
    private String name;
    private String toPing;
    private String pinged;
    private String modified;
    private String modifiedGmt;
    private String contentFiltered;
    private String type;
    private String vsnName;
    private String programSourceThumbnail;
    private Integer parent;
    private String guid;
    private String mimeType;
    private Object programInfo;
    private Object terminalsGroups;
    private Embedded embedded;
    private Title title;
    private Object links;
    
    public ProgramBatchDeleteResponse() {}
    
    public ProgramBatchDeleteResponse(Integer id, Integer author, String date, String dateGmt, 
                                     String content, String titleRaw, String excerpt, String status,
                                     String password, String name, String toPing, String pinged,
                                     String modified, String modifiedGmt, String contentFiltered,
                                     String type, String vsnName, String programSourceThumbnail,
                                     Integer parent, String guid, String mimeType, Object programInfo,
                                     Object terminalsGroups, Embedded embedded, Title title, Object links) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.dateGmt = dateGmt;
        this.content = content;
        this.titleRaw = titleRaw;
        this.excerpt = excerpt;
        this.status = status;
        this.password = password;
        this.name = name;
        this.toPing = toPing;
        this.pinged = pinged;
        this.modified = modified;
        this.modifiedGmt = modifiedGmt;
        this.contentFiltered = contentFiltered;
        this.type = type;
        this.vsnName = vsnName;
        this.programSourceThumbnail = programSourceThumbnail;
        this.parent = parent;
        this.guid = guid;
        this.mimeType = mimeType;
        this.programInfo = programInfo;
        this.terminalsGroups = terminalsGroups;
        this.embedded = embedded;
        this.title = title;
        this.links = links;
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
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getTitleRaw() { return titleRaw; }
    public void setTitleRaw(String titleRaw) { this.titleRaw = titleRaw; }
    
    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getToPing() { return toPing; }
    public void setToPing(String toPing) { this.toPing = toPing; }
    
    public String getPinged() { return pinged; }
    public void setPinged(String pinged) { this.pinged = pinged; }
    
    public String getModified() { return modified; }
    public void setModified(String modified) { this.modified = modified; }
    
    public String getModifiedGmt() { return modifiedGmt; }
    public void setModifiedGmt(String modifiedGmt) { this.modifiedGmt = modifiedGmt; }
    
    public String getContentFiltered() { return contentFiltered; }
    public void setContentFiltered(String contentFiltered) { this.contentFiltered = contentFiltered; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getVsnName() { return vsnName; }
    public void setVsnName(String vsnName) { this.vsnName = vsnName; }
    
    public String getProgramSourceThumbnail() { return programSourceThumbnail; }
    public void setProgramSourceThumbnail(String programSourceThumbnail) { this.programSourceThumbnail = programSourceThumbnail; }
    
    public Integer getParent() { return parent; }
    public void setParent(Integer parent) { this.parent = parent; }
    
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public Object getProgramInfo() { return programInfo; }
    public void setProgramInfo(Object programInfo) { this.programInfo = programInfo; }
    
    public Object getTerminalsGroups() { return terminalsGroups; }
    public void setTerminalsGroups(Object terminalsGroups) { this.terminalsGroups = terminalsGroups; }
    
    public Embedded getEmbedded() { return embedded; }
    public void setEmbedded(Embedded embedded) { this.embedded = embedded; }
    
    public Title getTitle() { return title; }
    public void setTitle(Title title) { this.title = title; }
    
    public Object getLinks() { return links; }
    public void setLinks(Object links) { this.links = links; }
    
    /**
     * 嵌入对象
     */
    public static class Embedded {
        private Object author;
        
        public Embedded() {}
        
        public Embedded(Object author) {
            this.author = author;
        }
        
        public Object getAuthor() { return author; }
        public void setAuthor(Object author) { this.author = author; }
    }
    
    /**
     * 标题对象
     */
    public static class Title {
        private Object rendered;
        
        public Title() {}
        
        public Title(Object rendered) {
            this.rendered = rendered;
        }
        
        public Object getRendered() { return rendered; }
        public void setRendered(Object rendered) { this.rendered = rendered; }
    }
} 