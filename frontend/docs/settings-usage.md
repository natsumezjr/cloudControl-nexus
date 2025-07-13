# 前端配置系统使用指南

## 概述

CloudControl-Nexus 前端项目使用统一的配置管理系统，所有配置都集中在 `src/settings/` 目录下，便于维护和管理。

## 配置文件结构

```
src/settings/
├── config.ts      # 主配置文件
└── index.ts       # 配置管理器和便捷方法
```

## 配置分类

### 1. 环境配置 (ENV_CONFIG)

管理不同环境的配置参数：

```typescript
import { getEnv, isDevelopment, isProduction } from '@/settings'

// 获取当前环境配置
const envConfig = getEnv()
console.log(envConfig.API_BASE_URL) // http://localhost:8080 (开发环境)

// 检查环境
if (isDevelopment()) {
  console.log('当前为开发环境')
}
```

### 2. API配置 (API_CONFIG)

管理API端点和请求配置：

```typescript
import { getApi, getApiEndpoint, getWpApiEndpoint } from '@/settings'

// 获取API配置
const apiConfig = getApi()

// 获取WordPress API端点
const commentsEndpoint = apiConfig.WP_ENDPOINTS.COMMENTS // '/wp-json/wp/v2/comments'

// 获取完整的API URL
const fullApiUrl = getApiEndpoint('/device/status') // 'http://localhost:8080/api/device/status'
const fullWpUrl = getWpApiEndpoint('/wp-json/wp/v2/comments') // 'http://localhost/wordpress/wp-json/wp/v2/comments'
```

### 3. 主题配置 (THEME_CONFIG)

管理UI主题相关的配置：

```typescript
import { getTheme, getThemeColor } from '@/settings'

// 获取主题配置
const themeConfig = getTheme()

// 获取特定颜色
const primaryColor = getThemeColor('primary') // { main: '#667eea', light: '#8b9df0', ... }

// 使用字体配置
const fontFamily = themeConfig.fonts.primary

// 使用间距配置
const spacing = themeConfig.spacing.md // '16px'
```

### 4. 设备配置 (DEVICE_CONFIG)

管理设备控制相关的配置：

```typescript
import { getDevice, getCommandType, getDeviceType } from '@/settings'

// 获取设备配置
const deviceConfig = getDevice()

// 获取命令类型
const powerCommand = getCommandType('POWER') // 'power'

// 获取设备类型
const lightDevice = getDeviceType('LIGHT') // 'light'

// 获取默认参数
const brightnessParams = deviceConfig.defaultParams.brightness
// { min: 0, max: 100, step: 5, default: 50 }
```

### 5. 存储配置 (STORAGE_CONFIG)

管理本地存储相关的配置：

```typescript
import { getStorage, getStorageKey, getStorageExpiration } from '@/settings'

// 获取存储配置
const storageConfig = getStorage()

// 获取存储键名
const userPrefsKey = getStorageKey('USER_PREFERENCES') // 'cloudcontrol_user_preferences'

// 获取过期时间
const tokenExpiration = getStorageExpiration('AUTH_TOKEN') // 24小时（毫秒）
```

### 6. 错误配置 (ERROR_CONFIG)

管理错误消息和错误代码：

```typescript
import { getError, getErrorMessage } from '@/settings'

// 获取错误配置
const errorConfig = getError()

// 获取错误消息
const networkErrorMsg = getErrorMessage('NETWORK_ERROR') // '网络连接错误，请检查网络设置'
```

### 7. 日志配置 (LOG_CONFIG)

管理日志相关的配置：

```typescript
import { getLog } from '@/settings'

// 获取日志配置
const logConfig = getLog()

// 检查是否启用控制台日志
if (logConfig.enableConsole) {
  console.log('日志已启用')
}
```

### 8. 路由配置 (ROUTE_CONFIG)

管理路由相关的配置：

```typescript
import { getRoute } from '@/settings'

// 获取路由配置
const routeConfig = getRoute()

// 获取路由路径
const deviceControlPath = routeConfig.paths.DEVICE_CONTROL // '/device-control'

// 获取路由元信息
const deviceControlMeta = routeConfig.meta.DEVICE_CONTROL // { title: '设备控制', requiresAuth: true }
```

## 在组件中使用

### Vue组件示例

```vue
<template>
  <div class="device-control">
    <el-button 
      :style="{ backgroundColor: themeColors.primary.main, color: themeColors.primary.contrast }"
      @click="sendCommand"
    >
      发送指令
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getThemeColor, getCommandType, getApiEndpoint } from '@/settings'
import { sendDeviceCommand } from '@/api/deviceApi'

// 获取主题颜色
const themeColors = {
  primary: getThemeColor('primary')
}

// 获取命令类型
const powerCommand = getCommandType('POWER')

// 发送指令
const sendCommand = async () => {
  try {
    const command = {
      post: 1,
      metadata: {
        act_url: getApiEndpoint('/device/control'),
        act_method: 1
      },
      content: JSON.stringify({
        type: powerCommand,
        value: 'on'
      })
    }
    
    const response = await sendDeviceCommand(command)
    console.log('指令发送成功:', response)
  } catch (error) {
    console.error('指令发送失败:', error)
  }
}
</script>
```

### 在API中使用

```typescript
// api/deviceApi.ts
import { getApi, getErrorMessage } from '@/settings'

const api = axios.create({
  baseURL: getApi().env.API_BASE_URL,
  timeout: getApi().REQUEST_CONFIG.TIMEOUT
})

export const sendDeviceCommand = async (command: SingleCommand): Promise<DeviceResponse> => {
  try {
    const response = await api.post(getApi().WP_ENDPOINTS.COMMENTS, command)
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}
```

## 配置更新

### 添加新的配置项

1. 在 `config.ts` 中添加新的配置对象
2. 在 `index.ts` 中添加相应的便捷方法
3. 更新类型定义（如果需要）

### 环境切换

配置会根据 `import.meta.env.MODE` 自动切换环境：

- `development`: 使用开发环境配置
- `production`: 使用生产环境配置

### 动态配置

可以通过 `configManager.reload()` 方法重新加载配置：

```typescript
import { configManager } from '@/settings'

// 重新加载配置
configManager.reload()
```

## 最佳实践

1. **统一使用配置系统**：避免在代码中硬编码配置值
2. **使用便捷方法**：优先使用 `getXXX()` 方法而不是直接访问配置对象
3. **类型安全**：利用TypeScript的类型检查确保配置使用正确
4. **环境隔离**：确保开发和生产环境的配置完全分离
5. **配置验证**：在应用启动时验证关键配置项

## 注意事项

1. 配置是只读的，不应在运行时修改
2. 敏感信息（如API密钥）不应直接写在配置文件中
3. 生产环境的配置应该通过环境变量注入
4. 配置更改后需要重启应用才能生效 