import axios from 'axios'
import { Modal, Spin } from 'view-design'
import router from './src/router'

axios.defaults.timeout = 6000000 // 响应时间
// axios.defaults.baseURL = '/api' // 配置接口地址
// axios.defaults.baseURL = '/bazlDnaMix/api_iLabSTRmix/' // 配置接口地址
// request拦截器 ==> 对请求参数进行处理
axios.interceptors.request.use(
  config => {
    if (!config.noLoading) {
      Spin.show({
        render: h => {
          return h('div', [
            h('Icon', {
              class: 'spin-icon-load',
              props: {
                type: 'ios-loading',
                size: 18
              }
            }),
            h('div', '加载中')
          ])
        }
      })
    }
    // get请求 隐藏loading  例:{params:{ id: 1, noLoading: true }}
    if (config.params) {
      if (config.params.noLoading) {
        Spin.hide()
      }
    }

    const token = sessionStorage.getItem('accessToken')
    if (token) {
      // 判断token是否存在
      config.headers.Authorization = token // 将token设置成请求头
    }
    return config
  },
  error => {
    // 处理请求错误
    console.log(error) // for debug
    Promise.reject(error)
  }
)
// respone拦截器 ==> 对响应做处理
axios.interceptors.response.use(
  res => {
    Spin.hide()
    if (res.data.code !== 1) {
      return Promise.reject(res)
    } else {
      return res.data
    }
  },
  error => {
    Spin.hide()
    // 判断error的status代码，并将对应的信息告知用户
    let text = ''
    const err = error.response
    if (err.status) {
      switch (err.status) {
        case 400:
          text = '请求错误(400)，请重新申请'
          break
        case 401:
          text = '登陆信息已失效,请重新登陆~'
          return router.replace('/')
        case 403:
          text = '拒绝访问(403)'
          break
        case 404:
          text = '请求出错(404)'
          break
        case 408:
          text = '请求超时(408)'
          break
        case 500:
          text = '服务器错误(500)，请重启软件或切换功能页！'
          break
        case 501:
          text = '服务未实现(501)'
          break
        case 502:
          text = '网络错误(502)'
          break
        case 503:
          text = '服务不可用(503)'
          break
        case 504:
          text = '网络超时(504)'
          break
        case 505:
          text = 'HTTP版本不受支持(505)'
          break
        default:
          text = '网络连接出错'
      }
    } else {
      text = '出错了,请重试~'
    }
    Modal.info({
      title: '提示',
      content: text
    })
    return Promise.reject(error)
  }
)
