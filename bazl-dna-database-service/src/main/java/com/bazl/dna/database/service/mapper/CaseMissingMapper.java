package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.database.service.model.qo.CaseMissingQuery;

@Repository
public interface CaseMissingMapper {

	public int countCaseMissingList(@Param("query") CaseMissingQuery query);
	
	public List<CaseMissingQuery> findCaseMissingList(@Param("query") CaseMissingQuery query,
			@Param("offset") int offset, @Param("rows") int rows);
}
