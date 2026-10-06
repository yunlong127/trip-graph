<template>
  <div class="route-planning-container">
    <h2>路径规划</h2>
    
    <div class="planning-form">
      <div class="form-group">
        <label>起点</label>
        <select v-model="startLocationId" required>
          <option value="">请选择</option>
          <option v-for="location in locations" :key="location.id" :value="location.id">
            {{ location.name }}, {{ location.city }}, {{ location.country }}
          </option>
        </select>
        <div v-if="startLocationFacilities.length > 0" class="facilities-list">
          <h4>起点设施</h4>
          <div v-for="facility in startLocationFacilities" :key="facility.id" class="facility-item">
            <p><strong>{{ facility.name }}</strong> ({{ facility.type }})</p>
            <p v-if="facility.description">{{ facility.description }}</p>
          </div>
        </div>
      </div>
      
      <div class="form-group">
        <label>终点</label>
        <select v-model="endLocationId" required>
          <option value="">请选择</option>
          <option v-for="location in locations" :key="location.id" :value="location.id">
            {{ location.name }}, {{ location.city }}, {{ location.country }}
          </option>
        </select>
        <div v-if="endLocationFacilities.length > 0" class="facilities-list">
          <h4>终点设施</h4>
          <div v-for="facility in endLocationFacilities" :key="facility.id" class="facility-item">
            <p><strong>{{ facility.name }}</strong> ({{ facility.type }})</p>
            <p v-if="facility.description">{{ facility.description }}</p>
          </div>
        </div>
      </div>
      
      <div class="form-group">
        <label>
          <input type="checkbox" v-model="includeVisaRequired"> 包含需要签证的路线
        </label>
      </div>
      
      <div class="form-group">
        <label>开始时间</label>
        <input type="datetime-local" v-model="startDate" required>
      </div>
      
      <div class="form-group">
        <label>结束时间</label>
        <input type="datetime-local" v-model="endDate" required>
      </div>
      
      <button @click="calculateRoutes" class="btn btn-primary">计算路径</button>
    </div>
    
    <div v-if="isLoading" class="loading-state">
      <p>计算中，请稍候...</p>
    </div>
    
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="calculateRoutes" class="btn btn-primary">重试</button>
    </div>
    
    <div v-else-if="shortestTimePath.length > 0 || leastCostPath.length > 0 || leastTransfersPath.length > 0" class="routes-result">
      <div class="route-option">
        <h3>最短时间</h3>
        <div class="route-details">
          <div v-for="(route, index) in shortestTimePath" :key="index" class="route-item">
            <p><strong>{{ route.startLocation.name }}</strong> → <strong>{{ route.endLocation.name }}</strong></p>
            <p>交通方式：{{ route.transportation }}</p>
            <p>距离：{{ route.distance }} 公里</p>
            <p>时间：{{ route.duration }} 小时</p>
            <p>价格：{{ route.price }} 元</p>
            <p v-if="route.visaRequired" class="visa-required">需要签证</p>
          </div>
        </div>
        <button @click="selectRoute(shortestTimePath)" class="btn btn-primary">选择此路径</button>
      </div>
      
      <div class="route-option">
        <h3>最少花费</h3>
        <div class="route-details">
          <div v-for="(route, index) in leastCostPath" :key="index" class="route-item">
            <p><strong>{{ route.startLocation.name }}</strong> → <strong>{{ route.endLocation.name }}</strong></p>
            <p>交通方式：{{ route.transportation }}</p>
            <p>距离：{{ route.distance }} 公里</p>
            <p>时间：{{ route.duration }} 小时</p>
            <p>价格：{{ route.price }} 元</p>
            <p v-if="route.visaRequired" class="visa-required">需要签证</p>
          </div>
        </div>
        <button @click="selectRoute(leastCostPath)" class="btn btn-primary">选择此路径</button>
      </div>
      
      <div class="route-option">
        <h3>最少中转</h3>
        <div class="route-details">
          <div v-for="(route, index) in leastTransfersPath" :key="index" class="route-item">
            <p><strong>{{ route.startLocation.name }}</strong> → <strong>{{ route.endLocation.name }}</strong></p>
            <p>交通方式：{{ route.transportation }}</p>
            <p>距离：{{ route.distance }} 公里</p>
            <p>时间：{{ route.duration }} 小时</p>
            <p>价格：{{ route.price }} 元</p>
            <p v-if="route.visaRequired" class="visa-required">需要签证</p>
          </div>
        </div>
        <button @click="selectRoute(leastTransfersPath)" class="btn btn-primary">选择此路径</button>
      </div>
    </div>
    
    <!-- 保存路径为旅行计划 -->
    <div v-if="selectedRoute.length > 0" class="save-trip-modal">
      <h3>保存为旅行计划</h3>
      <div class="form-group">
        <label>旅行名称</label>
        <input v-model="tripName" required>
      </div>
      <button @click="saveTrip" class="btn btn-primary">保存</button>
      <button @click="selectedRoute = []" class="btn btn-secondary">取消</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  name: 'RoutePlanning',
  setup() {
    const router = useRouter()
    
    return {
      router
    }
  },
  data() {
    return {
      locations: [],
      startLocationId: '',
      endLocationId: '',
      includeVisaRequired: false,
      startDate: '',
      endDate: '',
      isLoading: false,
      error: null,
      shortestTimePath: [],
      leastCostPath: [],
      leastTransfersPath: [],
      selectedRoute: [],
      tripName: '',
      startLocationFacilities: [],
      endLocationFacilities: []
    }
  },
  mounted() {
    this.loadLocations()
  },
  watch: {
    startLocationId(newVal) {
      if (newVal) {
        this.loadFacilities(newVal, 'start')
      } else {
        this.startLocationFacilities = []
      }
    },
    endLocationId(newVal) {
      if (newVal) {
        this.loadFacilities(newVal, 'end')
      } else {
        this.endLocationFacilities = []
      }
    }
  },
  methods: {
    async loadLocations() {
      try {
        const response = await axios.get('http://localhost:8080/api/locations')
        this.locations = response.data
      } catch (error) {
        console.error('加载地点失败:', error)
      }
    },
    async loadFacilities(locationId, type) {
      try {
        const response = await axios.get(`http://localhost:8080/api/facilities/location/${locationId}`)
        if (type === 'start') {
          this.startLocationFacilities = response.data
        } else {
          this.endLocationFacilities = response.data
        }
      } catch (error) {
        console.error('加载设施失败:', error)
      }
    },
    async calculateRoutes() {
      if (!this.startLocationId || !this.endLocationId || !this.startDate || !this.endDate) {
        this.error = '请填写所有必填字段'
        return
      }
      
      // 检查时间合法性：结束时间不能早于开始时间
      const start = new Date(this.startDate)
      const end = new Date(this.endDate)
      if (end < start) {
        this.error = '结束时间不能早于开始时间'
        return
      }
      
      // 计算旅程总时间（小时）
      const tripDuration = (end - start) / (1000 * 60 * 60)
      
      this.isLoading = true
      this.error = null
      try {
        // 获取最短时间路径
        const shortestTimeResponse = await axios.get(`http://localhost:8080/api/routes/shortest-time`, {
          params: {
            startLocationId: Number(this.startLocationId),
            endLocationId: Number(this.endLocationId),
            includeVisaRequired: this.includeVisaRequired
          }
        })
        this.shortestTimePath = shortestTimeResponse.data
        
        // 获取最少花费路径
        const leastCostResponse = await axios.get(`http://localhost:8080/api/routes/least-cost`, {
          params: {
            startLocationId: Number(this.startLocationId),
            endLocationId: Number(this.endLocationId),
            includeVisaRequired: this.includeVisaRequired
          }
        })
        this.leastCostPath = leastCostResponse.data
        
        // 获取最少中转路径
        const leastTransfersResponse = await axios.get(`http://localhost:8080/api/routes/least-transfers`, {
          params: {
            startLocationId: Number(this.startLocationId),
            endLocationId: Number(this.endLocationId),
            includeVisaRequired: this.includeVisaRequired
          }
        })
        this.leastTransfersPath = leastTransfersResponse.data
        
        // 检查路线时间是否超过旅程总时间
        const checkRouteTime = (path, pathName) => {
          if (path.length > 0) {
            const totalRouteTime = path.reduce((sum, route) => sum + route.duration, 0)
            if (totalRouteTime > tripDuration) {
              return `${pathName}路径总时间(${totalRouteTime.toFixed(2)}小时)超过旅程总时间(${tripDuration.toFixed(2)}小时)`
            }
          }
          return null
        }
        
        const timeErrors = []
        const error1 = checkRouteTime(this.shortestTimePath, '最短时间')
        if (error1) timeErrors.push(error1)
        const error2 = checkRouteTime(this.leastCostPath, '最少花费')
        if (error2) timeErrors.push(error2)
        const error3 = checkRouteTime(this.leastTransfersPath, '最少中转')
        if (error3) timeErrors.push(error3)
        
        if (timeErrors.length > 0) {
          this.error = timeErrors.join('；')
        }
        
        // 检查是否没有找到路径
        if (this.shortestTimePath.length === 0 && this.leastCostPath.length === 0 && this.leastTransfersPath.length === 0) {
          this.error = '没有找到合法路径，请尝试选择其他起点和终点，或勾选"包含需要签证的路线"选项'
        }
      } catch (error) {
        this.error = '计算路径失败'
        console.error('计算路径失败:', error)
      } finally {
        this.isLoading = false
      }
    },
    selectRoute(route) {
      this.selectedRoute = route
    },
    async saveTrip() {
      if (!this.tripName) {
        alert('请输入旅行名称')
        return
      }
      
      try {
        const user = JSON.parse(localStorage.getItem('user'))
        if (!user) {
          alert('请先登录')
          this.router.push('/login')
          return
        }
        
        // 计算总距离、总时间和总价格
        let totalDistance = 0
        let totalDuration = 0
        let totalPrice = 0
        let hasVisaRequired = false
        let mainTransportation = ''
        
        for (const route of this.selectedRoute) {
          totalDistance += route.distance
          totalDuration += route.duration
          totalPrice += route.price
          if (route.visaRequired) {
            hasVisaRequired = true
          }
          if (!mainTransportation) {
            mainTransportation = route.transportation
          }
        }
        
        // 创建旅行计划
        const trip = {
          name: this.tripName,
          destination: this.selectedRoute[this.selectedRoute.length - 1].endLocation.name,
          startDate: this.startDate,
          endDate: this.endDate,
          status: '规划中',
          description: `从${this.selectedRoute[0].startLocation.name}到${this.selectedRoute[this.selectedRoute.length - 1].endLocation.name}的旅行，总距离${totalDistance.toFixed(2)}公里，总时间${totalDuration.toFixed(2)}小时，总价格${totalPrice.toFixed(2)}元`,
          visaRequired: hasVisaRequired,
          transportation: mainTransportation,
          user: {
            id: user.id
          }
        }
        
        console.log('保存的旅行计划:', trip)
        
        await axios.post('http://localhost:8080/api/trips', trip)
        console.log('旅行计划创建成功')
        this.router.push('/trips')
      } catch (error) {
        alert('保存失败')
        console.error('保存失败:', error)
      }
    }
  }
}
</script>

