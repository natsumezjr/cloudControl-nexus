# CloudControl-Nexus 前端架构设计

## 概述

CloudControl-Nexus前端采用现代化的Vue 3 + TypeScript + Pinia架构，与后端的分层架构相对应，提供完整的设备管理、内容分发、播放控制等功能。

## 1. 技术栈

### 1.1 核心技术
- **Vue 3**: 现代化前端框架，使用Composition API
- **TypeScript**: 类型安全，提升开发体验
- **Pinia**: 状态管理，替代Vuex
- **Vue Router**: 路由管理
- **Element Plus**: UI组件库
- **Axios**: HTTP客户端
- **Vite**: 构建工具

### 1.2 开发工具
- **ESLint**: 代码规范检查
- **Prettier**: 代码格式化
- **TypeScript**: 类型检查

## 2. 项目结构

```
frontend/
├── src/
│   ├── components/           # 组件层
│   │   ├── common/          # 通用组件
│   │   │   ├── LoadingSpinner.vue
│   │   │   ├── ErrorBoundary.vue
│   │   │   └── ConfirmDialog.vue
│   │   ├── layout/          # 布局组件
│   │   │   ├── AppLayout.vue
│   │   │   ├── Sidebar.vue
│   │   │   └── Header.vue
│   │   ├── auth/            # 认证组件
│   │   │   ├── LoginForm.vue
│   │   │   └── UserProfile.vue
│   │   ├── terminal/        # 终端管理组件
│   │   │   ├── TerminalList.vue
│   │   │   ├── TerminalCard.vue
│   │   │   ├── TerminalDetail.vue
│   │   │   ├── TerminalForm.vue
│   │   │   ├── TerminalGroups.vue
│   │   │   └── TerminalTags.vue
│   │   ├── program/         # 节目管理组件
│   │   │   ├── ProgramList.vue
│   │   │   ├── ProgramCard.vue
│   │   │   ├── ProgramForm.vue
│   │   │   ├── ProgramEditor.vue
│   │   │   └── ProgramSchedule.vue
│   │   ├── media/           # 媒体管理组件
│   │   │   ├── MediaList.vue
│   │   │   ├── MediaCard.vue
│   │   │   ├── MediaUpload.vue
│   │   │   ├── MediaPlayer.vue
│   │   │   └── MediaGallery.vue
│   │   ├── command/         # 命令管理组件
│   │   │   ├── CommandPanel.vue
│   │   │   ├── CommandForm.vue
│   │   │   ├── CommandHistory.vue
│   │   │   └── CommandResult.vue
│   │   ├── schedule/        # 排程管理组件
│   │   │   ├── ScheduleList.vue
│   │   │   ├── ScheduleForm.vue
│   │   │   ├── ScheduleCalendar.vue
│   │   │   └── ScheduleTimeline.vue
│   │   └── system/          # 系统管理组件
│   │       ├── UserManagement.vue
│   │       ├── SystemSettings.vue
│   │       └── SystemLogs.vue
│   ├── pages/               # 页面层
│   │   ├── Dashboard.vue
│   │   ├── auth/
│   │   │   ├── Login.vue
│   │   │   ├── Register.vue
│   │   │   ├── ForgotPassword.vue
│   │   │   └── ResetPassword.vue
│   │   ├── terminal/
│   │   │   ├── TerminalList.vue
│   │   │   ├── TerminalDetail.vue
│   │   │   ├── TerminalGroups.vue
│   │   │   └── TerminalTags.vue
│   │   ├── program/
│   │   │   ├── ProgramList.vue
│   │   │   ├── ProgramCreate.vue
│   │   │   ├── ProgramDetail.vue
│   │   │   └── ProgramEdit.vue
│   │   ├── media/
│   │   │   ├── MediaList.vue
│   │   │   ├── MediaUpload.vue
│   │   │   └── MediaDetail.vue
│   │   ├── command/
│   │   │   ├── CommandSend.vue
│   │   │   ├── CommandHistory.vue
│   │   │   └── CommandDetail.vue
│   │   ├── schedule/
│   │   │   ├── ScheduleList.vue
│   │   │   ├── ScheduleCreate.vue
│   │   │   └── ScheduleDetail.vue
│   │   └── system/
│   │       ├── UserManagement.vue
│   │       ├── SystemSettings.vue
│   │       └── SystemLogs.vue
│   ├── api/                 # API接口层
│   │   ├── auth.ts          # 认证API
│   │   ├── terminal.ts      # 终端API
│   │   ├── program.ts       # 节目API
│   │   ├── media.ts         # 媒体API
│   │   ├── command.ts       # 命令API
│   │   ├── schedule.ts      # 排程API
│   │   └── system.ts        # 系统API
│   ├── stores/              # 状态管理层
│   │   ├── auth.ts          # 认证状态
│   │   ├── terminal.ts      # 终端状态
│   │   ├── program.ts       # 节目状态
│   │   ├── media.ts         # 媒体状态
│   │   ├── command.ts       # 命令状态
│   │   ├── schedule.ts      # 排程状态
│   │   └── system.ts        # 系统状态
│   ├── types/               # 类型定义层
│   │   ├── index.ts         # 基础类型
│   │   ├── auth.ts          # 认证类型
│   │   ├── terminal.ts      # 终端类型
│   │   ├── program.ts       # 节目类型
│   │   ├── media.ts         # 媒体类型
│   │   ├── command.ts       # 命令类型
│   │   └── schedule.ts      # 排程类型
│   ├── utils/               # 工具层
│   │   ├── request.ts       # HTTP请求封装
│   │   ├── auth.ts          # 认证工具
│   │   ├── storage.ts       # 存储工具
│   │   ├── format.ts        # 格式化工具
│   │   ├── validation.ts    # 验证工具
│   │   └── constants.ts     # 常量定义
│   ├── hooks/               # 自定义钩子
│   │   ├── useAuth.ts       # 认证钩子
│   │   ├── useApi.ts        # API钩子
│   │   ├── useWebSocket.ts  # WebSocket钩子
│   │   └── useLocalStorage.ts # 本地存储钩子
│   ├── styles/              # 样式层
│   │   ├── global.css       # 全局样式
│   │   ├── variables.css    # CSS变量
│   │   ├── components.css   # 组件样式
│   │   └── themes.css       # 主题样式
│   ├── settings/            # 配置层
│   │   ├── config.ts        # 主配置
│   │   └── index.ts         # 配置管理
│   ├── App.vue              # 根组件
│   ├── main.ts              # 应用入口
│   └── env.d.ts             # 环境变量类型
├── public/                  # 静态资源
├── docs/                    # 文档
├── package.json             # 依赖配置
├── vite.config.ts           # Vite配置
├── tsconfig.json            # TypeScript配置
└── README.md                # 项目说明
```

