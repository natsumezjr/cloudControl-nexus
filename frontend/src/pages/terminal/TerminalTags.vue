<template>
  <div class="terminal-tags">
    <div class="page-header">
      <h2>终端标签管理</h2>
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon><Plus /></el-icon>
        添加标签
      </el-button>
    </div>

    <el-card>
      <el-table
        v-loading="loading"
        :data="terminalTags"
        style="width: 100%"
      >
        <el-table-column prop="tag_id" label="ID" width="80" />
        <el-table-column prop="tag_name" label="标签名称" min-width="150" />
        <el-table-column prop="created_at" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.created_at) }}
          </template>
        </el-table-column>
        <el-table-column label="使用次数" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTagUsageCount(row.tag_id) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="viewTag(row)">查看</el-button>
            <el-button size="small" type="primary" @click="editTag(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteTag(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 创建标签对话框 -->
    <el-dialog v-model="showCreateDialog" title="添加标签" width="500px">
      <el-form ref="createFormRef" :model="createForm" :rules="createRules" label-width="100px">
        <el-form-item label="标签名称" prop="tag_name">
          <el-input v-model="createForm.tag_name" placeholder="请输入标签名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createTag" :loading="creating">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { TerminalTag } from '@/types'

const router = useRouter()

// 数据状态
const loading = ref(false)
const creating = ref(false)
const terminalTags = ref<TerminalTag[]>([])

// 创建表单
const showCreateDialog = ref(false)
const createFormRef = ref()
const createForm = reactive({
  tag_name: ''
})

const createRules = {
  tag_name: [
    { required: true, message: '请输入标签名称', trigger: 'blur' }
  ]
}

// 方法
const loadTerminalTags = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await terminalTagApiService.getTerminalTags()
    // terminalTags.value = response.data
    
    // 模拟数据
    terminalTags.value = [
      {
        tag_id: 1,
        tag_name: '重要设备',
        created_at: new Date().toISOString()
      },
      {
        tag_id: 2,
        tag_name: '测试设备',
        created_at: new Date().toISOString()
      },
      {
        tag_id: 3,
        tag_name: '生产环境',
        created_at: new Date().toISOString()
      }
    ]
  } catch (error) {
    ElMessage.error('加载标签失败')
  } finally {
    loading.value = false
  }
}

const createTag = async () => {
  if (!createFormRef.value) return
  
  try {
    await createFormRef.value.validate()
    creating.value = true
    
    // TODO: 调用API
    // await terminalTagApiService.createTerminalTag(createForm)
    ElMessage.success('创建成功')
    showCreateDialog.value = false
    loadTerminalTags()
    
    // 重置表单
    createForm.tag_name = ''
  } catch (error) {
    ElMessage.error('创建失败')
  } finally {
    creating.value = false
  }
}

const viewTag = (tag: TerminalTag) => {
  router.push(`/terminal/tags/${tag.tag_id}`)
}

const editTag = (tag: TerminalTag) => {
  router.push(`/terminal/tags/${tag.tag_id}/edit`)
}

const deleteTag = async (tag: TerminalTag) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除标签 "${tag.tag_name}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await terminalTagApiService.deleteTerminalTag(tag.tag_id)
    ElMessage.success('删除成功')
    loadTerminalTags()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const getTagUsageCount = (tagId: number) => {
  // TODO: 获取该标签的使用次数
  return Math.floor(Math.random() * 20)
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadTerminalTags()
})
</script>

<style scoped>
.terminal-tags {
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