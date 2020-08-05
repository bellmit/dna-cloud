package com.bazl.dna.lims.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbComprehensiveCaseCountView;

/**
 * Created by wangliu on 2019/11/7.
 */
@Repository
public interface DbComprehensiveCaseCountViewService {

    /**
     * 查询单位(分局)
     * @return
     */
    List<DbComprehensiveCaseCountView> selectDelagateOrgList();
    /**
     * 查询综合统计数据
     * @return
     */
    List<DbComprehensiveCaseCountView> selectCountByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 查询综合统计数据sy
     * @return
     */
    List<DbComprehensiveCaseCountView> selectCountByOrgCodeSyList(List<DbComprehensiveCaseCountView> dbInvadingWealthCaseViewList,DbComprehensiveCaseCountView dbComprehensiveCaseCountView);

    /**
     * 案件发送鉴定统计数据
     * @return
     */
    List<DbComprehensiveCaseCountView> selectCaseSendBooksCountList(DbComprehensiveCaseCountView dbInvadingWealthCaseView);
    /**
     * 案件发送鉴定统计数据sy
     * @return
     */
    List<DbComprehensiveCaseCountView> selectSyCaseSendBooksCountList(List<DbComprehensiveCaseCountView> dbInvadingWealthCaseViewList,DbComprehensiveCaseCountView dbComprehensiveCaseCountView);

}
