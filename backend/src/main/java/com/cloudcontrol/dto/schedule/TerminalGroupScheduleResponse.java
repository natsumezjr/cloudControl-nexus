package com.cloudcontrol.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 应用终端组排程响应DTO
 */
public class TerminalGroupScheduleResponse {
    
    @JsonProperty("parent")
    private Integer parent;
    
    @JsonProperty("schedule_time")
    private Long schedule_time;
    
    @JsonProperty("meta")
    private String meta;
    
    @JsonProperty("members")
    private Integer members;
    
    @JsonProperty("count")
    private Integer count;
    
    @JsonProperty("link")
    private String link;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("leds")
    private List<LedInfo> leds;
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("taxonomy")
    private String taxonomy;
    
    @JsonProperty("slug")
    private String slug;
    
    // 构造函数
    public TerminalGroupScheduleResponse() {}
    
    public TerminalGroupScheduleResponse(Integer parent, Long schedule_time, String meta, 
                                       Integer members, Integer count, String link, String name, 
                                       String description, List<LedInfo> leds, Integer id, 
                                       String taxonomy, String slug) {
        this.parent = parent;
        this.schedule_time = schedule_time;
        this.meta = meta;
        this.members = members;
        this.count = count;
        this.link = link;
        this.name = name;
        this.description = description;
        this.leds = leds;
        this.id = id;
        this.taxonomy = taxonomy;
        this.slug = slug;
    }
    
    // Getter和Setter方法
    public Integer getParent() {
        return parent;
    }
    
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    
    public Long getSchedule_time() {
        return schedule_time;
    }
    
    public void setSchedule_time(Long schedule_time) {
        this.schedule_time = schedule_time;
    }
    
    public String getMeta() {
        return meta;
    }
    
    public void setMeta(String meta) {
        this.meta = meta;
    }
    
    public Integer getMembers() {
        return members;
    }
    
    public void setMembers(Integer members) {
        this.members = members;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<LedInfo> getLeds() {
        return leds;
    }
    
    public void setLeds(List<LedInfo> leds) {
        this.leds = leds;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTaxonomy() {
        return taxonomy;
    }
    
    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    /**
     * 终端信息
     */
    public static class LedInfo {
        @JsonProperty("led_id")
        private Integer led_id;
        
        @JsonProperty("led_name")
        private String led_name;
        
        @JsonProperty("_led_latest_report_time")
        private String _led_latest_report_time;
        
        @JsonProperty("led_description")
        private String led_description;
        
        public LedInfo() {}
        
        public LedInfo(Integer led_id, String led_name, String _led_latest_report_time, String led_description) {
            this.led_id = led_id;
            this.led_name = led_name;
            this._led_latest_report_time = _led_latest_report_time;
            this.led_description = led_description;
        }
        
        public Integer getLed_id() {
            return led_id;
        }
        
        public void setLed_id(Integer led_id) {
            this.led_id = led_id;
        }
        
        public String getLed_name() {
            return led_name;
        }
        
        public void setLed_name(String led_name) {
            this.led_name = led_name;
        }
        
        public String get_led_latest_report_time() {
            return _led_latest_report_time;
        }
        
        public void set_led_latest_report_time(String _led_latest_report_time) {
            this._led_latest_report_time = _led_latest_report_time;
        }
        
        public String getLed_description() {
            return led_description;
        }
        
        public void setLed_description(String led_description) {
            this.led_description = led_description;
        }
    }
} 