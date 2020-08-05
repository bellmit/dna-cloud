
import request from '../xhr/xhr'
class MsgPromptServer {
    MsgPromptServer(UserInfo){
        return request.xhr({
            url:"api_iLabSTRmix/MobileNews/getMessage",
            data:UserInfo
        })
    }
   DeleteMsgServer(MsgId){
        return request.xhr({
            url:"api_iLabSTRmix/MobileNews/DeleteMobileNewsById",
            data:MsgId
        })
    }
    GetMsgNumberSever(Info){
        return request.xhr({
            url:"api_iLabSTRmix/MobileNews/getUserMobileNewsNumber",
            data:Info
        })
    }
    // 全部清空接口
    DeleteAllMsgSever(Info){
        return request.xhr({
            url:"api_iLabSTRmix/MobileNews/DeleteAllMobileNew",
            data:Info
        })
    }
}
export default new MsgPromptServer
