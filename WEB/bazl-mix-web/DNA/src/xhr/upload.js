// import Vue from 'vue'
// import { Message } from 'iview'
import { Message } from 'iview'
import Router from '../router/index'
class request{
  xhr(param){
    return new Promise((resolve, reject)=>{
      $.ajax({
        type :param.type          || 'post',
        url  :param.url           || 'http://192.168.1.138:70/',
        dataType:'json',
        data:param.data     || null ,
        processData: false,
        contentType: false, 
        // contentType:"application/json;charset=utf-8",
        timeout:180000,
        beforeSend: function (XMLHttpRequest) {
          XMLHttpRequest.setRequestHeader("Authorization", localStorage.getItem("token"));
        },
        success:function (res) {
          if (res.code == 401) {
            Message.error('您的登录已超时！');
            setTimeout(() => {
                sessionStorage.clear();
                localStorage.clear();
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

