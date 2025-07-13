# CloudControl-Nexus 前端调试工具

这是一个基于Vue3的设备控制面板前端应用，用于调试和测试CloudControl-Nexus平台的设备指令功能。

## 🚀 功能特性

- **设备指令支持**: 支持基础的设备控制指令（唤醒、休眠、重启等）
- **批量指令支持**: 支持单条指令和批量指令发送
- **响应显示**: 显示每个指令的发送和响应数据
- **现代化UI**: 基于Element Plus的美观界面设计
- **调试功能**: 控制台日志输出和错误处理
- **响应式设计**: 支持不同屏幕尺寸

## 📋 支持的指令类型

### 基础控制指令
- 唤醒指令
- 休眠指令
- 重启指令
- 屏幕截屏
- 清理缓存

### 显示控制指令
- 亮度指令（0-255）
- 色温指令（2000-10000）
- 切换信号源（HDMI/DVI）

### 音频控制指令
- 音量指令（0-15级）

### 继电器控制指令
- 板载继电器控制
- 多路继电器控制

### 系统配置指令
- 系统升级
- 语言和地区设置
- 时区设置

### 监控配置指令
- GPS上报间隔设置
- 监控上报间隔设置
- 内容上报开关
- 日志上报开关
- 轮播节目名上报开关

### 节目管理指令
- 更新节目
- 切换节目
- 清空节目
- 删除节目

### 状态查询指令
- 运行日志上报
- 网络接口配置信息上报

## 🛠️ 安装和运行

### 环境要求
- Node.js 18+
- npm 或 yarn

### 安装依赖
```bash
cd frontend
npm install
```

### 启动开发服务器
```bash
npm run dev
```

应用将在 `http://localhost:3000` 启动

### 构建生产版本
```bash
npm run build
```

## 📖 使用说明

### 1. 配置设备信息
在控制面板顶部输入：
- **终端ID**: 设备的唯一标识符（数字）
- **设备名称**: 设备的显示名称
- **指令模式**: 选择单条指令或批量指令模式

### 2. 发送单条指令
1. 选择要执行的指令类型
2. 点击对应的指令按钮
3. 系统会自动生成符合接口格式的指令数据
4. 指令会发送到后端API（`/wp-json/wp/v2/comments`）
5. 响应结果会显示在页面底部

### 3. 发送批量指令
1. 点击"添加到批量队列"按钮将指令添加到队列
2. 在批量指令队列中查看已添加的指令
3. 可以删除不需要的指令
4. 点击"发送批量指令"一次性发送所有指令
5. 发送完成后队列会自动清空

### 4. 查看响应
- 成功响应会显示完整的JSON数据
- 错误响应会显示错误信息和错误代码
- 所有请求和响应都会在浏览器控制台输出详细日志

## 🔧 配置说明

### API配置
在 `src/api/deviceApi.ts` 中可以修改：
- API基础URL
- 请求超时时间
- 请求头配置

### 代理配置
在 `vite.config.ts` 中配置了API代理：
```typescript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, '')
  }
}
```

## 📁 项目结构

```
frontend/
├── src/
│   ├── components/
│   │   └── DeviceControlPanel.vue    # 主控制面板组件
│   ├── api/
│   │   └── deviceApi.ts              # API接口定义
│   ├── router/
│   │   └── index.ts                  # 路由配置
│   ├── styles/
│   │   └── global.css                # 全局样式
│   ├── App.vue                       # 根组件
│   ├── main.ts                       # 应用入口
│   └── env.d.ts                      # 环境变量类型声明
├── docs/
│   └── command-reference.md          # 指令参考文档
├── package.json                      # 项目依赖
├── vite.config.ts                    # Vite配置
├── tsconfig.json                     # TypeScript配置
├── tsconfig.node.json                # Node.js TypeScript配置
├── index.html                        # HTML模板
├── README.md                         # 项目说明
├── QUICK_START.md                    # 快速开始指南
└── BEAUTIFICATION.md                 # 美化说明文档
```

## 🔍 调试技巧

### 1. 查看网络请求
- 打开浏览器开发者工具 (F12)
- 切换到Network标签页
- 发送指令后查看 `/wp-json/wp/v2/comments` 请求

### 2. 查看控制台日志
- 所有指令数据都会在控制台输出
- 包含请求数据、响应数据和错误信息

### 3. 模拟后端响应
如果后端还未完成，可以修改 `deviceApi.ts` 来模拟响应：
```typescript
// 在sendDeviceCommand函数中添加模拟响应
if (process.env.NODE_ENV === 'development') {
  return {
    success: true,
    code: 200,
    message: '模拟响应成功',
    data: { /* 模拟数据 */ },
    timestamp: Date.now()
  }
}
```

## 📚 相关文档

- [指令参考文档](./docs/command-reference.md) - 详细的指令格式说明
- [快速开始指南](./QUICK_START.md) - 5分钟快速上手
- [美化说明文档](./BEAUTIFICATION.md) - 前端美化详情
- [开发文档](https://developer.colorlightcloud.com/cloudServer/) - 官方开发文档

## 🤝 贡献

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 