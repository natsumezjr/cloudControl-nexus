package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 应用终端组排程请求DTO
 */
public class ApplyTerminalGroupScheduleRequest {
    
    @JsonProperty("to_children")
    private Boolean to_children;
    
    @JsonProperty("program_ids")
    private List<Integer> program_ids;
    
    @JsonProperty("schedules")
    private SchedulesRequest schedules;
    
    @JsonProperty("thumbnails")
    private List<ThumbnailRequest> thumbnails;
    
    // 构造函数
    public ApplyTerminalGroupScheduleRequest() {}
    
    public ApplyTerminalGroupScheduleRequest(Boolean to_children, List<Integer> program_ids, 
                                           SchedulesRequest schedules, List<ThumbnailRequest> thumbnails) {
        this.to_children = to_children;
        this.program_ids = program_ids;
        this.schedules = schedules;
        this.thumbnails = thumbnails;
    }
    
    // Getter和Setter方法
    public Boolean getTo_children() {
        return to_children;
    }
    
    public void setTo_children(Boolean to_children) {
        this.to_children = to_children;
    }
    
    public List<Integer> getProgram_ids() {
        return program_ids;
    }
    
    public void setProgram_ids(List<Integer> program_ids) {
        this.program_ids = program_ids;
    }
    
    public SchedulesRequest getSchedules() {
        return schedules;
    }
    
    public void setSchedules(SchedulesRequest schedules) {
        this.schedules = schedules;
    }
    
    public List<ThumbnailRequest> getThumbnails() {
        return thumbnails;
    }
    
    public void setThumbnails(List<ThumbnailRequest> thumbnails) {
        this.thumbnails = thumbnails;
    }
    
    /**
     * 缩略图请求
     */
    public static class ThumbnailRequest {
        @JsonProperty("id")
        private Integer id;
        
        @JsonProperty("src")
        private String src;
        
        public ThumbnailRequest() {}
        
        public ThumbnailRequest(Integer id, String src) {
            this.id = id;
            this.src = src;
        }
        
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getSrc() {
            return src;
        }
        
        public void setSrc(String src) {
            this.src = src;
        }
    }
    
    /**
     * 排程请求
     */
    public static class SchedulesRequest {
        @JsonProperty("contentsSchedule")
        private List<ContentsScheduleRequest> contentsSchedule;
        
        @JsonProperty("commandSchedule")
        private List<CommandScheduleRequest> commandSchedule;
        
        public SchedulesRequest() {}
        
        public SchedulesRequest(List<ContentsScheduleRequest> contentsSchedule, 
                              List<CommandScheduleRequest> commandSchedule) {
            this.contentsSchedule = contentsSchedule;
            this.commandSchedule = commandSchedule;
        }
        
        public List<ContentsScheduleRequest> getContentsSchedule() {
            return contentsSchedule;
        }
        
        public void setContentsSchedule(List<ContentsScheduleRequest> contentsSchedule) {
            this.contentsSchedule = contentsSchedule;
        }
        
        public List<CommandScheduleRequest> getCommandSchedule() {
            return commandSchedule;
        }
        
        public void setCommandSchedule(List<CommandScheduleRequest> commandSchedule) {
            this.commandSchedule = commandSchedule;
        }
    }
    
    /**
     * 节目排程请求
     */
    public static class ContentsScheduleRequest {
        @JsonProperty("if_limit_date")
        private Boolean if_limit_date;
        
        @JsonProperty("if_limit_time")
        private Boolean if_limit_time;
        
        @JsonProperty("if_limit_weekday")
        private Boolean if_limit_weekday;
        
        @JsonProperty("limit_date")
        private LimitDateRequest limit_date;
        
        @JsonProperty("limit_time")
        private LimitTimeRequest limit_time;
        
        @JsonProperty("limit_weekday")
        private List<Boolean> limit_weekday;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("operation")
        private ContentsOperationRequest operation;
        
        @JsonProperty("priority")
        private String priority;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("type_priority")
        private Integer type_priority;
        
        public ContentsScheduleRequest() {}
        
        // Getter和Setter方法
        public Boolean getIf_limit_date() {
            return if_limit_date;
        }
        
        public void setIf_limit_date(Boolean if_limit_date) {
            this.if_limit_date = if_limit_date;
        }
        
        public Boolean getIf_limit_time() {
            return if_limit_time;
        }
        
        public void setIf_limit_time(Boolean if_limit_time) {
            this.if_limit_time = if_limit_time;
        }
        
        public Boolean getIf_limit_weekday() {
            return if_limit_weekday;
        }
        
        public void setIf_limit_weekday(Boolean if_limit_weekday) {
            this.if_limit_weekday = if_limit_weekday;
        }
        
        public LimitDateRequest getLimit_date() {
            return limit_date;
        }
        
        public void setLimit_date(LimitDateRequest limit_date) {
            this.limit_date = limit_date;
        }
        
        public LimitTimeRequest getLimit_time() {
            return limit_time;
        }
        
        public void setLimit_time(LimitTimeRequest limit_time) {
            this.limit_time = limit_time;
        }
        
        public List<Boolean> getLimit_weekday() {
            return limit_weekday;
        }
        
