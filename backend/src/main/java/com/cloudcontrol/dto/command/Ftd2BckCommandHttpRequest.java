package com.cloudcontrol.dto.command;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public class Ftd2BckCommandHttpRequest implements CommandRequest{
    @JsonProperty("terminalIds")
    private ArrayList<Integer> terminalIds;


    @JsonProperty("value")
    private Optional<Object> value;

    // getter/setter
    public ArrayList<Integer> getTerminalIds() {
        return terminalIds;
    }

    public void setTerminalIds(ArrayList<Integer> terminalIds) {
        this.terminalIds = terminalIds;
    }

    public Optional<Object> getValue() {
        return value;
    }

    public void setValue(Optional<Object> value) {
        this.value = value;
    }    
}
