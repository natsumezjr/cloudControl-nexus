import axios from 'axios';
import type { 
  UserLoginRequest, 
  UserLoginResponse, 
  UserCreateRequest, 
  User, 
  ApiResponse 
} from '@/types';
import { UserRole, UserStatus } from '@/types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// 创建axios实例
const authApi = axios.create({
  baseURL: `${API_BASE_URL}/api/auth`,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器 - 添加token
authApi.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器 - 处理错误
authApi.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    if (error.response?.status === 401) {
      // Token过期，清除本地存储
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 模拟用户数据
const mockUsers: User[] = [
  {
    id: 1,
    username: 'admin',
    email: 'admin@example.com',
    role: UserRole.ADMIN,
    status: UserStatus.ACTIVE,
    created_at: '2024-01-01T00:00:00Z',
    updated_at: '2024-01-01T00:00:00Z',
  },
  {
    id: 2,
    username: 'user',
    email: 'user@example.com',
    role: UserRole.USER,
    status: UserStatus.ACTIVE,
    created_at: '2024-01-01T00:00:00Z',
    updated_at: '2024-01-01T00:00:00Z',
  },
];

// 模拟认证API接口
export const authApiService = {
  // 用户登录
  async login(credentials: UserLoginRequest): Promise<ApiResponse<UserLoginResponse>> {
    // 模拟API调用延迟
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // 模拟登录验证
    const user = mockUsers.find(u => u.username === credentials.username);
    
    if (user && credentials.password === '123456') {
      const mockToken = `mock_token_${user.id}_${Date.now()}`;
      
      return {
        success: true,
        data: {
          token: mockToken,
          user: user,
          expires_in: 3600,
        },
        message: '登录成功',
      };
    } else {
      throw new Error('用户名或密码错误');
    }
  },

  // 用户注册
  async register(userData: UserCreateRequest): Promise<ApiResponse<User>> {
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // 检查用户名是否已存在
    if (mockUsers.find(u => u.username === userData.username)) {
      throw new Error('用户名已存在');
    }
    
    const newUser: User = {
      id: mockUsers.length + 1,
      username: userData.username,
      email: userData.email,
      role: userData.role,
      status: UserStatus.ACTIVE,
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString(),
    };
    
    mockUsers.push(newUser);
    
    return {
      success: true,
      data: newUser,
      message: '注册成功',
    };
  },

  // 用户登出
  async logout(): Promise<ApiResponse<void>> {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    return {
      success: true,
      message: '登出成功',
    };
  },

  // 刷新token
  async refreshToken(): Promise<ApiResponse<{ token: string; expires_in: number }>> {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const mockToken = `mock_token_refresh_${Date.now()}`;
    
    return {
      success: true,
      data: {
        token: mockToken,
        expires_in: 3600,
      },
    };
  },

  // 获取当前用户信息
  async getCurrentUser(): Promise<ApiResponse<User>> {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('未找到认证令牌');
    }
    
    // 从token中提取用户ID（模拟）
    const userId = parseInt(token.split('_')[2]);
    const user = mockUsers.find(u => u.id === userId);
    
    if (!user) {
      throw new Error('用户不存在');
    }
    
    return {
      success: true,
      data: user,
    };
  },

  // 修改密码
  async changePassword(oldPassword: string, newPassword: string): Promise<ApiResponse<void>> {
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    if (oldPassword !== '123456') {
      throw new Error('原密码错误');
    }
    
    return {
      success: true,
      message: '密码修改成功',
    };
  },

  // 忘记密码
  async forgotPassword(email: string): Promise<ApiResponse<void>> {
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    const user = mockUsers.find(u => u.email === email);
    if (!user) {
      throw new Error('邮箱不存在');
    }
    
    return {
      success: true,
      message: '重置邮件已发送',
    };
  },

  // 重置密码
  async resetPassword(token: string, newPassword: string): Promise<ApiResponse<void>> {
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    if (!token || token.length < 10) {
      throw new Error('重置令牌无效');
    }
    
    return {
      success: true,
      message: '密码重置成功',
    };
  },
};

export default authApiService; 