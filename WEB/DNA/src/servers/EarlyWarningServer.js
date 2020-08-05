
import request from '../xhr/xhr'

class EarlyWarningServer {
  eWarning(pageInfo){
    return request.xhr({
    //   url:"api_iLabSTRmix/main/selectMixedDateBaseGeneList",
      data:pageInfo
    })
  }
  // 表格数据接口
  QueryMatchResultList(pageInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mixedTyping/queryMatchResultList",
      data:pageInfo
    })
  }
  // 查看详情接口
  SeeQueryMatchResult(pageInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mixedTyping/queryMatchResult",
      data:pageInfo
    })
  }
}

export default new EarlyWarningServer
