package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.dao.LabDetailsMapper;
import com.bazl.dna.lims.model.po.LabDetails;
import com.bazl.dna.lims.service.LabDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/4/29.
 */
@Service
public class LabDetailsServiceImpl implements LabDetailsService {
    @Autowired
    LabDetailsMapper labDetailsMapper;

    @Override
    public List<LabDetails> querylabDetailsList(String labId) {
        return labDetailsMapper.querylabDetailsList(labId);
    }
}
