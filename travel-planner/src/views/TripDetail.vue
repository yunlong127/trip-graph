<template>
  <div class="trip-detail-container">
    <div v-if="isLoading" class="loading-state">
      <p>加载中，请稍候...</p>
    </div>
    
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="loadTrip" class="btn btn-primary">重试</button>
    </div>
    
    <div v-else-if="trip" class="trip-detail">
      <div class="trip-header">
        <h2>{{ trip.name }}</h2>
        <div class="trip-actions">
          <button @click="goBack" class="btn btn-secondary">返回</button>
          <button @click="deleteTrip" class="btn btn-danger">删除</button>
        </div>
      </div>
      
      <div class="trip-info">
        <div class="info-card">
          <h3>基本信息</h3>
          <p><strong>目的地：</strong>{{ trip.destination }}</p>
          <p><strong>开始日期：</strong>{{ formatDate(trip.startDate) }}</p>
          <p><strong>结束日期：</strong>{{ formatDate(trip.endDate) }}</p>
          <p><strong>状态：</strong>{{ trip.status || '规划中' }}</p>
        </div>
        
        <div class="info-card">
          <h3>签证信息</h3>
          <p v-if="trip.visaRequired">需要签证</p>
          <p v-else>免签</p>
          <p v-if="trip.visaType"><strong>签证类型：</strong>{{ trip.visaType }}</p>
        </div>
        
        <div class="info-card">
          <h3>交通信息</h3>
          <p><strong>交通方式：</strong>{{ trip.transportation || '未指定' }}</p>
          <p v-if="trip.flightNumber"><strong>航班号：</strong>{{ trip.flightNumber }}</p>
          <p v-if="trip.trainNumber"><strong>车次：</strong>{{ trip.trainNumber }}</p>
        </div>
        
        <div class="info-card">
          <h3>住宿信息</h3>
          <p><strong>住宿类型：</strong>{{ trip.accommodation || '未指定' }}</p>
          <p v-if="trip.hotelName"><strong>酒店名称：</strong>{{ trip.hotelName }}</p>
        </div>
      </div>
      
      <div class="trip-description" v-if="trip.description">
        <h3>行程描述</h3>
        <p>{{ trip.description }}</p>
      </div>
      
      <!-- 行程规划 -->
      <div class="itinerary-section">
        <h3>行程规划</h3>
        <div v-if="trip.itineraries && trip.itineraries.length > 0">
          <div v-for="(itinerary, index) in trip.itineraries" :key="itinerary.id" class="itinerary-item">
            <div class="itinerary-day">第{{ index + 1 }}天</div>
            <div class="itinerary-activities">
              <div v-for="(activity, activityIndex) in itinerary.activities" :key="activityIndex" class="activity">
                <p><strong>{{ activity.time }}</strong> - {{ activity.name }}</p>
                <p>{{ activity.description }}</p>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="empty-state">
          <p>还没有行程规划，创建一个吧！</p>
        </div>
      </div>
      
      <!-- 预订信息 -->
      <div class="booking-section">
        <h3>预订信息</h3>
        <div v-if="trip.bookings && trip.bookings.length > 0">
          <div v-for="booking in trip.bookings" :key="booking.id" class="booking-item">
            <p><strong>类型：</strong>{{ booking.type }}</p>
            <p><strong>名称：</strong>{{ booking.name }}</p>
            <p><strong>日期：</strong>{{ formatDate(booking.date) }}</p>
            <p><strong>价格：</strong>{{ booking.price }} 元</p>
          </div>
        </div>
        <div v-else class="empty-state">
          <p>还没有预订信息，添加一个吧！</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

export default {
  name: 'TripDetail',
  setup() {
    const route = useRoute()
    const router = useRouter()
    
    return {
      route,
      router
    }
  },
  data() {
    return {
      trip: null,
      isLoading: false,
      error: null
    }
  },
  mounted() {
    this.loadTrip()
  },
  methods: {
    async loadTrip() {
      this.isLoading = true
      this.error = null
      try {
        const response = await axios.get(`http://localhost:8080/api/trips/${this.route.params.id}`)
        this.trip = response.data
      } catch (error) {
        this.error = '加载旅行详情失败'
        console.error('加载旅行详情失败:', error)
      } finally {
        this.isLoading = false
      }
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    },
    goBack() {
      this.router.push('/trips')
    },
    async deleteTrip() {
      if (!confirm('确定要删除这个旅行计划吗？')) return
      try {
        await axios.delete(`http://localhost:8080/api/trips/${this.route.params.id}`)
        this.router.push('/trips')
      } catch (error) {
        alert('删除失败')
      }
    }
  }
}
</script>

<style scoped>
.trip-detail-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px;
}

.loading-state,
.error-state {
  text-align: center;
  padding: 80px;
  font-size: 28px;
  color: #666666;
}

.trip-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.trip-header h2 {
  font-size: 48px;
  font-weight: 700;
  color: #333333;
  margin: 0;
}

.trip-actions {
  display: flex;
  gap: 16px;
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

.trip-info {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 32px;
  margin-bottom: 40px;
}

.info-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  border-top: 6px solid #3498db;
}

.info-card h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 24px 0;
}

.info-card p {
  font-size: 24px;
  color: #666666;
  margin: 0 0 12px 0;
  line-height: 1.6;
}

.info-card p strong {
  color: #333333;
  font-weight: 600;
}

.trip-description {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  border-top: 6px solid #2ecc71;
}

.trip-description h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 24px 0;
}

.trip-description p {
  font-size: 24px;
  color: #666666;
  margin: 0;
  line-height: 1.8;
}

.itinerary-section,
.booking-section {
  margin-top: 40px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  border-top: 6px solid #9b59b6;
}

.itinerary-section h3,
.booking-section h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 24px 0;
}

.itinerary-item {
  margin-bottom: 32px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 32px;
}

.itinerary-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.itinerary-day {
  font-size: 28px;
  font-weight: 700;
  color: #9b59b6;
  margin-bottom: 16px;
}

.activity {
  margin-bottom: 24px;
  padding-left: 24px;
  border-left: 4px solid #3498db;
}

.activity p {
  font-size: 22px;
  color: #666666;
  margin: 0 0 8px 0;
  line-height: 1.6;
}

.activity p strong {
  color: #333333;
  font-weight: 600;
}

.booking-item {
  margin-bottom: 32px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 32px;
}

.booking-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.booking-item p {
  font-size: 22px;
  color: #666666;
  margin: 0 0 8px 0;
  line-height: 1.6;
}

.booking-item p strong {
  color: #333333;
  font-weight: 600;
}

.empty-state {
  text-align: center;
  padding: 40px;
  font-size: 24px;
  color: #999999;
}
</style>
