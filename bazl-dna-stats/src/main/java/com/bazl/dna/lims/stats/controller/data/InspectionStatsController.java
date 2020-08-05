package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.po.OrgInfo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.model.vo.LimsSampleGeneVo;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.service.LaboratoryInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsSampleGeneService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.model.vo.SampleTypeCountVo;

/**
 * 送检单位统计
 * Created by zb on 2019/12/5.
 */
@Controller
@RequestMapping("/inspection")
public class InspectionStatsController extends BaseController {

    @Autowired
    OrgInfoService orgInfoService;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    LaboratoryInfoService laboratoryInfoService;

    @Autowired
    LimsCaseInfoService limsCaseInfoService;


    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;

    /**
     * 检材送检单位统计
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("/sampleInspectionStatsList")
    public ModelAndView sampleInspectionStatsList(HttpServletRequest request, LimsCaseInfoVo query) {
        ModelAndView modelAndView = new ModelAndView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

        Map<Object, Object> result = new HashMap<>();
        SampleTypeCountVo sampleTypeCount = new SampleTypeCountVo();
        List<SampleTypeCountVo> sampleTypeCountVoList = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(query.getDelegateOrgCode())) {

                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }

                if (null != query.getDelegateStartDatetime()) {
                    query.setDelegateStartDatetime(query.getDelegateStartDatetime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }
                try {
                    Map map = limsSampleInfoDnaService.selectSampleTypeCountByOrgCode(query);
                    result.put(query.getDelegateOrgName(), map);
                    sampleTypeCount = sampleTypeTotal(map,sampleTypeCount);
                    sampleTypeCountVoList.add(sampleTypeCount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("查询检材单位统计数据出错！" + ex);
                }

            } else {
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();
                for (OrgInfo orgInfo : orgInfoList) {
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    }else{
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }

                    try {
                        Map map = limsSampleInfoDnaService.selectSampleTypeCountByOrgCode(limsCaseInfo);

                        result.put(orgInfo.getOrgName(), map);
                        //总计
                        sampleTypeCount = sampleTypeTotal(map,sampleTypeCount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("查询检材单位统计数据出错！" + ex);
                    }
                }
                limsCaseInfo.setDelegateOrgCode(null);
                query.setDelegateStartDatetime(limsCaseInfo.getDelegateStartDatetime());
                query.setDelegateEndDatetime(limsCaseInfo.getDelegateEndDatetime());

                sampleTypeCountVoList.add(sampleTypeCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        modelAndView.addObject("sampleTypeCountVoList",sampleTypeCountVoList);
        modelAndView.addObject("orgInfoList", orgInfoList);
        modelAndView.addObject("result", result);
        modelAndView.addObject("query", query);
        modelAndView.setViewName("query/sampleInspectionStatsList");
        return modelAndView;
    }

    /**
     * 检材送检单位统计导出excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, LimsCaseInfoVo query) {

        Map<Object, Object> result = new HashMap<>();
        SampleTypeCountVo sampleTypeCount = new SampleTypeCountVo();
        List<SampleTypeCountVo> sampleTypeCountVoList = new ArrayList<>();

        try {
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
            if (StringUtils.isNotBlank(query.getDelegateOrgCode())) {
                if (null != query.getDelegateStartDatetime()) {
                    query.setDelegateStartDatetime(query.getDelegateStartDatetime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }
                try {
                    Map map = limsSampleInfoDnaService.selectSampleTypeCountByOrgCode(query);
                    result.put(query.getDelegateOrgName(), map);
                    sampleTypeCount = sampleTypeTotal(map,sampleTypeCount);
                    sampleTypeCountVoList.add(sampleTypeCount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("查询检材单位统计数据出错！" + ex);
                }

            } else {
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();
                for (OrgInfo orgInfo : orgInfoList) {
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    }else{
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }

                    try {
                        Map map = limsSampleInfoDnaService.selectSampleTypeCountByOrgCode(limsCaseInfo);

                        result.put(orgInfo.getOrgName(), map);
                        //总计
                        sampleTypeCount = sampleTypeTotal(map,sampleTypeCount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("查询检材单位统计数据出错！" + ex);
                    }
                }
                limsCaseInfo.setDelegateOrgCode(null);
                query.setDelegateStartDatetime(limsCaseInfo.getDelegateStartDatetime());
                query.setDelegateEndDatetime(limsCaseInfo.getDelegateEndDatetime());

                sampleTypeCountVoList.add(sampleTypeCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }


        String templateFilePath = request.getServletContext().getRealPath("templates/sampleDelegateStats.xls");
        HSSFWorkbook workbook = null;

        Map resultMap = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (Object key : result.keySet()) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(String.valueOf(key));

                resultMap = (Map) result.get(key);
                Integer i = 1;
                int count = 0;
                for(Object map : resultMap.keySet()){
                    cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellValue(Integer.parseInt(String.valueOf(resultMap.get(map))));
                    count += Integer.parseInt(String.valueOf(resultMap.get(map)));
                    i++;
                }

                //总数
                cell = row.getCell(i);
                if (cell == null) {
                    cell = row.createCell(i);
                }
                cell.setCellValue(count);

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
            for (int i= 0;i<sampleTypeCountVoList.size();i++) {
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getZjTotal());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getGgTotal());

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getZzTotal());

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getXyTotal());

                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getQtTotal());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getTlxbTotal());

                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getJbTotal());

                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getMfTotal());

                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getYcTotal());

                cell = row.getCell(10);
                if (cell == null) {
                    cell = row.createCell(10);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getTybTotal());
                //总计
                cell = row.getCell(11);
                if (cell == null) {
                    cell = row.createCell(11);
                }
                cell.setCellValue(sampleTypeCountVoList.get(i).getZjTotal()+sampleTypeCountVoList.get(i).getGgTotal()+sampleTypeCountVoList.get(i).getZzTotal()+sampleTypeCountVoList.get(i).getXyTotal()+sampleTypeCountVoList.get(i).getQtTotal()+sampleTypeCountVoList.get(i).getTlxbTotal()+sampleTypeCountVoList.get(i).getJbTotal()+sampleTypeCountVoList.get(i).getMfTotal()+sampleTypeCountVoList.get(i).getYcTotal()+sampleTypeCountVoList.get(i).getTybTotal());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_检材送检单位统计列表.xls", "utf-8"));
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
                    logger.error("导出检材送检单位统计Excel错误！", ex);
                }
            }
        }

    }

    /**
     * 总计
     * @param hashMap
     * @return
     */
    private SampleTypeCountVo sampleTypeTotal(Map hashMap, SampleTypeCountVo sampleTypeCount) {
        for (Object key : hashMap.keySet()) {
            String name = String.valueOf(key);
            String value = String.valueOf(hashMap.get(key));
            if (name.equals("ZJ")) {
                sampleTypeCount.setZjTotal(sampleTypeCount.getZjTotal() + Integer.parseInt(value));
            }else if (name.equals("GG")){
                sampleTypeCount.setGgTotal(sampleTypeCount.getGgTotal() + Integer.parseInt(value));
            }else if (name.equals("ZZ")){
                sampleTypeCount.setZzTotal(sampleTypeCount.getZzTotal() + Integer.parseInt(value));
            }else if (name.equals("XY")){
                sampleTypeCount.setXyTotal(sampleTypeCount.getXyTotal() + Integer.parseInt(value));
            }else if (name.equals("QT")){
                sampleTypeCount.setQtTotal(sampleTypeCount.getQtTotal() + Integer.parseInt(value));
            }else if (name.equals("TLXB")){
                sampleTypeCount.setTlxbTotal(sampleTypeCount.getTlxbTotal() + Integer.parseInt(value));
            }else if (name.equals("JB")){
                sampleTypeCount.setJbTotal(sampleTypeCount.getJbTotal() + Integer.parseInt(value));
            }else if (name.equals("MF")){
                sampleTypeCount.setMfTotal(sampleTypeCount.getMfTotal() + Integer.parseInt(value));
            }else if (name.equals("YC")){
                sampleTypeCount.setYcTotal(sampleTypeCount.getYcTotal() + Integer.parseInt(value));
            }else if (name.equals("TYB")){
                sampleTypeCount.setTybTotal(sampleTypeCount.getTybTotal() + Integer.parseInt(value));
            }
        }
        return sampleTypeCount;
    }


