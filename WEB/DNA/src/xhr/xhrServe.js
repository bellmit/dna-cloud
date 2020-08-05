// import Vue from 'vue'
// import { Message } from 'iview'
import { Message } from 'iview'
import Router from '../router/index'
class request{
  xhr(param){
    return new Promise((resolve, reject)=>{
      $.ajax({
        type :param.type          || 'post',
        // url  :param.url           || 'http://14.60.76.93:70/', 'http://192.168.0.138:70/',
        url  :param.url           || 'http://192.168.1.138:70/',
        dataType:'json',
        data:param.data     || null ,
        // contentType:"application/json",
        contentType:"application/json;charset=utf-8",
        timeout:180000,
        success:function (res) {
          if (res.data.code == 2) {
            Message.error('您的登录已超时！');
            setTimeout(() => {
                Router.push('/login' ) 
            }, 1000);
          }  
          typeof resolve=='function'&&resolve(res);
        },
        error(err){
          //请求失败
          console.log(err);
          typeof reject ==="function"&&reject(err)
        }
      })
    })
  }
}

export default new request

