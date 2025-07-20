package com.cloudcontrol.service.media;

import com.cloudcontrol.dto.media.MediaUploadResponse;
import com.cloudcontrol.dto.media.MediaDeleteResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * 素材服务接口
 */
public interface MediaService {
    
    /**
     * 上传素材文件
     * @param file 上传的文件
     * @return 上传响应
     */
    MediaUploadResponse uploadMedia(MultipartFile file);
    
    /**
     * 根据文件路径查询素材
     * @param filePath 文件路径
     * @return 素材信息
     */
    MediaUploadResponse getMediaByPath(String filePath);
    
    /**
     * 获取素材列表
     * @param page 页码，默认1
     * @param perPage 每页数量，默认12
     * @param flag 筛选标志
     * @return 素材列表
     */
    List<MediaUploadResponse> getMediaList(int page, int perPage, String flag);
    
    /**
     * 根据ID删除素材
     * @param mediaId 素材ID
     * @param force 是否强制删除
     * @return 删除响应，包含被删除的素材信息
     */
    MediaDeleteResponse deleteMediaById(Integer mediaId, boolean force);
} 