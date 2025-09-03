<template>
  <div class="command-send">
    <div class="page-header">
      <h2>发送命令</h2>
    </div>

    <el-card>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        class="command-form"
      >
        <el-form-item label="选择终端" prop="terminal_id">
          <el-select v-model="form.terminal_id" placeholder="选择终端" clearable>
            <el-option
              v-for="terminal in terminals"
              :key="terminal.serial_no"
              :label="terminal.device_name"
              :value="terminal.serial_no"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="命令类型" prop="command_type">
          <el-select v-model="form.command_type" placeholder="选择命令类型">
            <el-option label="唤醒设备" value="WAKEUP" />
            <el-option label="休眠设备" value="SLEEP" />
            <el-option label="重启设备" value="RESTART" />
            <el-option label="截图" value="SCREENSHOT" />
            <el-option label="清除缓存" value="CLEAR_CACHE" />
            <el-option label="亮度调节" value="BRIGHTNESS" />
            <el-option label="色温调节" value="COLORTEMP" />
            <el-option label="音量调节" value="VOLUME" />
            <el-option label="输入模式" value="INPUT_MODE" />
            <el-option label="继电器控制" value="RELAY_CONTROL" />
            <el-option label="系统升级" value="SYSTEM_UPGRADE" />
            <el-option label="GPS报告" value="GPS_REPORT" />
            <el-option label="传感器报告" value="SENSOR_REPORT" />
            <el-option label="节目更新" value="PROGRAM_UPDATE" />
            <el-option label="节目切换" value="PROGRAM_SWITCH" />
            <el-option label="节目清除" value="PROGRAM_CLEAR" />
            <el-option label="节目删除" value="PROGRAM_DELETE" />
            <el-option label="日志报告" value="LOG_REPORT" />
            <el-option label="网络信息" value="NETWORK_INFO" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="命令方法" prop="command_method">
          <el-radio-group v-model="form.command_method">
            <el-radio label="GET">GET</el-radio>
            <el-radio label="POST">POST</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="命令参数" prop="payload">
          <el-input
            v-model="form.payload"
            type="textarea"
            :rows="4"
            placeholder="请输入命令参数（JSON格式）"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="sendCommand" :loading="sending">
            发送命令
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import type { CommandCreateRequest, Terminal } from '@/types'
import { CommandType, CommandMethod } from '@/types'

const sending = ref(false)
const formRef = ref()
const terminals = ref<Terminal[]>([])

const form = reactive<CommandCreateRequest>({
  terminal_id: 0,
  command_type: CommandType.WAKEUP,
  command_method: CommandMethod.POST,
  payload: ''
})

const rules = {
  terminal_id: [
    { required: true, message: '请选择终端', trigger: 'change' }
  ],
  command_type: [
    { required: true, message: '请选择命令类型', trigger: 'change' }
  ],
  command_method: [
    { required: true, message: '请选择命令方法', trigger: 'change' }
  ]
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
      },
      {
        serial_no: 2,
        device_name: 'Terminal-002',
        status: 'ONLINE'
      }
    ] as Terminal[]
  } catch (error) {
    ElMessage.error('加载终端列表失败')
  }
}

const sendCommand = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    sending.value = true
    
    // TODO: 调用API
    // await commandApiService.createCommand(form)
    ElMessage.success('命令发送成功')
    resetForm()
  } catch (error) {
    ElMessage.error('命令发送失败')
  } finally {
    sending.value = false
  }
}

const resetForm = () => {
  form.terminal_id = 0
  form.command_type = CommandType.WAKEUP
  form.command_method = CommandMethod.POST
  form.payload = ''
}

onMounted(() => {
  loadTerminals()
})
</script>

<style scoped>
.command-send {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.command-form {
  max-width: 600px;
}
</style> 