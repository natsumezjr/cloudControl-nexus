package com.cloudcontrol.repository.command;

import com.cloudcontrol.entity.command.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    // 按终端ID查询所有指令
    List<Command> findByTerminalId(Long terminalId);

    // 按指令类型查询
    List<Command> findByAuthorUrl(String authorUrl);

    // 按执行方式查询
    List<Command> findByKarma(Integer karma);
} 