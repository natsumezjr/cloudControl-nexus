import axios from 'axios';
import type { 
  Schedule, 
  ScheduleCreateRequest,
  ApiResponse,
  PaginatedResponse,
  SearchRequest
} from '@/types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// 创建axios实例
const scheduleApi = axios.create({
  baseURL: `${API_BASE_URL}/api/schedules`,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器 - 添加token
scheduleApi.interceptors.request.use(
  (config: any) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: any) => {
    return Promise.reject(error);
  }
);

// 响应拦截器 - 处理错误
scheduleApi.interceptors.response.use(
  (response: any) => {
    return response.data;
  },
  (error: any) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 排程管理API接口
export const scheduleApiService = {
  // 获取排程列表
  async getSchedules(params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Schedule>>> {
    return scheduleApi.get('', { params });
  },

  // 获取单个排程
  async getSchedule(id: number): Promise<ApiResponse<Schedule>> {
    return scheduleApi.get(`/${id}`);
  },

  // 创建排程
  async createSchedule(data: ScheduleCreateRequest): Promise<ApiResponse<Schedule>> {
    return scheduleApi.post('', data);
  },

  // 更新排程
  async updateSchedule(id: number, data: Partial<Schedule>): Promise<ApiResponse<Schedule>> {
    return scheduleApi.put(`/${id}`, data);
  },

  // 删除排程
  async deleteSchedule(id: number): Promise<ApiResponse<void>> {
    return scheduleApi.delete(`/${id}`);
  },

  // 批量删除排程
  async batchDeleteSchedules(ids: number[]): Promise<ApiResponse<void>> {
    return scheduleApi.post('/batch-delete', { ids });
  },

  // 启用排程
  async enableSchedule(id: number): Promise<ApiResponse<Schedule>> {
    return scheduleApi.put(`/${id}/enable`);
  },

  // 禁用排程
  async disableSchedule(id: number): Promise<ApiResponse<Schedule>> {
    return scheduleApi.put(`/${id}/disable`);
  },

  // 获取终端的排程
  async getTerminalSchedules(terminalId: number, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Schedule>>> {
    return scheduleApi.get(`/terminal/${terminalId}`, { params });
  },

  // 获取节目的排程
  async getProgramSchedules(programId: number, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Schedule>>> {
    return scheduleApi.get(`/program/${programId}`, { params });
  },

  // 检查排程冲突
  async checkScheduleConflict(terminalId: number, startTime: string, endTime: string, excludeId?: number): Promise<ApiResponse<{
    has_conflict: boolean;
    conflicting_schedules: Schedule[];
  }>> {
    return scheduleApi.post('/check-conflict', {
      terminal_id: terminalId,
      start_time: startTime,
      end_time: endTime,
      exclude_id: excludeId,
    });
  },

  // 获取排程统计信息
  async getScheduleStats(): Promise<ApiResponse<{
    total: number;
    active: number;
    pending: number;
    completed: number;
    cancelled: number;
  }>> {
    return scheduleApi.get('/stats');
  },
};

// 终端组排程管理API接口
export const terminalGroupScheduleApiService = {
  // 获取终端组排程
  async getTerminalGroupSchedules(groupId: number, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Schedule>>> {
    return scheduleApi.get(`/group/${groupId}`, { params });
  },

  // 为终端组创建排程
  async createTerminalGroupSchedule(groupId: number, data: ScheduleCreateRequest): Promise<ApiResponse<Schedule[]>> {
    return scheduleApi.post(`/group/${groupId}`, data);
  },

  // 应用排程到终端组
  async applyScheduleToGroup(scheduleId: number, groupId: number): Promise<ApiResponse<void>> {
    return scheduleApi.post(`/${scheduleId}/apply-to-group`, { group_id: groupId });
  },

  // 从终端组移除排程
  async removeScheduleFromGroup(scheduleId: number, groupId: number): Promise<ApiResponse<void>> {
    return scheduleApi.delete(`/${scheduleId}/group/${groupId}`);
  },

  // 获取终端组排程统计
  async getTerminalGroupScheduleStats(groupId: number): Promise<ApiResponse<{
    total_schedules: number;
    active_schedules: number;
    total_terminals: number;
    terminals_with_schedules: number;
  }>> {
    return scheduleApi.get(`/group/${groupId}/stats`);
  },
};

export default {
  schedule: scheduleApiService,
  group: terminalGroupScheduleApiService,
}; 