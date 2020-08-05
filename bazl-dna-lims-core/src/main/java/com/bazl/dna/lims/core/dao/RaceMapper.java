package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.Race;

import java.util.List;

public interface RaceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Race record);

    Race selectByPrimaryKey(String id);

    List<Race> selectAll();

    int updateByPrimaryKey(Race record);
}