package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.model.po.AlleleFrequency;
import com.bazl.dna.lims.core.dao.AlleleFrequencyMapper;
import com.bazl.dna.lims.core.service.AlleleFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sun on 2019/4/2.
 */
@Service
public class AlleleFrequencyServiceImpl implements AlleleFrequencyService {

    @Autowired
    AlleleFrequencyMapper alleleFrequencyMapper;

    @Override
    public List<AlleleFrequency> selectAll() {
        return alleleFrequencyMapper.selectAll();
    }
}
