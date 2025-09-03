<template>
  <div class="command-history">
    <div class="page-header">
      <h2>命令历史</h2>
    </div>

    <el-card class="filter-card">
      <el-form :model="filterForm" inline>
        <el-form-item label="终端">
          <el-select v-model="filterForm.terminal_id" placeholder="选择终端" clearable>
            <el-option
              v-for="terminal in terminals"
              :key="terminal.serial_no"
              :label="terminal.device_name"
              :value="terminal.serial_no"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="命令类型">
          <el-select v-model="filterForm.command_type" placeholder="选择类型" clearable>
            <el-option label="唤醒设备" value="WAKEUP" />
            <el-option label="休眠设备" value="SLEEP" />
            <el-option label="重启设备" value="RESTART" />
            <el-option label="截图" value="SCREENSHOT" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filterForm.status" placeholder="选择状态" clearable>
            <el-option label="待处理" value="PENDING" />
            <el-option label="已发送" value="SENT" />
            <el-option label="执行中" value="EXECUTING" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="失败" value="FAILED" />
            <el-option label="超时" value="TIMEOUT" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadCommands">搜索</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <el-table
        v-loading="loading"
        :data="commands"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="terminal_id" label="终端ID" width="100" />
        <el-table-column prop="command_type" label="命令类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getCommandTypeTag(row.command_type)">
              {{ getCommandTypeText(row.command_type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="command_method" label="方法" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="created_at" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.created_at) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="viewCommand(row)">查看</el-button>
            <el-button size="small" type="primary" @click="retryCommand(row)" v-if="row.status === 'FAILED'">重试</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { Command, Terminal } from '@/types'

const router = useRouter()

const loading = ref(false)
const commands = ref<Command[]>([])
const terminals = ref<Terminal[]>([])

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

const filterForm = reactive({
  terminal_id: null as number | null,
  command_type: '',
  status: ''
})

const loadCommands = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await commandApiService.getCommands({
    //   page: pagination.page - 1,
    //   size: pagination.size,
    //   filters: filterForm
    // })
    // commands.value = response.data.content
    // pagination.total = response.data.total_elements
    
    // 模拟数据
    commands.value = [
      {
        id: 1,
        terminal_id: 1,
        command_type: 'WAKEUP',
        command_method: 'POST',
        status: 'COMPLETED',
        created_at: new Date().toISOString()
      }
    ] as Command[]
    pagination.total = 1
  } catch (error) {
    ElMessage.error('加载命令历史失败')
  } finally {
    loading.value = false
  }
}

const loadTerminals = async () => {
  try {
    // TODO: 调用API
    // const response = await terminalApiService.getTerminals()
    // terminals.value = response.data.content
    
    // 模拟数据
    terminals.value = [
      {
        serial_no: 1,
        device_name: 'Terminal-001',
        status: 'ONLINE'
      }
    ] as Terminal[]
  } catch (error) {
    ElMessage.error('加载终端列表失败')
  }
}

const resetFilter = () => {
  filterForm.terminal_id = null
  filterForm.command_type = ''
  filterForm.status = ''
  loadCommands()
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  pagination.page = 1
  loadCommands()
}

const handleCurrentChange = (page: number) => {
  pagination.page = page
  loadCommands()
}

const viewCommand = (command: Command) => {
  router.push(`/command/${command.id}`)
}

const retryCommand = async (command: Command) => {
  try {
    // TODO: 调用API
    // await commandApiService.retryCommand(command.id)
    ElMessage.success('重试命令已发送')
    loadCommands()
  } catch (error) {
    ElMessage.error('重试失败')
  }
}

const getCommandTypeTag = (type: string) => {
  const typeMap: Record<string, string> = {
    WAKEUP: 'success',
    SLEEP: 'warning',
    RESTART: 'danger',
    SCREENSHOT: 'info'
  }
  return typeMap[type] || 'info'
}

const getCommandTypeText = (type: string) => {
  const typeMap: Record<string, string> = {
    WAKEUP: '唤醒设备',
    SLEEP: '休眠设备',
    RESTART: '重启设备',
    SCREENSHOT: '截图'
  }
  return typeMap[type] || type
}

const getStatusType = (status: string) => {
  const statusMap: Record<string, string> = {
    PENDING: 'info',
    SENT: 'warning',
    EXECUTING: 'primary',
    COMPLETED: 'success',
    FAILED: 'danger',
    TIMEOUT: 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    PENDING: '待处理',
    SENT: '已发送',
    EXECUTING: '执行中',
    COMPLETED: '已完成',
    FAILED: '失败',
    TIMEOUT: '超时'
  }
  return statusMap[status] || status
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadCommands()
  loadTerminals()
})
</script>

<style scoped>
.command-history {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.filter-card {
  margin-bottom: 20px;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style> 