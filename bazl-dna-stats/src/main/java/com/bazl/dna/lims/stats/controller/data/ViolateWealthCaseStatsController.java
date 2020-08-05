package com.bazl.dna.lims.stats.controller.data;

import com.bazl.dna.lims.stats.LimsStatsConfigure;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.PageInfo;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.view.DbInvadingWealthCaseView;
import com.bazl.dna.lims.model.view.DbStatsCaseInfoView;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.service.*;
import com.bazl.dna.lims.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 侵财类案件
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/violateWealth")
public class ViolateWealthCaseStatsController extends BaseController {

    @Autowired
    DbInvadingWealthCaseViewService dbInvadingWealthCaseViewService;
    @Autowired
    DbStatsCaseInfoViewService dbStatsCaseInfoViewService;

    @Autowired
    DictItemService dictItemService;
    @Autowired
    OrgInfoService orgInfoService;

    @Autowired
    LaboratoryInfoService laboratoryInfoService;

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsStatsConfigure limsStatsConfigure;

    /**
     * 送检案件数
     *
     * @param request
     * @param acceptOrgId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/violateWealthCaseStatsList")
    public ModelAndView caseAndBring(HttpServletRequest request, String acceptOrgId, String startDate, String endDate) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        List<String> assetsCaseSubPropertyList = limsStatsConfigure.getAssetsCaseSubPropertyList();
        List<DictItem> caseSubPropertyDictList = dictItemService.selectListByDictTypeCode(Constants.CASE_PROPERTY);
        List<DictItem> assetsDictList = new ArrayList<>();
        for(String assetsStr : assetsCaseSubPropertyList){
            for(DictItem di : caseSubPropertyDictList){
                if(di.getDictCode().equals(assetsStr)){
                    assetsDictList.add(di);
                }
            }
        }
        assetsDictList.add(new DictItem("all", "小计"));

        List<OrgInfo> allOrgList = orgInfoService.selectAll();
        List<OrgInfo> secondOrgList = allOrgList.stream().filter(org -> org.getOrgLevel().equals("2")).collect(Collectors.toList());

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("caseSubPropertyList", assetsCaseSubPropertyList);
        if(StringUtils.isNotBlank(acceptOrgId)) {
            queryParams.put("acceptOrgId", acceptOrgId);
        }
        if(StringUtils.isNotBlank(startDate)) {
            queryParams.put("startDate", startDate);
        }
        if(StringUtils.isNotBlank(endDate)) {
            queryParams.put("endDate", endDate);
        }

        List<Map<String, Object>> resultList = new ArrayList<>();


        List<CasePropertyStatsVo> casePropertyStatsVoList = limsCaseInfoService.selectAssetsCaseCount(queryParams);
        int globalTotalCount = 0;

        if(ListUtils.isNotNullAndEmptyList(casePropertyStatsVoList)){
            // 加 1 是为了算上“小计”这一列
            List<Integer> allTotalCountList = new ArrayList<>();

            for(OrgInfo orgInfo : secondOrgList){
                Map<String, Object> result = new HashMap<>();

                List<Integer> assetsStatsCountList = new ArrayList<>();
                List<CasePropertyStatsVo> tempList = casePropertyStatsVoList.stream().filter(stat -> stat.getOrgCode().equals(orgInfo.getOrgCode())).collect(Collectors.toList());

                int orgTotalCount = 0;
                int idx = 0;
                int lastRowTotalCnt = 0;
                for(String caseSubProperty : assetsCaseSubPropertyList){
                    if(allTotalCountList.size() <= idx){
                        allTotalCountList.add(idx, 0);
                    }
                    lastRowTotalCnt = allTotalCountList.get(idx);

                    Optional<CasePropertyStatsVo> optional = tempList.stream().filter(stat -> (StringUtils.isNotBlank(stat.getCaseProperty()) && stat.getCaseProperty().equals(caseSubProperty))).findFirst();
                    if(optional != null && optional.isPresent()){
                        CasePropertyStatsVo tmpStat = optional.get();
                        assetsStatsCountList.add(tmpStat.getCaseCount());
                        orgTotalCount += tmpStat.getCaseCount();
                        lastRowTotalCnt += tmpStat.getCaseCount();
                    }else{
                        assetsStatsCountList.add(0);
                    }

                    allTotalCountList.set(idx, lastRowTotalCnt);
                    idx++;
                }

                globalTotalCount += orgTotalCount;
                assetsStatsCountList.add(orgTotalCount);

                if(allTotalCountList.size() <= idx){
                    allTotalCountList.add(idx, 0);
                }
                lastRowTotalCnt = allTotalCountList.get(idx);
                allTotalCountList.set(idx, lastRowTotalCnt+orgTotalCount);

                result.put("orgCode", orgInfo.getOrgCode());
                result.put("orgName", orgInfo.getOrgName());
                result.put("caseCountList", assetsStatsCountList);
                resultList.add(result);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("orgName", "总计");
            result.put("caseCountList", allTotalCountList);
            resultList.add(result);
        }

        List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();

        view.addObject("acceptOrgId", acceptOrgId);
        view.addObject("startDate", startDate);
        view.addObject("endDate", endDate);
        view.addObject("laboratoryInfoList", laboratoryInfoList);
        view.addObject("assetsDictList", assetsDictList);
        view.addObject("caseStatsResultList", resultList);
        view.addObject("globalTotalCount", globalTotalCount);
        view.setViewName("violateWealth/violateWealthCaseStatsList");
        return view;
    }

    /**
     * 查询入库物证案件数
     *
     * @param request
     * @return
     */
    @RequestMapping("/ruWzcaseCountList")
    @ResponseBody
    public Map<String, Object> ruWzcaseCount(HttpServletRequest request, String acceptDatetimeStart, String acceptDatetimeEnd) {
        DbInvadingWealthCaseView query = new DbInvadingWealthCaseView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(acceptDatetimeStart)) {
            query.setAcceptDatetimeStart(acceptDatetimeStart);
        }else{
			 Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
			query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
		}
        if (StringUtils.isNotBlank(acceptDatetimeEnd)) {
            query.setAcceptDatetimeEnd(acceptDatetimeEnd);
        }else{
			 query.setAcceptDatetimeStart(sdf.format(new Date()));
		}
        Map<String, Object> returnResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        int totalCount = 0;
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbInvadingWealthCaseView> delagateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
            //查询送检案件数
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.rkWzCaseCountList(query);

