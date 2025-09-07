// backend/src/main/java/com/cloudcontrol/serviceImpl/command/TmnlBckGetCommandServiceImpl.java

package com.cloudcontrol.serviceImpl.command;

import com.cloudcontrol.dto.command.Tmnl2BckCommandGetRequest;
import com.cloudcontrol.repository.command.CommandRepository;
import com.cloudcontrol.dto.command.Bck2TmnlCommandGetResponse;
import com.cloudcontrol.service.command.TmnlBckGetCommandService;
import com.cloudcontrol.entity.command.Command;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import com.cloudcontrol.util.RequestContextUtil;
import java.util.List;
import java.util.Optional;
import com.cloudcontrol.enums.command.CommandType;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

@Service
public class TmnlBckGetCommandServiceImpl implements TmnlBckGetCommandService<Tmnl2BckCommandGetRequest, Bck2TmnlCommandGetResponse> {
    private CommandRepository commandRepository;
    private TerminalRepository terminalRepository;

    public TmnlBckGetCommandServiceImpl(CommandRepository commandRepository, TerminalRepository terminalRepository) {
        this.commandRepository = commandRepository;
        this.terminalRepository = terminalRepository;
    }

    @Override
    public List<Bck2TmnlCommandGetResponse> handleCommand(Tmnl2BckCommandGetRequest commandRequest) {
        String cltType = commandRequest.getCltType();
        System.out.println("收到cltType: " + cltType);
        List<Bck2TmnlCommandGetResponse> resultList = new ArrayList<>();
        if (cltType == null || !cltType.equals("terminal")) {
            System.out.println("cltType不为terminal，直接返回空数组");
            return resultList;
        }
        String deviceNum = commandRequest.getDeviceNum();
        System.out.println("收到deviceNum: " + deviceNum);
        try {
            Optional<Terminal> terminalOpt = null;
            if (deviceNum != null && !deviceNum.trim().isEmpty()) {
                terminalOpt = terminalRepository.findByDeviceName(deviceNum);
                System.out.println("通过 deviceNum 查找终端: " + deviceNum);
            }
            if (terminalOpt == null || !terminalOpt.isPresent()) {
                System.out.println("通过 deviceNum 未找到终端，尝试使用认证信息查找");
                
                // 尝试从请求上下文中获取已认证的终端信息
                Optional<Terminal> authenticatedTerminalOpt = RequestContextUtil.getAuthenticatedTerminal();
                if (authenticatedTerminalOpt.isPresent()) {
                    // 直接修改并保存原终端的设备名称
                    Terminal originalTerminal = authenticatedTerminalOpt.get();
                    originalTerminal.setDeviceName(deviceNum);
                    terminalRepository.save(originalTerminal);
                    terminalOpt = Optional.of(originalTerminal);
                    System.out.println("使用认证信息找到终端账号: " + authenticatedTerminalOpt.get().getAccountName() + 
                                     "，更新设备名称为: " + deviceNum);
                } else {
                    System.out.println("请求上下文中也没有认证终端信息，返回空数组");
                    return resultList;
                }
            }
            Terminal terminal = terminalOpt.get();
            System.out.println("找到终端: " + terminal.getSerialNo());
            List<Command> commands = commandRepository.findByTerminalId(terminal.getSerialNo());
            System.out.println("查到指令数量: " + (commands == null ? 0 : commands.size()));
            if (commands == null || commands.isEmpty()) {
                System.out.println("无指令，返回空数组");
                return resultList;
            }
            for (Command command : commands) {
                int cmdNum = command.getCmdNum();
                System.out.println("处理cmdNum: " + cmdNum);
                CommandType cmdEnum;
                try {
                    cmdEnum = CommandType.fromCmdNum(cmdNum);
                } catch (Exception e) {
                    System.out.println("未识别cmdNum: " + cmdNum);
                    e.printStackTrace();
                    continue;
                }
                Bck2TmnlCommandGetResponse item = new Bck2TmnlCommandGetResponse();
                item.setId(command.getId());
                item.setTerminalId(command.getTerminalId());
                item.setAuthorUrl(cmdEnum.getAuthorUrl());
                item.setKarma(cmdEnum.getKarma());
                item.setCreatedAt(command.getCreatedAt());
                Bck2TmnlCommandGetResponse.Content content = new Bck2TmnlCommandGetResponse.Content();
                try {
                    content.setRaw(cmdEnum.buildRaw(Optional.ofNullable(command.getValue())));
                } catch (Exception e) {
                    System.out.println("buildRaw异常: cmdNum=" + cmdNum + ", id=" + command.getId());
                    e.printStackTrace();
                    content.setRaw("{}");
                }
                item.setContent(content);
                resultList.add(item);
                System.out.println("已添加到resultList: id=" + item.getId());
            }
        } catch (Exception e) {
            System.out.println("handleCommand主流程异常");
            e.printStackTrace();
            return new ArrayList<>();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String respJson = mapper.writeValueAsString(resultList);
            System.out.println("返回给终端的resp: " + respJson);
        } catch (Exception e) {
            System.out.println("resp序列化为json失败");
            e.printStackTrace();
        }
        System.out.println("最终返回resultList size: " + resultList.size());
        return resultList;
    }
}