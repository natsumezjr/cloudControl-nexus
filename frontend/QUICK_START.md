# 快速开始指南

## 🚀 5分钟快速启动

### 1. 检查环境
确保已安装：
- Node.js 18+
- npm 或 yarn

### 2. 安装依赖
```bash
cd frontend
npm install
```

### 3. 启动应用
```bash
npm run dev
```

### 4. 访问应用
打开浏览器访问：`http://localhost:3000`

## 📋 使用步骤

### 第一步：配置设备信息
在页面顶部输入：
- **终端ID**: 例如 `1`（必须是数字）
- **设备名称**: 例如 `测试设备`
- **指令模式**: 选择 `单条指令` 或 `批量指令`

### 第二步：测试基础指令
1. 点击 **"唤醒指令"** 按钮
2. 查看页面底部的响应结果
3. 检查浏览器控制台的详细日志

### 第三步：测试其他指令
- **显示控制**: 测试亮度、色温、信号源切换
- **音频控制**: 测试音量调节
- **继电器控制**: 测试板载继电器和多路继电器
- **系统配置**: 测试升级、语言、时区设置
- **监控配置**: 测试各种上报间隔和开关设置
- **节目管理**: 测试节目的更新、切换、清空、删除
- **状态查询**: 测试日志上报和网络接口信息

### 第四步：测试批量指令
1. 点击多个指令的 **"添加到批量队列"** 按钮
2. 在批量指令队列中查看已添加的指令
3. 点击 **"发送批量指令"** 一次性发送所有指令

## 🔧 后端对接

### API端点
- **URL**: `/wp-json/wp/v2/comments`
- **方法**: POST
- **格式**: JSON

### 单条指令格式
```json
{
  "post": 1,
  "metadata": {
    "act_url": "api/action",
    "act_method": 1
  },
  "content": "{\"command\":\"wakeup\"}"
}
```

### 批量指令格式
```json
{
  "post": 1,
  "CommentData": [
    {
      "post": 1,
      "metadata": {
        "act_url": "api/action",
        "act_method": 1
      },
      "content": "{\"command\":\"wakeup\"}"
    },
    {
      "post": 1,
      "metadata": {
        "act_url": "api/brightness",
        "act_method": 2
      },
      "content": "{\"brightness\":80}"
    }
  ]
}
```

### 响应格式
```json
{
  "success": true,
  "code": 200,
  "message": "操作成功",
  "data": {
    // 响应数据
  },
  "timestamp": 1640995200000
}
```

## 🐛 调试技巧

### 查看网络请求
1. 打开浏览器开发者工具 (F12)
2. 切换到 Network 标签页
3. 发送指令后查看 `/wp-json/wp/v2/comments` 请求

### 查看控制台日志
所有指令数据都会在控制台输出，包括：
- 发送的指令数据
- 接收的响应数据
- 错误信息

### 模拟后端响应
如果后端还未完成，可以修改 `src/api/deviceApi.ts`：
```typescript
// 在 sendDeviceCommand 函数中添加
if (process.env.NODE_ENV === 'development') {
  console.log('模拟响应:', command)
  return {
    success: true,
    code: 200,
    message: '模拟响应成功',
    data: { post: command.post },
    timestamp: Date.now()
  }
}
```

## 📚 相关文档

- [完整使用说明](./README.md)
- [指令参考文档](./docs/command-reference.md)
- [官方开发文档](https://developer.colorlightcloud.com/cloudServer/)

## ❓ 常见问题

### Q: 启动时提示端口被占用？
A: 修改 `vite.config.ts` 中的端口配置：
```typescript
server: {
  port: 3001  // 改为其他端口
}
```

### Q: API请求失败？
A: 检查：
1. 后端服务是否启动
2. API地址是否正确（`/wp-json/wp/v2/comments`）
3. 网络连接是否正常
4. 终端ID是否为有效数字

### Q: 指令格式不正确？
A: 参考 [指令参考文档](./docs/command-reference.md) 确认格式

### Q: 批量指令如何工作？
A: 
1. 批量指令会将多个指令打包在一个请求中发送
2. 每个指令都会独立执行
3. 发送完成后队列会自动清空
4. 可以在发送前删除不需要的指令

### Q: act_method 参数的含义？
A: 
- 0: GET 方法
- 1: POST 方法  
- 2: PUT 方法
- 3: DELETE 方法

## 🆘 获取帮助

如果遇到问题，请：
1. 查看浏览器控制台错误信息
2. 检查网络请求状态
3. 参考相关文档
4. 联系开发团队 