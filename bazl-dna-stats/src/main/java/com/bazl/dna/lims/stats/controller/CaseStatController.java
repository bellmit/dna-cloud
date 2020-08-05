package com.bazl.dna.lims.stats.controller;


import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.stats.LimsStatsConfigure;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.utils.DateUtils;
import com.bazl.dna.lims.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/*
*   案件统计
* */
@RestController
@RequestMapping("/stats/caseStat")
public class CaseStatController extends BaseController {

    @Autowired
    LimsCaseInfoService caseInfoService;
    @Autowired
    LimsStatsConfigure limsStatsConfigure;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;


    /*
    *   云析数据统计
    *       今年案件数   去年案件数   同比量
    * */
    @RequestMapping(value = "/sampleCaseCount")
    public ResponseData sampleCaseCount( ) {
        Map<String, Object> result = new HashMap<>();
        try {
//            if("110230000004".equals(areaCode)){
//                areaCode = "110230000000"; //法医中心账号登录查询
//            }
            //案件总数
//                int caseSum = caseInfoService.selectCountByCaseStatus(null,areaCode); //查询所有案件数

            //今年案件件数
            String currentYear = DateUtils.getCurrentYear();//当前年份
            Date date = DateUtils.beginningTime(currentYear); //今年开始时间
            LimsCaseInfoVo query = new LimsCaseInfoVo();
            query.setDelegateStartDatetime(date);
//            query.setAcceptOrgId("110230000000");
            query.setAppendFlag(Constants.APPEND_FLAG_0);//不补送
            int mainCaseCnt = caseInfoService.selectVOCount(query);

            //去年时期案件数
            String yearDate = Integer.toString(Integer.parseInt(currentYear) - 1);//去年年份
            Date yearStauatDate = DateUtils.beginningTime(yearDate); //去年开始时间
            Date yearTime = DateUtils.endYearTime(yearDate);//去年结束时间
            query.setDelegateStartDatetime(yearStauatDate);//开始时间
            query.setDelegateEndDatetime(yearTime);//结束时间
            int caseFormerYearCount = caseInfoService.selectVOCount(query);

            //案件量同比
            int count = mainCaseCnt - caseFormerYearCount;
            Double bigDecimal1 = null;
            String countFlag = "1";
            if (count > 0){
                countFlag = "2";//增长
                bigDecimal1 = new Double(mainCaseCnt);
            }else if (count < 0){
                countFlag = "3";//下降
                count = -count;
                bigDecimal1 = new Double(caseFormerYearCount);
            }
            Double aDouble = new Double(count);
            double v = aDouble / bigDecimal1;
            int schedule = (int) Math.round(v * 100);
            if (count == 0){
                result.put("percentage", 0);
            }else {
                result.put("percentage", schedule);
            }

            //案件性质数量
            List<CasePropertyStatsVo> casePropertyList = new ArrayList<>();
            List<CasePropertyStatsVo> caseStatsList = new ArrayList<>();
            try {
                Map<String, Object> queryParams = new HashMap<String, Object>();
//                queryParams.put("acceptOrgId", areaCode);
                queryParams.put("startDate", currentYear + "-01-01");
                queryParams.put("endDate", currentYear + "-12-31");
                casePropertyList = caseInfoService.caseStatsByCaseProperty(queryParams);
                if(ListUtils.isNotNullAndEmptyList(casePropertyList)){
                    int qqCount = 0;
                    for (CasePropertyStatsVo statsVo : casePropertyList){
                        if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_04)){
                            qqCount += statsVo.getCaseCount() ;
                        }else if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_22)){
                            qqCount += statsVo.getCaseCount() ;
                        }else if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_21)){
                            qqCount += statsVo.getCaseCount() ;
                        }else if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_16)){
                            qqCount += statsVo.getCaseCount() ;
                        }else if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_1300)){
                            qqCount += statsVo.getCaseCount() ;
                        }else if(statsVo.getCaseProperty().equals(Constants.CASE_PROPERTY_03)){
                            qqCount += statsVo.getCaseCount() ;
                        }else {
                            caseStatsList.add(statsVo);
                        }
                    }
                    CasePropertyStatsVo propertyStatsVo = new CasePropertyStatsVo();
                    propertyStatsVo.setCaseCount(qqCount);
                    propertyStatsVo.setCasePropertyName("侵财");
                    caseStatsList.add(propertyStatsVo);
                }
            }catch(Exception ex){
                logger.error("统计案件性质出错。", ex);
            }

            result.put("caseStatsList", caseStatsList);
            result.put("caseYearCount", mainCaseCnt);
            result.put("countFlag", countFlag);
            result.put("caseFormerYearCount", caseFormerYearCount);
            return new ResponseData(result);
        } catch (NumberFormatException e) {
            logger.error("云析统计数据错误！",e);
            return new ResponseData(0, "云析统计数据错误!");
        }
    }

    /*
    *   案件统计
    *       案件总数   待送检案件数   检验中案件数
    * */
    @RequestMapping(value = "/selectCaseCount")
    public ResponseData selectCaseCount( ) {
        Map<String, Object> result = new HashMap<>();
        try {
            /*if("110230000004".equals(areaCode)){
                areaCode = "110230000000"; //法医中心账号登录查询
            }*/
            //案件总数
            int caseSum = caseInfoService.selectCountByCaseStatus(null,null); //查询所有案件数

            //待送检案件总数
            int caseStatus01Count = caseInfoService.selectCountByCaseStatus(Constants.CASE_STATUS_01,null); //查询所有案件数

            //检验中案件总数
            int caseStatus02Count = caseInfoService.selectCountByCaseStatus(Constants.CASE_STATUS_02,null); //查询所有案件数

            result.put("caseSum", caseSum);
            result.put("awaitCaseCount", caseStatus01Count);
            result.put("inspectCaseCount", caseStatus02Count);

            return new ResponseData(result);
        } catch (Exception e) {
            logger.error("案件统计失败！",e);
            return new ResponseData(0, "案件统计失败!");
        }
    }

    /*
    *   综合送检统计 （已受理）
    *       送检检材数   送检案件数
    * */
    @RequestMapping(value = "/sampleCaseCountByOrgCode")
    public ResponseData sampleCaseCountByOrgCode() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<String> fenjuOrgList = limsStatsConfigure.getFenjuOrgList();
            Map<String, Object> assetsQueryParams = new HashMap<String, Object>();
