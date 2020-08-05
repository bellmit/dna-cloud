package com.bazl.dna.lims.dao;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbIvDetectionRateView;

/**
 * Created by zhangbo on 2020/2/3.
 */
@Repository
public interface DbIvDetectionRateViewMapper {

    /**
     * 查询脱落细胞样本总数
     * @param dbIvDetectionRateView
     * @return
     */
    int  selectTlxbSampleCount(DbIvDetectionRateView dbIvDetectionRateView);
    /**
     * 查询物证样本总数
     * @param dbIvDetectionRateView
     * @return
     */
    int  selectWzSampleCount(DbIvDetectionRateView dbIvDetectionRateView);

    /**
     * 侵财类案件有比中样本数 lims
     * @param dbIvDetectionRateView
     * @return
     */
    int selectComparisonCount(DbIvDetectionRateView dbIvDetectionRateView);
    /**
     * 侵财类案件有比中样本数 sylims
     * @param dbIvDetectionRateView
     * @return
     */
    int selectComparisonSyCount(DbIvDetectionRateView dbIvDetectionRateView);

    /**
     * 侵财类案件无比中入库样本数 lims
     * @param dbIvDetectionRateView
     * @return
     */
    int selectNotComparisonRkCount(DbIvDetectionRateView dbIvDetectionRateView);
    /**
     * 侵财类案件无比中入库样本数 sylims
     * @param dbIvDetectionRateView
     * @return
     */
    int selectNotComparisonRkSyCount(DbIvDetectionRateView dbIvDetectionRateView);

}
