package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmTopicRecord;
import java.util.List;

public interface AmTopicRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmTopicRecord record);

    AmTopicRecord selectByPrimaryKey(String id);

    List<AmTopicRecord> selectAll();

    int updateByPrimaryKey(AmTopicRecord record);
}