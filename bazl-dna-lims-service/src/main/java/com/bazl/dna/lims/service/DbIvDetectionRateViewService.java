package com.bazl.dna.lims.service;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbIvDetectionRateView;

/**
 * Created by wangliu on 2020/2/3.
 */
@Repository
public interface DbIvDetectionRateViewService {

    /**
     * 脱落细胞检出率统计和现场物证入库率统计
     * @param dbIvDetectionRateView
     * @return
     */
    DbIvDetectionRateView  selectExfoliatedCellsDetectionRate(DbIvDetectionRateView dbIvDetectionRateView,String sampleType);


}
