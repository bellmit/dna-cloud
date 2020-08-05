
import request from '../xhr/xhr'


class caseServer {
  dataSearch(searchInfo){
    return request.xhr({
      // url:"api_iLabSTRmix/main/selectByCaseNo",
      
      url:"api_iLabSTRmix/mix/nationalTreasury/selectByCaseNo", // 国家库
      // url:"api_iLabSTRmix/mix/kitLocus/selectSingleGeneBySampleNo",
      // url:"api_iLabSTRmix/clims/selectByCaseNo",
      data:searchInfo,
      type:"get"
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
  findMixAndCaseNo(caseInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/link/findMixAndCaseNo",
      data:caseInfo
    })
  }
}
export default new caseServer
