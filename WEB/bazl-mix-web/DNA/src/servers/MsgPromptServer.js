import request from "../xhr/xhr";
class MsgPromptServer {
  // 消息内容
  MsgPromptServer(UserInfo) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/mobileNews/findList",
      data: UserInfo,
      type: "get"
    });
  }
  // 根据id删除消息接口
  DeleteMsgServer(MsgId) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/mobileNews/deleteMobileNewsById",
      data: MsgId,
      type: "get"
    });
  }
  // 获取消息条数
  GetMsgNumberSever(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/mobileNews/getUserMobileNewsNumber",
      data: Info
    });
  }
  // 全部清空接口
  DeleteAllMsgSever(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/mobileNews/deleteAllMobileNew",
      data: Info
    });
  }
  // 查看消息 并清除本条消息
  seeMsgtoDetail(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/home/getMixAndMatchResultCount",
      data: Info,
      type: "get"
    });
  }
  // 收起消息模板
  msgModel(Info) {
    return request.xhr({
      url: "api_iLabSTRmix/mix/mobileNews/neglectMassage",
      data: Info,
      type: "post"
    });
  }
}
export default new MsgPromptServer();
