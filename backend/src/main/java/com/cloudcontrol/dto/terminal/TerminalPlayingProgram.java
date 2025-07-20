package com.cloudcontrol.dto.terminal;

/**
 * 终端正在播放的节目DTO
 */
public class TerminalPlayingProgram {
    
    private String name;
    private String type;
    
    public TerminalPlayingProgram() {}
    
    public TerminalPlayingProgram(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    // getter/setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
} 