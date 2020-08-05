package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.SamplePersonMap;

import java.util.List;

public interface SamplePersonMapMapper {
    int deleteByPrimaryKey(String id);

    int insert(SamplePersonMap record);

    SamplePersonMap selectByPrimaryKey(String id);

    SamplePersonMap selectByPrimaryKeySample(String id);

    List<SamplePersonMap> selectByPrimarySample(String id);

    List<SamplePersonMap> selectAll();

    int updateByPrimaryKey(SamplePersonMap record);
}