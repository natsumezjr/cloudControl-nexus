package com.cloudcontrol.repository.program;

import com.cloudcontrol.entity.program.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

/**
 * 节目Repository
 */
@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    
    /**
     * 根据标题查找节目
     */
    Optional<Program> findByTitle(String title);
    
    /**
     * 根据状态查找节目
     */
    List<Program> findByStatus(String status);
    
    /**
     * 根据状态分页查找节目
     */
    Page<Program> findByStatus(String status, Pageable pageable);
    
    /**
     * 根据作者查找节目
     */
    List<Program> findByAuthor(Integer author);
    
    /**
     * 根据作者分页查找节目
     */
    Page<Program> findByAuthor(Integer author, Pageable pageable);
    
    /**
     * 根据标题模糊查询
     */
    @Query("SELECT p FROM Program p WHERE p.title LIKE %:title%")
    List<Program> findByTitleContaining(@Param("title") String title);
    
    /**
     * 根据标题模糊查询分页
     */
    @Query("SELECT p FROM Program p WHERE p.title LIKE %:search%")
    Page<Program> findByTitleContaining(@Param("search") String search, Pageable pageable);
    
    /**
     * 根据状态和作者查找节目
     */
    List<Program> findByStatusAndAuthor(String status, Integer author);
    
    /**
     * 根据状态和作者分页查找节目
     */
    Page<Program> findByStatusAndAuthor(String status, Integer author, Pageable pageable);
    
    /**
     * 根据修改时间范围查找节目
     */
    @Query("SELECT p FROM Program p WHERE p.updatedAt >= :after AND p.updatedAt <= :before")
    Page<Program> findByUpdatedAtBetween(@Param("after") LocalDateTime after, 
                                        @Param("before") LocalDateTime before, 
                                        Pageable pageable);
    
    /**
     * 根据修改时间之后查找节目
     */
    @Query("SELECT p FROM Program p WHERE p.updatedAt >= :after")
    Page<Program> findByUpdatedAtAfter(@Param("after") LocalDateTime after, Pageable pageable);
    
    /**
     * 根据修改时间之前查找节目
     */
    @Query("SELECT p FROM Program p WHERE p.updatedAt <= :before")
    Page<Program> findByUpdatedAtBefore(@Param("before") LocalDateTime before, Pageable pageable);
    
    /**
     * 复杂查询：支持多个条件组合
     */
    @Query("SELECT p FROM Program p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:author IS NULL OR p.author = :author) AND " +
           "(:search IS NULL OR p.title LIKE %:search%) AND " +
           "(:after IS NULL OR p.updatedAt >= :after) AND " +
           "(:before IS NULL OR p.updatedAt <= :before)")
    Page<Program> findByConditions(@Param("status") String status,
                                  @Param("author") Integer author,
                                  @Param("search") String search,
                                  @Param("after") LocalDateTime after,
                                  @Param("before") LocalDateTime before,
                                  Pageable pageable);
    
    /**
     * 检查标题是否存在
     */
    boolean existsByTitle(String title);
} 