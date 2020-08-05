package com.bazl.dna.lims.service.impl;


import com.bazl.dna.lims.dao.PersonDetailMapper;
import com.bazl.dna.lims.service.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class PersonDetailServiceImpl implements PersonDetailService {

    @Autowired
    PersonDetailMapper personDetailMapper;


}
