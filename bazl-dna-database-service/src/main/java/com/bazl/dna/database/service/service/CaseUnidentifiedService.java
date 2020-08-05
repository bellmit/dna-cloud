package com.bazl.dna.database.service.service;

import java.util.Map;

import com.bazl.dna.database.service.model.qo.CaseUnidentifiedQuery;

public interface CaseUnidentifiedService {

	public Map<String, Object> findList(CaseUnidentifiedQuery query);
	
}
