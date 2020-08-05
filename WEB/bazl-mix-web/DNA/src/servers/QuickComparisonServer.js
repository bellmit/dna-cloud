
import request from '../xhr/xhr'
class QuickComparisonServer {
    dataComparison(comparisonInfo){
        return request.xhr({
            // url:"http://192.168.0.128:8090/main/theCaseFastComparison", // 
            // url:"/api_iLabSTRmix/main/theCaseFastComparison",
            url:"api_iLabSTRmix/main/theCaseFastComparison",
            data:comparisonInfo
        })
    }
    ComparisonDetails(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/main/allCompare",
            data:comparisonInfo
        })
    }
}
export default new QuickComparisonServer
  