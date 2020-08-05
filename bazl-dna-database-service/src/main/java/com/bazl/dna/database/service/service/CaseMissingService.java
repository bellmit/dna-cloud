package com.bazl.dna.database.service.service;

import java.util.Map;

import com.bazl.dna.database.service.model.qo.CaseMissingQuery;

public interface CaseMissingService {

	public Map<String, Object> findList(CaseMissingQuery query);
	
}
