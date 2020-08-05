package com.bazl.dna.dpznsy.service.impl;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.dao.MemberInfoMapper;
import com.bazl.dna.dpznsy.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberInfoServiceImpl extends BaseController implements MemberInfoService {

    @Autowired
    MemberInfoMapper memberInfoMapper;
}
