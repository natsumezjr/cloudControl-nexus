package com.cloudcontrol.service.terminal;

import com.cloudcontrol.dto.terminal.TerminalListResponse;
import com.cloudcontrol.dto.terminal.TerminalListRequest;
import com.cloudcontrol.dto.terminal.TerminalCreateRequest;
import com.cloudcontrol.dto.terminal.TerminalCreateResponse;
import com.cloudcontrol.dto.terminal.TerminalUpdateRequest;
import com.cloudcontrol.dto.terminal.TerminalUpdateResponse;
import com.cloudcontrol.dto.terminal.TerminalDeleteResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestMultipleRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsV2Response;
import com.cloudcontrol.dto.terminal.TerminalTrackRequest;
import com.cloudcontrol.dto.terminal.TerminalTrackResponse;
import com.cloudcontrol.dto.terminal.TerminalMileageRequest;
import com.cloudcontrol.dto.terminal.TerminalMileageResponse;
import java.util.List;

/**
 * 终端服务接口
 */
public interface TerminalService {
    
    /**
     * 根据终端ID列表获取终端列表
     * @param terminalIds 终端ID列表
     * @return 终端列表
     */
    List<TerminalListResponse> getTerminalList(List<Integer> terminalIds);
    
    /**
     * 根据节目ID获取终端列表
     * @param programId 节目ID
     * @param page 页码
     * @param perPage 每页数量
     * @return 终端列表响应
     */
    TerminalListResponse getTerminalsByProgram(Integer programId, Integer page, Integer perPage);
    
    /**
     * 获取终端列表
     * @param request 请求参数
     * @return 终端列表响应
     */
    TerminalListResponse getTerminalList(TerminalListRequest request);
    
    /**
     * 根据GPS获取终端列表
     * @param terminalGroup 终端组ID
     * @return GPS终端列表
     */
    List<TerminalGpsResponse> getGpsTerminalList(Integer terminalGroup);
    
    /**
     * 获取最新GPS信息（单个）
     * @param request 请求参数
     * @return GPS最新信息响应
     */
    TerminalGpsLatestResponse getLatestGpsSingle(TerminalGpsLatestRequest request);
    
    /**
     * 获取最新GPS信息（多个）
     * @param request 请求参数
     * @return GPS最新信息响应列表
     */
    List<TerminalGpsLatestResponse> getLatestGpsMultiple(TerminalGpsLatestMultipleRequest request);
    
    /**
     * 获取最新GPS信息V2
     * @param terminalId 终端ID
     * @return GPS V2响应
     */
    TerminalGpsV2Response getLatestGpsV2(Integer terminalId);
    
    /**
     * 根据终端ID获取终端信息
     * @param request 请求参数
     * @return 终端列表响应
     */
    TerminalListResponse getTerminalById(TerminalListRequest request);
    
    /**
     * 根据终端ID列表获取终端信息
     * @param request 请求参数
     * @return 终端列表响应
     */
    TerminalListResponse getTerminalsByIds(TerminalListRequest request);
    
    /**
     * 创建终端
     * @param request 创建请求
     * @return 创建响应
     */
    TerminalCreateResponse createTerminalWithAccount(TerminalCreateRequest request);
    
    /**
     * 更新终端
     * @param request 更新请求
     * @return 更新响应
     */
    TerminalUpdateResponse updateTerminal(TerminalUpdateRequest request);
    
    /**
     * 删除终端
     * @param terminalId 终端ID
     * @return 删除响应
     */
    TerminalDeleteResponse deleteTerminal(Integer terminalId);
    
    /**
     * 获取终端轨迹
     * @param request 轨迹请求
     * @return 轨迹响应
     */
    TerminalTrackResponse getTerminalTrack(TerminalTrackRequest request);
    
    /**
     * 获取终端里程
     * @param request 里程请求
     * @return 里程响应
     */
    TerminalMileageResponse getTerminalMileage(TerminalMileageRequest request);
} 