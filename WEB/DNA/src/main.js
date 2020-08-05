// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
// import App from './App'
// import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './assets/iconfont/iconfont.css'
import App from './App'  // 引入位置变动
import router from './router'
import animated from 'animate.css'
Vue.use(animated)

import echarts from 'echarts'
Vue.prototype.$echarts = echarts
//
// import App from './App'
// import router from './router'
Vue.config.productionTip = false;
Vue.use(iView);
import axios from 'axios'
Vue.prototype.$axios = axios;
// axios.defaults.baseURL='http://192.168.0.138:70/'
Vue.filter('moment', function (value, formatString) {
  formatString = formatString || 'YYYY/MM/DD';
  return moment(value).format(formatString);
});


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
