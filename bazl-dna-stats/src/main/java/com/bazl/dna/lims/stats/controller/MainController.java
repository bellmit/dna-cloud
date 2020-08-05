package com.bazl.dna.lims.stats.controller;

import com.alibaba.fastjson.JSONArray;
import com.bazl.dna.lims.stats.LimsStatsConfigure;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.po.DictInfo;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.view.DbInvadingWealthCaseView;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.service.DbInvadingWealthCaseViewService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LaboratoryInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.utils.DateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sun on 2018/12/20.
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

    @Autowired
    LimsStatsConfigure limsStatsConfigure;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    LaboratoryInfoService laboratoryInfoService;

    @Autowired
    LimsCaseInfoService caseInfoService;

    @Autowired
    DbInvadingWealthCaseViewService dbInvadingWealthCaseViewService;

    @RequestMapping("/home")
    public ModelAndView home(HttpServletRequest request, String acceptOrgId, String year) throws JsonProcessingException {
        ModelAndView view = new ModelAndView();
        view.setViewName("home");

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        List<String> yearList = new ArrayList<>();
        String currentYear = DateUtils.getCurrentYear();
        int currentYearIntVal = Integer.parseInt(currentYear);
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                yearList.add(currentYear);
            } else {
                yearList.add(String.valueOf(currentYearIntVal - i));
            }
        }

        if (StringUtils.isBlank(year)) {
            year = currentYear;
        }

        if(StringUtils.isBlank(acceptOrgId)){
            acceptOrgId = operateUser.getOrgId();
        }

        List<CasePropertyStatsVo> caseStatsList = new ArrayList<>();
        try {
            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("acceptOrgId", acceptOrgId);
            queryParams.put("startDate", year + "-01-01");
            queryParams.put("endDate", year + "-12-31");

            caseStatsList = caseInfoService.caseStatsByCaseProperty(queryParams);
        }catch(Exception ex){
            logger.error("统计案件性质出错。", ex);
        }

//        List<DictItem> allCasePropertyList = dictItemService.selectListByDictTypeCode(Constants.CASE_PROPERTY);
//        List<DictItem> assetsDictList = new ArrayList<>();
        List<String> assetsCasePropertyList = limsStatsConfigure.getAssetsCaseSubPropertyList();
//        for(String assetsStr : assetsCasePropertyList){
//            for(DictItem di : allCasePropertyList){
//                if(di.getDictCode().equals(assetsStr)){
//                    assetsDictList.add(di);
//                }
//            }
//        }

        List<String> fenjuOrgList = limsStatsConfigure.getFenjuOrgList();
        List<CasePropertyStatsVo> assetsCaseStatsListByOrg = new ArrayList<>();
        try {
            Map<String, Object> assetsQueryParams = new HashMap<String, Object>();
            assetsQueryParams.put("acceptOrgId", acceptOrgId);
            assetsQueryParams.put("startDate", year + "-01-01");
            assetsQueryParams.put("endDate", year + "-12-31");
            assetsQueryParams.put("caseSubPropertyList", assetsCasePropertyList);
            assetsQueryParams.put("fenjuOrgList", fenjuOrgList);

            assetsCaseStatsListByOrg = caseInfoService.caseStatsByCaseSubPropertyAndOrg(assetsQueryParams);
        }catch(Exception ex){
            logger.error("根据分局统计侵财类案件性质出错。", ex);
        }


        List<CasePropertyStatsVo> assetsCaseStatsList = new ArrayList<>();
        try {
            Map<String, Object> assetsQueryParams = new HashMap<String, Object>();
            assetsQueryParams.put("acceptOrgId", acceptOrgId);
            assetsQueryParams.put("startDate", year + "-01-01");
            assetsQueryParams.put("endDate", year + "-12-31");
            assetsQueryParams.put("caseSubPropertyList", assetsCasePropertyList);

            assetsCaseStatsList = caseInfoService.caseStatsByCaseSubProperty(assetsQueryParams);
        }catch(Exception ex){
            logger.error("统计侵财类案件性质出错。", ex);
        }

        /*HashMap result = caseInfoService.selectOneCategoryCaseTypeCountByYear(year);
        HashMap hashMap = caseInfoService.selectTwoCategoryCaseTypeCountByYear(year);

        int dqCount = 0;
        for (Object map : hashMap.values()) {
            dqCount += Integer.parseInt(String.valueOf(map));
        }*/

