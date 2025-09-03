<template>
  <div class="dashboard">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1>仪表板</h1>
      <el-button 
        type="primary" 
        :icon="Refresh" 
        :loading="loading"
        @click="loadDashboardData"
      >
        刷新数据
      </el-button>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-grid">
      <!-- 终端统计 -->
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon terminal-icon">
            <TerminalIcon :size="32" />
          </div>
          <div class="stat-info">
            <div class="stat-number">{{ stats.terminals.total }}</div>
            <div class="stat-label">终端总数</div>
          </div>
        </div>
        <div class="stat-footer">
          <div class="stat-detail">
            在线: {{ stats.terminals.online }} | 离线: {{ stats.terminals.offline }}
          </div>
        </div>
      </el-card>

      <!-- 节目统计 -->
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon program-icon">
            <VideoPlay :size="32" />
          </div>
          <div class="stat-info">
            <div class="stat-number">{{ stats.programs.total }}</div>
            <div class="stat-label">节目总数</div>
          </div>
        </div>
        <div class="stat-footer">
          <div class="stat-detail">
            已发布: {{ stats.programs.published }} | 草稿: {{ stats.programs.draft }}
          </div>
        </div>
      </el-card>

      <!-- 命令统计 -->
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon command-icon">
            <Monitor :size="32" />
          </div>
          <div class="stat-info">
            <div class="stat-number">{{ stats.commands.total }}</div>
            <div class="stat-label">命令总数</div>
          </div>
        </div>
        <div class="stat-footer">
          <div class="stat-detail">
            成功: {{ stats.commands.success }} | 失败: {{ stats.commands.failed }}
          </div>
        </div>
      </el-card>

      <!-- 媒体统计 -->
      <el-card class="stat-card">
        <div class="stat-content">
          <div class="stat-icon media-icon">
            <Picture :size="32" />
          </div>
          <div class="stat-info">
            <div class="stat-number">{{ stats.media.total }}</div>
            <div class="stat-label">媒体总数</div>
          </div>
        </div>
        <div class="stat-footer">
          <div class="stat-detail">
            图片: {{ stats.media.images }} | 视频: {{ stats.media.videos }}
          </div>
        </div>
      </el-card>
    </div>

    <!-- 图表和监控区域 -->
    <div class="charts-grid">
      <!-- 终端状态图表 -->
      <el-card class="chart-card">
        <template #header>
          <div class="card-header">
            <span>终端状态分布</span>
            <el-button 
              type="text" 
              :icon="Refresh" 
              @click="refreshTerminalStats"
            >
              刷新
            </el-button>
          </div>
        </template>
        <div class="chart-container">
          <div class="chart" ref="terminalChartRef">
            <div style="text-align: center;">
              <div style="font-size: 48px; color: #409eff; margin-bottom: 16px;">
                {{ stats.terminals.online }}/{{ stats.terminals.total }}
              </div>
              <div style="font-size: 16px; color: #606266;">
                在线终端
              </div>
              <div style="margin-top: 16px;">
                <el-progress 
                  :percentage="Math.round((stats.terminals.online / stats.terminals.total) * 100)" 
                  :color="getProgressColor(Math.round((stats.terminals.online / stats.terminals.total) * 100))"
                />
              </div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 命令执行图表 -->
      <el-card class="chart-card">
        <template #header>
          <div class="card-header">
            <span>命令执行状态</span>
            <el-button 
              type="text" 
              :icon="Refresh" 
              @click="refreshCommandStats"
            >
              刷新
            </el-button>
          </div>
        </template>
        <div class="chart-container">
          <div class="chart" ref="commandChartRef">
            <div style="text-align: center;">
              <div style="font-size: 48px; color: #67c23a; margin-bottom: 16px;">
                {{ stats.commands.success }}/{{ stats.commands.total }}
              </div>
              <div style="font-size: 16px; color: #606266;">
                成功命令
              </div>
              <div style="margin-top: 16px;">
                <el-progress 
                  :percentage="Math.round((stats.commands.success / stats.commands.total) * 100)" 
                  :color="getProgressColor(Math.round((stats.commands.success / stats.commands.total) * 100))"
                />
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 监控区域 -->
    <div class="monitor-grid">
      <!-- 在线终端 -->
      <el-card class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>在线终端</span>
            <el-button 
              type="text" 
              @click="viewAllTerminals"
            >
              查看全部
            </el-button>
          </div>
        </template>
        <div class="terminal-list">
          <div 
            v-for="terminal in onlineTerminals" 
            :key="terminal.serial_no"
            class="terminal-item"
          >
            <div class="terminal-info">
              <div class="terminal-name">{{ terminal.device_name }}</div>
              <div class="terminal-status">
                <el-tag 
                  :type="terminal.status === 'ONLINE' ? 'success' : 'danger'"
                  size="small"
                >
                  {{ terminal.status === 'ONLINE' ? '在线' : '离线' }}
                </el-tag>
                <span class="last-heartbeat">
                  最后心跳: {{ formatTime(terminal.last_heartbeat) }}
                </span>
              </div>
            </div>
            <div class="terminal-actions">
              <el-button 
                type="text" 
                size="small"
                @click="viewTerminal(terminal)"
              >
                查看
              </el-button>
            </div>
          </div>
          <div v-if="onlineTerminals.length === 0" class="more-terminals">
            <el-empty description="暂无在线终端" />
          </div>
        </div>
      </el-card>

      <!-- 最近命令 -->
      <el-card class="monitor-card">
        <template #header>
          <div class="card-header">
            <span>最近命令</span>
            <el-button 
              type="text" 
              @click="viewAllCommands"
            >
              查看全部
            </el-button>
          </div>
        </template>
        <div class="command-list">
          <div 
            v-for="command in recentCommands" 
            :key="command.id"
            class="command-item"
          >
            <div class="command-info">
              <div class="command-type">{{ getCommandTypeName(command.command_type) }}</div>
              <div class="command-status">
                <el-tag 
                  :type="getCommandStatusType(command.status)"
                  size="small"
                >
                  {{ getCommandStatusName(command.status) }}
                </el-tag>
                <span class="command-time">
                  {{ formatTime(command.created_at) }}
                </span>
              </div>
            </div>
            <div class="command-actions">
              <el-button 
                type="text" 
                size="small"
                @click="viewCommand(command)"
              >
                查看
              </el-button>
            </div>
          </div>
          <div v-if="recentCommands.length === 0" class="more-commands">
            <el-empty description="暂无最近命令" />
          </div>
        </div>
      </el-card>
    </div>

    <!-- 系统状态 -->
    <el-card class="system-status-card">
      <template #header>
        <div class="card-header">
          <span>系统状态</span>
          <el-button 
            type="text" 
            :icon="Refresh" 
            @click="refreshSystemStatus"
          >
            刷新
          </el-button>
        </div>
      </template>
      <div class="system-status-grid">
        <div class="status-item">
          <div class="status-label">CPU 使用率</div>
          <div class="status-value">
            <el-progress 
              :percentage="systemStatus.cpu" 
              :color="getProgressColor(systemStatus.cpu)"
            />
          </div>
        </div>
        <div class="status-item">
          <div class="status-label">内存使用率</div>
          <div class="status-value">
            <el-progress 
              :percentage="systemStatus.memory" 
              :color="getProgressColor(systemStatus.memory)"
            />
          </div>
        </div>
        <div class="status-item">
          <div class="status-label">磁盘使用率</div>
          <div class="status-value">
            <el-progress 
              :percentage="systemStatus.disk" 
              :color="getProgressColor(systemStatus.disk)"
            />
          </div>
        </div>
        <div class="status-item">
          <div class="status-label">网络状态</div>
          <div class="status-value">
            <el-tag 
              :type="systemStatus.network ? 'success' : 'danger'"
            >
              {{ systemStatus.network ? '正常' : '异常' }}
            </el-tag>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import {
  Monitor,
  VideoPlay,
  Monitor as TerminalIcon,
  Picture,
  Refresh,
} from '@element-plus/icons-vue';
import type { Terminal, Command } from '@/types';

