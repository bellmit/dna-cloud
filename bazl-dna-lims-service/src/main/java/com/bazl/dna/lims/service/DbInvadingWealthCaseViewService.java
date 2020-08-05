package com.bazl.dna.lims.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbInvadingWealthCaseView;

/**
 * Created by wangliu on 2019/11/7.
 */
@Repository
public interface DbInvadingWealthCaseViewService {

    /**
     * 查询单位(分局)
     * @return
     */
    List<DbInvadingWealthCaseView> selectDelagateOrgList();

    /**
     * 查询送检案件数
     * @return
     */
    List<DbInvadingWealthCaseView> sjCaseCountList(DbInvadingWealthCaseView query);

    /**
     * 入库物证案件数
     * @return
     */
    List<DbInvadingWealthCaseView> rkWzCaseCountList(DbInvadingWealthCaseView query);
    /**
     * 送检样本数
     * @return
     */
    List<DbInvadingWealthCaseView> sjSampleCountList(DbInvadingWealthCaseView query);
    /**
     * 入库样本数
     * @return
     */
    List<DbInvadingWealthCaseView> rkSampleCountList(DbInvadingWealthCaseView query);

    /*---图表--*/
    /**
     * 根据年份和单位查询送检案件数(首页用)
     * @return
     */
    List<DbInvadingWealthCaseView> sjCaseCountByYearList(DbInvadingWealthCaseView query);
    /**
     * 根据年份和单位查询送检案件数(首页用 sylims)
     * @return
     */
    List<DbInvadingWealthCaseView> sySjCaseCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList);
    /**
     * 根据年份和单位查询入库物证案件数(首页用)
     * @return
     */
    List<DbInvadingWealthCaseView> rkWzCaseCountByYearList(DbInvadingWealthCaseView query);
    /**
     * 根据年份和单位查询入库物证案件数(首页用 sylims)
     * @return
     */
    List<DbInvadingWealthCaseView> syRkWzCaseCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList);

    /**
     * 根据年份和单位查询入库样本数(首页用)
     * @return
     */
    List<DbInvadingWealthCaseView> rkSampleCountByYearList(DbInvadingWealthCaseView query);
    /**
     * 根据年份和单位查询入库样本数(首页用 sylims)
     * @return
     */
    List<DbInvadingWealthCaseView> syRkSampleCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList);
    /**
     * 根据年份和单位查询入库物证案件数(首页用)
     * @return
     */
    List<DbInvadingWealthCaseView> sjSampleCountByYearList(DbInvadingWealthCaseView query);
    /**
     * 根据年份和单位查询入库物证案件数(首页用 sylims)
     * @return
     */
    List<DbInvadingWealthCaseView> sySjSampleCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList);


    /*------类别----------*/
    /**
     * 查询送检案件数(首页类别)
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjCaseByYearAndCaseType(DbInvadingWealthCaseView query);

    /**
     * 入库物证案件数(首页类别)
     * @return
     */
    List<DbInvadingWealthCaseView> selectrkWzCaseByYearAndCaseType(DbInvadingWealthCaseView query);
    /**
     * 送检样本数(首页类别)
     * @return
     */
    List<DbInvadingWealthCaseView> selectsjSampleByYearAndCaseType(DbInvadingWealthCaseView query);
    /**
     * 入库样本数(首页类别)
     * @return
     */
    List<DbInvadingWealthCaseView> selectrkSampleByYearAndCaseType(DbInvadingWealthCaseView query);
}
