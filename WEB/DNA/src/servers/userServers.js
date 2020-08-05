
import request from '../xhr/xhr'

class userServers {
  datalogin(loginInfo){
    return request.xhr({
      url:"api_iLabSTRmix/loginUser",
      data:loginInfo
    })
  }
  LogOutServer(logOutInfo){
    return request.xhr({
      url:"api_iLabSTRmix/logOutUser",
      data:logOutInfo
    })
  }
}
export default new userServers
