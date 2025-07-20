package com.cloudcontrol.dto.schedule;

import java.util.List;

/**
 * 终端排程响应DTO
 */
public class TerminalScheduleResponse {
    
    private Integer status;
    private String msg;
    private TerminalScheduleData data;
    
    public TerminalScheduleResponse() {}
    
    public TerminalScheduleResponse(Integer status, String msg, TerminalScheduleData data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    // getter/setter
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    
    public TerminalScheduleData getData() { return data; }
    public void setData(TerminalScheduleData data) { this.data = data; }
    
    /**
     * 终端排程数据
     */
    public static class TerminalScheduleData {
        private Long updateTerminalScheduleTime;
        private ScheduleJsonBean scheduleJsonBean;
        
        public TerminalScheduleData() {}
        
        public TerminalScheduleData(Long updateTerminalScheduleTime, ScheduleJsonBean scheduleJsonBean) {
            this.updateTerminalScheduleTime = updateTerminalScheduleTime;
            this.scheduleJsonBean = scheduleJsonBean;
        }
        
        // getter/setter
        public Long getUpdateTerminalScheduleTime() { return updateTerminalScheduleTime; }
        public void setUpdateTerminalScheduleTime(Long updateTerminalScheduleTime) { this.updateTerminalScheduleTime = updateTerminalScheduleTime; }
        
        public ScheduleJsonBean getScheduleJsonBean() { return scheduleJsonBean; }
        public void setScheduleJsonBean(ScheduleJsonBean scheduleJsonBean) { this.scheduleJsonBean = scheduleJsonBean; }
    }
    
    /**
     * 排程JSON Bean
     */
    public static class ScheduleJsonBean {
        private Boolean to_children;
        private List<Integer> program_ids;
        private SchedulesResponse schedules;
        private List<ThumbnailResponse> thumbnails;
        
        public ScheduleJsonBean() {}
        
        public ScheduleJsonBean(Boolean to_children, List<Integer> program_ids, SchedulesResponse schedules, List<ThumbnailResponse> thumbnails) {
            this.to_children = to_children;
            this.program_ids = program_ids;
            this.schedules = schedules;
            this.thumbnails = thumbnails;
        }
        
        // getter/setter
        public Boolean getTo_children() { return to_children; }
        public void setTo_children(Boolean to_children) { this.to_children = to_children; }
        
        public List<Integer> getProgram_ids() { return program_ids; }
        public void setProgram_ids(List<Integer> program_ids) { this.program_ids = program_ids; }
        
        public SchedulesResponse getSchedules() { return schedules; }
        public void setSchedules(SchedulesResponse schedules) { this.schedules = schedules; }
        
        public List<ThumbnailResponse> getThumbnails() { return thumbnails; }
        public void setThumbnails(List<ThumbnailResponse> thumbnails) { this.thumbnails = thumbnails; }
    }
    
    /**
     * 缩略图响应
     */
    public static class ThumbnailResponse {
        private Integer id;
        private String src;
        
        public ThumbnailResponse() {}
        
        public ThumbnailResponse(Integer id, String src) {
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
     * 排程响应
     */
    public static class SchedulesResponse {
        private List<CommandScheduleResponse> commandSchedule;
        private List<ContentsScheduleResponse> contentsSchedule;
        
        public SchedulesResponse() {}
        
        public SchedulesResponse(List<CommandScheduleResponse> commandSchedule, List<ContentsScheduleResponse> contentsSchedule) {
            this.commandSchedule = commandSchedule;
            this.contentsSchedule = contentsSchedule;
        }
        
        // getter/setter
        public List<CommandScheduleResponse> getCommandSchedule() { return commandSchedule; }
        public void setCommandSchedule(List<CommandScheduleResponse> commandSchedule) { this.commandSchedule = commandSchedule; }
        
        public List<ContentsScheduleResponse> getContentsSchedule() { return contentsSchedule; }
        public void setContentsSchedule(List<ContentsScheduleResponse> contentsSchedule) { this.contentsSchedule = contentsSchedule; }
    }
    
    /**
     * 指令排程响应
     */
    public static class CommandScheduleResponse {
        private CommandContentResponse content;
        private Boolean if_limit_date;
        private LimitDateResponse limit_date;
        private Boolean if_limit_weekday;
        private List<Boolean> limit_weekday;
        private String name;
        private List<String> op_time;
        private String type;
        private CommandOperationResponse operation;
        
        public CommandScheduleResponse() {}
        
        // getter/setter
        public CommandContentResponse getContent() { return content; }
        public void setContent(CommandContentResponse content) { this.content = content; }
        
        public Boolean getIf_limit_date() { return if_limit_date; }
        public void setIf_limit_date(Boolean if_limit_date) { this.if_limit_date = if_limit_date; }
        
        public LimitDateResponse getLimit_date() { return limit_date; }
        public void setLimit_date(LimitDateResponse limit_date) { this.limit_date = limit_date; }
        
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
        
        public CommandOperationResponse getOperation() { return operation; }
        public void setOperation(CommandOperationResponse operation) { this.operation = operation; }
    }
    
    /**
     * 指令内容响应
     */
    public static class CommandContentResponse {
        private String name;
        private Object value;
        
        public CommandContentResponse() {}
        
        public CommandContentResponse(String name, Object value) {
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
     * 日期限制响应
     */
    public static class LimitDateResponse {
        private String start;
        private String end;
        private String start_time;
        private String end_time;
        
        public LimitDateResponse() {}
        
        public LimitDateResponse(String start, String end) {
            this.start = start;
            this.end = end;
        }
        
        public LimitDateResponse(String start, String end, String start_time, String end_time) {
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
     * 指令操作响应
     */
    public static class CommandOperationResponse {
        private String author_url;
        private Integer karma;
        private String content;
        
        public CommandOperationResponse() {}
        
        public CommandOperationResponse(String author_url, Integer karma, String content) {
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
     * 节目排程响应
     */
    public static class ContentsScheduleResponse {
        private Boolean if_limit_date;
        private LimitDateResponse limit_date;
        private Boolean if_limit_time;
        private LimitTimeResponse limit_time;
        private Boolean if_limit_weekday;
        private List<Boolean> limit_weekday;
        private String name;
        private String priority;
        private String type;
        private Integer type_priority;
        private ContentsOperationResponse operation;
        
        public ContentsScheduleResponse() {}
        
        // getter/setter
        public Boolean getIf_limit_date() { return if_limit_date; }
        public void setIf_limit_date(Boolean if_limit_date) { this.if_limit_date = if_limit_date; }
        
        public LimitDateResponse getLimit_date() { return limit_date; }
        public void setLimit_date(LimitDateResponse limit_date) { this.limit_date = limit_date; }
        
        public Boolean getIf_limit_time() { return if_limit_time; }
        public void setIf_limit_time(Boolean if_limit_time) { this.if_limit_time = if_limit_time; }
        
        public LimitTimeResponse getLimit_time() { return limit_time; }
        public void setLimit_time(LimitTimeResponse limit_time) { this.limit_time = limit_time; }
        
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
        
        public ContentsOperationResponse getOperation() { return operation; }
        public void setOperation(ContentsOperationResponse operation) { this.operation = operation; }
    }
    
    /**
     * 时间限制响应
     */
    public static class LimitTimeResponse {
        private String start_time;
        private String end_time;
        
        public LimitTimeResponse() {}
        
        public LimitTimeResponse(String start_time, String end_time) {
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
     * 节目操作响应
     */
    public static class ContentsOperationResponse {
        private Integer id;
        private String name;
        private String vsn;
        private String source;
        
        public ContentsOperationResponse() {}
        
        public ContentsOperationResponse(Integer id, String name, String vsn, String source) {
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