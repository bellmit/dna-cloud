package com.bazl.dna.database.core.controller.web.rapid;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.rabbitmq.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.vo.QuickCompareResultRelativeVo;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultRelativeService;
import com.bazl.dna.database.utils.ListUtils;

/**
 * 快速比对Controller
 */
@RestController
@RequestMapping("/rapid/relative")
public class RapidRelativeComparisonController extends BaseController {

    @Autowired
    QuickCompareQueueService quickCompareQueueService;
    @Autowired
    QuickCompareResultRelativeService quickCompareResultRelativeService;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    private ISendQuickCompareService sendQuickCompareService;

    /*
        *      快速比对--亲缘比对--提交比对
        * */
    @RequestMapping(value = "/submitSTRCompareQueue", produces = "application/json;charset=UTF-8")
    public ResponseData submitSTRCompareQueue(@CurrentUser AuthUser user, @RequestBody QuickCompareQueue queue) throws Exception {
        if (queue != null && queue.getPendingSampleaGeneInfo() != null){
            //获取用户信息
            queue.setQueueStatus(Constants.COMPARE_STATUS_NO);//比对状态
            queue.setCreatePersonId(user.getId());
            queue.setCreatePersonName(user.getUsername());
            queue.setCreateDatetime(LocalDateTime.now());
            quickCompareQueueService.insert(queue);
            
            // 发送单亲比对
            if (Constants.COMPARE_MODE_RELATIVE_SINGLE_CONJOINED == queue.getCompareMode()) {
            		sendQuickCompareService.sendQuickRelativeSingleConjoinedCompare(queue);
            }
            // 发送双亲比对
            if (Constants.COMPARE_MODE_RELATIVE_THREE_CONJOINED == queue.getCompareMode()) {
            		sendQuickCompareService.sendQuickRelativeThreeConjoinedCompare(queue);
            }
            logger.info("Send Compare Queue Relative:", queue.getId());
            queue.setQueueStatus(Constants.COMPARE_STATUS_WAITING);
            queue.setUpdateDatetime(LocalDateTime.now());
            quickCompareQueueService.updateById(queue);
            
            Integer id = queue.getId();
            return new ResponseData(id);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *   快速比对   亲缘比对---提交比对后结果列表
    * */
    @RequestMapping(value = "/findResultStR", produces = "application/json;charset=UTF-8")
    public ResponseData findResultStRList(Integer id) throws Exception {
        Map<String, Object> resultObj = new HashMap<>();
        if (id != null){
            List<QuickCompareResultRelativeVo> quickRelativeVos = quickCompareResultRelativeService.selectByQuickCompareQueueId(id);
            if (ListUtils.isNotNullAndEmptyList(quickRelativeVos)){
                for (QuickCompareResultRelativeVo relativeVo : quickRelativeVos){
                    //案件人员类型
                    String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PERSON_TYPE, relativeVo.getPersonType());
                    relativeVo.setPersonTypeName(personType);
                    /*//获取基因信息
                    QuickCompareQueue quickCompareQueue = quickCompareQueueService.selectByPrimaryKey(relativeVo.getQuickCompareQueueId());
                    String matchedGeneDetails = relativeVo.getMatchedGeneDetails();
                    if (quickCompareQueue != null && StringUtils.isNotBlank(matchedGeneDetails)){
                        String tarGene = GeneTransFormUtils.geneExchange(matchedGeneDetails);
                        String srcGene = null;
                        if (relativeVo.getSampleFlag().equals("1")){
                            srcGene = GeneTransFormUtils.geneExchange(quickCompareQueue.getPendingSampleaGeneInfo());
                        }else {
                            srcGene = GeneTransFormUtils.geneExchange(quickCompareQueue.getPendingSamplebGeneInfo());
                        }
                        Map<String, Object> map = GeneTransFormUtils.compareResultFlag(srcGene, tarGene, 0);
                        relativeVo.setGeneMap(map);
                    }*/
                }
            }
            QuickCompareQueue compareQueue = quickCompareQueueService.selectByPrimaryKey(id);
            String queueStatus = null;//比对完成状态
            if (null !=compareQueue && StringUtils.isNotBlank(compareQueue.getQueueStatus())){
                if (compareQueue.getQueueStatus().equals(Constants.FLAG_TRUE)){
                    queueStatus ="1";
                }else if (compareQueue.getQueueStatus().equals(Constants.FLAG_FALSE)){
                    queueStatus ="0";
                }
            }
            //总比中条数
            int count = quickCompareResultRelativeService.selectByQueueIdCount(id);
            resultObj.put("quickResultList",quickRelativeVos);
            resultObj.put("queueStatus",queueStatus);//比对完成状态
            resultObj.put("count",count);
            return new ResponseData(resultObj);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

}
