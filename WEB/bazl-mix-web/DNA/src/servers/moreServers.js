
import request from '../xhr/xhr'

class moreServers {
    dataMoreList(urlInfo,dataInfo){
        return request.xhr({
            url:"api_iLabSTRmix/"+urlInfo,
            data:dataInfo
        })
    }
}
export default new moreServers

