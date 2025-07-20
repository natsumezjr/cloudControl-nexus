package com.cloudcontrol.serviceImpl.terminal;

import com.cloudcontrol.dto.terminal.TerminalCreateRequest;
import com.cloudcontrol.dto.terminal.TerminalCreateResponse;
import com.cloudcontrol.dto.terminal.TerminalListRequest;
import com.cloudcontrol.dto.terminal.TerminalListResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestMultipleRequest;
import com.cloudcontrol.dto.terminal.TerminalGpsLatestResponse;
import com.cloudcontrol.dto.terminal.TerminalGpsV2Response;
import com.cloudcontrol.dto.terminal.TerminalTrackRequest;
import com.cloudcontrol.dto.terminal.TerminalTrackResponse;
import com.cloudcontrol.dto.terminal.TerminalMileageRequest;
import com.cloudcontrol.dto.terminal.TerminalMileageResponse;
import com.cloudcontrol.dto.terminal.TerminalUpdateRequest;
import com.cloudcontrol.dto.terminal.TerminalUpdateResponse;
import com.cloudcontrol.dto.terminal.TerminalDeleteResponse;
import com.cloudcontrol.entity.terminal.Terminal;
import java.util.Optional;
import com.cloudcontrol.entity.terminal.TerminalGroup;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import com.cloudcontrol.repository.terminal.TerminalGroupRepository;
import com.cloudcontrol.repository.terminal.ProgramTerminalRelationRepository;
import com.cloudcontrol.service.terminal.TerminalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 终端服务实现类
 */
@Service
public class TerminalServiceImpl implements TerminalService {

