package com.cloudcontrol.serviceImpl.terminal;

import com.cloudcontrol.dto.terminal.TerminalTagResponse;
import com.cloudcontrol.dto.terminal.TerminalTagAddRequest;
import com.cloudcontrol.dto.terminal.TerminalListResponse;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.entity.terminal.TerminalTag;
import com.cloudcontrol.entity.terminal.TerminalTagRelation;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import com.cloudcontrol.repository.terminal.TerminalTagRepository;
import com.cloudcontrol.repository.terminal.TerminalTagRelationRepository;
import com.cloudcontrol.service.terminal.TerminalTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 终端标签服务实现类
 */
@Service
public class TerminalTagServiceImpl implements TerminalTagService {
    
    private final TerminalTagRepository terminalTagRepository;
    private final TerminalTagRelationRepository terminalTagRelationRepository;
    private final TerminalRepository terminalRepository;
    
    @Autowired
    public TerminalTagServiceImpl(TerminalTagRepository terminalTagRepository,
                                TerminalTagRelationRepository terminalTagRelationRepository,
                                TerminalRepository terminalRepository) {
        this.terminalTagRepository = terminalTagRepository;
        this.terminalTagRelationRepository = terminalTagRelationRepository;
        this.terminalRepository = terminalRepository;
    }
    
    @Override
    public TerminalTagResponse getAllTags(Integer pageNum, Integer pageSize) {
        try {
            // 设置默认值
            pageNum = pageNum == null ? 1 : pageNum;
            pageSize = pageSize == null ? 10 : pageSize;
            
            // 创建分页对象
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            
            // 查询标签
            Page<TerminalTag> tagPage = terminalTagRepository.findAll(pageable);
            
            // 转换为响应对象
            List<TerminalTagResponse.TagVo> tagVos = tagPage.getContent().stream()
                .map(tag -> new TerminalTagResponse.TagVo(tag.getTagId(), tag.getTagName()))
                .collect(Collectors.toList());
            
            return new TerminalTagResponse(tagVos, (int) tagPage.getTotalElements());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all tags: " + e.getMessage());
        }
    }
    
    @Override
    public TerminalListResponse getTerminalsByTagIds(String tagIds) {
        try {
            // 解析标签ID
            List<Integer> tagIdList = parseTagIds(tagIds);
            if (tagIdList.isEmpty()) {
                return new TerminalListResponse(new ArrayList<>(), "No tag IDs provided");
            }
            
            // 根据标签ID查询终端ID
            List<Integer> terminalIds = terminalTagRelationRepository.findTerminalIdsByTagIds(tagIdList);
            if (terminalIds.isEmpty()) {
                return new TerminalListResponse(new ArrayList<>(), "No terminals found");
            }
            
            // 查询终端信息
            List<Terminal> terminals = terminalRepository.findAllById(terminalIds);
            
            // 转换为响应对象
            List<TerminalListResponse.TerminalInfo> terminalInfos = terminals.stream()
                .map(this::convertToTerminalInfo)
                .collect(Collectors.toList());
            
            TerminalListResponse response = new TerminalListResponse(terminalInfos, "Success");
            response.setTotal(terminalInfos.size());
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminals by tag IDs: " + e.getMessage());
        }
    }
    
