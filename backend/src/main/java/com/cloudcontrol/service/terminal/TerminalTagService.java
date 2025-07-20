package com.cloudcontrol.service.terminal;

import com.cloudcontrol.dto.terminal.TerminalTagResponse;
import com.cloudcontrol.dto.terminal.TerminalTagAddRequest;
import com.cloudcontrol.dto.terminal.TerminalListResponse;

import java.util.List;

/**
 * 终端标签服务接口
 */
public interface TerminalTagService {
    
    /**
     * 查询所有标签
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 标签列表响应
     */
    TerminalTagResponse getAllTags(Integer pageNum, Integer pageSize);
    
    /**
     * 根据标签ID查询终端
     * @param tagIds 标签ID列表
     * @return 终端列表响应
     */
    TerminalListResponse getTerminalsByTagIds(String tagIds);
    
    /**
     * 获取终端标签列表
     * @param terminalId 终端ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 标签列表响应
     */
    TerminalTagResponse getTerminalTags(Integer terminalId, Integer pageNum, Integer pageSize);
    
    /**
     * 添加终端标签
     * @param request 添加标签请求
     */
    void addTerminalTags(TerminalTagAddRequest request);
    
    /**
     * 删除终端标签
     * @param terminalId 终端ID
     * @param tagIds 标签ID列表
     */
    void deleteTerminalTags(Integer terminalId, List<Integer> tagIds);
} 