import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { User, UserLoginRequest, UserCreateRequest } from '@/types';
import authApiService from '@/api/auth';

export const useAuthStore = defineStore('auth', () => {
  // 状态
  const user = ref<User | null>(null);
  const token = ref<string | null>(null);
  const loading = ref(false);
  const error = ref<string | null>(null);

  // 计算属性
  const isAuthenticated = computed(() => !!token.value && !!user.value);
  const userRole = computed(() => user.value?.role || null);
  const isAdmin = computed(() => userRole.value === 'ADMIN');
  const isOperator = computed(() => userRole.value === 'OPERATOR');

  // 初始化 - 从localStorage恢复状态
  const initialize = () => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    
    if (storedToken && storedUser) {
      token.value = storedToken;
      user.value = JSON.parse(storedUser);
    }
  };

  // 登录
  const login = async (credentials: UserLoginRequest) => {
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.login(credentials);
      
      if (response.success && response.data) {
        token.value = response.data.token;
        user.value = response.data.user;
        
        // 保存到localStorage
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify(response.data.user));
        
        return response;
      } else {
        error.value = response.message || '登录失败';
        throw new Error(error.value);
      }
    } catch (err: any) {
      error.value = err.message || '登录失败';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // 注册
  const register = async (userData: UserCreateRequest) => {
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.register(userData);
      
      if (response.success) {
        return response;
      } else {
        error.value = response.message || '注册失败';
        throw new Error(error.value);
      }
    } catch (err: any) {
      error.value = err.message || '注册失败';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // 登出
  const logout = async () => {
    loading.value = true;
    
    try {
      if (token.value) {
        await authApiService.logout();
      }
    } catch (err) {
      console.error('登出时出错:', err);
    } finally {
      // 清除状态
      user.value = null;
      token.value = null;
      error.value = null;
      
      // 清除localStorage
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      
      loading.value = false;
    }
  };

  // 获取当前用户信息
  const getCurrentUser = async () => {
    if (!token.value) return;
    
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.getCurrentUser();
      
      if (response.success && response.data) {
        user.value = response.data;
        localStorage.setItem('user', JSON.stringify(response.data));
      } else {
        error.value = response.message || '获取用户信息失败';
      }
    } catch (err: any) {
      error.value = err.message || '获取用户信息失败';
      // 如果获取用户信息失败，可能是token过期，清除状态
      if (err.response?.status === 401) {
        await logout();
      }
    } finally {
      loading.value = false;
    }
  };

  // 修改密码
  const changePassword = async (oldPassword: string, newPassword: string) => {
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.changePassword(oldPassword, newPassword);
      
      if (response.success) {
        return response;
      } else {
        error.value = response.message || '修改密码失败';
        throw new Error(error.value);
      }
    } catch (err: any) {
      error.value = err.message || '修改密码失败';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // 忘记密码
  const forgotPassword = async (email: string) => {
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.forgotPassword(email);
      
      if (response.success) {
        return response;
      } else {
        error.value = response.message || '发送重置邮件失败';
        throw new Error(error.value);
      }
    } catch (err: any) {
      error.value = err.message || '发送重置邮件失败';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // 重置密码
  const resetPassword = async (resetToken: string, newPassword: string) => {
    loading.value = true;
    error.value = null;
    
    try {
      const response = await authApiService.resetPassword(resetToken, newPassword);
      
      if (response.success) {
        return response;
      } else {
        error.value = response.message || '重置密码失败';
        throw new Error(error.value);
      }
    } catch (err: any) {
      error.value = err.message || '重置密码失败';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // 清除错误
  const clearError = () => {
    error.value = null;
  };

  return {
    // 状态
    user,
    token,
    loading,
    error,
    
    // 计算属性
    isAuthenticated,
    userRole,
    isAdmin,
    isOperator,
    
    // 方法
    initialize,
    login,
    register,
    logout,
    getCurrentUser,
    changePassword,
    forgotPassword,
    resetPassword,
    clearError,
  };
}); 