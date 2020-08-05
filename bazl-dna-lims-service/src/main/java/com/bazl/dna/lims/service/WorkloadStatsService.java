package com.bazl.dna.lims.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.bo.WorkloadStatsModel;

/**
 * Created by wangliu on 2019/12/19.
 */
@Repository
public interface WorkloadStatsService  {

    /**
     * 查询实验员（法医）
     * @return
     */
    List<WorkloadStatsModel> selectFyTesterList();

    /**
     * 数据库工作量统计
     * @param workloadStatsModel
     * @return
     */
    WorkloadStatsModel selectWorkloadStatsCnt(WorkloadStatsModel workloadStatsModel);

}
