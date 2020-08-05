package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.model.PageInfo;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.view.DbComprehensiveCaseCountView;
import com.bazl.dna.lims.service.DbComprehensiveCaseCountViewService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/query")
public class ComprehensiveQueryController extends BaseController {

    @Autowired
    DbComprehensiveCaseCountViewService dbComprehensiveCaseCountViewService;
    @Autowired
    DictItemService dictItemService;

    /**
     * 综合查询
     *
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/comprehensiveQueryList")
    public ModelAndView caseAndBring(HttpServletRequest request, DbComprehensiveCaseCountView query, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        List<DictItem> dictItemList = dictItemService.selectCaseTypeList();
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> caaseCountResult = new HashMap<>();
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbComprehensiveCaseCountView> delagateOrgList = dbComprehensiveCaseCountViewService.selectDelagateOrgList();

            for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                //查询条件处理
                queryHandle(query);
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                //查询并处理lims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCountByOrgCodeList(query);
                //查询并处理sylims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectCountByOrgCodeSyList(dbComprehensiveCaseCountViewList, query);
                if (dbComprehensiveCaseCountViews.size() != 0) {
                    caaseCountResult.put(delagateOrg.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                }
            }

            List<DbComprehensiveCaseCountView> list = new ArrayList<>();
            int acceptCaseTotalcount = 0;
            int acceptWzSampleTotalcount = 0;
            int caseBookTtotalcount = 0;

            for (Object value : caaseCountResult.values()) {
                list = (List<DbComprehensiveCaseCountView>) value;
                for (DbComprehensiveCaseCountView list1 : list) {
                    acceptCaseTotalcount += list1.getCaseNumber();
                    acceptWzSampleTotalcount += list1.getWzSampleNumber();
                    caseBookTtotalcount += list1.getCaseBooksNumber();
                }
            }
            totalList.add(acceptCaseTotalcount);
            totalList.add(caseBookTtotalcount);
            totalList.add(acceptWzSampleTotalcount);

        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        if (dictItemList.size() != 0) {
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
        }

        view.addObject("dictResult", result);
        view.addObject("totalList", totalList);
        view.addObject("dbComprehensiveCaseCountViewList", caaseCountResult);
        view.addObject("query", query);
        view.setViewName("query/comprehensiveQueryList");
        return view;
    }

    /**
     * 综合查询统计导出excel
     *
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportComprehensiveQuery")
    public void exportCaseSort(HttpServletRequest request, HttpServletResponse response, DbComprehensiveCaseCountView query) {
        Map<String, Object> caaseCountResult = new HashMap<>();
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbComprehensiveCaseCountView> delagateOrgList = dbComprehensiveCaseCountViewService.selectDelagateOrgList();

            for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                //查询条件处理
                queryHandle(query);
                query.setDelegateOrg(delagateOrg.getDelegateOrg());
                //查询并处理lims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCountByOrgCodeList(query);
                //查询并处理sylims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectCountByOrgCodeSyList(dbComprehensiveCaseCountViewList, query);
                if (dbComprehensiveCaseCountViews.size() != 0) {
                    caaseCountResult.put(delagateOrg.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                }
            }

            List<DbComprehensiveCaseCountView> list = new ArrayList<>();
            int acceptCaseTotalcount = 0;
            int acceptWzSampleTotalcount = 0;
            int caseBookTtotalcount = 0;

            for (Object value : caaseCountResult.values()) {
                list = (List<DbComprehensiveCaseCountView>) value;
                for (DbComprehensiveCaseCountView list1 : list) {
                    acceptCaseTotalcount += list1.getCaseNumber();
                    acceptWzSampleTotalcount += list1.getWzSampleNumber();
                    caseBookTtotalcount += list1.getCaseBooksNumber();
                }
            }
            totalList.add(acceptCaseTotalcount);
            totalList.add(caseBookTtotalcount);
            totalList.add(acceptWzSampleTotalcount);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询需导出的数据错误！", e);
        }

        if (caaseCountResult.size() != 0) {
            String templateFilePath = request.getServletContext().getRealPath("templates/comprehensiveQueryExcel.xls");
            HSSFWorkbook workbook = null;

            try {
                FileInputStream fis = new FileInputStream(templateFilePath);
                workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row = null;
                HSSFCell cell = null;

                int startRow = 1;
                int idx = 0;
                List<DbComprehensiveCaseCountView> list = new ArrayList<>();
                for (Map.Entry<String, Object> result : caaseCountResult.entrySet()) {
                    result.getKey();
                    result.getValue();
                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }
                    //委托单位
                    cell = row.getCell(0);
                    if (cell == null) {
                        cell = row.createCell(0);
                    }
                    cell.setCellValue(result.getKey());

                    list = (List<DbComprehensiveCaseCountView>) result.getValue();
                    for (DbComprehensiveCaseCountView list1 : list) {
                        //受理案件数
                        cell = row.getCell(1);
                        if (cell == null) {
                            cell = row.createCell(1);
                        }
                        cell.setCellValue(list1.getCaseNumber());
                        //出具鉴定书数
                        cell = row.getCell(2);
                        if (cell == null) {
                            cell = row.createCell(2);
                        }
                        cell.setCellValue(list1.getCaseBooksNumber());
                        //送检物证检材数
                        cell = row.getCell(3);
                        if (cell == null) {
                            cell = row.createCell(3);
                        }
                        cell.setCellValue(list1.getWzSampleNumber());
                    }

                    idx++;
                }


                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue("总计");
                int i = 1;
                for (Integer total : totalList) {
                    cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellValue(total);

                    i++;
                    idx++;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_综合统计列表.xls", "utf-8"));
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
    }

    /**
     * 案件发送鉴定统计
     *
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("/sendOutBooksList")
    public ModelAndView sendOutBooksList(HttpServletRequest request, DbComprehensiveCaseCountView query) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        List<DictItem> dictItemList = dictItemService.selectCaseTypeList();
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> caaseCountResult = new HashMap<>();
        List<Integer> totalList = new ArrayList<>();
        //查询单位
        List<DbComprehensiveCaseCountView> delagateOrgList = dbComprehensiveCaseCountViewService.selectDelagateOrgList();
        try {
            queryHandle(query);
            if (StringUtils.isNotBlank(query.getDelegateOrg())) {
                for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                    if (delagateOrg.getDelegateOrg().equals(query.getDelegateOrg())) {
                        query.setDelegateOrgName(delagateOrg.getDelegateOrgName());
                    }
                }
                //查询并处理lims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCaseSendBooksCountList(query);
                //查询并处理sylims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectSyCaseSendBooksCountList(dbComprehensiveCaseCountViewList, query);
                if (dbComprehensiveCaseCountViews.size() != 0) {
                    caaseCountResult.put(query.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                }

            } else {
                for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                    DbComprehensiveCaseCountView comprehensiveCase = new DbComprehensiveCaseCountView();
                    comprehensiveCase.setDelegateOrg(delagateOrg.getDelegateOrg());
                    comprehensiveCase.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                    comprehensiveCase.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                    //查询并处理lims数据
                    List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCaseSendBooksCountList(comprehensiveCase);
                    //查询并处理sylims数据
                    List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectSyCaseSendBooksCountList(dbComprehensiveCaseCountViewList, comprehensiveCase);
                    if (dbComprehensiveCaseCountViews.size() != 0) {
                        caaseCountResult.put(delagateOrg.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                    }
                }
            }

            List<DbComprehensiveCaseCountView> list = new ArrayList<>();
            int caseBookTtotalcount = 0;

            for (Object value : caaseCountResult.values()) {
                list = (List<DbComprehensiveCaseCountView>) value;
                for (DbComprehensiveCaseCountView list1 : list) {
                    caseBookTtotalcount += list1.getCaseBooksNumber();
                }
            }
            totalList.add(caseBookTtotalcount);

        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        view.addObject("delagateOrgList",delagateOrgList);
        view.addObject("dictResult", result);
        view.addObject("totalList", totalList);
        view.addObject("dbComprehensiveCaseCountViewList", caaseCountResult);
        view.addObject("query", query);
        view.setViewName("query/sendOutBooksList");
        return view;
    }

    /**
     * 案件发送鉴定统计导出excel
     *
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportSendOutBooks")
    public void exportSendOutBooks(HttpServletRequest request, HttpServletResponse response, DbComprehensiveCaseCountView query) {
        Map<String, Object> caaseCountResult = new HashMap<>();
        List<Integer> totalList = new ArrayList<>();
        try {
            //查询单位
            List<DbComprehensiveCaseCountView> delagateOrgList = dbComprehensiveCaseCountViewService.selectDelagateOrgList();

            queryHandle(query);

            if (StringUtils.isNotBlank(query.getDelegateOrg())) {
                for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                    if (delagateOrg.getDelegateOrg().equals(query.getDelegateOrg())) {
                        query.setDelegateOrgName(delagateOrg.getDelegateOrgName());
                    }
                }
                //查询并处理lims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCaseSendBooksCountList(query);
                //查询并处理sylims数据
                List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectSyCaseSendBooksCountList(dbComprehensiveCaseCountViewList, query);
                if (dbComprehensiveCaseCountViews.size() != 0) {
                    caaseCountResult.put(query.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                }
            } else {
                for (DbComprehensiveCaseCountView delagateOrg : delagateOrgList) {
                    DbComprehensiveCaseCountView comprehensiveCase = new DbComprehensiveCaseCountView();
                    comprehensiveCase.setDelegateOrg(delagateOrg.getDelegateOrg());
                    comprehensiveCase.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                    comprehensiveCase.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                    //查询并处理lims数据
                    List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViewList = dbComprehensiveCaseCountViewService.selectCaseSendBooksCountList(comprehensiveCase);
                    //查询并处理sylims数据
                    List<DbComprehensiveCaseCountView> dbComprehensiveCaseCountViews = dbComprehensiveCaseCountViewService.selectSyCaseSendBooksCountList(dbComprehensiveCaseCountViewList, comprehensiveCase);
                    if (dbComprehensiveCaseCountViews.size() != 0) {
                        caaseCountResult.put(delagateOrg.getDelegateOrgName(), dbComprehensiveCaseCountViews);
                    }
                }
            }

            List<DbComprehensiveCaseCountView> list = new ArrayList<>();
            int caseBookTtotalcount = 0;

            for (Object value : caaseCountResult.values()) {
                list = (List<DbComprehensiveCaseCountView>) value;
                for (DbComprehensiveCaseCountView list1 : list) {
                    caseBookTtotalcount += list1.getCaseBooksNumber();
                }
            }
            totalList.add(caseBookTtotalcount);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询需导出的数据错误！", e);
        }

        if (caaseCountResult.size() != 0) {
            String templateFilePath = request.getServletContext().getRealPath("templates/sendOutBooksExcel.xls");
            HSSFWorkbook workbook = null;

            try {
                FileInputStream fis = new FileInputStream(templateFilePath);
                workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row = null;
                HSSFCell cell = null;

                int startRow = 2;
                int idx = 0;
                List<DbComprehensiveCaseCountView> list = new ArrayList<>();
                for (Map.Entry<String, Object> result : caaseCountResult.entrySet()) {
                    result.getKey();
                    result.getValue();
                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }
                    //委托单位
                    cell = row.getCell(0);
                    if (cell == null) {
                        cell = row.createCell(0);
                    }
                    cell.setCellValue(result.getKey());

                    list = (List<DbComprehensiveCaseCountView>) result.getValue();
                    for (DbComprehensiveCaseCountView list1 : list) {
                        //出具鉴定书数
                        cell = row.getCell(1);
                        if (cell == null) {
                            cell = row.createCell(1);
                        }
                        cell.setCellValue(list1.getCaseBooksNumber());

                    }

                    idx++;
                }

                for (Integer total : totalList) {
                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }
                    //委托单位
                    cell = row.getCell(0);
                    if (cell == null) {
                        cell = row.createCell(0);
                    }
                    cell.setCellValue("总计");

                    cell = row.getCell(1);
                    if (cell == null) {
                        cell = row.createCell(1);
                    }
                    cell.setCellValue(total);

                    idx++;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_案件发送鉴定统计列表.xls", "utf-8"));
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
                        logger.error("案件发送鉴定统计导出Excel错误！", ex);
                    }
                }
            }
        }
    }


    public DbComprehensiveCaseCountView queryHandle(DbComprehensiveCaseCountView query) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(query.getAcceptDatetimeStart())) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            query.setAcceptDatetimeStart(sdf.format(calendar.getTime()));
        } else {
            query.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
        }
        if (StringUtils.isBlank(query.getAcceptDatetimeEnd())) {
            query.setAcceptDatetimeEnd(sdf.format(new Date()));
        } else {
            query.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
        }
        return query;
    }

}
