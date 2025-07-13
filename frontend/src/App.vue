<template>
  <div id="app">
    <el-container>
      <el-header class="app-header">
        <div class="header-content">
          <div class="header-left">
            <div class="logo">
              <el-icon size="32" color="#4ade80">
                <Monitor />
              </el-icon>
              <h1>CloudControl-Nexus</h1>
            </div>
            <p class="subtitle">智能设备控制平台</p>
          </div>
          <div class="header-right">
            <el-tag type="success" effect="dark" size="large">
              <el-icon>
                <Connection />
              </el-icon>
              前端调试工具
            </el-tag>
          </div>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
      
      <el-footer class="app-footer">
        <div class="footer-content">
          <p>&copy; 2024 CloudControl-Nexus. 智能设备控制平台</p>
          <div class="footer-links">
            <span>版本 v1.0.0</span>
            <span>•</span>
            <span>技术支持</span>
          </div>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { Monitor, Connection } from '@element-plus/icons-vue'
// 组件逻辑
</script>

<style>
/* 导入Google Fonts */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=JetBrains+Mono:wght@400;500&display=swap');

#app {
  font-family: 'Inter', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
}

#app::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 20% 80%, rgba(120, 119, 198, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 119, 198, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 40% 40%, rgba(120, 219, 255, 0.2) 0%, transparent 50%);
  pointer-events: none;
}

.app-header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
  display: flex;
  align-items: center;
  padding: 0 30px;
  height: 80px;
  position: relative;
  z-index: 10;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo i {
  font-size: 32px;
  color: #4ade80;
  text-shadow: 0 0 20px rgba(74, 222, 128, 0.5);
}

.header-content h1 {
  margin: 0;
  font-size: 28px;
  font-weight: 700;
  background: linear-gradient(45deg, #ffffff, #e0e7ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: -0.5px;
}

.subtitle {
  margin: 0;
  font-size: 14px;
  font-weight: 400;
  color: rgba(255, 255, 255, 0.8);
  letter-spacing: 0.5px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.app-footer {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  text-align: center;
  padding: 20px 30px;
  color: rgba(255, 255, 255, 0.8);
  position: relative;
  z-index: 10;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-links {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.footer-links span {
  color: rgba(255, 255, 255, 0.6);
}

.el-main {
  padding: 30px;
  background: transparent;
  min-height: calc(100vh - 160px);
  position: relative;
  z-index: 5;
}

/* 全局样式重置 */
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
  background: #0f0f23;
}

/* Element Plus 组件样式优化 */
.el-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.el-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

.el-card__header {
  background: rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 16px 16px 0 0;
  padding: 20px 24px;
}

.el-button {
  border-radius: 12px;
  font-weight: 500;
  letter-spacing: 0.3px;
  transition: all 0.3s ease;
  border: none;
  position: relative;
  overflow: hidden;
  color: #ffffff !important;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.el-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.el-button:hover::before {
  left: 100%;
}

.el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  color: #ffffff !important;
}

.el-button--primary:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
  transform: translateY(-1px);
  color: #ffffff !important;
}

.el-button--success {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  box-shadow: 0 4px 15px rgba(16, 185, 129, 0.4);
  color: #ffffff !important;
}

.el-button--success:hover {
  background: linear-gradient(135deg, #0ea371 0%, #047857 100%);
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.6);
  transform: translateY(-1px);
  color: #ffffff !important;
}

.el-button--warning {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  box-shadow: 0 4px 15px rgba(245, 158, 11, 0.4);
  color: #ffffff !important;
}

.el-button--warning:hover {
  background: linear-gradient(135deg, #e0910a 0%, #c06505 100%);
  box-shadow: 0 6px 20px rgba(245, 158, 11, 0.6);
  transform: translateY(-1px);
  color: #ffffff !important;
}

.el-button--danger {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  box-shadow: 0 4px 15px rgba(239, 68, 68, 0.4);
  color: #ffffff !important;
}

.el-button--danger:hover {
  background: linear-gradient(135deg, #e03e3e 0%, #c81e1e 100%);
  box-shadow: 0 6px 20px rgba(239, 68, 68, 0.6);
  transform: translateY(-1px);
  color: #ffffff !important;
}

.el-button--info {
  background: linear-gradient(135deg, #6b7280 0%, #4b5563 100%);
  box-shadow: 0 4px 15px rgba(107, 114, 128, 0.4);
  color: #ffffff !important;
}

.el-button--info:hover {
  background: linear-gradient(135deg, #5f6368 0%, #3f4751 100%);
  box-shadow: 0 6px 20px rgba(107, 114, 128, 0.6);
  transform: translateY(-1px);
  color: #ffffff !important;
}

.el-input {
  border-radius: 12px;
}

.el-input__inner {
  border-radius: 12px;
  border: 2px solid rgba(102, 126, 234, 0.2);
  transition: all 0.3s ease;
}

.el-input__inner:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.el-form-item__label {
  font-weight: 600;
  color: #374151;
}

.el-divider__text {
  background: rgba(255, 255, 255, 0.95);
  color: #374151;
  font-weight: 600;
  padding: 0 20px;
}

.el-tag {
  border-radius: 8px;
  font-weight: 500;
}

.el-tag--success {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  border: none;
}

.el-tag--danger {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
  border: none;
}
</style> 