package com.cloudcontrol.repository.command;

import com.cloudcontrol.entity.command.Command;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.command.CommandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 指令数据访问层
 * 提供指令相关的数据库操作
 * 
 * 注意：当前仅测试User功能，此Repository暂时注释
 */
/*
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    
    // 所有方法暂时注释，避免影响User测试
    
}
*/ 