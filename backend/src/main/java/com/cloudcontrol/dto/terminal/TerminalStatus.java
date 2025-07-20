package com.cloudcontrol.dto.terminal;

import java.util.List;

/**
 * 终端状态信息DTO
 */
public class TerminalStatus {
    
    private List<TerminalProgramContent> vsns;
    private TerminalPlayingProgram playing;
    private Integer _report_time;
    
    public TerminalStatus() {}
    
    public TerminalStatus(List<TerminalProgramContent> vsns, TerminalPlayingProgram playing, Integer _report_time) {
        this.vsns = vsns;
        this.playing = playing;
        this._report_time = _report_time;
    }
    
    // getter/setter
    public List<TerminalProgramContent> getVsns() { return vsns; }
    public void setVsns(List<TerminalProgramContent> vsns) { this.vsns = vsns; }
    
    public TerminalPlayingProgram getPlaying() { return playing; }
    public void setPlaying(TerminalPlayingProgram playing) { this.playing = playing; }
    
    public Integer get_report_time() { return _report_time; }
    public void set_report_time(Integer _report_time) { this._report_time = _report_time; }
} 