    /**
     * 检材检出率统计
     * @param request
     * @param acceptOrgId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/sampleDetectionRateList")
    public ModelAndView sampleDetectionRateList(HttpServletRequest request, String acceptOrgId, String startDate, String endDate) {
        ModelAndView mv = new ModelAndView();
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);

//        List<OrgInfo> orgInfoList = orgInfoService.selectAll();
        List<CaseEvidenceDetectionRateVo> caseEvidenceDetectionRateVoList = new ArrayList<>();
        try {
            Map<String, Object> queryParams = new HashMap<>();
            if(StringUtils.isNotBlank(acceptOrgId)) {
                queryParams.put("acceptOrgId", acceptOrgId);
            }
            if(StringUtils.isNotBlank(startDate)) {
                queryParams.put("startDate", startDate);
            }
            if(StringUtils.isNotBlank(endDate)) {
                queryParams.put("endDate", endDate);
            }
            caseEvidenceDetectionRateVoList = limsCaseInfoService.selectEvidenceDetectionRate(queryParams);
            for(CaseEvidenceDetectionRateVo cedr : caseEvidenceDetectionRateVoList){
                if(cedr.getEvidenceCount() > 0) {
                    try {
                        int detectedCnt = cedr.getMatchedEvidenceCount() + cedr.getInstoreEvidenceCount();
                        double rateVal = new BigDecimal((float) detectedCnt / cedr.getEvidenceCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        cedr.setDetectionRate(rateVal * 100);
                    } catch (Exception exx) {
                        logger.error("计算物证检出率错误。", exx);
                        cedr.setDetectionRate(0.00);
                    }
                }else{
                    cedr.setDetectionRate(0.00);
                }
            }

            /*if (StringUtils.isNotBlank(query.getDelegateOrgCode())){
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }

                if (null != query.getDelegateStartDatetime()) {
                    query.setDelegateStartDatetime(query.getDelegateStartDatetime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }

                LimsSampleGeneVo limsSampleGene = new LimsSampleGeneVo();
                try{
                    //送检数
                    int delegateCount = limsSampleInfoDnaService.selectDelegateCountByOrgCode(query);
                    //检出基因型数
                    int detectionGeneCount = limsSampleGeneService.selectDetectionGeneCountByOrgCode(query);
                    limsSampleGene.setDelegateOrgName(query.getDelegateOrgName());
                    limsSampleGene.setDategateSampleCnt(delegateCount);
                    limsSampleGene.setDetectionGeneCnt(detectionGeneCount);
                    if (delegateCount != 0){
                        limsSampleGene.setDetectionRate(nf.format(df.format((float) detectionGeneCount / delegateCount)));
                    }else{
                        limsSampleGene.setDetectionRate("0%");
                    }
                    limsSampleGeneVoList.add(limsSampleGene);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("统计检出率出错！"+e);
                }
            }else{
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();
                for(OrgInfo orgInfo:orgInfoList){
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    }else{
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }
                    LimsSampleGeneVo limsSampleGene = new LimsSampleGeneVo();
                    try{
                        //送检数
                        int delegateCount = limsSampleInfoDnaService.selectDelegateCountByOrgCode(limsCaseInfo);
                        //检出基因型数
                        int detectionGeneCount = limsSampleGeneService.selectDetectionGeneCountByOrgCode(limsCaseInfo);
                        limsSampleGene.setDelegateOrgName(orgInfo.getOrgName());
                        limsSampleGene.setDategateSampleCnt(delegateCount);
                        limsSampleGene.setDetectionGeneCnt(detectionGeneCount);
                        if(delegateCount != 0){
                            limsSampleGene.setDetectionRate( nf.format(Double.valueOf(df.format((float) detectionGeneCount / delegateCount))));
                        }else{
                            limsSampleGene.setDetectionRate("0%");
                        }

                        limsSampleGeneVoList.add(limsSampleGene);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("统计出率出错"+e);
                    }
                }
                limsCaseInfo.setDelegateOrgCode(null);
                query.setDelegateStartDatetime(limsCaseInfo.getDelegateStartDatetime());
                query.setDelegateEndDatetime(limsCaseInfo.getDelegateEndDatetime());
            }*/
        }catch (Exception e){
            e.printStackTrace();
            logger.error("统计检材检出率出错！"+e);
        }

        List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();

