package com.cloudcontrol.entity.relation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * 用户终端组关系复合主键类
 */
@Embeddable
public class UserTerminalGroupId implements Serializable {
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "terminal_group_id")
    private Long terminalGroupId;
    
    // 构造方法
    public UserTerminalGroupId() {}
    
    public UserTerminalGroupId(Long userId, Long terminalGroupId) {
        this.userId = userId;
        this.terminalGroupId = terminalGroupId;
    }
    
    // Getter和Setter方法
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getTerminalGroupId() {
        return terminalGroupId;
    }
    
    public void setTerminalGroupId(Long terminalGroupId) {
        this.terminalGroupId = terminalGroupId;
    }
    
    /**
     * 重写equals方法
     * 
     * 必要性：
     * 1. 复合主键类必须重写equals方法，确保JPA能正确识别主键对象
     * 2. 用于HashMap、HashSet等集合的正确工作
     * 3. 确保两个复合主键对象在字段值相同时被认为是相等的
     */
    @Override
    public boolean equals(Object o) {
        // 如果是同一个对象，直接返回true
        if (this == o) return true;
        // 如果对象为null或类型不匹配，返回false
        if (o == null || getClass() != o.getClass()) return false;
        
        UserTerminalGroupId that = (UserTerminalGroupId) o;
        
        // 逐个比较复合主键的每个字段
        // 使用null安全的方式比较userId字段
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        // 比较terminalGroupId字段，只有两个字段都相等才返回true
        return terminalGroupId != null ? terminalGroupId.equals(that.terminalGroupId) : that.terminalGroupId == null;
    }
    
    /**
     * 重写hashCode方法
     * 
     * 必要性：
     * 1. 复合主键类必须重写hashCode方法，与equals方法保持一致
     * 2. 确保相等的对象有相同的哈希值，用于HashMap等集合的正确工作
     * 3. 使用31作为乘数（质数），减少哈希冲突
     */
    @Override
    public int hashCode() {
        // 计算第一个字段的哈希值
        int result = userId != null ? userId.hashCode() : 0;
        // 使用31作为乘数，加上第二个字段的哈希值
        // 31是质数，能有效减少哈希冲突
        result = 31 * result + (terminalGroupId != null ? terminalGroupId.hashCode() : 0);
        return result;
    }
} 