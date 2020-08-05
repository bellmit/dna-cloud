package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.view.DbIvDetectionRateView;
import com.bazl.dna.lims.service.DbIvDetectionRateViewService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/actionRate")
public class IwRateStatsController extends BaseController {

    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    DbIvDetectionRateViewService dbIvDetectionRateViewService;

    /**
     * 侵财类案件脱落细胞检出率
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("/exfoliatedCellsDetectionRateList")
    public ModelAndView exfoliatedCellsDetectionRateList(HttpServletRequest request, DbIvDetectionRateView query) {
        ModelAndView view = new ModelAndView();
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        //查询单位列表
        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
        List<DbIvDetectionRateView> dbIvDetectionRateViewList = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(query.getDelegateOrg())){
                for (OrgInfo orgInfo:orgInfoList){
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrg())){
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query.setSampleType(Constants.SAMPLE_TYPE_TLXB);
                query = resetQuery(query);
                try {
                    query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(query,Constants.SAMPLE_TYPE_TLXB);
                    dbIvDetectionRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("查询单个分局脱落细胞检出率出错!"+e);
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDetectionRateView dbIvDetectionRate = new DbIvDetectionRateView();
                    dbIvDetectionRate.setDelegateAtStart(query.getDelegateAtStart());
                    dbIvDetectionRate.setDelegateAtEnd(query.getDelegateAtEnd());
                    dbIvDetectionRate.setDelegateOrg(orgInfo.getOrgCode());
                    dbIvDetectionRate.setDelegateOrgName(orgInfo.getOrgName());
                    dbIvDetectionRate.setSampleType(Constants.SAMPLE_TYPE_TLXB);
                    dbIvDetectionRate = resetQuery(dbIvDetectionRate);

                    try {
                        query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(dbIvDetectionRate,Constants.SAMPLE_TYPE_TLXB);
                        dbIvDetectionRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("查询脱落细胞检出率出错！"+e);
                    }
                }
                query.setDelegateOrg(null);
            }

        } catch (Exception ex) {
            logger.info("查询侵财类案件脱落细胞检出率出错:" + ex);
        }

        //排序
        Collections.sort(dbIvDetectionRateViewList, new SortByEvidenceNo());


        for(DbIvDetectionRateView dbIvDetectionRate:dbIvDetectionRateViewList){
            dbIvDetectionRate.setComputedResult(String.valueOf(nf.format(Float.valueOf(dbIvDetectionRate.getComputedResult()))));
        }

        view.addObject("dbIvDetectionRateViewList",dbIvDetectionRateViewList);
        view.addObject("orgInfoList",orgInfoList);
        view.addObject("query", query);
        view.setViewName("actionRate/exfoliatedCellsDetectionRateList");
        return view;
    }

    /**
     * 导出侵财类案件脱落细胞检验率统计excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exfoliatedCellsExpotr")
    public void exfoliatedCellsExpotr(HttpServletRequest request, HttpServletResponse response, DbIvDetectionRateView query) {
        List<DbIvDetectionRateView> dbIvDetectionRateViewList = new ArrayList<>();
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
            if (StringUtils.isNotBlank(query.getDelegateOrg())){
                for (OrgInfo orgInfo:orgInfoList){
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrg())){
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query.setSampleType(Constants.SAMPLE_TYPE_TLXB);
                query = resetQuery(query);
                try {
                    query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(query,Constants.SAMPLE_TYPE_TLXB);
                    dbIvDetectionRateViewList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("查询单个分局脱落细胞检出率出错!"+e);
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDetectionRateView dbIvDetectionRate = new DbIvDetectionRateView();
                    dbIvDetectionRate.setDelegateAtStart(query.getDelegateAtStart());
                    dbIvDetectionRate.setDelegateAtEnd(query.getDelegateAtEnd());
                    dbIvDetectionRate.setDelegateOrg(orgInfo.getOrgCode());
                    dbIvDetectionRate.setDelegateOrgName(orgInfo.getOrgName());
                    dbIvDetectionRate.setSampleType(Constants.SAMPLE_TYPE_TLXB);
                    dbIvDetectionRate = resetQuery(dbIvDetectionRate);

                    try {
                        query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(dbIvDetectionRate,Constants.SAMPLE_TYPE_TLXB);
                        dbIvDetectionRateViewList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("查询脱落细胞检出率出错！"+e);
                    }
                }
                query.setDelegateOrg(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        /**排序*/
        Collections.sort(dbIvDetectionRateViewList, new SortByEvidenceNo());
        /**小数转百分数*/
        for(DbIvDetectionRateView dbIvDetectionRate:dbIvDetectionRateViewList){
            dbIvDetectionRate.setComputedResult(String.valueOf(nf.format(Float.valueOf(dbIvDetectionRate.getComputedResult()))));
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/exfoliatedCellsDetectionRateExcel.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (DbIvDetectionRateView detectionRate:dbIvDetectionRateViewList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(detectionRate.getDelegateOrgName());

                //送检案件数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getComparisonCount()));

                //出鉴定书数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getNotComparisonRkCount()));

                //有物证入库但未出鉴定书的案件数
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getSampleCount()));

