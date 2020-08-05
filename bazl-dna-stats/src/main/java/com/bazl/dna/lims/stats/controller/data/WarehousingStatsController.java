package com.bazl.dna.lims.stats.controller.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.OrgInfoService;

/**
 * 案件送检单位统计/入库样本统计
 * Created by zb on 2019/12/2.
 */
@Controller
@RequestMapping("/warehousing")
public class WarehousingStatsController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
//    @Autowired
//    QueryDnaDataStatsWebService queryDnaDataStatsWebService;
/*

    @RequestMapping("/warehousingCntList")
    public ModelAndView warehousingCntList(HttpServletRequest request, LimsCaseInfoVo query) {
        ModelAndView modelAndView = new ModelAndView();

        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
        List<CaseProperyCountVo> caseProperyCountVoList = new ArrayList<>();
        Map<Object, Object> result = new HashMap<>();
        CaseProperyCountVo caseProperyCountVo = new CaseProperyCountVo();
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
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }
                try {
                    HashMap hashMap = limsCaseInfoService.selectCasePropertyCountByOrgCode(query);
                    int theftCaseCount = limsCaseInfoService.selectTheftCaseCountByOrgCode(query);
                    hashMap.put("DQ", theftCaseCount);
                    result.put(query.getDelegateOrgName(), hashMap);

                    caseProperyCountVo = calculationTotal(hashMap, caseProperyCountVo);
                    caseProperyCountVoList.add(caseProperyCountVo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("查询案件统计数据出错！" + ex);
                }

            } else {
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();

                for (OrgInfo orgInfo : orgInfoList) {
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    } else {
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }

                    try {
                        HashMap hashMap = limsCaseInfoService.selectCasePropertyCountByOrgCode(limsCaseInfo);
                        int theftCaseCount = limsCaseInfoService.selectTheftCaseCountByOrgCode(limsCaseInfo);
                        hashMap.put("DQ", theftCaseCount);
                        result.put(orgInfo.getOrgName(), hashMap);

                        caseProperyCountVo = calculationTotal(hashMap, caseProperyCountVo);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("查询案件统计数据出错！" + ex);
                    }
                }
                caseProperyCountVoList.add(caseProperyCountVo);
                query.setDelegateStartDatetime(limsCaseInfo.getDelegateStartDatetime());
                query.setDelegateEndDatetime(limsCaseInfo.getDelegateEndDatetime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        modelAndView.addObject("caseProperyCountVoList", caseProperyCountVoList);
        modelAndView.addObject("orgInfoList", orgInfoList);
        modelAndView.addObject("result", result);
        modelAndView.addObject("query", query);
        modelAndView.setViewName("query/warehousingStatsList");
        return modelAndView;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, LimsCaseInfoVo query) {

        List<CaseProperyCountVo> caseProperyCountVoList = new ArrayList<>();
        Map<Object, Object> result = new HashMap<>();
        CaseProperyCountVo caseProperyCountVo = new CaseProperyCountVo();

        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

            if (StringUtils.isNotBlank(query.getDelegateOrgCode())) {
                if (null != query.getDelegateStartDatetime()) {
                    query.setDelegateStartDatetime(query.getDelegateStartDatetime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    query.setDelegateStartDatetime(calendar.getTime());
                }
                if (null != query.getDelegateEndDatetime()) {
                    query.setDelegateEndDatetime(query.getDelegateEndDatetime());
                } else {
                    query.setDelegateEndDatetime(new Date());
                }
                try {
                    HashMap hashMap = limsCaseInfoService.selectCasePropertyCountByOrgCode(query);
                    int theftCaseCount = limsCaseInfoService.selectTheftCaseCountByOrgCode(query);
                    hashMap.put("DQ", theftCaseCount);
                    result.put(query.getDelegateOrgName(), hashMap);

                    caseProperyCountVo = calculationTotal(hashMap, caseProperyCountVo);
                    caseProperyCountVoList.add(caseProperyCountVo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("导出查询案件统计数据出错！" + ex);
                }

            } else {
                LimsCaseInfoVo limsCaseInfo = new LimsCaseInfoVo();

                for (OrgInfo orgInfo : orgInfoList) {
                    limsCaseInfo = new LimsCaseInfoVo();
                    limsCaseInfo.setDelegateOrgCode(orgInfo.getOrgCode());
                    if (null != query.getDelegateStartDatetime()) {
                        limsCaseInfo.setDelegateStartDatetime(query.getDelegateStartDatetime());
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        limsCaseInfo.setDelegateStartDatetime(calendar.getTime());
                    }
                    if (null != query.getDelegateEndDatetime()) {
                        limsCaseInfo.setDelegateEndDatetime(query.getDelegateEndDatetime());
                    } else {
                        limsCaseInfo.setDelegateEndDatetime(new Date());
                    }

                    try {
                        HashMap hashMap = limsCaseInfoService.selectCasePropertyCountByOrgCode(limsCaseInfo);
                        int theftCaseCount = limsCaseInfoService.selectTheftCaseCountByOrgCode(limsCaseInfo);
                        hashMap.put("DQ", theftCaseCount);
                        result.put(orgInfo.getOrgName(), hashMap);
                        caseProperyCountVo = calculationTotal(hashMap, caseProperyCountVo);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("导出查询案件统计数据出错！" + ex);
                    }
                }
                caseProperyCountVoList.add(caseProperyCountVo);
                query.setDelegateStartDatetime(limsCaseInfo.getDelegateStartDatetime());
                query.setDelegateEndDatetime(limsCaseInfo.getDelegateEndDatetime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        String templateFilePath = request.getServletContext().getRealPath("templates/caseDelagateStats.xls");
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
                for (Object map : resultMap.keySet()) {
                    cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    count += Integer.parseInt(String.valueOf(resultMap.get(map)));
                    cell.setCellValue(Integer.parseInt(String.valueOf(resultMap.get(map))));
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
            for (int i = 0; i < caseProperyCountVoList.size(); i++) {
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getGkzwTotal());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getDgTotal());

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getQtTotal());

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getZaTotal());

                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getShzsTotal());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getQyTotal());

                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getQjaTotal());

                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getDqgdTotal());

                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getXcTotal());

                cell = row.getCell(10);
                if (cell == null) {
                    cell = row.createCell(10);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getDqTotal());

                cell = row.getCell(11);
                if (cell == null) {
                    cell = row.createCell(11);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getXxzsTotal());

                cell = row.getCell(12);
                if (cell == null) {
                    cell = row.createCell(12);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getQdTotal());

                cell = row.getCell(13);
                if (cell == null) {
                    cell = row.createCell(13);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getShTotal());

                cell = row.getCell(14);
                if (cell == null) {
                    cell = row.createCell(14);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getZpTotal());

                cell = row.getCell(15);
                if (cell == null) {
                    cell = row.createCell(15);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getFzcswTotal());

                cell = row.getCell(16);
                if (cell == null) {
                    cell = row.createCell(16);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getZsrkTotal());

                cell = row.getCell(17);
                if (cell == null) {
                    cell = row.createCell(17);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getQjTotal());

                cell = row.getCell(18);
                if (cell == null) {
                    cell = row.createCell(18);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getJtsgTotal());

                cell = row.getCell(19);
                if (cell == null) {
                    cell = row.createCell(19);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getSyrdTotal());
                //总计
                cell = row.getCell(20);
                if (cell == null) {
                    cell = row.createCell(20);
                }
                cell.setCellValue(caseProperyCountVoList.get(i).getGkzwTotal() + caseProperyCountVoList.get(i).getDgTotal() + caseProperyCountVoList.get(i).getQtTotal() + caseProperyCountVoList.get(i).getZaTotal() + caseProperyCountVoList.get(i).getShzsTotal() + caseProperyCountVoList.get(i).getQyTotal() + caseProperyCountVoList.get(i).getQjaTotal() + caseProperyCountVoList.get(i).getDqgdTotal() + caseProperyCountVoList.get(i).getXcTotal() + caseProperyCountVoList.get(i).getDqTotal() + caseProperyCountVoList.get(i).getXxzsTotal() + caseProperyCountVoList.get(i).getQdTotal() + caseProperyCountVoList.get(i).getShTotal() + caseProperyCountVoList.get(i).getZpTotal() + caseProperyCountVoList.get(i).getFzcswTotal() + caseProperyCountVoList.get(i).getZsrkTotal() + caseProperyCountVoList.get(i).getQjTotal() + caseProperyCountVoList.get(i).getJtsgTotal() + caseProperyCountVoList.get(i).getSyrdTotal());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_案件送检单位统计列表.xls", "utf-8"));
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
                    logger.error("导出案件送检单位统计Excel错误！", ex);
                }
            }
        }

    }


    */
