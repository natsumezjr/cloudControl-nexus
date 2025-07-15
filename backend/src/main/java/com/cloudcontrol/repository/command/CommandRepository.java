package com.cloudcontrol.repository.command;
import com.cloudcontrol.entity.command.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {
    // 按终端ID查询所有指令
    List<Command> findByTerminalId(Integer terminalId);

    // 按指令类型编号查询
    List<Command> findByCmdNum(int cmdNum);

    // 按指令ID查询指令编号
    int findCmdNumById(int id);
} 