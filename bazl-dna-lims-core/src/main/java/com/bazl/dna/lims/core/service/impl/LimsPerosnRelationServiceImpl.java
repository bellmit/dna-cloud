package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.LimsPerosnRelationMapper;
import com.bazl.dna.lims.core.model.po.LimsPerosnRelation;
import com.bazl.dna.lims.core.service.LimsPerosnRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class LimsPerosnRelationServiceImpl implements LimsPerosnRelationService {

    @Autowired
    LimsPerosnRelationMapper limsPerosnRelationMapper;


    @Override
    public LimsPerosnRelation selectPersonInfo(String sampleFlag) {
        return limsPerosnRelationMapper.selectBySourcePersonId(sampleFlag);
    }
}
