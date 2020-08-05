package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.MatchCaseResult;
import java.util.List;

public interface MatchCaseResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(MatchCaseResult record);

    MatchCaseResult selectByPrimaryKey(String id);

    List<MatchCaseResult> selectAll();

    int updateByPrimaryKey(MatchCaseResult record);
}