package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmLearnRecord;
import java.util.List;

public interface AmLearnRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmLearnRecord record);

    AmLearnRecord selectByPrimaryKey(String id);

    List<AmLearnRecord> selectAll();

    int updateByPrimaryKey(AmLearnRecord record);
}