<style scoped>
.route-planning-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px;
}

h2 {
  font-size: 48px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 40px 0;
  text-align: center;
}

.planning-form {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  margin-bottom: 40px;
  border-top: 6px solid #3498db;
}

.form-group {
  margin-bottom: 24px;
}

.form-group label {
  display: block;
  font-size: 24px;
  font-weight: 600;
  color: #333333;
  margin-bottom: 8px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 16px;
  border: 2px solid #e0e0e0;
  border-radius: 12px;
  font-size: 20px;
  color: #333333;
  background-color: #ffffff;
  transition: border-color 0.3s ease;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #3498db;
}

.form-group input[type="checkbox"] {
  width: auto;
  margin-right: 8px;
}

.facilities-list {
  margin-top: 16px;
  padding: 16px;
  background-color: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.facilities-list h4 {
  font-size: 20px;
  font-weight: 600;
  color: #333333;
  margin: 0 0 12px 0;
}

.facility-item {
  padding: 12px;
  margin-bottom: 8px;
  background-color: #ffffff;
  border-radius: 6px;
  border: 1px solid #e0e0e0;
}

.facility-item:last-child {
  margin-bottom: 0;
}

.facility-item p {
  font-size: 16px;
  color: #666666;
  margin: 0 0 4px 0;
  line-height: 1.4;
}

.facility-item p strong {
  color: #333333;
  font-weight: 600;
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

.loading-state,
.error-state {
  text-align: center;
  padding: 80px;
  font-size: 28px;
  color: #666666;
}

.routes-result {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 32px;
  margin-bottom: 40px;
}

.route-option {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  border-top: 6px solid #9b59b6;
}

.route-option h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 24px 0;
}

.route-details {
  margin-bottom: 32px;
}

.route-item {
  margin-bottom: 24px;
  padding-bottom: 24px;
  border-bottom: 1px solid #e0e0e0;
}

.route-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.route-item p {
  font-size: 20px;
  color: #666666;
  margin: 0 0 8px 0;
  line-height: 1.6;
}

.route-item p strong {
  color: #333333;
  font-weight: 600;
}

.visa-required {
  color: #e74c3c !important;
  font-weight: 600 !important;
}

.save-trip-modal {
  margin-top: 40px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 40px;
  border-top: 6px solid #2ecc71;
}

.save-trip-modal h3 {
  font-size: 32px;
  font-weight: 700;
  color: #333333;
  margin: 0 0 24px 0;
  text-align: center;
}

.save-trip-modal .form-group {
  margin-bottom: 32px;
}

.save-trip-modal .btn {
  margin-right: 16px;
}

@media (max-width: 768px) {
  .routes-result {
    grid-template-columns: 1fr;
  }
  
  .save-trip-modal .btn {
    display: block;
    width: 100%;
    margin-right: 0;
    margin-bottom: 16px;
  }
}
</style>