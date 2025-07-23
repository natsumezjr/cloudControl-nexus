# 模块开发记录

## 概述

本文档记录了backend中新增模块的详细开发情况，包括开发时间、功能特性、技术实现等。

## 新增模块概览

### 开发时间
- **开始时间**: 2025年7月
- **完成时间**: 2025年7月
- **开发周期**: 约1个月

### 新增模块列表
1. **Program模块** - 节目管理
2. **Media模块** - 媒体文件管理
3. **Schedule模块** - 排程管理
4. **Terminal模块** - 终端管理
5. **Command模块** - 命令管理
6. **TUS模块** - 大文件上传

## 详细模块记录

### 1. Program模块 (节目管理)

#### 功能特性
- **节目创建**: 支持创建新的节目内容
- **节目列表**: 支持分页查询和条件筛选
- **节目详情**: 获取单个节目的详细信息
- **节目更新**: 支持节目内容的修改
- **节目删除**: 支持单个和批量删除
- **节目发布**: 支持节目的发布和取消发布

#### 技术实现
- **Entity**: `Program.java` - 节目实体类
- **SQL**: `V9__create_programs.sql` - 创建节目表
- **DTO**: 
  - `ProgramCreateRequest` - 创建节目请求
  - `ProgramResponse` - 节目响应
  - `ProgramListResponse` - 节目列表响应
  - `ProgramBatchDeleteRequest` - 批量删除请求
- **Repository**: `ProgramRepository.java` - 节目数据访问层
- **Service**: 
  - `ProgramService.java` - 节目业务逻辑
  - `SimplifyCreateProgramService.java` - 简化创建服务
- **Controller**: 
  - `ProgramController.java` - 节目控制器
  - `ProgramInfoController.java` - 节目信息控制器

#### API接口
- `POST /wp-json/wp/v2/programs` - 创建节目
- `GET /wp-json/wp/v2/programs` - 获取节目列表
- `GET /wp-json/wp/v2/programs/{programId}` - 获取单个节目
- `PUT /wp-json/wp/v2/programs/{programId}` - 更新节目
- `DELETE /wp-json/wp/v2/programs/{programId}` - 删除节目
- `POST /wp-json/wp/v2/programs/delprograms` - 批量删除节目
- `DELETE /wp-json/wp/v2/programPublishRecords` - 取消发布节目

#### 数据库设计
```sql
CREATE TABLE programs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    program_info TEXT,
    programs_data TEXT,
    author INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. Media模块 (媒体文件管理)

#### 功能特性
- **文件上传**: 支持多种媒体文件上传
- **文件删除**: 支持媒体文件删除
- **文件信息**: 获取媒体文件详细信息
- **文件列表**: 支持媒体文件列表查询
- **缩略图**: 自动生成媒体缩略图
- **文件类型**: 支持图片、视频、音频等多种类型

#### 技术实现
- **Entity**: `Media.java` - 媒体实体类
- **SQL**: `V10__create_media.sql` - 创建媒体表
- **DTO**: 
  - `MediaUploadResponse` - 上传响应
  - `MediaDeleteResponse` - 删除响应
  - `MediaInfo` - 媒体信息
- **Repository**: `MediaRepository.java` - 媒体数据访问层
- **Service**: `MediaService.java` - 媒体业务逻辑
- **Controller**: `MediaController.java` - 媒体控制器

#### 支持的媒体类型
- **图片**: image/jpeg, image/png, image/gif等
- **视频**: video/mp4, video/avi, video/mov等
- **音频**: audio/mp3, audio/wav, audio/aac等
- **文档**: application/pdf, text/plain等

#### 数据库设计
```sql
CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title_raw VARCHAR(255),
    description TEXT,
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    media_type VARCHAR(50),
    attachment_filesize BIGINT,
    file_path VARCHAR(500),
    thumbnail_path VARCHAR(500),
    width INTEGER,
    height INTEGER,
    duration INTEGER,
    author INTEGER,
    guid VARCHAR(255),
    slug VARCHAR(255),
    source_url VARCHAR(500),
    src VARCHAR(500),
    post_status VARCHAR(50) DEFAULT 'inherit',
    comment_status VARCHAR(50) DEFAULT '',
    ping_status VARCHAR(50) DEFAULT '',
    parent INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Schedule模块 (排程管理)