    private final TerminalRepository terminalRepository;
    private final TerminalGroupRepository terminalGroupRepository;
    private final ProgramTerminalRelationRepository programTerminalRelationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TerminalServiceImpl(TerminalRepository terminalRepository, 
                             TerminalGroupRepository terminalGroupRepository,
                             ProgramTerminalRelationRepository programTerminalRelationRepository,
                             PasswordEncoder passwordEncoder) {
        this.terminalRepository = terminalRepository;
        this.terminalGroupRepository = terminalGroupRepository;
        this.programTerminalRelationRepository = programTerminalRelationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public TerminalCreateResponse createTerminalWithAccount(TerminalCreateRequest request) {
        // 1. 参数验证
        validateRequest(request);
        
        // 2. 检查终端名是否已存在
        if (!request.getForce() && terminalRepository.findByDeviceName(request.getTerminalModel().getTitle()).isPresent()) {
            throw new RuntimeException("10404:Terminal name already exists.");
        }
        
        // 3. 检查终端组是否存在
        TerminalGroup terminalGroup = validateTerminalGroup(request.getTerminalModel().getTerminalGroup().get(0));
        
        // 4. 创建终端
        Terminal terminal = new Terminal();
        terminal.setDeviceName(request.getTerminalModel().getTitle());
        terminal.setAccountName(request.getAccountModel().getUsername());
        terminal.setPassword(passwordEncoder.encode(request.getAccountModel().getPassword()));
        terminal.setStatus("online"); // 默认在线状态
        terminal.setTerminalGroup(terminalGroup); // 设置终端组
        terminal.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        
        // 5. 保存终端
        Terminal savedTerminal = terminalRepository.save(terminal);
        
        // 6. 返回响应
        return new TerminalCreateResponse(savedTerminal.getSerialNo(), "Terminal created successfully");
    }
    
    /**
     * 验证请求参数
     */
    private void validateRequest(TerminalCreateRequest request) {
        // 验证终端模型
        if (request.getTerminalModel() == null) {
            throw new RuntimeException("10016:Param must not be null.");
        }
        
        if (request.getTerminalModel().getTitle() == null || request.getTerminalModel().getTitle().trim().isEmpty()) {
            throw new RuntimeException("10401:Illegal terminal name.");
        }
        
        if (!"publish".equals(request.getTerminalModel().getStatus())) {
            throw new RuntimeException("Invalid terminal status. Must be 'publish'.");
        }
        
        if (request.getTerminalModel().getTerminalGroup() == null || request.getTerminalModel().getTerminalGroup().isEmpty()) {
            throw new RuntimeException("Terminal group is required.");
        }
        
        if (request.getTerminalModel().getRoles() == null || request.getTerminalModel().getRoles().isEmpty()) {
            throw new RuntimeException("Terminal roles are required.");
        }
        
        if (!request.getTerminalModel().getRoles().contains("terminal")) {
            throw new RuntimeException("10308:The User role are not allowed.");
        }
        
        // 验证账号模型
        if (request.getAccountModel() == null) {
            throw new RuntimeException("10016:Param must not be null.");
        }
        
        if (request.getAccountModel().getEmail() == null || request.getAccountModel().getEmail().trim().isEmpty()) {
            throw new RuntimeException("10306:Illegal email.");
        }
        
        if (!isValidEmail(request.getAccountModel().getEmail())) {
            throw new RuntimeException("10306:Illegal email.");
        }
        
        if (request.getAccountModel().getUsername() == null || request.getAccountModel().getUsername().trim().isEmpty()) {
            throw new RuntimeException("10304:Illegal username.");
        }
        
        if (request.getAccountModel().getPassword() == null || request.getAccountModel().getPassword().trim().isEmpty()) {
            throw new RuntimeException("10107:Illegal password length.");
        }
        
        if (request.getAccountModel().getPassword().length() < 8 || request.getAccountModel().getPassword().length() > 20) {
            throw new RuntimeException("10107:Illegal password length.");
        }
        
        if (!isValidPassword(request.getAccountModel().getPassword())) {
            throw new RuntimeException("10101:Password strength is too weak.Must contain a combination of uppercase and lowercase letters and numbers, special characters can be used, and the length is between 8-20.");
        }
        
        if (request.getAccountModel().getRoles() == null || request.getAccountModel().getRoles().isEmpty()) {
            throw new RuntimeException("10307:The User has at least one role.");
        }
        
        if (!request.getAccountModel().getRoles().contains("terminal")) {
            throw new RuntimeException("10308:The User role are not allowed.");
        }
    }
    
    /**
     * 验证终端组是否存在
     */
    private TerminalGroup validateTerminalGroup(Integer groupId) {
        return terminalGroupRepository.findById(groupId.longValue())
            .orElseThrow(() -> new RuntimeException("30001:Terminal group not found."));
    }
    
    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    /**
     * 验证密码强度
     */
    private boolean isValidPassword(String password) {
        // 至少包含一个大写字母、一个小写字母、一个数字
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        
        return hasUpper && hasLower && hasDigit;
    }
    
    @Override
    public TerminalListResponse getTerminalList(TerminalListRequest request) {
        try {
            // 1. 构建查询条件
            List<Terminal> terminals;
            int total;
            
            if (request.getCount() == 1) {
                // 只返回总数
                total = (int) terminalRepository.count();
                return new TerminalListResponse(null, "Total count: " + total);
            }
            
            // 2. 分页查询终端
            Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPerPage());
            Page<Terminal> terminalPage = terminalRepository.findAll(pageable);
            terminals = terminalPage.getContent();
            total = (int) terminalPage.getTotalElements();
            
            // 3. 转换为响应DTO
            List<TerminalListResponse.TerminalInfo> terminalInfos = terminals.stream()
                .map(terminal -> convertToTerminalInfo(terminal, request))
                .collect(Collectors.toList());
            
            // 4. 构建响应
            TerminalListResponse response = new TerminalListResponse(terminalInfos, "Success");
            response.setTotal(total);
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal list: " + e.getMessage());
        }
    }
    
    @Override
    public List<TerminalListResponse> getTerminalList(List<Integer> terminalIds) {
        try {
            List<TerminalListResponse> responses = new ArrayList<>();
            
            for (Integer terminalId : terminalIds) {
                Optional<Terminal> terminalOpt = terminalRepository.findById(terminalId);
                if (terminalOpt.isPresent()) {
                    Terminal terminal = terminalOpt.get();
                    TerminalListResponse response = new TerminalListResponse();
                    response.setId(terminal.getSerialNo());
                    response.setStatus("publish");
                    response.setDate(terminal.getCreatedAt() != null ? terminal.getCreatedAt().toString() : "");
                    response.setDate_gmt(terminal.getCreatedAt() != null ? terminal.getCreatedAt().toString() : "");
                    response.setAuthor(1); // 默认作者ID
                    
                    // 设置标题信息
                    TerminalListResponse.TitleInfo titleInfo = new TerminalListResponse.TitleInfo();
                    titleInfo.setRendered(terminal.getDeviceName());
                    titleInfo.setRaw(terminal.getDeviceName());
                    response.setTitle(titleInfo);
                    
                    // 设置额外信息
                    TerminalListResponse.ExtraInfo extraInfo = new TerminalListResponse.ExtraInfo();
                    extraInfo.setAuthorDisplayName("Admin");
                    response.setExtra(extraInfo);
                    
                    // 设置文章元数据
                    TerminalListResponse.PostMeta postMeta = new TerminalListResponse.PostMeta();
                    // 这里需要TerminalStatus枚举，暂时设为null
                    response.setPost_meta(postMeta);
                    
                    responses.add(response);
                }
            }
            
            return responses;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal list: " + e.getMessage());
        }
    }
    
