/**
 * 配置管理主入口
 * 提供便捷的配置访问方法
 */

import config, { 
  ENV_CONFIG, 
  API_CONFIG, 
  THEME_CONFIG, 
  DEVICE_CONFIG, 
  STORAGE_CONFIG, 
  ERROR_CONFIG, 
  LOG_CONFIG, 
  ROUTE_CONFIG,
  getCurrentConfig 
} from './config'

// 配置管理器类
class ConfigManager {
  private static instance: ConfigManager
  private config: ReturnType<typeof getCurrentConfig>

  private constructor() {
    this.config = getCurrentConfig()
  }

  // 单例模式
  public static getInstance(): ConfigManager {
    if (!ConfigManager.instance) {
      ConfigManager.instance = new ConfigManager()
    }
    return ConfigManager.instance
  }

  // 获取完整配置
  public getConfig() {
    return this.config
  }

  // 获取环境配置
  public getEnv() {
    return this.config.env
  }

  // 获取API配置
  public getApi() {
    return this.config.api
  }

  // 获取主题配置
  public getTheme() {
    return this.config.theme
  }

  // 获取设备配置
  public getDevice() {
    return this.config.device
  }

  // 获取存储配置
  public getStorage() {
    return this.config.storage
  }

  // 获取错误配置
  public getError() {
    return this.config.error
  }

  // 获取日志配置
  public getLog() {
    return this.config.log
  }

  // 获取路由配置
  public getRoute() {
    return this.config.route
  }

  // 获取API基础URL
  public getApiBaseUrl(): string {
    return this.config.env.API_BASE_URL
  }

  // 获取WordPress API基础URL
  public getWpApiBaseUrl(): string {
    return this.config.env.WP_API_BASE_URL
  }

  // 获取完整的API端点
  public getApiEndpoint(endpoint: string): string {
    return `${this.getApiBaseUrl()}${endpoint}`
  }

  // 获取完整的WordPress API端点
  public getWpApiEndpoint(endpoint: string): string {
    return `${this.getWpApiBaseUrl()}${endpoint}`
  }

  // 获取主题颜色
  public getThemeColor(type: keyof typeof THEME_CONFIG.colors) {
    return this.config.theme.colors[type]
  }

  // 获取设备命令类型
  public getCommandType(type: keyof typeof DEVICE_CONFIG.commandTypes) {
    return this.config.device.commandTypes[type]
  }

  // 获取设备类型
  public getDeviceType(type: keyof typeof DEVICE_CONFIG.deviceTypes) {
    return this.config.device.deviceTypes[type]
  }

  // 获取错误消息
  public getErrorMessage(code: keyof typeof ERROR_CONFIG.messages): string {
    return this.config.error.messages[code]
  }

  // 获取存储键名
  public getStorageKey(key: keyof typeof STORAGE_CONFIG.keys): string {
    return this.config.storage.keys[key]
  }

  // 获取存储过期时间
  public getStorageExpiration(key: keyof typeof STORAGE_CONFIG.expiration): number {
    return this.config.storage.expiration[key]
  }

  // 检查是否为开发环境
  public isDevelopment(): boolean {
    return this.config.env.DEBUG_MODE
  }

  // 检查是否为生产环境
  public isProduction(): boolean {
    return !this.config.env.DEBUG_MODE
  }

  // 重新加载配置
  public reload() {
    this.config = getCurrentConfig()
  }
}

// 创建全局配置管理器实例
const configManager = ConfigManager.getInstance()

// 导出便捷方法
export const getConfig = () => configManager.getConfig()
export const getEnv = () => configManager.getEnv()
export const getApi = () => configManager.getApi()
export const getTheme = () => configManager.getTheme()
export const getDevice = () => configManager.getDevice()
export const getStorage = () => configManager.getStorage()
export const getError = () => configManager.getError()
export const getLog = () => configManager.getLog()
export const getRoute = () => configManager.getRoute()

// 导出便捷的API方法
export const getApiBaseUrl = () => configManager.getApiBaseUrl()
export const getWpApiBaseUrl = () => configManager.getWpApiBaseUrl()
export const getApiEndpoint = (endpoint: string) => configManager.getApiEndpoint(endpoint)
export const getWpApiEndpoint = (endpoint: string) => configManager.getWpApiEndpoint(endpoint)

// 导出便捷的主题方法
export const getThemeColor = (type: keyof typeof THEME_CONFIG.colors) => configManager.getThemeColor(type)

// 导出便捷的设备方法
export const getCommandType = (type: keyof typeof DEVICE_CONFIG.commandTypes) => configManager.getCommandType(type)
export const getDeviceType = (type: keyof typeof DEVICE_CONFIG.deviceTypes) => configManager.getDeviceType(type)

// 导出便捷的错误方法
export const getErrorMessage = (code: keyof typeof ERROR_CONFIG.messages) => configManager.getErrorMessage(code)

// 导出便捷的存储方法
export const getStorageKey = (key: keyof typeof STORAGE_CONFIG.keys) => configManager.getStorageKey(key)
export const getStorageExpiration = (key: keyof typeof STORAGE_CONFIG.expiration) => configManager.getStorageExpiration(key)

// 导出环境检查方法
export const isDevelopment = () => configManager.isDevelopment()
export const isProduction = () => configManager.isProduction()

// 导出配置管理器实例
export { configManager }

// 默认导出
export default configManager 