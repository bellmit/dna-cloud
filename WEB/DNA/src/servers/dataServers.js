
import request from '../xhr/xhr'

class dataServers {
  // 页面初始数据
  dataList(pageInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/selectMixedDateBaseGeneList",
      url:"api_iLabSTRmix/mixedTyping/queryMixedListInfo",
      data:pageInfo
    })
  }
  // 条件查询接口 
  dataSearch(searchInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/selectMixedDateBaseGeneList", 
      url:"api_iLabSTRmix/mixedTyping/queryMixedListInfo",
      data:searchInfo
    })
  }
  // 删除接口
  dataRemove(removeInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/deleteMixedDateBaseGene",
      url:"api_iLabSTRmix/mixedTyping/deleteMixedInfo",
      data:removeInfo
    })
  }
  // 批量删除
  dataBatchDelete(deleteInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/batchDeleteMixedDateGene",
      url:"api_iLabSTRmix/mixedTyping/deleteMixedListInfo",
      data:deleteInfo
    })
  }
  // 批量删除
  dataQueryMixedDetails(deleteInfo){
      return request.xhr({
        // url:"api_iLabSTRmix/main/batchDeleteMixedDateGene",
        url:"api_iLabSTRmix/mixedTyping/queryMixedDetails",
        data:deleteInfo
      })
    }
}

export default new dataServers
