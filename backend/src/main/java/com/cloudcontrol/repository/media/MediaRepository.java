package com.cloudcontrol.repository.media;

import com.cloudcontrol.entity.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 素材Repository
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    
    /**
     * 根据文件路径查找素材
     */
    Media findByFilePath(String filePath);
    
    /**
     * 根据GUID查找素材
     */
    Media findByGuid(String guid);
    
    /**
     * 根据作者ID查找素材
     */
    java.util.List<Media> findByAuthor(Integer author);
    
    /**
     * 根据媒体类型查找素材
     */
    java.util.List<Media> findByMediaType(String mediaType);
    
    /**
     * 根据文件类型查找素材
     */
    java.util.List<Media> findByFileType(String fileType);
} 