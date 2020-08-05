package com.bazl.dna.lims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.view.DbIvDetectionRateView;
import com.bazl.dna.lims.service.DbIvDetectionRateViewService;
import com.bazl.dna.lims.dao.DbIvDetectionRateViewMapper;

/**
 * Created by wangliu on 2020/2/3.
 */
@Service
public class DbIvDetectionRateViewServiceImpl extends BaseService implements DbIvDetectionRateViewService {

    @Autowired
    DbIvDetectionRateViewMapper dbIvDetectionRateViewMapper;


    @Override
    public DbIvDetectionRateView selectExfoliatedCellsDetectionRate(DbIvDetectionRateView dbIvDetectionRateView,String sampleType) {
        if (sampleType.equals(Constants.SAMPLE_TYPE_TLXB)){
            dbIvDetectionRateView.setSampleCount(String.valueOf(dbIvDetectionRateViewMapper.selectTlxbSampleCount(dbIvDetectionRateView)));
        }else if(sampleType.equals(Constants.SAMPLE_TYPE_WZ)){
            dbIvDetectionRateView.setSampleCount(String.valueOf(dbIvDetectionRateViewMapper.selectWzSampleCount(dbIvDetectionRateView)));
        }

        int comparisonCount = 0;
        try {
             comparisonCount = dbIvDetectionRateViewMapper.selectComparisonCount(dbIvDetectionRateView);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询lims比中样本数错误！"+e);
        }

        int syComparisonCount = 0;
        try {
             syComparisonCount = dbIvDetectionRateViewMapper.selectComparisonSyCount(dbIvDetectionRateView);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询alims比中样本数错误！"+e);
        }
        dbIvDetectionRateView.setComparisonCount(String.valueOf(comparisonCount+syComparisonCount));

        int notComparisonRkCount = 0;
        try {
            notComparisonRkCount = dbIvDetectionRateViewMapper.selectNotComparisonRkCount(dbIvDetectionRateView);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询lims无比中入库样本数错误！"+e);
        }
        int notComparisonRkSyCount = 0;
        try {
            notComparisonRkSyCount = dbIvDetectionRateViewMapper.selectNotComparisonRkSyCount(dbIvDetectionRateView);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询alims无比中入库样本数错误！"+e);
        }
        dbIvDetectionRateView.setNotComparisonRkCount(String.valueOf(notComparisonRkCount+notComparisonRkSyCount));

        /**计算完成率*/
        int chu1 = Integer.parseInt(dbIvDetectionRateView.getComparisonCount()) + Integer.parseInt(dbIvDetectionRateView.getNotComparisonRkCount());
        float chu2 = 0;
        if (0 != chu1 && 0 != Integer.parseInt(dbIvDetectionRateView.getSampleCount())) {
             chu2 = (float) ((double) chu1 / Double.valueOf(dbIvDetectionRateView.getSampleCount()));
        }
        dbIvDetectionRateView.setComputedResult(String.valueOf(chu2));

        return dbIvDetectionRateView;
    }

}
