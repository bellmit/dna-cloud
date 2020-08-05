package com.bazl.dna.mix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.mix.dao.ContributorPossibilityMapper;
import com.bazl.dna.mix.model.po.ContributorPossibility;
import com.bazl.dna.mix.service.ContributorPossibilityService;

@Service
public class ContributorPossibilityImpl implements ContributorPossibilityService {
	
	@Autowired
	private ContributorPossibilityMapper mapper;

	@Override
	public List<ContributorPossibility> findListByCompareId(String compareId) {
		return mapper.findListByCompareId(compareId);
	}

	
}
