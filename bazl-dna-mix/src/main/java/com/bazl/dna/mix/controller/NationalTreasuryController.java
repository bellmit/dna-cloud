package com.bazl.dna.mix.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.CaseInfoServerClient;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.IKitClientService;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.GlobalConstants;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.po.CaseInfo;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.service.CaseInfoService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.service.PersonInfoService;
import com.bazl.dna.mix.service.SampleInfoService;
import com.bazl.dna.mix.service.SingleSampleGeneService;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;

@RestController
@RequestMapping("/nationalTreasury")
public class NationalTreasuryController extends BaseController {

    @Autowired
    MixedSampleGeneService mixedSampleGeneService;

    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    SampleInfoService sampleInfoService;

    @Autowired
    PersonInfoService personInfoService;

    @Autowired
    SingleSampleGeneService singleSampleGeneService;

    @Autowired
    GeneformatUtils geneformatUtils;
    //基因信息转换条件
    @Value("${Gen}")
    private int gen;
//    @Value("${locusNameUrl}")
//    private String locusNameUrl; nationalTreasury/selectByLocusName
//    @Value("${caseAnalystUrl}")
//    private String caseAnalystUrl; nationalTreasury/nationalTreasury/getCaseInfoByCaseNo

    @Value("${KitNameToLocusName}")
    private String KitNameToLocusName;

