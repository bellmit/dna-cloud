package com.bazl.dna.mix.service.impl;

import com.bazl.dna.mix.dao.AlleleFrequencyDAO;
import com.bazl.dna.mix.model.po.AlleleFrequency;
import com.bazl.dna.mix.service.AlleleFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sun on 2019/4/2.
 */
@Service
public class AlleleFrequencyServiceImpl implements AlleleFrequencyService {

    @Autowired
    AlleleFrequencyDAO alleleFrequencyDAO;

    @Override
    public List<AlleleFrequency> selectAll() {
        return alleleFrequencyDAO.selectAll();
    }
}
