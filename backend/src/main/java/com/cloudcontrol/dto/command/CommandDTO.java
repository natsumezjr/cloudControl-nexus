package com.cloudcontrol.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

public class CommandDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("post")
    private Long terminalId;

    @JsonProperty("author_url")
    private String authorUrl;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("karma")
    private Integer karma;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

    public String getAuthorUrl() { return authorUrl; }
    public void setAuthorUrl(String authorUrl) { this.authorUrl = authorUrl; }

    public Content getContent() { return content; }
    public void setContent(Content content) { this.content = content; }

    public Integer getKarma() { return karma; }
    public void setKarma(Integer karma) { this.karma = karma; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public static class Content {
        @JsonProperty("raw")
        private String raw;

        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }
    }
} 