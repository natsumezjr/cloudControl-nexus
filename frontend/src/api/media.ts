import axios from 'axios';
import type { 
  Media, 
  MediaUploadRequest,
  ApiResponse,
  PaginatedResponse,
  SearchRequest
} from '@/types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// 创建axios实例
const mediaApi = axios.create({
  baseURL: `${API_BASE_URL}/api/media`,
  timeout: 30000, // 媒体上传需要更长的超时时间
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器 - 添加token
mediaApi.interceptors.request.use(
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
mediaApi.interceptors.response.use(
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

// 媒体管理API接口
export const mediaApiService = {
  // 获取媒体列表
  async getMedia(params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Media>>> {
    return mediaApi.get('', { params });
  },

  // 获取单个媒体
  async getMediaById(id: number): Promise<ApiResponse<Media>> {
    return mediaApi.get(`/${id}`);
  },

  // 上传媒体文件
  async uploadMedia(data: MediaUploadRequest): Promise<ApiResponse<Media>> {
    const formData = new FormData();
    formData.append('file', data.file);
    if (data.title) {
      formData.append('title', data.title);
    }
    if (data.description) {
      formData.append('description', data.description);
    }

    return mediaApi.post('/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  // 更新媒体信息
  async updateMedia(id: number, data: Partial<Media>): Promise<ApiResponse<Media>> {
    return mediaApi.put(`/${id}`, data);
  },

  // 删除媒体
  async deleteMedia(id: number): Promise<ApiResponse<void>> {
    return mediaApi.delete(`/${id}`);
  },

  // 批量删除媒体
  async batchDeleteMedia(ids: number[]): Promise<ApiResponse<void>> {
    return mediaApi.post('/batch-delete', { ids });
  },

  // 获取媒体缩略图
  async getMediaThumbnail(id: number): Promise<Blob> {
    const response = await mediaApi.get(`/${id}/thumbnail`, {
      responseType: 'blob',
    });
    return response as unknown as Blob;
  },

  // 获取媒体下载链接
  async getMediaDownloadUrl(id: number): Promise<ApiResponse<{ download_url: string }>> {
    return mediaApi.get(`/${id}/download`);
  },

  // 获取媒体统计信息
  async getMediaStats(): Promise<ApiResponse<{
    total: number;
    images: number;
    videos: number;
    documents: number;
    total_size: number;
  }>> {
    return mediaApi.get('/stats');
  },

  // 搜索媒体
  async searchMedia(keyword: string, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Media>>> {
    return mediaApi.get('/search', { 
      params: { 
        keyword, 
        ...params 
      } 
    });
  },

  // 按类型获取媒体
  async getMediaByType(type: string, params?: SearchRequest): Promise<ApiResponse<PaginatedResponse<Media>>> {
    return mediaApi.get(`/type/${type}`, { params });
  },
};

// TUS上传API接口
export const tusApiService = {
  // 创建上传会话
  async createUpload(filename: string, fileSize: number, fileType?: string): Promise<ApiResponse<{ upload_id: string; upload_url: string }>> {
    return mediaApi.post('/tus/upload', {
      filename,
      file_size: fileSize,
      file_type: fileType,
    });
  },

  // 上传分片
  async uploadChunk(uploadId: string, chunk: Blob, offset: number): Promise<ApiResponse<{ upload_offset: number }>> {
    return mediaApi.patch(`/tus/upload/${uploadId}`, chunk, {
      headers: {
        'Content-Type': 'application/offset+octet-stream',
        'Upload-Offset': offset.toString(),
      },
    });
  },

  // 获取上传状态
  async getUploadStatus(uploadId: string): Promise<ApiResponse<{
    upload_offset: number;
    file_size: number;
    status: string;
  }>> {
    return mediaApi.head(`/tus/upload/${uploadId}`);
  },

  // 删除上传
  async deleteUpload(uploadId: string): Promise<ApiResponse<void>> {
    return mediaApi.delete(`/tus/upload/${uploadId}`);
  },
};

export default {
  media: mediaApiService,
  tus: tusApiService,
}; 