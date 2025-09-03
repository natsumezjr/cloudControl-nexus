<template>
  <div class="terminal-groups">
    <div class="page-header">
      <h2>终端组管理</h2>
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon><Plus /></el-icon>
        添加终端组
      </el-button>
    </div>

    <el-card>
      <el-table
        v-loading="loading"
        :data="terminalGroups"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="组名称" min-width="150" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column prop="created_at" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.created_at) }}
          </template>
        </el-table-column>
        <el-table-column label="终端数量" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTerminalCount(row.id) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="viewGroup(row)">查看</el-button>
            <el-button size="small" type="primary" @click="editGroup(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteGroup(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 创建终端组对话框 -->
    <el-dialog v-model="showCreateDialog" title="添加终端组" width="500px">
      <el-form ref="createFormRef" :model="createForm" :rules="createRules" label-width="100px">
        <el-form-item label="组名称" prop="name">
          <el-input v-model="createForm.name" placeholder="请输入组名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="createForm.description"
            type="textarea"
            placeholder="请输入描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createGroup" :loading="creating">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { TerminalGroup } from '@/types'

const router = useRouter()

// 数据状态
const loading = ref(false)
const creating = ref(false)
const terminalGroups = ref<TerminalGroup[]>([])

// 创建表单
const showCreateDialog = ref(false)
const createFormRef = ref()
const createForm = reactive({
  name: '',
  description: ''
})

const createRules = {
  name: [
    { required: true, message: '请输入组名称', trigger: 'blur' }
  ]
}

// 方法
const loadTerminalGroups = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await terminalGroupApiService.getTerminalGroups()
    // terminalGroups.value = response.data
    
    // 模拟数据
    terminalGroups.value = [
      {
        id: 1,
        name: '默认组',
        description: '系统默认终端组',
        created_at: new Date().toISOString()
      },
      {
        id: 2,
        name: '测试组',
        description: '用于测试的终端组',
        created_at: new Date().toISOString()
      }
    ]
  } catch (error) {
    ElMessage.error('加载终端组失败')
  } finally {
    loading.value = false
  }
}

const createGroup = async () => {
  if (!createFormRef.value) return
  
  try {
    await createFormRef.value.validate()
    creating.value = true
    
    // TODO: 调用API
    // await terminalGroupApiService.createTerminalGroup(createForm)
    ElMessage.success('创建成功')
    showCreateDialog.value = false
    loadTerminalGroups()
    
    // 重置表单
    createForm.name = ''
    createForm.description = ''
  } catch (error) {
    ElMessage.error('创建失败')
  } finally {
    creating.value = false
  }
}

const viewGroup = (group: TerminalGroup) => {
  router.push(`/terminal/groups/${group.id}`)
}

const editGroup = (group: TerminalGroup) => {
  router.push(`/terminal/groups/${group.id}/edit`)
}

const deleteGroup = async (group: TerminalGroup) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除终端组 "${group.name}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await terminalGroupApiService.deleteTerminalGroup(group.id)
    ElMessage.success('删除成功')
    loadTerminalGroups()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const getTerminalCount = (groupId: number) => {
  // TODO: 获取该组的终端数量
  return Math.floor(Math.random() * 10)
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadTerminalGroups()
})
</script>

<style scoped>
.terminal-groups {
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
</style> 