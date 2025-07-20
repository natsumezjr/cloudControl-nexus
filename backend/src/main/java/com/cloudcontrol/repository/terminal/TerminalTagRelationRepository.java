package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.TerminalTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 终端标签关系Repository
 */
@Repository
public interface TerminalTagRelationRepository extends JpaRepository<TerminalTagRelation, Integer> {
    
    /**
     * 根据终端ID查询关系
     */
    List<TerminalTagRelation> findByTerminalId(Integer terminalId);
    
    /**
     * 根据标签ID查询关系
     */
    List<TerminalTagRelation> findByTagId(Integer tagId);
    
    /**
     * 根据终端ID和标签ID查询关系
     */
    TerminalTagRelation findByTerminalIdAndTagId(Integer terminalId, Integer tagId);
    
    /**
     * 根据终端ID删除关系
     */
    void deleteByTerminalId(Integer terminalId);
    
    /**
     * 根据终端ID和标签ID删除关系
     */
    void deleteByTerminalIdAndTagId(Integer terminalId, Integer tagId);
    
    /**
     * 根据标签ID列表查询终端ID
     */
    @Query("SELECT DISTINCT r.terminalId FROM TerminalTagRelation r WHERE r.tagId IN :tagIds")
    List<Integer> findTerminalIdsByTagIds(@Param("tagIds") List<Integer> tagIds);
    
    /**
     * 根据终端ID列表查询标签ID
     */
    @Query("SELECT DISTINCT r.tagId FROM TerminalTagRelation r WHERE r.terminalId IN :terminalIds")
    List<Integer> findTagIdsByTerminalIds(@Param("terminalIds") List<Integer> terminalIds);
} 