package com.cloudcontrol.service.command;

import com.cloudcontrol.dto.command.CommandRequest;
import com.cloudcontrol.dto.command.CommandResponse;

import java.util.List;

public interface TmnlBckGetCommandService<T extends CommandRequest, R extends CommandResponse> {
    List<R> handleCommand(T commandRequest);
}
