package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.entity.terminal.TerminalGroup;
import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.terminal.TerminalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 终端数据访问层
 * 提供终端相关的数据库操作
 * 
 * 注意：当前仅测试User功能，此Repository暂时注释
 */
/*
@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    
    // 所有方法暂时注释，避免影响User测试
    
}
*/ 