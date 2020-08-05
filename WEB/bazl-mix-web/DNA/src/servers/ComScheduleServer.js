
import request from '../xhr/xhr'

class ComScheduleServer {
    ComSchedule(pageInfo){
    return request.xhr({
    //   url:"api_iLabSTRmix/main/selectMixedDateBaseGeneList",
      data:pageInfo
    })
  }
}

export default new ComScheduleServer
