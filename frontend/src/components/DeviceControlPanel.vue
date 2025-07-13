<template>
  <div class="device-control-panel">
    <el-card class="control-card">
      <template #header>
        <div class="card-header">
          <span>设备控制面板</span>
          <el-tag :type="connectionStatus ? 'success' : 'danger'">
            {{ connectionStatus ? '已连接' : '未连接' }}
          </el-tag>
        </div>
      </template>

      <!-- 设备信息 -->
      <el-form :model="deviceInfo" label-width="120px" class="device-form">
        <el-form-item label="终端ID">
          <el-input v-model="deviceInfo.post" placeholder="请输入终端ID" type="number" />
        </el-form-item>
        <el-form-item label="设备名称">
          <el-input v-model="deviceInfo.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="指令模式">
          <el-radio-group v-model="commandMode">
            <el-radio label="single">单条指令</el-radio>
            <el-radio label="batch">批量指令</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <!-- 基础控制指令 -->
      <el-divider content-position="left">基础控制指令</el-divider>
      <div class="control-buttons">
        <el-button type="primary" @click="sendCommand('wakeup')" :loading="loading" data-category="basic">
          唤醒指令
        </el-button>
        <el-button type="warning" @click="sendCommand('sleep')" :loading="loading" data-category="basic">
          休眠指令
        </el-button>
        <el-button type="danger" @click="sendCommand('reboot')" :loading="loading" data-category="basic">
          重启指令
        </el-button>
        <el-button @click="sendCommand('screenshot')" :loading="loading" data-category="basic">
          屏幕截屏
        </el-button>
        <el-button @click="sendCommand('clearCache')" :loading="loading" data-category="basic">
          清理缓存
        </el-button>
      </div>

      <!-- 显示控制指令 -->
      <el-divider content-position="left">显示控制指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('brightness')" :loading="loading" data-category="display">
          亮度指令
        </el-button>
        <el-button @click="sendCommand('colortemp')" :loading="loading" data-category="display">
          色温指令
        </el-button>
        <el-button @click="sendCommand('inputmode')" :loading="loading" data-category="display">
          切换信号源
        </el-button>
      </div>

      <!-- 音频控制指令 -->
      <el-divider content-position="left">音频控制指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('volume')" :loading="loading" data-category="audio">
          音量指令
        </el-button>
      </div>

      <!-- 继电器控制指令 -->
      <el-divider content-position="left">继电器控制指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('boardRelay')" :loading="loading" data-category="relay">
          板载继电器
        </el-button>
        <el-button @click="sendCommand('relay')" :loading="loading" data-category="relay">
          继电器指令
        </el-button>
      </div>

      <!-- 系统配置指令 -->
      <el-divider content-position="left">系统配置指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('update')" :loading="loading" data-category="system">
          升级指令
        </el-button>
        <el-button @click="sendCommand('locale')" :loading="loading" data-category="system">
          语言和地区设置
        </el-button>
        <el-button @click="sendCommand('timezone')" :loading="loading" data-category="system">
          时区设置
        </el-button>
      </div>

      <!-- 监控配置指令 -->
      <el-divider content-position="left">监控配置指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('gpsReport')" :loading="loading" data-category="monitor">
          GPS上报间隔
        </el-button>
        <el-button @click="sendCommand('sensorReport')" :loading="loading" data-category="monitor">
          监控上报间隔
        </el-button>
        <el-button @click="sendCommand('contentReport')" :loading="loading" data-category="monitor">
          内容上报开关
        </el-button>
        <el-button @click="sendCommand('logReport')" :loading="loading" data-category="monitor">
          日志上报开关
        </el-button>
        <el-button @click="sendCommand('rotateProgramReport')" :loading="loading" data-category="monitor">
          轮播节目名上报
        </el-button>
      </div>

      <!-- 节目管理指令 -->
      <el-divider content-position="left">节目管理指令</el-divider>
      <div class="control-buttons">
        <el-button type="primary" @click="sendCommand('updateProgram')" :loading="loading" data-category="program">
          更新节目
        </el-button>
        <el-button type="success" @click="sendCommand('switchProgram')" :loading="loading" data-category="program">
          切换节目
        </el-button>
        <el-button type="warning" @click="sendCommand('clearPrograms')" :loading="loading" data-category="program">
          清空节目
        </el-button>
        <el-button type="danger" @click="sendCommand('deleteProgram')" :loading="loading" data-category="program">
          删除节目
        </el-button>
      </div>

      <!-- 状态查询指令 -->
      <el-divider content-position="left">状态查询指令</el-divider>
      <div class="control-buttons">
        <el-button @click="sendCommand('currentLog')" :loading="loading" data-category="status">
          运行日志上报
        </el-button>
        <el-button @click="sendCommand('ifstatus')" :loading="loading" data-category="status">
          网络接口配置
        </el-button>
      </div>

      <!-- 批量指令测试 -->
      <el-divider content-position="left">批量指令测试</el-divider>
      <div class="control-buttons">
        <el-button type="success" @click="sendBatchCommands" :loading="loading" data-category="batch">
          发送批量指令
        </el-button>
        <el-button @click="addToBatch" :loading="loading" data-category="batch">
          添加到批量队列
        </el-button>
        <el-button @click="clearBatch" :loading="loading" data-category="batch">
          清空批量队列
        </el-button>
      </div>

      <!-- 批量指令队列 -->
      <el-card v-if="batchCommands.length > 0" class="batch-queue">
        <template #header>
          <span>批量指令队列 ({{ batchCommands.length }} 条)</span>
        </template>
        <div v-for="(cmd, index) in batchCommands" :key="index" class="batch-item">
          <span>{{ cmd.name }}</span>
          <el-button size="small" type="danger" @click="removeFromBatch(index)">删除</el-button>
        </div>
      </el-card>
    </el-card>

    <!-- 响应结果显示 -->
    <el-card class="response-card" v-if="responseData">
      <template #header>
        <span>指令响应结果</span>
      </template>
      <pre class="response-content">{{ JSON.stringify(responseData, null, 2) }}</pre>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { sendDeviceCommand } from '@/api/deviceApi'

