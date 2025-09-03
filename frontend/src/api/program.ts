import axios from 'axios';
import type { 
  Program, 
  ProgramCreateRequest, 
  ProgramUpdateRequest,
  ProgramTerminalRelation,
  ApiResponse,
  PaginatedResponse,
  SearchRequest
} from '@/types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// 创建axios实例
const programApi = axios.create({
  baseURL: `${API_BASE_URL}/api/programs`,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器 - 添加token
programApi.interceptors.request.use(
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
programApi.interceptors.response.use(
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

// 节目管理API接口
export const programApiService = {
  // 获取节目列表
  async getPrograms(params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Program>>> {
    return programApi.get('', { params });
  },

  // 获取单个节目
  async getProgram(id: number): Promise<ApiResponse<Program>> {
    return programApi.get(`/${id}`);
  },

  // 创建节目
  async createProgram(data: ProgramCreateRequest): Promise<ApiResponse<Program>> {
    return programApi.post('', data);
  },

  // 更新节目
  async updateProgram(id: number, data: ProgramUpdateRequest): Promise<ApiResponse<Program>> {
    return programApi.put(`/${id}`, data);
  },

  // 删除节目
  async deleteProgram(id: number): Promise<ApiResponse<void>> {
    return programApi.delete(`/${id}`);
  },

  // 批量删除节目
  async batchDeletePrograms(ids: number[]): Promise<ApiResponse<void>> {
    return programApi.post('/batch-delete', { ids });
  },

  // 发布节目
  async publishProgram(id: number): Promise<ApiResponse<Program>> {
    return programApi.put(`/${id}/publish`);
  },

  // 取消发布节目
  async unpublishProgram(id: number): Promise<ApiResponse<Program>> {
    return programApi.put(`/${id}/unpublish`);
  },

  // 复制节目
  async copyProgram(id: number, newTitle: string): Promise<ApiResponse<Program>> {
    return programApi.post(`/${id}/copy`, { title: newTitle });
  },

  // 获取节目统计信息
  async getProgramStats(): Promise<ApiResponse<{
    total: number;
    published: number;
    draft: number;
    archived: number;
  }>> {
    return programApi.get('/stats');
  },
};

// 节目终端关系管理API接口
export const programTerminalApiService = {
  // 获取节目的终端列表
  async getProgramTerminals(programId: number, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<ProgramTerminalRelation>>> {
    return programApi.get(`/${programId}/terminals`, { params });
  },

  // 为节目分配终端
  async assignTerminalToProgram(programId: number, terminalId: number): Promise<ApiResponse<ProgramTerminalRelation>> {
    return programApi.post(`/${programId}/terminals`, { terminal_id: terminalId });
  },

  // 从节目移除终端
  async removeTerminalFromProgram(programId: number, terminalId: number): Promise<ApiResponse<void>> {
    return programApi.delete(`/${programId}/terminals/${terminalId}`);
  },

  // 批量分配终端到节目
  async batchAssignTerminals(programId: number, terminalIds: number[]): Promise<ApiResponse<void>> {
    return programApi.post(`/${programId}/terminals/batch`, { terminal_ids: terminalIds });
  },

  // 获取终端的节目列表
  async getTerminalPrograms(terminalId: number, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<ProgramTerminalRelation>>> {
    return programApi.get(`/terminals/${terminalId}`, { params });
  },

  // 更新节目终端关系状态
  async updateProgramTerminalStatus(programId: number, terminalId: number, status: string): Promise<ApiResponse<ProgramTerminalRelation>> {
    return programApi.put(`/${programId}/terminals/${terminalId}/status`, { status });
  },
};

export default {
  program: programApiService,
  terminal: programTerminalApiService,
}; 