package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.database.service.model.qo.CaseUnidentifiedQuery;

@Repository
public interface CaseUnidentifiedMapper {

	public int countCaseUnidentifiedList(@Param("query") CaseUnidentifiedQuery query);
	
	public List<CaseUnidentifiedQuery> findCaseUnidentifiedList(@Param("query") CaseUnidentifiedQuery query,
			@Param("offset") int offset, @Param("rows") int rows);
}
