import axios from 'axios'
import { getApiBaseUrl, getWpApiEndpoint, getApi, getErrorMessage } from '../settings'

// 创建axios实例
const api = axios.create({
  baseURL: getApiBaseUrl(),
  timeout: getApi().REQUEST_CONFIG.TIMEOUT,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  (config) => {
    // 可以在这里添加认证token等
    console.log('发送请求:', config)
    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  (response) => {
    console.log('收到响应:', response)
    return response.data
  },
  (error) => {
    console.error('响应错误:', error)
    return Promise.reject(error)
  }
)

// 单条指令接口类型定义
export interface SingleCommand {
  post: number
  metadata: {
    act_url: string
    act_method: number
  }
  content: string
}

// 批量指令接口类型定义
export interface BatchCommand {
  post: number
  CommentData: SingleCommand[]
}

// 设备响应接口类型定义
export interface DeviceResponse {
  success: boolean
  code: number
  message: string
  data: any
  timestamp: number
}

// 发送设备指令（支持单条和批量）
export const sendDeviceCommand = async (command: SingleCommand | BatchCommand): Promise<DeviceResponse> => {
  try {
    // 根据接口要求，发送到 /wp-json/wp/v2/comments
    const response = await api.post(getApi().WP_ENDPOINTS.COMMENTS, command)
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

// 获取设备列表
export const getDeviceList = async (): Promise<DeviceResponse> => {
  try {
    const response = await api.get(getApi().CUSTOM_ENDPOINTS.DEVICE_STATUS)
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

// 获取设备详情
export const getDeviceDetail = async (deviceId: string): Promise<DeviceResponse> => {
  try {
    const response = await api.get(`${getApi().CUSTOM_ENDPOINTS.DEVICE_STATUS}/${deviceId}`)
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

// 连接设备
export const connectDevice = async (deviceId: string): Promise<DeviceResponse> => {
  try {
    const response = await api.post(`${getApi().CUSTOM_ENDPOINTS.DEVICE_CONTROL}/connect`, { deviceId })
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

// 断开设备连接
export const disconnectDevice = async (deviceId: string): Promise<DeviceResponse> => {
  try {
    const response = await api.post(`${getApi().CUSTOM_ENDPOINTS.DEVICE_CONTROL}/disconnect`, { deviceId })
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

// 获取设备状态
export const getDeviceStatus = async (deviceId: string): Promise<DeviceResponse> => {
  try {
    const response = await api.get(`${getApi().CUSTOM_ENDPOINTS.DEVICE_STATUS}/${deviceId}`)
    return response as DeviceResponse
  } catch (error: any) {
    throw new Error(error.response?.data?.message || error.message || getErrorMessage('API_ERROR'))
  }
}

export default api 