    @Override
    public TerminalTagResponse getTerminalTags(Integer terminalId, Integer pageNum, Integer pageSize) {
        try {
            // 设置默认值
            pageNum = pageNum == null ? 1 : pageNum;
            pageSize = pageSize == null ? 12 : pageSize;
            
            // 创建分页对象
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            
            // 查询终端的标签
            Page<TerminalTag> tagPage = terminalTagRepository.findByTerminalId(terminalId, pageable);
            
            // 转换为响应对象
            List<TerminalTagResponse.TagVo> tagVos = tagPage.getContent().stream()
                .map(tag -> new TerminalTagResponse.TagVo(tag.getTagId(), tag.getTagName()))
                .collect(Collectors.toList());
            
            return new TerminalTagResponse(tagVos, (int) tagPage.getTotalElements());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal tags: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void addTerminalTags(TerminalTagAddRequest request) {
        try {
            if (request.getTerminalTags() == null || request.getTerminalTags().isEmpty()) {
                throw new RuntimeException("Terminal tags cannot be empty");
            }
            
            for (TerminalTagAddRequest.TerminalTagInfo terminalTagInfo : request.getTerminalTags()) {
                Integer terminalId = terminalTagInfo.getTerminalId();
                List<String> tagNames = new ArrayList<>(terminalTagInfo.getTagNames());
                
                // 验证终端是否存在
                if (!terminalRepository.existsById(terminalId)) {
                    throw new RuntimeException("Terminal not found with ID: " + terminalId);
                }
                
                // 处理每个标签
                for (String tagName : tagNames) {
                    // 查找或创建标签
                    TerminalTag tag = terminalTagRepository.findByTagName(tagName)
                        .orElseGet(() -> {
                            TerminalTag newTag = new TerminalTag(tagName);
                            return terminalTagRepository.save(newTag);
                        });
                    
                    // 检查关系是否已存在
                    TerminalTagRelation existingRelation = terminalTagRelationRepository
                        .findByTerminalIdAndTagId(terminalId, tag.getTagId());
                    
                    if (existingRelation == null) {
                        // 创建新关系
                        TerminalTagRelation relation = new TerminalTagRelation(terminalId, tag.getTagId());
                        terminalTagRelationRepository.save(relation);
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to add terminal tags: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void deleteTerminalTags(Integer terminalId, List<Integer> tagIds) {
        try {
            // 验证终端是否存在
            if (!terminalRepository.existsById(terminalId)) {
                throw new RuntimeException("Terminal not found with ID: " + terminalId);
            }
            
            // 删除指定的标签关系
            for (Integer tagId : tagIds) {
                terminalTagRelationRepository.deleteByTerminalIdAndTagId(terminalId, tagId);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete terminal tags: " + e.getMessage());
        }
    }
    
    /**
     * 解析标签ID字符串
     */
    private List<Integer> parseTagIds(String tagIds) {
        List<Integer> result = new ArrayList<>();
        if (tagIds == null || tagIds.trim().isEmpty()) {
            return result;
        }
        
        String[] ids = tagIds.split(",");
        for (String id : ids) {
            try {
                result.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Invalid tag ID: " + id);
            }
        }
        
        return result;
    }
    
    /**
     * 转换为终端信息对象
     */
    private TerminalListResponse.TerminalInfo convertToTerminalInfo(Terminal terminal) {
        TerminalListResponse.TerminalInfo info = new TerminalListResponse.TerminalInfo();
        info.setId(terminal.getSerialNo());
        info.setDate(terminal.getCreatedAt().toString());
        info.setAuthor(terminal.getSerialNo());
        info.setStatus("publish");
        
        // 设置标题
        TerminalListResponse.TitleInfo titleInfo = new TerminalListResponse.TitleInfo();
        titleInfo.setRendered(terminal.getDeviceName());
        titleInfo.setRaw(terminal.getDeviceName());
        info.setTitle(titleInfo);
        
        // 设置额外信息
        TerminalListResponse.ExtraInfo extraInfo = new TerminalListResponse.ExtraInfo();
        extraInfo.setAuthorDisplayName("admin");
        info.setExtra(extraInfo);
        
        // 设置终端组信息
        if (terminal.getTerminalGroup() != null) {
            TerminalListResponse.TerminalGroupInfo terminalGroupInfo = new TerminalListResponse.TerminalGroupInfo();
            terminalGroupInfo.setId(terminal.getTerminalGroup().getId().intValue());
            terminalGroupInfo.setName(terminal.getTerminalGroup().getName());
            terminalGroupInfo.setDescription(terminal.getTerminalGroup().getDescription());
            info.setTerminalGroup(terminalGroupInfo);
        }
        
        // 设置元数据
        TerminalListResponse.PostMetaInfo metaInfo = new TerminalListResponse.PostMetaInfo();
        metaInfo.setUpdateStatus(new Object());
        metaInfo.setDownloadStatus(new TerminalListResponse.DownloadStatusInfo());
        metaInfo.setLedStatus(new TerminalListResponse.LedStatusInfo());
        
        // 设置地理坐标信息
        if (terminal.getLat() != null && terminal.getLng() != null) {
            TerminalListResponse.GeoCoordinateInfo geoCoordinateInfo = new TerminalListResponse.GeoCoordinateInfo();
            geoCoordinateInfo.setLatitude(Double.valueOf(terminal.getLat()));
            geoCoordinateInfo.setLongitude(Double.valueOf(terminal.getLng()));
            info.setGeoCoordinate(geoCoordinateInfo);
        }
        
        info.setPostMeta(metaInfo);
        
        return info;
    }
} 