//        assetsQueryParams.put("acceptOrgId", acceptOrgId);
            assetsQueryParams.put("status", Constants.CASE_STATUS_02);
            assetsQueryParams.put("fenjuOrgList", fenjuOrgList);
            //案件数量
            List<CasePropertyStatsVo> orgCaseInfo = caseInfoService.selectCaseStatsByOrg(assetsQueryParams);
            //检材数量
            List<CasePropertyStatsVo> orgSampleInfo = limsSampleInfoDnaService.selectSampleStatsByOrg(assetsQueryParams);
            if (ListUtils.isNotNullAndEmptyList(orgCaseInfo)){
                for (CasePropertyStatsVo caseStatsVo : orgCaseInfo) {
                    //去掉分局
                    if (StringUtils.isNotBlank(caseStatsVo.getOrgName())){
                        String str = caseStatsVo.getOrgName().replaceAll("分局", "");
                        caseStatsVo.setOrgName(str);
                    }
                    if (ListUtils.isNotNullAndEmptyList(orgSampleInfo)){
                        for (CasePropertyStatsVo smpleStatsVo : orgSampleInfo) {
                            if (caseStatsVo.getOrgCode().equals(smpleStatsVo.getOrgCode())){
                                caseStatsVo.setSampleCount(smpleStatsVo.getCaseCount());
                            }
                        }
                    }
                }
            }
            result.put("orgCaseInfo",orgCaseInfo);
//            result.put("orgSampleInfo",orgSampleInfo);
            return new ResponseData(result);
        } catch (Exception e) {
            logger.error("综合送检统计失败！",e);
            return new ResponseData(0, "综合送检统计失败!");
        }
    }

}
