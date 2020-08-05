
import request from '../xhr/xhr'

class dataServers {
  // 页面初始数据
  dataList(pageInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/selectMixedDateBaseGeneList",
      // url:"api_iLabSTRmix/mixedTyping/queryMixedListInfo",
       url:"api_iLabSTRmix/mix/mixedTyping/selectListPaging",
      data:pageInfo,
      
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
      url:"api_iLabSTRmix/mix/mixedTyping/deleteById",
      data:removeInfo,
      type:"get"
    })
  }
  // 批量删除
  dataBatchDelete(deleteInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/batchDeleteMixedDateGene",
      url:"api_iLabSTRmix/mix/mixedTyping/deleteByIds",
      data:deleteInfo,
      type:"get"
    })
  }
  // 查看混合分型
  dataQueryMixedDetails(deleteInfo){
      return request.xhr({
        // url:"api_iLabSTRmix/main/batchDeleteMixedDateGene",
        url:"api_iLabSTRmix/mix/mixedTyping/queryMixedDetails",
        data:deleteInfo,
        type:"get"
      })
    }
}

export default new dataServers
