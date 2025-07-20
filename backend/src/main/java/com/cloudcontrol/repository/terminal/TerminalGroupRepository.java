package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.TerminalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalGroupRepository extends JpaRepository<TerminalGroup, Long> {
    
    /**
     * 根据名称查找终端组
     */
    Optional<TerminalGroup> findByName(String name);
    
    /**
     * 查找所有启用的终端组
     */
    List<TerminalGroup> findByEnabledTrue();
    
    /**
     * 查找根级终端组（没有父级的）
     */
    List<TerminalGroup> findByParentIsNull();
    
    /**
     * 根据父级ID查找子终端组
     */
    List<TerminalGroup> findByParentId(Long parentId);
    
    /**
     * 根据父级ID查找启用的子终端组
     */
    List<TerminalGroup> findByParentIdAndEnabledTrue(Long parentId);
} 