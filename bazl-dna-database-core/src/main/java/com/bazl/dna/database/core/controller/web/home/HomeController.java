package com.bazl.dna.database.core.controller.web.home;


import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.MatchResultConstants;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.response.datamodel.ComprehensiveInfo;
import com.bazl.dna.database.core.response.datamodel.HomePageDataModel;
import com.bazl.dna.database.service.model.po.*;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhi
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/home")
public class HomeController extends BaseController {

    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    ConsignmentInfoService consignmentInfoService;

    @Autowired
    CasePersonInfoService casePersonInfoService;

    @Autowired
    DnaSampleInfoService dnaSampleInfoService;

    @Autowired
    QcSampleInfoService qcSampleInfoService;

    @Autowired
    MatchResultSameService matchResultSameService;

    @Autowired
    DnaGeneInfoService dnaGeneInfoService;

    @Autowired
    DnaStrGeneInfoService dnaStrGeneInfoService;

    @Autowired
    DnaYstrGeneInfoService dnaYstrGeneInfoService;

    @Autowired
    DnaMixGeneInfoService dnaMixGeneInfoService;

    @Autowired
    MatchResultSameGroupService matchResultSameGroupService;


    //查询首页信息--liuchang
    /*
     * 查询案件信息
     * @return
     */
    @PostMapping(value = "/gethomePageInfo")
    @SuppressWarnings("all")
    public ResponseData gethomePageInfo(@RequestBody  DnaUser dnaUser){
        HomePageDataModel dataModel = new HomePageDataModel();

        //获取当前登录人信息
        try {
            ReviewCompare reviewCompare = new ReviewCompare();
            ArrayList<MatterComparePerson> matterComparePersonList = new ArrayList<>();
            ArrayList<MatterComparePerson> matterCompareMatterList = new ArrayList<>();
            /**
             * 查询最新10条 的物证比中人员分组信息
             */
            List<MatchResultSame> matchResultSames = matchResultSameService.selectLatestTenList(MatchResultConstants.GROUP_TYPE_EVIDENCE_PERSON);
            reviewCompare.setMatterComparematterCount(matchResultSames.size());
            for (MatchResultSame matchResultSame : matchResultSames) {
                MatterComparePerson matterComparePerson = matchResultSameService.queryCaseInfo(matchResultSame);
                matterComparePersonList.add(matterComparePerson);
            }

            if (matchResultSames.size()==0){
                dataModel.setMatterComparePersonList(new ArrayList<>());
            }else {
                if (null != matterComparePersonList.get(0) && !matterComparePersonList.isEmpty()) {
                    dataModel.setMatterComparePersonList(matterComparePersonList);
                } else {
                    dataModel.setMatterComparePersonList(new ArrayList<>());
                }
            }

            /**
             * 查询最新10条 的物证比中物证分组信息
             */
            List<MatchResultSame> matchResultSame = matchResultSameService.selectLatestTenList(MatchResultConstants.GROUP_TYPE_EVIDENCE_EVIDENCE);
            reviewCompare.setMatterComparematterCount(matchResultSames.size());
            for (MatchResultSame matchResultSame2 : matchResultSame) {
                MatterComparePerson matterComparePerson = matchResultSameService.queryMatterCompareMatter(matchResultSame2);
                matterCompareMatterList.add(matterComparePerson);
            }
            if (matchResultSame.size()==0){
                dataModel.setMatterCompareMatterList(new ArrayList<>());
            }else {
               if (null != matterCompareMatterList.get(0) && !matterCompareMatterList.isEmpty()) {
                    dataModel.setMatterCompareMatterList(matterCompareMatterList);
                 } else {
                    dataModel.setMatterCompareMatterList(new ArrayList<>());
                }
            }

            /**
             * 待复核比中
             */
            List<DnaSampleInfo> spanningSampleInfoList = new ArrayList<>();         //人员比中物证跨区比中集合
            List<DnaSampleInfo> regionSampleInfoList = new ArrayList<>();         //人员比中物证本区比中集合
            List<DnaSampleInfo> matterSameRegionList = new ArrayList<>();         //物证比中物证本区比中集合
            List<DnaSampleInfo> matterSpanningAreaList = new ArrayList<>();         //物证比中物证跨区比中集合

            List<MatchResultSame> personAndEvidenceList = new ArrayList<>();         //查询待复核人员比中物证信息集合
            List<MatchResultSameGroup> matchResultSameGroups = matchResultSameGroupService.selectListByGroupType(MatchResultConstants.GROUP_TYPE_EVIDENCE_PERSON);
            for (MatchResultSameGroup matchResultSameGroup : matchResultSameGroups) {
                List<MatchResultSame> personAndEvidEnceMatchResultSame = matchResultSameService.selectListByGroupId(matchResultSameGroup.getId());
                for (MatchResultSame matchResult : personAndEvidEnceMatchResultSame) {
                    if (matchResult.getReviewFlag() == 0) {
                        personAndEvidenceList.add(matchResult); //待复核 比中总数(物证比中人员)
                    }
                    if (matchResult.getSampleaId() != null && matchResult.getSamplebId() != null) {
                        DnaSampleInfo sourceSampleInfo = dnaSampleInfoService.selectById(matchResult.getSampleaId());
                        DnaSampleInfo targetSampleInfo = dnaSampleInfoService.selectById(matchResult.getSamplebId());
                        //查询目标检材
                        if (sourceSampleInfo!=null && targetSampleInfo!=null) { //非空校验
                            if (sourceSampleInfo.getLabServerNo().equals(targetSampleInfo.getLabServerNo())) {
                                spanningSampleInfoList.add(sourceSampleInfo);
                            } else {
                                regionSampleInfoList.add(sourceSampleInfo);
                            }
                        }
                    }
                }
            }

            List<MatchResultSame> evidenceAndEvidenceList = new ArrayList<>();         //查询待复核物证比中物证信息集合
            List<MatchResultSameGroup> matchResultSameGroups2 = matchResultSameGroupService.selectListByGroupType(MatchResultConstants.GROUP_TYPE_EVIDENCE_EVIDENCE);
            for (MatchResultSameGroup matchResultSameGroup : matchResultSameGroups2) {
                List<MatchResultSame> personAndEvidEnceMatchResultSame = matchResultSameService.selectListByGroupId(matchResultSameGroup.getId());
                for (MatchResultSame matchResult : personAndEvidEnceMatchResultSame) {
                    if (matchResult.getReviewFlag() == 0) {
                        evidenceAndEvidenceList.add(matchResult);//待复核 比中总数(物证比中物证)
                    }
                    if (matchResult.getSampleaId() != null && matchResult.getSamplebId() != null) {
                        DnaSampleInfo sourceSampleInfo = dnaSampleInfoService.selectById(matchResult.getSampleaId());//查询源检材
                        DnaSampleInfo targetSampleInfo = dnaSampleInfoService.selectById(matchResult.getSamplebId());//查询目标检材
                        if (sourceSampleInfo!=null &&targetSampleInfo!=null) { //非空校验
                            if (sourceSampleInfo.getLabServerNo().equals(targetSampleInfo.getLabServerNo())) {
                                matterSameRegionList.add(sourceSampleInfo);
                            } else {
                                matterSpanningAreaList.add(sourceSampleInfo);
                            }
                        }
                    }
                }
            }

            reviewCompare.setRegionCompare(spanningSampleInfoList.size()); //物证比中人员 本区比中数量
            reviewCompare.setSpanningArea(regionSampleInfoList.size());//物证比中人员 跨区比中数量
            reviewCompare.setMatterSameRegionCount(matterSameRegionList.size());//物证比中物证数据 本区比中数量
            reviewCompare.setMatterSpanningAreaCount(matterSpanningAreaList.size());//物证比中物证数据 跨区比中数量
            reviewCompare.setMatterComparematterCount(evidenceAndEvidenceList.size()); //待复核物证比中物证数量
            reviewCompare.setMatterComparePersonCount(personAndEvidenceList.size());  //待复核物证比中人员数量
            dataModel.setToReviewCompare(reviewCompare);

            //待上报数据
            int toReportMatterCount = 0;
            //获取未上报案件数
            int TRANSFER_NATION_FLAG = 0;
            List<CaseInfo> caseInfoList = caseInfoService.selectByCaseTransferFlag(TRANSFER_NATION_FLAG);//未上报数据，国家库状态类型为0
            //获取未上报物证数量
            for (CaseInfo caseInfo : caseInfoList) {
                List<DnaSampleInfoVo> dnaSampleInfoList = dnaSampleInfoService.selectByCaseId(caseInfo.getId());//查询未上报国家库中的样本数
                toReportMatterCount = dnaSampleInfoList.size() + toReportMatterCount;
            }
            //获取未上报人员数
            int TRANSFER_FLAG = 0;
            List<CasePersonInfo> personInfos = casePersonInfoService.selectByPersonTransferFlag(TRANSFER_FLAG);//未上报的案件人员数据
            ToReportInfo toReportInfo = new ToReportInfo();
            toReportInfo.setToReportCaseCount(caseInfoList.size());
            toReportInfo.setToReportMatterCount(toReportMatterCount);
            toReportInfo.setToReportPersionCount(personInfos.size());

            dataModel.setToReportInfo(toReportInfo);

            //本地库数据情况
            ComprehensiveInfo comprehensiveInfo = new ComprehensiveInfo();
            if (StringUtils.isNotBlank(dnaUser.getUserId())) {
                CasePersonInfo casePersonInfo = casePersonInfoService.selectLabServerNo(dnaUser.getUserId());//查询案件人员信息根据实验室编号

                /**
                 * 本地库案件数
                 * 根据当前登录人所属实验室，查询本实验室的案件总数
                 */
                if (StringUtils.isNotBlank(dnaUser.getUserId()) && dnaUser.getUserId().equals("1")) {
                    int caseCounts = caseInfoService.selectAllCaseCount(); //查询全部案件总数
                    if (caseCounts !=0){
                        comprehensiveInfo.setCaseCount(caseCounts);
                    }else{
                        comprehensiveInfo.setCaseCount(0);
                    }
                } else {
                    List<CaseInfo> caseCounts = caseInfoService.selectCaseByLabServerNo(casePersonInfo.getLabServerNo());
                    comprehensiveInfo.setCaseCount(caseCounts.size());
                }
                /**
                 * 本地库物证数
                 * 根据当前登录人所属实验室，查询本实验室的案件物证总数
                 */
                if (dnaUser.getUserId() != null && dnaUser.getUserId().equals("1")) {
                     int matterCounts = dnaSampleInfoService.selectAllSampleCount();
                     if (matterCounts!=0){
                         comprehensiveInfo.setMatterCount(matterCounts);//查询全部物证数据
                     }else{
                         comprehensiveInfo.setMatterCount(0);
                     }
                } else {
                    List<DnaSampleInfo> matterCounts = dnaSampleInfoService.selectSampleByLabServerNo(casePersonInfo.getLabServerNo());
                    comprehensiveInfo.setMatterCount(matterCounts.size());
                }
                /**
                 * 本地库人员数
                 * 根据当前登录人所属实验室，查询本实验室的人员总数
                 */
                if (dnaUser.getUserId() != null && dnaUser.getUserId().equals("1")) {
                     int personCounts = casePersonInfoService.selectAllCasePersonCount();
                     if (personCounts != 0){
                         comprehensiveInfo.setPersonCount(personCounts);//查询全部案件人员数
                     }else{
                         comprehensiveInfo.setPersonCount(0);
                     }
                } else {
                    List<CasePersonInfo> personCounts = casePersonInfoService.selectByLabServerNo(casePersonInfo.getLabServerNo());
                    comprehensiveInfo.setPersonCount(personCounts.size());
                }
                /**
                 * 本地库STR信息数,如果登陆账号为北京市公安局刑事侦查总队，则默认查询全部
                 * 根据当前登录人所属实验室，查询本实验室的STR信息总数
                 */
                ArrayList<DnaStrGeneInfo> strdnaGeneInfos = new ArrayList<>();
                ArrayList<DnaYstrGeneInfo> ystrdnaGeneInfos = new ArrayList<>();
                ArrayList<DnaMixGeneInfo> hunhednaGeneInfos = new ArrayList<>();
                if (dnaUser.getUserId() != null && dnaUser.getUserId().equals("1")) {
                    int strGeneCount = dnaStrGeneInfoService.selectAllGeneCount();//查询全部Str基因数据
                    if (strGeneCount >0){
                        comprehensiveInfo.setStrCount(strGeneCount); //STR分型数
                    }else{
                        comprehensiveInfo.setStrCount(0);
                    }

                    int ystrGeneCount = dnaYstrGeneInfoService.selectAllGeneCount();//查询全部Ystr基因数据
                    if (ystrGeneCount>0){
                        comprehensiveInfo.setYstrCount(ystrGeneCount);
                    }else {
                        comprehensiveInfo.setYstrCount(0);
                    }

                    int mixGeneCount = dnaMixGeneInfoService.selectAllGeneCount();//查询全部MIX基因数据
                    if (mixGeneCount>0){
                        comprehensiveInfo.setBlendCount(mixGeneCount);
                    }else{
                        comprehensiveInfo.setBlendCount(0);
                    }

                    dataModel.setComprehensiveInfo(comprehensiveInfo);//保存全部分型数据(STR,YSTR.MIX)
                } else {
                    List<DnaStrGeneInfo> geneStrInfos = dnaStrGeneInfoService.selectListByLabServerNo(casePersonInfo.getLabServerNo());//STR分型信息
                    if (geneStrInfos.size() > 0) {
                        for (DnaStrGeneInfo strGeneInfo : geneStrInfos) {
                            strdnaGeneInfos.add(strGeneInfo);
                        }
                    } else {
                        comprehensiveInfo.setStrCount(0);
                    }
                    //geneYstrInfos
                    List<DnaYstrGeneInfo> geneYstrInfos = dnaYstrGeneInfoService.selectListByLabServerNo(casePersonInfo.getLabServerNo());//YSTR分型信息
                    if (geneYstrInfos.size() > 0) {
                        for (DnaYstrGeneInfo ystrGeneInfo : geneYstrInfos) {
                            ystrdnaGeneInfos.add(ystrGeneInfo);
                        }
                    } else {
                        comprehensiveInfo.setYstrCount(0);
                    }
                    //mixGeneInfos
                    List<DnaMixGeneInfo> mixGeneInfos = dnaMixGeneInfoService.selectListByLabServerNo(casePersonInfo.getLabServerNo());//mix分型信息
                    if (mixGeneInfos.size() > 0) {
                        for (DnaMixGeneInfo mixGeneInfo : mixGeneInfos) {
                            hunhednaGeneInfos.add(mixGeneInfo);
                        }
                    } else {
                        comprehensiveInfo.setBlendCount(0);
                    }
                    comprehensiveInfo.setStrCount(strdnaGeneInfos.size());//STR分型数
                    comprehensiveInfo.setYstrCount(ystrdnaGeneInfos.size());//YSTR分型数
                    comprehensiveInfo.setBlendCount(hunhednaGeneInfos.size());//混合分型数
                    dataModel.setComprehensiveInfo(comprehensiveInfo);//综合计数
                }
             }
                return new ResponseData(dataModel);
            }catch(Exception ex){
                logger.error("invoke CurrencyQueryController.queryCaseById error.", ex);
                return new ResponseData("查询案件信息出现异常！" + ex.getMessage());
        }
    }

    /**
     * 查询质控信息
     * @return
     */
    @PostMapping(value = "/currencyCriminalQuery")
    public ResponseData currencyCriminalQuery(CurrencyQcResult currencyQcResult){
        try {
            List<CurrencyQcResult> currencyQcResults = qcSampleInfoService.currencyCriminalQuery(currencyQcResult);
            return new ResponseData(currencyQcResults);
        }catch(Exception ex){
            logger.error("invoke CurrencyQueryController.queryCaseById error.", ex);
            return new ResponseData("查询案件信息出现异常！" + ex.getMessage());
        }
    }
}
