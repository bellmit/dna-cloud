package com.bazl.dna.dpznsy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.dao.CaseInfoMapper;
import com.bazl.dna.dpznsy.service.CaseInfoService;


@Service
public class CaseInfoServiceImpl extends BaseController implements CaseInfoService {

    @Autowired
    CaseInfoMapper caseInfoMapper;
}
