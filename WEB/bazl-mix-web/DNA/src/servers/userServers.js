
import request from '../xhr/xhr'

class userServers {
  datalogin(loginInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/loginUser",
      url:"api_iLabSTRmix/system/auth/login", // 新版地址
      data:loginInfo,
      // type:"get"
    })
  }
  LogOutServer(logOutInfo){
    return request.xhr({
      url:"api_iLabSTRmix/logOutUser",
      data:logOutInfo
    })
  }
  checkUserName(info) {
    return request.xhr({ // 判断用户名是否存在
      url: 'api_iLabSTRmix/system/user/checkUserName',
      data: info
    })
  }
  companyList(info) { // 获取单位名称
    return request.xhr({
      url: 'api_iLabSTRmix/system/org/list',
      data: info
    })
  }
  getConnectName(info) { // 获取数据源信息
    return request.xhr({
      url: 'api_iLabSTRmix/system/datasource/getConnectName',
      data: info,
      type: 'get'
    })
  }
  registerUser(info) { // 注册用户
    return request.xhr({
      url: 'api_iLabSTRmix/system/register/save',
      data: info
    })
  }
  isConnection(info) { // 测试连接
    return request.xhr({
      url: 'api_iLabSTRmix/system/org/isConnection',
      data: info
    })
  }
  getUserList(info) { // 获取用户列表
    return request.xhr({
      url: 'api_iLabSTRmix/system/user/list',
      data: info
    })
  }
  editStatus(info) {  // 用户审核 "userId":"1" //用户id status:1 // 1：已审核  0：未通过
    return request.xhr({
      url: 'api_iLabSTRmix/system/user/editStatus',
      data: info
    })
  }
  delUser(info) { // 删除用户
    return request.xhr({
      url: 'api_iLabSTRmix/system/user/delete/' + info,
      type: 'get'
    })
  }
  editPassword(info) { // 修改密码
    return request.xhr({
      url: 'api_iLabSTRmix/system/user/editPassword',
      data: info
    })
  }
}
export default new userServers
