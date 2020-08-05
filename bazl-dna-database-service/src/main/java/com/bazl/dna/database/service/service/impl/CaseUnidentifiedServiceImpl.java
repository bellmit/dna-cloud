package com.bazl.dna.database.service.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.database.service.mapper.CaseUnidentifiedMapper;
import com.bazl.dna.database.service.model.qo.CaseUnidentifiedQuery;
import com.bazl.dna.database.service.service.CaseUnidentifiedService;
import com.google.common.collect.Maps;

@Service
public class CaseUnidentifiedServiceImpl implements CaseUnidentifiedService {
	
	@Autowired
	private CaseUnidentifiedMapper mapper;
	
	@Override
	public Map<String, Object> findList(CaseUnidentifiedQuery data) {
		int totalCount = mapper.countCaseUnidentifiedList(data);
		List<CaseUnidentifiedQuery> list = mapper.findCaseUnidentifiedList(data, data.getOffset(), data.getRows());
		
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_PAGE, new PageInfo(data.getPageIndex(), data.getRows(), totalCount));
		result.put(PublicConstants.PARAM_LIST, list);
		return result;
	}

}
