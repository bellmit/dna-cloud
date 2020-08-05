// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './assets/iconfont/iconfont.css'
import App from './App'  // 引入位置变动
import router from './router'
import animated from 'animate.css'
import echarts from 'echarts'
// import 'babel-polyfill'
Vue.use(animated)
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false;
Vue.use(iView);
import axios from 'axios'
Vue.prototype.$axios = axios;
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
