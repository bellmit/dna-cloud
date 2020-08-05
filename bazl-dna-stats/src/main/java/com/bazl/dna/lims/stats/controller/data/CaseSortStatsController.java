package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.PageInfo;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.view.DbCaseSortStatsView;
import com.bazl.dna.lims.service.DbCaseSortStatsViewService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.service.LimsPersonInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/caseSort")
public class CaseSortStatsController extends BaseController {

    @Autowired
    DbCaseSortStatsViewService dbCaseSortStatsViewService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    DictItemService dictItemService;

    @RequestMapping("/caseSortStatsList")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public ModelAndView caseAndBring(HttpServletRequest request, DbCaseSortStatsView query, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        List<DbCaseSortStatsView> dbCaseSortStatsViewList = new ArrayList<>();

        List<DbCaseSortStatsView> delagateOrgList = dbCaseSortStatsViewService.selectDelagateOrgList();

        List<DictItem> dictItemList = dictItemService.selectCaseTypeList();
        int totalCount = 0;
        try {
            query = queryHandle(query);

            dbCaseSortStatsViewList = dbCaseSortStatsViewService.selectCaseSortStatsList(query, pageInfo);
            totalCount = dbCaseSortStatsViewService.selectCaseSortStatsCount(query);

            for (DbCaseSortStatsView dbCaseSortStatsView : dbCaseSortStatsViewList) {
                try {
                    if (null != dbCaseSortStatsView.getCaseNo()) {
                        dbCaseSortStatsView.setSampleCount(limsSampleInfoDnaService.selectSampleCountByCaseNo(dbCaseSortStatsView.getCaseNo()));
                    }
                } catch (Exception e) {
                    logger.info("查询案件下样本失败:" + e);
                }
            }

        } catch (Exception ex) {
            logger.info("查询案件分类统计失败:" + ex);
        }
        Map<String, Object> result = new HashMap<>();

        for (DictItem dictItem : dictItemList) {
            result.put(dictItem.getDictName(), dictItem);
        }

        for (DictItem dictItem : dictItemList) {
            List<DictItem> dictItemList1 = dictItemService.selectCaseTypeBydictItemIdList(dictItem.getParentId());
            for (String key : result.keySet()) {
                if (key.equals("盗窃")) {
                    result.put(key, dictItemList1);
                    break;
                }
            }
            break;
        }

        view.addObject("delagateOrgList",delagateOrgList);
        view.addObject("dictResult", result);
        view.addObject("dictItemList", dictItemList);
        view.addObject("dbCaseSortStatsViewList", dbCaseSortStatsViewList);
        view.addObject("pageInfo", pageInfo.updatePageInfo(totalCount));
        view.addObject("query", query);
        view.setViewName("caseSort/caseSortStatsList");
        return view;
    }

    @RequestMapping("/caseDetails")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public ModelAndView caseDetails(HttpServletRequest request, String caseNo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        List<LimsConsignmentInfo> limsConsignmentInfoList = new ArrayList<>();
        LimsConsignmentInfo limsConsignmentInfo = null;
        List<LimsPersonInfo> limsPersonInfoList = new ArrayList<>();
        LinkedList<LimsSampleInfoDna> limsSampleInfoDnaRyList = new LinkedList<>();
        LinkedList<LimsSampleInfoDna> limsSampleInfoDnaWzList = new LinkedList<>();

        try {
            if (null != caseNo) {
                limsCaseInfo = limsCaseInfoService.selectByCaseNo(caseNo);
                if (null != limsCaseInfo && null != limsCaseInfo.getCaseId()) {
                    List<DictItem> dictItemList = dictItemService.selectListByDictTypeCode(Constants.CASE_PROPERTY);
                    for (DictItem dictItem : dictItemList) {
                        if (dictItem.getDictCode().equals(limsCaseInfo.getCaseProperty())) {
                            limsCaseInfo.setCaseProperty(dictItem.getDictName());
                        }
                    }
                    logger.info("caseid="+limsCaseInfo.getCaseId());
                    limsConsignmentInfoList = limsConsignmentInfoService.selectByCaseId(limsCaseInfo.getCaseId());
                    
                    if (limsConsignmentInfoList != null && !limsConsignmentInfoList.isEmpty()) {
                    	limsConsignmentInfo = limsConsignmentInfoList.get(0);
                        List<DictItem> dictPositionList = dictItemService.selectListByDictTypeCode(Constants.POSITION_LIST);
                        for (DictItem dictPosition : dictPositionList) {
                            if (dictPosition.getDictCode().equals(limsConsignmentInfo.getDelegator1Position())) {
                                limsConsignmentInfo.setDelegator1Position(dictPosition.getDictName());
                            }
                            if (dictPosition.getDictCode().equals(limsConsignmentInfo.getDelegator2Position())) {
                                limsConsignmentInfo.setDelegator2Position(dictPosition.getDictName());
                            }
                        }

                        LimsPersonInfo limsPersonInfo = new LimsPersonInfo();
                        limsPersonInfo.setCaseId(limsCaseInfo.getCaseId());
                        limsPersonInfo.setConsignmentId(limsConsignmentInfo.getConsignmentId());
                        limsPersonInfoList = limsPersonInfoService.selectByCaseIdAndConsignmentId(limsPersonInfo);

                        LimsSampleInfoDna limsSampleInfoDna = new LimsSampleInfoDna();
                        limsSampleInfoDna.setCaseId(limsCaseInfo.getCaseId());
                        limsSampleInfoDna.setConsignmentId(limsConsignmentInfo.getConsignmentId());
                        limsSampleInfoDnaRyList = limsSampleInfoDnaService.selectByCaseIdAndRy(limsSampleInfoDna);
                        if (null != limsPersonInfoList && limsPersonInfoList.size() > 0 && null != limsSampleInfoDnaRyList && limsSampleInfoDnaRyList.size() > 0) {
                            for (LimsPersonInfo limsPersonInfo1 : limsPersonInfoList) {
                                for (LimsSampleInfoDna sampleInfoDna2 : limsSampleInfoDnaRyList) {
                                    if (limsPersonInfo1.getPersonId().equals(sampleInfoDna2.getLinkId())) {
                                        sampleInfoDna2.setPersonName(limsPersonInfo1.getPersonName());
                                    }
                                }
                            }
                        }
                        limsSampleInfoDnaWzList = limsSampleInfoDnaService.selectByCaseIdAndWz(limsSampleInfoDna);
                    }
                }
            }
        } catch (Exception ex) {
            logger.info("查询案件信息失败:" + ex);
        }

        view.addObject("limsCaseInfo", limsCaseInfo);
        view.addObject("limsConsignmentInfo", limsConsignmentInfo);
        view.addObject("limsPersonInfoList", limsPersonInfoList);
        view.addObject("limsSampleInfoDnaRyList", limsSampleInfoDnaRyList);
        view.addObject("limsSampleInfoDnaWzList", limsSampleInfoDnaWzList);
        view.setViewName("caseSort/caseDetails");
        return view;
    }

