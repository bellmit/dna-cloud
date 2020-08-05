package com.bazl.dna.database.service.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.database.service.mapper.CaseMissingMapper;
import com.bazl.dna.database.service.model.qo.CaseMissingQuery;
import com.bazl.dna.database.service.service.CaseMissingService;
import com.google.common.collect.Maps;

@Service
public class CaseMissingServiceImpl implements CaseMissingService {
	
	@Autowired
	private CaseMissingMapper mapper;
	
	@Override
	public Map<String, Object> findList(CaseMissingQuery data) {
		int totalCount = mapper.countCaseMissingList(data);
		List<CaseMissingQuery> list = mapper.findCaseMissingList(data, data.getOffset(), data.getRows());
		
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_PAGE, new PageInfo(data.getPageIndex(), data.getRows(), totalCount));
		result.put(PublicConstants.PARAM_LIST, list);
		return result;
	}

}
