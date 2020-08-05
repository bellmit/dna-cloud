package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.lims.model.view.DbStatsCaseInfoView;

/**
 * Created by wangliu on 2020/4/3.
 */
public interface DbStatsCaseInfoViewService {


    /**
     * 查询单位
     * @return
     */
    List<DbStatsCaseInfoView> selecDelegateOrg();
    /**
     * 有效比中
     * @param dbStatsCaseInfoView
     * @return
     */
    DbStatsCaseInfoView selectEffectiveRatio(DbStatsCaseInfoView dbStatsCaseInfoView);

}
