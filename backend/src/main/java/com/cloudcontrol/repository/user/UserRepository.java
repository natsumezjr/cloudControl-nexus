package com.cloudcontrol.repository.user;

import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.user.UserRole;
import com.cloudcontrol.enums.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问层
 * 提供用户相关的数据库操作
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据用户名或邮箱查找用户
     */
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 根据用户状态查找用户列表
     */
    List<User> findByStatus(UserStatus status);
    
    /**
     * 根据用户角色查找用户列表
     */
    List<User> findByRole(UserRole role);
    
    /**
     * 根据终端组ID查找用户列表
     */
    List<User> findByTermId(Long termId);
    
    /**
     * 根据创建时间范围查找用户列表
     */
    List<User> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据最后登录时间查找活跃用户
     */
    List<User> findByLastLoginAfter(LocalDateTime time);
    
    /**
     * 查找指定角色且状态为激活的用户
     */
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    
    /**
     * 根据用户名模糊查询
     */
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    /**
     * 根据昵称模糊查询
     */
    List<User> findByNicknameContainingIgnoreCase(String nickname);
    
    /**
     * 查找分配空间大于指定值的用户
     */
    List<User> findByAssignedSizeGreaterThan(Long size);
    
    /**
     * 统计指定角色的用户数量
     */
    long countByRole(UserRole role);
    
    /**
     * 统计指定状态的用户数量
     */
    long countByStatus(UserStatus status);
    
    /**
     * 统计指定终端组的用户数量
     */
    long countByTermId(Long termId);
    
    /**
     * 查找最近登录的用户（按最后登录时间倒序）
     */
    @Query("SELECT u FROM User u ORDER BY u.lastLogin DESC")
    List<User> findRecentActiveUsers();
    
    /**
     * 查找指定时间范围内注册的用户
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findUsersRegisteredBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    /**
     * 查找指定终端组且角色为指定角色的用户
     */
    @Query("SELECT u FROM User u WHERE u.termId = :termId AND u.role = :role")
    List<User> findUsersByTermIdAndRole(@Param("termId") Long termId, @Param("role") UserRole role);
    
    /**
     * 查找用户名或邮箱包含指定关键字的用户
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> findUsersByKeyword(@Param("keyword") String keyword);
    
    /**
     * 查找分配空间使用率超过指定百分比的用户
     */
    @Query("SELECT u FROM User u WHERE u.assignedSize > :minSize")
    List<User> findUsersWithLargeStorage(@Param("minSize") Long minSize);
    
    /**
     * 统计指定时间范围内注册的用户数量
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    long countUsersRegisteredBetween(@Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);
    
    /**
     * 查找指定角色且最后登录时间在指定时间之后的用户
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.lastLogin > :time")
    List<User> findActiveUsersByRole(@Param("role") UserRole role, @Param("time") LocalDateTime time);
} 