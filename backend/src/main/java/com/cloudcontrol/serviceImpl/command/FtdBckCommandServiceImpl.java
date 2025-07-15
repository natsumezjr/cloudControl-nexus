package com.cloudcontrol.serviceImpl.command;

import com.cloudcontrol.service.command.FtdBckCommandService;
import com.cloudcontrol.dto.command.Ftd2BckCommandHttpRequest;
import com.cloudcontrol.dto.command.Bck2FtdCommandHttpResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
import com.cloudcontrol.repository.command.CommandRepository;
import org.springframework.transaction.annotation.Transactional;
import com.cloudcontrol.entity.command.Command;
import com.cloudcontrol.enums.command.CommandType;
import java.sql.Timestamp;
import org.springframework.http.HttpStatus;

@Service
public class FtdBckCommandServiceImpl implements FtdBckCommandService<Ftd2BckCommandHttpRequest, Bck2FtdCommandHttpResponse> {

    private CommandRepository commandRepository;

    public FtdBckCommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Transactional
    @Override
    public Bck2FtdCommandHttpResponse handleCommand(Ftd2BckCommandHttpRequest commandRequest, String cmdType) {
        ArrayList<Integer> terminalIds = commandRequest.getTerminalIds();
        Optional<Object> value = commandRequest.getValue();

        // 根据cmdType获取指令类型
        CommandType cmd = CommandType.fromCmdType(cmdType);
        /*  
        // 根据终端ID获取终端

        // 根据指令类型对终端字段进行处理，暂不实现

        */
        // 对于每个终端，将指令存储到数据库，redis保存value
        try {
            for (int terminalId : terminalIds) {
                Command command = new Command();
                command.setTerminalId(terminalId);
                command.setValue(value);
                command.setCmdNum(cmd.getCmdNum());
                command.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                commandRepository.save(command);
            }
            Bck2FtdCommandHttpResponse response = new Bck2FtdCommandHttpResponse(null, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            // 返回响应
            Bck2FtdCommandHttpResponse response = new Bck2FtdCommandHttpResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        } 
    }
}