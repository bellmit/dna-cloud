package com.bazl.dna.lims.service;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbIvDnaActionRateView;

/**
 * Created by wangliu on 2019/12/13.
 */
@Repository
public interface DbIvDnaActionRateViewService {

    /**
     * 查询侵财类案件DNA综合作用率
     * @param dbIvDnaActionRateView
     * @return
     */
    DbIvDnaActionRateView selectDnaActionRate(DbIvDnaActionRateView dbIvDnaActionRateView);
    /**
     * 查询侵财类案件DNA检验率
     * @param dbIvDnaActionRateView
     * @return
     */
    DbIvDnaActionRateView selectDnaTestRate(DbIvDnaActionRateView dbIvDnaActionRateView);

}
