<template>
  <div class="program-create">
    <div class="page-header">
      <el-button @click="$router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2>创建节目</h2>
    </div>

    <el-card>
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
            创建节目
          </el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import type { ProgramCreateRequest } from '@/types'

const router = useRouter()

const submitting = ref(false)
const formRef = ref()

const form = reactive<ProgramCreateRequest>({
  title: '',
  program_info: '',
  programs_data: ''
})

const rules = {
  title: [
    { required: true, message: '请输入节目名称', trigger: 'blur' }
  ]
}

const submitForm = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    submitting.value = true
    
    // TODO: 调用API
    // await programApiService.createProgram(form)
    ElMessage.success('创建成功')
    router.push('/program')
  } catch (error) {
    ElMessage.error('创建失败')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.program-create {
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