// 路由
const router = useRouter();

// 状态
const loading = ref(false);
const terminalChartRef = ref<HTMLElement>();
const commandChartRef = ref<HTMLElement>();

// 统计数据
const stats = ref({
  terminals: {
    total: 0,
    online: 0,
    offline: 0,
  },
  programs: {
    total: 0,
    published: 0,
    draft: 0,
  },
  commands: {
    total: 0,
    success: 0,
    failed: 0,
  },
  media: {
    total: 0,
    images: 0,
    videos: 0,
  },
});

// 在线终端
const onlineTerminals = ref<Terminal[]>([]);

// 最近命令
const recentCommands = ref<Command[]>([]);

// 系统状态
const systemStatus = ref({
  cpu: 0,
  memory: 0,
  disk: 0,
  network: true,
});

// 定时器
let refreshTimer: NodeJS.Timeout;

// 方法
const loadDashboardData = async () => {
  loading.value = true;
  try {
    // 这里应该调用实际的API
    // 目前使用模拟数据
    await Promise.all([
      loadStats(),
      loadOnlineTerminals(),
      loadRecentCommands(),
      loadSystemStatus(),
    ]);
  } catch (error) {
    ElMessage.error('加载仪表板数据失败');
  } finally {
    loading.value = false;
  }
};

const loadStats = async () => {
  // 模拟API调用
  stats.value = {
    terminals: {
      total: 156,
      online: 142,
      offline: 14,
    },
    programs: {
      total: 89,
      published: 67,
      draft: 22,
    },
    commands: {
      total: 1234,
      success: 1189,
      failed: 45,
    },
    media: {
      total: 456,
      images: 234,
      videos: 222,
    },
  };
};

