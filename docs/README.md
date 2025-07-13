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

#### 其他模块 ⏳
- **Terminal**: Entity和SQL已完成，DTO和Repository待开发
- **TerminalGroup**: Entity和SQL已完成，DTO和Repository待开发
- **Command**: Entity和SQL已完成，DTO和Repository待开发

### 下一步计划

#### 短期目标 (1-2周)
1. **完成User模块Service层**
   - 实现用户注册服务
   - 实现用户登录服务
   - 实现用户管理服务

2. **开始Terminal模块开发**
   - 完成DTO设计
   - 完成Repository接口
   - 开始Service层开发

#### 中期目标 (1个月)
1. **完成所有基础模块**
   - Terminal模块完整开发
   - TerminalGroup模块完整开发
   - Command模块完整开发

2. **完善测试体系**
   - 提高测试覆盖率
   - 完善集成测试
   - 建立自动化测试

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