## 3. 分层架构设计

### 3.1 组件层 (Components)
- **职责**: 可复用的UI组件
- **特点**: 纯展示组件，无业务逻辑
- **分类**: 按功能模块组织

### 3.2 页面层 (Pages)
- **职责**: 路由页面组件
- **特点**: 组合多个组件，处理页面级逻辑
- **分类**: 按路由结构组织

### 3.3 API接口层 (API)
- **职责**: 与后端API通信
- **特点**: 封装HTTP请求，统一错误处理
- **分类**: 按后端模块对应

### 3.4 状态管理层 (Stores)
- **职责**: 全局状态管理
- **特点**: 使用Pinia，响应式状态
- **分类**: 按业务模块组织

### 3.5 类型定义层 (Types)
- **职责**: TypeScript类型定义
- **特点**: 与后端DTO对应
- **分类**: 按数据模型组织

### 3.6 工具层 (Utils)
- **职责**: 通用工具函数
- **特点**: 纯函数，无副作用
- **分类**: 按功能类型组织

## 4. 核心模块设计

### 4.1 认证模块 (Auth)
```typescript
// 认证状态管理
interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
}

// 认证API接口
interface AuthApi {
  login(credentials: LoginRequest): Promise<LoginResponse>;
  register(userData: RegisterRequest): Promise<User>;
  logout(): Promise<void>;
  getCurrentUser(): Promise<User>;
}
```

### 4.2 终端管理模块 (Terminal)
```typescript
// 终端状态管理
interface TerminalState {
  terminals: Terminal[];
  currentTerminal: Terminal | null;
  loading: boolean;
  pagination: PaginationInfo;
}

// 终端API接口
interface TerminalApi {
  getTerminals(params?: SearchParams): Promise<PaginatedResponse<Terminal>>;
  getTerminal(id: number): Promise<Terminal>;
  createTerminal(data: CreateTerminalRequest): Promise<Terminal>;
  updateTerminal(id: number, data: UpdateTerminalRequest): Promise<Terminal>;
  deleteTerminal(id: number): Promise<void>;
}
```

