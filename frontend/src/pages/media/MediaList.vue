<template>
  <div>Media List Page</div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import type { Media } from '@/types'

const router = useRouter()

// 数据状态
const loading = ref(false)
const mediaList = ref<Media[]>([])
const selectedMedia = ref<Media[]>([])

// 分页
const pagination = reactive({
  page: 1,
  size: 20,
  total: 0
})

// 筛选表单
const filterForm = reactive({
  title: '',
  media_type: ''
})

// 方法
const loadMedia = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await mediaApiService.getMedia({
    //   page: pagination.page - 1,
    //   size: pagination.size,
    //   keyword: filterForm.title,
    //   filters: {
    //     media_type: filterForm.media_type
    //   }
    // })
    // mediaList.value = response.data.content
    // pagination.total = response.data.total_elements
    
    // 模拟数据
    mediaList.value = [
      {
        id: 1,
        title_raw: '示例图片.jpg',
        media_type: 'IMAGE',
        attachment_filesize: 1024000,
        width: 1920,
        height: 1080,
        created_at: new Date().toISOString()
      },
      {
        id: 2,
        title_raw: '示例视频.mp4',
        media_type: 'VIDEO',
        attachment_filesize: 52428800,
        width: 1920,
        height: 1080,
        duration: 120,
        created_at: new Date().toISOString()
      }
    ] as Media[]
    pagination.total = 2
  } catch (error) {
    ElMessage.error('加载媒体列表失败')
  } finally {
    loading.value = false
  }
}

const resetFilter = () => {
  filterForm.title = ''
  filterForm.media_type = ''
  loadMedia()
}

const handleSelectionChange = (selection: Media[]) => {
  selectedMedia.value = selection
}

const handleSizeChange = (size: number) => {
  pagination.size = size
  pagination.page = 1
  loadMedia()
}

const handleCurrentChange = (page: number) => {
  pagination.page = page
  loadMedia()
}

const viewMedia = (media: Media) => {
  router.push(`/media/${media.id}`)
}

const editMedia = (media: Media) => {
  router.push(`/media/${media.id}/edit`)
}

const downloadMedia = async (media: Media) => {
  try {
    // TODO: 调用API
    // const response = await mediaApiService.downloadMedia(media.id)
    // 创建下载链接
    const link = document.createElement('a')
    link.href = media.src || '#'
    link.download = media.title_raw || 'download'
    link.click()
    ElMessage.success('下载成功')
  } catch (error) {
    ElMessage.error('下载失败')
  }
}

const deleteMedia = async (media: Media) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除媒体 "${media.title_raw}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await mediaApiService.deleteMedia(media.id)
    ElMessage.success('删除成功')
    loadMedia()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const batchDownload = async () => {
  try {
    // TODO: 调用API
    // await mediaApiService.batchDownload(selectedMedia.value.map(m => m.id))
    ElMessage.success('批量下载成功')
  } catch (error) {
    ElMessage.error('批量下载失败')
  }
}

const batchDelete = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要批量删除选中的 ${selectedMedia.value.length} 个媒体文件吗？`,
      '确认批量删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await mediaApiService.batchDelete(selectedMedia.value.map(m => m.id))
    ElMessage.success('批量删除成功')
    loadMedia()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败')
    }
  }
}

const getMediaTypeTag = (type: string | undefined) => {
  if (!type) return 'info'
  const typeMap: Record<string, string> = {
    IMAGE: 'success',
    VIDEO: 'warning',
    AUDIO: 'info',
    DOCUMENT: 'danger'
  }
  return typeMap[type] || 'info'
}

const getMediaTypeText = (type: string | undefined) => {
  if (!type) return '-'
  const typeMap: Record<string, string> = {
    IMAGE: '图片',
    VIDEO: '视频',
    AUDIO: '音频',
    DOCUMENT: '文档'
  }
  return typeMap[type] || type
}

const formatFileSize = (size: number | undefined) => {
  if (!size) return '-'
  const units = ['B', 'KB', 'MB', 'GB']
  let unitIndex = 0
  let fileSize = size
  
  while (fileSize >= 1024 && unitIndex < units.length - 1) {
    fileSize /= 1024
    unitIndex++
  }
  
  return `${fileSize.toFixed(1)} ${units[unitIndex]}`
}

const formatDuration = (duration: number | undefined) => {
  if (!duration) return '-'
  const minutes = Math.floor(duration / 60)
  const seconds = duration % 60
  return `${minutes}:${seconds.toString().padStart(2, '0')}`
}

const formatTime = (time: string | undefined) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

onMounted(() => {
  loadMedia()
})
</script>

<style scoped>
.media-list {
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

.no-thumbnail {
  width: 60px;
  height: 60px;
  background: #f5f7fa;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  font-size: 12px;
}
</style> 