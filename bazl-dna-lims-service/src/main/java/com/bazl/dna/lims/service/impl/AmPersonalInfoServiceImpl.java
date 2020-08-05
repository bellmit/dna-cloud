package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.dao.AmPersonalInfoMapper;
import com.bazl.dna.lims.model.po.AmPersonalInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;

/**
 * Created by Administrator on 2018/12/21.
 */
@Service
public class AmPersonalInfoServiceImpl implements AmPersonalInfoService {

    @Autowired
    private AmPersonalInfoMapper amPersonalInfoMapper;

    @Override
    public List<AmPersonalInfo> queryAmPersonalInfoLIst(String orgId) {
        return amPersonalInfoMapper.queryAmPersonalInfoLIst(orgId);
    }

    @Override
    public List<AmPersonalInfoVo> queryAmPersonalInfoVoList(String orgId) {
        return amPersonalInfoMapper.queryAmPersonalInfoVoList(orgId);
    }

    @Override
    public void addAmPersonalInfo(AmPersonalInfo amPersonalInfo) {
        amPersonalInfoMapper.addAmPersonalInfo(amPersonalInfo);
    }

    @Override
    public void deleteAmPersonalInfo(AmPersonalInfo amPersonalInfo) {
        amPersonalInfoMapper.deleteAmPersonalInfo(amPersonalInfo);
    }

    @Override
    public void updateAmPersonalInfo(AmPersonalInfo amPersonalInfo) {
        amPersonalInfoMapper.updateAmPersonalInfo(amPersonalInfo);
    }

    @Override
    public AmPersonalInfo queryAmPersonalInfo(String personalId) {
        return amPersonalInfoMapper.queryAmPersonalInfo(personalId);
    }

    @Override
    public void updateAmPersonalInfoById(AmPersonalInfo amPersonalInfo) {
        amPersonalInfoMapper.updateAmPersonalInfoById(amPersonalInfo);
    }

    @Override
    public AmPersonalInfo selectByPersonalId(String personalId) {
        return amPersonalInfoMapper.selectByPersonalId(personalId);
    }

    @Override
    public List<AmPersonalInfo> queryAmPersonalInfoListOrgId(String orgId) {
        return amPersonalInfoMapper.queryAmPersonalInfoListOrgId(orgId);
    }

    @Override
    public List<AmPersonalInfoVo> selectVOList(AmPersonalInfoVo query, PageInfo pageInfo) {

        try {

            int pageNo = pageInfo.getPage();
            int pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            return amPersonalInfoMapper.selectVOList(query);
        }catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public int selectVOCnt(AmPersonalInfoVo query) {
        try {
            return amPersonalInfoMapper.selectVOCnt(query);
        }catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }
}
