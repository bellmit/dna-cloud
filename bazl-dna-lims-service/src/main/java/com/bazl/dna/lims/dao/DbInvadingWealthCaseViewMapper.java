package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbInvadingWealthCaseView;

/**
 * Created by wangliu on 2019/11/5.
 */
@Repository
public interface DbInvadingWealthCaseViewMapper {
    /**
     * 查询单位(分局)
     * @return
     */
    List<DbInvadingWealthCaseView> selectDelagateOrgList();

    /**
     * 根据分局code和案件性质查询送检案件数 lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjCaseByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询送检案件数 sy
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjCaseByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询物证入库案件数 lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkWzCaseByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询物证入库案件数 sy
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkWzCaseByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询入库样本数 lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkSampleByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询入库样本数 sy
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkSampleByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询送检样本数 lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjSampleByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和案件性质查询送检样本数 sy
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjSampleByOrgCodeAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和年份查询送检案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjCaseCountByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据分局code和年份查询送检案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjCaseCountByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);


    /**
     * 根据分局code和年查询入库物证案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkWzCaseByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据分局code和年查询入库物证案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkWzCaseByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和年查询入库样本数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkSampleByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据分局code和年查询入库样本数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkSampleByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据分局code和年查询送检样本数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjSampleByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据分局code和年查询送检样本数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjSampleByOrgCodeAndYear(DbInvadingWealthCaseView dbInvadingWealthCaseView);


    /**
     * 根据年份和案件性质查询送检案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjCaseByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据年份和案件性质查询送检案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjCaseByYeareAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);


    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkWzCaseByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkWzCaseByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectRkSampleByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSyRkSampleByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);

    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) lims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSjSampleByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);
    /**
     * 根据年份和案件性质查询物证入库案件数(首页展示用) sylims
     * @return
     */
    List<DbInvadingWealthCaseView> selectSySjSampleByYearAndCaseTypeList(DbInvadingWealthCaseView dbInvadingWealthCaseView);
}
