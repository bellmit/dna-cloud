
import request from '../xhr/xhrServe'
// import request from '../xhr/xhr'

class serve {
  
  // 案件 提交比对 
  updateLocusListInfoForId(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/kitLocus/updateLocusList",
      data:listInfo
    })
  }
}
export default new serve
