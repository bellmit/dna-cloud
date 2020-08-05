package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbCaseSortStatsView;

/**
 * Created by wangliu on 2019/11/5.
 */
@Repository
public interface DbCaseSortStatsViewMapper {

    /**
     * 查询单位
     * @return
     */
    List<DbCaseSortStatsView> selectDelagateOrgList();

    /**
     * 案件分类统计查询
     * @param dbCaseSortStatsView
     * @return
     */
    List<DbCaseSortStatsView> selectCaseSortStatsList(DbCaseSortStatsView dbCaseSortStatsView);


    int selectCaseSortStatsCount(DbCaseSortStatsView dbCaseSortStatsView);

    /**
     * 导出查询
     * @param dbCaseSortStatsView
     * @return
     */
    List<DbCaseSortStatsView> selectExportCaseSortList(DbCaseSortStatsView dbCaseSortStatsView);

    /**
     * 根据案件编号查询案件信息
     * @param caseNo
     * @return
     */
    DbCaseSortStatsView selectCaseByCaseNo(String caseNo);

    /**
     * 根据案件编号查询样本列表
     * @param caseNo
     * @return
     */
    List<DbCaseSortStatsView> selectCaseSampleByCaseNo(String caseNo);

    /**
     * 回滚
     */
    void rollback();
}
