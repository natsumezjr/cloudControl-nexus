package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.TerminalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 终端组数据访问层
 * 提供终端组相关的数据库操作
 * 
 * 注意：当前仅测试User功能，此Repository暂时注释
 */
/*
@Repository
public interface TerminalGroupRepository extends JpaRepository<TerminalGroup, Long> {
    
    // 所有方法暂时注释，避免影响User测试
    
}
*/ 