package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmQualificationEvaluation;
import java.util.List;

public interface AmQualificationEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmQualificationEvaluation record);

    AmQualificationEvaluation selectByPrimaryKey(String id);

    List<AmQualificationEvaluation> selectAll();

    int updateByPrimaryKey(AmQualificationEvaluation record);
}