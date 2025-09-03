<template>
  <div class="terminal-list-page">
    <div class="page-header">
      <h1>终端列表</h1>
      <el-button type="primary" @click="createTerminal">
        添加终端
      </el-button>
    </div>

    <el-card>
      <template #header>
        <div class="card-header">
          <span>终端管理</span>
          <el-button type="text" @click="refreshList">
            刷新
          </el-button>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="terminals"
        style="width: 100%"
      >
        <el-table-column prop="serial_no" label="序列号" width="120" />
        <el-table-column prop="device_name" label="设备名称" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="last_heartbeat" label="最后心跳" width="180">
          <template #default="{ row }">
            {{ formatTime(row.last_heartbeat) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="text" @click="viewTerminal(row)">
              查看
            </el-button>
            <el-button type="text" @click="editTerminal(row)">
              编辑
            </el-button>
            <el-button type="text" @click="deleteTerminal(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import type { Terminal } from '@/types';

const router = useRouter();

// 状态
const loading = ref(false);
const terminals = ref<Terminal[]>([]);

// 方法
const loadTerminals = async () => {
  loading.value = true;
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    terminals.value = [
      {
        id: 1,
        serial_no: 1,
        device_name: '终端001',
        status: 'ONLINE',
        last_heartbeat: new Date().toISOString(),
        created_at: '2024-01-01T00:00:00Z',
        updated_at: '2024-01-01T00:00:00Z',
      },
      {
        id: 2,
        serial_no: 2,
        device_name: '终端002',
        status: 'OFFLINE',
        last_heartbeat: new Date(Date.now() - 300000).toISOString(),
        created_at: '2024-01-01T00:00:00Z',
        updated_at: '2024-01-01T00:00:00Z',
      },
    ];
  } catch (error) {
    ElMessage.error('加载终端列表失败');
  } finally {
    loading.value = false;
  }
};

const refreshList = () => {
  loadTerminals();
  ElMessage.success('列表已刷新');
};

const createTerminal = () => {
  ElMessage.info('创建终端功能待实现');
};

const viewTerminal = (terminal: Terminal) => {
  router.push(`/terminal/${terminal.serial_no}`);
};

const editTerminal = (terminal: Terminal) => {
  ElMessage.info('编辑终端功能待实现');
};

const deleteTerminal = async (terminal: Terminal) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除终端 "${terminal.device_name}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );
    
    ElMessage.success('删除成功');
    loadTerminals();
  } catch (error) {
    // 用户取消
  }
};

const getStatusType = (status: string) => {
  const typeMap: Record<string, string> = {
    ONLINE: 'success',
    OFFLINE: 'danger',
    ERROR: 'warning',
    MAINTENANCE: 'info',
  };
  return typeMap[status] || 'info';
};

const getStatusText = (status: string) => {
  const textMap: Record<string, string> = {
    ONLINE: '在线',
    OFFLINE: '离线',
    ERROR: '错误',
    MAINTENANCE: '维护',
  };
  return textMap[status] || status;
};

const formatTime = (time: string) => {
  return new Date(time).toLocaleString('zh-CN');
};

// 生命周期
onMounted(() => {
  loadTerminals();
});
</script>

<style scoped>
.terminal-list-page {
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 