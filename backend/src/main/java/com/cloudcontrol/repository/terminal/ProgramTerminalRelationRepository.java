package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.ProgramTerminalRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 节目终端关系Repository
 */
@Repository
public interface ProgramTerminalRelationRepository extends JpaRepository<ProgramTerminalRelation, Integer> {
    
    /**
     * 根据节目ID查询关系
     */
    List<ProgramTerminalRelation> findByProgramId(Integer programId);
    
    /**
     * 根据节目ID分页查询关系
     */
    Page<ProgramTerminalRelation> findByProgramId(Integer programId, Pageable pageable);
    
    /**
     * 根据终端ID查询关系
     */
    List<ProgramTerminalRelation> findByTerminalId(Integer terminalId);
    
    /**
     * 根据节目ID和终端ID查询关系
     */
    ProgramTerminalRelation findByProgramIdAndTerminalId(Integer programId, Integer terminalId);
    
    /**
     * 根据节目ID查询终端ID列表
     */
    @Query("SELECT r.terminalId FROM ProgramTerminalRelation r WHERE r.programId = :programId")
    List<Integer> findTerminalIdsByProgramId(@Param("programId") Integer programId);
    
    /**
     * 根据节目ID和发布状态查询关系
     */
    List<ProgramTerminalRelation> findByProgramIdAndPublishStatus(Integer programId, String publishStatus);
    
    /**
     * 根据节目ID删除关系
     */
    void deleteByProgramId(Integer programId);
    
    /**
     * 根据终端ID删除关系
     */
    void deleteByTerminalId(Integer terminalId);
    
    /**
     * 根据节目ID和终端ID删除关系
     */
    void deleteByProgramIdAndTerminalId(Integer programId, Integer terminalId);
} 