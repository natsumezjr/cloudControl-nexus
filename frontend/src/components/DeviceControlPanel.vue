<template>
  <div>Device Control Panel</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { sendDeviceCommand } from '../api/deviceApi'

// 响应式数据
const loading = ref(false)
const connectionStatus = ref(false)
const responseData = ref<any>(null)
const commandMode = ref('single')
const batchCommands = ref<Array<{type: string, payload: any}>>([])

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
    const payload = generateCommandPayload(commandType)
    console.log(`发送指令: ${commandType}`, payload)
    const response = await sendDeviceCommand(commandType, payload)
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
  const payload = generateCommandPayload(commandType)
  batchCommands.value.push({
    type: commandType,
    payload
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
    // 依次发送每条批量指令
    for (const cmd of batchCommands.value) {
      await sendDeviceCommand(cmd.type, cmd.payload)
    }
    ElMessage.success(`批量指令发送成功，共 ${batchCommands.value.length} 条`)
    batchCommands.value = []
  } catch (error: any) {
    console.error('发送批量指令失败:', error)
    ElMessage.error(`批量指令发送失败: ${error.message}`)
  } finally {
    loading.value = false
  }
}


// 新格式：根据指令类型生成payload
const generateCommandPayload = (commandType: string) => {
  const terminalIds = [deviceInfo.post]
  switch (commandType) {
    case 'brightnessCommand':
      return { terminalIds, value: '66' }
    case 'upgradeCommand':
      return { terminalIds, value: 'http://ip/wp-content/upload/2020/12/update_c1_v1.67.1.1329_b7c809cdb9bd4a78500d3bd8c27f0de9_480774370.zip' }
    case 'rebootCommand':
    case 'screenshotCommand':
    case 'sleepCommand':
    case 'wakeupCommand':
    case 'clearCacheCommand':
      return { terminalIds }
    case 'boardRelayCommand':
      return { terminalIds, value: '1' }
    case 'relayCommand':
      return { terminalIds, value: '[0,1,0]' }
    case 'colortempCommand':
      return { terminalIds, value: '10000' }
    case 'switchSignalSourceCommand':
      return { terminalIds, value: '1' }
    case 'volumeCommand':
      return { terminalIds, value: '10' }
    // 其余case也返回新格式，哪怕只是 { terminalIds }
    default:
      return { terminalIds }
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