// 响应式数据
const loading = ref(false)
const connectionStatus = ref(false)
const responseData = ref(null)
const commandMode = ref('single')
const batchCommands = ref<Array<{name: string, command: any}>>([])

const deviceInfo = reactive({
  post: 1,
  deviceName: '测试设备'
})

// 发送指令函数
const sendCommand = async (commandType: string) => {
  if (!deviceInfo.post) {
    ElMessage.warning('请先输入终端ID')
    return
  }

  loading.value = true
  try {
    const commandData = generateCommandData(commandType)
    console.log(`发送指令: ${commandType}`, commandData)
    
    const response = await sendDeviceCommand(commandData)
    responseData.value = response
    
    ElMessage.success(`指令 ${commandType} 发送成功`)
  } catch (error: any) {
    console.error('发送指令失败:', error)
    ElMessage.error(`指令发送失败: ${error.message}`)
  } finally {
    loading.value = false
  }
}

// 添加到批量队列
const addToBatch = (commandType: string) => {
  const commandData = generateCommandData(commandType)
  batchCommands.value.push({
    name: getCommandName(commandType),
    command: commandData
  })
  ElMessage.success('已添加到批量队列')
}

// 从批量队列移除
const removeFromBatch = (index: number) => {
  batchCommands.value.splice(index, 1)
}

// 清空批量队列
const clearBatch = () => {
  batchCommands.value = []
  ElMessage.success('批量队列已清空')
}

// 发送批量指令
const sendBatchCommands = async () => {
  if (batchCommands.value.length === 0) {
    ElMessage.warning('批量队列为空')
    return
  }

  loading.value = true
  try {
    const batchData = {
      post: deviceInfo.post,
      CommentData: batchCommands.value.map(item => item.command)
    }
    
    console.log('发送批量指令:', batchData)
    const response = await sendDeviceCommand(batchData)
    responseData.value = response
    
    ElMessage.success(`批量指令发送成功，共 ${batchCommands.value.length} 条`)
    batchCommands.value = []
  } catch (error: any) {
    console.error('发送批量指令失败:', error)
    ElMessage.error(`批量指令发送失败: ${error.message}`)
  } finally {
    loading.value = false
  }
}

// 获取指令名称
const getCommandName = (commandType: string): string => {
  const commandNames: Record<string, string> = {
    wakeup: '唤醒指令',
    sleep: '休眠指令',
    reboot: '重启指令',
    screenshot: '屏幕截屏',
    clearCache: '清理缓存',
    brightness: '亮度指令',
    colortemp: '色温指令',
    inputmode: '切换信号源',
    volume: '音量指令',
    boardRelay: '板载继电器',
    relay: '继电器指令',
    update: '升级指令',
    locale: '语言和地区设置',
    timezone: '时区设置',
    gpsReport: 'GPS上报间隔',
    sensorReport: '监控上报间隔',
    contentReport: '内容上报开关',
    logReport: '日志上报开关',
    rotateProgramReport: '轮播节目名上报',
    updateProgram: '更新节目',
    switchProgram: '切换节目',
    clearPrograms: '清空节目',
    deleteProgram: '删除节目',
    currentLog: '运行日志上报',
    ifstatus: '网络接口配置'
  }
  return commandNames[commandType] || commandType
}

