<template>
  <div class="program-edit">
    <div class="page-header">
      <el-button @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2>编辑节目</h2>
    </div>

    <el-card v-loading="loading">
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        class="program-form"
      >
        <el-form-item label="节目名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入节目名称" />
        </el-form-item>
        
        <el-form-item label="节目信息" prop="program_info">
          <el-input
            v-model="form.program_info"
            type="textarea"
            :rows="4"
            placeholder="请输入节目描述信息"
          />
        </el-form-item>
        
        <el-form-item label="节目数据" prop="programs_data">
          <el-input
            v-model="form.programs_data"
            type="textarea"
            :rows="8"
            placeholder="请输入节目数据（JSON格式）"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="submitForm" :loading="submitting">
            保存修改
          </el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import type { ProgramUpdateRequest } from '@/types'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const submitting = ref(false)
const formRef = ref()

const form = reactive<ProgramUpdateRequest>({
  title: '',
  program_info: '',
  programs_data: ''
})

const rules = {
  title: [
    { required: true, message: '请输入节目名称', trigger: 'blur' }
  ]
}

const loadProgram = async () => {
  const programId = route.params.id as string
  if (!programId) return
  
  loading.value = true
  try {
    // TODO: 调用API
    // const response = await programApiService.getProgram(parseInt(programId))
    // const program = response.data
    // form.title = program.title
    // form.program_info = program.program_info || ''
    // form.programs_data = program.programs_data || ''
    
    // 模拟数据
    form.title = `节目-${programId}`
    form.program_info = '这是一个示例节目的描述信息'
    form.programs_data = JSON.stringify({
      type: 'video',
      duration: 30,
      url: 'https://example.com/video.mp4'
    }, null, 2)
  } catch (error) {
    ElMessage.error('加载节目信息失败')
  } finally {
    loading.value = false
  }
}

const submitForm = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    submitting.value = true
    
    const programId = route.params.id as string
    // TODO: 调用API
    // await programApiService.updateProgram(parseInt(programId), form)
    ElMessage.success('保存成功')
    router.push(`/program/${programId}`)
  } catch (error) {
    ElMessage.error('保存失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadProgram()
})
</script>

<style scoped>
.program-edit {
  padding: 20px;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.program-form {
  max-width: 800px;
}
</style> 