const loadOnlineTerminals = async () => {
  // 模拟API调用
  onlineTerminals.value = [
    {
      serial_no: 1,
      device_name: '终端001',
      status: 'ONLINE',
      last_heartbeat: new Date().toISOString(),
    } as Terminal,
    {
      serial_no: 2,
      device_name: '终端002',
      status: 'ONLINE',
      last_heartbeat: new Date(Date.now() - 30000).toISOString(),
    } as Terminal,
  ];
};

const loadRecentCommands = async () => {
  // 模拟API调用
  recentCommands.value = [
    {
      id: 1,
      terminal_id: 1,
      command_type: 'WAKEUP',
      status: 'COMPLETED',
      created_at: new Date().toISOString(),
    } as Command,
    {
      id: 2,
      terminal_id: 2,
      command_type: 'BRIGHTNESS',
      status: 'EXECUTING',
      created_at: new Date(Date.now() - 60000).toISOString(),
    } as Command,
  ];
};

const loadSystemStatus = async () => {
  // 模拟API调用
  systemStatus.value = {
    cpu: 45,
    memory: 62,
    disk: 78,
    network: true,
  };
};

const refreshTerminalStats = () => {
  loadStats();
  ElMessage.success('终端统计已刷新');
};

const refreshCommandStats = () => {
  loadRecentCommands();
  ElMessage.success('命令统计已刷新');
};

const refreshSystemStatus = () => {
  loadSystemStatus();
  ElMessage.success('系统状态已刷新');
};

const viewTerminal = (terminal: Terminal) => {
  router.push(`/terminal/${terminal.serial_no}`);
};

const viewAllTerminals = () => {
  router.push('/terminal/list');
};

const viewCommand = (command: Command) => {
  router.push(`/command/${command.id}`);
};

const viewAllCommands = () => {
  router.push('/command/history');
};

const getCommandTypeName = (type: string) => {
  const typeMap: Record<string, string> = {
    WAKEUP: '唤醒',
    SLEEP: '休眠',
    RESTART: '重启',
    BRIGHTNESS: '亮度调节',
    VOLUME: '音量调节',
  };
  return typeMap[type] || type;
};

const getCommandStatusName = (status: string) => {
  const statusMap: Record<string, string> = {
    PENDING: '等待中',
    SENT: '已发送',
    EXECUTING: '执行中',
    COMPLETED: '已完成',
    FAILED: '失败',
  };
  return statusMap[status] || status;
};

const getCommandStatusType = (status: string) => {
  const typeMap: Record<string, string> = {
    PENDING: 'warning',
    SENT: 'info',
    EXECUTING: 'primary',
    COMPLETED: 'success',
    FAILED: 'danger',
  };
  return typeMap[status] || 'info';
};

const getProgressColor = (percentage: number) => {
  if (percentage < 50) return '#67c23a';
  if (percentage < 80) return '#e6a23c';
  return '#f56c6c';
};

const formatTime = (time: string) => {
  return new Date(time).toLocaleString('zh-CN');
};

// 生命周期
onMounted(() => {
  loadDashboardData();
  
  // 设置定时刷新
  refreshTimer = setInterval(() => {
    loadOnlineTerminals();
    loadRecentCommands();
    loadSystemStatus();
  }, 30000); // 30秒刷新一次
});

onUnmounted(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer);
  }
});
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 24px;
  padding: 24px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #303133;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #303133;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.stat-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 12px;
}

.stat-icon {
  width: 64px;
  height: 64px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.terminal-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.program-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.command-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.media-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #303133;
  line-height: 1;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.stat-footer {
  border-top: 1px solid #f0f0f0;
  padding-top: 12px;
}

.stat-detail {
  font-size: 12px;
  color: #909399;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 20px;
}

.chart-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chart-container {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chart {
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 14px;
}

.monitor-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 20px;
}

.monitor-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.terminal-list,
.command-list {
  max-height: 300px;
  overflow-y: auto;
}

.terminal-item,
.command-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.terminal-item:last-child,
.command-item:last-child {
  border-bottom: none;
}

.terminal-info,
.command-info {
  flex: 1;
}

.terminal-name,
.command-type {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
}

.terminal-status,
.command-status {
  display: flex;
  align-items: center;
  gap: 8px;
}

.last-heartbeat,
.command-time {
  font-size: 12px;
  color: #909399;
}

.terminal-actions,
.command-actions {
  flex-shrink: 0;
}

.more-terminals,
.more-commands {
  text-align: center;
  padding: 12px 0;
}

.system-status-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.system-status-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.status-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.status-label {
  font-size: 14px;
  color: #606266;
  font-weight: 500;
}

.status-value {
  flex: 1;
}
</style> 