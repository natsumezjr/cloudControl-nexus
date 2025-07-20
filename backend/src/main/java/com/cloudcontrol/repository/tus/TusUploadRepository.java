package com.cloudcontrol.repository.tus;

import com.cloudcontrol.entity.tus.TusUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Tus上传文件Repository
 */
@Repository
public interface TusUploadRepository extends JpaRepository<TusUpload, Long> {
    
    /**
     * 根据上传ID查找
     */
    Optional<TusUpload> findByUploadId(String uploadId);
    
    /**
     * 根据MD5校验和查找已完成的文件
     */
    @Query("SELECT t FROM TusUpload t WHERE t.checksum = :checksum AND t.status = 'COMPLETED'")
    Optional<TusUpload> findByChecksumAndCompleted(@Param("checksum") String checksum);
    
    /**
     * 根据MD5校验和查找文件（包括未完成的）
     */
    Optional<TusUpload> findByChecksum(String checksum);
    
    /**
     * 检查是否存在指定校验和的文件
     */
    boolean existsByChecksum(String checksum);
    
    /**
     * 根据上传ID删除
     */
    void deleteByUploadId(String uploadId);
} 