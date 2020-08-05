package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.vo.CompareQueueVo;
import com.bazl.dna.mix.rebbitmq.ISendMixCompareService;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.util.DateTools;
import com.bazl.dna.util.RequestUtils;

/**
 * Created by Administrator on 2019/12/18.
 */
@RestController
@RequestMapping("/record")
public class RecordController extends BaseController {

    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    MatchResulService matchResulService;
    @Autowired
    ISendMixCompareService sendMixCompareService;

    /*
     *     比对记录---比对结果列表
     * */
    @RequestMapping(value = "/findRecordTask", produces = "application/json;charset=UTF-8")
    public ResponseData findRecordTask(@RequestBody CompareQueueVo query) throws Exception {
        if (0 != query.getPage()){
            // 获得用户对象
            AuthUser user = RequestUtils.getAuthUser();
            Map<String, Object> resultMap = new HashMap<>();
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(query.getPage());
            //解决时间无时分秒造成的00:00:00查询
            if (null != query.getEndDatetime()){
                query.setEndDatetime(DateTools.addDateByDay(query.getEndDatetime(),1));//增加一天
            }
            if (user != null && user.getId() != null){
                query.getEntity().setCreatePerson(user.getId());
            }
//            query = resetLabCompareQueueVo(query);
            List<CompareQueue> compareQueueList = compareQueueService.selectByQueueVoList(query, pageInfo);
            if (ListUtils.isNotNullAndEmptyList(compareQueueList)){
                for (CompareQueue compareQueue : compareQueueList){
                    //时间格式转化
                    if (null != compareQueue.getCreateDatetime()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateStr = sdf.format(compareQueue.getCreateDatetime());
                        compareQueue.setDatetime(currentDateStr);
                    }
                    //查询比中结果数量
                    int count = matchResulService.selectByMatchCount(compareQueue.getId());
                    compareQueue.setSameCount(Integer.toString(count));
                    //查询拆分数量
                    int spcount = 0;
                    if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        CompareQueue queue = new CompareQueue();
                        queue.setMixedSampleId(compareQueue.getMixedSampleId());
                        queue.setQueueType(Constants.QUEUE_TYPE_02);
//                        queue.setQueueFlag(Constants.QUEUE_FLAG_1);
                        List<CompareQueue> compareQueueList1 = compareQueueService.selectQueueList(queue);
                        spcount = compareQueueList1.size();
                    }
                    compareQueue.setSplitCount(Integer.toString(spcount));
                }
            }
            int count = compareQueueService.selectByQueueVoCount(query);
            pageInfo.setAllRecordCount(count);
            resultMap.put("pageInfo", pageInfo);
            resultMap.put("infoList", compareQueueList);
            return new ResponseData(resultMap);
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *     比对记录---入库比对
    * */
    @RequestMapping(value = "/updateRecordTask", produces = "application/json;charset=UTF-8")
    public ResponseData updateRecordTask(String id) throws IOException {
        if(id != null){
            //修改比对状态重新比对
            CompareQueue compareQueue = compareQueueService.selectByPrimaryKey(id);
            if (compareQueue != null){
                compareQueue.setStatus(Constants.MATCH_STATUS_03);
                compareQueueService.updateByPrimaryKey(compareQueue);
                compareQueue.setDataType(compareQueue.getServeNo());
                sendMixCompareService.sendCompare(JSON.toJSONString(compareQueue));
                return new ResponseData(1,"修改成功！");
            }else{
                logger.error("根据id查询比对队列为空！");
                return new ResponseData(ErrorCodes.ERR_PARAM, "根据id查询比对队列为空！");
            }
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *     比对记录---有效比中
    * */
    @RequestMapping(value = "/effective", produces = "application/json;charset=UTF-8")
    public ResponseData effectiveTask(@RequestBody List<String> idList) throws IOException {
        if(ListUtils.isNotNullAndEmptyList(idList)){
            for (String id : idList){
                MatchResult matchResult = matchResulService.selectByPrimaryKey(id);
                if (matchResult != null){
                    //有效状态
                    matchResult.setEffectFlag(Constants.EFFECT_FLAG_1);
                    matchResulService.updateByPrimaryKey(matchResult);
                }
            }
            return new ResponseData(1,"修改成功！");
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *     比对记录---无效比中
    * */
    @RequestMapping(value = "/noneffective", produces = "application/json;charset=UTF-8")
    public ResponseData noneffectiveTask(@RequestBody List<String> idList) throws IOException {
        if(ListUtils.isNotNullAndEmptyList(idList)){
            for (String id : idList){
                MatchResult matchResult = matchResulService.selectByPrimaryKey(id);
                if (matchResult != null){
                    //无效状态
                    matchResult.setEffectFlag(Constants.EFFECT_FLAG_0);
                    matchResulService.updateByPrimaryKey(matchResult);
                }
            }
            return new ResponseData(1,"修改成功！");
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }


    public static CompareQueueVo resetLabCompareQueueVo(CompareQueueVo query) {
        if (null == query.getStartDatetime()) {
            query.setStartDatetime(null);
        } else {
            query.setStartDatetime(query.getStartDatetime());
        }

        if (null == query.getEndDatetime()) {
            query.setEndDatetime(null);
        } else {
            query.setEndDatetime(query.getEndDatetime());
        }

        if (StringUtils.isBlank(query.getSampleNo())) {
            query.setSampleNo(null);
        } else {
            query.setSampleNo(query.getSampleNo().trim());
        }

        return query;
    }

}
