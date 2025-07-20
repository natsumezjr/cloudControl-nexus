package com.cloudcontrol.service.program;

import com.cloudcontrol.dto.program.SimplifyCreateProgramRequest;
import com.cloudcontrol.dto.program.SimplifyCreateProgramResponse;

/**
 * 简化创建节目服务接口
 */
public interface SimplifyCreateProgramService {
    
    /**
     * 创建网页节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createWebProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建文件节目或同步节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createDocumentProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建单行文本或多行文本节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createLineTextProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建时钟节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createClockProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建天气节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createWeatherProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建倒计时节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createCountDownProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建环境节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createEnvironmentProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建新闻节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createRssProgram(SimplifyCreateProgramRequest request);
    
    /**
     * 创建多窗口节目
     * @param request 请求参数
     * @return 节目ID
     */
    SimplifyCreateProgramResponse createMultiWindowProgram(SimplifyCreateProgramRequest request);
} 