    /**
     * 将Terminal实体转换为TerminalInfo DTO
     */
    private TerminalListResponse.TerminalInfo convertToTerminalInfo(Terminal terminal, TerminalListRequest request) {
        TerminalListResponse.TerminalInfo info = new TerminalListResponse.TerminalInfo();
        
        // 基本信息
        info.setId(terminal.getSerialNo());
        info.setStatus("publish");
        info.setAuthor(1); // 默认作者ID
        
        // 时间信息
        if (terminal.getCreatedAt() != null) {
            info.setDate(terminal.getCreatedAt().toString());
            info.setDateGmt(terminal.getCreatedAt().toString() + "Z");
        }
        
        // 标题信息
        TerminalListResponse.TitleInfo titleInfo = new TerminalListResponse.TitleInfo();
        titleInfo.setRaw(terminal.getDeviceName());
        titleInfo.setRendered(terminal.getDeviceName());
        info.setTitle(titleInfo);
        
        // 设置终端组信息
        if (terminal.getTerminalGroup() != null) {
            TerminalListResponse.TerminalGroupInfo terminalGroupInfo = new TerminalListResponse.TerminalGroupInfo();
            terminalGroupInfo.setId(terminal.getTerminalGroup().getId().intValue());
            terminalGroupInfo.setName(terminal.getTerminalGroup().getName());
            terminalGroupInfo.setDescription(terminal.getTerminalGroup().getDescription());
            info.setTerminalGroup(terminalGroupInfo);
        }
        
        // 扩展信息
        TerminalListResponse.ExtraInfo extraInfo = new TerminalListResponse.ExtraInfo();
        extraInfo.setAuthorDisplayName("admin"); // 默认管理员
        info.setExtra(extraInfo);
        
        // 元数据信息
        TerminalListResponse.PostMetaInfo postMetaInfo = new TerminalListResponse.PostMetaInfo();
        
        // 设置地理坐标信息
        if (request.getWithCoordinate() != null && request.getWithCoordinate() && terminal.getLat() != null && terminal.getLng() != null) {
            TerminalListResponse.GeoCoordinateInfo geoCoordinateInfo = new TerminalListResponse.GeoCoordinateInfo();
            geoCoordinateInfo.setLatitude(Double.valueOf(terminal.getLat()));
            geoCoordinateInfo.setLongitude(Double.valueOf(terminal.getLng()));
            info.setGeoCoordinate(geoCoordinateInfo);
        }
        
        // LED状态信息
        TerminalListResponse.LedStatusInfo ledStatusInfo = new TerminalListResponse.LedStatusInfo();
        // 这里可以设置各种状态信息，暂时使用默认值
        ledStatusInfo.setTerminal(null);
        ledStatusInfo.setPowerStatus(null);
        ledStatusInfo.setInfo(null);
        ledStatusInfo.setVsns(null);
        ledStatusInfo.setDimension(null);
        ledStatusInfo.setVolume(null);
        ledStatusInfo.setInputMode(null);
        ledStatusInfo.setBrightnessAndColorTemp(null);
        ledStatusInfo.setReportTime(null);
        ledStatusInfo.setReportSwitch(null);
        ledStatusInfo.setNewRtc(null);
        ledStatusInfo.setInboundFirewall(null);
        ledStatusInfo.setWebSocketStatus(null);
        ledStatusInfo.setAllBrightnessInfo(null);
        ledStatusInfo.setBrightCurve(null);
        ledStatusInfo.setBrightnessVersion(null);
        ledStatusInfo.setCameraConfig(null);
        ledStatusInfo.setContentReport(null);
        ledStatusInfo.setContentReportInterval(null);
        ledStatusInfo.setIfStatus(null);
        ledStatusInfo.setLocale(null);
        ledStatusInfo.setRtc(null);
        ledStatusInfo.setSyncProgramMode(null);
        
        postMetaInfo.setLedStatus(ledStatusInfo);
        info.setPostMeta(postMetaInfo);
        
        return info;
    }
    
