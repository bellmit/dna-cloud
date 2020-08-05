
import request from '../xhr/xhr'
class hybridSplitServers {
    // exportComparison(comparisonInfo){
    //     return request.xhr({
    //         url:"api_iLabSTRmix/main/exportComparison",
    //         // data:comparisonInfo
    //     })
    // }

    // 快比对拆分记录 数据接口
    recording(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/split/findTaskList",
            data:comparisonInfo
        })
    }
    // 快速比对记录 删除接口
    deleteSpli(deleteInfo){
        return request.xhr({
            url:"api_iLabSTRmix/split/deleteSplitTask",
            data:deleteInfo
        })
    } 
    // 拆分入库
    allCompare(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/split/addSplit",
            data:comparisonInfo
        })
    }
}
export default new hybridSplitServers
