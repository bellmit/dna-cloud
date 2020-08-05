import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ViewUI from 'view-design'
import './assets/iconfont/iconfont.css'
import 'view-design/dist/styles/iview.css'
import axios from 'axios'
import '../http'
import md5 from 'js-md5'
Vue.prototype.$md5 = md5
axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? '/bazlDnaMix/api_iLabSTRmix/' : '/api'
axios.defaults.withCredentials = true
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(ViewUI)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
