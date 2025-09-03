<template>
  <div class="program-list">
    <div class="page-header">
      <h2>节目管理</h2>
      <el-button type="primary" @click="$router.push('/program/create')">
        <el-icon><Plus /></el-icon>
        创建节目
      </el-button>
    </div>

    <el-card class="filter-card">
      <el-form :model="filterForm" inline>
        <el-form-item label="节目名称">
          <el-input v-model="filterForm.title" placeholder="搜索节目名称" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filterForm.status" placeholder="选择状态" clearable>
            <el-option label="草稿" value="DRAFT" />
            <el-option label="已发布" value="PUBLISHED" />
            <el-option label="已归档" value="ARCHIVED" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadPrograms">搜索</el-button>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <el-table
        v-loading="loading"
        :data="programs"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="节目名称" min-width="200" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="created_at" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.created_at) }}
          </template>
        </el-table-column>
        <el-table-column prop="updated_at" label="更新时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.updated_at) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="viewProgram(row)">查看</el-button>
            <el-button size="small" type="primary" @click="editProgram(row)">编辑</el-button>
            <el-button 
              size="small" 
              type="success" 
              @click="publishProgram(row)"
              v-if="row.status === 'DRAFT'"
            >
              发布
            </el-button>
            <el-button 
              size="small" 
              type="warning" 
              @click="unpublishProgram(row)"
              v-if="row.status === 'PUBLISHED'"
            >
              取消发布
            </el-button>
            <el-button size="small" type="danger" @click="deleteProgram(row)">删除</el-button>
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

    <!-- 批量操作 -->
    <div class="batch-actions" v-if="selectedPrograms.length > 0">
      <el-card>
        <div class="batch-header">
          <span>已选择 {{ selectedPrograms.length }} 个节目</span>
          <div class="batch-buttons">
            <el-button type="success" @click="batchPublish">批量发布</el-button>
            <el-button type="warning" @click="batchUnpublish">批量取消发布</el-button>
            <el-button type="danger" @click="batchDelete">批量删除</el-button>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { Program } from '@/types'

const router = useRouter()

// 数据状态
const loading = ref(false)
const programs = ref<Program[]>([])
const selectedPrograms = ref<Program[]>([])

// 分页
const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

// 筛选表单
const filterForm = reactive({
  title: '',
  status: ''
})

// 方法
const loadPrograms = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await programApiService.getPrograms({
    //   page: pagination.page - 1,
    //   size: pagination.size,
    //   keyword: filterForm.title,
    //   filters: {
    //     status: filterForm.status
    //   }
    // })
    // programs.value = response.data.content
    // pagination.total = response.data.total_elements
    
    // 模拟数据
    programs.value = [
      {
        id: 1,
        title: '欢迎节目',
        status: 'PUBLISHED',
        author: 1,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
      },
      {
        id: 2,
        title: '测试节目',
        status: 'DRAFT',
        author: 1,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
      }
    ] as Program[]
    pagination.total = 2
  } catch (error) {
    ElMessage.error('加载节目列表失败')
  } finally {
    loading.value = false
  }
}

const resetFilter = () => {
  filterForm.title = ''
  filterForm.status = ''
  loadPrograms()
}

const handleSelectionChange = (selection: Program[]) => {
  selectedPrograms.value = selection
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  pagination.page = 1
  loadPrograms()
}

const handleCurrentChange = (page: number) => {
  pagination.page = page
  loadPrograms()
}

const viewProgram = (program: Program) => {
  router.push(`/program/${program.id}`)
}

const editProgram = (program: Program) => {
  router.push(`/program/${program.id}/edit`)
}

const publishProgram = async (program: Program) => {
  try {
    await ElMessageBox.confirm(
      `确定要发布节目 "${program.title}" 吗？`,
      '确认发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )
    
    // TODO: 调用API
    // await programApiService.publishProgram(program.id)
    ElMessage.success('发布成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('发布失败')
    }
  }
}

const unpublishProgram = async (program: Program) => {
  try {
    await ElMessageBox.confirm(
      `确定要取消发布节目 "${program.title}" 吗？`,
      '确认取消发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.unpublishProgram(program.id)
    ElMessage.success('取消发布成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消发布失败')
    }
  }
}

const deleteProgram = async (program: Program) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除节目 "${program.title}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.deleteProgram(program.id)
    ElMessage.success('删除成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const batchPublish = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要批量发布选中的 ${selectedPrograms.value.length} 个节目吗？`,
      '确认批量发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )
    
    // TODO: 调用API
    // await programApiService.batchPublish(selectedPrograms.value.map(p => p.id))
    ElMessage.success('批量发布成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量发布失败')
    }
  }
}

const batchUnpublish = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要批量取消发布选中的 ${selectedPrograms.value.length} 个节目吗？`,
      '确认批量取消发布',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.batchUnpublish(selectedPrograms.value.map(p => p.id))
    ElMessage.success('批量取消发布成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量取消发布失败')
    }
  }
}

const batchDelete = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要批量删除选中的 ${selectedPrograms.value.length} 个节目吗？`,
      '确认批量删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await programApiService.batchDelete(selectedPrograms.value.map(p => p.id))
    ElMessage.success('批量删除成功')
    loadPrograms()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败')
    }
  }
}

const getStatusType = (status: string) => {
  const statusMap: Record<string, string> = {
    DRAFT: 'info',
    PUBLISHED: 'success',
    ARCHIVED: 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: string) => {
  const statusMap: Record<string, string> = {
    DRAFT: '草稿',
    PUBLISHED: '已发布',
    ARCHIVED: '已归档'
  }
  return statusMap[status] || status
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadPrograms()
})
</script>

<style scoped>
.program-list {
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

.batch-actions {
  margin-top: 20px;
}

.batch-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.batch-buttons {
  display: flex;
  gap: 10px;
}
</style> 