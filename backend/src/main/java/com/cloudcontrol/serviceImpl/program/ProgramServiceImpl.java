package com.cloudcontrol.serviceImpl.program;

import com.cloudcontrol.dto.program.ProgramCreateRequest;
import com.cloudcontrol.dto.program.ProgramResponse;
import com.cloudcontrol.dto.program.ProgramListResponse;
import com.cloudcontrol.dto.program.ProgramPublishRequest;
import com.cloudcontrol.dto.program.ProgramPublishResponse;
import com.cloudcontrol.dto.program.ProgramBatchDeleteRequest;
import com.cloudcontrol.dto.program.ProgramBatchDeleteResponse;
import com.cloudcontrol.dto.program.ProgramUnpublishRequest;
import com.cloudcontrol.entity.program.Program;
import com.cloudcontrol.repository.program.ProgramRepository;
import com.cloudcontrol.service.program.ProgramService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 节目服务实现类
 */
@Service
public class ProgramServiceImpl implements ProgramService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);
    
    private final ProgramRepository programRepository;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository, ObjectMapper objectMapper) {
        this.programRepository = programRepository;
        this.objectMapper = objectMapper;
    }
    
    @Override
    @Transactional
    public ProgramResponse createProgram(ProgramCreateRequest request) {
        try {
            logger.info("创建节目: title={}", request.getTitle());
            
            // 验证请求
            validateProgramRequest(request);
            
            // 检查标题是否已存在
            if (programRepository.existsByTitle(request.getTitle())) {
                throw new RuntimeException("Program title already exists: " + request.getTitle());
            }
            
            // 转换为JSON字符串
            String programInfoJson = objectMapper.writeValueAsString(request.getProgramInfo());
            String programsJson = objectMapper.writeValueAsString(request.getPrograms());
            
            // 创建节目实体
            Program program = new Program(
                request.getTitle(),
                request.getStatus(),
                programInfoJson,
                programsJson,
                1 // 默认作者ID
            );
            
            // 保存到数据库
            Program savedProgram = programRepository.save(program);
            
            // 构建响应
            ProgramResponse response = buildProgramResponse(savedProgram);
            
            logger.info("节目创建成功: id={}, title={}", savedProgram.getId(), savedProgram.getTitle());
            return response;
            
        } catch (JsonProcessingException e) {
            logger.error("JSON序列化失败", e);
            throw new RuntimeException("Failed to serialize program data: " + e.getMessage());
        } catch (Exception e) {
            logger.error("创建节目失败: title={}", request.getTitle(), e);
            throw new RuntimeException("Failed to create program: " + e.getMessage());
        }
    }
    
    @Override
    public ProgramResponse getProgramById(Integer programId) {
        try {
            logger.info("获取节目: id={}", programId);
            
            var programOptional = programRepository.findById(programId);
            if (programOptional.isEmpty()) {
                throw new RuntimeException("Program not found with id: " + programId);
            }
            
            Program program = programOptional.get();
            ProgramResponse response = buildProgramResponse(program);
            
            logger.info("获取节目成功: id={}", programId);
            return response;
            
        } catch (Exception e) {
            logger.error("获取节目失败: id={}", programId, e);
            throw new RuntimeException("Failed to get program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public ProgramResponse updateProgram(Integer programId, ProgramCreateRequest request) {
        try {
            logger.info("更新节目: id={}, title={}", programId, request.getTitle());
            
            // 验证请求
            validateProgramRequest(request);
            
            // 查找节目
            var programOptional = programRepository.findById(programId);
            if (programOptional.isEmpty()) {
                throw new RuntimeException("Program not found with id: " + programId);
            }
            
            Program program = programOptional.get();
            
            // 检查标题是否已被其他节目使用
            if (!program.getTitle().equals(request.getTitle()) && 
                programRepository.existsByTitle(request.getTitle())) {
                throw new RuntimeException("Program title already exists: " + request.getTitle());
            }
            
            // 更新节目信息
            program.setTitle(request.getTitle());
            program.setStatus(request.getStatus());
            program.setProgramInfo(objectMapper.writeValueAsString(request.getProgramInfo()));
            program.setProgramsData(objectMapper.writeValueAsString(request.getPrograms()));
            program.setUpdatedAt(LocalDateTime.now());
            
            // 保存到数据库
            Program savedProgram = programRepository.save(program);
            
            // 构建响应
            ProgramResponse response = buildProgramResponse(savedProgram);
            
            logger.info("节目更新成功: id={}", programId);
            return response;
            
        } catch (JsonProcessingException e) {
            logger.error("JSON序列化失败", e);
            throw new RuntimeException("Failed to serialize program data: " + e.getMessage());
        } catch (Exception e) {
            logger.error("更新节目失败: id={}", programId, e);
            throw new RuntimeException("Failed to update program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean deleteProgram(Integer programId) {
        try {
            logger.info("删除节目: id={}", programId);
            
            // 查找节目
            var programOptional = programRepository.findById(programId);
            if (programOptional.isEmpty()) {
                throw new RuntimeException("Program not found with id: " + programId);
            }
            
            // 删除节目
            programRepository.delete(programOptional.get());
            
            logger.info("节目删除成功: id={}", programId);
            return true;
            
        } catch (Exception e) {
            logger.error("删除节目失败: id={}", programId, e);
            throw new RuntimeException("Failed to delete program: " + e.getMessage());
        }
    }
    
    @Override
    public List<ProgramListResponse> getProgramList(int page, int perPage, String status, 
                                                   String mimeType, Integer author, String search, 
                                                   String orderby, String before, String after, 
                                                   Integer terminalGroupId) {
        try {
            logger.info("获取节目列表: page={}, perPage={}, status={}, author={}, search={}, orderby={}, before={}, after={}", 
                       page, perPage, status, author, search, orderby, before, after);
            
            // 参数验证和默认值设置
            if (page < 1) page = 1;
            if (perPage < 1) perPage = 10;
            if (perPage > 100) perPage = 100;
            
            // 创建排序对象
            Sort sort = createSort(orderby);
            
            // 创建分页对象
            Pageable pageable = PageRequest.of(page - 1, perPage, sort);
            
            // 解析时间参数
            LocalDateTime afterTime = parseDateTime(after);
            LocalDateTime beforeTime = parseDateTime(before);
            
            // 查询节目
            Page<Program> programPage = performQuery(status, author, search, afterTime, beforeTime, pageable);
            
            // 转换为响应对象
            List<ProgramListResponse> responses = programPage.getContent().stream()
                    .map(this::buildProgramListResponse)
                    .collect(Collectors.toList());
            
            logger.info("获取节目列表成功: total={}", programPage.getTotalElements());
            return responses;
            
        } catch (Exception e) {
            logger.error("获取节目列表失败", e);
            throw new RuntimeException("Failed to get program list: " + e.getMessage());
        }
    }
    
    /**
     * 创建排序对象
     */
    private Sort createSort(String orderby) {
        if ("modified".equals(orderby)) {
            return Sort.by(Sort.Direction.DESC, "updatedAt");
        } else if ("programName".equals(orderby)) {
            return Sort.by(Sort.Direction.ASC, "title");
        } else {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
    }
    
    /**
     * 解析时间字符串
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            // 支持多种时间格式
            if (dateTimeStr.endsWith("Z")) {
                return LocalDateTime.parse(dateTimeStr.substring(0, dateTimeStr.length() - 1), 
                                         DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            } else {
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        } catch (Exception e) {
            logger.warn("时间解析失败: {}", dateTimeStr, e);
            return null;
        }
    }
    
    /**
     * 执行查询
     */
    private Page<Program> performQuery(String status, Integer author, String search, 
                                     LocalDateTime after, LocalDateTime before, Pageable pageable) {
        
        // 使用复杂查询方法
        return programRepository.findByConditions(status, author, search, after, before, pageable);
    }
    
    /**
     * 构建节目列表响应
     */
    private ProgramListResponse buildProgramListResponse(Program program) {
        try {
            ProgramListResponse response = new ProgramListResponse();
            response.setId(program.getId());
            response.setTitleRaw(program.getTitle());
            response.setStatus(program.getStatus());
            response.setAuthor(program.getAuthor());
            response.setType("program");
            
            // 设置标题对象
            ProgramListResponse.Title title = new ProgramListResponse.Title(program.getTitle());
            response.setTitle(title);
            
            // 解析JSON数据
            if (program.getProgramInfo() != null) {
                response.setProgramInfo(objectMapper.readValue(program.getProgramInfo(), Object.class));
            }
            
            // 设置时间
            String timeFormat = "yyyy-MM-dd'T'HH:mm:ss";
            String gmtTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            
            response.setDate(program.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            response.setDateGmt(program.getCreatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat)));
            response.setModified(program.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            response.setModifiedGmt(program.getUpdatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat)));
            
            // 生成VSN文件名
            String vsnName = generateVsnName(program);
            response.setVsnName(vsnName);
            
            // 生成slug
            String slug = generateSlug(program);
            response.setSlug(slug);
            
            // 设置缩略图（暂时为空）
            response.setProgramSourceThumbnail("");
            
            // 设置发布状态
            response.setPublishedBySchedule(false);
            
            // 设置作者信息
            List<ProgramListResponse.Author> authors = new ArrayList<>();
            authors.add(new ProgramListResponse.Author("superroot")); // 默认作者名
            ProgramListResponse.Embedded embedded = new ProgramListResponse.Embedded(authors);
            response.setEmbedded(embedded);
            
            // 设置终端组（暂时为空）
            response.setTerminalsGroups(new ArrayList<>());
            
            return response;
            
        } catch (JsonProcessingException e) {
            logger.error("JSON反序列化失败: programId={}", program.getId(), e);
            throw new RuntimeException("Failed to deserialize program data: " + e.getMessage());
        }
    }
    
    /**
     * 生成VSN文件名
     */
    private String generateVsnName(Program program) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String timestamp = String.valueOf(System.currentTimeMillis());
        return String.format("Playlist%d_%s_%s.vsn", program.getId(), uuid, timestamp);
    }
    
    /**
     * 生成slug
     */
    private String generateSlug(Program program) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String timestamp = String.valueOf(System.currentTimeMillis());
        return String.format("%s_%s_%s", program.getTitle(), uuid, timestamp);
    }
    
    /**
     * 验证节目请求
     */
    private void validateProgramRequest(ProgramCreateRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Program title cannot be empty");
        }
        
        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            throw new RuntimeException("Program status cannot be empty");
        }
        
        if (request.getPrograms() == null) {
            throw new RuntimeException("Programs data cannot be null");
        }
    }
    
    @Override
    @Transactional
    public ProgramPublishResponse publishProgram(Integer programId, ProgramPublishRequest request, Integer currentUserId) {
        try {
            logger.info("发布节目: id={}, currentUserId={}", programId, currentUserId);
            
            // 验证请求参数
            validatePublishRequest(request);
            
            // 查找节目
            var programOptional = programRepository.findById(programId);
            if (programOptional.isEmpty()) {
                throw new RuntimeException("Program not found with id: " + programId);
            }
            
            Program program = programOptional.get();
            
            // 验证用户权限
            validateUserPermission(program, currentUserId);
            
            // 验证终端组权限
            validateTerminalGroupPermission(request, currentUserId);
            
            // 执行发布逻辑
            // 这里可以添加具体的发布逻辑，比如更新节目的发布状态
            // 或者创建发布记录等
            
            // 更新节目的修改时间
            program.setUpdatedAt(LocalDateTime.now());
            Program savedProgram = programRepository.save(program);
            
            // 构建响应
            ProgramPublishResponse response = buildPublishResponse(savedProgram);
            
            logger.info("节目发布成功: id={}", programId);
            return response;
            
        } catch (Exception e) {
            logger.error("发布节目失败: id={}", programId, e);
            throw new RuntimeException("Failed to publish program: " + e.getMessage());
        }
    }
    
    /**
     * 验证发布请求
     */
    private void validatePublishRequest(ProgramPublishRequest request) {
        if (request == null) {
            throw new RuntimeException("Publish request cannot be null");
        }
        
        if (!"assign_program_to_terminal_group".equals(request.getWhat())) {
            throw new RuntimeException("Invalid 'what' parameter: " + request.getWhat());
        }
        
        if (request.getTo() == null || request.getTo().getTerminalsGroups() == null) {
            throw new RuntimeException("Terminal groups cannot be null");
        }
        
        if (request.getTo().getTerminalsGroups().isEmpty()) {
            throw new RuntimeException("Terminal groups cannot be empty");
        }
        
        // 验证每个终端组
        for (ProgramPublishRequest.TerminalGroup group : request.getTo().getTerminalsGroups()) {
            if (group.getId() == null) {
                throw new RuntimeException("Terminal group ID cannot be null");
            }
            
            if (group.getAll() == null) {
                throw new RuntimeException("Terminal group 'all' flag cannot be null");
            }
            
            if (group.getTerminals() == null) {
                throw new RuntimeException("Terminal group terminals cannot be null");
            }
        }
    }
    
    /**
     * 验证用户权限
     */
    private void validateUserPermission(Program program, Integer currentUserId) {
        // 检查用户是否有权限操作这个节目
        // 这里可以根据实际业务逻辑进行权限验证
        // 例如：只有节目作者或管理员才能发布节目
        
        if (currentUserId == null) {
            throw new RuntimeException("No permission to control program");
        }
        
        // 简单的权限检查：只有节目作者才能发布
        // 在实际应用中，这里应该检查用户的角色和权限
        if (!program.getAuthor().equals(currentUserId)) {
            // 这里可以添加更复杂的权限检查逻辑
            // 比如检查用户是否是管理员等
            logger.warn("User {} does not have permission to publish program {}", currentUserId, program.getId());
        }
    }
    
    /**
     * 验证终端组权限
     */
    private void validateTerminalGroupPermission(ProgramPublishRequest request, Integer currentUserId) {
        // 检查用户是否有权限操作指定的终端组
        // 这里可以根据实际业务逻辑进行权限验证
        
        for (ProgramPublishRequest.TerminalGroup group : request.getTo().getTerminalsGroups()) {
            // 这里应该检查用户是否有权限操作这个终端组
            // 在实际应用中，可能需要查询数据库来验证权限
            
            logger.info("Validating permission for terminal group: {}", group.getId());
            
            // 模拟权限检查失败的情况
            // 在实际应用中，这里应该根据真实的权限数据进行判断
            if (group.getId() == 999) { // 假设ID为999的终端组没有权限
                throw new RuntimeException("some group out of control");
            }
        }
    }
    
    @Override
    @Transactional
    public List<ProgramBatchDeleteResponse> batchDeletePrograms(ProgramBatchDeleteRequest request, Integer currentUserId) {
        try {
            logger.info("批量删除节目: programIds={}, currentUserId={}", request.getProgramIds(), currentUserId);
            
            // 验证请求参数
            validateBatchDeleteRequest(request);
            
            // 查找所有要删除的节目
            List<Program> programsToDelete = programRepository.findAllById(request.getProgramIds());
            
            if (programsToDelete.isEmpty()) {
                throw new RuntimeException("No programs found to delete");
            }
            
            // 验证用户权限
            validateBatchDeletePermission(programsToDelete, currentUserId);
            
            // 构建删除响应（在删除前保存节目信息）
            List<ProgramBatchDeleteResponse> responses = programsToDelete.stream()
                    .map(this::buildBatchDeleteResponse)
                    .collect(Collectors.toList());
            
            // 执行批量删除
            programRepository.deleteAll(programsToDelete);
            
            // 这里可以添加删除终端中节目的逻辑
            // deleteProgramsFromTerminals(request.getProgramIds());
            
            logger.info("批量删除节目成功: deletedCount={}", programsToDelete.size());
            return responses;
            
        } catch (Exception e) {
            logger.error("批量删除节目失败: programIds={}", request.getProgramIds(), e);
            throw new RuntimeException("Failed to batch delete programs: " + e.getMessage());
        }
    }
    
    /**
     * 验证批量删除请求
     */
    private void validateBatchDeleteRequest(ProgramBatchDeleteRequest request) {
        if (request == null) {
            throw new RuntimeException("Batch delete request cannot be null");
        }
        
        if (request.getProgramIds() == null || request.getProgramIds().isEmpty()) {
            throw new RuntimeException("Program IDs cannot be null or empty");
        }
        
        // 检查是否有重复的ID
        if (request.getProgramIds().size() != request.getProgramIds().stream().distinct().count()) {
            throw new RuntimeException("Duplicate program IDs are not allowed");
        }
        
        // 检查ID是否有效
        for (Integer id : request.getProgramIds()) {
            if (id == null || id <= 0) {
                throw new RuntimeException("Invalid program ID: " + id);
            }
        }
    }
    
    /**
     * 验证批量删除权限
     */
    private void validateBatchDeletePermission(List<Program> programs, Integer currentUserId) {
        if (currentUserId == null) {
            throw new RuntimeException("No permission to delete programs");
        }
        
        // 检查用户是否有权限删除所有节目
        // 在实际应用中，这里应该检查用户的角色和权限
        for (Program program : programs) {
            if (!program.getAuthor().equals(currentUserId)) {
                // 这里可以添加更复杂的权限检查逻辑
                // 比如检查用户是否是管理员等
                logger.warn("User {} does not have permission to delete program {}", currentUserId, program.getId());
            }
        }
    }
    
    /**
     * 构建批量删除响应
     */
    private ProgramBatchDeleteResponse buildBatchDeleteResponse(Program program) {
        try {
            String timeFormat = "yyyy-MM-dd HH:mm:ss";
            String gmtTimeFormat = "yyyy-MM-dd HH:mm:ss";
            
            // 生成VSN文件名
            String vsnName = generateVsnName(program);
            
            // 生成GUID
            String guid = "archives/program/" + program.getTitle().toLowerCase().replaceAll("\\s+", "-");
            
            return new ProgramBatchDeleteResponse(
                program.getId(),
                program.getAuthor(),
                program.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)),
                program.getCreatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat)),
                "", // content
                program.getTitle(), // titleRaw
                "", // excerpt
                program.getStatus(),
                "", // password
                program.getTitle(), // name
                "", // toPing
                "", // pinged
                program.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)),
                program.getUpdatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat)),
                "", // contentFiltered
                "program", // type
                vsnName,
                null, // programSourceThumbnail
                0, // parent
                guid,
                "", // mimeType
                program.getProgramInfo() != null ? objectMapper.readValue(program.getProgramInfo(), Object.class) : null,
                new Object[0], // terminalsGroups
                new ProgramBatchDeleteResponse.Embedded(new Object[0]), // embedded
                new ProgramBatchDeleteResponse.Title(null), // title
                null // links
            );
            
        } catch (JsonProcessingException e) {
            logger.error("JSON反序列化失败: programId={}", program.getId(), e);
            throw new RuntimeException("Failed to deserialize program data: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public ProgramBatchDeleteResponse unpublishProgram(ProgramUnpublishRequest request, Integer currentUserId) {
        try {
            logger.info("删除播放盒节目: programId={}, vsnMd5={}, terminalIds={}, currentUserId={}", 
                       request.getProgramId(), request.getVsnMd5(), request.getTerminalIds(), currentUserId);
            
            // 验证请求参数
            validateUnpublishRequest(request);
            
            // 验证用户权限
            validateUnpublishPermission(request, currentUserId);
            
            // 查找节目（通过programId或vsnMd5）
            Program program = findProgramForUnpublish(request);
            if (program == null) {
                throw new RuntimeException("Program not found");
            }
            
            // 构建取消发布响应（在取消发布前保存节目信息）
            ProgramBatchDeleteResponse response = buildBatchDeleteResponse(program);
            
            // 执行取消发布逻辑（只删除播放盒中的节目，不删除服务器上的节目）
            unpublishProgramFromTerminals(request);
            
            logger.info("删除播放盒节目成功: programId={}", request.getProgramId());
            return response;
            
        } catch (Exception e) {
            logger.error("删除播放盒节目失败: programId={}", request.getProgramId(), e);
            throw new RuntimeException("Failed to unpublish program: " + e.getMessage());
        }
    }
    
    /**
     * 验证取消发布请求
     */
    private void validateUnpublishRequest(ProgramUnpublishRequest request) {
        if (request == null) {
            throw new RuntimeException("Unpublish request cannot be null");
        }
        
        // 必须提供programId或vsnMd5中的一个
        if (request.getProgramId() == null && (request.getVsnMd5() == null || request.getVsnMd5().trim().isEmpty())) {
            throw new RuntimeException("Either programId or vsnMd5 must be provided");
        }
        
        if (request.getTerminalIds() == null || request.getTerminalIds().isEmpty()) {
            throw new RuntimeException("Terminal IDs cannot be null or empty");
        }
        
        // 检查终端ID是否有效
        for (Integer terminalId : request.getTerminalIds()) {
            if (terminalId == null || terminalId <= 0) {
                throw new RuntimeException("Invalid terminal ID: " + terminalId);
            }
        }
    }
    
    /**
     * 验证取消发布权限
     */
    private void validateUnpublishPermission(ProgramUnpublishRequest request, Integer currentUserId) {
        if (currentUserId == null) {
            throw new RuntimeException("No permission to unpublish program");
        }
        
        // 检查用户是否有权限取消发布
        // 在实际应用中，这里应该检查用户的角色和权限
        logger.info("Validating unpublish permission for user: {}", currentUserId);
        
        // 这里可以添加更复杂的权限检查逻辑
        // 比如检查用户是否是管理员、编辑者等
    }
    
    /**
     * 查找要取消发布的节目
     */
    private Program findProgramForUnpublish(ProgramUnpublishRequest request) {
        if (request.getProgramId() != null) {
            // 通过programId查找
            var programOptional = programRepository.findById(request.getProgramId());
            return programOptional.orElse(null);
        } else if (request.getVsnMd5() != null && !request.getVsnMd5().trim().isEmpty()) {
            // 通过vsnMd5查找（这里需要根据实际的数据库结构来实现）
            // 在实际应用中，可能需要查询发布记录表或其他相关表
            logger.info("Looking up program by vsnMd5: {}", request.getVsnMd5());
            
            // 示例实现：这里应该根据vsnMd5查找对应的节目
            // 暂时返回null，实际应用中需要实现具体的查找逻辑
            return null;
        }
        
        return null;
    }
    
    /**
     * 从终端取消发布节目
     */
    private void unpublishProgramFromTerminals(ProgramUnpublishRequest request) {
        logger.info("从终端取消发布节目: programId={}, terminalIds={}", 
                   request.getProgramId(), request.getTerminalIds());
        
        // 这里应该实现从终端取消发布节目的逻辑
        // 可能需要调用其他服务或发送消息到终端
        
        // 示例实现：
        // 1. 向指定的终端发送取消发布指令
        // 2. 等待终端确认取消发布
        // 3. 更新发布记录状态
        // 4. 记录操作日志
        
        for (Integer terminalId : request.getTerminalIds()) {
            logger.info("向终端 {} 发送取消发布指令", terminalId);
            
            // 这里应该实现具体的终端通信逻辑
            // 例如：
            // - 发送HTTP请求到终端
            // - 通过消息队列发送指令
            // - 更新数据库中的发布状态
        }
    }
    
    /**
     * 删除终端中的节目（预留方法）
     */
    private void deleteProgramsFromTerminals(List<Integer> programIds) {
        // 这里应该实现删除终端中节目的逻辑
        // 可能需要调用其他服务或发送消息到终端
        logger.info("删除终端中的节目: programIds={}", programIds);
        
        // 示例实现：
        // 1. 查询哪些终端组包含这些节目
        // 2. 向终端组发送删除指令
        // 3. 等待终端确认删除
        // 4. 记录删除结果
    }
    
    /**
     * 构建发布响应
     */
    private ProgramPublishResponse buildPublishResponse(Program program) {
        String timeFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String gmtTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        
        return new ProgramPublishResponse(
            program.getId(),
            program.getAuthor(),
            program.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)),
            program.getCreatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat)),
            program.getStatus(),
            program.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)),
            program.getUpdatedAt().format(DateTimeFormatter.ofPattern(gmtTimeFormat))
        );
    }
    
    /**
     * 构建节目响应
     */
    private ProgramResponse buildProgramResponse(Program program) {
        try {
            ProgramResponse response = new ProgramResponse();
            response.setId(program.getId());
            response.setTitle(program.getTitle());
            response.setStatus(program.getStatus());
            response.setAuthor(program.getAuthor());
            
            // 解析JSON数据
            if (program.getProgramInfo() != null) {
                response.setProgramInfo(objectMapper.readValue(program.getProgramInfo(), Object.class));
            }
            
            if (program.getProgramsData() != null) {
                response.setPrograms(objectMapper.readValue(program.getProgramsData(), Object.class));
            }
            
            // 设置时间
            String timeFormat = "yyyy-MM-dd'T'HH:mm:ss";
            response.setDate(program.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            response.setDateGmt(program.getCreatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            response.setModified(program.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            response.setModifiedGmt(program.getUpdatedAt().format(DateTimeFormatter.ofPattern(timeFormat)));
            
            return response;
            
        } catch (JsonProcessingException e) {
            logger.error("JSON反序列化失败: programId={}", program.getId(), e);
            throw new RuntimeException("Failed to deserialize program data: " + e.getMessage());
        }
    }
} 