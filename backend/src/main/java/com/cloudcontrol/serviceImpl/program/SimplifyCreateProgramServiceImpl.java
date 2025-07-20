package com.cloudcontrol.serviceImpl.program;

import com.cloudcontrol.dto.program.*;
import com.cloudcontrol.service.program.SimplifyCreateProgramService;
import com.cloudcontrol.entity.program.Program;
import com.cloudcontrol.repository.program.ProgramRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 简化创建节目服务实现类
 */
@Service
public class SimplifyCreateProgramServiceImpl implements SimplifyCreateProgramService {
    
    private static final Logger logger = LoggerFactory.getLogger(SimplifyCreateProgramServiceImpl.class);
    
    private final ProgramRepository programRepository;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public SimplifyCreateProgramServiceImpl(ProgramRepository programRepository, ObjectMapper objectMapper) {
        this.programRepository = programRepository;
        this.objectMapper = objectMapper;
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createWebProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建网页节目: programName={}, screenWidth={}, screenHeight={}", 
                   request.getProgramName(), request.getScreenWidth(), request.getScreenHeight());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("web");
            region.setUrl(request.getUrl());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建网页节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create web program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createDocumentProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建文件节目: programName={}, mediaId={}, syncProgram={}", 
                   request.getProgramName(), request.getMediaId(), request.getSyncProgram());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("document");
            region.setSyncProgram(request.getSyncProgram());
            
            // 构建medium
            List<MediumRequest> medium = new ArrayList<>();
            MediumRequest mediumRequest = new MediumRequest();
            mediumRequest.setMediaId(request.getMediaId());
            mediumRequest.setDurationMillis(request.getDurationMillis());
            mediumRequest.setConstrainedProportion(request.getConstrainedProportion());
            medium.add(mediumRequest);
            region.setMedium(medium);
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建文件节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create document program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createLineTextProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建文本节目: programName={}, lineText={}, lineTextType={}", 
                   request.getProgramName(), request.getLineText(), request.getLineTextType());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("lineText");
            region.setText(request.getLineText());
            region.setTextType(request.getLineTextType());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建文本节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create line text program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createClockProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建时钟节目: programName={}, clockType={}", 
                   request.getProgramName(), request.getClockType());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("clock");
            region.setClockType(request.getClockType());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建时钟节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create clock program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createWeatherProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建天气节目: programName={}, regionName={}", 
                   request.getProgramName(), request.getRegionName());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("weather");
            region.setRegionName(request.getRegionName());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建天气节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create weather program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createCountDownProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建倒计时节目: programName={}, endDateTime={}", 
                   request.getProgramName(), request.getEndDateTime());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("countDown");
            region.setEndDateTime(request.getEndDateTime());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建倒计时节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create countdown program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createEnvironmentProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建环境节目: programName={}, environmentType={}", 
                   request.getProgramName(), request.getEnvironmentType());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("environment");
            region.setEnvironmentType(request.getEnvironmentType());
            region.setPrefix(request.getPrefix());
            region.setSuffix(request.getSuffix());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            
            // 设置温度相关参数
            if (request.getEnvironmentType() == 22) { // 温度
                region.setTemperatureShowAsFahrenheit(request.getTemperatureShowAsFahrenheit());
                region.setTemperatureOffset(request.getTemperatureOffset());
            } else if (request.getEnvironmentType() == 28) { // 烟雾
                region.setSmokeStandard(request.getSmokeStandard());
            }
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建环境节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create environment program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createRssProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建新闻节目: programName={}, rssUrl={}", 
                   request.getProgramName(), request.getRssUrl());
        
        try {
            // 构建regions
            List<RegionRequest> regions = new ArrayList<>();
            RegionRequest region = new RegionRequest();
            region.setRegionType("rss");
            region.setRssUrl(request.getRssUrl());
            region.setFontSize(request.getFontSize());
            region.setDurationMillis(request.getDurationMillis());
            region.setRefreshIntervalMs(request.getRefreshIntervalMs());
            region.setScrollSpeed(request.getScrollSpeed());
            
            // 设置默认的rect（铺满屏幕）
            RectRequest rect = new RectRequest();
            rect.setRectHeight(request.getScreenHeight());
            rect.setRectWidth(request.getScreenWidth());
            rect.setX(0);
            rect.setY(0);
            region.setRect(rect);
            
            regions.add(region);
            
            // 创建节目
            return createProgram(request, regions);
            
        } catch (Exception e) {
            logger.error("创建新闻节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create RSS program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public SimplifyCreateProgramResponse createMultiWindowProgram(SimplifyCreateProgramRequest request) {
        logger.info("创建多窗口节目: programName={}, regions={}", 
                   request.getProgramName(), request.getRegions().size());
        
        try {
            // 直接使用请求中的regions创建节目
            return createProgram(request, request.getRegions());
            
        } catch (Exception e) {
            logger.error("创建多窗口节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create multi-window program: " + e.getMessage());
        }
    }
    
    /**
     * 创建节目的通用方法
     */
    private SimplifyCreateProgramResponse createProgram(SimplifyCreateProgramRequest request, List<RegionRequest> regions) {
        try {
            // 创建节目实体
            Program program = new Program();
            program.setTitle(request.getProgramName());
            program.setStatus("publish");
            program.setAuthor(1); // 默认作者ID
            program.setCreatedAt(LocalDateTime.now());
            program.setUpdatedAt(LocalDateTime.now());
            
            // 生成VSN文件名
            String vsnName = generateVsnName(program);
            program.setVsnName(vsnName);
            
            // 生成slug
            String slug = generateSlug(program);
            program.setSlug(slug);
            
            // 构建节目信息JSON
            String programInfo = buildProgramInfo(request, regions);
            program.setProgramInfo(programInfo);
            
            // 构建节目数据JSON
            String programsData = buildProgramsData(request, regions);
            program.setProgramsData(programsData);
            
            // 保存节目
            Program savedProgram = programRepository.save(program);
            
            logger.info("节目创建成功: programId={}, programName={}", savedProgram.getId(), request.getProgramName());
            
            return new SimplifyCreateProgramResponse(savedProgram.getId());
            
        } catch (Exception e) {
            logger.error("创建节目失败: programName={}", request.getProgramName(), e);
            throw new RuntimeException("Failed to create program: " + e.getMessage());
        }
    }
    
    /**
     * 生成VSN文件名
     */
    private String generateVsnName(Program program) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return program.getTitle() + "_" + uuid + "_" + timestamp;
    }
    
    /**
     * 生成slug
     */
    private String generateSlug(Program program) {
        return program.getTitle().toLowerCase().replaceAll("\\s+", "-");
    }
    
    /**
     * 构建节目信息JSON
     */
    private String buildProgramInfo(SimplifyCreateProgramRequest request, List<RegionRequest> regions) throws JsonProcessingException {
        // 这里应该根据实际的节目信息结构来构建
        // 暂时返回一个简单的JSON结构
        return objectMapper.writeValueAsString(regions);
    }
    
    /**
     * 构建节目数据JSON
     */
    private String buildProgramsData(SimplifyCreateProgramRequest request, List<RegionRequest> regions) throws JsonProcessingException {
        // 这里应该根据实际的节目数据结构来构建
        // 暂时返回一个简单的JSON结构
        return objectMapper.writeValueAsString(regions);
    }
} 