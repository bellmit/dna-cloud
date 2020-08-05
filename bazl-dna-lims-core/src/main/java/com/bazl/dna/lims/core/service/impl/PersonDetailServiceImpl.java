package com.bazl.dna.lims.core.service.impl;


import com.bazl.dna.lims.core.dao.PersonDetailMapper;
import com.bazl.dna.lims.core.model.po.PersonDetail;
import com.bazl.dna.lims.core.service.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class PersonDetailServiceImpl implements PersonDetailService {

    @Autowired
    private PersonDetailMapper personDetailMapper;

	@Override
	public PersonDetail selectByDetailId(String personDetailId) {
		return personDetailMapper.selectByDetailId(personDetailId);
	}


}