        public void setLimit_weekday(List<Boolean> limit_weekday) {
            this.limit_weekday = limit_weekday;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public ContentsOperationRequest getOperation() {
            return operation;
        }
        
        public void setOperation(ContentsOperationRequest operation) {
            this.operation = operation;
        }
        
        public String getPriority() {
            return priority;
        }
        
        public void setPriority(String priority) {
            this.priority = priority;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public Integer getType_priority() {
            return type_priority;
        }
        
        public void setType_priority(Integer type_priority) {
            this.type_priority = type_priority;
        }
    }
    
    /**
     * 指令排程请求
     */
    public static class CommandScheduleRequest {
        @JsonProperty("content")
        private CommandContentRequest content;
        
        @JsonProperty("if_limit_date")
        private Boolean if_limit_date;
        
        @JsonProperty("if_limit_weekday")
        private Boolean if_limit_weekday;
        
        @JsonProperty("limit_date")
        private LimitDateRequest limit_date;
        
        @JsonProperty("limit_weekday")
        private List<Boolean> limit_weekday;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("op_time")
        private List<String> op_time;
        
        @JsonProperty("operation")
        private CommandOperationRequest operation;
        
        @JsonProperty("type")
        private String type;
        
        public CommandScheduleRequest() {}
        
        // Getter和Setter方法
        public CommandContentRequest getContent() {
            return content;
        }
        
        public void setContent(CommandContentRequest content) {
            this.content = content;
        }
        
        public Boolean getIf_limit_date() {
            return if_limit_date;
        }
        
        public void setIf_limit_date(Boolean if_limit_date) {
            this.if_limit_date = if_limit_date;
        }
        
        public Boolean getIf_limit_weekday() {
            return if_limit_weekday;
        }
        
        public void setIf_limit_weekday(Boolean if_limit_weekday) {
            this.if_limit_weekday = if_limit_weekday;
        }
        
        public LimitDateRequest getLimit_date() {
            return limit_date;
        }
        
        public void setLimit_date(LimitDateRequest limit_date) {
            this.limit_date = limit_date;
        }
        
        public List<Boolean> getLimit_weekday() {
            return limit_weekday;
        }
        
        public void setLimit_weekday(List<Boolean> limit_weekday) {
            this.limit_weekday = limit_weekday;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public List<String> getOp_time() {
            return op_time;
        }
        
        public void setOp_time(List<String> op_time) {
            this.op_time = op_time;
        }
        
        public CommandOperationRequest getOperation() {
            return operation;
        }
        
        public void setOperation(CommandOperationRequest operation) {
            this.operation = operation;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
    }
    
    /**
     * 日期限制请求
     */
    public static class LimitDateRequest {
        @JsonProperty("start")
        private String start;
        
        @JsonProperty("start_time")
        private String start_time;
        
        @JsonProperty("end")
        private String end;
        
        @JsonProperty("end_time")
        private String end_time;
        
        public LimitDateRequest() {}
        
        public LimitDateRequest(String start, String start_time, String end, String end_time) {
            this.start = start;
            this.start_time = start_time;
            this.end = end;
            this.end_time = end_time;
        }
        
        public String getStart() {
            return start;
        }
        
        public void setStart(String start) {
            this.start = start;
        }
        
        public String getStart_time() {
            return start_time;
        }
        
        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
        
        public String getEnd() {
            return end;
        }
        
        public void setEnd(String end) {
            this.end = end;
        }
        
        public String getEnd_time() {
            return end_time;
        }
        
        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
    
    /**
     * 时间限制请求
     */
    public static class LimitTimeRequest {
        @JsonProperty("start_time")
        private String start_time;
        
        @JsonProperty("end_time")
        private String end_time;
        
        public LimitTimeRequest() {}
        
        public LimitTimeRequest(String start_time, String end_time) {
            this.start_time = start_time;
            this.end_time = end_time;
        }
        
        public String getStart_time() {
            return start_time;
        }
        
        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
        
        public String getEnd_time() {
            return end_time;
        }
        
        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
    
    /**
     * 指令内容请求
     */
    public static class CommandContentRequest {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("value")
        private Object value;
        
        public CommandContentRequest() {}
        
        public CommandContentRequest(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Object getValue() {
            return value;
        }
        
        public void setValue(Object value) {
            this.value = value;
        }
    }
    
    /**
     * 节目操作请求
     */
    public static class ContentsOperationRequest {
        @JsonProperty("id")
        private Integer id;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("vsn")
        private String vsn;
        
        @JsonProperty("source")
        private String source;
        
        public ContentsOperationRequest() {}
        
        public ContentsOperationRequest(Integer id, String name, String vsn, String source) {
            this.id = id;
            this.name = name;
            this.vsn = vsn;
            this.source = source;
        }
        
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getVsn() {
            return vsn;
        }
        
        public void setVsn(String vsn) {
            this.vsn = vsn;
        }
        
        public String getSource() {
            return source;
        }
        
        public void setSource(String source) {
            this.source = source;
        }
    }
    
    /**
     * 指令操作请求
     */
    public static class CommandOperationRequest {
        @JsonProperty("author_url")
        private String author_url;
        
        @JsonProperty("karma")
        private Integer karma;
        
        @JsonProperty("content")
        private String content;
        
        public CommandOperationRequest() {}
        
        public CommandOperationRequest(String author_url, Integer karma, String content) {
            this.author_url = author_url;
            this.karma = karma;
            this.content = content;
        }
        
        public String getAuthor_url() {
            return author_url;
        }
        
        public void setAuthor_url(String author_url) {
            this.author_url = author_url;
        }
        
        public Integer getKarma() {
            return karma;
        }
        
        public void setKarma(Integer karma) {
            this.karma = karma;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
    }
} 