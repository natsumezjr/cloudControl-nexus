package com.cloudcontrol.dto.schedule;

import java.util.List;

/**
 * 更新终端排程请求DTO
 */
public class UpdateTerminalScheduleRequest {
    
    private List<Integer> program_ids;
    private List<ThumbnailRequest> thumbnails;
    private Boolean to_children;
    private SchedulesRequest schedules;
    
    public UpdateTerminalScheduleRequest() {}
    
    public UpdateTerminalScheduleRequest(List<Integer> program_ids, List<ThumbnailRequest> thumbnails, 
                                       Boolean to_children, SchedulesRequest schedules) {
        this.program_ids = program_ids;
        this.thumbnails = thumbnails;
        this.to_children = to_children;
        this.schedules = schedules;
    }
    
    // getter/setter
    public List<Integer> getProgram_ids() { return program_ids; }
    public void setProgram_ids(List<Integer> program_ids) { this.program_ids = program_ids; }
    
    public List<ThumbnailRequest> getThumbnails() { return thumbnails; }
    public void setThumbnails(List<ThumbnailRequest> thumbnails) { this.thumbnails = thumbnails; }
    
    public Boolean getTo_children() { return to_children; }
    public void setTo_children(Boolean to_children) { this.to_children = to_children; }
    
    public SchedulesRequest getSchedules() { return schedules; }
    public void setSchedules(SchedulesRequest schedules) { this.schedules = schedules; }
    
    /**
     * 缩略图请求
     */
    public static class ThumbnailRequest {
        private Integer id;
        private String src;
        
        public ThumbnailRequest() {}
        
        public ThumbnailRequest(Integer id, String src) {
            this.id = id;
            this.src = src;
        }
        
        // getter/setter
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getSrc() { return src; }
        public void setSrc(String src) { this.src = src; }
    }
    
    /**
     * 排程请求
     */
    public static class SchedulesRequest {
        private List<CommandScheduleRequest> commandSchedule;
        private List<ContentsScheduleRequest> contentsSchedule;
        
        public SchedulesRequest() {}
        
        public SchedulesRequest(List<CommandScheduleRequest> commandSchedule, List<ContentsScheduleRequest> contentsSchedule) {
            this.commandSchedule = commandSchedule;
            this.contentsSchedule = contentsSchedule;
        }
        
        // getter/setter
        public List<CommandScheduleRequest> getCommandSchedule() { return commandSchedule; }
        public void setCommandSchedule(List<CommandScheduleRequest> commandSchedule) { this.commandSchedule = commandSchedule; }
        
        public List<ContentsScheduleRequest> getContentsSchedule() { return contentsSchedule; }
        public void setContentsSchedule(List<ContentsScheduleRequest> contentsSchedule) { this.contentsSchedule = contentsSchedule; }
    }
    
    /**
     * 指令排程请求
     */
    public static class CommandScheduleRequest {
        private CommandContentRequest content;
        private Boolean if_limit_date;
        private LimitDateRequest limit_date;
        private Boolean if_limit_weekday;
        private List<Boolean> limit_weekday;
        private String name;
        private List<String> op_time;
        private String type;
        private CommandOperationRequest operation;
        
        public CommandScheduleRequest() {}
        
        // getter/setter
        public CommandContentRequest getContent() { return content; }
        public void setContent(CommandContentRequest content) { this.content = content; }
        
        public Boolean getIf_limit_date() { return if_limit_date; }
        public void setIf_limit_date(Boolean if_limit_date) { this.if_limit_date = if_limit_date; }
        
        public LimitDateRequest getLimit_date() { return limit_date; }
        public void setLimit_date(LimitDateRequest limit_date) { this.limit_date = limit_date; }
        
        public Boolean getIf_limit_weekday() { return if_limit_weekday; }
        public void setIf_limit_weekday(Boolean if_limit_weekday) { this.if_limit_weekday = if_limit_weekday; }
        