/**
     * 总计
     *
     * @param hashMap
     * @return
     *//*

    private CaseProperyCountVo calculationTotal(HashMap hashMap, CaseProperyCountVo caseProperyCount) {
        for (Object key : hashMap.keySet()) {
            String name = String.valueOf(key);
            String value = String.valueOf(hashMap.get(key));
            if (name.equals("GKZW")) {
                caseProperyCount.setGkzwTotal(caseProperyCount.getGkzwTotal() + Integer.parseInt(value));
            } else if (name.equals("DG")) {
                caseProperyCount.setDgTotal(caseProperyCount.getDgTotal() + Integer.parseInt(value));
            } else if (name.equals("QT")) {
                caseProperyCount.setQtTotal(caseProperyCount.getQtTotal() + Integer.parseInt(value));
            } else if (name.equals("DG")) {
                caseProperyCount.setDgTotal(caseProperyCount.getDgTotal() + Integer.parseInt(value));
            } else if (name.equals("ZA")) {
                caseProperyCount.setZaTotal(caseProperyCount.getZaTotal() + Integer.parseInt(value));
            } else if (name.equals("SHZS")) {
                caseProperyCount.setShzsTotal(caseProperyCount.getShzsTotal() + Integer.parseInt(value));
            } else if (name.equals("QY")) {
                caseProperyCount.setQyTotal(caseProperyCount.getQyTotal() + Integer.parseInt(value));
            } else if (name.equals("QJA")) {
                caseProperyCount.setQjaTotal(caseProperyCount.getQjaTotal() + Integer.parseInt(value));
            } else if (name.equals("DQGD")) {
                caseProperyCount.setDqgdTotal(caseProperyCount.getDqgdTotal() + Integer.parseInt(value));
            } else if (name.equals("XC")) {
                caseProperyCount.setXcTotal(caseProperyCount.getXcTotal() + Integer.parseInt(value));
            } else if (name.equals("DQ")) {
                caseProperyCount.setDqTotal(caseProperyCount.getDqTotal() + Integer.parseInt(value));
            } else if (name.equals("XXZS")) {
                caseProperyCount.setXxzsTotal(caseProperyCount.getXxzsTotal() + Integer.parseInt(value));
            } else if (name.equals("QD")) {
                caseProperyCount.setQdTotal(caseProperyCount.getQdTotal() + Integer.parseInt(value));
            } else if (name.equals("SH")) {
                caseProperyCount.setShTotal(caseProperyCount.getShTotal() + Integer.parseInt(value));
            } else if (name.equals("ZP")) {
                caseProperyCount.setZpTotal(caseProperyCount.getZpTotal() + Integer.parseInt(value));
            } else if (name.equals("FZCSW")) {
                caseProperyCount.setFzcswTotal(caseProperyCount.getFzcswTotal() + Integer.parseInt(value));
            } else if (name.equals("ZSRK")) {
                caseProperyCount.setZsrkTotal(caseProperyCount.getZsrkTotal() + Integer.parseInt(value));
            } else if (name.equals("QJ")) {
                caseProperyCount.setQjTotal(caseProperyCount.getQjTotal() + Integer.parseInt(value));
            } else if (name.equals("JTSG")) {
                caseProperyCount.setJtsgTotal(caseProperyCount.getJtsgTotal() + Integer.parseInt(value));
            } else if (name.equals("SYRD")) {
                caseProperyCount.setSyrdTotal(caseProperyCount.getSyrdTotal() + Integer.parseInt(value));
            }
        }
        return caseProperyCount;
    }

    */
