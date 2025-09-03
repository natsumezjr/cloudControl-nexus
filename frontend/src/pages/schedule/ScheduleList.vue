<template>
  <div class="schedule-list">
    <div class="page-header">
      <h2>排程管理</h2>
      <el-button type="primary" @click="$router.push('/schedule/create')">
        <el-icon><Plus /></el-icon>
        创建排程
      </el-button>
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
        <el-form-item label="状态">
          <el-select v-model="filterForm.status" placeholder="选择状态" clearable>
            <el-option label="待执行" value="PENDING" />
            <el-option label="执行中" value="ACTIVE" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已取消" value="CANCELLED" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadSchedules">搜索</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <el-table
        v-loading="loading"
        :data="schedules"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="terminal_id" label="终端ID" width="100" />
        <el-table-column prop="program_id" label="节目ID" width="100" />
        <el-table-column prop="start_time" label="开始时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.start_time) }}
          </template>
        </el-table-column>
        <el-table-column prop="end_time" label="结束时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.end_time) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="80" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="viewSchedule(row)">查看</el-button>
            <el-button size="small" type="primary" @click="editSchedule(row)">编辑</el-button>
            <el-button size="small" type="success" @click="enableSchedule(row)" v-if="row.status === 'CANCELLED'">启用</el-button>
            <el-button size="small" type="warning" @click="disableSchedule(row)" v-if="row.status === 'ACTIVE'">禁用</el-button>
            <el-button size="small" type="danger" @click="deleteSchedule(row)">删除</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { Schedule, Terminal } from '@/types'

const router = useRouter()

const loading = ref(false)
const schedules = ref<Schedule[]>([])
const selectedSchedules = ref<Schedule[]>([])
const terminals = ref<Terminal[]>([])

const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

const filterForm = reactive({
  terminal_id: null as number | null,
  status: ''
})

const loadSchedules = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await scheduleApiService.getSchedules({
    //   page: pagination.page - 1,
    //   size: pagination.size,
    //   filters: filterForm
    // })
    // schedules.value = response.data.content
    // pagination.total = response.data.total_elements
    
    // 模拟数据
    schedules.value = [
      {
        id: 1,
        terminal_id: 1,
        program_id: 1,
        start_time: new Date().toISOString(),
        end_time: new Date(Date.now() + 3600000).toISOString(),
        status: 'ACTIVE',
        priority: 1
      }
    ] as Schedule[]
    pagination.total = 1
  } catch (error) {
    ElMessage.error('加载排程列表失败')
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
  filterForm.status = ''
  loadSchedules()
}

const handleSelectionChange = (selection: Schedule[]) => {
  selectedSchedules.value = selection
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  pagination.page = 1
  loadSchedules()
}

const handleCurrentChange = (page: number) => {
  pagination.page = page
  loadSchedules()
}

const viewSchedule = (schedule: Schedule) => {
  router.push(`/schedule/${schedule.id}`)
}

const editSchedule = (schedule: Schedule) => {
  router.push(`/schedule/${schedule.id}/edit`)
}

const enableSchedule = async (schedule: Schedule) => {
  try {
    // TODO: 调用API
    // await scheduleApiService.enableSchedule(schedule.id)
    ElMessage.success('排程已启用')
    loadSchedules()
  } catch (error) {
    ElMessage.error('启用失败')
  }
}

const disableSchedule = async (schedule: Schedule) => {
  try {
    // TODO: 调用API
    // await scheduleApiService.disableSchedule(schedule.id)
    ElMessage.success('排程已禁用')
    loadSchedules()
  } catch (error) {
    ElMessage.error('禁用失败')
  }
}

const deleteSchedule = async (schedule: Schedule) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除这个排程吗？',
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await scheduleApiService.deleteSchedule(schedule.id)
    ElMessage.success('删除成功')
    loadSchedules()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const getStatusType = (status: string) => {
  const statusMap: Record<string, string> = {
    PENDING: 'info',
    ACTIVE: 'success',
    COMPLETED: 'warning',
    CANCELLED: 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    PENDING: '待执行',
    ACTIVE: '执行中',
    COMPLETED: '已完成',
    CANCELLED: '已取消'
  }
  return statusMap[status] || status
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadSchedules()
  loadTerminals()
})
</script>

<style scoped>
.schedule-list {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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