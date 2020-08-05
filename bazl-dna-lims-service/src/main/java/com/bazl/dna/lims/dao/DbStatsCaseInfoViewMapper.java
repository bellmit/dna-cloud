package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.view.DbStatsCaseInfoView;

/**
 * 有效比中
 * Created by wangliu on 2020/4/3.
 */
@Repository
public interface DbStatsCaseInfoViewMapper {

    /**
     * 查询单位
     * @return
     */
     List<DbStatsCaseInfoView> selecDelegateOrg();

    /**
     * 查询案件总数
     * @param dbStatsCaseInfoView
     * @return
     */
     int selectCaseCntByDelegateOrg(DbStatsCaseInfoView dbStatsCaseInfoView);
    /**
     * 查询入库案件总数
     * @param dbStatsCaseInfoView
     * @return
     */
    int selectRkCaseCntByDelegateOrg(DbStatsCaseInfoView dbStatsCaseInfoView);
    /**
     * 查询比中嫌疑人案件总数
     * @param dbStatsCaseInfoView
     * @return
     */
    int selectBzXyrCaseCntByDelegateOrg(DbStatsCaseInfoView dbStatsCaseInfoView);
    /**
     * 查询比中物证案件总数
     * @param dbStatsCaseInfoView
     * @return
     */
    int selectBzWzCaseCntByDelegateOrg(DbStatsCaseInfoView dbStatsCaseInfoView);

}
