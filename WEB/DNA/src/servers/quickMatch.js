
import request from '../xhr/xhr'

class quickMatchServers {
  querykitLocusListInfo(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/querykitLocusListInfo",
      data:listInfo
    })
  }
  querySimleNofoForkitId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/querySimleNofoForkitId",
      data:listInfo
    })
  }
  queryLocusListInfoForkitId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/queryLocusListInfoForkitId",
      data:listInfo
    })
  }
  //录入基因信息后-加入比对列表
  addLocusListInfoForkitId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/addLocusListInfoForkitId",
      data:listInfo
    })
  }
  deleteLocusListInfoForId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/deleteLocusListInfoForId",
      data:listInfo
    })
  }
  // 提交比对
  updateLocusListInfoForId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/updateLocusListInfoForId",
      data:listInfo
    })
  }
}
export default new quickMatchServers
