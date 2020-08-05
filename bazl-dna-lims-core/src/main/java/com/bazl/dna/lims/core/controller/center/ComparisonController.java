package com.bazl.dna.lims.core.controller.center;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.compare.GeneMixCompareUtil;
import com.bazl.dna.lims.core.compare.GeneRelativeCompareUtil;
import com.bazl.dna.lims.core.compare.GeneSameCompareUtil;
import com.bazl.dna.lims.core.compare.model.CaseSelfCompareParams;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchResult;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchResultRecord;
import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.dao.BlendRelationMapper;
import com.bazl.dna.lims.core.datamodel.CaseCompareResultGroup;
import com.bazl.dna.lims.core.datamodel.CaseCompareResultInfoModel;
import com.bazl.dna.lims.core.model.po.*;
import com.bazl.dna.lims.core.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.core.service.*;
import com.bazl.dna.lims.core.utils.CommonUtils;
import com.bazl.dna.lims.core.utils.DataFormat;
import com.bazl.dna.lims.core.utils.DictUtil;
import com.bazl.dna.lims.core.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.bazl.dna.lims.core.controller.center.QueryCompareResultController.distinctByKey;

/**
 * @author wanghaiyang
 * @date 2019/4/10.
 */
@Controller
@RequestMapping("/center")
public class ComparisonController extends BaseController {

    @Autowired
    LimsConfigure limsConfigure;

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    MatchAuditedGeneService matchAuditedGeneService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    GeneRelativeCompareUtil geneRelativeCompareUtil;
    @Autowired
    RaceService raceService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    CompareSameResultService compareSameResultService;
    @Autowired
    CompareRelativeResultService compareRelativeResultService;
    @Autowired
    ExaminerService examinerService;
    @Autowired
    PanelService panelService;
    @Autowired
    BlendRelationMapper blendRelationMapper;
    /**
     * 进入本案比对页面
     * 包含：
     *  同一比中列表              sameMatchedGroupList
     *  亲缘比中列表              compareRelativeResultList
     *  未比中检材列表     同型、亲缘均无比中的STR检材列表
     *  Y-STR检材列表     检出Y-STR基因分型的检材列表
     *  混合STR检材列表    检出混合STR基因分型的检材列表
     *  未检出检材列表     未检出基因分型的检材列表（STR/Y-STR/混合STR均没有）
     * @param request
     * @return
     */
    @RequestMapping("/enterCaseCompare")
    public ModelAndView enterCaseCompare(HttpServletRequest request, String caseId, CaseSelfCompareParams caseSelfCompareParams, String isAgainMatch) {
        ModelAndView view = new ModelAndView();

        if (caseSelfCompareParams == null
                || caseSelfCompareParams.getSameCompareMatchLimit() == 0) {
            caseSelfCompareParams = initializationData.getInitCaseSelfCompareParam();
        }

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByCaseId(caseId);
        LimsConsignmentInfo limsConsignmentInfo = limsConsignmentInfoService.getMainConsignmentByCaseId(caseId);

        //案件所有检材
        List<LimsSampleInfoDna> allSampleList = new ArrayList<>();

        //全部已审核的检材基因列表
        List<MatchAuditedGene> allGeneList = new ArrayList<>();
        //全部已审核的STR检材列表
        List<MatchAuditedGene> allStrGeneList = new ArrayList<>();
        //全部已审核且位点数大于最少位点个数的STR检材列表
        List<MatchAuditedGene> allCheckedStrGeneList = new ArrayList<>();
        //同型比中信息
        List<CaseCompareResultGroup> sameMatchedGroupList = new ArrayList<>();
        //亲缘比中信息
        List<CompareRelativeResult> compareRelativeResultList = new ArrayList<>();
        //同型无比中检材信息
        List<MatchAuditedGene> notMatchedGeneList = new ArrayList<>();
        //Y-STR检材列表
        List<MatchAuditedGene> ystrGeneList = new ArrayList<>();
        //混合STR检材列表
        List<MatchAuditedGene> mixGeneList = new ArrayList<>();
        //未检出检材信息
        List<LimsSampleInfoDna> notDetectedList = new ArrayList<>();

//        List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByCaseId(caseId);
//        List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectListByCaseId(caseId);
        //获取本案所有已受理检材信息
        allSampleList = limsSampleInfoDnaService.selectAcceptedByCaseId(caseId);


        //全部已审核的检材基因列表
        MatchAuditedGene matchAuditedGene = new MatchAuditedGene();
        matchAuditedGene.setCaseId(caseId);
        allGeneList = matchAuditedGeneService.selectByMatchAuditedGene(matchAuditedGene);

        if(ListUtils.isNotNullAndEmptyList(allGeneList)) {
            //所有的已审核STR列表
            allStrGeneList = allGeneList.stream().filter(gene -> Constants.GENE_STR.equals(gene.getGeneType())).collect(Collectors.toList());
            //所有的已审核Y-STR列表
            ystrGeneList = allGeneList.stream().filter(gene -> Constants.GENE_YSTR.equals(gene.getGeneType())).collect(Collectors.toList());
            //所有的已审核MIX列表
            mixGeneList = allGeneList.stream().filter(gene -> Constants.GENE_MIXED.equals(gene.getGeneType())).collect(Collectors.toList());
        }

        if(ListUtils.isNotNullAndEmptyList(allStrGeneList)){
            allCheckedStrGeneList = getAllSatisfiedInstoreConditionsGeneList(allStrGeneList);
            allCheckedStrGeneList = allStrGeneList.stream().filter(strGene -> (strGene.getGeneCount() >= limsConfigure.getStrInstoreGeneCountLimit())).collect(Collectors.toList());
        }

        //本案同型比中结果列表
        sameMatchedGroupList = getCaseSameMatcheGroupList(caseSelfCompareParams, caseInfo.getCaseId(), allCheckedStrGeneList, isAgainMatch);
        //本案亲缘比中结果列表
        compareRelativeResultList = getCaseRetiveMatcheGroupList(caseId, allSampleList);
        //STR 无比中的检材列表
        notMatchedGeneList = getNotMatchedGeneList(allCheckedStrGeneList, sameMatchedGroupList, compareRelativeResultList);

        //未检出检材列表
        notDetectedList.addAll(getNotSatisfiedInstoreConditionsGeneList(allSampleList, allGeneList));

        List<CaseCompareResultGroup> allGroupList = new ArrayList<>();
        allGroupList.addAll(sameMatchedGroupList);
//        allGroupList.addAll(mixGroupList);

        List<MatchAuditedGene> allAuditedGeneList = new ArrayList<>();
        allAuditedGeneList.addAll(allCheckedStrGeneList);
//        allAuditedGeneList.addAll(mixAuditedGeneList);

        //查询种族信息
        List<Race> raceList = raceService.selectAll();

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if(operateUser == null){
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return  view;
        }

//        //获取技术任职职称
//        DictItem dictItem = new DictItem();
//        dictItem.setDictTypeCode(Constants.TECHNICAL_TITLES);
//        List<DictItem> titlesList = DictUtil.getDictList(dictItem);
//        view.addObject("titlesList", titlesList);

        view.addObject("caseId", caseId);
        view.addObject("limsCaseInfo", caseInfo);
        view.addObject("limsConsignmentInfo", limsConsignmentInfo);
        view.addObject("notDetectedList", notDetectedList);
        view.addObject("sameMatchedGroupList", sameMatchedGroupList);
        view.addObject("notMatchedGeneList", notMatchedGeneList);
        view.addObject("ystrGeneList", ystrGeneList);
        view.addObject("mixGeneList", mixGeneList);
//        view.addObject("mixGroupList", mixGroupList);
//        view.addObject("mixGroupList", newMixGroupList4);
        view.addObject("raceList", raceList);
//        view.addObject("auditedGeneList", auditedGeneList);
        view.addObject("compareRelativeResultList", compareRelativeResultList);
        view.addObject("relationCount", compareRelativeResultList.size());
        view.addObject("strInstoreGeneCountLimit", limsConfigure.getStrInstoreGeneCountLimit());
        view.addObject("ystrInstoreGeneCountLimit", limsConfigure.getYstrInstoreGeneCountLimit());
        view.setViewName("caseCompare/enterCaseCompare");
        return view;
    }

