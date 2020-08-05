package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LimsSampleInfoRest;
import java.util.List;

public interface LimsSampleInfoRestMapper {
    int deleteByPrimaryKey(String sampleId);

    int insert(LimsSampleInfoRest record);

    LimsSampleInfoRest selectByPrimaryKey(String sampleId);

    List<LimsSampleInfoRest> selectAll();

    int updateByPrimaryKey(LimsSampleInfoRest record);
}