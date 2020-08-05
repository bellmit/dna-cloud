
import request from '../xhr/xhr'

class MatchRecordServer {
    // 比对记录 表格数据
    Matchrecord(dataInfo){
        return request.xhr({
            // url:"api_iLabSTRmix/record/findRecordTask",//旧版接口,
            url:"api_iLabSTRmix/mix/record/findRecordTask",
            data:dataInfo
        })
    }
    // 比对记录 表格 入库比对
    InboundComparison(dataInfo){
        return request.xhr({
            url:"api_iLabSTRmix/mix/record/updateRecordTask",
            data:dataInfo,
            type:"get"
        })
    }
    // 比对记录 重新比对
    QueryLocusListInfoForId(dataInfo){
            return request.xhr({
                url:"api_iLabSTRmix/mix/kitLocus/queryLocusListInfoForId",
                data:dataInfo,
                type:"get"
            })
        }
}
export default new MatchRecordServer

