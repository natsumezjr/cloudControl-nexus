<template>
  <div class="app-layout">
    <!-- 侧边栏 -->
    <div class="sidebar" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
      <!-- 侧边栏头部 -->
      <div class="sidebar-header">
        <div class="logo">
          <DataBoard :size="24" color="#409eff" />
          <span v-if="!sidebarCollapsed" class="logo-text">CloudControl</span>
        </div>
        <el-button
          v-if="!sidebarCollapsed"
          class="sidebar-toggle"
          type="text"
          :icon="Fold"
          @click="toggleSidebar"
        />
      </div>

      <!-- 侧边栏导航 -->
      <div class="sidebar-nav">
        <el-menu
          :default-active="activeMenu"
          :collapse="sidebarCollapsed"
          router
          background-color="#fff"
          text-color="#606266"
          active-text-color="#409eff"
        >
          <el-menu-item index="/dashboard">
            <el-icon><DataBoard /></el-icon>
            <template #title>仪表板</template>
          </el-menu-item>

          <el-sub-menu index="/terminal">
            <template #title>
              <el-icon><Terminal /></el-icon>
              <span>终端管理</span>
            </template>
            <el-menu-item index="/terminal/list">终端列表</el-menu-item>
            <el-menu-item index="/terminal/groups">终端组</el-menu-item>
            <el-menu-item index="/terminal/tags">标签管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/program">
            <template #title>
              <el-icon><VideoPlay /></el-icon>
              <span>节目管理</span>
            </template>
            <el-menu-item index="/program/list">节目列表</el-menu-item>
            <el-menu-item index="/program/create">创建节目</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/schedule">
            <template #title>
              <el-icon><Calendar /></el-icon>
              <span>排程管理</span>
            </template>
            <el-menu-item index="/schedule/list">排程列表</el-menu-item>
            <el-menu-item index="/schedule/create">创建排程</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/media">
            <template #title>
              <el-icon><Picture /></el-icon>
              <span>媒体管理</span>
            </template>
            <el-menu-item index="/media/list">媒体列表</el-menu-item>
            <el-menu-item index="/media/upload">上传媒体</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/command">
            <template #title>
              <el-icon><Monitor /></el-icon>
              <span>命令管理</span>
            </template>
            <el-menu-item index="/command/send">发送命令</el-menu-item>
            <el-menu-item index="/command/history">历史记录</el-menu-item>
          </el-sub-menu>

          <el-menu-item v-if="isAdmin" index="/system" disabled>
            <el-icon><Setting /></el-icon>
            <template #title>系统管理</template>
          </el-menu-item>

          <el-sub-menu v-if="isAdmin" index="/system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/system/users">用户管理</el-menu-item>
            <el-menu-item index="/system/settings">系统设置</el-menu-item>
            <el-menu-item index="/system/logs">系统日志</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
    </div>

    <!-- 主内容区域 -->
    <div class="main-content" :class="{ 'main-expanded': sidebarCollapsed }">
      <!-- 顶部导航栏 -->
      <div class="top-navbar">
        <div class="navbar-left">
          <el-button
            v-if="sidebarCollapsed"
            class="sidebar-toggle"
            type="text"
            :icon="Expand"
            @click="toggleSidebar"
          />
          <el-breadcrumb separator="/">
            <el-breadcrumb-item
              v-for="item in breadcrumbs"
              :key="item.path"
              :to="item.path"
            >
              {{ item.name }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="navbar-right">
          <!-- 通知按钮 -->
          <el-badge :value="notificationCount" :hidden="notificationCount === 0">
            <el-button
              type="text"
              :icon="Bell"
              @click="showNotifications"
            />
          </el-badge>

          <!-- 用户菜单 -->
          <el-dropdown @command="handleUserCommand">
            <div class="user-menu">
              <el-avatar :size="32" :src="userAvatar">
                {{ userInitials }}
              </el-avatar>
              <span v-if="user" class="username">{{ user.username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人资料</el-dropdown-item>
                <el-dropdown-item command="settings">设置</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>

      <!-- 页面内容 -->
      <div class="page-content">
        <router-view />
      </div>
    </div>

    <!-- 通知抽屉 -->
    <el-drawer
      v-model="notificationDrawerVisible"
      title="通知"
      direction="rtl"
      size="400px"
    >
      <div class="notification-list">
        <div
          v-for="notification in notifications"
          :key="notification.id"
          class="notification-item"
          :class="{ unread: !notification.read }"
        >
          <div class="notification-icon">
            <el-icon :color="getNotificationColor(notification.type)">
              <component :is="getNotificationIcon(notification.type)" />
            </el-icon>
          </div>
          <div class="notification-content">
            <div class="notification-title">{{ notification.title }}</div>
            <div class="notification-message">{{ notification.message }}</div>
            <div class="notification-time">{{ formatTime(notification.created_at) }}</div>
          </div>
        </div>
        <div v-if="notifications.length === 0" class="empty-notifications">
          <el-empty description="暂无通知" />
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  Monitor,
  Fold,
  Expand,
  DataBoard,
  VideoPlay,
  Picture,
  Monitor as Terminal,
  Calendar,
  Setting,
  Bell,
  ArrowDown,
  User,
  SwitchButton,
  Warning,
  CircleCheck,
  InfoFilled,
  CircleClose,
} from '@element-plus/icons-vue';
import { useAuthStore } from '@/stores/auth';

// 状态
const sidebarCollapsed = ref(false);
const notificationDrawerVisible = ref(false);
const notificationCount = ref(0);
const notifications = ref<any[]>([]);

// 路由和store
const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

// 计算属性
const user = computed(() => authStore.user);
const isAdmin = computed(() => authStore.isAdmin);
const userAvatar = computed(() => user.value?.avatar || '');
const userInitials = computed(() => {
  if (!user.value?.username) return '';
  return user.value.username.substring(0, 2).toUpperCase();
});

const activeMenu = computed(() => route.path);

const breadcrumbs = computed(() => {
  const paths = route.path.split('/').filter(Boolean);
  const breadcrumbs = [{ path: '/', name: '首页' }];
  
  let currentPath = '';
  paths.forEach((path, index) => {
    currentPath += `/${path}`;
    const name = getBreadcrumbName(path, index);
    breadcrumbs.push({ path: currentPath, name });
  });
  
  return breadcrumbs;
});

// 方法
const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value;
};

const getBreadcrumbName = (path: string, index: number) => {
  const nameMap: Record<string, string> = {
    dashboard: '仪表板',
    terminal: '终端管理',
    list: '列表',
    groups: '终端组',
    tags: '标签管理',
    program: '节目管理',
    create: '创建',
    schedule: '排程管理',
    media: '媒体管理',
    upload: '上传',
    command: '命令管理',
    send: '发送命令',
    history: '历史记录',
    system: '系统管理',
    users: '用户管理',
    settings: '设置',
    logs: '系统日志',
  };
  
  return nameMap[path] || path;
};

const showNotifications = () => {
  notificationDrawerVisible.value = true;
  // 标记所有通知为已读
  notificationCount.value = 0;
};

const handleUserCommand = async (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/profile');
      break;
    case 'settings':
      router.push('/settings');
      break;
    case 'logout':
      await handleLogout();
      break;
  }
};

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });
    
    await authStore.logout();
    ElMessage.success('已退出登录');
    router.push('/login');
  } catch (error) {
    // 用户取消
  }
};

