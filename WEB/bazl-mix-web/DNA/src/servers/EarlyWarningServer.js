
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
      url:"api_iLabSTRmix/mix/mixedTyping/queryMatchResultList",
      data:pageInfo,
      type:"POST"
    })
  }
  // 查看详情接口
  SeeQueryMatchResult(pageInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/home/findNewestGene",
      data:pageInfo,
      type:"get"
    })
  }
    // 有效比中
    idList(pageInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mix/record/effective",
        data:pageInfo
      })
    }
    // 无效比中
    idList_2(pageInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mix/record/noneffective",
        data:pageInfo
      })
    }
    // 导出比对记录
    downLoadExl(pageInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mix/export/exportCompare",
        data:pageInfo,
        type:"get"
      })
    }
}

export default new EarlyWarningServer
