

import request from '../xhr/xhr'

class breakServers {
    breakList(breakSampleId){
        return request.xhr({
            url:"api_iLabSTRmix/main/strMixSplit",
            data:breakSampleId
        })
    }
    // 查看已选中基因型
    dataComparison(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/split/addSTRSplitCaseTask",
            // url:"/record/findRecordTask",
            data:comparisonInfo
        })
    }
  // 快速比对记录数据接口
  recording(recordingInfo){
    return request.xhr({
        url:"api_iLabSTRmix/split/findSplitCaseTaskList",
        data:recordingInfo
        })
    }
 // 快速比对记录 删除接口
  deleteSpli(deleteInfo){
    return request.xhr({
        url:"api_iLabSTRmix/split/deleteSplitCaseTask",
        data:deleteInfo
    })
  } 
}

export default new breakServers
