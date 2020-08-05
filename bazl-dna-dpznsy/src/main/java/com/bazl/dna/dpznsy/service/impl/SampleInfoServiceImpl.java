package com.bazl.dna.dpznsy.service.impl;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.dao.SampleInfoMapper;
import com.bazl.dna.dpznsy.service.SampleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SampleInfoServiceImpl extends BaseController implements SampleInfoService {

    @Autowired
    SampleInfoMapper sampleInfoMapper;
}
