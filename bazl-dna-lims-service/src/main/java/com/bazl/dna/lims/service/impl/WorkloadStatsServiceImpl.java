package com.bazl.dna.lims.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.bo.WorkloadStatsModel;
import com.bazl.dna.lims.service.WorkloadStatsService;
import com.bazl.dna.lims.dao.WorkloadStatsMapper;

/**
 * Created by wangliu on 2019/12/19.
 */
@Service
public class WorkloadStatsServiceImpl extends BaseService implements WorkloadStatsService {

    @Autowired
    WorkloadStatsMapper workloadStatsMapper;


    @Override
    public List<WorkloadStatsModel> selectFyTesterList() {
        return workloadStatsMapper.selectFyTesterList();
    }

    @Override
    public WorkloadStatsModel selectWorkloadStatsCnt(WorkloadStatsModel query) {
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);

        WorkloadStatsModel workloadStatsModel = new WorkloadStatsModel();
        workloadStatsModel.setTester(query.getTester());
        try {
            WorkloadStatsModel queryCriteria1 = new WorkloadStatsModel();
            queryCriteria1.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
            queryCriteria1.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());
            queryCriteria1.setTester(query.getTester());
            queryCriteria1.setAuditState(Constants.AUDIT_STATS_ONE);
            //完成板数（已审核）
            int auditStateCnt = workloadStatsMapper.selectAuditStateCnt(queryCriteria1);
            queryCriteria1.setAuditState(Constants.AUDIT_STATS_ZERO);
            //完成板数（未审核）
            int notAuditStateCnt = workloadStatsMapper.selectAuditStateCnt(queryCriteria1);

            workloadStatsModel.setAuditStateCnt(auditStateCnt);
            workloadStatsModel.setNotAuditStateCnt(notAuditStateCnt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据库工作量统计-统计完成板数出错" + e);
        }

        try {
            WorkloadStatsModel queryCriteria2 = new WorkloadStatsModel();
            queryCriteria2.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
            queryCriteria2.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());
            queryCriteria2.setTester(query.getTester());
            //首次试验样本数
            int oneTestSampleCnt = workloadStatsMapper.selectOneTestCnt(queryCriteria2);
            queryCriteria2.setAuditState(Constants.AUDIT_STATS_ONE);
            queryCriteria2.setIsStore(Constants.RK_STATS_ONE);
            //首次试验 入库成功的样本数
            int oneTestRkFulfilCnt = workloadStatsMapper.selectOneTestCnt(queryCriteria2);
            queryCriteria2.setIsStore(Constants.RK_STATS_ZERO);
            //首次试验 入库失败的样本数
            int oneTestRkFailCnt = workloadStatsMapper.selectOneTestCnt(queryCriteria2);

            //首次试验入库成功率
            float fulfilRate = 0;
            if (0 != oneTestSampleCnt) {
                fulfilRate = (float) ((double) oneTestRkFulfilCnt / (double) oneTestSampleCnt);
            }
            workloadStatsModel.setOneTestFulfilRate(nf.format(Double.valueOf(df.format(fulfilRate))));

            workloadStatsModel.setOneTestSampleCnt(oneTestSampleCnt);
            workloadStatsModel.setOneTestSampleRkFulfilCnt(oneTestRkFulfilCnt);
            workloadStatsModel.setOneTestSampleRkFailCnt(oneTestRkFailCnt);

            workloadStatsModel.setTestFulfilDateTimeStart(query.getTestFulfilDateTimeStart());
            workloadStatsModel.setTestFulfilDateTimeEnd(query.getTestFulfilDateTimeEnd());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据库工作量统计-查询首次的试验样本总数、入库成功的样本数 、入库失败的样本数" + e);
        }

        return workloadStatsModel;
    }

}