    @ResponseBody
    @RequestMapping("/caseinfo/mixAnalysis")
    public Map<String, Object> mixAnalysis(HttpServletRequest request, String caseId, String mixSampleId){
        Map<String, Object> resultMap = new HashMap<>();

        //TODO  根据caseID和混合样本mixSampleId查询数据，并封装混合库接口定义的数据接口
        //TODO  调用混合库接口

        resultMap.put("success", true);

        return resultMap;
    }

    /**
     * 亲缘比对
     * @param request
     * @param relationCompare
     * @return
     */
    @RequestMapping(value = "/realtionCompareTo", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public List<Map<String, Object>> realtionCompareTo(HttpServletRequest request, @RequestBody RelationCompare relationCompare) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        MatchAuditedGene FsampleGene = null;
        String fatherGeneInfo = null;
        if (StringUtils.isNotBlank(relationCompare.getFatherSampleNo())) {
            List<MatchAuditedGene> fatherGeneList = matchAuditedGeneService.selectListBySampleNo(relationCompare.getFatherSampleNo());
            if (ListUtils.isNotNullAndEmptyList(fatherGeneList)) {
                FsampleGene = fatherGeneList.get(0);
                if (FsampleGene != null) {
                    fatherGeneInfo = FsampleGene.getGeneInfo();
                }
            }
        }

        MatchAuditedGene MsampleGene = null;
        String motherGeneInfo = null;
        if (StringUtils.isNotBlank(relationCompare.getMotherSampleNo())) {
            List<MatchAuditedGene> motherGeneList = matchAuditedGeneService.selectListBySampleNo(relationCompare.getMotherSampleNo());
            if (ListUtils.isNotNullAndEmptyList(motherGeneList)) {
                MsampleGene = motherGeneList.get(0);
                if (MsampleGene != null) {
                    motherGeneInfo = MsampleGene.getGeneInfo();
                }
            }
        }

        MatchAuditedGene ZsampleGene = null;
        String childGeneInfo = null;
        String[] zBarcodeArr = relationCompare.getChildSampleNoStr().substring(0, relationCompare.getChildSampleNoStr().length() - 1).split(",");
        for (int i = 0; i < zBarcodeArr.length; i ++) {
            List<MatchAuditedGene> childrenGeneList = matchAuditedGeneService.selectListBySampleNo(zBarcodeArr[i].trim());
            if (ListUtils.isNotNullAndEmptyList(childrenGeneList)) {
                ZsampleGene = childrenGeneList.get(0);
                if (ZsampleGene != null) {
                    childGeneInfo = ZsampleGene.getGeneInfo();
                }
            }

            CompareRelativeResult compareRelativeResult = new CompareRelativeResult();
            compareRelativeResult.setMatchLimit(relationCompare.getMatchRelationLimit());
            compareRelativeResult.setTolerance(relationCompare.getHalfDiffCount());
            resultList.add(geneRelativeCompareUtil.relationCompare(fatherGeneInfo, motherGeneInfo, childGeneInfo, compareRelativeResult));
        }

        return resultList;
    }

    /**
     * 更新基因分型为无效检材
     * @param request
     * @param matchAuditedGeneList
     * @return
     */
    @RequestMapping(value="/updateInvalidStatus", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateInvalidStatus(HttpServletRequest request, @RequestBody List<MatchAuditedGene> matchAuditedGeneList) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (ListUtils.isNotNullAndEmptyList(matchAuditedGeneList)) {
                for (MatchAuditedGene mag : matchAuditedGeneList) {
                    MatchAuditedGene auditedGene = matchAuditedGeneService.selectByPrimaryKey(mag.getAuditedGeneId());
                    auditedGene.setInvalidStatus(mag.getInvalidStatus());
                    matchAuditedGeneService.updateByPrimaryKey(auditedGene);
                }
            }
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
            logger.error("更新基因分型为无效检材报错！" + e.getMessage());
            e.getStackTrace();
        }