// 根据指令类型生成对应的数据格式
const generateCommandData = (commandType: string) => {
  const baseData = {
    post: deviceInfo.post
  }

  switch (commandType) {
    case 'wakeup':
      return {
        ...baseData,
        metadata: {
          act_url: "api/action",
          act_method: 1
        },
        content: "{\"command\":\"wakeup\"}"
      }

    case 'sleep':
      return {
        ...baseData,
        metadata: {
          act_url: "api/action",
          act_method: 1
        },
        content: "{\"command\":\"sleep\"}"
      }

    case 'reboot':
      return {
        ...baseData,
        metadata: {
          act_url: "api/action",
          act_method: 1
        },
        content: "{\"command\":\"reboot\"}"
      }

    case 'screenshot':
      return {
        ...baseData,
        metadata: {
          act_url: "transmission/ftp/config",
          act_method: 0
        },
        content: "{}"
      }

    case 'clearCache':
      return {
        ...baseData,
        metadata: {
          act_url: "api/clrresunused",
          act_method: 3
        },
        content: "{}"
      }

    case 'brightness':
      return {
        ...baseData,
        metadata: {
          act_url: "api/brightness",
          act_method: 2
        },
        content: "{\"brightness\":80}"
      }

    case 'colortemp':
      return {
        ...baseData,
        metadata: {
          act_url: "api/colortemp",
          act_method: 2
        },
        content: "{\"colortemp\":10000}"
      }

    case 'inputmode':
      return {
        ...baseData,
        metadata: {
          act_url: "api/inputmode",
          act_method: 2
        },
        content: "{\"inputmode\":\"dvi\"}"
      }

    case 'volume':
      return {
        ...baseData,
        metadata: {
          act_url: "api/volume",
          act_method: 2
        },
        content: "{\"musicvolume\":10}"
      }

    case 'boardRelay':
      return {
        ...baseData,
        metadata: {
          act_url: "api/board_relay",
          act_method: 2
        },
        content: "[{\"relay\":1,\"delay\":0,\"status\":1}]"
      }

    case 'relay':
      return {
        ...baseData,
        metadata: {
          act_url: "api/relay",
          act_method: 2
        },
        content: "[{\"relay\":1,\"delay\":0,\"status\":0},{\"relay\":2,\"delay\":0,\"status\":1},{\"relay\":3,\"delay\":0,\"status\":0}]"
      }

    case 'update':
      return {
        ...baseData,
        metadata: {
          act_url: "api/update",
          act_method: 0
        },
        content: "http://ip/wp-content/upload/2020/12/update_c1_v1.67.1.1329_b7c809cdb9bd4a78500d3bd8c27f0de9_480774370.zip"
      }

    case 'locale':
      return {
        ...baseData,
        metadata: {
          act_url: "api/locale",
          act_method: 2
        },
        content: "{\"language\":\"zh\",\"country\":\"CN\"}"
      }

    case 'timezone':
      return {
        ...baseData,
        metadata: {
          act_url: "api/newrtc",
          act_method: 2
        },
        content: "{\"timezoneId\":\"Asia/Shanghai\",\"timezone\":8,\"isautotime\":1}"
      }

    case 'gpsReport':
      return {
        ...baseData,
        metadata: {
          act_url: "api/setreporttime",
          act_method: 1
        },
        content: "{\"gps.report.interval\":30}"
      }

    case 'sensorReport':
      return {
        ...baseData,
        metadata: {
          act_url: "api/setreporttime",
          act_method: 1
        },
        content: "{\"sensor.report.interval\":\"300\",\"ber.report.interval\":\"300\"}"
      }

    case 'contentReport':
      return {
        ...baseData,
        metadata: {
          act_url: "api/contentreport",
          act_method: 2
        },
        content: "{\"status\":1}"
      }

    case 'logReport':
      return {
        ...baseData,
        metadata: {
          act_url: "api/reportswitch",
          act_method: 2
        },
        content: "{\"log_report\":\"on\"}"
      }

    case 'rotateProgramReport':
      return {
        ...baseData,
        metadata: {
          act_url: "api/reportswitch",
          act_method: 2
        },
        content: "{\"rotate_program_vsns_report\":\"on\"}"
      }

    case 'updateProgram':
      return {
        ...baseData,
        metadata: {
          act_url: "",
          act_method: 0
        },
        content: "{\"program\":\"dirty\"}"
      }

    case 'switchProgram':
      return {
        ...baseData,
        metadata: {
          act_url: "api/vsns/sources/internet/vsns/ProgramDocumentImage_7557cda64942545a6843bbe93d07012f_1048.vsn/activated",
          act_method: 2
        },
        content: "{\"command\":\"\"}"
      }

    case 'clearPrograms':
      return {
        ...baseData,
        metadata: {
          act_url: "api/clrprgms",
          act_method: 3
        },
        content: "{}"
      }

    case 'deleteProgram':
      return {
        ...baseData,
        metadata: {
          act_url: "api/vsns/sources/internet/vsns/Playlist9017_783596d9ee396d7a604dac56a6979546_1332.vsn",
          act_method: 3
        },
        content: "{\"command\":\"\"}"
      }

    case 'currentLog':
      return {
        ...baseData,
        metadata: {
          act_url: "api/current_log",
          act_method: 0
        },
        content: "{}"
      }

    case 'ifstatus':
      return {
        ...baseData,
        metadata: {
          act_url: "api/ifstatus",
          act_method: 0
        },
        content: "{}"
      }

    default:
      return {
        ...baseData,
        metadata: {
          act_url: "api/action",
          act_method: 1
        },
        content: "{}"
      }
  }
}
</script>

