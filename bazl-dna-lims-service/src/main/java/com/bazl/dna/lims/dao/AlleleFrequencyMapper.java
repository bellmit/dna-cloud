package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AlleleFrequency;

import java.util.List;

public interface AlleleFrequencyMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlleleFrequency record);

    AlleleFrequency selectByPrimaryKey(String id);

    List<AlleleFrequency> selectAll();

    int updateByPrimaryKey(AlleleFrequency record);
}