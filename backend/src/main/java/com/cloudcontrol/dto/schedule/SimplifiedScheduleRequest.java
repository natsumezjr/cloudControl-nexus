package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 简化应用排程请求DTO
 */
public class SimplifiedScheduleRequest {
    
    @JsonProperty("terminalGroupId")
    private Integer terminalGroupId;
    
    @JsonProperty("toChildren")
    private Boolean toChildren;
    
    @JsonProperty("terminalId")
    private Integer terminalId;
    
    @JsonProperty("commandSchedules")
    private List<CommandScheduleRequest> commandSchedules;
    
    @JsonProperty("programSchedules")
    private List<ProgramScheduleRequest> programSchedules;
    
    // 构造函数
    public SimplifiedScheduleRequest() {}
    
    public SimplifiedScheduleRequest(Integer terminalGroupId, Boolean toChildren, Integer terminalId,
                                   List<CommandScheduleRequest> commandSchedules, List<ProgramScheduleRequest> programSchedules) {
        this.terminalGroupId = terminalGroupId;
        this.toChildren = toChildren;
        this.terminalId = terminalId;
        this.commandSchedules = commandSchedules;
        this.programSchedules = programSchedules;
    }
    
    // Getter和Setter方法
    public Integer getTerminalGroupId() {
        return terminalGroupId;
    }
    
    public void setTerminalGroupId(Integer terminalGroupId) {
        this.terminalGroupId = terminalGroupId;
    }
    
    public Boolean getToChildren() {
        return toChildren;
    }
    
    public void setToChildren(Boolean toChildren) {
        this.toChildren = toChildren;
    }
    
    public Integer getTerminalId() {
        return terminalId;
    }
    
    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }
    
    public List<CommandScheduleRequest> getCommandSchedules() {
        return commandSchedules;
    }
    
    public void setCommandSchedules(List<CommandScheduleRequest> commandSchedules) {
        this.commandSchedules = commandSchedules;
    }
    
    public List<ProgramScheduleRequest> getProgramSchedules() {
        return programSchedules;
    }
    
    public void setProgramSchedules(List<ProgramScheduleRequest> programSchedules) {
        this.programSchedules = programSchedules;
    }
    
    /**
     * 指令排程请求参数
     */
    public static class CommandScheduleRequest {
        @JsonProperty("startDate")
        private String startDate;
        
        @JsonProperty("endDate")
        private String endDate;
        
        @JsonProperty("operationTime")
        private String operationTime;
        
        @JsonProperty("weeks")
        private List<Boolean> weeks;
        
        @JsonProperty("commandType")
        private String commandType;
        
        @JsonProperty("value")
        private String value;
        
        public CommandScheduleRequest() {}
        
        public CommandScheduleRequest(String startDate, String endDate, String operationTime,
                                    List<Boolean> weeks, String commandType, String value) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.operationTime = operationTime;
            this.weeks = weeks;
            this.commandType = commandType;
            this.value = value;
        }
        
        // Getter和Setter方法
        public String getStartDate() {
            return startDate;
        }
        
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
        
        public String getEndDate() {
            return endDate;
        }
        
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
        
        public String getOperationTime() {
            return operationTime;
        }
        
        public void setOperationTime(String operationTime) {
            this.operationTime = operationTime;
        }
        
        public List<Boolean> getWeeks() {
            return weeks;
        }
        
        public void setWeeks(List<Boolean> weeks) {
            this.weeks = weeks;
        }
        
        public String getCommandType() {
            return commandType;
        }
        
        public void setCommandType(String commandType) {
            this.commandType = commandType;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
    }
    
    /**
     * 节目排程请求参数
     */
    public static class ProgramScheduleRequest {
        @JsonProperty("startDate")
        private String startDate;
        
        @JsonProperty("endDate")
        private String endDate;
        
        @JsonProperty("startTime")
        private String startTime;
        
        @JsonProperty("endTime")
        private String endTime;
        
        @JsonProperty("weeks")
        private List<Boolean> weeks;
        
        @JsonProperty("programPlayForm")
        private Integer programPlayForm;
        
        @JsonProperty("programId")
        private Integer programId;
        
        @JsonProperty("programPriority")
        private Integer programPriority;
        
        public ProgramScheduleRequest() {}
        
        public ProgramScheduleRequest(String startDate, String endDate, String startTime, String endTime,
                                    List<Boolean> weeks, Integer programPlayForm, Integer programId, Integer programPriority) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.weeks = weeks;
            this.programPlayForm = programPlayForm;
            this.programId = programId;
            this.programPriority = programPriority;
        }
        
        // Getter和Setter方法
        public String getStartDate() {
            return startDate;
        }
        
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
        
        public String getEndDate() {
            return endDate;
        }
        
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
        
        public String getStartTime() {
            return startTime;
        }
        
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
        
        public String getEndTime() {
            return endTime;
        }
        
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
        
        public List<Boolean> getWeeks() {
            return weeks;
        }
        
        public void setWeeks(List<Boolean> weeks) {
            this.weeks = weeks;
        }
        
        public Integer getProgramPlayForm() {
            return programPlayForm;
        }
        
        public void setProgramPlayForm(Integer programPlayForm) {
            this.programPlayForm = programPlayForm;
        }
        
        public Integer getProgramId() {
            return programId;
        }
        
        public void setProgramId(Integer programId) {
            this.programId = programId;
        }
        
        public Integer getProgramPriority() {
            return programPriority;
        }
        
        public void setProgramPriority(Integer programPriority) {
            this.programPriority = programPriority;
        }
    }
    
    /**
     * 指令类型枚举
     */
    public enum CommandScheduleType {
        BOARD_RELAY("BOARD_RELAY"),
        SWITCH_SIGNAL_SOURCE("SWITCH_SIGNAL_SOURCE"),
        RELAY("RELAY"),
        VOLUME("VOLUME"),
        BRIGHTNESS("BRIGHTNESS"),
        COLORTEMP("COLORTEMP"),
        SLEEP("SLEEP"),
        WAKEUP("WAKEUP"),
        REBOOT("REBOOT"),
        CLEAR_CACHE("CLEAR_CACHE");
        
        private final String value;
        
        CommandScheduleType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static CommandScheduleType fromString(String text) {
            for (CommandScheduleType type : CommandScheduleType.values()) {
                if (type.value.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
} 