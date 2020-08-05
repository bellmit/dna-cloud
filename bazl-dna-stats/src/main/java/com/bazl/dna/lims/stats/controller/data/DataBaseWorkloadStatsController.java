package com.bazl.dna.lims.stats.controller.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import com.bazl.dna.lims.model.bo.WorkloadStatsModel;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.service.WorkloadStatsService;

/**
 * Created by wangliu on 2019/12/19.
 */
@Controller
@RequestMapping("/workloadStats")
public class DataBaseWorkloadStatsController extends BaseController {


    @Autowired
    WorkloadStatsService workloadStatsService;

    /**
     * 数据库工作量统计
     *
     * @param query
     * @return
     */
    @RequestMapping("/workloadStatsCntList")
    public ModelAndView workloadStatsCntList(HttpServletRequest request, WorkloadStatsModel query) {
        ModelAndView view = new ModelAndView();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        List<WorkloadStatsModel> fyTesterList = workloadStatsService.selectFyTesterList();
        List<WorkloadStatsModel> workloadStatsList = new ArrayList<>();
        try{
            query = queryHandle(query);
            if(StringUtils.isNotBlank(query.getTester())){
                workloadStatsList.add(workloadStatsService.selectWorkloadStatsCnt(query));
            }else{
                for (WorkloadStatsModel workloadStatsModel : fyTesterList) {
                    workloadStatsModel.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
                    workloadStatsModel.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());
                    workloadStatsList.add(workloadStatsService.selectWorkloadStatsCnt(workloadStatsModel));
                }
            }
            query.setTester(null);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("数据库工作量统计出错！"+e);
        }

        List<WorkloadStatsModel> totalList = totalList(workloadStatsList);

        view.addObject("totalList",totalList);
        view.addObject("testerList",fyTesterList);
        view.addObject("workloadStatsList",workloadStatsList);
        view.addObject("query",query);
        view.setViewName("query/workloadStatsList");
        return view;
    }

    /**
     * 导出数据库工作量统计查询结果excel
     * @param request
     * @param response
     * @param query
     */
    @RequestMapping("/exportWorkloadStatsExcel")
    public void exportWorkloadStatsExcel(HttpServletRequest request, HttpServletResponse response, WorkloadStatsModel query) {

        List<WorkloadStatsModel> workloadStatsList = new ArrayList<>();

        try {
            //查询试验人列表
            List<WorkloadStatsModel> fyTesterList = workloadStatsService.selectFyTesterList();

            query = queryHandle(query);
            if(StringUtils.isNotBlank(query.getTester())){
                workloadStatsList.add(workloadStatsService.selectWorkloadStatsCnt(query));
            }else{
                for (WorkloadStatsModel workloadStatsModel : fyTesterList) {
                    workloadStatsModel.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
                    workloadStatsModel.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());
                    workloadStatsList.add(workloadStatsService.selectWorkloadStatsCnt(workloadStatsModel));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("统计数据库工作量查询结果出错"+e);
        }
        List<WorkloadStatsModel> totalList = totalList(workloadStatsList);

        String templateFilePath = request.getServletContext().getRealPath("templates/workloadStatsExcel.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            for (WorkloadStatsModel workload:workloadStatsList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //试验人
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(workload.getTester());

                //完成板数(已审核)
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(workload.getAuditStateCnt());

                //完成板数(未审核)
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(workload.getNotAuditStateCnt());

                //首次实验样本总数
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(workload.getOneTestSampleCnt());

                //首次实验入库成功样本数
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(workload.getOneTestSampleRkFulfilCnt());

                //首次实验入库失败样本数
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(workload.getOneTestSampleRkFailCnt());

                //首次实验成功率
                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(workload.getOneTestFulfilRate());

                idx++;
            }

            for (WorkloadStatsModel total:totalList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                //总数
                cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue("总计");

                //完成板数(已审核)（总数）
                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellValue(total.getAuditStateCnt());

                //完成板数(未审核)（总数）
                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(total.getNotAuditStateCnt());

                //首次实验样本总数（总数）
                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(total.getOneTestSampleCnt());

                //首次实验入库成功样本数（总数）
                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(total.getOneTestSampleRkFulfilCnt());

                //首次实验入库失败样本数（总数）
                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellValue(total.getOneTestSampleRkFailCnt());

                //首次实验成功率（总数）
                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(total.getOneTestFulfilRate());

                idx++;
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + sdf.format(new Date()) + URLEncoder.encode("_数据库工作量统计列表.xls", "utf-8"));
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
                    logger.error("数据库工作量统计导出excel错误！", ex);
                }
            }
        }

    }


    /**
     * 计算总数
     * @param workloadStatsList
     * @return
     */
    public List<WorkloadStatsModel> totalList(List<WorkloadStatsModel> workloadStatsList) {
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);

        List<WorkloadStatsModel> totalList = new ArrayList<>();
        WorkloadStatsModel workloadStatsModel = new WorkloadStatsModel();
        for(WorkloadStatsModel workloadStats:workloadStatsList){
            workloadStatsModel.setAuditStateCnt(workloadStatsModel.getAuditStateCnt()+workloadStats.getAuditStateCnt());
            workloadStatsModel.setNotAuditStateCnt(workloadStatsModel.getNotAuditStateCnt()+workloadStats.getNotAuditStateCnt());
            workloadStatsModel.setOneTestSampleCnt(workloadStatsModel.getOneTestSampleCnt()+workloadStats.getOneTestSampleCnt());
            workloadStatsModel.setOneTestSampleRkFulfilCnt(workloadStatsModel.getOneTestSampleRkFulfilCnt()+workloadStats.getOneTestSampleRkFulfilCnt());
            workloadStatsModel.setOneTestSampleRkFailCnt(workloadStatsModel.getOneTestSampleRkFailCnt()+workloadStats.getOneTestSampleRkFailCnt());
        }
        //总数试验入库成功率
        float fulfilRate = 0;
        if (0 != workloadStatsModel.getOneTestSampleCnt()) {
            fulfilRate = (float) ((double) workloadStatsModel.getOneTestSampleRkFulfilCnt() / (double) workloadStatsModel.getOneTestSampleCnt());
        }
        workloadStatsModel.setOneTestFulfilRate(nf.format(Double.valueOf(df.format(fulfilRate))));

        totalList.add(workloadStatsModel);
        return totalList;
    }

    public WorkloadStatsModel queryHandle(WorkloadStatsModel query) {
        if (null == query.getTestFulfilDateTimeStart()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            query.setTestFulfilDateTimeStart(calendar.getTime());
        } else {
            query.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
        }
        if (null == query.getTestFulfilDateTimeEnd()) {
            query.setTestFulfilDateTimeEnd(new Date());
        } else {
            query.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());
        }
        return query;
    }

}