//        mv.addObject("orgInfoList",orgInfoList);
        mv.addObject("caseEvidenceDetectionRateVoList",caseEvidenceDetectionRateVoList);
        mv.addObject("acceptOrgId", acceptOrgId);
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("laboratoryInfoList", laboratoryInfoList);
        mv.setViewName("query/sampleDetectionRateList");
        return mv;
    }

    /**
     * 检材检出率统计导出excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/detectionRateExportExcel")
    public void detectionRateExportExcel(HttpServletRequest request, HttpServletResponse response, LimsCaseInfoVo query) {

        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        List<LimsSampleGeneVo> limsSampleGeneVoList  = new ArrayList<>();
        try {
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

            if (StringUtils.isNotBlank(query.getDelegateOrgCode())){
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getDelegateOrgCode())) {
                        query.setDelegateOrgName(orgInfo.getOrgName());
                    }
                }

                if (null != query.getDelegateStartDatetime()) {
                    query.setDelegateStartDatetime(query.getDelegateStartDatetime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }

                LimsSampleGeneVo limsSampleGene = new LimsSampleGeneVo();
                try{
                    //送检数
                    int delegateCount = limsSampleInfoDnaService.selectDelegateCountByOrgCode(query);
                    //检出基因型数
                    int detectionGeneCount = limsSampleGeneService.selectDetectionGeneCountByOrgCode(query);
                    limsSampleGene.setDelegateOrgName(query.getDelegateOrgName());
                    limsSampleGene.setDategateSampleCnt(delegateCount);
                    limsSampleGene.setDetectionGeneCnt(detectionGeneCount);
                    if (delegateCount != 0){
                        limsSampleGene.setDetectionRate(nf.format(df.format((float) detectionGeneCount / delegateCount)));
                    }else{
                        limsSampleGene.setDetectionRate("0%");
                    }
                    limsSampleGeneVoList.add(limsSampleGene);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("统计检材检出率出错！"+e);
                }
            }else{
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();
                for(OrgInfo orgInfo:orgInfoList){
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    }else{
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }
                    LimsSampleGeneVo limsSampleGene = new LimsSampleGeneVo();
                    try{
                        //送检数
                        int delegateCount = limsSampleInfoDnaService.selectDelegateCountByOrgCode(limsCaseInfo);
                        //检出基因型数
                        int detectionGeneCount = limsSampleGeneService.selectDetectionGeneCountByOrgCode(limsCaseInfo);
                        limsSampleGene.setDelegateOrgName(orgInfo.getOrgName());
                        limsSampleGene.setDategateSampleCnt(delegateCount);
                        limsSampleGene.setDetectionGeneCnt(detectionGeneCount);
                        if(delegateCount != 0){
                            limsSampleGene.setDetectionRate( nf.format(Double.valueOf(df.format((float) detectionGeneCount / delegateCount))));
                        }else{
                            limsSampleGene.setDetectionRate("0%");
                        }
                        limsSampleGeneVoList.add(limsSampleGene);
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("统计检材检出率出错"+e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }


        String templateFilePath = request.getServletContext().getRealPath("templates/detectionRateStats.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (LimsSampleGeneVo limsSampleGene : limsSampleGeneVoList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //送检单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(limsSampleGene.getDelegateOrgName());

                //送检数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(limsSampleGene.getDategateSampleCnt());

                //检出数
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(limsSampleGene.getDetectionGeneCnt());

                //检出率
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(limsSampleGene.getDetectionRate());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_检材检出率统计列表.xls", "utf-8"));
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
                    logger.error("导出检材送检率统计Excel错误！", ex);
                }
            }
        }

    }

}