const getNotificationColor = (type: string) => {
  const colorMap: Record<string, string> = {
    success: '#67c23a',
    warning: '#e6a23c',
    error: '#f56c6c',
    info: '#909399',
  };
  return colorMap[type] || '#909399';
};

const getNotificationIcon = (type: string) => {
  const iconMap: Record<string, any> = {
    success: CircleCheck,
    warning: Warning,
    error: CircleClose,
    info: InfoFilled,
  };
  return iconMap[type] || InfoFilled;
};

const formatTime = (time: string) => {
  return new Date(time).toLocaleString('zh-CN');
};

// 生命周期
onMounted(() => {
  // 初始化认证状态
  authStore.initialize();
  
  // 模拟通知数据
  notifications.value = [
    {
      id: 1,
      type: 'success',
      title: '系统通知',
      message: '终端001已成功连接',
      read: false,
      created_at: new Date().toISOString(),
    },
    {
      id: 2,
      type: 'warning',
      title: '设备警告',
      message: '终端002离线超过5分钟',
      read: false,
      created_at: new Date(Date.now() - 60000).toISOString(),
    },
  ];
  
  notificationCount.value = notifications.value.filter(n => !n.read).length;
});
</script>

<style scoped>
.app-layout {
  display: flex;
  height: 100vh;
  background: #f5f7fa;
}

.sidebar {
  width: 240px;
  background: #fff;
  border-right: 1px solid #e4e7ed;
  transition: width 0.3s ease;
  display: flex;
  flex-direction: column;
}

.sidebar-collapsed {
  width: 64px;
}

.sidebar-header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  border-bottom: 1px solid #e4e7ed;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo-text {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.sidebar-toggle {
  padding: 4px;
}

.sidebar-nav {
  flex: 1;
  overflow-y: auto;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  transition: margin-left 0.3s ease;
}

.main-expanded {
  margin-left: 0;
}

.top-navbar {
  height: 60px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
}

.navbar-left {
  display: flex;
  align-items: center;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.notification-badge {
  margin-right: 8px;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.user-menu:hover {
  background-color: #f5f7fa;
}

.username {
  font-size: 14px;
  color: #606266;
}

.page-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.notification-list {
  padding: 16px;
}

.notification-item {
  display: flex;
  gap: 12px;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 8px;
  transition: background-color 0.2s;
}

.notification-item:hover {
  background-color: #f5f7fa;
}

.notification-item.unread {
  background-color: #f0f9ff;
}

.notification-icon {
  flex-shrink: 0;
  margin-top: 2px;
}

.notification-content {
  flex: 1;
}

.notification-title {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
}

.notification-message {
  font-size: 14px;
  color: #606266;
  margin-bottom: 4px;
}

.notification-time {
  font-size: 12px;
  color: #909399;
}

.empty-notifications {
  text-align: center;
  padding: 40px 0;
}
</style> 