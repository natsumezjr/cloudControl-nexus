package com.cloudcontrol.repository.command;

import com.cloudcontrol.entity.command.CommandResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 指令结果数据访问层
 * 提供指令结果相关的数据库操作
 * 
 * 注意：当前仅测试User功能，此Repository暂时注释
 */
/*
@Repository
public interface CommandResultRepository extends JpaRepository<CommandResult, Long> {
    
    // 所有方法暂时注释，避免影响User测试
    
}
*/ 