//        result.put("DQ", dqCount);



        /*
        Map<String,Object> sjCaseCategoryMap = new HashMap<>();
        for(DbInvadingWealthCaseView sjCaseCategory:sjCaseCategoryCount(year)){
            if(sjCaseCategory.getCaseType().equals("13,13-1,17")){
                sjCaseCategoryMap.put("入室盗窃",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("04,22")){
                sjCaseCategoryMap.put("抢夺抢劫",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("14")){
                sjCaseCategoryMap.put("盗窃机动车",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("15")){
                sjCaseCategoryMap.put("盗窃车内财物",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("16")){
                sjCaseCategoryMap.put("盗窃工地",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("03")){
                sjCaseCategoryMap.put("其他盗窃",sjCaseCategory.getNumber());
            }else if (sjCaseCategory.getCaseType().equals("21")){
                sjCaseCategoryMap.put("诈骗",sjCaseCategory.getNumber());
            }
        }
        */

        List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();

        view.addObject("yearList", yearList);
        view.addObject("laboratoryInfoList", laboratoryInfoList);
        view.addObject("year", year);
        view.addObject("ivYear", year);
        view.addObject("caseStatsList", caseStatsList);
        view.addObject("acceptOrgId", StringUtils.isBlank(acceptOrgId) ? "" : acceptOrgId);
        view.addObject("assetsCaseStatsList", assetsCaseStatsList);
