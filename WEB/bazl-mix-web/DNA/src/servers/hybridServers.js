
import request from '../xhr/xhr'

class hybridServers {
  dataList(hybridInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/main/autoanalysisMixedAndSingleCompare",
      data:hybridInfo,
      // type:""
    })
  }
  dataLeft(dataLeftInfo){
    return request.xhr({
      url:"api_iLabSTRmix/main/selectMatchResultMixSingleByMixedSampleGeneId",
      data:dataLeftInfo
    })
  }
  // 自动拆分
  dataSplit(splitInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/main/automaticSplit",
      data:splitInfo
    })
  }
  // 拆分样本加入比对 接口
  dataStorage(storageInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/kitLocus/addLocusListInfoForkitId",
      data:storageInfo
    })
  }
  dataStorageHBD(storageHBDInfo){
    return request.xhr({
      url:"api_iLabSTRmix/main/mixedSampleGeneCompareQueue",
      data:storageHBDInfo
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
  // 人员条件数据接口
  GetPersonnelData(deleteInfo){
      return request.xhr({
          url:"api_iLabSTRmix/mix/kitLocus/selectPersonCategory",
          data:deleteInfo
      })
  }
  // 地区条件数据接口
  GetCitysData(deleteInfo){
      return request.xhr({
          url:"api_iLabSTRmix/mix/kitLocus/selectProvinceAndCity",
          data:deleteInfo,
          type: "get"
      })
  } 
    // 查看拆分样本并下一步
    ViewAndNext(deleteInfo){
      return request.xhr({
          url:"api_iLabSTRmix/mix/split/findCompareTaskList",
          data:deleteInfo,
          type:"get"
      })
  }
  //  查看并拆分下一步 删除表格队列任务
  Datequeue(deleteInfo){
        return request.xhr({
            url:"api_iLabSTRmix/mix/kitLocus/deleteLocusListInfoForId",
            data:deleteInfo,
            type:"get"
        })
    }
  // 查看并拆分下一步 查看详情接口
  SeeDetails(deleteInfo){
    return request.xhr({
        url:"api_iLabSTRmix/mix/split/findGeneTask",
        data:deleteInfo,
        type:"get"
    })
  }
  // 比中数量
  SelectCount(listInfo){
    return request.xhr({
      url:"api_iLabSTRmix/mix/split/selectCount",
      data:listInfo,
      type:"get"
    })
  }  
    // 比中数量
    onStrmix(listInfo){
      return request.xhr({
        url:"http://localhost:928/StartRun?ProgramName=baidunetdisk.exe",
        data:listInfo
      })
    }   
    //根据样本编号查询单一样本信息
    getDataBaseBySampleNo(listInfo){
      return request.xhr({
        url:"api_iLabSTRmix/mix/kitLocus/selectSingleGeneBySampleNo",
        data: listInfo,
        type: 'get'
      })
    } 
    // getGeneNames(info){
    //   return request.xhr({
    //     url: 'api_iLabSTRmix/mix/kitLocus/queryLocusListInfoForkitId',
    //     data: info,
    //     type: 'get'
    //   })
    // }
}

export default new hybridServers()
