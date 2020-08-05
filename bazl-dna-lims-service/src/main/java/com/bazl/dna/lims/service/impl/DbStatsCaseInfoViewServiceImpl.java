package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.model.view.DbStatsCaseInfoView;
import com.bazl.dna.lims.service.DbStatsCaseInfoViewService;
import com.bazl.dna.lims.dao.DbStatsCaseInfoViewMapper;

/**
 * Created by wangliu on 2020/4/3.
 */
@Service
public class DbStatsCaseInfoViewServiceImpl extends BaseService implements DbStatsCaseInfoViewService {

    @Autowired
    DbStatsCaseInfoViewMapper dbStatsCaseInfoViewMapper;


    @Override
    public List<DbStatsCaseInfoView> selecDelegateOrg() {
        return dbStatsCaseInfoViewMapper.selecDelegateOrg();
    }

    @Override
    public DbStatsCaseInfoView selectEffectiveRatio(DbStatsCaseInfoView dbStatsCaseInfoView) {

        try {
            dbStatsCaseInfoView.setCaseCnt(dbStatsCaseInfoViewMapper.selectCaseCntByDelegateOrg(dbStatsCaseInfoView));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询查询案件总数错误！");
        }

        try {
            dbStatsCaseInfoView.setRkCaseCnt(dbStatsCaseInfoViewMapper.selectRkCaseCntByDelegateOrg(dbStatsCaseInfoView));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询查询入库案件数错误！");
        }

        try {
            dbStatsCaseInfoView.setBzXyrCaseCnt(dbStatsCaseInfoViewMapper.selectBzXyrCaseCntByDelegateOrg(dbStatsCaseInfoView));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询查询比中嫌疑人案件数错误！");
        }

        try {
            dbStatsCaseInfoView.setBzWzCaseCnt(dbStatsCaseInfoViewMapper.selectBzWzCaseCntByDelegateOrg(dbStatsCaseInfoView));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询查询比中物证案件数错误！");
        }
        /**
         * 其他入库案件数
         */
        dbStatsCaseInfoView.setOtherCaseCnt(dbStatsCaseInfoView.getRkCaseCnt() - dbStatsCaseInfoView.getBzXyrCaseCnt() - dbStatsCaseInfoView.getBzWzCaseCnt());

        return dbStatsCaseInfoView;
    }
}