    @Override
    public TerminalListResponse getTerminalsByIds(TerminalListRequest request) {
        try {
            // 1. 验证终端ID数组
            if (request.getTerminalIds() == null || request.getTerminalIds().isEmpty()) {
                throw new RuntimeException("No terminal IDs provided");
            }
            
            // 2. 根据ID数组查询终端
            List<Terminal> terminals = terminalRepository.findAllById(request.getTerminalIds());
            
            // 3. 转换为响应DTO
            List<TerminalListResponse.TerminalInfo> terminalInfos = terminals.stream()
                .map(terminal -> convertToTerminalInfo(terminal, request))
                .collect(Collectors.toList());
            
            // 4. 构建响应
            TerminalListResponse response = new TerminalListResponse(terminalInfos, "Success");
            response.setTotal(terminals.size());
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminals by IDs: " + e.getMessage());
        }
    }
    
    @Override
    public TerminalListResponse getTerminalById(TerminalListRequest request) {
        try {
            // 1. 验证终端ID
            if (request.getTerminalIds() == null || request.getTerminalIds().isEmpty()) {
                throw new RuntimeException("No terminal ID provided");
            }
            
            Integer terminalId = request.getTerminalIds().get(0);
            
            // 2. 根据ID查询终端
            Terminal terminal = terminalRepository.findById(terminalId)
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + terminalId));
            
            // 3. 转换为响应DTO
            TerminalListResponse.TerminalInfo terminalInfo = convertToTerminalInfo(terminal, request);
            