                //计算结果
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(detectionRate.getComputedResult());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_侵财类案件脱落细胞检出率统计统计列表.xls", "utf-8"));
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
                    logger.error("导出侵财类案件脱落细胞检出率统计excel错误！", ex);
                }
            }
        }
    }

    /**
     * 侵财类案件物证入库率
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("/wzWarehousingRateList")
    public ModelAndView wzWarehousingRateList(HttpServletRequest request, DbIvDetectionRateView query) {
        ModelAndView view = new ModelAndView();

        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        //查询单位列表
        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
        List<DbIvDetectionRateView> wzWarehousingRateList = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(query.getDelegateOrg())){
                for (OrgInfo orgInfo:orgInfoList){
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrg())){
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query.setSampleType(Constants.SAMPLE_TYPE_WZ);
                query = resetQuery(query);
                try {
                    query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(query,Constants.SAMPLE_TYPE_WZ);
                    wzWarehousingRateList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("查询单个分局物证入库率出错!"+e);
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDetectionRateView dbIvDetectionRate = new DbIvDetectionRateView();
                    dbIvDetectionRate.setDelegateAtStart(query.getDelegateAtStart());
                    dbIvDetectionRate.setDelegateAtEnd(query.getDelegateAtEnd());
                    dbIvDetectionRate.setDelegateOrg(orgInfo.getOrgCode());
                    dbIvDetectionRate.setDelegateOrgName(orgInfo.getOrgName());
                    dbIvDetectionRate.setSampleType(Constants.SAMPLE_TYPE_WZ);
                    dbIvDetectionRate = resetQuery(dbIvDetectionRate);

                    try {
                        query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(dbIvDetectionRate,Constants.SAMPLE_TYPE_WZ);
                        wzWarehousingRateList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("查询物证入库率出错！"+e);
                    }
                }
                query.setDelegateOrg(null);
            }

        } catch (Exception ex) {
            logger.info("查询侵财类案件物证入库率出错:" + ex);
        }

        //排序
        Collections.sort(wzWarehousingRateList, new SortByEvidenceNo());


        for(DbIvDetectionRateView dbIvDetectionRate:wzWarehousingRateList){
            dbIvDetectionRate.setComputedResult(String.valueOf(nf.format(Float.valueOf(dbIvDetectionRate.getComputedResult()))));
        }

        view.addObject("wzWarehousingRateList",wzWarehousingRateList);
        view.addObject("orgInfoList",orgInfoList);
        view.addObject("query", query);
        view.setViewName("actionRate/wzWarehousingRateList");
        return view;
    }


    /**
     * 导出侵财类案件脱落细胞检验率统计excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/wzWarehousingRateExpotr")
    public void wzWarehousingRateExpotr(HttpServletRequest request, HttpServletResponse response, DbIvDetectionRateView query) {
        List<DbIvDetectionRateView> wzWarehousingRateList = new ArrayList<>();
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
            if (StringUtils.isNotBlank(query.getDelegateOrg())){
                for (OrgInfo orgInfo:orgInfoList){
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrg())){
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }
                query.setSampleType(Constants.SAMPLE_TYPE_WZ);
                query = resetQuery(query);
                try {
                    query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(query,Constants.SAMPLE_TYPE_WZ);
                    wzWarehousingRateList.add(query);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("导出excel查询单个分局物证入库率出错!"+e);
                }

            }else{
                for(OrgInfo orgInfo:orgInfoList){
                    DbIvDetectionRateView dbIvDetectionRate = new DbIvDetectionRateView();
                    dbIvDetectionRate.setDelegateAtStart(query.getDelegateAtStart());
                    dbIvDetectionRate.setDelegateAtEnd(query.getDelegateAtEnd());
                    dbIvDetectionRate.setDelegateOrg(orgInfo.getOrgCode());
                    dbIvDetectionRate.setDelegateOrgName(orgInfo.getOrgName());
                    dbIvDetectionRate.setSampleType(Constants.SAMPLE_TYPE_WZ);
                    dbIvDetectionRate = resetQuery(dbIvDetectionRate);

                    try {
                        query = dbIvDetectionRateViewService.selectExfoliatedCellsDetectionRate(dbIvDetectionRate,Constants.SAMPLE_TYPE_WZ);
                        wzWarehousingRateList.add(query);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("导出excel查询物证入库率出错！"+e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        /**排序*/
        Collections.sort(wzWarehousingRateList, new SortByEvidenceNo());
        /**小数转百分数*/
        for(DbIvDetectionRateView dbIvDetectionRate:wzWarehousingRateList){
            dbIvDetectionRate.setComputedResult(String.valueOf(nf.format(Float.valueOf(dbIvDetectionRate.getComputedResult()))));
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/wzWarehousingRateExcel.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (DbIvDetectionRateView detectionRate:wzWarehousingRateList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(detectionRate.getDelegateOrgName());

                //有比中数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getComparisonCount()));

                //（未比中）已入库的个数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getNotComparisonRkCount()));

                //总的样本数
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(Integer.parseInt(detectionRate.getSampleCount()));

                //计算结果
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(detectionRate.getComputedResult());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_侵财类案件现场物证入库率统计列表.xls", "utf-8"));
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
                    logger.error("导出侵财类案件现场物证入库率统计excel错误！", ex);
                }
            }
        }
    }

    /**
     * 查询条件处理
     * @param query
     * @return
     * @throws ParseException
     */
    private DbIvDetectionRateView resetQuery(DbIvDetectionRateView query) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null != query.getDelegateAtStart()) {
            query.setDelegateAtStart(query.getDelegateAtStart());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            query.setDelegateAtStart(sdf.format(calendar.getTime()));
        }
        if (null != query.getDelegateAtEnd()) {
            query.setDelegateAtEnd(query.getDelegateAtEnd());
        } else {

            query.setDelegateAtEnd(sdf.format(new Date()));
        }


        return query;
    }


    //排序
    class SortByEvidenceNo implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            DbIvDetectionRateView s1 = (DbIvDetectionRateView) o1;
            DbIvDetectionRateView s2 = (DbIvDetectionRateView) o2;
            //return s1.getComputedResult().compareTo(s2.getComputedResult());
            return s2.getComputedResult().compareTo(s1.getComputedResult());
        }
    }

}
