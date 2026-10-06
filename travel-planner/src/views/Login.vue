<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <div class="logo">
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M2 17L12 22L22 17" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M2 12L12 17L22 12" stroke="#3498db" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h2>欢迎回来</h2>
        <p>请登录您的账户</p>
      </div>
      <form @submit.prevent="login" class="login-form">
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
            <path d="M15 3H19C19.5304 3 20.0391 3.21071 20.4142 3.58579C20.7893 3.96086 21 4.46957 21 5V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H15" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M10 17L15 12L10 7" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M15 12H3" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          登录
        </button>
      </form>
      <div class="login-footer">
        <p>还没有账号？<router-link to="/register">立即注册</router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const form = {
      email: '',
      password: ''
    }
    
    const login = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/users/login', {
          email: form.email,
          password: form.password
        })
        localStorage.setItem('user', JSON.stringify(response.data))
        window.dispatchEvent(new StorageEvent('storage', {
          key: 'user',
          newValue: JSON.stringify(response.data)
        }))
        router.push('/trips')
      } catch (error) {
        alert('登录失败，请检查邮箱和密码')
      }
    }
    
    return {
      form,
      login
    }
  }
}
</script>

<style scoped>
.login-container {
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

.login-button {
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

.login-button:hover {
  transform: translateY(-2px) !important;
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3) !important;
}

.register-link {
  text-align: center !important;
  margin-top: 16px !important;
  font-size: 14px !important;
  color: #666 !important;
}

.register-link a {
  color: #667eea !important;
  text-decoration: none !important;
  font-weight: 600 !important;
  transition: color 0.3s ease !important;
}

.register-link a:hover {
  color: #764ba2 !important;
  text-decoration: underline !important;
}

.login-card {
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

.login-card::before {
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

.login-header {
  text-align: center !important;
  margin-bottom: 32px !important;
}

.login-header h2 {
  font-size: 24px !important;
  font-weight: 700 !important;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  -webkit-background-clip: text !important;
  -webkit-text-fill-color: transparent !important;
  background-clip: text !important;
  margin: 0 0 12px 0 !important;
  line-height: 1.2 !important;
}

.login-header p {
  font-size: 14px !important;
  color: #666666 !important;
  margin: 0 !important;
  line-height: 1.4 !important;
}

.login-form {
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

.login-footer {
  text-align: center !important;
  margin-top: 24px !important;
}

.login-footer p {
  font-size: 14px !important;
  color: #666666 !important;
  margin: 0 !important;
  line-height: 1.4 !important;
}

.login-footer a {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  -webkit-background-clip: text !important;
  -webkit-text-fill-color: transparent !important;
  background-clip: text !important;
  text-decoration: none !important;
  font-weight: 600 !important;
  font-size: 14px !important;
  transition: opacity 0.3s ease !important;
}

.login-footer a:hover {
  opacity: 0.8 !important;
}
</style>
