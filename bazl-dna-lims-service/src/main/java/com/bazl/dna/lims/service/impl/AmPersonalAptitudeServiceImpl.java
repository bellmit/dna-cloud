package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.dao.AmPersonalAptitudeMapper;
import com.bazl.dna.lims.model.po.AmPersonalAptitude;
import com.bazl.dna.lims.service.AmPersonalAptitudeService;
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
