package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.bo.WorkloadStatsModel;

/**
 * Created by wangliu on 2019/11/19.
 */
@Repository
public interface WorkloadStatsMapper {

    /**
     * 查询实验员（法医）
     * @return
     */
   List<WorkloadStatsModel> selectFyTesterList();

    /**
     * 查询完成板数（审核/未审核）
     * @param workloadStatsModel
     * @return
     */
    int selectAuditStateCnt(WorkloadStatsModel workloadStatsModel);
    /**
     * 查询首次的试验样本总数、入库成功的样本数 、入库失败的样本数
     * @param workloadStatsModel
     * @return
     */
    int selectOneTestCnt(WorkloadStatsModel workloadStatsModel);
    /**
     * 查询重做试验的样本总数、入库成功的样本数
     * @param workloadStatsModel
     * @return
     */
    int selectTwoTestCnt(WorkloadStatsModel workloadStatsModel);
    /**
     * 需重做试验的样本数
     * @return
     */
    int selectNeedTestCnt();

    /**
     * 回滚
     */
    void rollback();
}
