
import request from '../xhr/xhr'

class MatchRecordServer {
    // 比对记录 表格数据
    Matchrecord(dataInfo){
        return request.xhr({
            url:"api_iLabSTRmix/record/findRecordTask",
            data:dataInfo
        })
    }
    // 比对记录 表格 入库比对
    InboundComparison(dataInfo){
        return request.xhr({
            url:"api_iLabSTRmix/record/updateRecordTask",
            data:dataInfo
        })
    }
    // 比对记录 重新比对
    QueryLocusListInfoForId(dataInfo){
            return request.xhr({
                url:"api_iLabSTRmix/kitLocus/queryLocusListInfoForId",
                data:dataInfo
            })
        }
}
export default new MatchRecordServer

