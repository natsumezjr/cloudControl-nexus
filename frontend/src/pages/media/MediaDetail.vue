<template>
  <div>Media Detail Page</div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowLeft, Document } from '@element-plus/icons-vue'
import type { Media } from '@/types'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const media = ref<Media | null>(null)

const loadMedia = async () => {
  const mediaId = route.params.id as string
  if (!mediaId) return
  
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await mediaApiService.getMedia(parseInt(mediaId))
    // media.value = response.data
    
    // 模拟数据
    media.value = {
      id: parseInt(mediaId),
      title_raw: `媒体-${mediaId}.jpg`,
      description: '这是一个示例媒体文件的描述信息',
      file_type: 'jpg',
      mime_type: 'image/jpeg',
      media_type: 'IMAGE',
      attachment_filesize: 1024000,
      file_path: '/uploads/media/example.jpg',
      thumbnail_path: '/uploads/thumbnails/example.jpg',
      width: 1920,
      height: 1080,
      author: 1,
      guid: 'example-guid',
      slug: `media-${mediaId}`,
      source_url: 'https://example.com/media.jpg',
      src: 'https://example.com/media.jpg',
      post_status: 'inherit',
      comment_status: 'open',
      ping_status: 'open',
      parent: 0,
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString()
    } as Media
  } catch (error) {
    ElMessage.error('加载媒体信息失败')
  } finally {
    loading.value = false
  }
}

const editMedia = () => {
  router.push(`/media/${media.value?.id}/edit`)
}

const downloadMedia = async () => {
  if (!media.value) return
  
  try {
    // TODO: 调用API
    // const response = await mediaApiService.downloadMedia(media.value.id)
    // 创建下载链接
    const link = document.createElement('a')
    link.href = media.value.src || '#'
    link.download = media.value.title_raw || 'download'
    link.click()
    ElMessage.success('下载成功')
  } catch (error) {
    ElMessage.error('下载失败')
  }
}

const deleteMedia = async () => {
  if (!media.value) return
  
  try {
    await ElMessageBox.confirm(
      `确定要删除媒体 "${media.value.title_raw}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // TODO: 调用API
    // await mediaApiService.deleteMedia(media.value.id)
    ElMessage.success('删除成功')
    router.push('/media')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const copyUrl = async () => {
  if (!media.value?.src) return
  
  try {
    await navigator.clipboard.writeText(media.value.src)
    ElMessage.success('链接已复制到剪贴板')
  } catch (error) {
    ElMessage.error('复制失败')
  }
}

const generateThumbnail = async () => {
  if (!media.value) return
  
  try {
    // TODO: 调用API
    // await mediaApiService.generateThumbnail(media.value.id)
    ElMessage.success('缩略图生成成功')
    loadMedia() // 重新加载数据
  } catch (error) {
    ElMessage.error('缩略图生成失败')
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

const getPostStatusType = (status: string | undefined) => {
  if (!status) return 'info'
  const statusMap: Record<string, string> = {
    inherit: 'success',
    private: 'warning',
    trash: 'danger'
  }
  return statusMap[status] || 'info'
}

const getPostStatusText = (status: string | undefined) => {
  if (!status) return '-'
  const statusMap: Record<string, string> = {
    inherit: '继承',
    private: '私有',
    trash: '回收站'
  }
  return statusMap[status] || status
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
.media-detail {
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

.preview-card,
.info-card,
.description-card,
.actions-card,
.file-info-card {
  margin-bottom: 20px;
}

.media-preview {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 300px;
}

.file-preview {
  text-align: center;
  color: #909399;
}

.file-preview p {
  margin-top: 10px;
  font-size: 14px;
}

.media-description {
  line-height: 1.6;
  color: #606266;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.action-buttons .el-button {
  width: 100%;
}

.file-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item:last-child {
  border-bottom: none;
}

.label {
  color: #606266;
  font-weight: 500;
  min-width: 80px;
}

.value {
  color: #303133;
  text-align: right;
  word-break: break-all;
}
</style> 