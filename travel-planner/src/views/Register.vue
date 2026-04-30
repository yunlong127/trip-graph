<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <div class="logo">
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M2 17L12 22L22 17" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M2 12L12 17L22 12" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h2>创建账户</h2>
        <p>开始您的旅行规划之旅</p>
      </div>
      <form @submit.prevent="register" class="register-form">
        <div class="form-group">
          <label for="username">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <circle cx="12" cy="7" r="4" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            用户名
          </label>
          <input type="text" id="username" v-model="form.username" required placeholder="请输入用户名">
        </div>
        <div class="form-group">
          <label for="email">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M4 4H20C21.1 4 22 4.9 22 6V18C22 19.1 21.1 20 20 20H4C2.9 20 2 19.1 2 18V6C2 4.9 2.9 4 4 4Z" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M22 6L12 13L2 6" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            邮箱
          </label>
          <input type="email" id="email" v-model="form.email" required placeholder="请输入邮箱">
        </div>
        <div class="form-group">
          <label for="password">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M7 11V7C7 5.67392 7.52678 4.40215 8.46447 3.46447C9.40215 2.52678 10.6739 2 12 2C13.3261 2 14.5979 2.52678 15.5355 3.46447C16.4732 4.40215 17 5.67392 17 7V11" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            密码
          </label>
          <input type="password" id="password" v-model="form.password" required placeholder="请输入密码">
        </div>
        <button type="submit" class="btn btn-primary">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M16 21V19C16 17.9391 15.5786 16.9217 14.8284 16.1716C14.0783 15.4214 13.0609 15 12 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <circle cx="8.5" cy="7" r="4" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <line x1="20" y1="8" x2="20" y2="14" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <line x1="23" y1="11" x2="17" y2="11" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          注册
        </button>
      </form>
      <div class="register-footer">
        <p>已有账号？<router-link to="/login">立即登录</router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  name: 'Register',
  setup() {
    const router = useRouter()
    const form = {
      username: '',
      email: '',
      password: ''
    }
    
    const register = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/users/register', form)
        localStorage.setItem('user', JSON.stringify(response.data))
        window.dispatchEvent(new StorageEvent('storage', {
          key: 'user',
          newValue: JSON.stringify(response.data)
        }))
        router.push('/trips')
      } catch (error) {
        alert('注册失败，请检查信息')
      }
    }
    
    return {
      form,
      register
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
  min-height: 100vh !important;
  padding: 20px !important;
  background: linear-gradient(135deg, #e8f4f8 0%, #f0f4f8 100%) !important;
  font-size: 16px !important;
}

.input-group {
  margin-bottom: 16px !important;
}

.input-group label {
  display: block !important;
  margin-bottom: 8px !important;
  font-weight: 600 !important;
  color: #333 !important;
  font-size: 14px !important;
}

.input-group input {
  width: 100% !important;
  padding: 12px !important;
  border: 1px solid #ddd !important;
  border-radius: 8px !important;
  font-size: 16px !important;
  transition: all 0.3s ease !important;
  box-sizing: border-box !important;
}

.input-group input:focus {
  outline: none !important;
  border-color: #667eea !important;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1) !important;
}

.register-button {
  width: 100% !important;
  padding: 12px !important;
  border: none !important;
  border-radius: 8px !important;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  color: white !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  cursor: pointer !important;
  transition: all 0.3s ease !important;
  margin-top: 8px !important;
}

.register-button:hover {
  transform: translateY(-2px) !important;
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3) !important;
}

.login-link {
  text-align: center !important;
  margin-top: 16px !important;
  font-size: 14px !important;
  color: #666 !important;
}

.login-link a {
  color: #667eea !important;
  text-decoration: none !important;
  font-weight: 600 !important;
  transition: color 0.3s ease !important;
}

.login-link a:hover {
  color: #764ba2 !important;
  text-decoration: underline !important;
}

.register-card {
  width: 100% !important;
  max-width: 400px !important;
  min-height: 180px !important;
  background-color: #ffffff !important;
  border-radius: 12px !important;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2) !important;
  padding: 20px !important;
  display: flex !important;
  flex-direction: column !important;
  justify-content: center !important;
  position: relative !important;
  overflow: hidden !important;
}

.register-card::before {
  content: '' !important;
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  height: 8px !important;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%) !important;
}

.logo {
  display: flex !important;
  justify-content: center !important;
  margin-bottom: 16px !important;
}

.logo svg {
  width: 40px !important;
  height: 40px !important;
  filter: drop-shadow(0 4px 8px rgba(52, 152, 219, 0.3)) !important;
}

.register-header {
  text-align: center !important;
  margin-bottom: 32px !important;
}

.register-header h2 {
  font-size: 24px !important;
  font-weight: 700 !important;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  -webkit-background-clip: text !important;
  -webkit-text-fill-color: transparent !important;
  background-clip: text !important;
  margin: 0 0 12px 0 !important;
  line-height: 1.2 !important;
}

.register-header p {
  font-size: 14px !important;
  color: #666666 !important;
  margin: 0 !important;
  line-height: 1.4 !important;
}

.register-form {
  display: flex !important;
  flex-direction: column !important;
  gap: 20px !important;
}

.form-group {
  display: flex !important;
  flex-direction: column !important;
  gap: 8px !important;
}

.form-group label {
  font-size: 14px !important;
  font-weight: 600 !important;
  color: #333333 !important;
  line-height: 1.4 !important;
  display: flex !important;
  align-items: center !important;
  gap: 8px !important;
}

.form-group label svg {
  width: 16px !important;
  height: 16px !important;
  flex-shrink: 0 !important;
}

.form-group input {
  width: 100% !important;
  padding: 12px !important;
  border: 1px solid #e0e0e0 !important;
  border-radius: 8px !important;
  font-size: 16px !important;
  line-height: 1.4 !important;
  color: #333333 !important;
  background-color: #f8f9fa !important;
  transition: all 0.3s ease !important;
  box-sizing: border-box !important;
}

.form-group input:focus {
  outline: none !important;
  border-color: #667eea !important;
  background-color: #ffffff !important;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1) !important;
}

.form-group input::placeholder {
  color: #999999 !important;
  font-size: 14px !important;
}

.btn {
  display: inline-flex !important;
  align-items: center !important;
  justify-content: center !important;
  gap: 8px !important;
  padding: 12px 24px !important;
  border: none !important;
  border-radius: 8px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  cursor: pointer !important;
  transition: all 0.3s ease !important;
  text-decoration: none !important;
  text-align: center !important;
  line-height: 1.4 !important;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  color: #ffffff !important;
  width: 100% !important;
  margin-top: 12px !important;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4) !important;
}

.btn-primary:hover {
  transform: translateY(-2px) !important;
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.5) !important;
}

.btn-primary:active {
  transform: translateY(-1px) !important;
}

.register-footer {
  text-align: center !important;
  margin-top: 24px !important;
}

.register-footer p {
  font-size: 14px !important;
  color: #666666 !important;
  margin: 0 !important;
  line-height: 1.4 !important;
}

.register-footer a {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  -webkit-background-clip: text !important;
  -webkit-text-fill-color: transparent !important;
  background-clip: text !important;
  text-decoration: none !important;
  font-weight: 600 !important;
  font-size: 14px !important;
  transition: opacity 0.3s ease !important;
}

.register-footer a:hover {
  opacity: 0.8 !important;
}
</style>
