const path = require('path')
module.exports = {
  publicPath: '/bazlDnaData',
  // publicPath: './',
  productionSourceMap: false,
  chainWebpack: config => {
    const types = ['vue-modules', 'vue', 'normal-modules', 'normal']
    types.forEach(type => addStyleResource(config.module.rule('less').oneOf(type)))
    config.module
      .rule('images')
      .use('url-loader')
      .loader('url-loader')
      .tap(options => Object.assign(options, { limit: 10240 }))
  },
  css: {
    loaderOptions: { // 向 CSS 相关的 loader 传递选项
      less: {
        javascriptEnabled: true
      }
    }
  },
  devServer: {
    disableHostCheck: true,
    // port: 8086,
    proxy: {
      '/api': {
        // target: 'http://192.168.1.102:8704',
        target: 'http://192.168.1.199:8704/',
        // target: 'http://192.168.1.222:8704/',
        // target: 'http://rap2.taobao.org:38080/app/mock/250253',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/'
        }
      }
    }
    // // 生产环境是否生成 sourceMap 文件
    // productionSourceMap: false,
    // outputDir: 'dist',
    // assetsDir: 'static'
    // 服务器端口号
  }
}
function addStyleResource (rule) {
  rule.use('style-resource')
    .loader('style-resources-loader')
    .options({
      patterns: [
        path.resolve(__dirname, './src/assets/styles/base.less'),
        path.resolve(__dirname, './src/assets/styles/base.css')
      ]
    })
}
