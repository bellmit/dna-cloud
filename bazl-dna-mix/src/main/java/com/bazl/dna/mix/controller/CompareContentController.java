package com.bazl.dna.mix.controller;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.error.IException;
import com.bazl.dna.mix.dao.MobileNewsMapper;
import com.bazl.dna.mix.model.po.*;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/CompareQueue")
public class CompareContentController {

    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    MatchResulService matchResulService;
    @Autowired
    MobileNewsMapper mobileNewsMapper;
    @Autowired
    SplitedSampleGeneService selectByCompareQueueId;

    @PostMapping("/updateStatus")
    public ResponseData updateStatus(@RequestParam("Id") String Id,@RequestParam("Status") String Status,
                                     @RequestParam("targetCount") String targetCount) {
        if (Id == null || Status == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        CompareQueue compareQueue = new CompareQueue();
        compareQueue.setId(Id);
        compareQueue.setStatus(Status);
        compareQueue.setTargetCount(targetCount);
        compareQueueService.updateStatus(compareQueue);
        return new ResponseData(compareQueue);
    }
    @PostMapping("/updateCompareQueue")
    public ResponseData updateCompareQueue(@RequestParam("Id") String id, @RequestParam("argetCount") String argetCount) {
        if (id == null || argetCount== null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        CompareQueue compareQueue = compareQueueService.selectByPrimaryKey(id);
        if (null != compareQueue){
            compareQueue.setTargetCount(argetCount);
            compareQueue.setUpdateDatetime(new Date());
            compareQueueService.updateByPrimaryKey(compareQueue);
        }
        return new ResponseData(compareQueue);
    }
    @RequestMapping(value = "/contributorGene")
    public ResponseData contributorGene(@RequestParam("MixedSampleId") String MixedSampleId)throws IException {
        List<String> contributorGeneList = compareQueueService.contributorGene(MixedSampleId);
        return new ResponseData(contributorGeneList);
    }
    @RequestMapping(value = "/getMixSampleInfo")
    public ResponseData getMixSampleInfo(@RequestParam("MixedSampleId") String MixedSampleId)throws IException {
        mixContributorBean mixContributorBean = compareQueueService.getMixSampleInfo(MixedSampleId);
        return new ResponseData(mixContributorBean);
    }



    @PostMapping("/selectByMatch")
    public ResponseData selectByMatch(@RequestParam("CompareId") String CompareId, @RequestParam("SingleGeneId") String SingleGeneId, @RequestParam("ResultType") String ResultType) {
        if (CompareId == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        MatchResult matchResult1 = new  MatchResult();
        matchResult1.setCompareId(CompareId);
        matchResult1.setSingleGeneId(SingleGeneId);
        matchResult1.setSampleGeneResultType(ResultType);
        MatchResult  Result = matchResulService.selectByMatch(matchResult1);
        Result.setComparisonTime(null);
        return new ResponseData(Result);
    }
    @PostMapping("/updateByPrimaryKey")
    public ResponseData updateByPrimaryKey(@RequestParam("matchResult") String matchResult) {
        if (matchResult == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        MatchResult matchResult1 = JSON.parseObject(matchResult, MatchResult.class);
        return new ResponseData(matchResult1);
    }
    @PostMapping("/insert")
    public ResponseData insert(@RequestParam("match") String match) {
        if (match == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        MatchResult matchResult1 = JSON.parseObject(match, MatchResult.class);
        matchResulService.insert(matchResult1);
        return new ResponseData(matchResult1);
    }
    @PostMapping("/insertMobile")
    public ResponseData insertMobile(@RequestParam("mobileNews") String mobileNews) {
        if (mobileNews == null) {
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        MobileNews mobileNews1 = JSON.parseObject(mobileNews, MobileNews.class);
        mobileNewsMapper.insert(mobileNews1);
        return new ResponseData(mobileNews1);
    }


    @RequestMapping(value = "/selectByCompareQueueId")
    public ResponseData selectByCompareQueueId(@RequestParam("Id") String Id)throws IException {
        SplitedSampleGene splitedSampleGene = selectByCompareQueueId.selectByCompareQueueId(Id);
        return new ResponseData(splitedSampleGene);
    }


}
