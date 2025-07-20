package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.TerminalTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 终端标签Repository
 */
@Repository
public interface TerminalTagRepository extends JpaRepository<TerminalTag, Integer> {
    
    /**
     * 根据标签名称查找标签
     */
    Optional<TerminalTag> findByTagName(String tagName);
    
    /**
     * 根据标签名称列表查找标签
     */
    List<TerminalTag> findByTagNameIn(List<String> tagNames);
    
    /**
     * 分页查询所有标签
     */
    Page<TerminalTag> findAll(Pageable pageable);
    
    /**
     * 根据终端ID查询标签
     */
    @Query("SELECT t FROM TerminalTag t " +
           "JOIN TerminalTagRelation r ON t.tagId = r.tagId " +
           "WHERE r.terminalId = :terminalId")
    List<TerminalTag> findByTerminalId(@Param("terminalId") Integer terminalId);
    
    /**
     * 根据终端ID分页查询标签
     */
    @Query("SELECT t FROM TerminalTag t " +
           "JOIN TerminalTagRelation r ON t.tagId = r.tagId " +
           "WHERE r.terminalId = :terminalId")
    Page<TerminalTag> findByTerminalId(@Param("terminalId") Integer terminalId, Pageable pageable);
} 