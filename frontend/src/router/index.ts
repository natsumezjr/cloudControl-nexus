import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import AppLayout from '@/components/layout/AppLayout.vue';
import Dashboard from '@/pages/Dashboard.vue';
import DeviceControlPanel from '@/components/DeviceControlPanel.vue';

// 路由配置
const routes = [
  {
    path: '/',
    component: AppLayout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
          title: '仪表板',
          requiresAuth: true,
        },
      },
      {
        path: '/terminal',
        name: 'Terminal',
        redirect: '/terminal/list',
        meta: {
          title: '终端管理',
          requiresAuth: true,
        },
        children: [
          {
            path: 'list',
            name: 'TerminalList',
            component: () => import('@/pages/terminal/TerminalList.vue'),
            meta: {
              title: '终端列表',
            },
          },
          {
            path: ':id',
            name: 'TerminalDetail',
            component: () => import('@/pages/terminal/TerminalDetail.vue'),
            meta: {
              title: '终端详情',
            },
          },
          {
            path: 'groups',
            name: 'TerminalGroups',
            component: () => import('@/pages/terminal/TerminalGroups.vue'),
            meta: {
              title: '终端组',
            },
          },
          {
            path: 'tags',
            name: 'TerminalTags',
            component: () => import('@/pages/terminal/TerminalTags.vue'),
            meta: {
              title: '标签管理',
            },
          },
        ],
      },
      {
        path: '/program',
        name: 'Program',
        redirect: '/program/list',
        meta: {
          title: '节目管理',
          requiresAuth: true,
        },
        children: [
          {
            path: 'list',
            name: 'ProgramList',
            component: () => import('@/pages/program/ProgramList.vue'),
            meta: {
              title: '节目列表',
            },
          },
          {
            path: 'create',
            name: 'ProgramCreate',
            component: () => import('@/pages/program/ProgramCreate.vue'),
            meta: {
              title: '创建节目',
            },
          },
          {
            path: ':id',
            name: 'ProgramDetail',
            component: () => import('@/pages/program/ProgramDetail.vue'),
            meta: {
              title: '节目详情',
            },
          },
          {
            path: ':id/edit',
            name: 'ProgramEdit',
            component: () => import('@/pages/program/ProgramEdit.vue'),
            meta: {
              title: '编辑节目',
            },
          },
        ],
      },
      {
        path: '/media',
        name: 'Media',
        redirect: '/media/list',
        meta: {
          title: '媒体管理',
          requiresAuth: true,
        },
        children: [
          {
            path: 'list',
            name: 'MediaList',
            component: () => import('@/pages/media/MediaList.vue'),
            meta: {
              title: '媒体库',
            },
          },
          {
            path: 'upload',
            name: 'MediaUpload',
            component: () => import('@/pages/media/MediaUpload.vue'),
            meta: {
              title: '上传媒体',
            },
          },
          {
            path: ':id',
            name: 'MediaDetail',
            component: () => import('@/pages/media/MediaDetail.vue'),
            meta: {
              title: '媒体详情',
            },
          },
        ],
      },
      {
        path: '/command',
        name: 'Command',
        redirect: '/command/send',
        meta: {
          title: '命令管理',
          requiresAuth: true,
        },
        children: [
          {
            path: 'send',
            name: 'CommandSend',
            component: () => import('@/pages/command/CommandSend.vue'),
            meta: {
              title: '发送命令',
            },
          },
          {
            path: 'history',
            name: 'CommandHistory',
            component: () => import('@/pages/command/CommandHistory.vue'),
            meta: {
              title: '命令历史',
            },
          },
          {
            path: ':id',
            name: 'CommandDetail',
            component: () => import('@/pages/command/CommandDetail.vue'),
            meta: {
              title: '命令详情',
            },
          },
        ],
      },
      {
        path: '/schedule',
        name: 'Schedule',
        redirect: '/schedule/list',
        meta: {
          title: '排程管理',
          requiresAuth: true,
          requiresAdmin: true,
        },
        children: [
          {
            path: 'list',
            name: 'ScheduleList',
            component: () => import('@/pages/schedule/ScheduleList.vue'),
            meta: {
              title: '排程列表',
            },
          },
          {
            path: 'create',
            name: 'ScheduleCreate',
            component: () => import('@/pages/schedule/ScheduleCreate.vue'),
            meta: {
              title: '创建排程',
            },
          },
          {
            path: ':id',
            name: 'ScheduleDetail',
            component: () => import('@/pages/schedule/ScheduleDetail.vue'),
            meta: {
              title: '排程详情',
            },
          },
        ],
      },
      {
        path: '/system',
        name: 'System',
        redirect: '/system/users',
        meta: {
          title: '系统管理',
          requiresAuth: true,
          requiresAdmin: true,
        },
        children: [
          {
            path: 'users',
            name: 'UserManagement',
            component: () => import('@/pages/system/UserManagement.vue'),
            meta: {
              title: '用户管理',
            },
          },
          {
            path: 'settings',
            name: 'SystemSettings',
            component: () => import('@/pages/system/SystemSettings.vue'),
            meta: {
              title: '系统设置',
            },
          },
          {
            path: 'logs',
            name: 'SystemLogs',
            component: () => import('@/pages/system/SystemLogs.vue'),
            meta: {
              title: '系统日志',
            },
          },
        ],
      },
      {
        path: '/profile',
        name: 'Profile',
        component: () => import('@/pages/Profile.vue'),
        meta: {
          title: '个人资料',
          requiresAuth: true,
        },
      },
      {
        path: '/settings',
        name: 'Settings',
        component: () => import('@/pages/Settings.vue'),
        meta: {
          title: '设置',
          requiresAuth: true,
        },
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/pages/auth/Login.vue'),
    meta: {
      title: '登录',
      requiresGuest: true,
    },
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/pages/auth/Register.vue'),
    meta: {
      title: '注册',
      requiresGuest: true,
    },
  },
  {
    path: '/forgot-password',
    name: 'ForgotPassword',
    component: () => import('@/pages/auth/ForgotPassword.vue'),
    meta: {
      title: '忘记密码',
      requiresGuest: true,
    },
  },
  {
    path: '/reset-password',
    name: 'ResetPassword',
    component: () => import('@/pages/auth/ResetPassword.vue'),
    meta: {
      title: '重置密码',
      requiresGuest: true,
    },
  },
  // 调试工具路由
  {
    path: '/debug',
    name: 'Debug',
    component: DeviceControlPanel,
    meta: {
      title: '调试工具',
    },
  },
  // 404页面
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/pages/NotFound.vue'),
    meta: {
      title: '页面未找到',
    },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 路由守卫
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} - CloudControl-Nexus`;
  }

  const authStore = useAuthStore();
  
  // 初始化认证状态
  if (!authStore.isAuthenticated) {
    authStore.initialize();
  }

  // 需要认证的页面
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login');
    return;
  }

  // 需要管理员权限的页面
  if (to.meta.requiresAdmin && !authStore.isAdmin) {
    next('/dashboard');
    return;
  }

  // 已登录用户不能访问登录页面
  if (to.meta.requiresGuest && authStore.isAuthenticated) {
    next('/dashboard');
    return;
  }

  next();
});

export default router; 