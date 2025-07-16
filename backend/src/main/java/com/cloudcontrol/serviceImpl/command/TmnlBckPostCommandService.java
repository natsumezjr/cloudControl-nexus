package com.cloudcontrol.serviceImpl.command;

import com.cloudcontrol.dto.command.Tmnl2BckCommandPostRequest;
import com.cloudcontrol.dto.command.Bck2TmnlCommandPostResponse;
import com.cloudcontrol.repository.command.CommandRepository;
import com.cloudcontrol.entity.command.Command;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TmnlBckPostCommandService {
    private CommandRepository commandRepository;

    public TmnlBckPostCommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public Bck2TmnlCommandPostResponse handleCommand(Tmnl2BckCommandPostRequest commandRequest) {
        Integer commandId = commandRequest.getCommandId();
        String content = commandRequest.getContent();
        System.out.println(content);

        // 根据commandId获取指令
        Command command = commandRepository.findById(commandId).orElse(null);
        if (command == null) {
            return new Bck2TmnlCommandPostResponse(null, HttpStatus.NOT_FOUND);
        }
        else {
            // 终端确认后，删除指令
            commandRepository.deleteById(commandId);
            return new Bck2TmnlCommandPostResponse(null, HttpStatus.OK);
        }
    }
}
