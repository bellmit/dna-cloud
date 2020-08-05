import request from "../xhr/xhr"

class quickMatchServers {
  querykitLocusListInfo(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/kitLocus/querykitLocusListInfo",
      data: listInfo
    });
  }
  querySimleNofoForkitId(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/kitLocus/querySimleNofoForkitId",
      data: listInfo
    });
  }
  queryLocusListInfoForkitId(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/kitLocus/queryLocusListInfoForkitId",
      data: listInfo
    });
  }
  //录入基因信息后-加入比对列表
  addLocusListInfoForkitId(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/addLocusListInfoForkitId",
      data: listInfo
    });
  }
  deleteLocusListInfoForId(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/deleteLocusListInfoForId",
      data: listInfo,
      type: "get"
    });
  }
  // 提交比对
  updateLocusListInfoForId(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/updateLocusListInfoForId",
      data: listInfo
    });
  }
  // 3.1	样本编号模糊查询 数据库检索
  getSelectSampleNoList(listInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/dnaParting/selectSampleNoList",
      data: listInfo,
      type: "get"
    });
  }
  //3.2	试剂盒查询接口
  getQuerykitLocusListInfo(info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/querykitLocusListInfo",
      data: info,
      type: "get"
    });
  }
  //3.3	生成混合id
  getSampleMixId(info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/dnaParting/foundMixedSampleGeneId",
      data: info,
      type: "get"
    });
  }
  //3.4	通过试剂盒id查询基因座
  getGeneNameByKitId(info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/queryLocusListInfoForkitId",
      data: info,
      type: "get"
    });
  }
  // 远程搜索
  getRemoteGeneName(info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/kitLocus/queryVagueLocusName",
      data: info,
      type: "get"
    });
  }
  // 手工录入codis导入
  getCodisFile(info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/codis/uploadCodis",
      data: info
    });
  }
  removePic(info) {
    //删除峰图
    return request.xhr({
      url: "api_iLabSTRmix/mix/main/deleteGenePictureById",
      data: info,
      type: "get"
    });
  }
  removeContributor(info) {
    //删除贡献者
    return request.xhr({
      url: "api_iLabSTRmix/mix/dnaParting/deleteContributorInfo",
      data: info,
      type: "get"
    });
  }
  removeContributor(info){  //删除贡献者
    return request.xhr({
      url: 'api_iLabSTRmix/mix/dnaParting/deleteContributorInfo',
      data: info,
      type: 'get'
    })
  }
}
export default new quickMatchServers();
