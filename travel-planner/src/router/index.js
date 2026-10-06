import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import TripList from '../views/TripList.vue'
import TripDetail from '../views/TripDetail.vue'
import RoutePlanning from '../views/RoutePlanning.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/trips', component: TripList, meta: { requiresAuth: true } },
  { path: '/trips/:id', component: TripDetail, meta: { requiresAuth: true } },
  { path: '/route-planning', component: RoutePlanning, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const isLoggedIn = localStorage.getItem('user') !== null
  if (to.meta.requiresAuth && !isLoggedIn) {
    return '/login'
  }
  if (isLoggedIn && (to.path === '/login' || to.path === '/register')) {
    return '/trips'
  }
})

export default router
