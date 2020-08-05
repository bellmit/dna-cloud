package com.bazl.dna.lims.core.service;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.core.model.po.PersonDetail;

@Repository
public interface PersonDetailService {

	public PersonDetail selectByDetailId(String personDetailId);

}