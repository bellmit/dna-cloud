package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.MatchCaseResult;
import java.util.List;

public interface MatchCaseResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(MatchCaseResult record);

    MatchCaseResult selectByPrimaryKey(String id);

    List<MatchCaseResult> selectAll();

    int updateByPrimaryKey(MatchCaseResult record);
}