    @Autowired
    CaseInfoServerClient caseInfoServerClient;
    @Autowired
    GeneListServerClient geneListServerClient;
    @Autowired
    SampleDataServicr sampleDataServicr;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    IKitClientService iKitClientService;


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
    @GetMapping(value = "/selectByCaseNo", produces = "application/json;charset=UTF-8")
    public ResponseData selectByCaseNo(HttpServletRequest request, HttpServletResponse response,@RequestParam("caseNo") String caseNo) {
        Map<String, Object> resultMap = new HashMap<>();

        logger.info("调用国家库案件编号--------------" + caseNo + "------开始");
        try {
            String no = caseNo.toUpperCase();
            Map<String, String> querymap = new HashMap<String, String>();
            querymap.put("caseNo",no);
            ResponseData responseData=caseInfoServerClient.getCaseInfoByCaseNo(caseNo.toUpperCase());
            String caseInfoData=JSONObject.toJSONString(responseData);
            JSONObject resultObject = JSON.parseObject(caseInfoData);
            if (resultObject.getString("message")!=null ?resultObject.getString("message").contains("1"):false) {
                logger.info("该案件不存在，获取信息失败！");
                return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "该案件不存在，获取信息失败！");
            } else {
                JSONObject str = JSON.parseObject(JSONObject.toJSONString(responseData.getResult()));
                CaseInfo caseInfo = new CaseInfo();
                List<SampleInfo> sampleInfoList = new ArrayList<>();
                List<SingleSampleGene> singleSampleGenesList = new ArrayList<>();
                List<MixedSampleGene> mixedSampleGeneList = new ArrayList<>();
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
                        String caseId = (String) map.get("id");
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

                //样本信息
                if (str.containsKey("sampleInfoList")) {
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<SampleInfo> list2 = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("sampleInfoList"), SampleInfo.class);
                        if(list2.size() >0){
                            for(int j=0;j< list2.size();j++){
                                SampleInfo sampleInfoes = list2.get(j);
                                sampleInfoList.add(sampleInfoes);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


                //单一审核通过基因型信息
                if (str.containsKey("singleSampleDnaGeneList")){
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<SingleSampleGene> singleSampleGeneList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("singleSampleDnaGeneList"), SingleSampleGene.class);
                        if(ListUtils.isNotNullAndEmptyList(singleSampleGeneList)){
                            for(SingleSampleGene sampleGene : singleSampleGeneList){
                                singleSampleGenesList.add(sampleGene);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }



                //混合审核通过基因型信息
                if (str.containsKey("mixSampleDnaGeneList")){
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<MixedSampleGene> mixAuditedGeneList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("mixSampleDnaGeneList"), MixedSampleGene.class);
                        if(ListUtils.isNotNullAndEmptyList(mixAuditedGeneList)){
                            for(MixedSampleGene sampleGene : mixAuditedGeneList){
                                mixedSampleGeneList.add(sampleGene);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                List<Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
                if (gen == 1){
                    logger.info("查询基因座信息开始----------------------");
                    ResponseData responseData1 = geneListServerClient.selectByLocusName();
                    String locusInfo = JSONObject.toJSONString(responseData1.getResult());
                    List<HashMap> llocus = JSON.parseArray(locusInfo, HashMap.class);
                    for (Object object : llocus ) {
                        listLocusName.add((Map<String, String>) object);
                    }
                    logger.info("查询基因座信息结束----------------------");
                }


                //案件
                resultMap.put("caseInfo", caseInfo);

                //单一
                List<SingleSampleGeneVo> singleSampleGeneVoList=new ArrayList<SingleSampleGeneVo>();
                SingleSampleGeneVo singleSampleGeneVo=null;
                for(SingleSampleGene singleSampleGene : singleSampleGenesList){
                    String geneInfoSample = null;
                    String gene = null;
                    if (gen == 1){
                        logger.info("基因信息转换开始----------------------");
                        geneInfoSample = geneformatUtils.geneFormatConversionNew(singleSampleGene.getGeneInfo(), listLocusName);
                        String reagentKitId = singleSampleGene.getReagentName();
                        gene = geneFormatConversionGj(geneInfoSample, reagentKitId);
                        singleSampleGene.setGeneInfo(gene);
                        logger.info("基因信息转换结束----------------------");
                    }else {
                        gene = singleSampleGene.getGeneInfo();
                        singleSampleGene.setGeneInfo(gene);
                    }


                    //关联样本信息字段 关联案件信息字段
                    for(SampleInfo s:sampleInfoList){
                        if(s.getId().equals(singleSampleGene.getSampleId())){
                            singleSampleGeneVo=new SingleSampleGeneVo();
                            singleSampleGeneVo.setEntity(singleSampleGene);
                            singleSampleGeneVo.setSampleNo(s.getSampleNo());
                            singleSampleGeneVo.setSampleName(s.getSampleName());
                            singleSampleGeneVo.setCaseNo(caseInfo.getCaseNo());
                            singleSampleGeneVo.setCaseName(caseInfo.getCaseName());
                            singleSampleGeneVo.setSampleType(s.getSampleType());
                            singleSampleGeneVo.setSampleTypeName(getConvertSampleType(s.getSampleType()));
                            singleSampleGeneVoList.add(singleSampleGeneVo);
                        }
                    }


                    //列表页面查看详情 转换基因信息和图片字段
                    Map<String, List<String>> singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());
                    //判空
                    if (singleSampleGeneInfo != null) {
                        singleSampleGeneVo.setGeneMap(geneSameCompareUtil.analysisGene(singleSampleGeneInfo));
                    }
                    //单一图谱
                    singleSampleGeneVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                }
                resultMap.put("singleSampleGenesList",singleSampleGeneVoList);


                //混合
                List<MixedSampleGeneVo> mixedSampleGeneList1 = new ArrayList<MixedSampleGeneVo>();
                MixedSampleGeneVo mixedSampleGeneVo=null;
                for (MixedSampleGene mixedSampleGene : mixedSampleGeneList) {
                    String geneInfoMix = null;
                    String gene = null;
                    if (gen == 1){
                        logger.info("基因信息转换开始----------------------");
                        geneInfoMix = geneformatUtils.geneFormatConversionNew(mixedSampleGene.getGeneInfo(),listLocusName);
                        String reagentKitId = mixedSampleGene.getReagentName();
                        gene = geneFormatConversionGj(geneInfoMix, reagentKitId);
                        mixedSampleGene.setGeneInfo(gene);
                        logger.info("基因信息转换结束----------------------");
                    }else {
                        gene = mixedSampleGene.getGeneInfo();
                        mixedSampleGene.setGeneInfo(gene);
                    }

                    //关联样本信息字段 关联案件信息字段
                    for(SampleInfo s:sampleInfoList){
                        if(s.getId().equals(mixedSampleGene.getSampleId())){
                            mixedSampleGeneVo=new MixedSampleGeneVo();
                            mixedSampleGeneVo.setEntity(mixedSampleGene);
                            mixedSampleGeneVo.setSampleNo(s.getSampleNo());
                            mixedSampleGeneVo.setSampleName(s.getSampleName());
                            mixedSampleGeneVo.setCaseNo(caseInfo.getCaseNo());
                            mixedSampleGeneVo.setCaseName(caseInfo.getCaseName());
                            mixedSampleGeneVo.setSampleType(s.getSampleType());
                            mixedSampleGeneVo.setSampleTypeName(getConvertSampleType(s.getSampleType()));
                            mixedSampleGeneList1.add(mixedSampleGeneVo);
                        }
                    }
                    //列表页面查看详情 转换基因信息和图片字段
                    Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());
                    //判空
                    if (mixedSampleGeneInfo != null) {
                        //解析基因型信息
                        mixedSampleGeneVo.setGeneMap(geneSameCompareUtil.analysisGene(mixedSampleGeneInfo));
                    }
                    //混合图谱
                     mixedSampleGeneVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                }
                resultMap.put("mixedSampleGeneList", mixedSampleGeneList1);
                logger.info("根据案件编号调取国家库:" + new ResponseData(resultMap));
            }
            return new ResponseData(resultMap);
        } catch (Exception e) {
            logger.error("根据案件编号调取国家库数据并添加失败" + e.getMessage());
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,e.toString());
        }
    }


    @SuppressWarnings("rawtypes")
	public String geneFormatConversionGj(String gene, String reagentKitName) {
            String geneInfos = null;
            try {
//                Map reagentNameMap = new LinkedHashMap();
//                reagentNameMap.put("reagentKitName", reagentKitName);
//                String sendRequest = HttpClientUtil.doGet(KitNameToLocusName, reagentNameMap);

                ResponseData responseData=iKitClientService.selectByKitNameToLocusName(reagentKitName);
                if(responseData.getCode() == 1 && responseData.getResult() != null ){
                    List<Map> resultUserVOS = JSON.parseArray(JSONObject.toJSONString(responseData.getResult()), Map.class);
                    Map<Object, Object> GeneInfo = new LinkedHashMap<>();
                    for (int i = 0; i < resultUserVOS.size(); i++) {
                        String locusName = (String) resultUserVOS.get(i).get("locusName");
                        Map<String, Object> jsonToMap = JSONObject.parseObject(gene);
                        if ( jsonToMap.containsKey(locusName) == true) {
                            GeneInfo.put(locusName, jsonToMap.get(locusName));
                        }
                    }
                    geneInfos = JSON.toJSONString(GeneInfo);
                }else {
                    geneInfos = gene;
                }
//                JSONObject jsonObject = JSONObject.parseObject(sendRequest);
//                String data = jsonObject.getString("data");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("=======国家库基因信息转换失败========");
            }
            return geneInfos;
    }

    /**
     * dna_lib表里的panelname转换为panel表中的panelname   已知的
     */
    public String getConvertSampleType(String sampleType){
        if("08".equals(sampleType)){
            return "肌肉(组织)";
        }else if("09".equals(sampleType)){
            return "组织";
        }else if("10".equals(sampleType)){
            return "指纹";
        }else if("11".equals(sampleType)){
            return "脱落细胞";
        }else if("99".equals(sampleType)){
            return "其他";
        }else if("01".equals(sampleType)){
            return "血斑/血液";
        }else if("02".equals(sampleType)){
            return "精斑";
        }else if("03".equals(sampleType)){
            return "毛发";
        }else if("04".equals(sampleType)){
            return "指甲";
        }else if("05".equals(sampleType)){
            return "烟蒂";
        }else if("06".equals(sampleType)){
            return "唾液(斑)";
        }else if("07".equals(sampleType)){
            return "骨骼/牙齿";
        }else {
            return sampleType;
        }
    }



}