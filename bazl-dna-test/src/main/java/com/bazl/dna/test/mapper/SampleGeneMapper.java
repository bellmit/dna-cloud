package com.bazl.dna.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SampleGeneMapper {
	
	public List<Map<String, Object>> findGrantList(@Param("params") Map<String, Object> params);
}
