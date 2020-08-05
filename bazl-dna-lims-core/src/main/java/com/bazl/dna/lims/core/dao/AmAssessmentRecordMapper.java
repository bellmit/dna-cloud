package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmAssessmentRecord;
import java.util.List;

public interface AmAssessmentRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmAssessmentRecord record);

    AmAssessmentRecord selectByPrimaryKey(String id);

    List<AmAssessmentRecord> selectAll();

    int updateByPrimaryKey(AmAssessmentRecord record);
}