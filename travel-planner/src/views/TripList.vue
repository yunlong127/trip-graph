<template>
  <div class="trip-list-container">
    <div class="trip-list-header">
      <h2>我的旅行</h2>
    </div>
    
    <div v-if="isLoading" class="loading-state">
      <p>加载中，请稍候...</p>
    </div>
    
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="loadTrips" class="btn btn-primary">重试</button>
    </div>
    
    <div v-else-if="trips.length === 0" class="empty-state">
      <p>还没有旅行计划，创建一个吧！</p>
    </div>
    
    <div v-else class="trip-grid">
      <div v-for="trip in trips" :key="trip.id" class="trip-card" @click="viewTrip(trip.id)">
        <h3>{{ trip.name }}</h3>
        <p class="trip-destination">目的地：{{ trip.destination }}</p>
        <p class="trip-date">{{ formatDate(trip.startDate) }} - {{ formatDate(trip.endDate) }}</p>
        <div class="trip-actions">
          <button @click.stop="viewTrip(trip.id)" class="btn btn-secondary">查看</button>
          <button @click.stop="deleteTrip(trip.id)" class="btn btn-danger">删除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  name: 'TripList',
  setup() {
    const router = useRouter()
    
    return {
      router
    }
  },
  data() {
    return {
      trips: [],
      isLoading: false,
      error: null
    }
  },
  mounted() {
    this.loadTrips()
  },
  activated() {
    this.loadTrips()
  },
  methods: {
    async loadTrips() {
      console.log('开始加载旅行计划')
      this.isLoading = true
      this.error = null
      try {
        const user = JSON.parse(localStorage.getItem('user'))
        console.log('用户ID:', user.id)
        const response = await axios.get(`http://localhost:8080/api/trips/user/${user.id}`)
        console.log('旅行计划数据:', response.data)
        this.trips = response.data
        console.log('旅行计划加载成功，共', this.trips.length, '个旅行计划')
      } catch (error) {
        this.error = '加载旅行列表失败'
        console.error('加载旅行列表失败:', error)
      } finally {
        this.isLoading = false
        console.log('旅行计划加载完成')
      }
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    },
    viewTrip(id) {
      this.router.push(`/trips/${id}`)
    },
    async deleteTrip(id) {
      if (!confirm('确定要删除这个旅行计划吗？')) return
      try {
        await axios.delete(`http://localhost:8080/api/trips/${id}`)
        this.loadTrips()
      } catch (error) {
        alert('删除失败')
      }
    },

  }
}
</script>

<style scoped>
.trip-list-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px;
}

.trip-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.trip-list-header h2 {
  font-size: 48px;
  font-weight: 700;
  color: #333333;
  margin: 0;
}

.btn {
  display: inline-block;
  padding: 20px 40px;
  border: none;
  border-radius: 12px;
  font-size: 24px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  text-align: center;
  line-height: 1.4;
}

.btn-primary {
  background-color: #3498db;
  color: #ffffff;
}

.btn-primary:hover {
  background-color: #2980b9;
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(52, 152, 219, 0.3);
}

.btn-secondary {
  background-color: #95a5a6;
  color: #ffffff;
}

.btn-danger {
  background-color: #e74c3c;
  color: #ffffff;
}

.loading-state,
.error-state,
.empty-state {
  text-align: center;
  padding: 80px;
  font-size: 28px;
  color: #666666;
}

.trip-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 32px;
}

.trip-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-top: 6px solid #3498db;
}

.trip-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
}

.trip-card h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 20px 0;
}

.trip-destination {
  font-size: 24px;
  color: #666666;
  margin: 0 0 12px 0;
}

.trip-date {
  font-size: 22px;
  color: #999999;
  margin: 0 0 24px 0;
}

.trip-actions {
  display: flex;
  gap: 16px;
}
</style>