    @RequestMapping("/sampleDetails")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public ModelAndView sampleDetails(HttpServletRequest request, String caseNo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        List<DbCaseSortStatsView> dbCaseSampleList = new ArrayList<>();
        DbCaseSortStatsView dbCaseSortStatsView = new DbCaseSortStatsView();
        try {
            if (null != caseNo) {
                dbCaseSampleList = dbCaseSortStatsViewService.selectCaseSampleByCaseNo(caseNo);
                dbCaseSortStatsView = dbCaseSortStatsViewService.selectCaseByCaseNo(caseNo);
            }
        } catch (Exception ex) {
            logger.info("查询案件下的样本失败:" + ex);
        }

        view.addObject("dbCaseSortStatsView", dbCaseSortStatsView);
        view.addObject("dbCaseSampleList", dbCaseSampleList);
        view.setViewName("caseSort/sampleDetails");
        return view;
    }

    /**
     * 案件分类统计导出excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportCaseSort")
    @ResponseBody
    public void exportCaseSort(HttpServletRequest request, HttpServletResponse response, DbCaseSortStatsView query) {
        Map<String, Object> result = new HashMap<>();
        query = queryHandle(query);
        List<DbCaseSortStatsView> dbCaseSortStatsViewList = dbCaseSortStatsViewService.selectExportCaseSortList(query);

        if (dbCaseSortStatsViewList.size() != 0) {
            String templateFilePath = request.getServletContext().getRealPath("templates/caseSortExcel.xls");
            HSSFWorkbook workbook = null;

            try {
                FileInputStream fis = new FileInputStream(templateFilePath);
                workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row = null;
                HSSFCell cell = null;

                int startRow = 1;
                int idx = 0;
                for (DbCaseSortStatsView caseSort : dbCaseSortStatsViewList) {
                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }
                    //案件编号
                    cell = row.getCell(0);
                    if (cell == null) {
                        cell = row.createCell(0);
                    }
                    cell.setCellValue(caseSort.getCaseNo());
                    //案件名称
                    cell = row.getCell(1);
                    if (cell == null) {
                        cell = row.createCell(1);
                    }
                    cell.setCellValue(caseSort.getCaseName());
                    //简要案情
                    cell = row.getCell(2);
                    if (cell == null) {
                        cell = row.createCell(2);
                    }
                    cell.setCellValue(caseSort.getCaseDigest());
                    //委托单位
                    cell = row.getCell(3);
                    if (cell == null) {
                        cell = row.createCell(3);
                    }
                    cell.setCellValue(caseSort.getOrgName());

                    //现勘号
                    cell = row.getCell(4);
                    if (cell == null) {
                        cell = row.createCell(4);
                    }
                    cell.setCellValue(caseSort.getCaseXkNo());

                    //受理时间
                    cell = row.getCell(5);
                    if (cell == null) {
                        cell = row.createCell(5);
                    }
                    cell.setCellValue(caseSort.getAcceptDatetime());
                    //案发时间
                    cell = row.getCell(6);
                    if (cell == null) {
                        cell = row.createCell(6);
                    }
                    cell.setCellValue(caseSort.getCaseDatetime());
                    //样本编号
                    cell = row.getCell(7);
                    if (cell == null) {
                        cell = row.createCell(7);
                    }
                    cell.setCellValue(caseSort.getSampleNo());
                    //样本名称
                    cell = row.getCell(8);
                    if (cell == null) {
                        cell = row.createCell(8);
                    }
                    cell.setCellValue(caseSort.getSampleName());
                    //入库编号
                    cell = row.getCell(9);
                    if (cell == null) {
                        cell = row.createCell(9);
                    }
                    cell.setCellValue(caseSort.getGjkSysNo());
                    //比中样本编号
                    cell = row.getCell(10);
                    if (cell == null) {
                        cell = row.createCell(10);
                    }
                    cell.setCellValue(caseSort.getSampleBNo());
                    //比中样本名称
                    cell = row.getCell(11);
                    if (cell == null) {
                        cell = row.createCell(11);
                    }
                    cell.setCellValue(caseSort.getSampleBName());

                    idx++;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_案件分类统计列表.xls", "utf-8"));
                workbook.write(response.getOutputStream());
                result.put("success",true);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("导出Excel错误！", ex);
                result.put("success",false);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        logger.error("导出Excel错误！", ex);
                        result.put("success",false);
                    }

                }
            }
        }
    }


    public DbCaseSortStatsView queryHandle(DbCaseSortStatsView dbCaseSortStatsView) {

        if (StringUtils.isBlank(dbCaseSortStatsView.getCaseNo())) {
            dbCaseSortStatsView.setCaseNo(null);
        } else {
            dbCaseSortStatsView.setCaseNo(dbCaseSortStatsView.getCaseNo().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getCaseName())) {
            dbCaseSortStatsView.setCaseName(null);
        } else {
            dbCaseSortStatsView.setCaseName(dbCaseSortStatsView.getCaseName().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getCaseType())) {
            dbCaseSortStatsView.setCaseType(null);
        } else {
            dbCaseSortStatsView.setCaseType(dbCaseSortStatsView.getCaseType().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getCaseXkNo())) {
            dbCaseSortStatsView.setCaseXkNo(null);
        } else {
            dbCaseSortStatsView.setCaseXkNo(dbCaseSortStatsView.getCaseXkNo().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getSampleNo())) {
            dbCaseSortStatsView.setSampleNo(null);
        } else {
            dbCaseSortStatsView.setSampleNo(dbCaseSortStatsView.getSampleNo().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getSampleName())) {
            dbCaseSortStatsView.setSampleNo(null);
        } else {
            dbCaseSortStatsView.setSampleNo(dbCaseSortStatsView.getSampleNo().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getAcceptName())) {
            dbCaseSortStatsView.setAcceptName(null);
        } else {
            dbCaseSortStatsView.setAcceptName(dbCaseSortStatsView.getAcceptName().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getOrgName())) {
            dbCaseSortStatsView.setOrgName(null);
        } else {
            dbCaseSortStatsView.setOrgName(dbCaseSortStatsView.getOrgName().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getSampleName())) {
            dbCaseSortStatsView.setSampleName(null);
        } else {
            dbCaseSortStatsView.setSampleName(dbCaseSortStatsView.getSampleName().trim());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getCaseStatusName())) {
            dbCaseSortStatsView.setCaseStatusName(null);
        } else {
            dbCaseSortStatsView.setCaseStatusName(dbCaseSortStatsView.getCaseStatusName().trim());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(dbCaseSortStatsView.getAcceptDatetimeStart())) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            dbCaseSortStatsView.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
        } else {
            dbCaseSortStatsView.setAcceptDatetimeStart(dbCaseSortStatsView.getAcceptDatetimeStart());
        }
        if (StringUtils.isBlank(dbCaseSortStatsView.getAcceptDatetimeEnd())) {
            dbCaseSortStatsView.setAcceptDatetimeEnd(sdf.format(new Date()));
        } else {
            dbCaseSortStatsView.setAcceptDatetimeEnd(dbCaseSortStatsView.getAcceptDatetimeEnd());
        }

        String caseType = "";
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(dbCaseSortStatsView.getCaseType())) {
            List<DictItem> dictItemList = dictItemService.selectCaseTypeList();

            String[] caseProperty = dbCaseSortStatsView.getCaseType().split(",");
            for (int i = 0; i < caseProperty.length; i++) {
                for (DictItem dictItem : dictItemList) {
                    if (caseProperty[i].equals(dictItem.getDictName())) {
                        caseType = dictItem.getDictCode();
                        list.add(caseType);
                    }
                }
            }
            dbCaseSortStatsView.setCaseTypeList(list);
        }

        return dbCaseSortStatsView;
    }

    /**
     * 转换案件性质(lims->alims)
     * @param caseType
     * @return
     */
    /*private String changeCaseType(String caseType) {
        if (caseType.equals("13")) {
            caseType = "13,13-1,17";
        } else if (caseType.equals("4")) {
            caseType = "04,22";
        } else if (caseType.equals("16,37")) {
            caseType = "16";
        } else if (caseType.equals("3")) {
            caseType = "03";
        } else if (caseType.equals("19,190")) {
            caseType = "21";
        }
        return caseType;
    }*/

}
