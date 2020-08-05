package com.bazl.dna.test.service;

import java.util.List;
import java.util.Map;

import com.bazl.dna.test.entity.SampleGene;

public interface ISampleGeneService {

	SampleGene save(SampleGene entity);

	public SampleGene getById(String id);

	public void delete(String id);
	
	public List<Map<String, Object>> findGrantList(Map<String, Object> map);
}
