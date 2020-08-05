package com.bazl.dna.lims.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.model.view.DbIvDnaActionRateView;
import com.bazl.dna.lims.service.DbIvDnaActionRateViewService;
import com.bazl.dna.lims.dao.DbIvDnaActionRateViewMapper;

/**
 * Created by wangliu on 2019/12/13.
 */
@Service
public class DbIvDnaActionRateViewServiceImpl extends BaseService implements DbIvDnaActionRateViewService {

    @Autowired
    DbIvDnaActionRateViewMapper dbIvDnaActionRateViewMapper;

    @Override
    public DbIvDnaActionRateView selectDnaActionRate(DbIvDnaActionRateView dbIvDnaActionRateView) {
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        try {
            int sjCaseCnt = dbIvDnaActionRateViewMapper.selectSjCaseCountList(dbIvDnaActionRateView);
            int sySjCaseCnt = dbIvDnaActionRateViewMapper.selectSySjCaseCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setSjCaseCount(String.valueOf(sjCaseCnt + sySjCaseCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA综合作用率查询送检案件数出错" + e);
        }

        try {
            int idBooksCnt = dbIvDnaActionRateViewMapper.selectIdBooksCountList(dbIvDnaActionRateView);
            int syIdBooksCnt = dbIvDnaActionRateViewMapper.selectSyIdBooksCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setIssueCaseCount(String.valueOf(idBooksCnt + syIdBooksCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA综合作用率查询出鉴定书数出错" + e);
        }

        try {
            int rkNotBookCnt = dbIvDnaActionRateViewMapper.selectRkNotBookCountList(dbIvDnaActionRateView);
            int syRkNotBookCnt = dbIvDnaActionRateViewMapper.selectSyRkNotBookCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setRkNotIssueCaseCount(String.valueOf(rkNotBookCnt + syRkNotBookCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA综合作用率查询有物证入库但未出鉴定的案件数出错" + e);
        }

        try {
            int notBookCnt = dbIvDnaActionRateViewMapper.selectNotBookCountList(dbIvDnaActionRateView);
            int syNotBookCnt = dbIvDnaActionRateViewMapper.selectSyNotBookCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setNotIssueCaseCount(String.valueOf(notBookCnt + syNotBookCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA综合作用率查询未出鉴定的案件数出错" + e);
        }
        //计算作用率
        float chu1 = 0;
        float chu2 = 0;
        if (0 != Integer.parseInt(dbIvDnaActionRateView.getSjCaseCount())) {
            chu1 = (float) (Double.valueOf(dbIvDnaActionRateView.getIssueCaseCount()) / Double.valueOf(dbIvDnaActionRateView.getSjCaseCount()));
        }
        if (0 != Integer.parseInt(dbIvDnaActionRateView.getNotIssueCaseCount())) {
            chu2 = (float) (Double.valueOf(dbIvDnaActionRateView.getRkNotIssueCaseCount()) / Double.valueOf(dbIvDnaActionRateView.getNotIssueCaseCount()));
        }
        dbIvDnaActionRateView.setComputedResult(nf.format(Double.valueOf(df.format(chu1 + chu2))));
        return dbIvDnaActionRateView;
    }

    @Override
    public DbIvDnaActionRateView selectDnaTestRate(DbIvDnaActionRateView dbIvDnaActionRateView) {
        //设置保留位数
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        try {
            int sjCaseCnt = dbIvDnaActionRateViewMapper.selectSjCaseCountList(dbIvDnaActionRateView);
            int sySjCaseCnt = dbIvDnaActionRateViewMapper.selectSySjCaseCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setSjCaseCount(String.valueOf(sjCaseCnt + sySjCaseCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA检验率查询送检案件数出错" + e);
        }

        try {
            int sampleRkCaseCnt = dbIvDnaActionRateViewMapper.selectSampleRkCaseCountList(dbIvDnaActionRateView);
            int SySampleRkCaseCnt = dbIvDnaActionRateViewMapper.selectSySampleRkCaseCountList(dbIvDnaActionRateView);
            dbIvDnaActionRateView.setRkCaseCount(String.valueOf(sampleRkCaseCnt + SySampleRkCaseCnt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("侵财类案件DNA检验率查询有物证入库的案件数出错" + e);
        }
        //计算检验率
        float testRate = 0;
        if (0 != Integer.parseInt(dbIvDnaActionRateView.getSjCaseCount())) {
            testRate = (float) (Double.valueOf(dbIvDnaActionRateView.getRkCaseCount()) / Double.valueOf(dbIvDnaActionRateView.getSjCaseCount()));
        }
        dbIvDnaActionRateView.setComputedResult(nf.format(Double.valueOf(df.format(testRate))));
        return dbIvDnaActionRateView;
    }
}
