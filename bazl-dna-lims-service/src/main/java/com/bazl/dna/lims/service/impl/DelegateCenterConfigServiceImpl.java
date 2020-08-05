package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.dao.DelegateCenterConfigMapper;
import com.bazl.dna.lims.model.po.DelegateCenterConfig;
import com.bazl.dna.lims.service.DelegateCenterConfigService;

/**
 * Created by LX on 2019/9/12.
 */
@Service
public class DelegateCenterConfigServiceImpl implements DelegateCenterConfigService {

    @Autowired
    DelegateCenterConfigMapper delegateCenterConfigMapper;

    @Override
    public int insert(DelegateCenterConfig record) {
        return delegateCenterConfigMapper.insert(record);
    }

    @Override
    public List<DelegateCenterConfig> selectAll() {
        return delegateCenterConfigMapper.selectAll();
    }

    @Override
    public List<DelegateCenterConfig> selectQualification(String delegateOrgCodePrefix) {
        return delegateCenterConfigMapper.selectQualification(delegateOrgCodePrefix);
    }
}
