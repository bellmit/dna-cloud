package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.model.po.AlleleFrequency;
import com.bazl.dna.lims.dao.AlleleFrequencyMapper;
import com.bazl.dna.lims.service.AlleleFrequencyService;
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
