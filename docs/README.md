# 项目文档体系

## 文档分类概览

本项目采用分层文档体系，确保开发过程的连续性和可维护性。

```
docs/
├── README.md                           # 本文档 - 文档体系概览
├── documentation_overview.md           # 文档概览 - 详细介绍所有文档功能
├── development/                        # 📋 开发指南类文档
│   ├── development_continuity_guide.md # 开发连续性指南 - 核心开发流程
│   ├── project_structure_guide.md     # 项目结构指南 - 目录结构说明
│   └── development_checklist.md       # 开发检查清单 - 开发规范检查
├── architecture/                       # 🔧 技术架构类文档
│   ├── architecture_decision_record.md # 架构决策记录 - 重要技术决策
│   ├── springboot_layered_architecture.md # Spring Boot分层架构
│   └── django_to_springboot_migration.md # Django到Spring Boot迁移
├── database/                          # 🗄️ 数据库类文档
│   ├── database_migration_guide.md    # 数据库迁移指南 - 关键报错点记录
│   ├── cloud_platform_er_design.md   # 云平台ER设计 - 数据库设计
│   └── logging_api_als.md            # 日志API设计 - 日志系统设计
└── records/                          # 📝 记录类文档
    └── ai_chat_history.md            # AI聊天历史 - 开发过程记录
```

## 核心文档说明

### 1. 开发连续性指南 (development/development_continuity_guide.md)
**最重要的文档** - 强调开发的连续性，确保从Entity到Controller的完整开发流程。

**核心原则**:
- **四层联动开发**: Entity、SQL、DTO、Repository必须一起开发
- **接口先行**: 每个Repository必须为Service层提供清晰的接口定义
- **测试驱动**: 每个层次开发完成后必须进行测试
- **文档记录**: 记录已完成模块的接口定义和待开发内容

**关键规则**:
1. 前四个层次（Entity、SQL、DTO、Repository）必须一起开发
2. 每个Repository必须为Service层提供完整的接口定义
3. 开发完成后必须记录向上层提供的接口
4. 测试必须覆盖所有开发的层次

### 2. 数据库迁移指南 (database/database_migration_guide.md)
**重要文档** - 记录数据库迁移的关键报错点和解决方案。

**包含内容**:
- 当前数据库配置
- 关键报错点及解决方案
- 迁移脚本顺序
- 测试环境设置
- 最佳实践

**关键报错点**:
1. Flyway迁移校验失败
2. 数据库密码认证失败
3. 数据库配置冲突
4. GBK编码错误

### 3. 架构决策记录 (architecture/architecture_decision_record.md)
**重要文档** - 记录重要的架构决策和解决方案。

**包含内容**:
- Spring Boot与Maven测试环境冲突解决方案
- 数据库配置冲突解决方案
- 编码问题解决方案
- 架构评估和风险评估

### 4. 文档概览 (documentation_overview.md)
**导航文档** - 详细介绍所有文档功能和宏观规则。

**包含内容**:
- 文档体系架构
- 文档分类说明
- 宏观规则体系
- 文档使用指南
- 文档维护规范

## 宏观规则体系

### 1. 开发连续性规则

#### 四层联动开发规则
```
Entity + SQL + DTO + Repository = 必须一起开发
```

**原因**:
- 确保数据模型一致性
- 避免字段映射错误
- 保证类型安全
- 减少返工成本

#### 接口先行规则
```
Repository → Service → Controller
```

**要求**:
- Repository必须为Service提供完整接口
- Service必须为Controller提供完整接口
- 接口定义必须清晰明确

#### 测试驱动规则
```
开发完成 → 立即测试 → 记录结果
```

**要求**:
- 每个层次开发完成后立即测试
- 测试必须覆盖所有功能
- 测试结果必须记录

### 2. 文档记录规则

#### 必须记录的文档
1. **接口文档**: Repository和Service接口定义
2. **测试文档**: 测试用例和结果
3. **迁移文档**: SQL脚本说明
4. **API文档**: Controller接口说明

#### 文档更新规则
- 代码变更时同步更新文档
- 问题解决后记录解决方案
- 定期检查和维护文档

### 3. 质量保证规则

#### 代码质量规则
- 遵循编码规范
- 添加必要注释
- 进行代码审查

#### 测试覆盖规则
- 单元测试覆盖率 > 80%
- 集成测试覆盖主要流程
- 端到端测试覆盖关键路径

#### 文档完整性规则
- 接口文档完整
- 测试文档详细
- 部署文档清晰

## 文档使用指南

### 1. 新开发者入门
1. 阅读 **文档概览** (documentation_overview.md)
2. 学习 **开发连续性指南** (development/development_continuity_guide.md)
3. 查看 **项目结构指南** (development/project_structure_guide.md)
4. 参考 **开发检查清单** (development/development_checklist.md)

### 2. 开发新功能
1. 查看 **开发连续性指南** 了解开发流程
2. 参考 **数据库迁移指南** 处理数据库问题
3. 查看 **架构决策记录** 了解技术选择
4. 更新相关文档记录开发进度

### 3. 解决问题
1. 查看 **数据库迁移指南** 中的关键报错点
2. 参考 **架构决策记录** 中的解决方案
3. 查看 **AI聊天历史** 中的类似问题
4. 记录新的解决方案到相应文档

