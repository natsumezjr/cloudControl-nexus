package com.cloudcontrol.entity.program;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 节目实体类
 */
@Entity
@Table(name = "programs")
public class Program {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "program_info", columnDefinition = "TEXT")
    private String programInfo;
    
    @Column(name = "programs_data", columnDefinition = "TEXT")
    private String programsData;
    
    @Column(name = "author")
    private Integer author;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "vsn_name")
    private String vsnName;
    
    @Column(name = "slug")
    private String slug;
    
    public Program() {}
    
    public Program(String title, String status, String programInfo, String programsData, Integer author) {
        this.title = title;
        this.status = status;
        this.programInfo = programInfo;
        this.programsData = programsData;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getProgramInfo() { return programInfo; }
    public void setProgramInfo(String programInfo) { this.programInfo = programInfo; }
    
    public String getProgramsData() { return programsData; }
    public void setProgramsData(String programsData) { this.programsData = programsData; }
    
    public Integer getAuthor() { return author; }
    public void setAuthor(Integer author) { this.author = author; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getVsnName() { return vsnName; }
    public void setVsnName(String vsnName) { this.vsnName = vsnName; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
} 