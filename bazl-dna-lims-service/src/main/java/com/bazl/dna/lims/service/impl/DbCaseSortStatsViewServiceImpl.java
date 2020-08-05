package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.lims.dao.DbCaseSortStatsViewMapper;
import com.bazl.dna.lims.model.PageInfo;
import com.bazl.dna.lims.model.view.DbCaseSortStatsView;
import com.bazl.dna.lims.service.DbCaseSortStatsViewService;

/**
 * Created by wangliu on 2019/11/5.
 */
@Service
public class DbCaseSortStatsViewServiceImpl extends BaseService implements DbCaseSortStatsViewService {

    @Autowired
    DbCaseSortStatsViewMapper dbCaseSortStatsViewMapper;

    @Override
    public List<DbCaseSortStatsView> selectDelagateOrgList() {
        return dbCaseSortStatsViewMapper.selectDelagateOrgList();
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<DbCaseSortStatsView> selectCaseSortStatsList(DbCaseSortStatsView dbCaseSortStatsView, PageInfo pageInfo) {

        List<DbCaseSortStatsView> DbCaseSortStatsViewList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            dbCaseSortStatsView.setOffset((pageNo - 1) * pageSize);
            dbCaseSortStatsView.setRows(dbCaseSortStatsView.getOffset() + pageSize);

            DbCaseSortStatsViewList = dbCaseSortStatsViewMapper.selectCaseSortStatsList(dbCaseSortStatsView);
        } catch(Exception ex) {
            logger.info("查询案件分类统计报错："+ex);
            return null;
        }

        return DbCaseSortStatsViewList;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public int selectCaseSortStatsCount(DbCaseSortStatsView dbCaseSortStatsView) {
        return dbCaseSortStatsViewMapper.selectCaseSortStatsCount(dbCaseSortStatsView);
    }

    @Override
    public List<DbCaseSortStatsView> selectExportCaseSortList(DbCaseSortStatsView dbCaseSortStatsView) {
        return dbCaseSortStatsViewMapper.selectExportCaseSortList(dbCaseSortStatsView);
    }

    @Override
    public DbCaseSortStatsView selectCaseByCaseNo(String caseNo) {
        return dbCaseSortStatsViewMapper.selectCaseByCaseNo(caseNo);
    }

    @Override
    public List<DbCaseSortStatsView> selectCaseSampleByCaseNo(String caseNo) {
        return dbCaseSortStatsViewMapper.selectCaseSampleByCaseNo(caseNo);
    }

    @Override
    public void rollback() {
        dbCaseSortStatsViewMapper.rollback();
    }
}