### 4.3 节目管理模块 (Program)
```typescript
// 节目状态管理
interface ProgramState {
  programs: Program[];
  currentProgram: Program | null;
  loading: boolean;
  pagination: PaginationInfo;
}

// 节目API接口
interface ProgramApi {
  getPrograms(params?: SearchParams): Promise<PaginatedResponse<Program>>;
  getProgram(id: number): Promise<Program>;
  createProgram(data: CreateProgramRequest): Promise<Program>;
  updateProgram(id: number, data: UpdateProgramRequest): Promise<Program>;
  deleteProgram(id: number): Promise<void>;
  publishProgram(id: number): Promise<Program>;
}
```

### 4.4 媒体管理模块 (Media)
```typescript
// 媒体状态管理
interface MediaState {
  media: Media[];
  currentMedia: Media | null;
  loading: boolean;
  uploadProgress: number;
}

// 媒体API接口
interface MediaApi {
  getMedia(params?: SearchParams): Promise<PaginatedResponse<Media>>;
  uploadMedia(file: File, metadata?: MediaMetadata): Promise<Media>;
  deleteMedia(id: number): Promise<void>;
  getMediaThumbnail(id: number): Promise<Blob>;
}
```

### 4.5 命令管理模块 (Command)
```typescript
// 命令状态管理
interface CommandState {
  commands: Command[];
  currentCommand: Command | null;
  loading: boolean;
  realTimeStatus: Map<number, CommandStatus>;
}

// 命令API接口
interface CommandApi {
  sendCommand(data: SendCommandRequest): Promise<Command>;
  getCommandHistory(params?: SearchParams): Promise<PaginatedResponse<Command>>;
  getCommandStatus(id: number): Promise<CommandStatus>;
  cancelCommand(id: number): Promise<void>;
}
```

## 5. 状态管理设计

### 5.1 Pinia Store结构
```typescript
// 基础Store接口
interface BaseStore<T> {
  items: T[];
  current: T | null;
  loading: boolean;
  error: string | null;
  pagination: PaginationInfo;
  
  // 基础方法
  fetchItems(params?: SearchParams): Promise<void>;
  fetchItem(id: number): Promise<void>;
  createItem(data: any): Promise<void>;
  updateItem(id: number, data: any): Promise<void>;
  deleteItem(id: number): Promise<void>;
  clearError(): void;
}
```

### 5.2 状态持久化
```typescript
// 本地存储策略
interface StorageStrategy {
  // 认证信息持久化
  auth: {
    token: 'localStorage';
    user: 'localStorage';
  };
  
  // 用户偏好设置
  preferences: {
    theme: 'localStorage';
    language: 'localStorage';
    sidebarCollapsed: 'localStorage';
  };
  
  // 临时数据
  temp: {
    searchHistory: 'sessionStorage';
    formData: 'sessionStorage';
  };
}
```

## 6. 路由设计

### 6.1 路由结构
```typescript
// 路由配置
const routes = [
  {
    path: '/',
    component: AppLayout,
    children: [
      { path: 'dashboard', component: Dashboard },
      {
        path: 'terminal',
        children: [
          { path: 'list', component: TerminalList },
          { path: ':id', component: TerminalDetail },
          { path: 'groups', component: TerminalGroups },
        ],
      },
      // 其他模块路由...
    ],
  },
  {
    path: '/login',
    component: Login,
    meta: { requiresGuest: true },
  },
];
```

### 6.2 路由守卫
```typescript
// 路由守卫逻辑
router.beforeEach(async (to, from, next) => {
  // 认证检查
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login');
    return;
  }
  
  // 权限检查
  if (to.meta.requiresAdmin && !authStore.isAdmin) {
    next('/dashboard');
    return;
  }
  
  // 已登录用户重定向
  if (to.meta.requiresGuest && authStore.isAuthenticated) {
    next('/dashboard');
    return;
  }
  
  next();
});
```

## 7. API设计

### 7.1 HTTP客户端封装
```typescript
// Axios实例配置
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 响应拦截器
apiClient.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response?.status === 401) {
      authStore.logout();
      router.push('/login');
    }
    return Promise.reject(error);
  }
);
```

### 7.2 API模块化
```typescript
// API模块结构
export const terminalApi = {
  // 基础CRUD
  getTerminals: (params) => apiClient.get('/terminals', { params }),
  getTerminal: (id) => apiClient.get(`/terminals/${id}`),
  createTerminal: (data) => apiClient.post('/terminals', data),
  updateTerminal: (id, data) => apiClient.put(`/terminals/${id}`, data),
  deleteTerminal: (id) => apiClient.delete(`/terminals/${id}`),
  
  // 业务方法
  updateStatus: (id, status) => apiClient.put(`/terminals/${id}/status`, { status }),
  getHeartbeat: (id) => apiClient.get(`/terminals/${id}/heartbeat`),
};
```