        public List<Boolean> getLimit_weekday() { return limit_weekday; }
        public void setLimit_weekday(List<Boolean> limit_weekday) { this.limit_weekday = limit_weekday; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public List<String> getOp_time() { return op_time; }
        public void setOp_time(List<String> op_time) { this.op_time = op_time; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public CommandOperationRequest getOperation() { return operation; }
        public void setOperation(CommandOperationRequest operation) { this.operation = operation; }
    }
    
    /**
     * 指令内容请求
     */
    public static class CommandContentRequest {
        private String name;
        private Object value;
        
        public CommandContentRequest() {}
        
        public CommandContentRequest(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        
        // getter/setter
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Object getValue() { return value; }
        public void setValue(Object value) { this.value = value; }
    }
    
    /**
     * 日期限制请求
     */
    public static class LimitDateRequest {
        private String start;
        private String end;
        private String start_time;
        private String end_time;
        
        public LimitDateRequest() {}
        
        public LimitDateRequest(String start, String end) {
            this.start = start;
            this.end = end;
        }
        
        public LimitDateRequest(String start, String end, String start_time, String end_time) {
            this.start = start;
            this.end = end;
            this.start_time = start_time;
            this.end_time = end_time;
        }
        
        // getter/setter
        public String getStart() { return start; }
        public void setStart(String start) { this.start = start; }
        
        public String getEnd() { return end; }
        public void setEnd(String end) { this.end = end; }
        
        public String getStart_time() { return start_time; }
        public void setStart_time(String start_time) { this.start_time = start_time; }
        
        public String getEnd_time() { return end_time; }
        public void setEnd_time(String end_time) { this.end_time = end_time; }
    }
    
    /**
     * 指令操作请求
     */
    public static class CommandOperationRequest {
        private String author_url;
        private Integer karma;
        private String content;
        
        public CommandOperationRequest() {}
        
        public CommandOperationRequest(String author_url, Integer karma, String content) {
            this.author_url = author_url;
            this.karma = karma;
            this.content = content;
        }
        
        // getter/setter
        public String getAuthor_url() { return author_url; }
        public void setAuthor_url(String author_url) { this.author_url = author_url; }
        
        public Integer getKarma() { return karma; }
        public void setKarma(Integer karma) { this.karma = karma; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    /**
     * 节目排程请求
     */
    public static class ContentsScheduleRequest {
        private Boolean if_limit_date;
        private LimitDateRequest limit_date;
        private Boolean if_limit_time;
        private LimitTimeRequest limit_time;
        private Boolean if_limit_weekday;
        private List<Boolean> limit_weekday;
        private String name;
        private String priority;
        private String type;
        private Integer type_priority;
        private ContentsOperationRequest operation;
        
        public ContentsScheduleRequest() {}
        
        // getter/setter
        public Boolean getIf_limit_date() { return if_limit_date; }
        public void setIf_limit_date(Boolean if_limit_date) { this.if_limit_date = if_limit_date; }
        
        public LimitDateRequest getLimit_date() { return limit_date; }
        public void setLimit_date(LimitDateRequest limit_date) { this.limit_date = limit_date; }
        
        public Boolean getIf_limit_time() { return if_limit_time; }
        public void setIf_limit_time(Boolean if_limit_time) { this.if_limit_time = if_limit_time; }
        
        public LimitTimeRequest getLimit_time() { return limit_time; }
        public void setLimit_time(LimitTimeRequest limit_time) { this.limit_time = limit_time; }
        
        public Boolean getIf_limit_weekday() { return if_limit_weekday; }
        public void setIf_limit_weekday(Boolean if_limit_weekday) { this.if_limit_weekday = if_limit_weekday; }
        
        public List<Boolean> getLimit_weekday() { return limit_weekday; }
        public void setLimit_weekday(List<Boolean> limit_weekday) { this.limit_weekday = limit_weekday; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public Integer getType_priority() { return type_priority; }
        public void setType_priority(Integer type_priority) { this.type_priority = type_priority; }
        
        public ContentsOperationRequest getOperation() { return operation; }
        public void setOperation(ContentsOperationRequest operation) { this.operation = operation; }
    }
    
    /**
     * 时间限制请求
     */
    public static class LimitTimeRequest {
        private String start_time;
        private String end_time;
        
        public LimitTimeRequest() {}
        
        public LimitTimeRequest(String start_time, String end_time) {
            this.start_time = start_time;
            this.end_time = end_time;
        }
        
        // getter/setter
        public String getStart_time() { return start_time; }
        public void setStart_time(String start_time) { this.start_time = start_time; }
        
        public String getEnd_time() { return end_time; }
        public void setEnd_time(String end_time) { this.end_time = end_time; }
    }
    
    /**
     * 节目操作请求
     */
    public static class ContentsOperationRequest {
        private Integer id;
        private String name;
        private String vsn;
        private String source;
        
        public ContentsOperationRequest() {}
        
        public ContentsOperationRequest(Integer id, String name, String vsn, String source) {
            this.id = id;
            this.name = name;
            this.vsn = vsn;
            this.source = source;
        }
        
        // getter/setter
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getVsn() { return vsn; }
        public void setVsn(String vsn) { this.vsn = vsn; }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }
} 