<style scoped>
.device-control-panel {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
  display: grid;
  gap: 24px;
  grid-template-columns: 1fr;
}

.control-card {
  margin-bottom: 0;
  animation: fadeInUp 0.6s ease-out;
}

.control-card:nth-child(1) { animation-delay: 0.1s; }
.control-card:nth-child(2) { animation-delay: 0.2s; }

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.card-header span {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
  display: flex;
  align-items: center;
  gap: 8px;
}

.card-header span::before {
  content: '';
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 2px;
}

.device-form {
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border-radius: 16px;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.device-form .el-form-item {
  margin-bottom: 20px;
}

.device-form .el-form-item__label {
  font-weight: 600;
  color: #374151;
  font-size: 14px;
  letter-spacing: 0.3px;
}

.device-form .el-input__inner {
  height: 44px;
  font-size: 14px;
  font-weight: 600;
}

.device-form .el-radio-group {
  display: flex;
  gap: 16px;
}

.device-form .el-radio {
  margin-right: 0;
}

.device-form .el-radio__label {
  font-weight: 500;
  color: #374151;
}

.control-buttons {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 12px;
  margin-bottom: 24px;
  padding: 0 8px;
}

.control-buttons .el-button {
  height: 48px;
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 0.3px;
  margin-bottom: 0;
  position: relative;
  overflow: hidden;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.control-buttons .el-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.6s ease;
}

.control-buttons .el-button:hover::before {
  left: 100%;
}

.control-buttons .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.control-buttons .el-button:active {
  transform: translateY(0);
}

/* 指令分类样式 */
.control-buttons .el-button[data-category="basic"] {
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  box-shadow: 0 4px 15px rgba(59, 130, 246, 0.4);
}

.control-buttons .el-button[data-category="basic"]:hover {
  background: linear-gradient(135deg, #2563eb 0%, #1e40af 100%);
  box-shadow: 0 6px 20px rgba(59, 130, 246, 0.6);
}

.control-buttons .el-button[data-category="display"] {
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.4);
}

.control-buttons .el-button[data-category="display"]:hover {
  background: linear-gradient(135deg, #7c3aed 0%, #6d28d9 100%);
  box-shadow: 0 6px 20px rgba(139, 92, 246, 0.6);
}

.control-buttons .el-button[data-category="audio"] {
  background: linear-gradient(135deg, #06b6d4 0%, #0891b2 100%);
  box-shadow: 0 4px 15px rgba(6, 182, 212, 0.4);
}

.control-buttons .el-button[data-category="audio"]:hover {
  background: linear-gradient(135deg, #0891b2 0%, #0e7490 100%);
  box-shadow: 0 6px 20px rgba(6, 182, 212, 0.6);
}

.control-buttons .el-button[data-category="relay"] {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  box-shadow: 0 4px 15px rgba(245, 158, 11, 0.4);
}

.control-buttons .el-button[data-category="relay"]:hover {
  background: linear-gradient(135deg, #e0910a 0%, #c06505 100%);
  box-shadow: 0 6px 20px rgba(245, 158, 11, 0.6);
}

.control-buttons .el-button[data-category="system"] {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  box-shadow: 0 4px 15px rgba(16, 185, 129, 0.4);
}

.control-buttons .el-button[data-category="system"]:hover {
  background: linear-gradient(135deg, #0ea371 0%, #047857 100%);
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.6);
}

.control-buttons .el-button[data-category="monitor"] {
  background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%);
  box-shadow: 0 4px 15px rgba(99, 102, 241, 0.4);
}

.control-buttons .el-button[data-category="monitor"]:hover {
  background: linear-gradient(135deg, #5855eb 0%, #4338ca 100%);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.6);
}

.control-buttons .el-button[data-category="program"] {
  background: linear-gradient(135deg, #ec4899 0%, #db2777 100%);
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.4);
}

.control-buttons .el-button[data-category="program"]:hover {
  background: linear-gradient(135deg, #db2777 0%, #be185d 100%);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.6);
}

.control-buttons .el-button[data-category="status"] {
  background: linear-gradient(135deg, #6b7280 0%, #4b5563 100%);
  box-shadow: 0 4px 15px rgba(107, 114, 128, 0.4);
}

.control-buttons .el-button[data-category="status"]:hover {
  background: linear-gradient(135deg, #5f6368 0%, #3f4751 100%);
  box-shadow: 0 6px 20px rgba(107, 114, 128, 0.6);
}

.control-buttons .el-button[data-category="batch"] {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.control-buttons .el-button[data-category="batch"]:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.batch-queue {
  margin-top: 24px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.batch-queue .el-card__header {
  background: rgba(102, 126, 234, 0.1);
  border-bottom: 1px solid rgba(102, 126, 234, 0.2);
}

.batch-queue .el-card__header span {
  font-weight: 600;
  color: #374151;
  display: flex;
  align-items: center;
  gap: 8px;
}

.batch-queue .el-card__header span::before {
  content: '📋';
  font-size: 16px;
}

.batch-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  margin: 8px 0;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 8px;
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.3s ease;
}

.batch-item:hover {
  background: rgba(255, 255, 255, 0.9);
  transform: translateX(4px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.batch-item span {
  font-weight: 500;
  color: #374151;
}

.batch-item .el-button {
  height: 32px;
  padding: 0 12px;
  font-size: 12px;
}

.response-card {
  margin-top: 24px;
  animation: slideInUp 0.6s ease-out;
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.response-card .el-card__header {
  background: rgba(16, 185, 129, 0.1);
  border-bottom: 1px solid rgba(16, 185, 129, 0.2);
}

.response-card .el-card__header span {
  font-weight: 600;
  color: #374151;
  display: flex;
  align-items: center;
  gap: 8px;
}

.response-card .el-card__header span::before {
  content: '📊';
  font-size: 16px;
}

.response-content {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  padding: 20px;
  border-radius: 12px;
  font-family: 'JetBrains Mono', 'Courier New', monospace;
  font-size: 13px;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-all;
  max-height: 400px;
  overflow-y: auto;
  border: 1px solid rgba(102, 126, 234, 0.1);
  color: #1f2937;
  font-weight: 500;
}

.response-content::-webkit-scrollbar {
  width: 8px;
}

.response-content::-webkit-scrollbar-track {
  background: rgba(102, 126, 234, 0.1);
  border-radius: 4px;
}

.response-content::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 4px;
}

.response-content::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
}

/* 分割线样式 */
.el-divider {
  margin: 32px 0 24px 0;
}

.el-divider__text {
  background: rgba(255, 255, 255, 0.95);
  color: #374151;
  font-weight: 600;
  font-size: 16px;
  padding: 0 24px;
  letter-spacing: 0.5px;
  position: relative;
}

.el-divider__text::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 2px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .device-control-panel {
    padding: 16px;
    gap: 16px;
  }
  
  .control-buttons {
    grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
    gap: 8px;
  }
  
  .control-buttons .el-button {
    height: 44px;
    font-size: 13px;
  }
  
  .device-form {
    padding: 16px;
  }
  
  .footer-content {
    flex-direction: column;
    gap: 8px;
  }
}

@media (max-width: 480px) {
  .control-buttons {
    grid-template-columns: 1fr;
  }
  
  .control-buttons .el-button {
    height: 48px;
    font-size: 14px;
  }
  
  .header-content {
    flex-direction: column;
    gap: 12px;
    text-align: center;
  }
  
  .logo h1 {
    font-size: 24px;
  }
}
</style> 