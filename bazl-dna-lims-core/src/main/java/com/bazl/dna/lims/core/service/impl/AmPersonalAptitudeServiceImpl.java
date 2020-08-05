package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.AmPersonalAptitudeMapper;
import com.bazl.dna.lims.core.model.po.AmPersonalAptitude;
import com.bazl.dna.lims.core.service.AmPersonalAptitudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/5/5.
 */
@Service
public class AmPersonalAptitudeServiceImpl implements AmPersonalAptitudeService {
    @Autowired
    AmPersonalAptitudeMapper amPersonalAptitudeMapper;

    @Override
    public AmPersonalAptitude queryAmPersonalAptitudeById(String personalId) {
        return amPersonalAptitudeMapper.queryAmPersonalAptitudeById(personalId);
    }
}
