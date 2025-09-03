<template>
  <div class="terminal-detail">
    <div class="page-header">
      <el-button @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2>终端详情</h2>
      <div class="header-actions">
        <el-button type="primary" @click="editTerminal">编辑</el-button>
        <el-button type="danger" @click="deleteTerminal">删除</el-button>
      </div>
    </div>

    <div v-loading="loading" class="detail-content">
      <el-row :gutter="20">
        <el-col :span="16">
          <el-card class="info-card">
            <template #header>
              <div class="card-header">
                <span>基本信息</span>
                <el-tag :type="getStatusType(terminal?.status)">
                  {{ getStatusText(terminal?.status) }}
                </el-tag>
              </div>
            </template>
            
            <el-descriptions :column="2" border>
              <el-descriptions-item label="序列号">
                {{ terminal?.serial_no }}
              </el-descriptions-item>
              <el-descriptions-item label="设备名称">
                {{ terminal?.device_name }}
              </el-descriptions-item>
              <el-descriptions-item label="终端组">
                {{ getGroupName(terminal?.terminal_group_id) }}
              </el-descriptions-item>
              <el-descriptions-item label="最后心跳">
                {{ formatTime(terminal?.last_heartbeat) }}
              </el-descriptions-item>
              <el-descriptions-item label="LED描述" :span="2">
                {{ terminal?.led_description || '-' }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          <el-card class="status-card">
            <template #header>
              <span>设备状态</span>
            </template>
            
            <el-row :gutter="20">
              <el-col :span="6">
                <div class="status-item">
                  <div class="status-label">电源状态</div>
                  <el-tag :type="terminal?.power_status ? 'success' : 'danger'">
                    {{ terminal?.power_status ? '开启' : '关闭' }}
                  </el-tag>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="status-item">
                  <div class="status-label">亮度</div>
                  <div class="status-value">{{ terminal?.brightness || 0 }}%</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="status-item">
                  <div class="status-label">色温</div>
                  <div class="status-value">{{ terminal?.colortemp || 0 }}K</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="status-item">
                  <div class="status-label">音量</div>
                  <div class="status-value">{{ terminal?.volume || 0 }}%</div>
                </div>
              </el-col>
            </el-row>
          </el-card>

          <el-card class="settings-card">
            <template #header>
              <span>设备设置</span>
            </template>
            
            <el-descriptions :column="2" border>
              <el-descriptions-item label="输入模式">
                {{ terminal?.input_mode || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="语言">
                {{ terminal?.language || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="国家">
                {{ terminal?.country || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="时区">
                {{ terminal?.timezone_id || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="自动时间">
                {{ terminal?.is_auto_time ? '是' : '否' }}
              </el-descriptions-item>
              <el-descriptions-item label="GPS报告间隔">
                {{ terminal?.gps_report_interval || 0 }}秒
              </el-descriptions-item>
              <el-descriptions-item label="传感器报告间隔">
                {{ terminal?.sensor_report_interval || 0 }}秒
              </el-descriptions-item>
              <el-descriptions-item label="内容报告状态">
                {{ terminal?.content_report_status ? '开启' : '关闭' }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card class="location-card">
            <template #header>
              <span>位置信息</span>
            </template>
            
            <div class="location-info">
              <div class="location-item">
                <span class="label">纬度:</span>
                <span class="value">{{ terminal?.lat || '-' }}</span>
              </div>
              <div class="location-item">
                <span class="label">经度:</span>
                <span class="value">{{ terminal?.lng || '-' }}</span>
              </div>
            </div>
            
            <div class="map-placeholder">
              <el-empty description="地图加载中..." />
            </div>
          </el-card>

          <el-card class="program-card">
            <template #header>
              <span>当前节目</span>
            </template>
            
            <div class="program-info">
              <div class="program-item">
                <span class="label">节目名称:</span>
                <span class="value">{{ terminal?.program || '-' }}</span>
              </div>
              <div class="program-item">
                <span class="label">节目类型:</span>
                <span class="value">{{ terminal?.program_type || '-' }}</span>
              </div>
            </div>
          </el-card>

          <el-card class="actions-card">
            <template #header>
              <span>快捷操作</span>
            </template>
            
            <div class="action-buttons">
              <el-button type="primary" @click="sendCommand('WAKEUP')" :disabled="!terminal">
                唤醒设备
              </el-button>
              <el-button type="warning" @click="sendCommand('SLEEP')" :disabled="!terminal">
                休眠设备
              </el-button>
              <el-button type="info" @click="sendCommand('RESTART')" :disabled="!terminal">
                重启设备
              </el-button>
              <el-button type="success" @click="sendCommand('SCREENSHOT')" :disabled="!terminal">
                截图
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import type { Terminal, TerminalGroup } from '@/types'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const terminal = ref<Terminal | null>(null)
const terminalGroups = ref<TerminalGroup[]>([])

const loadTerminal = async () => {
  const terminalId = route.params.id as string
  if (!terminalId) return
  
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await terminalApiService.getTerminal(parseInt(terminalId))
    // terminal.value = response.data
    
    // 模拟数据
    terminal.value = {
      serial_no: parseInt(terminalId),
      device_name: `Terminal-${terminalId}`,
      status: 'ONLINE',
      last_heartbeat: new Date().toISOString(),
      power_status: 1,
      brightness: 80,
      colortemp: 6500,
      volume: 50,
      input_mode: 'HDMI',
      language: 'zh-CN',
      country: 'CN',
      timezone_id: 'Asia/Shanghai',
      is_auto_time: 1,
      gps_report_interval: 60,
      sensor_report_interval: 30,
      content_report_status: 1,
      program: '默认节目',
      program_type: 'VIDEO',
      lat: 39.9042,
      lng: 116.4074
    } as Terminal
  } catch (error) {
    ElMessage.error('加载终端信息失败')
  } finally {
    loading.value = false
  }
}

const loadTerminalGroups = async () => {
  try {
    // TODO: 调用API
    // const response = await terminalGroupApiService.getTerminalGroups()
    // terminalGroups.value = response.data
    
    // 模拟数据
    terminalGroups.value = [
      { id: 1, name: '默认组' },
      { id: 2, name: '测试组' }
    ]
  } catch (error) {
    ElMessage.error('加载终端组失败')
  }
}

const editTerminal = () => {
  router.push(`/terminal/${terminal.value?.serial_no}/edit`)
}

const deleteTerminal = async () => {
  if (!terminal.value) return
  
  try {
    await ElMessageBox.confirm(
      `确定要删除终端 "${terminal.value.device_name}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await terminalApiService.deleteTerminal(terminal.value.serial_no)
    ElMessage.success('删除成功')
    router.push('/terminal')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const sendCommand = async (commandType: string) => {
  if (!terminal.value) return
  
  try {
    // TODO: 调用API
    // await commandApiService.createCommand({
    //   terminal_id: terminal.value.serial_no,
    //   command_type: commandType,
    //   command_method: 'POST'
    // })
    ElMessage.success('命令发送成功')
  } catch (error) {
    ElMessage.error('命令发送失败')
  }
}

const getStatusType = (status: string | undefined) => {
  if (!status) return 'info'
  const statusMap: Record<string, string> = {
    ONLINE: 'success',
    OFFLINE: 'info',
    ERROR: 'danger',
    MAINTENANCE: 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: string | undefined) => {
  if (!status) return '-'
  const statusMap: Record<string, string> = {
    ONLINE: '在线',
    OFFLINE: '离线',
    ERROR: '错误',
    MAINTENANCE: '维护'
  }
  return statusMap[status] || status
}

const getGroupName = (groupId: number | undefined) => {
  if (!groupId) return '-'
  const group = terminalGroups.value.find(g => g.id === groupId)
  return group?.name || '-'
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadTerminal()
  loadTerminalGroups()
})
</script>

<style scoped>
.terminal-detail {
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

.header-actions {
  display: flex;
  gap: 10px;
}

.detail-content {
  margin-top: 20px;
}

.info-card,
.status-card,
.settings-card,
.location-card,
.program-card,
.actions-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.status-item {
  text-align: center;
  padding: 10px;
}

.status-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 5px;
}

.status-value {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.location-info,
.program-info {
  margin-bottom: 15px;
}

.location-item,
.program-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.label {
  color: #606266;
  font-weight: 500;
}

.value {
  color: #303133;
}

.map-placeholder {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.action-buttons .el-button {
  width: 100%;
}
</style> 