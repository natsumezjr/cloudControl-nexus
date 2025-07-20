package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * 获取终端组排程信息响应DTO
 */
public class TerminalGroupSchedulesResponse {
    
    @JsonProperty("status")
    private Integer status;
    
    @JsonProperty("msg")
    private String msg;
    
    @JsonProperty("data")
    private Map<String, ScheduleJsonBean> data;
    
    // 构造函数
    public TerminalGroupSchedulesResponse() {}
    
    public TerminalGroupSchedulesResponse(Integer status, String msg, Map<String, ScheduleJsonBean> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    // Getter和Setter方法
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Map<String, ScheduleJsonBean> getData() {
        return data;
    }
    
    public void setData(Map<String, ScheduleJsonBean> data) {
        this.data = data;
    }
    
    /**
     * 排程JSON Bean
     */
    public static class ScheduleJsonBean {
        @JsonProperty("to_children")
        private Boolean to_children;
        
        @JsonProperty("program_ids")
        private java.util.List<Integer> program_ids;
        
        @JsonProperty("schedules")
        private Schedules schedules;
        
        @JsonProperty("thumbnails")
        private java.util.List<Object> thumbnails;
        
        public ScheduleJsonBean() {}
        
        public ScheduleJsonBean(Boolean to_children, java.util.List<Integer> program_ids, 
                              Schedules schedules, java.util.List<Object> thumbnails) {
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
        
        public java.util.List<Integer> getProgram_ids() {
            return program_ids;
        }
        
        public void setProgram_ids(java.util.List<Integer> program_ids) {
            this.program_ids = program_ids;
        }
        
        public Schedules getSchedules() {
            return schedules;
        }
        
        public void setSchedules(Schedules schedules) {
            this.schedules = schedules;
        }
        
        public java.util.List<Object> getThumbnails() {
            return thumbnails;
        }
        
        public void setThumbnails(java.util.List<Object> thumbnails) {
            this.thumbnails = thumbnails;
        }
    }
    
    /**
     * 排程信息
     */
    public static class Schedules {
        @JsonProperty("contentsSchedule")
        private java.util.List<ContentsSchedule> contentsSchedule;
        
        @JsonProperty("commandSchedule")
        private java.util.List<CommandSchedule> commandSchedule;
        
        public Schedules() {}
        
        public Schedules(java.util.List<ContentsSchedule> contentsSchedule, 
                        java.util.List<CommandSchedule> commandSchedule) {
            this.contentsSchedule = contentsSchedule;
            this.commandSchedule = commandSchedule;
        }
        
        // Getter和Setter方法
        public java.util.List<ContentsSchedule> getContentsSchedule() {
            return contentsSchedule;
        }
        
        public void setContentsSchedule(java.util.List<ContentsSchedule> contentsSchedule) {
            this.contentsSchedule = contentsSchedule;
        }
        
        public java.util.List<CommandSchedule> getCommandSchedule() {
            return commandSchedule;
        }
        
        public void setCommandSchedule(java.util.List<CommandSchedule> commandSchedule) {
            this.commandSchedule = commandSchedule;
        }
    }
    
    /**
     * 节目排程
     */
    public static class ContentsSchedule {
        @JsonProperty("if_limit_date")
        private Boolean if_limit_date;
        
        @JsonProperty("if_limit_time")
        private Boolean if_limit_time;
        
        @JsonProperty("if_limit_weekday")
        private Boolean if_limit_weekday;
        
        @JsonProperty("limit_date")
        private LimitDate limit_date;
        
        @JsonProperty("limit_time")
        private LimitTime limit_time;
        
        @JsonProperty("limit_weekday")
        private java.util.List<Boolean> limit_weekday;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("operation")
        private ContentsOperation operation;
        
        @JsonProperty("priority")
        private String priority;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("type_priority")
        private Integer type_priority;
        
        public ContentsSchedule() {}
        
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
        
        public LimitDate getLimit_date() {
            return limit_date;
        }
        
        public void setLimit_date(LimitDate limit_date) {
            this.limit_date = limit_date;
        }
        
        public LimitTime getLimit_time() {
            return limit_time;
        }
        
        public void setLimit_time(LimitTime limit_time) {
            this.limit_time = limit_time;
        }
        
        public java.util.List<Boolean> getLimit_weekday() {
            return limit_weekday;
        }
        
        public void setLimit_weekday(java.util.List<Boolean> limit_weekday) {
            this.limit_weekday = limit_weekday;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public ContentsOperation getOperation() {
            return operation;
        }
        
        public void setOperation(ContentsOperation operation) {
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
     * 指令排程
     */
    public static class CommandSchedule {
        @JsonProperty("operation")
        private CommandOperation operation;
        
        @JsonProperty("op_time")
        private java.util.List<String> op_time;
        
        @JsonProperty("if_limit_date")
        private Boolean if_limit_date;
        
        @JsonProperty("limit_date")
        private LimitDate limit_date;
        
        @JsonProperty("if_limit_weekday")
        private Boolean if_limit_weekday;
        
        @JsonProperty("limit_weekday")
        private java.util.List<Boolean> limit_weekday;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("name")
        private String name;
        
        public CommandSchedule() {}
        
        // Getter和Setter方法
        public CommandOperation getOperation() {
            return operation;
        }
        
        public void setOperation(CommandOperation operation) {
            this.operation = operation;
        }
        
        public java.util.List<String> getOp_time() {
            return op_time;
        }
        
        public void setOp_time(java.util.List<String> op_time) {
            this.op_time = op_time;
        }
        
        public Boolean getIf_limit_date() {
            return if_limit_date;
        }
        
        public void setIf_limit_date(Boolean if_limit_date) {
            this.if_limit_date = if_limit_date;
        }
        
        public LimitDate getLimit_date() {
            return limit_date;
        }
        
        public void setLimit_date(LimitDate limit_date) {
            this.limit_date = limit_date;
        }
        
        public Boolean getIf_limit_weekday() {
            return if_limit_weekday;
        }
        
        public void setIf_limit_weekday(Boolean if_limit_weekday) {
            this.if_limit_weekday = if_limit_weekday;
        }
        
        public java.util.List<Boolean> getLimit_weekday() {
            return limit_weekday;
        }
        
        public void setLimit_weekday(java.util.List<Boolean> limit_weekday) {
            this.limit_weekday = limit_weekday;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    /**
     * 日期限制
     */
    public static class LimitDate {
        @JsonProperty("start")
        private String start;
        
        @JsonProperty("end")
        private String end;
        
        public LimitDate() {}
        
        public LimitDate(String start, String end) {
            this.start = start;
            this.end = end;
        }
        
        public String getStart() {
            return start;
        }
        
        public void setStart(String start) {
            this.start = start;
        }
        
        public String getEnd() {
            return end;
        }
        
        public void setEnd(String end) {
            this.end = end;
        }
    }
    
    /**
     * 时间限制
     */
    public static class LimitTime {
        @JsonProperty("start_time")
        private String start_time;
        
        @JsonProperty("end_time")
        private String end_time;
        
        public LimitTime() {}
        
        public LimitTime(String start_time, String end_time) {
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
     * 节目操作
     */
    public static class ContentsOperation {
        @JsonProperty("id")
        private Integer id;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("vsn")
        private String vsn;
        
        @JsonProperty("source")
        private String source;
        
        public ContentsOperation() {}
        
        public ContentsOperation(Integer id, String name, String vsn, String source) {
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
     * 指令操作
     */
    public static class CommandOperation {
        @JsonProperty("author_url")
        private String author_url;
        
        @JsonProperty("karma")
        private Integer karma;
        
        @JsonProperty("content")
        private String content;
        
        public CommandOperation() {}
        
        public CommandOperation(String author_url, Integer karma, String content) {
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