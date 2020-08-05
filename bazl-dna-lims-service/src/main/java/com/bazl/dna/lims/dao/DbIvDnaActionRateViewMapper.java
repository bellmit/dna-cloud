package com.bazl.dna.lims.dao;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbIvDnaActionRateView;

/**
 * Created by wangliu on 2019/12/13.
 */
@Repository
public interface DbIvDnaActionRateViewMapper {

    /**
     * 综合作用率查询案件送检总数 lims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSjCaseCountList(DbIvDnaActionRateView ivDnaActionRateView);
    /**
     * 综合作用率查询案件送检总数 sylims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSySjCaseCountList(DbIvDnaActionRateView ivDnaActionRateView);

    /**
     * 综合作用率查询出鉴定书数 lims
     * @param ivDnaActionRateView
     * @return
     */
    int selectIdBooksCountList(DbIvDnaActionRateView ivDnaActionRateView);
    /**
     * 综合作用率查询出鉴定书数 sylims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSyIdBooksCountList(DbIvDnaActionRateView ivDnaActionRateView);

    /**
     * 综合作用率查询有物证入库但未出鉴定的案件数 lims
     * @param ivDnaActionRateView
     * @return
     */
    int selectRkNotBookCountList(DbIvDnaActionRateView ivDnaActionRateView);
    /**
     * 综合作用率查询有物证入库但未出鉴定的案件数 sylims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSyRkNotBookCountList(DbIvDnaActionRateView ivDnaActionRateView);

    /**
     * 综合作用率查询未出鉴定的案件数 lims
     * @param ivDnaActionRateView
     * @return
     */
    int selectNotBookCountList(DbIvDnaActionRateView ivDnaActionRateView);
    /**
     * 综合作用率查询未出鉴定的案件数 sylims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSyNotBookCountList(DbIvDnaActionRateView ivDnaActionRateView);

    /**
     * 综合作用率查询有物证入库的案件数 lims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSampleRkCaseCountList(DbIvDnaActionRateView ivDnaActionRateView);
    /**
     * 综合作用率查询有物证入库的案件数 sylims
     * @param ivDnaActionRateView
     * @return
     */
    int selectSySampleRkCaseCountList(DbIvDnaActionRateView ivDnaActionRateView);

}
