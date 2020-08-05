package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.SamplePersonMap;

import java.util.List;

public interface SamplePersonMapMapper {
    int deleteByPrimaryKey(String id);

    int insert(SamplePersonMap record);

    SamplePersonMap selectByPrimaryKey(String id);

    List<SamplePersonMap> selectAll();

    int updateByPrimaryKey(SamplePersonMap record);
}