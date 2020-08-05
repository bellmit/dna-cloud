package com.bazl.dna.lims.connector.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.connector.model.po.LimsCaseInfo;
import com.bazl.dna.lims.connector.model.vo.LimsSampleGeneVo;
import com.bazl.dna.lims.connector.service.LimsCaseInfoService;
import com.bazl.dna.lims.connector.service.LimsSampleGeneService;
import com.bazl.dna.lims.connector.utils.ListUtils;

@RestController
@RequestMapping("/mixConnector")
public class MixController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LimsCaseInfoService limsCaseInfoService;
    @Autowired
    private LimsSampleGeneService limsSampleGeneService;

    @RequestMapping(value = "/caseInfoAndSampelInfo", produces = "application/json;charset=UTF-8")
    public ResponseData caseInfoAndSampelInfoList(@RequestParam("caseId")String caseId){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //案件信息
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
            resultMap.put("caseInfo",limsCaseInfo);
            //查询单一样本信息
            List<LimsSampleGeneVo> limsSampleGenes = limsSampleGeneService.selectListVoByCaseId(caseId);
            resultMap.put("singleSampleDnaGeneList",limsSampleGenes);
        } catch (Exception e) {
            logger.error("查询相关案件信息失败: ", e);
        }
        return new ResponseData(resultMap);
    }

    @RequestMapping(value = "/findMixSampelInfo", produces = "application/json;charset=UTF-8")
    public ResponseData findMixSampelInfoList(@RequestParam("geneIdList") List<String> geneIdList){
        List<LimsSampleGeneVo> limsSampleGenes = new ArrayList<>();
        try {
            if (ListUtils.isNotNullAndEmptyList(geneIdList)){
                for (String geneId : geneIdList){
                    LimsSampleGeneVo limsSampleGeneVo = limsSampleGeneService.selectVoListByGeneId(geneId);
                    limsSampleGenes.add(limsSampleGeneVo);
                }
            }else {
                return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "传入参数为空！");
            }
        } catch (Exception e) {
            logger.error("查询lims混合样本信息失败: ", e);
        }
        return new ResponseData(limsSampleGenes);
    }
}
