package com.cloudcontrol.dto.terminal;

/**
 * 终端删除响应DTO
 */
public class TerminalDeleteResponse {
    
    private String date;
    private Integer author;
    private ExtraInfo extra;
    private Integer id;
    private String type;
    private String date_gmt;
    private TitleInfo title;
    private ExcerptInfo excerpt;
    private String comment_status;
    private String post_meta;
    private Object[] TriggerEmails;
    
    public TerminalDeleteResponse() {}
    
    public TerminalDeleteResponse(String date, Integer author, ExtraInfo extra, Integer id, 
                                String type, String date_gmt, TitleInfo title, ExcerptInfo excerpt, 
                                String comment_status, String post_meta, Object[] TriggerEmails) {
        this.date = date;
        this.author = author;
        this.extra = extra;
        this.id = id;
        this.type = type;
        this.date_gmt = date_gmt;
        this.title = title;
        this.excerpt = excerpt;
        this.comment_status = comment_status;
        this.post_meta = post_meta;
        this.TriggerEmails = TriggerEmails;
    }
    
    // 用于错误响应的构造函数
    public TerminalDeleteResponse(Integer id, String errorMessage) {
        this.id = id;
        this.date = null;
        this.author = null;
        this.extra = null;
        this.type = null;
        this.date_gmt = null;
        this.title = null;
        this.excerpt = null;
        this.comment_status = null;
        this.post_meta = null;
        this.TriggerEmails = null;
    }
    
    // getter/setter
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public ExtraInfo getExtra() { return extra; }
    public void setExtra(ExtraInfo extra) { this.extra = extra; }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDate_gmt() { return date_gmt; }
    public void setDate_gmt(String date_gmt) { this.date_gmt = date_gmt; }
    
    public TitleInfo getTitle() { return title; }
    public void setTitle(TitleInfo title) { this.title = title; }
    
    public ExcerptInfo getExcerpt() { return excerpt; }
    public void setExcerpt(ExcerptInfo excerpt) { this.excerpt = excerpt; }
    
    public String getComment_status() { return comment_status; }
    public void setComment_status(String comment_status) { this.comment_status = comment_status; }
    
    public String getPost_meta() { return post_meta; }
    public void setPost_meta(String post_meta) { this.post_meta = post_meta; }
    
    public Object[] getTriggerEmails() { return TriggerEmails; }
    public void setTriggerEmails(Object[] TriggerEmails) { this.TriggerEmails = TriggerEmails; }
    
    /**
     * 额外信息内部类
     */
    public static class ExtraInfo {
        private String author_display_name;
        
        public ExtraInfo() {}
        
        public ExtraInfo(String author_display_name) {
            this.author_display_name = author_display_name;
        }
        
        // getter/setter
        public String getAuthor_display_name() { return author_display_name; }
        public void setAuthor_display_name(String author_display_name) { this.author_display_name = author_display_name; }
    }
    
    /**
     * 标题信息内部类
     */
    public static class TitleInfo {
        private String rendered;
        private String raw;
        
        public TitleInfo() {}
        
        public TitleInfo(String rendered, String raw) {
            this.rendered = rendered;
            this.raw = raw;
        }
        
        // getter/setter
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
        
        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }
    }
    
    /**
     * 摘要信息内部类
     */
    public static class ExcerptInfo {
        private String rendered;
        private Boolean _protected;
        private String raw;
        
        public ExcerptInfo() {}
        
        public ExcerptInfo(String rendered, Boolean _protected, String raw) {
            this.rendered = rendered;
            this._protected = _protected;
            this.raw = raw;
        }
        
        // getter/setter
        public String getRendered() { return rendered; }
        public void setRendered(String rendered) { this.rendered = rendered; }
        
        public Boolean getProtected() { return _protected; }
        public void setProtected(Boolean _protected) { this._protected = _protected; }
        
        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }
    }
} 