/**
     * 二级库数据统计（入库数）
     *
     * @param request
     * @param query
     * @return
     *//*

    @RequestMapping("/dnaSampleStatsList")
    public ModelAndView dnaSampleStatsList(HttpServletRequest request, GdnaInstoredStats query) {
        ModelAndView modelAndView = new ModelAndView();
        List<GdnaInstoredStats> gdnaInstoredStatsList = new ArrayList<>();
        //查询单位列表
        List<OrgInfo> orgInfoList = orgInfoService.selectAllList();
        try {

            if (StringUtils.isNotBlank(query.getInitServerNo())) {
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getInitServerNo())) {
                        query.setInitServerName(orgInfo.getOrgName());
                    }
                }
                try {
                    gdnaInstoredStatsList = queryDnaDataStatsWebService.gdnaInstoredStatsList(query);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("入库数统计出错！" + ex);
                }

            } else {
                GdnaInstoredStats instoredStats = new GdnaInstoredStats();
                for (OrgInfo orgInfo : orgInfoList) {
                    instoredStats = new GdnaInstoredStats();
                    instoredStats.setInitServerNo(orgInfo.getOrgCode());
                    instoredStats.setInitServerName(orgInfo.getOrgName());
                    instoredStats.setTransferDateTimeStart(query.getTransferDateTimeStart());
                    instoredStats.setTransferDateTimeEnd(query.getTransferDateTimeEnd());

                    try {
                        List<GdnaInstoredStats> instoredStatsList = queryDnaDataStatsWebService.gdnaInstoredStatsList(instoredStats);
                        gdnaInstoredStatsList.addAll(instoredStatsList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("查询案件统计数据出错！" + ex);
                    }
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                query.setTransferDateTimeStart(calendar.getTime());
                query.setTransferDateTimeEnd(new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }
        List<GdnaInstoredStatsTotal> totalList = new ArrayList<>();
        GdnaInstoredStatsTotal instoredStatsTotal = new GdnaInstoredStatsTotal();
        //总计
        int totalCount = 0;
        for (GdnaInstoredStats gdnaInstoredStats : gdnaInstoredStatsList) {
            instoredStatsTotal.setWzInstoredStatsTotal(instoredStatsTotal.getWzInstoredStatsTotal() + gdnaInstoredStats.getWzInstoredStats());
            instoredStatsTotal.setCaseIllegalCrimeTotal(instoredStatsTotal.getCaseIllegalCrimeTotal() + gdnaInstoredStats.getCaseIllegalCrime());
            instoredStatsTotal.setJkIllegalCrimeTotal(instoredStatsTotal.getJkIllegalCrimeTotal() + gdnaInstoredStats.getJkIllegalCrime());
            instoredStatsTotal.setSuspectInstoredStatsTotal(instoredStatsTotal.getSuspectInstoredStatsTotal() + gdnaInstoredStats.getSuspectInstoredStats());
            instoredStatsTotal.setVictimInstoredStatsTotal(instoredStatsTotal.getVictimInstoredStatsTotal() + gdnaInstoredStats.getVictimInstoredStats());
            instoredStatsTotal.setBeMissingInstoredStatsTotal(instoredStatsTotal.getBeMissingInstoredStatsTotal() + gdnaInstoredStats.getBeMissingInstoredStats());
            instoredStatsTotal.setNamelessCorpseInstoredStatsTotal(instoredStatsTotal.getNamelessCorpseInstoredStatsTotal() + gdnaInstoredStats.getNamelessCorpseInstoredStats());
            instoredStatsTotal.setBeMissingRelativesInstoredStatsTotal(instoredStatsTotal.getBeMissingRelativesInstoredStatsTotal() + gdnaInstoredStats.getBeMissingRelativesInstoredStats());
            instoredStatsTotal.setSuspectRelativesInstoredStatsTotal(instoredStatsTotal.getSuspectRelativesInstoredStatsTotal() + gdnaInstoredStats.getSuspectRelativesInstoredStats());
            totalCount = gdnaInstoredStats.getWzInstoredStats() + gdnaInstoredStats.getCaseIllegalCrime() + gdnaInstoredStats.getJkIllegalCrime() + gdnaInstoredStats.getSuspectInstoredStats() + gdnaInstoredStats.getVictimInstoredStats() + gdnaInstoredStats.getBeMissingInstoredStats() + gdnaInstoredStats.getNamelessCorpseInstoredStats() + gdnaInstoredStats.getBeMissingRelativesInstoredStats() + gdnaInstoredStats.getSuspectRelativesInstoredStats();
        }
        instoredStatsTotal.setTotalCount(totalCount);
        totalList.add(instoredStatsTotal);

        modelAndView.addObject("gdnaInstoredStatsList", gdnaInstoredStatsList);
        modelAndView.addObject("totalList", totalList);
        modelAndView.addObject("orgInfoList", orgInfoList);
        modelAndView.addObject("query", query);
        modelAndView.setViewName("query/dnaSampleStatsList");
        return modelAndView;
    }

    */