## 8. 组件设计

### 8.1 组件分类
- **展示组件**: 纯UI组件，无状态
- **容器组件**: 包含业务逻辑的组件
- **布局组件**: 页面布局相关组件
- **表单组件**: 数据输入相关组件

### 8.2 组件通信
```typescript
// Props定义
interface ComponentProps {
  // 数据传递
  data: any;
  
  // 事件回调
  onUpdate: (data: any) => void;
  onDelete: (id: number) => void;
  
  // 配置选项
  config: {
    editable: boolean;
    deletable: boolean;
    showActions: boolean;
  };
}
```

## 9. 样式设计

### 9.1 CSS架构
```css
/* CSS变量定义 */
:root {
  /* 颜色系统 */
  --primary-color: #667eea;
  --secondary-color: #764ba2;
  --success-color: #67c23a;
  --warning-color: #e6a23c;
  --error-color: #f56c6c;
  
  /* 间距系统 */
  --spacing-xs: 4px;
  --spacing-sm: 8px;
  --spacing-md: 16px;
  --spacing-lg: 24px;
  --spacing-xl: 32px;
  
  /* 字体系统 */
  --font-size-xs: 12px;
  --font-size-sm: 14px;
  --font-size-md: 16px;
  --font-size-lg: 18px;
  --font-size-xl: 24px;
}
```

### 9.2 响应式设计
```css
/* 断点定义 */
@media (max-width: 768px) {
  /* 移动端样式 */
}

@media (min-width: 769px) and (max-width: 1024px) {
  /* 平板端样式 */
}

@media (min-width: 1025px) {
  /* 桌面端样式 */
}
```

## 10. 性能优化

### 10.1 代码分割
```typescript
// 路由懒加载
const routes = [
  {
    path: '/terminal',
    component: () => import('@/pages/terminal/TerminalList.vue'),
  },
];

// 组件懒加载
const LazyComponent = defineAsyncComponent(() => 
  import('@/components/LazyComponent.vue')
);
```

### 10.2 缓存策略
```typescript
// API缓存
const useCachedApi = (key: string, fetcher: () => Promise<any>) => {
  const cache = new Map();
  
  return async () => {
    if (cache.has(key)) {
      return cache.get(key);
    }
    
    const data = await fetcher();
    cache.set(key, data);
    return data;
  };
};
```

## 11. 安全设计

### 11.1 认证安全
- Token存储在localStorage
- 自动刷新Token机制
- 路由级别的权限控制

### 11.2 数据安全
- 输入验证和过滤
- XSS防护
- CSRF防护

## 12. 测试策略

### 12.1 单元测试
- 组件测试
- Store测试
- 工具函数测试

### 12.2 集成测试
- API集成测试
- 路由测试
- 用户交互测试

## 13. 部署配置

### 13.1 构建配置
```typescript
// vite.config.ts
export default defineConfig({
  build: {
    target: 'es2015',
    outDir: 'dist',
    assetsDir: 'assets',
    sourcemap: false,
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
```

### 13.2 环境配置
```typescript
// 环境变量
interface ImportMetaEnv {
  VITE_API_BASE_URL: string;
  VITE_APP_TITLE: string;
  VITE_APP_VERSION: string;
}
```

## 14. 开发规范

### 14.1 代码规范
- ESLint + Prettier
- TypeScript严格模式
- Vue 3 Composition API

### 14.2 命名规范
- 组件名: PascalCase
- 文件名: kebab-case
- 变量名: camelCase
- 常量名: UPPER_SNAKE_CASE

### 14.3 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式
refactor: 重构
test: 测试
chore: 构建过程或辅助工具的变动
```

## 15. 总结

CloudControl-Nexus前端架构采用现代化的技术栈和设计模式，具有以下特点：

1. **模块化设计**: 按功能模块组织代码，便于维护和扩展
2. **类型安全**: 全面使用TypeScript，提升开发体验
3. **状态管理**: 使用Pinia进行响应式状态管理
4. **组件化**: 高度组件化，提高代码复用性
5. **性能优化**: 代码分割、懒加载等优化策略
6. **安全可靠**: 完善的认证和权限控制机制
7. **开发友好**: 完善的开发工具和规范

该架构为CloudControl-Nexus提供了稳定、可扩展、易维护的前端基础，能够满足复杂的设备管理需求。 