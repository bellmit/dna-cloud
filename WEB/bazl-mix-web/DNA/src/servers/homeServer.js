import request from '../xhr/xhr'
class HomeServer {
  homeList() {
    return request.xhr({
      url: "api_iLabSTRmix/main/latestMatchedSuspectList",
      data: {
        lastestCount: 15
      }
    })
  }
  // ????? 嫌疑人总数
  homePerson() {
    return request.xhr({
      url: "api_iLabSTRmix/main/latestMatchedSuspectCount",
      data: {
        lastestCount: 15
      }
    })
  }
  dataSerial() {
    return request.xhr({
      url: "api_iLabSTRmix/main/latestSerialCaseMatchedSuspectList",
      data: {
        lastestCount: 15
      }
    })
  }
  dataSerialCount() {
    return request.xhr({
      url: "api_iLabSTRmix/main/serialCaseMixedSampleCount"
    })
  }
  dataSelectCount() {
    return request.xhr({
      url: "api_iLabSTRmix/main/selectMixedSampleGeneCount"
    })
  }
  dataLatest() {
    return request.xhr({
      url: "api_iLabSTRmix/main/latestQualityMixedSampleList",
      data: {
        lastestCount: 15
      }
    })
  }
  dataLatestCount() {
    return request.xhr({
      url: "api_iLabSTRmix/main/selectMixedSampleQualityPersonnelCount"
    })
  }
  dataDetail(detailInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/main/selectMixedSampleSuspectsDetails",
      data: detailInfo
    })
  }
  allCompare(allInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/comparison/findcomparisonList",
      data: allInfo
    })
  }
  allCompareDetails(allInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/comparison/findresultList",
      data: allInfo,
      // type:'get'
    })
  }
  allCompareDetailsList(allInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/comparison/details",
      data: allInfo
    })
  }
  allCompareDelete(allInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/comparison/deleteById",
      data: allInfo
    })
  }
  // 柱形图接口
  BarTypeData(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/matchResultSortCount",
      data: Info
    })
  }
  // 折线图接口
  LineDNAData(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/sampleCount",
      data: Info
    })
  }
  // 饼形图接口
  RadiusData(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/home/compareCount",
      data: Info
    })
  }
  // 最新比中详情一览 findNewestGene
  findNewestList(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/findNewestList",
      data: Info
    })
  }
  // 查看详情
  findNewestGene(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/findNewestGene",
      data: Info,
      type: "get"
    })
  }
  // 环形图数据
  radiusData(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/getMixAndMatchResultCount",
      data: Info,
      type: "get"
    })
  }
}
export default new HomeServer
