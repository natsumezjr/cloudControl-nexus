/**
 * 前端配置文件
 * 管理API端点、主题设置、环境配置等
 */

// 环境配置
export const ENV_CONFIG = {
  // 开发环境
  development: {
    API_BASE_URL: 'http://localhost:8080',
    WP_API_BASE_URL: 'http://localhost/wordpress',
    DEBUG_MODE: true,
    LOG_LEVEL: 'debug'
  },
  // 生产环境
  production: {
    API_BASE_URL: 'https://api.cloudcontrol-nexus.com',
    WP_API_BASE_URL: 'https://cloudcontrol-nexus.com',
    DEBUG_MODE: false,
    LOG_LEVEL: 'error'
  }
}

// 当前环境
export const CURRENT_ENV = import.meta.env.MODE || 'development'

// API配置
export const API_CONFIG = {
  // WordPress REST API端点
  WP_ENDPOINTS: {
    COMMENTS: '/wp-json/wp/v2/comments',
    POSTS: '/wp-json/wp/v2/posts',
    USERS: '/wp-json/wp/v2/users'
  },
  // 自定义API端点
  CUSTOM_ENDPOINTS: {
    DEVICE_CONTROL: '/api/device/control',
    DEVICE_STATUS: '/api/device/status',
    BATCH_COMMAND: '/api/device/batch',
    AUTH: '/api/auth'
  },
  // 请求配置
  REQUEST_CONFIG: {
    TIMEOUT: 10000,
    RETRY_TIMES: 3,
    RETRY_DELAY: 1000
  }
}

// 主题配置
export const THEME_CONFIG = {
  // 颜色主题
  colors: {
    primary: {
      main: '#667eea',
      light: '#8b9df0',
      dark: '#5a6fd8',
      contrast: '#ffffff'
    },
    success: {
      main: '#10b981',
      light: '#34d399',
      dark: '#0ea371',
      contrast: '#ffffff'
    },
    warning: {
      main: '#f59e0b',
      light: '#fbbf24',
      dark: '#e0910a',
      contrast: '#ffffff'
    },
    danger: {
      main: '#ef4444',
      light: '#f87171',
      dark: '#e03e3e',
      contrast: '#ffffff'
    },
    info: {
      main: '#6b7280',
      light: '#9ca3af',
      dark: '#5f6368',
      contrast: '#ffffff'
    }
  },
  // 字体配置
  fonts: {
    primary: "'Inter', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif",
    mono: "'JetBrains Mono', 'Fira Code', 'Consolas', 'Monaco', monospace"
  },
  // 间距配置
  spacing: {
    xs: '4px',
    sm: '8px',
    md: '16px',
    lg: '24px',
    xl: '32px',
    xxl: '48px'
  },
  // 圆角配置
  borderRadius: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    xl: '16px',
    full: '9999px'
  }
}

// 设备控制配置
export const DEVICE_CONFIG = {
  // 命令类型
  commandTypes: {
    POWER: 'power',
    BRIGHTNESS: 'brightness',
    COLOR: 'color',
    TEMPERATURE: 'temperature',
    MODE: 'mode',
    TIMER: 'timer',
    RESET: 'reset'
  },
  // 设备类型
  deviceTypes: {
    LIGHT: 'light',
    THERMOSTAT: 'thermostat',
    CAMERA: 'camera',
    SENSOR: 'sensor',
    SWITCH: 'switch'
  },
  // 默认参数
  defaultParams: {
    brightness: {
      min: 0,
      max: 100,
      step: 5,
      default: 50
    },
    temperature: {
      min: 16,
      max: 30,
      step: 1,
      default: 22
    },
    color: {
      default: '#ffffff'
    }
  },
  // 批量命令配置
  batchConfig: {
    maxCommands: 10,
    delayBetweenCommands: 500, // 毫秒
    timeout: 30000 // 毫秒
  }
}

// 本地存储配置
export const STORAGE_CONFIG = {
  // 存储键名
  keys: {
    USER_PREFERENCES: 'cloudcontrol_user_preferences',
    DEVICE_HISTORY: 'cloudcontrol_device_history',
    AUTH_TOKEN: 'cloudcontrol_auth_token',
    THEME_MODE: 'cloudcontrol_theme_mode'
  },
  // 存储过期时间（毫秒）
  expiration: {
    AUTH_TOKEN: 24 * 60 * 60 * 1000, // 24小时
    DEVICE_HISTORY: 7 * 24 * 60 * 60 * 1000, // 7天
    USER_PREFERENCES: 365 * 24 * 60 * 60 * 1000 // 1年
  }
}

// 错误配置
export const ERROR_CONFIG = {
  // 错误消息
  messages: {
    NETWORK_ERROR: '网络连接错误，请检查网络设置',
    API_ERROR: 'API请求失败，请稍后重试',
    AUTH_ERROR: '认证失败，请重新登录',
    DEVICE_OFFLINE: '设备离线，请检查设备连接',
    INVALID_PARAMS: '参数无效，请检查输入',
    TIMEOUT_ERROR: '请求超时，请稍后重试'
  },
  // 错误代码
  codes: {
    NETWORK_ERROR: 'NETWORK_ERROR',
    API_ERROR: 'API_ERROR',
    AUTH_ERROR: 'AUTH_ERROR',
    DEVICE_OFFLINE: 'DEVICE_OFFLINE',
    INVALID_PARAMS: 'INVALID_PARAMS',
    TIMEOUT_ERROR: 'TIMEOUT_ERROR'
  }
}

// 日志配置
export const LOG_CONFIG = {
  // 日志级别
  levels: {
    ERROR: 0,
    WARN: 1,
    INFO: 2,
    DEBUG: 3
  },
  // 日志前缀
  prefix: '[CloudControl-Nexus]',
  // 是否启用控制台日志
  enableConsole: CURRENT_ENV === 'development',
  // 是否启用文件日志
  enableFile: false
}

// 路由配置
export const ROUTE_CONFIG = {
  // 路由路径
  paths: {
    HOME: '/',
    DEVICE_CONTROL: '/device-control',
    SETTINGS: '/settings',
    ABOUT: '/about'
  },
  // 路由元信息
  meta: {
    HOME: {
      title: '首页',
      requiresAuth: false
    },
    DEVICE_CONTROL: {
      title: '设备控制',
      requiresAuth: true
    },
    SETTINGS: {
      title: '设置',
      requiresAuth: true
    },
    ABOUT: {
      title: '关于',
      requiresAuth: false
    }
  }
}

// 导出当前环境的配置
export const getCurrentConfig = () => {
  return {
    env: ENV_CONFIG[CURRENT_ENV as keyof typeof ENV_CONFIG],
    api: API_CONFIG,
    theme: THEME_CONFIG,
    device: DEVICE_CONFIG,
    storage: STORAGE_CONFIG,
    error: ERROR_CONFIG,
    log: LOG_CONFIG,
    route: ROUTE_CONFIG
  }
}

// 默认导出
export default getCurrentConfig() 