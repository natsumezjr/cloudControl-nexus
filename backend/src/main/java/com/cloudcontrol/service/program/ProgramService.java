package com.cloudcontrol.service.program;

import com.cloudcontrol.dto.program.ProgramCreateRequest;
import com.cloudcontrol.dto.program.ProgramResponse;
import com.cloudcontrol.dto.program.ProgramListResponse;
import com.cloudcontrol.dto.program.ProgramPublishRequest;
import com.cloudcontrol.dto.program.ProgramPublishResponse;
import com.cloudcontrol.dto.program.ProgramBatchDeleteRequest;
import com.cloudcontrol.dto.program.ProgramBatchDeleteResponse;
import com.cloudcontrol.dto.program.ProgramUnpublishRequest;

/**
 * 节目服务接口
 */
public interface ProgramService {
    
    /**
     * 创建节目
     * @param request 创建请求
     * @return 节目响应
     */
    ProgramResponse createProgram(ProgramCreateRequest request);
    
    /**
     * 根据ID获取节目
     * @param programId 节目ID
     * @return 节目响应
     */
    ProgramResponse getProgramById(Integer programId);
    
    /**
     * 更新节目
     * @param programId 节目ID
     * @param request 更新请求
     * @return 节目响应
     */
    ProgramResponse updateProgram(Integer programId, ProgramCreateRequest request);
    
    /**
     * 删除节目
     * @param programId 节目ID
     * @return 删除结果
     */
    boolean deleteProgram(Integer programId);
    
    /**
     * 获取节目列表
     * @param page 页码
     * @param perPage 每页数量
     * @param status 状态筛选
     * @param mimeType MIME类型
     * @param author 作者ID
     * @param search 搜索关键词
     * @param orderby 排序方式
     * @param before 修改时间之前
     * @param after 修改时间之后
     * @param terminalGroupId 终端组ID
     * @return 节目列表
     */
    java.util.List<ProgramListResponse> getProgramList(int page, int perPage, String status, 
                                                      String mimeType, Integer author, String search, 
                                                      String orderby, String before, String after, 
                                                      Integer terminalGroupId);
    
    /**
     * 发布节目到终端组
     * @param programId 节目ID
     * @param request 发布请求
     * @param currentUserId 当前用户ID
     * @return 发布响应
     */
    ProgramPublishResponse publishProgram(Integer programId, ProgramPublishRequest request, Integer currentUserId);
    
    /**
     * 批量删除节目
     * @param request 批量删除请求
     * @param currentUserId 当前用户ID
     * @return 删除的节目列表
     */
    java.util.List<ProgramBatchDeleteResponse> batchDeletePrograms(ProgramBatchDeleteRequest request, Integer currentUserId);
    
    /**
     * 删除播放盒节目（取消发布）
     * @param request 取消发布请求
     * @param currentUserId 当前用户ID
     * @return 取消发布的节目信息
     */
    ProgramBatchDeleteResponse unpublishProgram(ProgramUnpublishRequest request, Integer currentUserId);
} 