#### 功能特性
- **终端排程**: 管理单个终端的播放排程
- **终端组排程**: 管理终端组的批量排程
- **排程状态**: 跟踪排程的执行状态
- **权限控制**: 基于用户权限的排程管理
- **错误处理**: 完善的排程错误处理机制

#### 技术实现
- **Entity**: 相关排程实体类
- **SQL**: 相关迁移脚本
- **DTO**: 
  - `UpdateTerminalScheduleRequest` - 更新终端排程请求
  - `TerminalScheduleResponse` - 终端排程响应
  - `ScheduleErrorResponse` - 排程错误响应
- **Repository**: 相关Repository接口
- **Service**: 
  - `TerminalScheduleService.java` - 终端排程服务
  - `TerminalGroupScheduleService.java` - 终端组排程服务
- **Controller**: 
  - `ScheduleController.java` - 排程控制器
  - `TerminalGroupScheduleController.java` - 终端组排程控制器

#### API接口
- `PUT /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules` - 更新终端排程
- `GET /wp-json/wp/v3/schedules/{terminalId}/terminalSchedules` - 获取终端排程
- `GET /wp-json/wp/v3/schedules/terminalGroupSchedules` - 获取终端组排程
- `POST /wp-json/wp/v3/schedules/applyTerminalGroupSchedules` - 应用终端组排程

### 4. Terminal模块 (终端管理)

#### 功能特性
- **终端管理**: 终端的创建、更新、删除
- **终端分组**: 终端的分组管理
- **终端标签**: 终端的标签管理
- **终端认证**: 终端身份认证
- **状态监控**: 终端状态实时监控
- **心跳检测**: 终端心跳检测机制

#### 技术实现
- **Entity**: 
  - `Terminal.java` - 终端实体类
  - `TerminalGroup.java` - 终端组实体类
- **SQL**: 
  - `V2__create_terminal_groups.sql` - 创建终端组表
  - `V3__create_terminals.sql` - 创建终端表
  - `V14__add_missing_terminal_fields.sql` - 添加缺失字段
- **DTO**: 
  - `TerminalCreateRequest` - 终端创建请求
  - `TerminalResponse` - 终端响应
- **Repository**: 
  - `TerminalRepository.java` - 终端数据访问层
  - `TerminalGroupRepository.java` - 终端组数据访问层
- **Service**: 
  - `TerminalService.java` - 终端业务逻辑
  - `TerminalTagService.java` - 终端标签服务
- **Controller**: 
  - `TerminalController.java` - 终端控制器
  - `TerminalTagController.java` - 终端标签控制器

### 5. Command模块 (命令管理)

#### 功能特性
- **命令创建**: 创建各种类型的命令
- **命令发送**: 向终端发送命令
- **状态跟踪**: 跟踪命令执行状态
- **结果查询**: 查询命令执行结果
- **历史记录**: 保存命令执行历史

#### 技术实现
- **Entity**: 
  - `Command.java` - 命令实体类
  - `CommandResult.java` - 命令结果实体类
- **SQL**: 
  - `V4__create_commands.sql` - 创建命令表
  - `V6__create_command_results.sql` - 创建命令结果表
- **DTO**: 
  - `CommandRequest` - 命令请求
  - `CommandResponse` - 命令响应
- **Repository**: 
  - `CommandRepository.java` - 命令数据访问层
  - `CommandResultRepository.java` - 命令结果数据访问层
- **Service**: 
  - `FtdBckCommandService.java` - 前端到后端命令服务
  - `TmnlBckGetCommandService.java` - 终端到后端获取命令服务
- **Controller**: 
  - `FtdBckCommandController.java` - 前端到后端命令控制器
  - `TmnlBckCommandController.java` - 终端到后端命令控制器

#### 命令类型
- **终端控制命令**: 控制终端行为
- **系统管理命令**: 系统级管理命令
- **数据同步命令**: 数据同步相关命令

#### 命令状态
- **PENDING**: 待执行
- **EXECUTING**: 执行中
- **SUCCESS**: 执行成功
- **FAILED**: 执行失败

### 6. TUS模块 (大文件上传)

#### 功能特性
- **分片上传**: 支持大文件分片上传
- **断点续传**: 支持上传中断后继续
- **进度跟踪**: 实时跟踪上传进度
- **状态管理**: 管理上传状态
- **错误处理**: 完善的错误处理机制

