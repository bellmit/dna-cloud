
import request from '../xhr/xhr'

class thansDetailsServers {
  splitSingleCompare(splitSingleInfo){
    return request.xhr({
      url:"api_iLabSTRmix/main/selectSplitSingleCompareResult",
      data:splitSingleInfo
    })
  }
  // 页面表格数据接口 三个表格统一
  mixedSingleCompare(mixedSingleInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mixedTyping/queryMatchResultMixSingle",
        data:mixedSingleInfo
      })
    }
    // 查看接口
    QueryMatchResultInfo(mixedSingleInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mixedTyping/queryMatchResultInfo",
        data:mixedSingleInfo
      })
    }
  // mixedSingleCompare(mixedSingleInfo){
  //   return request.xhr({
  //     url:"api_iLabSTRmix/main/selectMixedSingleCompareResult",
  //     data:mixedSingleInfos
  //   })
  // }
  // mixedSplitedCompare(mixedSplitedInfo){
  //   return request.xhr({
  //     url:"api_iLabSTRmix/main/selectMixedSplitedCompareResult",
  //     data:mixedSplitedInfo
  //   })
  // }
  // MixedSampleSuspectsDetails(detailInfo){
  //   return request.xhr({
  //     url:"api_iLabSTRmix/main/selectDatabaseRatioDetails",
  //     data:detailInfo
  //   })
  // }
}
export default new thansDetailsServers
