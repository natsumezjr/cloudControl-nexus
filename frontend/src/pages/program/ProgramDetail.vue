<template>
  <div class="program-detail">
    <div class="page-header">
      <el-button @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2>节目详情</h2>
      <div class="header-actions">
        <el-button type="primary" @click="editProgram">编辑</el-button>
        <el-button type="danger" @click="deleteProgram">删除</el-button>
      </div>
    </div>

    <div v-loading="loading" class="detail-content">
      <el-row :gutter="20">
        <el-col :span="16">
          <el-card class="info-card">
            <template #header>
              <div class="card-header">
                <span>基本信息</span>
                <el-tag :type="getStatusType(program?.status)">
                  {{ getStatusText(program?.status) }}
                </el-tag>
              </div>
            </template>
            
            <el-descriptions :column="2" border>
              <el-descriptions-item label="ID">
                {{ program?.id }}
              </el-descriptions-item>
              <el-descriptions-item label="节目名称">
                {{ program?.title }}
              </el-descriptions-item>
              <el-descriptions-item label="作者">
                {{ program?.author }}
              </el-descriptions-item>
              <el-descriptions-item label="创建时间">
                {{ formatTime(program?.created_at) }}
              </el-descriptions-item>
              <el-descriptions-item label="更新时间">
                {{ formatTime(program?.updated_at) }}
              </el-descriptions-item>
              <el-descriptions-item label="Slug">
                {{ program?.slug || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="版本名称">
                {{ program?.vsn_name || '-' }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          <el-card class="content-card">
            <template #header>
              <span>节目信息</span>
            </template>
            
            <div class="program-info">
              {{ program?.program_info || '暂无描述' }}
            </div>
          </el-card>

          <el-card class="data-card">
            <template #header>
              <span>节目数据</span>
            </template>
            
            <pre class="program-data">{{ program?.programs_data || '暂无数据' }}</pre>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card class="actions-card">
            <template #header>
              <span>操作</span>
            </template>
            
            <div class="action-buttons">
              <el-button 
                type="success" 
                @click="publishProgram"
                v-if="program?.status === 'DRAFT'"
                :disabled="!program"
              >
                发布节目
              </el-button>
              <el-button 
                type="warning" 
                @click="unpublishProgram"
                v-if="program?.status === 'PUBLISHED'"
                :disabled="!program"
              >
                取消发布
              </el-button>
              <el-button 
                type="info" 
                @click="copyProgram"
                :disabled="!program"
              >
                复制节目
              </el-button>
            </div>
          </el-card>

          <el-card class="terminals-card">
            <template #header>
              <span>关联终端</span>
            </template>
            
            <div class="terminals-list">
              <div v-if="terminals.length === 0" class="empty-terminals">
                <el-empty description="暂无关联终端" />
              </div>
              <div v-else class="terminal-items">
                <div 
                  v-for="terminal in terminals" 
                  :key="terminal.terminal_id"
                  class="terminal-item"
                >
                  <span class="terminal-name">{{ terminal.terminal_id }}</span>
                  <el-tag :type="getPublishStatusType(terminal.publish_status)">
                    {{ getPublishStatusText(terminal.publish_status) }}
                  </el-tag>
                </div>
              </div>
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
import type { Program, ProgramTerminalRelation } from '@/types'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const program = ref<Program | null>(null)
const terminals = ref<ProgramTerminalRelation[]>([])

const loadProgram = async () => {
  const programId = route.params.id as string
  if (!programId) return
  
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await programApiService.getProgram(parseInt(programId))
    // program.value = response.data
    
    // 模拟数据
    program.value = {
      id: parseInt(programId),
      title: `节目-${programId}`,
      status: 'PUBLISHED',
      program_info: '这是一个示例节目的描述信息',
      programs_data: JSON.stringify({
        type: 'video',
        duration: 30,
        url: 'https://example.com/video.mp4'
      }, null, 2),
      author: 1,
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString(),
      slug: `program-${programId}`,
      vsn_name: 'v1.0.0'
    } as Program
  } catch (error) {
    ElMessage.error('加载节目信息失败')
  } finally {
    loading.value = false
  }
}

const loadTerminals = async () => {
  const programId = route.params.id as string
  if (!programId) return
  
  try {
    // TODO: 调用API
    // const response = await programTerminalApiService.getProgramTerminals(parseInt(programId))
    // terminals.value = response.data.content
    
    // 模拟数据
    terminals.value = [
      {
        relation_id: 1,
        program_id: parseInt(programId),
        terminal_id: 1,
        publish_status: 'published',
        publish_time: new Date().toISOString()
      }
    ]
  } catch (error) {
    ElMessage.error('加载关联终端失败')
  }
}

const editProgram = () => {
  router.push(`/program/${program.value?.id}/edit`)
}

const deleteProgram = async () => {
  if (!program.value) return
  
  try {
    await ElMessageBox.confirm(
      `确定要删除节目 "${program.value.title}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.deleteProgram(program.value.id)
    ElMessage.success('删除成功')
    router.push('/program')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const publishProgram = async () => {
  if (!program.value) return
  
  try {
    await ElMessageBox.confirm(
      `确定要发布节目 "${program.value.title}" 吗？`,
      '确认发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )
    
    // TODO: 调用API
    // await programApiService.publishProgram(program.value.id)
    ElMessage.success('发布成功')
    loadProgram()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('发布失败')
    }
  }
}

const unpublishProgram = async () => {
  if (!program.value) return
  
  try {
    await ElMessageBox.confirm(
      `确定要取消发布节目 "${program.value.title}" 吗？`,
      '确认取消发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.unpublishProgram(program.value.id)
    ElMessage.success('取消发布成功')
    loadProgram()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消发布失败')
    }
  }
}

const copyProgram = async () => {
  if (!program.value) return
  
  try {
    // TODO: 调用API
    // await programApiService.copyProgram(program.value.id)
    ElMessage.success('复制成功')
    router.push('/program')
  } catch (error) {
    ElMessage.error('复制失败')
  }
}

const getStatusType = (status: string | undefined) => {
  if (!status) return 'info'
  const statusMap: Record<string, string> = {
    DRAFT: 'info',
    PUBLISHED: 'success',
    ARCHIVED: 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: string | undefined) => {
  if (!status) return '-'
  const statusMap: Record<string, string> = {
    DRAFT: '草稿',
    PUBLISHED: '已发布',
    ARCHIVED: '已归档'
  }
  return statusMap[status] || status
}

const getPublishStatusType = (status: string) => {
  const statusMap: Record<string, string> = {
    published: 'success',
    unpublished: 'info',
    pending: 'warning'
  }
  return statusMap[status] || 'info'
}

const getPublishStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    published: '已发布',
    unpublished: '未发布',
    pending: '待发布'
  }
  return statusMap[status] || status
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadProgram()
  loadTerminals()
})
</script>

<style scoped>
.program-detail {
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
.content-card,
.data-card,
.actions-card,
.terminals-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.program-info {
  line-height: 1.6;
  color: #606266;
}

.program-data {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  font-family: 'JetBrains Mono', monospace;
  font-size: 14px;
  line-height: 1.5;
  overflow-x: auto;
  white-space: pre-wrap;
  word-break: break-all;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.action-buttons .el-button {
  width: 100%;
}

.terminals-list {
  min-height: 200px;
}

.empty-terminals {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
}

.terminal-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.terminal-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.terminal-name {
  font-weight: 500;
  color: #303133;
}
</style> 