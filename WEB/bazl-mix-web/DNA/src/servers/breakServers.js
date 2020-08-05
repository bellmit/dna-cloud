

import request from '../xhr/xhr'

class breakServers {
    uploadPdf(data){
        return request.xhr({
            url:"api_iLabSTRmix/mix/main/strMixSplitReportFileList",
            data:data,
            type:"post"
        })
    }
    breakList(breakSampleId){
        return request.xhr({
            url:"api_iLabSTRmix/main/strMixSplit",
            data:breakSampleId
        })
    }
    // 查看已选中基因型 加入比对
    dataComparison(comparisonInfo){
        return request.xhr({
            url:"api_iLabSTRmix/mix/split/addSTRSplitCaseTask",
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
