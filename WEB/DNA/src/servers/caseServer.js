
import request from '../xhr/xhr'


class caseServer {
  dataSearch(searchInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/selectByCaseNo",
      
      // url:"api_iLabSTRmix/nationalTreasury/selectByCaseNo", // 国家库
      url:"api_iLabSTRmix/clims/selectByCaseNo",
      data:searchInfo
    })
  }
  dataDetail(dataInfo){
    return request.xhr({
      url:"api_iLabSTRmix/main/selectMatchResultMixSingleByMixedSampleGeneId",
      data:dataInfo
    })
  }
  dataModalDetail(caseInfo){
    return request.xhr({
      url:"api_iLabSTRmix/main/selectSingleMixedSampleGeneDetails",
      data:caseInfo
    })
  }
}
export default new caseServer