                if (dbSjCaseCountViewList.size()>0){
                    DbInvadingWealthCaseView caseView = new DbInvadingWealthCaseView();
                    int cnt = 0;
                    for (DbInvadingWealthCaseView dbSjCaseCountView:dbSjCaseCountViewList){
                        cnt += dbSjCaseCountView.getNumber();
                    }
                    caseView.setNumber(cnt);
                    dbSjCaseCountViewList.add(caseView);
                }

                if (dbSjCaseCountViewList.size() != 0) {
                    result.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;
            int cntTotalcount = 0;

            for (Object value : result.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    totalCount += list1.getNumber();
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }else if (null == list1.getCaseType()) {
                        cntTotalcount+=list1.getNumber();
                    }
                }
            }
            totalList.add(rsDqTotalcount);
            totalList.add(qdQjTtotalcount);
            totalList.add(dqJcTtotalcount);
            totalList.add(dqCnTtotalcount);
            totalList.add(dqGdTtotalcount);
            totalList.add(qtDqTtotalcount);
            totalList.add(zqTtotalcount);
            totalList.add(cntTotalcount);
        } catch (Exception ex) {
            logger.info("查询入库物证案件数失败:" + ex);
        }
        returnResult.put("success", true);
        returnResult.put("acceptDatetimeStart", acceptDatetimeStart);
        returnResult.put("acceptDatetimeEnd", acceptDatetimeEnd);
        returnResult.put("totalCount", totalCount);
        returnResult.put("totalList", totalList);
        returnResult.put("dbSjCaseCountViewList", result);
        return returnResult;
    }

    /**
     * 查询送检样本数
     *
     * @param request
     * @return
     */
    @RequestMapping("/sjSampleCountList")
    @ResponseBody
    public Map<String, Object> sjSampleCountList(HttpServletRequest request, String acceptDatetimeStart, String acceptDatetimeEnd) {
        DbInvadingWealthCaseView query = new DbInvadingWealthCaseView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(acceptDatetimeStart)) {
            query.setAcceptDatetimeStart(acceptDatetimeStart);
        }else{
			 Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
			query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
		}
        if (StringUtils.isNotBlank(acceptDatetimeEnd)) {
            query.setAcceptDatetimeEnd(acceptDatetimeEnd);
        }else{
			 query.setAcceptDatetimeStart(sdf.format(new Date()));
		}
        Map<String, Object> returnResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        int totalCount = 0;
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbInvadingWealthCaseView> delagateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.sjSampleCountList(query);

                if (dbSjCaseCountViewList.size()>0){
                    DbInvadingWealthCaseView caseView = new DbInvadingWealthCaseView();
                    int cnt = 0;
                    for (DbInvadingWealthCaseView dbSjCaseCountView:dbSjCaseCountViewList){
                        cnt += dbSjCaseCountView.getNumber();
                    }
                    caseView.setNumber(cnt);
                    dbSjCaseCountViewList.add(caseView);
                }

                if (dbSjCaseCountViewList.size() != 0) {
                    result.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;
            int cntTotalcount = 0;

            for (Object value : result.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    totalCount += list1.getNumber();
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }else if (null == list1.getCaseType()) {
                        cntTotalcount+=list1.getNumber();
                    }
                }
            }
            totalList.add(rsDqTotalcount);
            totalList.add(qdQjTtotalcount);
            totalList.add(dqJcTtotalcount);
            totalList.add(dqCnTtotalcount);
            totalList.add(dqGdTtotalcount);
            totalList.add(qtDqTtotalcount);
            totalList.add(zqTtotalcount);
            totalList.add(cntTotalcount);
        } catch (Exception ex) {
            logger.info("查询送检样本数失败:" + ex);
        }
        returnResult.put("success", true);
        returnResult.put("acceptDatetimeStart", acceptDatetimeStart);
        returnResult.put("acceptDatetimeEnd", acceptDatetimeEnd);
        returnResult.put("totalCount", totalCount);
        returnResult.put("totalList", totalList);
        returnResult.put("dbSjCaseCountViewList", result);
        return returnResult;
    }

    /**
     * 查询入库物证案件数
     *
     * @param request
     * @return
     */
    @RequestMapping("/ruSampleCountList")
    @ResponseBody
    public Map<String, Object> ruSampleCountList(HttpServletRequest request, String acceptDatetimeStart, String acceptDatetimeEnd) {
        DbInvadingWealthCaseView query = new DbInvadingWealthCaseView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(acceptDatetimeStart)) {
            query.setAcceptDatetimeStart(acceptDatetimeStart);
        }else{
			 Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
			query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
		}
        if (StringUtils.isNotBlank(acceptDatetimeEnd)) {
            query.setAcceptDatetimeEnd(acceptDatetimeEnd);
        }else{
			 query.setAcceptDatetimeStart(sdf.format(new Date()));
		}
        Map<String, Object> returnResult = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        int totalCount = 0;
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbInvadingWealthCaseView> delagateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.rkSampleCountList(query);

                if (dbSjCaseCountViewList.size()>0){
                    DbInvadingWealthCaseView caseView = new DbInvadingWealthCaseView();
                    int cnt = 0;
                    for (DbInvadingWealthCaseView dbSjCaseCountView:dbSjCaseCountViewList){
                        cnt += dbSjCaseCountView.getNumber();
                    }
                    caseView.setNumber(cnt);
                    dbSjCaseCountViewList.add(caseView);
                }

                if (dbSjCaseCountViewList.size() != 0) {
                    result.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;
            int cntTotalcount = 0;

            for (Object value : result.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    totalCount += list1.getNumber();
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }else if (null == list1.getCaseType()) {
                        cntTotalcount+=list1.getNumber();
                    }
                }
            }
            totalList.add(rsDqTotalcount);
            totalList.add(qdQjTtotalcount);
            totalList.add(dqJcTtotalcount);
            totalList.add(dqCnTtotalcount);
            totalList.add(dqGdTtotalcount);
            totalList.add(qtDqTtotalcount);
            totalList.add(zqTtotalcount);
            totalList.add(cntTotalcount);
        } catch (Exception ex) {
            logger.info("查询入库物证案件数失败:" + ex);
        }
        returnResult.put("success", true);
        returnResult.put("acceptDatetimeStart", acceptDatetimeStart);
        returnResult.put("acceptDatetimeEnd", acceptDatetimeEnd);
        returnResult.put("totalCount", totalCount);
        returnResult.put("totalList", totalList);
        returnResult.put("dbSjCaseCountViewList", result);
        return returnResult;
    }

    /**
     * DNA有效比中率
     * @param request
     * @param acceptDatetimeStart
     * @param acceptDatetimeEnd
     * @return
     */
    @RequestMapping("/effectiveRatioList")
    @ResponseBody
    public Map<String, Object> validComparisonCountList(HttpServletRequest request, String acceptDatetimeStart, String acceptDatetimeEnd) {
        DbStatsCaseInfoView query = new DbStatsCaseInfoView();
        Map<String, Object> returnResult = new HashMap<>();
        List<DbStatsCaseInfoView> dbStatsCaseInfoViewList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (StringUtils.isBlank(acceptDatetimeStart)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
        } else {
            query.setAcceptDatetimeStart(acceptDatetimeStart);
        }
        if (StringUtils.isBlank(acceptDatetimeEnd)) {
            query.setAcceptDatetimeEnd(sdf.format(new Date()));
        } else {
            query.setAcceptDatetimeEnd(acceptDatetimeEnd);
        }

        try {
            //查询单位
            List<DbStatsCaseInfoView> delagateOrgList = dbStatsCaseInfoViewService.selecDelegateOrg();

            for(DbStatsCaseInfoView delagateOrg:delagateOrgList){
                DbStatsCaseInfoView statsCaseInfoView = new DbStatsCaseInfoView();
                statsCaseInfoView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                statsCaseInfoView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                statsCaseInfoView.setDelegateOrg(delagateOrg.getDelegateOrg());
                statsCaseInfoView.setDelegateOrgName(delagateOrg.getDelegateOrgName());
                statsCaseInfoView = dbStatsCaseInfoViewService.selectEffectiveRatio(statsCaseInfoView);
                dbStatsCaseInfoViewList.add(statsCaseInfoView);
            }

        } catch (Exception ex) {
            logger.info("查询DNA有效比中率错误:" + ex);
        }
        returnResult.put("success", true);
        returnResult.put("acceptDatetimeStart", acceptDatetimeStart);
        returnResult.put("acceptDatetimeEnd", acceptDatetimeEnd);
        returnResult.put("dbStatsCaseInfoViewList", dbStatsCaseInfoViewList);
        return returnResult;
    }

    /**
     * DNA有效比中率导出
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/effectiveRatio")
    public void effectiveRatio(HttpServletRequest request, HttpServletResponse response, DbStatsCaseInfoView query) {

        Map<String, Object> returnResult = new HashMap<>();
        List<DbStatsCaseInfoView> dbStatsCaseInfoViewList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (StringUtils.isBlank(query.getAcceptDatetimeStart())) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
        } else {
            query.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
        }
        if (StringUtils.isBlank(query.getAcceptDatetimeEnd())) {
            query.setAcceptDatetimeEnd(sdf.format(new Date()));
        } else {
            query.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
        }
        try {
            //查询单位
            List<DbStatsCaseInfoView> delagateOrgList = dbStatsCaseInfoViewService.selecDelegateOrg();

            for(DbStatsCaseInfoView delagateOrg:delagateOrgList){
                DbStatsCaseInfoView statsCaseInfoView = new DbStatsCaseInfoView();
                statsCaseInfoView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                statsCaseInfoView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                statsCaseInfoView.setDelegateOrg(delagateOrg.getDelegateOrg());
                statsCaseInfoView.setDelegateOrgName(delagateOrg.getDelegateOrgName());
                statsCaseInfoView = dbStatsCaseInfoViewService.selectEffectiveRatio(statsCaseInfoView);
                dbStatsCaseInfoViewList.add(statsCaseInfoView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出查询DNA有效比中率错误！", e);
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/effectiveRatioExcel.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (DbStatsCaseInfoView statsCaseInfoView:dbStatsCaseInfoViewList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(statsCaseInfoView.getDelegateOrgName());

                //案件总数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(statsCaseInfoView.getCaseCnt());

                //入库案件总数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(statsCaseInfoView.getRkCaseCnt());

                //物证入库比中嫌疑人案件数
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(statsCaseInfoView.getBzXyrCaseCnt());

                //物证串并案件数
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(statsCaseInfoView.getBzWzCaseCnt());

                //其他入库案件数
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(statsCaseInfoView.getOtherCaseCnt());

                idx++;
            }

            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sd.format(new Date()) + URLEncoder.encode("_DNA有效比中统计列表.xls", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("导出Excel错误！", ex);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("导出DNA有效比中错误！", ex);
                }
            }
        }

    }


    /**
     * 侵财统计导出excel
     *
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportInvadingWealth")
    public void exportInvadingWealth(HttpServletRequest request, HttpServletResponse response, DbInvadingWealthCaseView query) {
        List<String> totalList = new ArrayList<>();
        Map<String,List> totalMaplist = new HashMap<>();
        Map<String, Object> sjCaseResult = new HashMap<>();
        Map<String, Object> rkWzCaseResult = new HashMap<>();
        Map<String, Object> sjSampleResult = new HashMap<>();
        Map<String, Object> rkSampleResult = new HashMap<>();
        //查询单位
        List<DbInvadingWealthCaseView> delagateOrgList = dbInvadingWealthCaseViewService.selectDelagateOrgList();
        //送检案件数
        try {
            //案件受理数
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.sjCaseCountList(query);
                if (dbSjCaseCountViewList.size() != 0) {
                    sjCaseResult.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;

            for (Object value : sjCaseResult.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }
                }
            }
            totalList.add(String.valueOf(rsDqTotalcount));
            totalList.add(String.valueOf(qdQjTtotalcount));
            totalList.add(String.valueOf(dqJcTtotalcount));
            totalList.add(String.valueOf(dqCnTtotalcount));
            totalList.add(String.valueOf(dqGdTtotalcount));
            totalList.add(String.valueOf(qtDqTtotalcount));
            totalList.add(String.valueOf(zqTtotalcount));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询送检案件数错误！", e);
        }

        //物证检材入库案件数
        try {
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.rkWzCaseCountList(query);
                if (dbSjCaseCountViewList.size() != 0) {
                    rkWzCaseResult.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;

            for (Object value : rkWzCaseResult.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }
                }
            }
            totalList.add(String.valueOf(rsDqTotalcount));
            totalList.add(String.valueOf(qdQjTtotalcount));
            totalList.add(String.valueOf(dqJcTtotalcount));
            totalList.add(String.valueOf(dqCnTtotalcount));
            totalList.add(String.valueOf(dqGdTtotalcount));
            totalList.add(String.valueOf(qtDqTtotalcount));
            totalList.add(String.valueOf(zqTtotalcount));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询物证检材入库案件数错误！", e);
        }

        //送检样本数
        try {
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.sjSampleCountList(query);
                if (dbSjCaseCountViewList.size() != 0) {
                    sjSampleResult.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;

            for (Object value : sjSampleResult.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }
                }
            }
            totalList.add(String.valueOf(rsDqTotalcount));
            totalList.add(String.valueOf(qdQjTtotalcount));
            totalList.add(String.valueOf(dqJcTtotalcount));
            totalList.add(String.valueOf(dqCnTtotalcount));
            totalList.add(String.valueOf(dqGdTtotalcount));
            totalList.add(String.valueOf(qtDqTtotalcount));
            totalList.add(String.valueOf(zqTtotalcount));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询送检样本数数错误！", e);
        }

        //入库样本数
        try {
            for (DbInvadingWealthCaseView delagateOrg : delagateOrgList) {
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                query = queryHandle(query);
                List<DbInvadingWealthCaseView> dbSjCaseCountViewList = dbInvadingWealthCaseViewService.rkSampleCountList(query);
                if (dbSjCaseCountViewList.size() != 0) {
                    rkSampleResult.put(delagateOrg.getDelegateOrgName(), dbSjCaseCountViewList);
                }
            }

            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            int rsDqTotalcount = 0;
            int qdQjTtotalcount = 0;
            int dqJcTtotalcount = 0;
            int dqCnTtotalcount = 0;
            int dqGdTtotalcount = 0;
            int qtDqTtotalcount = 0;
            int zqTtotalcount = 0;

            for (Object value : rkSampleResult.values()) {
                list = (List<DbInvadingWealthCaseView>) value;
                for (DbInvadingWealthCaseView list1 : list) {
                    if (Constants.caseTypeRs.equals(list1.getCaseType())) {
                        rsDqTotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQdQj.equals(list1.getCaseType())) {
                        qdQjTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqJc.equals(list1.getCaseType())) {
                        dqJcTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqCn.equals(list1.getCaseType())) {
                        dqCnTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeDqGd.equals(list1.getCaseType())) {
                        dqGdTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeQtDq.equals(list1.getCaseType())) {
                        qtDqTtotalcount += list1.getNumber();
                    } else if (Constants.caseTypeZp.equals(list1.getCaseType())) {
                        zqTtotalcount += list1.getNumber();
                    }
                }
            }
            totalList.add(String.valueOf(rsDqTotalcount));
            totalList.add(String.valueOf(qdQjTtotalcount));
            totalList.add(String.valueOf(dqJcTtotalcount));
            totalList.add(String.valueOf(dqCnTtotalcount));
            totalList.add(String.valueOf(dqGdTtotalcount));
            totalList.add(String.valueOf(qtDqTtotalcount));
            totalList.add(String.valueOf(zqTtotalcount));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询送检样本数数错误！", e);
        }
        totalMaplist.put("总数",totalList);

        String templateFilePath = request.getServletContext().getRealPath("templates/invadingWealthStatsExcel.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            List<DbInvadingWealthCaseView> list = new ArrayList<>();
            for (Map.Entry<String, Object> sjCase : sjCaseResult.entrySet()) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(sjCase.getKey());

                list = (List<DbInvadingWealthCaseView>) sjCase.getValue();
                Integer i = 1;
                for (DbInvadingWealthCaseView list1 : list) {
                    cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellValue(list1.getNumber());
                    i++;
                }
                for (Map.Entry<String, Object> rkWzCase : rkWzCaseResult.entrySet()) {
                    if (rkWzCase.getKey().equals(sjCase.getKey())) {
                        list = (List<DbInvadingWealthCaseView>) rkWzCase.getValue();
                        for (DbInvadingWealthCaseView list1 : list) {
                            cell = row.getCell(i);
                            if (cell == null) {
                                cell = row.createCell(i);
                            }
                            cell.setCellValue(list1.getNumber());
                            i++;
                        }
                    }
                }

                for (Map.Entry<String, Object> sjSample : sjSampleResult.entrySet()) {
                    if (sjSample.getKey().equals(sjCase.getKey())) {
                        list = (List<DbInvadingWealthCaseView>) sjSample.getValue();
                        for (DbInvadingWealthCaseView list1 : list) {
                            cell = row.getCell(i);
                            if (cell == null) {
                                cell = row.createCell(i);
                            }
                            cell.setCellValue(list1.getNumber());
                            i++;
                        }
                    }
                }

                for (Map.Entry<String, Object> rkSample : rkSampleResult.entrySet()) {
                    if (rkSample.getKey().equals(sjCase.getKey())) {
                        list = (List<DbInvadingWealthCaseView>) rkSample.getValue();
                        for (DbInvadingWealthCaseView list1 : list) {
                            cell = row.getCell(i);
                            if (cell == null) {
                                cell = row.createCell(i);
                            }
                            cell.setCellValue(list1.getNumber());
                            i++;
                        }
                    }
                }
                idx++;
            }
            List newList;
            for (Map.Entry<String, List> total : totalMaplist.entrySet()) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(total.getKey());
                newList = total.getValue();
                Integer i = 1;
                for (Object list1 : newList) {
                    cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellValue(String.valueOf(list1));
                    i++;
                }

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_侵财类案件统计列表.xls", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("导出Excel错误！", ex);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("导出Excel错误！", ex);
                }
            }
        }

    }

    public DbInvadingWealthCaseView queryHandle(DbInvadingWealthCaseView dbInvadingWealthCaseView) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (StringUtils.isBlank(dbInvadingWealthCaseView.getAcceptDatetimeStart())) {
           Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
		   dbInvadingWealthCaseView.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
        } else {
            dbInvadingWealthCaseView.setAcceptDatetimeStart(dbInvadingWealthCaseView.getAcceptDatetimeStart());
        }
        if (StringUtils.isBlank(dbInvadingWealthCaseView.getAcceptDatetimeEnd())) {
            dbInvadingWealthCaseView.setAcceptDatetimeEnd(sdf.format(new Date()));
        } else {
            dbInvadingWealthCaseView.setAcceptDatetimeEnd(dbInvadingWealthCaseView.getAcceptDatetimeEnd());
        }
        return dbInvadingWealthCaseView;
    }

}
