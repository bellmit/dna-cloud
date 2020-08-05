
import request from '../xhr/xhr'
class contrastServers {
    // 本案
    dataComparison(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/main/theCaseFastComparison",
            data:comparisonInfo
        })
    }
    // 全库
    ComparisonDetails(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/main/allCompare",
            data:comparisonInfo
        })
    }
    // 查看比中
    LookList(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/main/examineComparison",
            data:comparisonInfo
        })
    }
}
export default new contrastServers