        return result;
    }

    /**
     * 保存比对结果
     * @param request
     * @param caseCompareResultInfoModel
     * @return
     */
    @RequestMapping(value="/saveComparisonResult", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> saveComparisonResult(HttpServletRequest request, @RequestBody CaseCompareResultInfoModel caseCompareResultInfoModel){
        Map<String, Object> result = new HashMap<String, Object>();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            result.put("success", false);
            result.put("", "/login.html?timeoutFlag=1");
            return result;
        }

        //查询当前人员信息
        AmPersonalInfo amPersonalInfo = amPersonalInfoService.queryAmPersonalInfo(operateUser.getPersonalId());

        boolean sameFlag = false;
        try {
            sameFlag = saveSameCompareResult(caseCompareResultInfoModel, amPersonalInfo);
            result.put("sameFlag", sameFlag);
        }catch (Exception e) {
            result.put("sameFlag", false);
            logger.error("保存同一比对结果报错！" + e.getMessage());
            e.getStackTrace();
        }


        boolean relationFlag = false;
        try {
            relationFlag = saveRelationCompareResult(caseCompareResultInfoModel, amPersonalInfo);
            result.put("relationFlag", relationFlag);
        }catch (Exception e) {
            result.put("relationFlag", false);
            logger.error("保存亲缘比对结果报错！" + e.getMessage());
            e.getStackTrace();
        }

        return result;
    }

    /**
     * 查看同一比对结果
     * @param request
     * @param compareSameResult
     * @return
     */
    @RequestMapping(value="/viewCompareResult")
    @ResponseBody
    public ModelAndView viewCompareResult(HttpServletRequest request, CompareSameResult compareSameResult) {
        Map<String, Object> result = new HashMap<>();
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (compareSameResult != null) {
                String referenceId = compareSameResult.getReferenceId();
                String sampleNo = compareSameResult.getSampleNo();
                List<MatchAuditedGene> referenceAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(referenceId);
                List<MatchAuditedGene> auditedGeneList = matchAuditedGeneService.selectListBySampleNo(sampleNo);

                if (ListUtils.isNotNullAndEmptyList(referenceAuditedGeneList) && ListUtils.isNotNullAndEmptyList(auditedGeneList)) {
                    MatchAuditedGene referenceAuditedGene = referenceAuditedGeneList.get(0);
                    MatchAuditedGene auditedGene = auditedGeneList.get(0);

                    String geneInfos1 = referenceAuditedGene.getGeneInfo();
                    if (StringUtils.isNotBlank(referenceAuditedGene.getPanelName()) && CommonUtils.contain(referenceAuditedGene.getPanelName(), "identifiler")) {
                        geneInfos1 = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, geneInfos1);
                    }
                    String geneInfos2 = auditedGene.getGeneInfo();
                    if (StringUtils.isNotBlank(auditedGene.getPanelName()) && CommonUtils.contain(auditedGene.getPanelName(), "identifiler")) {
                        geneInfos2 = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, geneInfos2);
                    }
                    Map<String, List<String>> referenceGeneInfo = (Map) JSON.parse(geneInfos1,Feature.OrderedField);
                    Map<String, List<String>> geneInfo = (Map) JSON.parse(geneInfos2,Feature.OrderedField);
                    if (referenceGeneInfo != null && geneInfo != null) {
                        if (compareSameResult.getMatchLimit() == null) {
                            compareSameResult.setMatchLimit(0);
                        }
                        result = geneSameCompareUtil.compareResult(referenceGeneInfo, geneInfo, compareSameResult.getMatchLimit());
                    }

                    Double fProb = (Double)result.get("probability");
                    String str = DataFormat.formatDecimal(fProb == null?0.0:fProb, 3, 1, true);
                    modelAndView.addObject("mapList", result);
                    modelAndView.addObject("probability", str);
                    modelAndView.addObject("referenceAuditedGene", referenceAuditedGene);
                    modelAndView.addObject("auditedGene", auditedGene);
                    modelAndView.addObject("success", true);
                    modelAndView.setViewName("caseCompare/fromComparison");
                }else {
                    modelAndView.addObject("success", false);
                }
            }else {
                modelAndView.addObject("success", false);
            }
        }catch (Exception e) {
            logger.error("查看同一比对结果报错！" + e.getMessage());
            modelAndView.addObject("success", false);
            e.getStackTrace();
        }

        return modelAndView;
    }

    /**
     * 查看亲缘比对结果
     * @param request
     * @param compareRelativeResult
     * @return
     */
    @RequestMapping(value="/viewRelationCompareResult")
    public ModelAndView viewRelationCompareResult(HttpServletRequest request, CompareRelativeResult compareRelativeResult) {
        ModelAndView modelAndView = new ModelAndView();

        try{
            MatchAuditedGene FsampleGene = null;
            String fatherGeneInfo = null;
            if (StringUtils.isNotBlank(compareRelativeResult.getFatherSampleNo())) {
                List<MatchAuditedGene> fatherGeneList = matchAuditedGeneService.selectListBySampleNo(compareRelativeResult.getFatherSampleNo());
                if (ListUtils.isNotNullAndEmptyList(fatherGeneList)) {
                    FsampleGene = fatherGeneList.get(0);
                    if (FsampleGene != null) {
                        fatherGeneInfo = FsampleGene.getGeneInfo();
                        modelAndView.addObject("fatherSampleNo", FsampleGene.getSampleNo());
                    }
                }
            }

            MatchAuditedGene MsampleGene = null;
            String motherGeneInfo = null;
            if (StringUtils.isNotBlank(compareRelativeResult.getMotherSampleNo())) {
                List<MatchAuditedGene> motherGeneList = matchAuditedGeneService.selectListBySampleNo(compareRelativeResult.getMotherSampleNo());
                if (ListUtils.isNotNullAndEmptyList(motherGeneList)) {
                    MsampleGene = motherGeneList.get(0);
                    if (MsampleGene != null) {
                        motherGeneInfo = MsampleGene.getGeneInfo();
                        modelAndView.addObject("motherSampleNo", MsampleGene.getSampleNo());
                    }
                }
            }

            MatchAuditedGene ZsampleGene = null;
            String childGeneInfo = null;
            if (StringUtils.isNotBlank(compareRelativeResult.getChildSampleNo())) {
                List<MatchAuditedGene> childGeneList = matchAuditedGeneService.selectListBySampleNo(compareRelativeResult.getChildSampleNo());
                if (ListUtils.isNotNullAndEmptyList(childGeneList)) {
                    ZsampleGene = childGeneList.get(0);
                    if (ZsampleGene != null) {
                        childGeneInfo = ZsampleGene.getGeneInfo();
                        modelAndView.addObject("childSampleNo", ZsampleGene.getSampleNo());
                    }
                }
            }

            List<Map<String, Object>> resultList = new ArrayList<>();
            resultList.add(geneRelativeCompareUtil.relationCompare(fatherGeneInfo, motherGeneInfo, childGeneInfo, compareRelativeResult));

            Map<String, List<String>> fatherResult = null;
            try {
//                fatherResult =(Map) JSON.parse(fatherGeneInfo);

                String geneInfos1 = fatherGeneInfo;
                if (StringUtils.isNotBlank(FsampleGene.getPanelName()) && CommonUtils.contain(FsampleGene.getPanelName(), "identifiler")) {
                    geneInfos1 = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, geneInfos1);
                }
                fatherResult = (Map) JSON.parse(geneInfos1, Feature.OrderedField);
                System.out.println(fatherResult);
            } catch (Exception ex) {
                logger.error("解析父亲基因分型信息错误！", ex);
            }

            Map<String, List<String>> motherResult = null;
            try {
//                motherResult =(Map) JSON.parse(motherGeneInfo);
                String geneInfos2 = motherGeneInfo;
                if (StringUtils.isNotBlank(MsampleGene.getPanelName()) && CommonUtils.contain(MsampleGene.getPanelName(), "identifiler")) {
                    geneInfos2 = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, geneInfos2);
                }
                motherResult = (Map) JSON.parse(geneInfos2, Feature.OrderedField);
                System.out.println(motherResult);


            } catch (Exception ex) {
                logger.error("解析母亲基因分型信息错误！", ex);
            }

            Map<String, List<String>> childResult = null;
            try {
//                childResult =(Map) JSON.parse(childGeneInfo);

                String geneInfos3 = childGeneInfo;
                if (StringUtils.isNotBlank(ZsampleGene.getPanelName()) && CommonUtils.contain(ZsampleGene.getPanelName(), "identifiler")) {
                    geneInfos3 = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, geneInfos3);
                }
                childResult = (Map) JSON.parse(geneInfos3, Feature.OrderedField);
                System.out.println(childResult);
            } catch (Exception ex) {
                logger.error("解析孩子基因分型信息错误！", ex);
            }

            List<Map<String, Object>> groupList = new ArrayList<>();
            if (ListUtils.isNotNullAndEmptyList(resultList)) {
                for (Map<String,Object> result : resultList) {
                    ParentageMatchResult parentageMatchResult = (ParentageMatchResult)result.get("parentageMatchResult");

                    if (parentageMatchResult != null) {
                        ParentageMatchResultRecord totalResult = parentageMatchResult.getResult();
                        ParentageMatchResultRecord afResult = parentageMatchResult.getResultOfAf();
                        ParentageMatchResultRecord mResult = parentageMatchResult.getResultOfM();

                        for (Map.Entry<String, List<String>> entry : childResult.entrySet()) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            String markerName = entry.getKey();
                            List<String> cAlleleList = entry.getValue();
                            map.put("markerName", markerName);
                            String cGene =  geneRelativeCompareUtil.getAllele(cAlleleList);

                            map.put("cGene", cGene);
                            Map<String, Double> cMarker = totalResult.getPiOfMarker();
                            String cPiStr = "";
                            if (cMarker.containsKey(markerName)) {
                                Double piDouble = cMarker.get(markerName);
                                if (!Double.isNaN(piDouble)) {
                                    cPiStr = DataFormat.formatDecimal(piDouble == 0.0?0.0:piDouble, 3, 1, true);
                                }
                            }
                            map.put("cPiStr", cPiStr);
                            modelAndView.addObject("cMatchCount", totalResult.getMatchCount());
                            Double totalPi = totalResult.getPi();
                            String totalPiStr = "";
                            if (!Double.isNaN(totalPi)) {
                                totalPiStr =  DataFormat.formatDecimal(totalPi == 0.0?0.0:totalPi, 3, 1, true);
                            }
                            modelAndView.addObject("totalPiStr", totalPiStr);

                            String fGene = "";
                            String fPiStr = "";
                            if (fatherResult != null) {
                                if (fatherResult.containsKey(markerName)) {
                                    List<String> fAlleleList = fatherResult.get(markerName);
                                    if (ListUtils.isNotNullAndEmptyList(fAlleleList)) {
                                        fGene = geneRelativeCompareUtil.getAllele(fAlleleList);

                                    }
                                    Map<String, Double> fMarker = afResult.getPiOfMarker();
                                    if (fMarker.containsKey(markerName)) {
                                        Double piDouble = fMarker.get(markerName);
                                        if (!Double.isNaN(piDouble)) {
                                            fPiStr = DataFormat.formatDecimal(piDouble == 0.0 ? 0.0 : piDouble, 3, 1, true);
                                        }
                                    }
                                }
                            }
                            map.put("fGene", fGene);
                            map.put("fPiStr", fPiStr);
                            modelAndView.addObject("fMatchCount", afResult.getMatchCount());
                            Double fTotalPi = afResult.getPi();
                            String fTotalPiStr = "";
                            if (!Double.isNaN(fTotalPi)  && Math.abs(fTotalPi - 1.0d ) > 0d) {
                                fTotalPiStr =  DataFormat.formatDecimal(fTotalPi == 0.0?0.0:fTotalPi, 3, 1, true);
                            }
                            modelAndView.addObject("fTotalPiStr", fTotalPiStr);

                            String mGene = "";
                            String mPiStr = "";
                            if (motherResult != null) {
                                if (motherResult.containsKey(markerName)) {
                                    List<String> mAlleleList = motherResult.get(markerName);
                                    if (ListUtils.isNotNullAndEmptyList(mAlleleList)) {
                                        mGene = geneRelativeCompareUtil.getAllele(mAlleleList);
                                    }
                                    Map<String, Double> mMarker = mResult.getPiOfMarker();
                                    if (mMarker.containsKey(markerName)) {
                                        Double piDouble = mMarker.get(markerName);
                                        if (!Double.isNaN(piDouble)) {
                                            mPiStr = DataFormat.formatDecimal(piDouble == 0.0?0.0:piDouble, 3, 1, true);
                                        }
                                    }
                                }
                            }

                            map.put("mPiStr", mPiStr);
                            map.put("mGene", mGene);
                            modelAndView.addObject("mMatchCount", mResult.getMatchCount());
                            Double mTotalPi = mResult.getPi();
                            String mTotalPiStr = "";
                            if (!Double.isNaN(mTotalPi) && Math.abs(mTotalPi - 1.0d ) > 0d) {
                                mTotalPiStr =  DataFormat.formatDecimal(mTotalPi == 0.0?0.0:mTotalPi, 3, 1, true);
                            }
                            modelAndView.addObject("mTotalPiStr", mTotalPiStr);

                            boolean isMatch = true;
                            if (ListUtils.isNotNullAndEmptyList(cAlleleList) && cAlleleList.size() == 2) {
                                //判断父、母亲父基因位点与孩子其中一位位点是否比中
                                String allele1 = cAlleleList.get(0).toString();
                                String allele2 = cAlleleList.get(1).toString();
                                if (StringUtils.isNotBlank(fGene) && StringUtils.isNotBlank(mGene)) {
                                    if (fGene.contains(allele1) && mGene.contains(allele2)) {
                                        isMatch = true;
                                    }else if (fGene.contains(allele2) && mGene.contains(allele1)) {
                                        isMatch = true;
                                    }else {
                                        isMatch = false;
                                    }
                                }else if (StringUtils.isNotBlank(fGene) && StringUtils.isBlank(mGene)) {
                                    if (!fGene.contains(allele1) && !fGene.contains(allele2) ) {
                                        isMatch = false;
                                    }
                                }else if (StringUtils.isBlank(fGene) && StringUtils.isNotBlank(mGene)) {
                                    if (!mGene.contains(allele1) && !mGene.contains(allele2)) {
                                        isMatch = false;
                                    }
                                }
                            }

                            if (!isMatch) {
                                map.put("type", "abnormal");
                            }

                            groupList.add(map);
                        }
                    }
                }
            }

            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(compareRelativeResult.getCaseId());

            modelAndView.addObject("groupList", groupList);
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);
            modelAndView.setViewName("caseCompare/relationComparison");
        }catch (Exception e) {
            logger.error("查看亲缘比对结果报错！" + e.getMessage());
            e.getStackTrace();
        }

        return modelAndView;
    }

    //查看混合比对详情
    @RequestMapping("/details")
    public ModelAndView details(HttpServletRequest request, String caseId, String consignmentId, Integer mixMatchLimit, Integer groupId,String sampleNo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo loaUserInfo = (LoaUserInfo) subject.getPrincipal();


        MatchAuditedGene matchAuditedGene = new MatchAuditedGene();
        matchAuditedGene.setCaseId(caseId);
        matchAuditedGene.setGeneType(Constants.GENE_STR);
        //查询审核过的常染色体基因信息
        List<MatchAuditedGene> auditedGeneList = matchAuditedGeneService.selectByMatchAuditedGene(matchAuditedGene);

        matchAuditedGene.setGeneType(Constants.GENE_MIXED);
        //查询审核过混合的基因信息
        List<MatchAuditedGene> mixAuditedGeneList = matchAuditedGeneService.selectByMatchAuditedGene(matchAuditedGene);

        //混合检材分组
        List<CaseCompareResultGroup> mixGroupList = new ArrayList<>();
        List<CaseCompareResultGroup> newMixGroupList2 = new ArrayList<>();
        List<CaseCompareResultGroup> newMixGroupList3 = new ArrayList<>();



        List<CompareSameResult> compareSameResults = compareSameResultService.selectListByCaseId(caseId);
        List<String> compareSameResults2 = new ArrayList<>();

        try {
            mixMatchLimit=0;
            mixGroupList = geneMixCompareUtil.mixDoCompare(mixAuditedGeneList, auditedGeneList, mixMatchLimit);

            for (CompareSameResult compareSameResult : compareSameResults) {
                if (!compareSameResult.getSampleNo().equals(compareSameResult.getReferenceId())){
                    compareSameResults2.add(compareSameResult.getSampleNo());
                }
            }


            for (CaseCompareResultGroup caseCompareResultGroup : mixGroupList) {
                List<MatchAuditedGene> matchedSampleList1 = new ArrayList<>();
                if (caseCompareResultGroup.getMixMatchedSampleList().get(0).getSampleNo().equals(sampleNo)){
                    List<MatchAuditedGene> matchedSampleList = caseCompareResultGroup.getMixMatchedSampleList();

                    for (MatchAuditedGene auditedGene : matchedSampleList) {
                        if (!compareSameResults2.contains(auditedGene.getSampleNo())){
                            matchedSampleList1.add(auditedGene);
                        }
                    }


                    caseCompareResultGroup.setMixMatchedSampleList(matchedSampleList1);
                    newMixGroupList2.add(caseCompareResultGroup);
                }

            }
            List<MatchAuditedGene> matchAuditedGeneList = new ArrayList<>();
            Set<MatchAuditedGene> notDetectedList = new LinkedHashSet<>();



            for (CaseCompareResultGroup caseCompareResultGroup : newMixGroupList2) {

                List<MatchAuditedGene> mixMatchedSampleList = caseCompareResultGroup.getMixMatchedSampleList();
                for (MatchAuditedGene auditedGene : mixMatchedSampleList) {

                    if (StringUtils.isNotBlank(auditedGene.getGeneInfo())) {
                        List<String> markerList = geneSameCompareUtil.getMarker(auditedGene.getGeneInfo());
                        //审核通过的检材且基因座少于8个时，表示未检出
                        if(markerList.size() < 8){
                            notDetectedList.add(auditedGene);
                        }else {
                            if(!matchAuditedGeneList.contains(auditedGene)){
                                matchAuditedGeneList.add(auditedGene);
                            }
                        }
                    }else {
                        notDetectedList.add(auditedGene);
                    }

                }
                if (newMixGroupList3.size() == 0){
                    CaseCompareResultGroup caseCompareResultGroup1 = new CaseCompareResultGroup();
                    caseCompareResultGroup1 =caseCompareResultGroup;
                    caseCompareResultGroup1.setMixMatchedSampleList(matchAuditedGeneList);
                    newMixGroupList3.add(caseCompareResultGroup1);
                }
                    if (!caseCompareResultGroup.getMixMatchedSampleList().containsAll(notDetectedList)){


                    if (!newMixGroupList3.get(0).getMixMatchedSampleList().containsAll(caseCompareResultGroup.getMixMatchedSampleList())){
                        CaseCompareResultGroup caseCompareResultGroup1 = new CaseCompareResultGroup();
                        caseCompareResultGroup1 =caseCompareResultGroup;
                        caseCompareResultGroup1.setMixMatchedSampleList(matchAuditedGeneList);
                        newMixGroupList3.add(caseCompareResultGroup1);
                    }
                    }
            }


        }catch (Exception e) {
            logger.error("混合检材分组报错！" + e.getMessage());
            e.getStackTrace();
        }

        List<MatchAuditedGene> contributorList1 = new ArrayList<>();
        List<MatchAuditedGene> contributorList2 = new ArrayList<>();
        MatchAuditedGene contributor3 = null;
        MatchAuditedGene contributor4 = null;
        List<String> geneList1 = new ArrayList<>();
        List<String> geneList2 = new ArrayList<>();
        MatchAuditedGene mixAuditedGene = new MatchAuditedGene();
        MatchAuditedGene contributor1Gene = null;
        MatchAuditedGene contributor2Gene = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
//        if (groupId != null) {
            if (ListUtils.isNotNullAndEmptyList(newMixGroupList3)) {
                for (CaseCompareResultGroup ccrg : newMixGroupList3) {
//                    if (groupId.intValue() == ccrg.getGroupId()) {
                        List<MatchAuditedGene> matchAuditedGeneList = ccrg.getMixMatchedSampleList();
                        for (int i = 0;i < matchAuditedGeneList.size(); i++) {
                            MatchAuditedGene auditedGene = matchAuditedGeneList.get(i);
                            mixAuditedGene = matchAuditedGeneList.get(0);
                            if (auditedGene != null) {
                                //获取贡献者1名称
                                if (i != 0) {
                                    contributorList1.add(auditedGene);
                                    contributor1Gene = auditedGene;
                                }
//                                if (i == 2) {
//                                    contributorList2.add(auditedGene.getSampleName());
//                                    contributor2Gene = auditedGene;
//                                }
                            }
                        }
                        break;
//                    }
                }
            }


//        }
        //试剂盒
        List<Panel> panels = panelService.selectAll();

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        if (mixAuditedGene != null){
            BlendRelationResult blendRelationResult = blendRelationMapper.selectById(mixAuditedGene.getSampleId());
            if (blendRelationResult!= null){
                contributor3 = new MatchAuditedGene();

                contributor3.setSampleId(blendRelationResult.getBlendId());
                contributor3.setSampleName(blendRelationResult.getContributionName());
                contributor3.setSampleId1(blendRelationResult.getSampleId1());
                contributor3.setSampleId2(blendRelationResult.getSampleId2());

                contributor4 = new MatchAuditedGene();
                contributor4.setSampleId(blendRelationResult.getBlendId());
                contributor4.setSampleName(blendRelationResult.getContributionName2());
                contributor4.setSampleId1(blendRelationResult.getSampleId1());
                contributor4.setSampleId2(blendRelationResult.getSampleId2());

                for (Panel panel : panels) {
                    if (panel.getPanelname().equals(blendRelationResult.getTestkitName())){
                        panels.clear();
                        panels.add(panel);
                        break;
                    }
                }
                if (StringUtils.isNotBlank(blendRelationResult.getSampleId1())) {
                    List<MatchAuditedGene> matchAuditedGenes = matchAuditedGeneService.selectBySampleId(blendRelationResult.getSampleId1());
                    List<String> marker = geneSameCompareUtil.getMarker(matchAuditedGenes.get(0).getGeneInfo());
                    for (String s : marker) {
                        String geneStr = geneSameCompareUtil.analysisGeneStr(s, matchAuditedGenes.get(0).getGeneInfo());
                        geneList1.add(geneStr);
                    }
                }
                if (StringUtils.isNotBlank(blendRelationResult.getSampleId2())){

                    List<MatchAuditedGene> matchAuditedGenes2 = matchAuditedGeneService.selectBySampleId(blendRelationResult.getSampleId2());
                    List<String> marker2 = geneSameCompareUtil.getMarker(matchAuditedGenes2.get(0).getGeneInfo());
                    for (String s : marker2) {
                        String geneStr = geneSameCompareUtil.analysisGeneStr(s, matchAuditedGenes2.get(0).getGeneInfo());
                        geneList2.add(geneStr);
                    }
                }

            }
        }
        //获取混合与贡献者的基因座和基因位点
        resultList = geneMixCompareUtil.getGeneList(mixAuditedGene, contributor1Gene, contributor2Gene);
        view.addObject("resultList", resultList);
        contributorList2=contributorList1;
        if (panels.size() == 1){
            view.addObject("hiddenButton", 0);
        }
        view.addObject("panels", panels);

        view.addObject("geneList1", geneList1);
        view.addObject("geneList2", geneList2);
        view.addObject("mixGroupList", newMixGroupList3);
        view.addObject("limsCaseInfo", limsCaseInfo);
        view.addObject("mixAuditedGene", mixAuditedGene);
        view.addObject("contributorList1", contributorList1);
        view.addObject("contributorList2", contributorList2);
        view.addObject("contributor3", contributor3);
        view.addObject("contributor4", contributor4);
        view.addObject("caseId", caseId);
        view.addObject("consignmentId", consignmentId);
        view.addObject("mixMatchLimit", mixMatchLimit);
        view.addObject("fullName", loaUserInfo.getAmPersonalInfo().getFullName());
        view.setViewName("caseCompare/caseComparison");
        return view;
    }

    /**
     * 判断此检材是否是未检出检材
     * @param sampleNo
     * @param notDetectedList
     * @return
     */
    public boolean isInNotDetected(String sampleNo, List<LimsSampleInfoDna> notDetectedList) {
        boolean flag = false;

        if (StringUtils.isNotBlank(sampleNo)) {
            for (LimsSampleInfoDna sampleInfoDna : notDetectedList) {
                if (sampleNo.equals(sampleInfoDna.getSampleNo()) && StringUtils.isNotBlank(sampleInfoDna.getSampleId())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    @RequestMapping("/saveBlendRelation")
    @ResponseBody
    public Map<String, Object> saveBlendRelation(BlendRelationResult blendRelationResult) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (blendRelationResult != null && StringUtils.isNotBlank(blendRelationResult.getBlendId())){
                BlendRelationResult blendRelationResult1 = blendRelationMapper.selectById(blendRelationResult.getBlendId());
                if (blendRelationResult1!=null){
                    blendRelationResult.setId(blendRelationResult1.getId());
                    int update =  blendRelationMapper.update(blendRelationResult);
                    if (update != 0){
                        result.put("success", true);
                    }
                }else{
                    String string = UUID.randomUUID().toString();
                    blendRelationResult.setId(string);
                    int insert = blendRelationMapper.insert(blendRelationResult);
                    if (insert != 0){
                        result.put("success", true);
                    }
                }
            }else {
                result.put("success", false);
            }
        }catch (Exception e) {
            logger.error("保存混合关系结果报错！" + e.getMessage());
            result.put("success", false);
            e.getStackTrace();
        }

        return result;
    }

    @RequestMapping("/selectMatchAuditedGene")
    @ResponseBody
    public Map<String, Object> selectMatchAuditedGene(String sampleid) {
        Map<String, Object> result = new HashMap<>();

        try {
           if (StringUtils.isNotBlank(sampleid)){
               List<String> geneList = new ArrayList<>();
               List<MatchAuditedGene> matchAuditedGenes = matchAuditedGeneService.selectBySampleId(sampleid);
               List<String> marker = geneSameCompareUtil.getMarker(matchAuditedGenes.get(0).getGeneInfo());
               for (String s : marker) {
                   String geneStr = geneSameCompareUtil.analysisGeneStr(s, matchAuditedGenes.get(0).getGeneInfo());
                   geneList.add(geneStr);
               }

               System.out.println(marker);
               result.put("resultList",marker);
               result.put("geneList",geneList);
           }



        }catch (Exception e) {
            logger.error("保存混合关系结果报错！" + e.getMessage());
            result.put("success", false);
            e.getStackTrace();
        }

        return result;
    }


    private String defaultInspector(String inspector, List<AmPersonalInfoVo> amPersonalInfoVoList) {
        String personalId = null;

        if (StringUtils.isNotBlank(inspector) && ListUtils.isNotNullAndEmptyList(amPersonalInfoVoList)) {
            for (AmPersonalInfoVo apiv : amPersonalInfoVoList) {
                if (inspector.equals(apiv.getEntity().getFullName())) {
                    personalId = apiv.getEntity().getPersonalId();
                    break;
                }
            }
        }

        return personalId;
    }

    /**
     * 判断此检材是否已经在亲缘比对中
     * @param sampleNo
     * @param relativeResultList
     * @return
     */
    public boolean isInRelative(String sampleNo, List<CompareRelativeResult> relativeResultList) {
        boolean flag = false;

        if (StringUtils.isNotBlank(sampleNo) && ListUtils.isNotNullAndEmptyList(relativeResultList)) {
            for (CompareRelativeResult crr : relativeResultList) {
                if (sampleNo.equals(crr.getFatherSampleNo()) || sampleNo.equals(crr.getMotherSampleNo())
                        || sampleNo.equals(crr.getChildSampleNo())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }


    /**
     * 保存同一比对结果
     * @param caseCompareResultInfoModel
     * @return
     */
    public boolean saveSameCompareResult(CaseCompareResultInfoModel caseCompareResultInfoModel, AmPersonalInfo amPersonalInfo) {
        boolean flag = true;

        try {
            List<CompareSameResult> compareSameResultList = caseCompareResultInfoModel.getCompareSameResultList();
            if (caseCompareResultInfoModel != null && ListUtils.isNotNullAndEmptyList(compareSameResultList)) {
                LimsConsignmentInfo limsConsignmentInfo = caseCompareResultInfoModel.getLimsConsignmentInfo();

                //根据条件删除之前的结果信息
                if (limsConsignmentInfo != null) {
                    compareSameResultService.deleteByCaseId(limsConsignmentInfo.getCaseId());
                }

                CompareSameResult compareSameResult = compareSameResultList.get(0);
                String referenceId = null;
                if (compareSameResult != null) {
                    referenceId = compareSameResult.getReferenceId();
                    if (StringUtils.isEmpty(referenceId)) {
                        referenceId = compareSameResult.getSampleNo();
                    }
                }

                for (CompareSameResult csr : compareSameResultList) {
                    csr.setId(UUID.randomUUID().toString());
                    if (amPersonalInfo != null) {
                        csr.setCreatePerson(amPersonalInfo.getFullName());
                    }
                    csr.setCreateDatetime(new Date());
                    if (StringUtils.isBlank(csr.getSampleNo())) {
                        csr.setSampleNo(referenceId);
                    }
                    if (StringUtils.isBlank(csr.getReferenceId())) {
                        csr.setReferenceId(referenceId);
                    }

                    //保存比对结果信息
                    compareSameResultService.insert(csr);
                }
            }
        }catch (Exception e) {
            logger.error("保存同一比对结果报错！" + e.getMessage());
            flag = false;
            e.getStackTrace();
        }

        return flag;
    }

    /**
     * 保存亲缘比对结果
     * @param caseCompareResultInfoModel
     * @param amPersonalInfo
     * @return
     */
    public boolean saveRelationCompareResult(CaseCompareResultInfoModel caseCompareResultInfoModel, AmPersonalInfo amPersonalInfo) {
        boolean flag = true;

        try {
            List<CompareRelativeResult> compareRelativeResultList = caseCompareResultInfoModel.getCompareRelativeResultList();
            if (caseCompareResultInfoModel != null && ListUtils.isNotNullAndEmptyList(compareRelativeResultList)) {

                LimsConsignmentInfo limsConsignmentInfo = caseCompareResultInfoModel.getLimsConsignmentInfo();

                //根据条件删除之前的结果信息
                if (limsConsignmentInfo != null) {
                    compareRelativeResultService.deleteByCaseId(limsConsignmentInfo.getCaseId());
                }
                for (CompareRelativeResult crr : compareRelativeResultList) {
                    if (StringUtils.isNotBlank(crr.getCaseId())) {
                        crr.setId(UUID.randomUUID().toString());
                        if (amPersonalInfo != null) {
                            crr.setCreatePerson(amPersonalInfo.getFullName());
                        }
                        crr.setCreateDatetime(new Date());

                        //保存比对结果信息
                        compareRelativeResultService.insert(crr);
                    }
                }
            }
        }catch (Exception e) {
            logger.error("保存亲缘比对结果报错！" + e.getMessage());
            flag = false;
            e.getStackTrace();
        }

        return flag;
    }


    /**
     * 从全部检材STR基因列表中过滤出无同型比中和亲缘比中的集合
     * @param allCheckedStrGeneList
     * @param sameMatchedGroupList
     * @param compareRelativeResultList
     * @return
     */
    public List<MatchAuditedGene> getNotMatchedGeneList(List<MatchAuditedGene> allCheckedStrGeneList, List<CaseCompareResultGroup> sameMatchedGroupList, List<CompareRelativeResult> compareRelativeResultList){
        logger.info("过滤已比中检材前，集合size = " + allCheckedStrGeneList.size());

        Iterator<MatchAuditedGene> allGeneIterator = allCheckedStrGeneList.iterator();
        List<MatchAuditedGene> sameMatchedGeneList = new ArrayList<>();
        while(allGeneIterator.hasNext()){
            final MatchAuditedGene gene = allGeneIterator.next();

            for(CaseCompareResultGroup sameGroup : sameMatchedGroupList){
                sameMatchedGeneList = sameGroup.getMatchedSampleList();
                if(ListUtils.isNotNullAndEmptyList(sameMatchedGeneList)){
                    Optional<MatchAuditedGene> optional = sameMatchedGeneList.stream().filter(same -> same.getAuditedGeneId().equals(gene.getAuditedGeneId())).findAny();
                    if(optional != null && optional.isPresent() && optional.get() != null){
                        allGeneIterator.remove();
                        break;
                    }
                }
            }
        }
        logger.info("过滤同型比中检材后，集合size = " + allCheckedStrGeneList.size());

        Iterator<MatchAuditedGene> allGeneIterator1 = allCheckedStrGeneList.iterator();
        while(allGeneIterator1.hasNext()){
            final MatchAuditedGene gene = allGeneIterator1.next();

            for(CompareRelativeResult relative : compareRelativeResultList){
                if(gene.getSampleId().equals(relative.getFatherSampleId())
                        || gene.getSampleId().equals(relative.getMotherSampleId())
                        || gene.getSampleId().equals(relative.getChildSampleId())){
                    allGeneIterator1.remove();
                }
            }
        }
        logger.info("过滤亲缘比中检材后，集合size = " + allCheckedStrGeneList.size());

        return allCheckedStrGeneList;
    }

    /**
     * 从已知列表中过滤出满足（已检出）基因分型入库条件的集合
     * @return
     */
    public List<MatchAuditedGene> getAllSatisfiedInstoreConditionsGeneList(List<MatchAuditedGene> matchAuditedGeneList){
        List<MatchAuditedGene> resultList = new ArrayList<>();

        for(MatchAuditedGene matchAuditedGene : matchAuditedGeneList){
            if(matchAuditedGene.getGeneCount() <= 0 || StringUtils.isBlank(matchAuditedGene.getGeneInfo())){
                continue;
            }

            if(Constants.GENE_STR.equals(matchAuditedGene.getGeneType())
                    && matchAuditedGene.getGeneCount() >= limsConfigure.getStrInstoreGeneCountLimit()){
                resultList.add(matchAuditedGene);
            }else if(Constants.GENE_YSTR.equals(matchAuditedGene.getGeneType())
                    && matchAuditedGene.getGeneCount() >= limsConfigure.getYstrInstoreGeneCountLimit()){
                resultList.add(matchAuditedGene);
            }else if(Constants.GENE_MIXED.equals(matchAuditedGene.getGeneType())){
                resultList.add(matchAuditedGene);
            }
        }

        return resultList;
    }

    /**
     * 从已知列表中过滤出不满足（已检出）基因分型入库条件的集合
     * @return
     */
    public List<LimsSampleInfoDna> getNotSatisfiedInstoreConditionsGeneList(List<LimsSampleInfoDna> allSampleList, List<MatchAuditedGene> allMatchAuditedGeneList){
        List<LimsSampleInfoDna> resultList = new ArrayList<>();

        List<MatchAuditedGene> sampleGeneList = null;
        for(LimsSampleInfoDna sampleInfoDna : allSampleList){
            sampleGeneList = allMatchAuditedGeneList.stream().filter(gene -> gene.getSampleId().equals(sampleInfoDna.getSampleId())).collect(Collectors.toList());

            if(ListUtils.isNullOrEmptyList(sampleGeneList)){
                resultList.add(sampleInfoDna);
                continue;
            }

            boolean notSatisfiedFlag = false;
            for(MatchAuditedGene sampleGene : sampleGeneList){

                if(notSatisfiedFlag){
                    break;
                }

                if(sampleGene.getGeneCount() <= 0 || StringUtils.isBlank(sampleGene.getGeneInfo())){
                    notSatisfiedFlag = true;
                    continue;
                }

                if(Constants.GENE_STR.equals(sampleGene.getGeneType())
                        && sampleGene.getGeneCount() < limsConfigure.getStrInstoreGeneCountLimit()){
                    notSatisfiedFlag = true;
                }else if(Constants.GENE_YSTR.equals(sampleGene.getGeneType())
                        && sampleGene.getGeneCount() < limsConfigure.getYstrInstoreGeneCountLimit()){
                    notSatisfiedFlag = true;
                }
            }

            if(notSatisfiedFlag){
                resultList.add(sampleInfoDna);
            }
        }

        return resultList;
    }


    /**
     * 获取当前案件同型比对结果
     * @param caseId
     * @return
     */
    public List<CaseCompareResultGroup> getCaseSameMatcheGroupList(CaseSelfCompareParams caseSelfCompareParams, String caseId, List<MatchAuditedGene> allCheckedStrGeneList, String isAgainMatch){
        List<CaseCompareResultGroup> sameMatchedGroupList = new ArrayList<>();

        //受害人基因信息
        List<MatchAuditedGene> victimAuditedGeneList = new ArrayList<>();
        //嫌疑人基因信息
        List<MatchAuditedGene> suspectsAuditedGeneList = new ArrayList<>();
        //其他人员基因信息
        List<MatchAuditedGene> otherAuditedGeneList = new ArrayList<>();
        //同一基因型的物证信息
        List<MatchAuditedGene> evidenceAuditedGeneList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(allCheckedStrGeneList)) {
            for (MatchAuditedGene msg : allCheckedStrGeneList) {
                if (Constants.GENE_STR.equals(msg.getGeneType())) {
                    if (Constants.SAMPLE_FLAG_0.equals(msg.getSampleFlag())) {
                        //同一基因型的物证信息
                        evidenceAuditedGeneList.add(msg);
                    }else {
                        if (Constants.PERSON_TYPE_03.equals(msg.getPersonType())) {
                            //受害人基因信息
                            victimAuditedGeneList.add(msg);
                        }else if (Constants.PERSON_TYPE_01.equals(msg.getPersonType())) {
                            //嫌疑人基因信息
                            suspectsAuditedGeneList.add(msg);
                        }else {
                            //其他人员基因信息
                            otherAuditedGeneList.add(msg);
                        }
                    }
                }
            }
        }


        try {
            //受害人与物比中分组
            if (ListUtils.isNotNullAndEmptyList(victimAuditedGeneList)) {
                List<CaseCompareResultGroup> victimGroupList = geneSameCompareUtil.doCompare(victimAuditedGeneList, evidenceAuditedGeneList, Constants.REFERENCE_TYPE_OWNER, caseSelfCompareParams.getSameCompareMatchLimit());
                if (ListUtils.isNotNullAndEmptyList(victimGroupList)) {
                    sameMatchedGroupList.addAll(victimGroupList);
                }
            }
            //嫌疑人与物比中分组
            if (ListUtils.isNotNullAndEmptyList(suspectsAuditedGeneList)) {
                List<CaseCompareResultGroup> suspectsGroupList = geneSameCompareUtil.doCompare(suspectsAuditedGeneList, evidenceAuditedGeneList, Constants.REFERENCE_TYPE_SUSPECTS, caseSelfCompareParams.getSameCompareMatchLimit());
                if (ListUtils.isNotNullAndEmptyList(suspectsGroupList)) {
                    sameMatchedGroupList.addAll(suspectsGroupList);
                }
            }
            //其他人员与物比中分组
            if (ListUtils.isNotNullAndEmptyList(otherAuditedGeneList)) {
                List<CaseCompareResultGroup> otherGroupList = geneSameCompareUtil.doCompare(otherAuditedGeneList, evidenceAuditedGeneList, Constants.REFERENCE_TYPE_OTHER_PEOPLE, caseSelfCompareParams.getSameCompareMatchLimit());
                if (ListUtils.isNotNullAndEmptyList(otherGroupList)) {
                    sameMatchedGroupList.addAll(otherGroupList);
                }
            }
            //物与物比中分组
            if (ListUtils.isNotNullAndEmptyList(evidenceAuditedGeneList)) {
                List<MatchAuditedGene> newAuditedGeneList = new ArrayList<>();
                for (MatchAuditedGene auditedGene : evidenceAuditedGeneList) {
                    boolean isExists = false;
                    if (ListUtils.isNotNullAndEmptyList(sameMatchedGroupList)) {
                        for (int i = 0; i < sameMatchedGroupList.size(); i++) {
                            CaseCompareResultGroup caseCompareResultGroup = sameMatchedGroupList.get(i);
                            if (caseCompareResultGroup != null) {
                                List<MatchAuditedGene> matchAuditedGeneList = caseCompareResultGroup.getMatchedSampleList();
                                for (MatchAuditedGene mag : matchAuditedGeneList) {
                                    if (auditedGene.getSampleId().equals(mag.getSampleId())) {
                                        isExists = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (!isExists) {
                        newAuditedGeneList.add(auditedGene);
                    }
                }
                List<CaseCompareResultGroup> evidenceGroupList = geneSameCompareUtil.doCompare(newAuditedGeneList, newAuditedGeneList, Constants.REFERENCE_TYPE_EVIDENCE, caseSelfCompareParams.getSameCompareMatchLimit());
                //判断物物比中分组中跟其他分组是否有相同检材并进行合并
                if (ListUtils.isNotNullAndEmptyList(evidenceGroupList)) {
                    sameMatchedGroupList.addAll(evidenceGroupList);
                }
            }
        }catch (Exception e) {
            logger.error("同型比中分组报错！" + e.getMessage());
            e.getStackTrace();
        }

        //查询同一比中信息
        List<CompareSameResult> compareSameResultList = compareSameResultService.selectListByCaseId(caseId);
        //此案件同一比中信息存在并且没有重新比对时
        if (ListUtils.isNotNullAndEmptyList(sameMatchedGroupList)) {
            for (CaseCompareResultGroup ccrg : sameMatchedGroupList) {
                List<MatchAuditedGene> matchedSampleList = ccrg.getMatchedSampleList();
                for (MatchAuditedGene mag : matchedSampleList) {
                    String referenceId = mag.getReferenceId();
                    if (matchedSampleList.get(0) != null) {
                        if (StringUtils.isEmpty(referenceId)) {
                            referenceId = matchedSampleList.get(0).getSampleNo();
                        }
                    }
                    mag.setReferenceId(referenceId);
                    LimsSampleGene sampleGene = limsSampleGeneService.selectByPrimaryKey(mag.getAuditedGeneId());
                    if (sampleGene != null) {
                        mag.setPanelName(sampleGene.getPanelName());
                    }
                    //此案件同一比中信息存在并且没有重新比对时
                    if (ListUtils.isNotNullAndEmptyList(compareSameResultList) && !Constants.FLAG_TRUE.equals(isAgainMatch)) {
                        for (CompareSameResult csr : compareSameResultList) {
                            if (StringUtils.isNotBlank(csr.getSampleNo()) && csr.getSampleNo().equals(mag.getSampleNo())) {
                                if (csr.getCompareGeneSum() != null) {
                                    mag.setSameCount(csr.getCompareGeneSum().toString());
                                }
                                mag.setTotalProbability(csr.getCompareTotalProbability());
                                mag.setComparePopulationId(csr.getComparePopulationId());
                                mag.setMatchLimit(csr.getMatchLimit());
                                mag.setTolerance(mag.getTolerance());
                            }
                        }
                    }
                }
            }
        }

        return sameMatchedGroupList;
    }

    /**
     * 获取当前案件亲缘比对结果
     * @param caseId
     * @return
     */
    public List<CompareRelativeResult> getCaseRetiveMatcheGroupList(String caseId, List<LimsSampleInfoDna> sampleInfoDnaList){

        //先查数据库是否已存本案亲缘比对结果，有的话直接查出来返回，没有的话则进行比对后返回
        List<CompareRelativeResult> compareRelativeResultList = compareRelativeResultService.selectListByCaseId(caseId);
        //根据编号赋值
        for (CompareRelativeResult crr : compareRelativeResultList) {
            List<MatchAuditedGene> geneList = null;
            for (LimsSampleInfoDna lsid : sampleInfoDnaList) {
                if (StringUtils.isNotBlank(crr.getFatherSampleNo())) {
                    if (crr.getFatherSampleNo().equals(lsid.getSampleNo())) {
                        crr.setFatherSampleName(lsid.getSampleName());
                        crr.setFatherSampleId(lsid.getSampleId());
                        geneList = matchAuditedGeneService.selectBySampleId(lsid.getSampleId());
                        if (ListUtils.isNotNullAndEmptyList(geneList) && geneList.get(0) != null) {
                            crr.setFatherGeneId(geneList.get(0).getAuditedGeneId());
                            LimsSampleGene sampleGene = limsSampleGeneService.selectByPrimaryKey(geneList.get(0).getAuditedGeneId());
                            if (sampleGene != null) {
                                crr.setReagentNameF(sampleGene.getPanelName());
                            }
                        }
                    }
                }

                if (StringUtils.isNotBlank(crr.getMotherSampleNo())) {
                    if (crr.getMotherSampleNo().equals(lsid.getSampleNo())) {
                        crr.setMotherSampleName(lsid.getSampleName());
                        crr.setMotherSampleId(lsid.getSampleId());
                        geneList = matchAuditedGeneService.selectBySampleId(lsid.getSampleId());
                        if (ListUtils.isNotNullAndEmptyList(geneList) && geneList.get(0) != null) {
                            crr.setMotherGeneId(geneList.get(0).getAuditedGeneId());
                            LimsSampleGene sampleGene = limsSampleGeneService.selectByPrimaryKey(geneList.get(0).getAuditedGeneId());
                            if (sampleGene != null) {
                                crr.setReagentNameM(sampleGene.getPanelName());
                            }
                        }
                    }
                }

                if (StringUtils.isNotBlank(crr.getChildSampleNo())) {
                    if (crr.getChildSampleNo().equals(lsid.getSampleNo())) {
                        crr.setChildSampleName(lsid.getSampleName());
                        crr.setChildSampleId(lsid.getSampleId());
                        geneList = matchAuditedGeneService.selectBySampleId(lsid.getSampleId());
                        if (ListUtils.isNotNullAndEmptyList(geneList) && geneList.get(0) != null) {
                            crr.setChildGeneId(geneList.get(0).getAuditedGeneId());
                            LimsSampleGene sampleGene = limsSampleGeneService.selectByPrimaryKey(geneList.get(0).getAuditedGeneId());
                            if (sampleGene != null) {
                                crr.setReagentNameC(sampleGene.getPanelName());
                            }
                        }
                    }
                }
            }

            int matchedMode = Integer.valueOf(StringUtils.isBlank(crr.getMatchMode()) ? "0" : crr.getMatchMode());

            if (matchedMode == Constants.MATCH_MODE_PARENTS) {
                crr.setBacgroundF("green");
                crr.setBacgroundM("green");
            }else if (matchedMode == Constants.MATCH_MODE_FATHER) {
                crr.setBacgroundF("green");
                crr.setBacgroundM("red");
            }else if (matchedMode == Constants.MATCH_MODE_MOTHER) {
                crr.setBacgroundF("red");
                crr.setBacgroundM("green");
            }else if (matchedMode == Constants.MATCH_MODE_ONLY_SINGLE) {
                crr.setBacgroundF("red");
                crr.setBacgroundM("red");
            }else {
                crr.setBacgroundF("red");
                crr.setBacgroundM("red");
            }
        }

        return compareRelativeResultList;
    }

}
