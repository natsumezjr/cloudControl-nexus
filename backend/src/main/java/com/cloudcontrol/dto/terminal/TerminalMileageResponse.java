package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 终端里程数查询响应DTO
 */
public class TerminalMileageResponse {
    
    private List<Integer> terminalIds;
    private Float totalMileage;
    private String startTime;
    private String endTime;
    private List<MileageData> data;
    
    public TerminalMileageResponse() {}
    
    public TerminalMileageResponse(List<Integer> terminalIds, Float totalMileage, String startTime, 
                                 String endTime, List<MileageData> data) {
        this.terminalIds = terminalIds;
        this.totalMileage = totalMileage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.data = data;
    }
    
    // getter/setter
    public List<Integer> getTerminalIds() { return terminalIds; }
    public void setTerminalIds(List<Integer> terminalIds) { this.terminalIds = terminalIds; }
    
    public Float getTotalMileage() { return totalMileage; }
    public void setTotalMileage(Float totalMileage) { this.totalMileage = totalMileage; }
    
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    
    public List<MileageData> getData() { return data; }
    public void setData(List<MileageData> data) { this.data = data; }
    
    /**
     * 里程数据内部类
     */
    public static class MileageData {
        private Integer terminalId;
        private String terminalName;
        private Float totalMileage;
        
        public MileageData() {}
        
        public MileageData(Integer terminalId, String terminalName, Float totalMileage) {
            this.terminalId = terminalId;
            this.terminalName = terminalName;
            this.totalMileage = totalMileage;
        }
        
        // getter/setter
        public Integer getTerminalId() { return terminalId; }
        public void setTerminalId(Integer terminalId) { this.terminalId = terminalId; }
        
        public String getTerminalName() { return terminalName; }
        public void setTerminalName(String terminalName) { this.terminalName = terminalName; }
        
        public Float getTotalMileage() { return totalMileage; }
        public void setTotalMileage(Float totalMileage) { this.totalMileage = totalMileage; }
    }
} 