//        view.addObject("sjCaseCategoryList",sjCaseCategoryMap);
//        HttpSession session = request.getSession();
        view.addObject("assetsCaseStatsOrgJsonList", JSONArray.toJSONString(assetsCaseStatsListByOrg));
        return view;
    }

    @RequestMapping("/invadingWealth")
    @ResponseBody
    public Map<String, Object> invadingWealth(HttpServletRequest request, String year, String choiceType) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();
        if (null != year && null != choiceType) {
            if (choiceType.equals("01")) {
                Map<String,Object> sjCaseCategoryMap = new HashMap<>();
                for(DbInvadingWealthCaseView sjCaseCategory:sjCaseCategoryCount(year)){
                    if(sjCaseCategory.getCaseType().equals("13,13-1,17")){
                        sjCaseCategoryMap.put("入室盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("04,22")){
                        sjCaseCategoryMap.put("抢夺抢劫",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("14")){
                        sjCaseCategoryMap.put("盗窃机动车",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("15")){
                        sjCaseCategoryMap.put("盗窃车内财物",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("16")){
                        sjCaseCategoryMap.put("盗窃工地",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("03")){
                        sjCaseCategoryMap.put("其他盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("21")){
                        sjCaseCategoryMap.put("诈骗",sjCaseCategory.getNumber());
                    }
                }
                result.put("sjCaseCategoryList",sjCaseCategoryMap);
                result.put("success", true);
                result.put("dataStats", sjCaseCount(year));
                HttpSession session = request.getSession();
                session.setAttribute("jsonVal", sjCaseCount(year));
            } else if (choiceType.equals("02")) {
                Map<String,Object> sjCaseCategoryMap = new HashMap<>();
                for(DbInvadingWealthCaseView sjCaseCategory:rkWzCaseCategoryCount(year)){
                    if(sjCaseCategory.getCaseType().equals("13,13-1,17")){
                        sjCaseCategoryMap.put("入室盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("04,22")){
                        sjCaseCategoryMap.put("抢夺抢劫",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("14")){
                        sjCaseCategoryMap.put("盗窃机动车",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("15")){
                        sjCaseCategoryMap.put("盗窃车内财物",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("16")){
                        sjCaseCategoryMap.put("盗窃工地",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("03")){
                        sjCaseCategoryMap.put("其他盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("21")){
                        sjCaseCategoryMap.put("诈骗",sjCaseCategory.getNumber());
                    }
                }
                result.put("sjCaseCategoryList",sjCaseCategoryMap);
                result.put("success", true);
                result.put("dataStats", rkWzCaseCount(year));
                HttpSession session = request.getSession();
                session.setAttribute("jsonVal", rkWzCaseCount(year));
            } else if (choiceType.equals("03")) {
                Map<String,Object> sjCaseCategoryMap = new HashMap<>();
                for(DbInvadingWealthCaseView sjCaseCategory:rkSampleCategoryCount(year)){
                    if(sjCaseCategory.getCaseType().equals("13,13-1,17")){
                        sjCaseCategoryMap.put("入室盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("04,22")){
                        sjCaseCategoryMap.put("抢夺抢劫",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("14")){
                        sjCaseCategoryMap.put("盗窃机动车",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("15")){
                        sjCaseCategoryMap.put("盗窃车内财物",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("16")){
                        sjCaseCategoryMap.put("盗窃工地",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("03")){
                        sjCaseCategoryMap.put("其他盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("21")){
                        sjCaseCategoryMap.put("诈骗",sjCaseCategory.getNumber());
                    }
                }
                result.put("sjCaseCategoryList",sjCaseCategoryMap);
                result.put("success", true);
                result.put("dataStats", rkSampleCount(year));
                HttpSession session = request.getSession();
                session.setAttribute("jsonVal", rkSampleCount(year));
            } else if (choiceType.equals("04")) {
                Map<String,Object> sjCaseCategoryMap = new HashMap<>();
                for(DbInvadingWealthCaseView sjCaseCategory:sjSampleCategoryCount(year)){
                    if(sjCaseCategory.getCaseType().equals("13,13-1,17")){
                        sjCaseCategoryMap.put("入室盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("04,22")){
                        sjCaseCategoryMap.put("抢夺抢劫",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("14")){
                        sjCaseCategoryMap.put("盗窃机动车",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("15")){
                        sjCaseCategoryMap.put("盗窃车内财物",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("16")){
                        sjCaseCategoryMap.put("盗窃工地",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("03")){
                        sjCaseCategoryMap.put("其他盗窃",sjCaseCategory.getNumber());
                    }else if (sjCaseCategory.getCaseType().equals("21")){
                        sjCaseCategoryMap.put("诈骗",sjCaseCategory.getNumber());
                    }
                }
                result.put("sjCaseCategoryList",sjCaseCategoryMap);
                result.put("success", true);
                result.put("dataStats", sjSampleCount(year));
                HttpSession session = request.getSession();
                session.setAttribute("jsonVal", sjSampleCount(year));
            }
        }



        result.put("ivYear", year);
        result.put("choiceType", choiceType);
        return result;
    }

    /**
     * 根据年份查询统计数据
     *
     * @param year
     * @return
     */
    @RequestMapping("/getCount")
    @ResponseBody
    public Map<String, Object> getCount(String year) {
        Map<String, Object> map = new HashMap<>();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            map.put("date", "redirect:/login.html?timeoutFlag=1");
            return map;
        }

        try {
            //根据年份获取各个月份的案件数
            //HashMap<String, String> monthMap = caseInfoService.selectMonthCountByYear(year, operateUser.getOrgId());
            //map.put("monthMap", monthMap);
            map.put("code", 0);
            map.put("message", "获取成功！");
        } catch (Exception ex) {
            logger.info("获取失败" + ex);
            map.put("code", 1);
            map.put("message", "获取失败！");
        }
        return map;
    }

    @RequestMapping("/receiveWord")
    public void circulationRecord(HttpServletRequest request, HttpServletResponse response,
                                  String caseNo, String caseName, String orgName, String delegatorName, String delegatorNo) throws ParseException {

        Map<String, Object> params = new HashMap<String, Object>();
        /*if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }*/

        params.put("caseNo", StringUtils.isBlank(caseNo) ? "" : caseNo);
        params.put("caseName", StringUtils.isBlank(caseName) ? "" : caseName);
        params.put("orgName", StringUtils.isBlank(orgName) ? "" : orgName);
        params.put("delegatorName", StringUtils.isBlank(delegatorName) ? "" : delegatorName);
        params.put("delegatorNo", StringUtils.isBlank(delegatorNo) ? "" : delegatorNo);


        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = null;//cfg.getTemplate("鉴定书领取单.ftl", "UTF-8");

            String filename = "-鉴定书领取单" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("下载错误", ex);
        } finally {
            try {
                response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 送检案件数查询处理
     *
     * @return
     */
    private String sjCaseCount(String year) throws JsonProcessingException {
        List<DbInvadingWealthCaseView> delegateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
        DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewLimsList = new ArrayList<>();
        for (DbInvadingWealthCaseView delegateOrg : delegateOrgList) {
            dbInvadingWealthCaseView.setAcceptDatetime(year);
            dbInvadingWealthCaseView.setDelegateOrg(delegateOrg.getDelegateOrg());
            dbInvadingWealthCaseView.setDelegateOrgName(delegateOrg.getDelegateOrgName());
            dbInvadingWealthCaseViewLimsList.addAll(dbInvadingWealthCaseViewService.sjCaseCountByYearList(dbInvadingWealthCaseView));
        }
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.sySjCaseCountByYearList(dbInvadingWealthCaseViewLimsList);
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        JSONArray jsonArr = new JSONArray();

        for (DbInvadingWealthCaseView dbInvadingWealthCase : dbInvadingWealthCaseViewList) {
            map = new HashMap<>();
            map.put("NAME", dbInvadingWealthCase.getDelegateOrgName());
            map.put("VALUE", dbInvadingWealthCase.getNumber());
            jsonArr.add(map);
        }
        String result = jsonObjectMapper.writeValueAsString(jsonArr).toUpperCase();
        return result;
    }

    /**
     * 入库物证案件数查询处理
     *
     * @return
     */
    private String rkWzCaseCount(String year) throws JsonProcessingException {
        List<DbInvadingWealthCaseView> delegateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
        DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewLimsList = new ArrayList<>();
        for (DbInvadingWealthCaseView delegateOrg : delegateOrgList) {
            dbInvadingWealthCaseView.setAcceptDatetime(year);
            dbInvadingWealthCaseView.setDelegateOrg(delegateOrg.getDelegateOrg());
            dbInvadingWealthCaseView.setDelegateOrgName(delegateOrg.getDelegateOrgName());
            dbInvadingWealthCaseViewLimsList.addAll(dbInvadingWealthCaseViewService.rkWzCaseCountByYearList(dbInvadingWealthCaseView));
        }
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.syRkWzCaseCountByYearList(dbInvadingWealthCaseViewLimsList);
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        Map map = new HashMap<>();
        JSONArray jsonArr = new JSONArray();

        for (DbInvadingWealthCaseView dbInvadingWealthCase : dbInvadingWealthCaseViewList) {
            map = new HashMap<>();
            map.put("NAME", dbInvadingWealthCase.getDelegateOrgName());
            map.put("VALUE", dbInvadingWealthCase.getNumber());
            jsonArr.add(map);
        }
        String result = jsonObjectMapper.writeValueAsString(jsonArr);
        return result;
    }

    /**
     * 入库样本数查询处理
     *
     * @return
     */
    private String rkSampleCount(String year) throws JsonProcessingException {
        List<DbInvadingWealthCaseView> delegateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
        DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewLimsList = new ArrayList<>();
        for (DbInvadingWealthCaseView delegateOrg : delegateOrgList) {
            dbInvadingWealthCaseView.setAcceptDatetime(year);
            dbInvadingWealthCaseView.setDelegateOrg(delegateOrg.getDelegateOrg());
            dbInvadingWealthCaseView.setDelegateOrgName(delegateOrg.getDelegateOrgName());
            dbInvadingWealthCaseViewLimsList.addAll(dbInvadingWealthCaseViewService.rkSampleCountByYearList(dbInvadingWealthCaseView));
        }
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.syRkSampleCountByYearList(dbInvadingWealthCaseViewLimsList);
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        Map map = new HashMap<>();
        JSONArray jsonArr = new JSONArray();

        for (DbInvadingWealthCaseView dbInvadingWealthCase : dbInvadingWealthCaseViewList) {
            map = new HashMap<>();
            map.put("NAME", dbInvadingWealthCase.getDelegateOrgName());
            map.put("VALUE", dbInvadingWealthCase.getNumber());
            jsonArr.add(map);
        }
        String result = jsonObjectMapper.writeValueAsString(jsonArr);
        return result;
    }

    /**
     * 送检样本数查询处理
     *
     * @return
     */
    private String sjSampleCount(String year) throws JsonProcessingException {
        List<DbInvadingWealthCaseView> delegateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
        DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewLimsList = new ArrayList<>();
        for (DbInvadingWealthCaseView delegateOrg : delegateOrgList) {
            dbInvadingWealthCaseView.setAcceptDatetime(year);
            dbInvadingWealthCaseView.setDelegateOrg(delegateOrg.getDelegateOrg());
            dbInvadingWealthCaseView.setDelegateOrgName(delegateOrg.getDelegateOrgName());
            dbInvadingWealthCaseViewLimsList.addAll(dbInvadingWealthCaseViewService.sjSampleCountByYearList(dbInvadingWealthCaseView));
        }
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.sySjSampleCountByYearList(dbInvadingWealthCaseViewLimsList);
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        Map map = new HashMap<>();
        JSONArray jsonArr = new JSONArray();

        for (DbInvadingWealthCaseView dbInvadingWealthCase : dbInvadingWealthCaseViewList) {
            map = new HashMap<>();
            map.put("NAME", dbInvadingWealthCase.getDelegateOrgName());
            map.put("VALUE", dbInvadingWealthCase.getNumber());
            jsonArr.add(map);
        }
        String result = jsonObjectMapper.writeValueAsString(jsonArr);
        return result;
    }


    /*-----类别------*/
    /**
     * 送检案件数查询处理
     *
     * @return
     */
    private List<DbInvadingWealthCaseView> sjCaseCategoryCount(String year) throws JsonProcessingException {
        DbInvadingWealthCaseView invadingWealthCaseView = new DbInvadingWealthCaseView();
        invadingWealthCaseView.setAcceptDatetime(year);
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.selectSjCaseByYearAndCaseType(invadingWealthCaseView);

        return dbInvadingWealthCaseViewList;
    }

    /**
     * 入库物证案件数查询处理
     *
     * @return
     */
    private List<DbInvadingWealthCaseView> rkWzCaseCategoryCount(String year) throws JsonProcessingException {
        DbInvadingWealthCaseView invadingWealthCaseView = new DbInvadingWealthCaseView();
        invadingWealthCaseView.setAcceptDatetime(year);
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.selectrkWzCaseByYearAndCaseType(invadingWealthCaseView);

        return dbInvadingWealthCaseViewList;
    }

    /**
     * 入库样本数查询处理
     *
     * @return
     */
    private List<DbInvadingWealthCaseView> rkSampleCategoryCount(String year) throws JsonProcessingException {

        DbInvadingWealthCaseView invadingWealthCaseView = new DbInvadingWealthCaseView();
        invadingWealthCaseView.setAcceptDatetime(year);
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.selectsjSampleByYearAndCaseType(invadingWealthCaseView);

        return dbInvadingWealthCaseViewList;
    }

    /**
     * 送检样本数查询处理
     *
     * @return
     */
    private List<DbInvadingWealthCaseView> sjSampleCategoryCount(String year) throws JsonProcessingException {

        DbInvadingWealthCaseView invadingWealthCaseView = new DbInvadingWealthCaseView();
        invadingWealthCaseView.setAcceptDatetime(year);
        List<DbInvadingWealthCaseView> dbInvadingWealthCaseViewList = dbInvadingWealthCaseViewService.selectrkSampleByYearAndCaseType(invadingWealthCaseView);

        return dbInvadingWealthCaseViewList;
    }
}