            // 4. 构建响应
            List<TerminalListResponse.TerminalInfo> terminalInfos = List.of(terminalInfo);
            TerminalListResponse response = new TerminalListResponse(terminalInfos, "Success");
            response.setTotal(1);
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal by ID: " + e.getMessage());
        }
    }
    
    @Override
    public List<TerminalGpsResponse> getGpsTerminalList(Integer terminalGroup) {
        try {
            List<Terminal> terminals;
            
            // 根据终端组过滤
            if (terminalGroup != null) {
                terminals = terminalRepository.findByTerminalGroupId(terminalGroup);
            } else {
                terminals = terminalRepository.findAll();
            }
            
            // 过滤有GPS坐标的终端并转换为GPS响应格式
            return terminals.stream()
                .filter(terminal -> hasGpsCoordinate(terminal))
                .map(this::convertToGpsResponse)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            throw new RuntimeException("Failed to get GPS terminal list: " + e.getMessage());
        }
    }
    
    /**
     * 检查终端是否有GPS坐标
     */
    private boolean hasGpsCoordinate(Terminal terminal) {
        // 这里需要根据实际的数据库字段来判断是否有GPS坐标
        // 假设有lat和lng字段，且不为空
        return terminal.getLat() != null && terminal.getLng() != null 
               && terminal.getLat() != 0 && terminal.getLng() != 0;
    }
    
    /**
     * 转换为GPS响应格式
     */
    private TerminalGpsResponse convertToGpsResponse(Terminal terminal) {
        TerminalGpsResponse response = new TerminalGpsResponse();
        
        // 设置基本信息
        response.setId(terminal.getSerialNo());
        response.setName(terminal.getDeviceName());
        response.setTerminalgroupId(terminal.getTerminalGroup() != null ? 
            terminal.getTerminalGroup().getId().toString() : "");
        
        // 设置时间信息（这里需要根据实际字段调整）
        response.setLast_report_time(String.valueOf(System.currentTimeMillis() / 1000));
        response.set_led_latest_report_time(String.valueOf(System.currentTimeMillis() / 1000));
        
        // 设置位置信息
        TerminalGpsResponse.Position position = new TerminalGpsResponse.Position();
        position.setLedId(terminal.getSerialNo());
        position.setLatitude(terminal.getLat() != null ? terminal.getLat().floatValue() : 0.0f);
        position.setLongitude(terminal.getLng() != null ? terminal.getLng().floatValue() : 0.0f);
        position.setId(0);
        position.setSensorId(0);
        
        response.setPosition(position);
        
        return response;
    }
    
    @Override
    public TerminalGpsLatestResponse getLatestGpsSingle(TerminalGpsLatestRequest request) {
        try {
            // 1. 验证请求
            if (request.getTerminalId() == null) {
                throw new RuntimeException("Terminal ID is required");
            }
            
            // 2. 查询终端
            Terminal terminal = terminalRepository.findById(request.getTerminalId())
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + request.getTerminalId()));
            
            // 3. 构建GPS响应
            return buildGpsLatestResponse(terminal);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get latest GPS for single terminal: " + e.getMessage());
        }
    }
    
    @Override
    public List<TerminalGpsLatestResponse> getLatestGpsMultiple(TerminalGpsLatestMultipleRequest request) {
        try {
            // 1. 验证请求
            if (request.getTerminalIds() == null || request.getTerminalIds().isEmpty()) {
                throw new RuntimeException("Terminal IDs are required");
            }
            
            // 2. 查询终端列表
            List<Terminal> terminals = terminalRepository.findAllById(request.getTerminalIds());
            
            // 3. 构建GPS响应列表
            return terminals.stream()
                .map(this::buildGpsLatestResponse)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            throw new RuntimeException("Failed to get latest GPS for multiple terminals: " + e.getMessage());
        }
    }
    
    @Override
    public TerminalGpsV2Response getLatestGpsV2(Integer terminalId) {
        try {
            // 1. 验证终端ID
            if (terminalId == null || terminalId <= 0) {
                throw new RuntimeException("Invalid terminal ID");
            }
            
            // 2. 查询终端
            Terminal terminal = terminalRepository.findById(terminalId)
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + terminalId));
            
            // 3. 构建v2格式的GPS响应
            return buildGpsV2Response(terminal);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get latest GPS v2: " + e.getMessage());
        }
    }
    
    /**
     * 构建最新GPS响应 (v3)
     */
    private TerminalGpsLatestResponse buildGpsLatestResponse(Terminal terminal) {
        TerminalGpsLatestResponse response = new TerminalGpsLatestResponse();
        
        // 设置基本信息
        response.setTerminalId(terminal.getSerialNo());
        response.setTerminalName(terminal.getDeviceName());
        
        // 设置时间信息（模拟数据，实际应该从GPS记录表获取）
        String currentTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        response.setReportTime(currentTime);
        response.setServerTime(currentTime);
        response.setClientTime(currentTime);
        response.setLatestReportTime(currentTime);
        
        // 设置GPS信息
        response.setSensorId(20); // 默认传感器ID
        response.setLongitude(terminal.getLng() != null ? terminal.getLng() : 0.0f);
        response.setLatitude(terminal.getLat() != null ? terminal.getLat() : 0.0f);
        response.setAccuracy(1.5f); // 默认精度
        response.setAltitude(100.0f); // 默认高度
        response.setSpeed(0.0f); // 默认速度
        response.setDirect(0.0f); // 默认方向
        response.setSatellites(6); // 默认卫星数量
        response.setCellInfo(null);
        response.setGsv(null);
        response.setManual(null);
        
        return response;
    }
    
    /**
     * 构建v2格式的GPS响应
     */
    private TerminalGpsV2Response buildGpsV2Response(Terminal terminal) {
        TerminalGpsV2Response response = new TerminalGpsV2Response();
        
        // 设置基本信息
        response.setId(terminal.getSerialNo());
        response.setName(terminal.getDeviceName());
        response.setDescription(terminal.getLedDescription() != null ? terminal.getLedDescription() : "");
        response.setLastReportTime(String.valueOf(System.currentTimeMillis() / 1000));
        response.setGsv(null);
        
        // 构建最新GPS信息
        TerminalGpsV2Response.LatestGps latestGps = new TerminalGpsV2Response.LatestGps();
        
        // 构建GPS坐标信息
        TerminalGpsV2Response.LatestGpsCoordinate coordinate = new TerminalGpsV2Response.LatestGpsCoordinate();
        coordinate.setId(1); // 模拟坐标ID
        coordinate.setLedId(terminal.getSerialNo());
        coordinate.setSensorId(20);
        coordinate.setSensorType("gps");
        coordinate.setLongitude(terminal.getLng() != null ? terminal.getLng() : 0.0f);
        coordinate.setLatitude(terminal.getLat() != null ? terminal.getLat() : 0.0f);
        coordinate.setAccuracy(0.8f);
        coordinate.setAltitude(9.9f);
        coordinate.setSpeed(1.0f);
        coordinate.setDate(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        coordinate.setDirect(57.3f);
        coordinate.setSatellites(7);
        coordinate.setCellInfo(null);
        coordinate.setGsv(null);
        coordinate.setServerTime(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        latestGps.setLatestGpsCoordinate(coordinate);
        latestGps.setLatestGpsReportTime(System.currentTimeMillis());
        
        response.setLatestGps(latestGps);
        
        return response;
    }
    
    @Override
    public TerminalTrackResponse getTerminalTrack(TerminalTrackRequest request) {
        try {
            // 1. 验证请求参数
            if (request.getTerminalId() == null || request.getStartTime() == null || request.getEndTime() == null) {
                throw new RuntimeException("Terminal ID, start time and end time are required");
            }
            
            // 2. 解析终端ID
            Integer terminalId = Integer.parseInt(request.getTerminalId());
            
            // 3. 查询终端信息
            Terminal terminal = terminalRepository.findById(terminalId)
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + terminalId));
            
            // 4. 生成模拟轨迹数据（实际应该从GPS记录表查询）
            List<TerminalTrackResponse.TrackData> trackData = generateMockTrackData(request.getStartTime(), request.getEndTime());
            
            // 5. 构建响应
            return new TerminalTrackResponse(
                terminal.getSerialNo(),
                terminal.getDeviceName(),
                request.getStartTime(),
                request.getEndTime(),
                trackData
            );
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal track: " + e.getMessage());
        }
    }
    
    @Override
    public TerminalMileageResponse getTerminalMileage(TerminalMileageRequest request) {
        try {
            // 1. 验证请求参数
            if (request.getTerminalIds() == null || request.getTerminalIds().isEmpty() ||
                request.getStartTime() == null || request.getEndTime() == null) {
                throw new RuntimeException("Terminal IDs, start time and end time are required");
            }
            
            // 2. 查询终端列表
            List<Terminal> terminals = terminalRepository.findAllById(request.getTerminalIds());
            
            // 3. 计算每个终端的里程数
            List<TerminalMileageResponse.MileageData> mileageDataList = new ArrayList<>();
            float totalMileage = 0.0f;
            
            for (Terminal terminal : terminals) {
                float mileage = calculateMileage(terminal, request.getStartTime(), request.getEndTime());
                mileageDataList.add(new TerminalMileageResponse.MileageData(
                    terminal.getSerialNo(),
                    terminal.getDeviceName(),
                    mileage
                ));
                totalMileage += mileage;
            }
            
            // 4. 构建响应
            return new TerminalMileageResponse(
                request.getTerminalIds(),
                totalMileage,
                request.getStartTime(),
                request.getEndTime(),
                mileageDataList
            );
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminal mileage: " + e.getMessage());
        }
    }
    
    /**
     * 生成模拟轨迹数据
     */
    private List<TerminalTrackResponse.TrackData> generateMockTrackData(String startTime, String endTime) {
        List<TerminalTrackResponse.TrackData> trackData = new ArrayList<>();
        
        try {
            // 解析时间范围
            java.time.LocalDateTime start = java.time.LocalDateTime.parse(startTime);
            java.time.LocalDateTime end = java.time.LocalDateTime.parse(endTime);
            
            // 生成模拟轨迹点（每隔1分钟一个点）
            java.time.LocalDateTime current = start;
            int pointIndex = 0;
            
            while (!current.isAfter(end) && pointIndex < 10) { // 限制最多10个点
                // 模拟GPS坐标变化
                float baseLng = 113.943053f;
                float baseLat = 22.577767f;
                float lngOffset = (float) (pointIndex * 0.000001); // 微小的经度变化
                float latOffset = (float) (pointIndex * 0.000001); // 微小的纬度变化
                
                TerminalTrackResponse.TrackData data = new TerminalTrackResponse.TrackData();
                data.setLongitude(baseLng + lngOffset);
                data.setLatitude(baseLat + latOffset);
                data.setServerTime(current.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS")));
                data.setClientTime(current.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS")));
                
                trackData.add(data);
                
                current = current.plusMinutes(1);
                pointIndex++;
            }
            
        } catch (Exception e) {
            // 如果时间解析失败，返回空列表
            System.out.println("Failed to parse time range, returning empty track data");
        }
        
        return trackData;
    }
    
    /**
     * 计算终端里程数
     */
    private float calculateMileage(Terminal terminal, String startTime, String endTime) {
        // 这里应该根据实际的GPS轨迹数据计算里程数
        // 目前返回模拟数据
        try {
            // 解析时间范围
            java.time.LocalDateTime start = java.time.LocalDateTime.parse(startTime);
            java.time.LocalDateTime end = java.time.LocalDateTime.parse(endTime);
            
            // 计算时间差（小时）
            long hours = java.time.Duration.between(start, end).toHours();
            
            // 模拟里程数：每小时0.001公里
            return (float) (hours * 0.001);
            
        } catch (Exception e) {
            // 如果时间解析失败，返回默认值
            return 0.004242756490152385f;
        }
    }
    
    @Override
    public TerminalUpdateResponse updateTerminal(TerminalUpdateRequest request) {
        try {
            // 1. 验证请求参数
            validateUpdateRequest(request);
            
            // 2. 查询终端
            Terminal terminal = terminalRepository.findById(request.getTerminal().getId())
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + request.getTerminal().getId()));
            
            // 3. 验证终端名称唯一性（如果不强制）
            if (!Boolean.TRUE.equals(request.getForce())) {
                validateTerminalNameUnique(request.getTerminal().getTitle(), request.getTerminal().getId());
            }
            
            // 4. 查询目标终端组
            TerminalGroup targetGroup = null;
            if (request.getTerminal().getTerminalGroup() != null && !request.getTerminal().getTerminalGroup().isEmpty()) {
                Long groupId = request.getTerminal().getTerminalGroup().get(0).longValue();
                targetGroup = terminalGroupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Terminal group not found with ID: " + groupId));
            }
            
            // 5. 更新终端信息
            updateTerminalInfo(terminal, request.getTerminal(), targetGroup);
            
            // 6. 保存更新
            terminalRepository.save(terminal);
            
            // 7. 返回成功响应
            return new TerminalUpdateResponse("200", "modify terminal.", null);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to update terminal: " + e.getMessage());
        }
    }
    
    /**
     * 验证更新请求参数
     */
    private void validateUpdateRequest(TerminalUpdateRequest request) {
        if (request.getTerminal() == null) {
            throw new RuntimeException("Terminal information is required");
        }
        
        if (request.getTerminal().getId() == null) {
            throw new RuntimeException("Terminal ID is required");
        }
        
        if (request.getTerminal().getTitle() == null || request.getTerminal().getTitle().trim().isEmpty()) {
            throw new RuntimeException("Terminal title is required");
        }
        
        if (!"publish".equals(request.getTerminal().getStatus())) {
            throw new RuntimeException("Terminal status must be 'publish'");
        }
        
        if (request.getTerminal().getTerminalGroup() == null || request.getTerminal().getTerminalGroup().isEmpty()) {
            throw new RuntimeException("Terminal group ID is required");
        }
        
        if (request.getRoles() == null || request.getRoles().isEmpty() || 
            !request.getRoles().contains("terminal")) {
            throw new RuntimeException("Roles must contain 'terminal'");
        }
    }
    
    /**
     * 验证终端名称唯一性
     */
    private void validateTerminalNameUnique(String title, Integer excludeId) {
        Optional<Terminal> existingTerminal = terminalRepository.findByDeviceName(title);
        if (existingTerminal.isPresent() && !existingTerminal.get().getSerialNo().equals(excludeId)) {
            throw new RuntimeException("Terminal name already exists: " + title);
        }
    }
    
    /**
     * 更新终端信息
     */
    private void updateTerminalInfo(Terminal terminal, TerminalUpdateRequest.TerminalInfo terminalInfo, TerminalGroup targetGroup) {
        // 更新终端名称
        terminal.setDeviceName(terminalInfo.getTitle());
        
        // 更新终端组
        if (targetGroup != null) {
            terminal.setTerminalGroup(targetGroup);
        }
        
        // 更新安装时间（如果有提供）
        if (terminalInfo.getInstallDateUTC() != null && !terminalInfo.getInstallDateUTC().trim().isEmpty()) {
            try {
                java.time.LocalDateTime installDateTime = java.time.LocalDateTime.parse(
                    terminalInfo.getInstallDateUTC(), 
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                );
                terminal.setCreatedAt(java.sql.Timestamp.valueOf(installDateTime));
            } catch (Exception e) {
                System.out.println("Failed to parse install date: " + terminalInfo.getInstallDateUTC());
                // 如果解析失败，不更新安装时间
            }
        }
    }
    
    @Override
    public TerminalListResponse getTerminalsByProgram(Integer programId, Integer page, Integer perPage) {
        try {
            // 设置默认值
            page = page == null ? 1 : page;
            perPage = perPage == null ? 12 : perPage;
            
            // 验证节目ID
            if (programId == null || programId <= 0) {
                throw new RuntimeException("Invalid program ID");
            }
            
            // 根据节目ID查询终端ID列表
            List<Integer> terminalIds = programTerminalRelationRepository.findTerminalIdsByProgramId(programId);
            if (terminalIds.isEmpty()) {
                return new TerminalListResponse(new ArrayList<>(), "No terminals found for program: " + programId);
            }
            
            // 计算分页
            int startIndex = (page - 1) * perPage;
            int endIndex = Math.min(startIndex + perPage, terminalIds.size());
            
            // 获取当前页的终端ID
            List<Integer> pageTerminalIds = terminalIds.subList(startIndex, endIndex);
            
            // 查询终端信息
            List<Terminal> terminals = terminalRepository.findAllById(pageTerminalIds);
            
            // 转换为响应对象
            List<TerminalListResponse.TerminalInfo> terminalInfos = terminals.stream()
                .map(terminal -> convertToTerminalInfo(terminal, null))
                .collect(Collectors.toList());
            
            TerminalListResponse response = new TerminalListResponse(terminalInfos, "Success");
            response.setTotal(terminalIds.size());
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to get terminals by program: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public TerminalDeleteResponse deleteTerminal(Integer terminalId) {
        try {
            // 1. 查询终端
            Terminal terminal = terminalRepository.findById(terminalId)
                .orElseThrow(() -> new RuntimeException("Terminal not found with ID: " + terminalId));
            
            // 2. 构建删除响应（在删除前获取终端信息）
            TerminalDeleteResponse response = buildDeleteResponse(terminal);
            
            // 3. 执行删除操作
            terminalRepository.delete(terminal);
            
            // 4. 返回删除响应
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete terminal: " + e.getMessage());
        }
    }
    
    /**
     * 构建删除响应
     */
    private TerminalDeleteResponse buildDeleteResponse(Terminal terminal) {
        // 获取当前时间
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String currentTime = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+08:00"));
        String gmtTime = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+08:00"));
        
        // 构建额外信息
        TerminalDeleteResponse.ExtraInfo extra = new TerminalDeleteResponse.ExtraInfo();
        extra.setAuthor_display_name("admin"); // 默认管理员
        
        // 构建标题信息
        TerminalDeleteResponse.TitleInfo title = new TerminalDeleteResponse.TitleInfo();
        title.setRendered("<p>" + terminal.getDeviceName() + "</p>");
        title.setRaw(terminal.getDeviceName());
        
        // 构建摘要信息
        TerminalDeleteResponse.ExcerptInfo excerpt = new TerminalDeleteResponse.ExcerptInfo();
        excerpt.setRendered("<p>" + terminal.getDeviceName() + "</p>");
        excerpt.setProtected(false);
        excerpt.setRaw(terminal.getDeviceName());
        
        return new TerminalDeleteResponse(
            currentTime,                    // date
            terminal.getSerialNo(),         // author (使用终端ID)
            extra,                          // extra
            terminal.getSerialNo(),         // id
            "led",                          // type
            gmtTime,                        // date_gmt
            title,                          // title
            excerpt,                        // excerpt
            "open",                         // comment_status
            "",                             // post_meta
            new Object[0]                   // TriggerEmails
        );
    }
} 