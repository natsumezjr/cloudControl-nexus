package com.cloudcontrol.repository.relation;

import com.cloudcontrol.entity.relation.UserTerminalGroup;
import com.cloudcontrol.entity.relation.UserTerminalGroupId;
import com.cloudcontrol.entity.terminal.TerminalGroup;
import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户终端组关联数据访问层
 * 提供用户终端组关联相关的数据库操作
 * 
 * 注意：当前仅测试User功能，此Repository暂时注释
 */
/*
@Repository
public interface UserTerminalGroupRepository extends JpaRepository<UserTerminalGroup, UserTerminalGroupId> {
    
    // 所有方法暂时注释，避免影响User测试
    
}
*/ 