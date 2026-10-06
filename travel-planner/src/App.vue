<template>
  <div class="app">
    <header class="navbar">
      <div class="navbar-brand">
        <h1>旅行规划器</h1>
      </div>
      <nav class="nav-links">
          <span v-if="isLoggedIn" class="user-info">{{ userName }}</span>
          <router-link v-if="isLoggedIn" to="/trips" class="nav-link">我的旅行</router-link>
          <router-link v-if="isLoggedIn" to="/route-planning" class="nav-link">路径规划</router-link>
          <router-link v-if="!isLoggedIn" to="/login" class="nav-link">登录</router-link>
          <router-link v-if="!isLoggedIn" to="/register" class="nav-link">注册</router-link>
          <button v-if="isLoggedIn" @click="logout" class="btn btn-secondary">退出登录</button>
        </nav>
    </header>
    
    <main class="main-content">
      <keep-alive>
        <router-view />
      </keep-alive>
    </main>
    
    <footer class="footer">
      <p>&copy; 2026 旅行规划器. 保留所有权利.</p>
    </footer>
  </div>
</template>

<script>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'App',
  setup() {
    const router = useRouter()
    const isLoggedIn = ref(localStorage.getItem('user') !== null)
    const userData = ref(localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null)
    
    const userName = computed(() => {
      if (userData.value) {
        return userData.value.username || '用户'
      }
      return ''
    })
    
    const logout = () => {
      localStorage.removeItem('user')
      isLoggedIn.value = false
      userData.value = null
      router.push('/login')
    }
    
    const updateAuthStatus = () => {
      const user = localStorage.getItem('user')
      isLoggedIn.value = user !== null
      userData.value = user ? JSON.parse(user) : null
    }
    
    onMounted(() => {
      window.addEventListener('storage', updateAuthStatus)
    })
    
    onUnmounted(() => {
      window.removeEventListener('storage', updateAuthStatus)
    })
    
    return {
      isLoggedIn,
      userName,
      logout
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f5f7fa;
}

.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.navbar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
  padding: 24px 48px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 100;
}

.navbar-brand h1 {
  font-size: 24px;
  font-weight: 700;
  color: #ffffff;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-info {
  color: #ffffff;
  font-weight: 600;
  font-size: 16px;
  background-color: rgba(255, 255, 255, 0.2);
  padding: 8px 16px;
  border-radius: 6px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.nav-link {
  text-decoration: none;
  font-weight: 600;
  font-size: 16px;
  transition: all 0.3s ease;
  padding: 8px 16px;
  border-radius: 6px;
  background-color: rgba(255, 255, 255, 0.2);
  color: #ffffff;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn {
  display: inline-block;
  padding: 8px 16px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 6px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  text-align: center;
  line-height: 1.4;
  background-color: rgba(255, 255, 255, 0.2);
  color: #ffffff;
  backdrop-filter: blur(10px);
}

.btn-secondary:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.main-content {
  flex: 1;
  padding: 0;
}

.footer {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  margin-top: auto;
}

.footer p {
  text-align: center;
  color: #ffffff;
  font-size: 14px;
  margin: 0;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}
</style>
