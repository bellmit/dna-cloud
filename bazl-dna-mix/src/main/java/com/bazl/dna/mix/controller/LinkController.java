package com.bazl.dna.mix.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.IKitClientService;
import com.bazl.dna.mix.client.LimsServerClient;
import com.bazl.dna.mix.constants.GlobalConstants;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.SampleInfoModel;
import com.bazl.dna.mix.model.po.*;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/link")
public class LinkController extends BaseController {

    @Value("${Gen}")
    private int gen;
    @Autowired
    LimsServerClient limsServerClient;
    @Autowired
    GeneListServerClient geneListServerClient;
    @Autowired
    GeneformatUtils geneformatUtils;
    @Autowired
    IKitClientService iKitClientService;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;

    @SuppressWarnings("rawtypes")
	@Transactional
    @RequestMapping(value = "/findMixAndCaseNo", produces = "application/json;charset=UTF-8")
    public ResponseData selectByCaseNo(@RequestBody SampleInfoModel sampleInfoModel) {
        Map<String, Object> resultMap = new HashMap<>();
        String caseId = sampleInfoModel.getCaseId();
        logger.info("调用国家库案件编号--------------" + caseId + "------开始");
        try {
            ResponseData responseData=limsServerClient.getCaseInfoByCaseId(caseId);
            String caseInfoData= JSONObject.toJSONString(responseData);
            JSONObject resultObject = JSON.parseObject(caseInfoData);
            if (resultObject.getString("message")!=null ?resultObject.getString("message").contains("1"):false) {
                logger.info("该案件不存在，获取信息失败！");
                return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "该案件不存在，获取信息失败！");
            } else {
                JSONObject str = JSON.parseObject(JSONObject.toJSONString(responseData.getResult()));
                CaseInfo caseInfo = new CaseInfo();
                //案件信息
                if (str.containsKey("caseInfo")) {
                    try {
                        Map map = (Map) JSONObject.parse(str.get("caseInfo").toString());
                        String createDatetime = (String) map.get("createDatetime");
                        String caseDatetime2 = (String) map.get("caseDatetime");
                        //给定模式(这里给定的模式须与给定日期字符串格式匹配)
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date createDatetime2 = sdf2.parse(createDatetime);

                        String caseProperty = (String) map.get("caseProperty");
                        String caseStatus = (String) map.get("caseStatus");
                        String createPerson = (String) map.get("createPerson");
                        String caseBrief = (String) map.get("caseBrief");
                        String caseNoes = (String) map.get("caseNo");
                        String caseType = (String) map.get("caseType");
                        caseId = (String) map.get("caseId");
                        String caseName = (String) map.get("caseName");

                        if (caseDatetime2 != null) {
                            Date caseDatetime = sdf2.parse(caseDatetime2);
                            caseInfo.setCaseDatetime(caseDatetime);
                        }
                        caseInfo.setCreateDatetime(createDatetime2);
                        caseInfo.setCaseProperty(caseProperty);
                        caseInfo.setCaseStatus(caseStatus);
                        caseInfo.setCreatePerson(createPerson);
                        caseInfo.setCaseBrief(caseBrief);
                        caseInfo.setCaseNo(caseNoes);
                        caseInfo.setCaseType(caseType);
                        caseInfo.setId(caseId);
                        caseInfo.setCaseId(caseId);
                        caseInfo.setCaseName(caseName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //案件
                resultMap.put("caseInfo", caseInfo);

                //单一样本
                List<SingleSampleGeneVo> singleSampleGeneVoList=new ArrayList<SingleSampleGeneVo>();
                //单一审核通过基因型信息
                if (str.containsKey("singleSampleDnaGeneList")){
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        singleSampleGeneVoList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("singleSampleDnaGeneList"), SingleSampleGeneVo.class);
                        if(ListUtils.isNotNullAndEmptyList(singleSampleGeneVoList)){
                            //单一图谱
                            for (SingleSampleGeneVo sampleGeneVo : singleSampleGeneVoList) {
                                sampleGeneVo.getEntity().setId(sampleGeneVo.getEntity().getGeneId());
                                sampleGeneVo.getEntity().setReagentName(sampleGeneVo.getEntity().getPanelName());
                                sampleGeneVo.getEntity().setBoardBarcode(sampleGeneVo.getEntity().getBoardNo());
                                sampleGeneVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                                //列表页面查看详情 转换基因信息和图片字段
                                Map<String, List<String>> singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(sampleGeneVo.getEntity().getGeneInfo());
                                //判空
                                if (singleSampleGeneInfo != null) {
                                    sampleGeneVo.setGeneMap(geneSameCompareUtil.analysisGene(singleSampleGeneInfo));
                                }
                            }
                        }
                    }catch(Exception e){
                        logger.error("查询单一样本信息失败: ", e);
                    }
                }
                resultMap.put("singleSampleGenesList",singleSampleGeneVoList);

                //混合样本
                List<String> geneIdList = sampleInfoModel.getGeneIdList();
                ResponseData response = limsServerClient.getSampelInfoByGeneId(geneIdList);
                Gson gson = new Gson();
                List<MixedSampleGeneVo> mixedSampleGeneList  = gson.fromJson(gson.toJson(response.getResult()), new TypeToken<List<MixedSampleGeneVo>>(){}. getType());
                if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList)){
                    for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneList) {
                        mixedSampleGeneVo.getEntity().setId(mixedSampleGeneVo.getEntity().getGeneId());
                        mixedSampleGeneVo.getEntity().setReagentName(mixedSampleGeneVo.getEntity().getPanelName());
                        mixedSampleGeneVo.getEntity().setBoardBarcode(mixedSampleGeneVo.getEntity().getBoardNo());
                        mixedSampleGeneVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                        //列表页面查看详情 转换基因信息和图片字段
                        Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGeneVo.getEntity().getGeneInfo());
                        //判空
                        if (mixedSampleGeneInfo != null) {
                            //解析基因型信息
                            mixedSampleGeneVo.setGeneMap(geneSameCompareUtil.analysisGene(mixedSampleGeneInfo));
                        }
                        //混合图谱
                        mixedSampleGeneVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                    }
                }
                resultMap.put("mixedSampleGeneList", mixedSampleGeneList);
                logger.info("根据案件编号调取国家库:" + new ResponseData(resultMap));
            }
            return new ResponseData(resultMap);
        } catch (Exception e) {
            logger.error("根据案件编号调取国家库数据并添加失败" + e.getMessage());
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,e.toString());
        }
    }

}
