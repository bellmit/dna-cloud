package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.view.DbIvDnaActionRateView;
import com.bazl.dna.lims.service.DbIvDnaActionRateViewService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/actionRate")
public class DnaActionRateStatsController extends BaseController {

    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    DbIvDnaActionRateViewService dbIvDnaActionRateViewService;

    /**
     * 侵财类案件DNA综合作用率统计
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("/violateWealthCaseStatsList")
    public ModelAndView violateWealthCaseStatsList(HttpServletRequest request, DbIvDnaActionRateView query) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        //查询单位列表
        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

        List<DbIvDnaActionRateView> dbIvDnaActionRateViewList = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(query.getDelegateOrgCode())){
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query = resetQuery(query);
                try {
                    query = dbIvDnaActionRateViewService.selectDnaActionRate(query);
                    dbIvDnaActionRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("查询一个分局数据出错");
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDnaActionRateView   dnaActionRate = new DbIvDnaActionRateView();
                    dnaActionRate.setDelagateDatetimeStart(query.getDelagateDatetimeStart());
                    dnaActionRate.setDelagateDatetimeEnd(query.getDelagateDatetimeEnd());
                    dnaActionRate.setDelegateOrgCode(orgInfo.getOrgCode());
                    dnaActionRate.setDelegateOrgName(orgInfo.getOrgName());

                    dnaActionRate = resetQuery(dnaActionRate);

                    try {
                        query = dbIvDnaActionRateViewService.selectDnaActionRate(dnaActionRate);
                        dbIvDnaActionRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("查询全部数据出错");
                    }

                }
                query.setDelegateOrgCode(null);
            }
        } catch (Exception ex) {
            logger.info("查询侵财类案件DNA综合作用率出错:" + ex);
        }

        view.addObject("dbIvDnaActionRateViewList",dbIvDnaActionRateViewList);
        view.addObject("orgInfoList",orgInfoList);
        view.addObject("query", query);
        view.setViewName("actionRate/dnaActionRateStatsList");
        return view;
    }

    /**
     * 导出侵财类案件DNA综合作用率统计excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/dnaActionrateExpotr")
    public void dnaActionrateExpotr(HttpServletRequest request, HttpServletResponse response, DbIvDnaActionRateView query) {

        List<DbIvDnaActionRateView> dbIvDnaActionRateViewList = new ArrayList<>();

        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

            if (StringUtils.isNotBlank(query.getDelegateOrgCode())) {
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query = resetQuery(query);
                try {
                    query = dbIvDnaActionRateViewService.selectDnaActionRate(query);
                    dbIvDnaActionRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("导出查询一个分局数据出错");
                }

            } else {
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDnaActionRateView   dnaActionRate = new DbIvDnaActionRateView();
                    dnaActionRate.setDelagateDatetimeStart(query.getDelagateDatetimeStart());
                    dnaActionRate.setDelagateDatetimeEnd(query.getDelagateDatetimeEnd());
                    dnaActionRate.setDelegateOrgCode(orgInfo.getOrgCode());
                    dnaActionRate.setDelegateOrgName(orgInfo.getOrgName());

                    dnaActionRate = resetQuery(dnaActionRate);

                    try {
                        query = dbIvDnaActionRateViewService.selectDnaActionRate(dnaActionRate);
                        dbIvDnaActionRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("导出查询全部数据出错");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/dnaActionrateExpotrStats.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (DbIvDnaActionRateView instoredStats:dbIvDnaActionRateViewList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(instoredStats.getDelegateOrgName());

                //送检案件数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getSjCaseCount()));

                //出鉴定书数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getIssueCaseCount()));

                //有物证入库但未出鉴定书的案件数
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getRkNotIssueCaseCount()));

                //未出鉴定的案件数
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getNotIssueCaseCount()));

                //计算结果
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(instoredStats.getComputedResult());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_侵财类案件DNA综合作用率统计列表.xls", "utf-8"));
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
                    logger.error("导出侵财类案件DNA综合作用率统计excel错误！", ex);
                }
            }
        }

    }

    /**
     * 侵财类案件DNA检验率统计
     * @param request
     * @return
     */
    @RequestMapping("/dnaTestRateStatsList")
    public ModelAndView dnaTestRateStatsList(HttpServletRequest request, DbIvDnaActionRateView query) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }
        //查询单位列表
        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

        List<DbIvDnaActionRateView> dbIvDnaTestRateViewList = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(query.getDelegateOrgCode())){
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query = resetQuery(query);
                try {
                    query = dbIvDnaActionRateViewService.selectDnaTestRate(query);
                    dbIvDnaTestRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("DNA检验率查询一个分局数据出错");
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDnaActionRateView  dnaTestRate = new DbIvDnaActionRateView();
                    dnaTestRate.setDelagateDatetimeStart(query.getDelagateDatetimeStart());
                    dnaTestRate.setDelagateDatetimeEnd(query.getDelagateDatetimeEnd());
                    dnaTestRate.setDelegateOrgCode(orgInfo.getOrgCode());
                    dnaTestRate.setDelegateOrgName(orgInfo.getOrgName());

                    dnaTestRate = resetQuery(dnaTestRate);

                    try {
                        query = dbIvDnaActionRateViewService.selectDnaTestRate(dnaTestRate);
                        dbIvDnaTestRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("DNA检验率查询一个分局数据出错");
                    }

                }
                query.setDelegateOrgCode(null);
            }
        } catch (Exception ex) {
            logger.info("查询侵财类案件DNA检验率出错:" + ex);
        }

        view.addObject("dbIvDnaTestRateViewList",dbIvDnaTestRateViewList);
        view.addObject("orgInfoList",orgInfoList);
        view.addObject("query", query);
        view.setViewName("actionRate/dnaTestRateStatsList");
        return view;
    }


    /**
     * 导出侵财类案件DNA检验率统计excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/dnaTestRateExport")
    public void dnaTestRateExport(HttpServletRequest request, HttpServletResponse response, DbIvDnaActionRateView query) {

        List<DbIvDnaActionRateView> dbIvDnaActionRateViewList = new ArrayList<>();

        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

            if (StringUtils.isNotBlank(query.getDelegateOrgCode())) {
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query = resetQuery(query);
                try {
                    query = dbIvDnaActionRateViewService.selectDnaTestRate(query);
                    dbIvDnaActionRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("DNA检验率导出查询一个分局数据出错");
                }

            } else {
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDnaActionRateView   dnaActionRate = new DbIvDnaActionRateView();
                    dnaActionRate.setDelagateDatetimeStart(query.getDelagateDatetimeStart());
                    dnaActionRate.setDelagateDatetimeEnd(query.getDelagateDatetimeEnd());
                    dnaActionRate.setDelegateOrgCode(orgInfo.getOrgCode());
                    dnaActionRate.setDelegateOrgName(orgInfo.getOrgName());

                    dnaActionRate = resetQuery(dnaActionRate);

                    try {
                        query = dbIvDnaActionRateViewService.selectDnaTestRate(dnaActionRate);
                        dbIvDnaActionRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("DNA检验率导出查询全部数据出错");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/dnaTestRateExpotrStats.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (DbIvDnaActionRateView instoredStats:dbIvDnaActionRateViewList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(instoredStats.getDelegateOrgName());

                //送检案件数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getSjCaseCount()));

                //有物证入库案件数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(Integer.parseInt(instoredStats.getRkCaseCount()));

                //计算结果
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(instoredStats.getComputedResult());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_侵财类案件DNA检验率统计列表.xls", "utf-8"));
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
                    logger.error("导出侵财类案件DNA综合作用率统计excel错误！", ex);
                }
            }
        }

    }

    private DbIvDnaActionRateView resetQuery(DbIvDnaActionRateView query) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null != query.getDelagateDatetimeStart()) {
            query.setDelagateDatetimeStart(query.getDelagateDatetimeStart());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            query.setDelagateDatetimeStart(sdf.format(calendar.getTime()));
        }
        if (null != query.getDelagateDatetimeEnd()) {
            query.setDelagateDatetimeEnd(query.getDelagateDatetimeEnd());
        } else {

            query.setDelagateDatetimeEnd(sdf.format(new Date()));
        }

        return query;
    }


}