#### 技术实现
- **Entity**: `TusUpload.java` - TUS上传实体类
- **SQL**: `V8__create_tus_upload.sql` - 创建TUS上传表
- **DTO**: `TusUploadResponse` - TUS上传响应
- **Repository**: `TusUploadRepository.java` - TUS上传数据访问层
- **Service**: `TusService.java` - TUS上传业务逻辑
- **Controller**: `TusController.java` - TUS上传控制器

#### TUS协议支持
- 符合TUS协议标准
- 支持多种上传方式
- 错误处理和重试机制

## 数据库迁移记录

### 新增迁移脚本
1. `V8__create_tus_upload.sql` - 创建TUS上传表
2. `V9__create_programs.sql` - 创建节目表
3. `V10__create_media.sql` - 创建媒体表
4. `V11__create_program_terminal_relations.sql` - 创建节目终端关系表
5. `V12__create_terminal_tags.sql` - 创建终端标签表
6. `V13__create_terminal_tag_relations.sql` - 创建终端标签关系表
7. `V14__add_missing_terminal_fields.sql` - 添加缺失的终端字段
8. `V15__add_missing_program_fields.sql` - 添加缺失的节目字段

### 关系表设计
- **节目终端关系表**: 管理节目与终端的发布关系
- **终端标签关系表**: 管理终端与标签的关联关系
- **用户终端组关系表**: 管理用户与终端组的权限关系

## 技术架构特点

### 1. 分层架构
- **Controller层**: 处理HTTP请求和响应
- **Service层**: 实现业务逻辑
- **Repository层**: 数据访问层
- **Entity层**: 数据模型层
- **DTO层**: 数据传输对象

### 2. 设计模式
- **Repository模式**: 数据访问抽象
- **Service模式**: 业务逻辑封装
- **DTO模式**: 数据传输对象
- **Factory模式**: 对象创建工厂

### 3. 技术栈
- **Spring Boot**: 主框架
- **Spring Data JPA**: 数据访问
- **PostgreSQL**: 数据库
- **Flyway**: 数据库迁移
- **Maven**: 项目管理

## 开发规范遵循

### 1. 四层联动开发
- Entity、SQL、DTO、Repository必须一起开发
- 确保数据模型一致性
- 保证字段映射正确性

### 2. 接口先行
- Repository为Service提供清晰接口
- Service为Controller提供完整接口
- 接口定义必须明确

### 3. 测试驱动
- 每个层次开发完成后立即测试
- 测试覆盖所有功能
- 测试结果必须记录

## 质量保证

### 1. 代码质量
- 遵循编码规范
- 添加必要注释
- 进行代码审查

### 2. 测试覆盖
- 单元测试覆盖率 > 80%
- 集成测试覆盖主要流程
- 端到端测试覆盖关键路径

### 3. 文档完整性
- 接口文档完整
- 测试文档详细
- 部署文档清晰

## 下一步计划

### 短期目标 (1-2周)
1. **完善现有模块**
   - 补充单元测试
   - 完善API文档
   - 优化错误处理

2. **系统集成测试**
   - 模块间集成测试
   - 端到端功能测试
   - 性能压力测试

### 中期目标 (1个月)
1. **功能完善**
   - 用户权限系统
   - 日志记录系统
   - 监控告警系统

2. **性能优化**
   - 数据库查询优化
   - 缓存机制实现
   - 并发处理优化

### 长期目标 (2-3个月)
1. **系统集成**
   - 模块间集成测试
   - 性能优化
   - 安全加固

2. **生产部署**
   - 部署环境准备
   - 监控告警配置
   - 运维文档完善

## 总结

本次新增模块开发遵循了项目的开发连续性指南，严格按照四层联动开发的原则进行。所有模块都完成了从Entity到Controller的完整开发流程，并提供了详细的API文档。

新增的6个模块涵盖了云控制系统的核心功能：
- 节目管理：支持节目的创建、编辑、发布等完整生命周期
- 媒体管理：支持多种媒体文件的上传和管理
- 排程管理：支持终端和终端组的排程管理
- 终端管理：支持终端的完整管理功能
- 命令管理：支持系统命令的创建和执行
- 文件上传：支持大文件的分片上传

这些模块为云控制系统提供了完整的功能支持，为后续的系统集成和生产部署奠定了坚实的基础。 