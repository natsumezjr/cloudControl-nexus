import { createRouter, createWebHistory } from 'vue-router'
import DeviceControlPanel from '@/components/DeviceControlPanel.vue'

const routes = [
  {
    path: '/',
    name: 'DeviceControl',
    component: DeviceControlPanel,
    meta: {
      title: '设备控制面板'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} - CloudControl-Nexus`
  }
  next()
})

export default router 