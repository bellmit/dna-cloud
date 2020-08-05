package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.AmPersonalInfoMapper;
import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.core.service.AmPersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
