package com.cloudcontrol.service.command;

import com.cloudcontrol.dto.command.CommandRequest;
import com.cloudcontrol.dto.command.CommandResponse;



public interface FtdBckCommandService<T extends CommandRequest, R extends CommandResponse> {
    R handleCommand(T commandRequest, String cmdType);
}
