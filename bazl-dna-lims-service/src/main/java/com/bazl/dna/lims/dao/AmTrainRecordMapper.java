package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmTrainRecord;
import java.util.List;

public interface AmTrainRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmTrainRecord record);

    AmTrainRecord selectByPrimaryKey(String id);

    List<AmTrainRecord> selectAll();

    int updateByPrimaryKey(AmTrainRecord record);
}