/**
     * 导出DNA库样本统计excel
     *
     * @param request
     * @param response
     * @param query
     *//*

    @RequestMapping("/exportDnaSampleStatsExcel")
    public void exportDnaSampleStatsExcel(HttpServletRequest request, HttpServletResponse response, GdnaInstoredStats query) {

        List<GdnaInstoredStats> gdnaInstoredStatsList = new ArrayList<>();

        try {
            //查询单位列表
            List<OrgInfo> orgInfoList = orgInfoService.selectAllList();

            if (StringUtils.isNotBlank(query.getInitServerNo())) {
                for (OrgInfo orgInfo : orgInfoList) {
                    if (orgInfo.getOrgCode().equals(query.getInitServerNo())) {
                        query.setInitServerName(orgInfo.getOrgName());
                    }
                }
                try {
                    gdnaInstoredStatsList = queryDnaDataStatsWebService.gdnaInstoredStatsList(query);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error("导出查询入DNA库数出错！" + ex);
                }

            } else {
                GdnaInstoredStats instoredStats = new GdnaInstoredStats();
                for (OrgInfo orgInfo : orgInfoList) {
                    instoredStats = new GdnaInstoredStats();
                    instoredStats.setInitServerNo(orgInfo.getOrgCode());
                    instoredStats.setInitServerName(orgInfo.getOrgName());
                    instoredStats.setTransferDateTimeStart(query.getTransferDateTimeStart());
                    instoredStats.setTransferDateTimeEnd(query.getTransferDateTimeEnd());

                    try {
                        List<GdnaInstoredStats> instoredStatsList = queryDnaDataStatsWebService.gdnaInstoredStatsList(instoredStats);
                        gdnaInstoredStatsList.addAll(instoredStatsList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("导出入DNA库数据查询出错！" + ex);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询出错");
        }

        List<GdnaInstoredStatsTotal> totalList = new ArrayList<>();
        GdnaInstoredStatsTotal instoredStatsTotal = new GdnaInstoredStatsTotal();
        //总计
        int totalCount = 0;
        for (GdnaInstoredStats gdnaInstoredStats : gdnaInstoredStatsList) {
            instoredStatsTotal.setWzInstoredStatsTotal(instoredStatsTotal.getWzInstoredStatsTotal() + gdnaInstoredStats.getWzInstoredStats());
            instoredStatsTotal.setCaseIllegalCrimeTotal(instoredStatsTotal.getCaseIllegalCrimeTotal() + gdnaInstoredStats.getCaseIllegalCrime());
            instoredStatsTotal.setJkIllegalCrimeTotal(instoredStatsTotal.getJkIllegalCrimeTotal() + gdnaInstoredStats.getJkIllegalCrime());
            instoredStatsTotal.setSuspectInstoredStatsTotal(instoredStatsTotal.getSuspectInstoredStatsTotal() + gdnaInstoredStats.getSuspectInstoredStats());
            instoredStatsTotal.setVictimInstoredStatsTotal(instoredStatsTotal.getVictimInstoredStatsTotal() + gdnaInstoredStats.getVictimInstoredStats());
            instoredStatsTotal.setBeMissingInstoredStatsTotal(instoredStatsTotal.getBeMissingInstoredStatsTotal() + gdnaInstoredStats.getBeMissingInstoredStats());
            instoredStatsTotal.setNamelessCorpseInstoredStatsTotal(instoredStatsTotal.getNamelessCorpseInstoredStatsTotal() + gdnaInstoredStats.getNamelessCorpseInstoredStats());
            instoredStatsTotal.setBeMissingRelativesInstoredStatsTotal(instoredStatsTotal.getBeMissingRelativesInstoredStatsTotal() + gdnaInstoredStats.getBeMissingRelativesInstoredStats());
            instoredStatsTotal.setSuspectRelativesInstoredStatsTotal(instoredStatsTotal.getSuspectRelativesInstoredStatsTotal() + gdnaInstoredStats.getSuspectRelativesInstoredStats());
            totalCount = gdnaInstoredStats.getWzInstoredStats() + gdnaInstoredStats.getCaseIllegalCrime() + gdnaInstoredStats.getJkIllegalCrime() + gdnaInstoredStats.getSuspectInstoredStats() + gdnaInstoredStats.getVictimInstoredStats() + gdnaInstoredStats.getBeMissingInstoredStats() + gdnaInstoredStats.getNamelessCorpseInstoredStats() + gdnaInstoredStats.getBeMissingRelativesInstoredStats() + gdnaInstoredStats.getSuspectRelativesInstoredStats();
        }
        instoredStatsTotal.setTotalCount(totalCount);
        totalList.add(instoredStatsTotal);

        String templateFilePath = request.getServletContext().getRealPath("templates/dnaSampleStats.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (GdnaInstoredStats gdnaInstoredStats:gdnaInstoredStatsList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //委托单位
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(gdnaInstoredStats.getInitServerName());

                //现场物证入库数
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(gdnaInstoredStats.getWzInstoredStats());

                //违法犯罪人员入库数 （案件）
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(gdnaInstoredStats.getCaseIllegalCrime());

                //违法犯罪人员入库数（建库）
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(gdnaInstoredStats.getJkIllegalCrime());

                //嫌疑人入库数
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(gdnaInstoredStats.getSuspectInstoredStats());

                //受害人入库数
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(gdnaInstoredStats.getVictimInstoredStats());

                //失踪人员入库数
                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(gdnaInstoredStats.getBeMissingInstoredStats());

                //无名尸入库数
                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                cell.setCellValue(gdnaInstoredStats.getNamelessCorpseInstoredStats());

                //嫌疑人亲属入库数
                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                cell.setCellValue(gdnaInstoredStats.getSuspectRelativesInstoredStats());

                //失踪人员亲属入库数
                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(gdnaInstoredStats.getBeMissingRelativesInstoredStats());

                //总数
                cell = row.getCell(10);
                if (cell == null) {
                    cell = row.createCell(10);
                }
                cell.setCellValue(gdnaInstoredStats.getTotalCount());

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
            cell.setCellValue("总数");
            for (int i = 0; i < totalList.size(); i++) {
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(totalList.get(i).getWzInstoredStatsTotal());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(totalList.get(i).getCaseIllegalCrimeTotal());

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(totalList.get(i).getJkIllegalCrimeTotal());

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(totalList.get(i).getSuspectInstoredStatsTotal());

                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(totalList.get(i).getVictimInstoredStatsTotal());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(totalList.get(i).getBeMissingInstoredStatsTotal());

                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                cell.setCellValue(totalList.get(i).getNamelessCorpseInstoredStatsTotal());

                cell = row.getCell(8);
                if (cell == null) {
                    cell = row.createCell(8);
                }
                cell.setCellValue(totalList.get(i).getSuspectRelativesInstoredStatsTotal());

                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(totalList.get(i).getBeMissingRelativesInstoredStatsTotal());

                cell = row.getCell(10);
                if (cell == null) {
                    cell = row.createCell(10);
                }
                cell.setCellValue(totalList.get(i).getTotalCount());

                idx++;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_DNA库样本统计列表.xls", "utf-8"));
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
                    logger.error("导出DNA库样本统计Excel错误！", ex);
                }
            }
        }

    }
*/

}