### 4. 代码审查
1. 检查是否遵循 **开发连续性指南**
2. 验证是否更新了相关文档
3. 确认测试覆盖是否满足要求
4. 检查代码质量是否符合规范

## 当前开发状态

### 已完成模块

#### User模块 ✅
- **Entity**: ✅ 已完成
- **SQL**: ✅ 已完成 (V1__create_users.sql)
- **DTO**: ✅ 已完成
- **Repository**: ✅ 已完成
- **Service**: ⏳ 待开发
- **Controller**: ⏳ 待开发

#### Program模块 ✅
- **Entity**: ✅ 已完成 (Program.java)
- **SQL**: ✅ 已完成 (V9__create_programs.sql, V15__add_missing_program_fields.sql)
- **DTO**: ✅ 已完成 (ProgramCreateRequest, ProgramResponse, ProgramListResponse等)
- **Repository**: ✅ 已完成 (ProgramRepository.java)
- **Service**: ✅ 已完成 (ProgramService.java, SimplifyCreateProgramService.java)
- **Controller**: ✅ 已完成 (ProgramController.java, ProgramInfoController.java)

#### Media模块 ✅
- **Entity**: ✅ 已完成 (Media.java)
- **SQL**: ✅ 已完成 (V10__create_media.sql)
- **DTO**: ✅ 已完成 (MediaUploadResponse, MediaDeleteResponse, MediaInfo等)
- **Repository**: ✅ 已完成 (MediaRepository.java)
- **Service**: ✅ 已完成 (MediaService.java)
- **Controller**: ✅ 已完成 (MediaController.java)

#### Schedule模块 ✅
- **Entity**: ✅ 已完成 (相关实体类)
- **SQL**: ✅ 已完成 (相关迁移脚本)
- **DTO**: ✅ 已完成 (UpdateTerminalScheduleRequest, TerminalScheduleResponse等)
- **Repository**: ✅ 已完成 (相关Repository接口)
- **Service**: ✅ 已完成 (TerminalScheduleService.java, TerminalGroupScheduleService.java)
- **Controller**: ✅ 已完成 (ScheduleController.java, TerminalGroupScheduleController.java)

#### Terminal模块 ✅
- **Entity**: ✅ 已完成 (Terminal.java, TerminalGroup.java等)
- **SQL**: ✅ 已完成 (V2__create_terminal_groups.sql, V3__create_terminals.sql, V14__add_missing_terminal_fields.sql)
- **DTO**: ✅ 已完成 (TerminalCreateRequest, TerminalResponse等)
- **Repository**: ✅ 已完成 (TerminalRepository.java, TerminalGroupRepository.java)
- **Service**: ✅ 已完成 (TerminalService.java, TerminalTagService.java)
- **Controller**: ✅ 已完成 (TerminalController.java, TerminalTagController.java)

#### Command模块 ✅
- **Entity**: ✅ 已完成 (Command.java, CommandResult.java)
- **SQL**: ✅ 已完成 (V4__create_commands.sql, V6__create_command_results.sql)
- **DTO**: ✅ 已完成 (CommandRequest, CommandResponse等)
- **Repository**: ✅ 已完成 (CommandRepository.java, CommandResultRepository.java)
- **Service**: ✅ 已完成 (FtdBckCommandService.java, TmnlBckGetCommandService.java)
- **Controller**: ✅ 已完成 (FtdBckCommandController.java, TmnlBckCommandController.java)

#### TUS模块 ✅
- **Entity**: ✅ 已完成 (TusUpload.java)
- **SQL**: ✅ 已完成 (V8__create_tus_upload.sql)
- **DTO**: ✅ 已完成 (TusUploadResponse等)
- **Repository**: ✅ 已完成 (TusUploadRepository.java)
- **Service**: ✅ 已完成 (TusService.java)
- **Controller**: ✅ 已完成 (TusController.java)

### 下一步计划

#### 短期目标 (1-2周)
1. **完善现有模块**
   - 补充单元测试
   - 完善API文档
   - 优化错误处理

2. **系统集成测试**
   - 模块间集成测试
   - 端到端功能测试
   - 性能压力测试

#### 中期目标 (1个月)
1. **功能完善**
   - 用户权限系统
   - 日志记录系统
   - 监控告警系统

2. **性能优化**
   - 数据库查询优化
   - 缓存机制实现
   - 并发处理优化

#### 长期目标 (2-3个月)
1. **系统集成**
   - 模块间集成测试
   - 性能优化
   - 安全加固

2. **生产部署**
   - 部署环境准备
   - 监控告警配置
   - 运维文档完善

## 文档维护规范

### 1. 更新频率
- **核心文档**: 每次重要变更后立即更新
- **技术文档**: 技术栈变更时更新
- **记录文档**: 持续更新

### 2. 更新内容
- 新增功能说明
- 问题解决方案
- 最佳实践总结
- 经验教训记录

### 3. 版本控制
- 文档变更需要提交到Git
- 重要变更需要添加变更说明
- 定期检查文档一致性

## 联系信息

如有文档相关问题或建议，请：
1. 查看相关文档分类
2. 参考文档使用指南
3. 记录新的解决方案
4. 更新相关文档 