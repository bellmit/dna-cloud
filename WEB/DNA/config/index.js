'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path');
const assetsPublic_Path = '/limsiLabSTRmix/';
module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    // assetsPublicPath:assetsPublic_Path,
    // 混合库本地接口
    proxyTable: { 
      '/iLabSTRmix/api_iLabSTRmix':{  
        // target:"http://192.168.1.138:70/iLabSTRmix/api_iLabSTRmix",
        target:"http://luoo.nat300.top/",
        changeOrigin:true,
        pathRewrite:{
          '^/iLabSTRmix/api_iLabSTRmix':'/'
        }
      }
    },
    // proxyTable: { 
    //   '/api':{  
    //     // target:"http://192.168.1.138:70/iLabSTRmix/api_iLabSTRmix",
    //     target:"http://luoo.nat300.top/",
    //     changeOrigin:true,
    //     pathRewrite:{
    //       '^/api': ''
    //     }
    //   }
    // },
    // proxyTable: { 
    //   '/iLabSTRmix/api_iLabSTRmix':{  
    //     target:"http://14.60.76.54:60", 
    //     changeOrigin:true,
    //     pathRewrite:{
    //       '^/iLabSTRmix/api_iLabSTRmix':'/'
    //     }
    //   }
    // },
    // lims 本地调用接口
    // proxyTable: { 
    //   '/limsiLabSTRmix/api_iLabSTRmix':{  
    //     target:"http://192.168.1.142:8092", 
    //     changeOrigin:true,
    //     pathRewrite:{
    //       '^/limsiLabSTRmix/api_iLabSTRmix':'/'
    //     }
    //   }
    // },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8083, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,
    cssSourceMap: false
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'),
    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    // assetsPublicPath: '/limsiLabSTRmix/',  // ???????
    // assetsPublicPath: assetsPublic_Path,
    assetsPublicPath: '/', 

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
