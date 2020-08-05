package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbComprehensiveCaseCountView;

/**
 * Created by wangliu on 2019/11/5.
 */
@Repository
public interface DbComprehensiveCaseCountViewMapper {

    /**
     * 查询单位(分局)
     * @return
     */
    List<DbComprehensiveCaseCountView> selectDelagateOrgList();
    /**
     * 根据分局code查询案件受理数 lims
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSjCaseByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 根据分局code查询案件受理数 SY
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSySjCaseByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 根据分局code查询出具鉴定书数 lims
     * @return
     */
    List<DbComprehensiveCaseCountView> selectCaseBooksByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 根据分局code查询出具鉴定书数 SY
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSyCaseBooksByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 根据分局code查询送检物证数 lims
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSjSampleByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 根据分局code查询送检物证数 SY
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSySjSampleByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
}
