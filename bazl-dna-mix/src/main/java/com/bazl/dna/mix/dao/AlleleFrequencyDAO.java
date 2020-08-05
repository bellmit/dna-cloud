package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.AlleleFrequency;

import java.util.List;

public interface AlleleFrequencyDAO {
    int deleteByPrimaryKey(String id);

    int insert(AlleleFrequency record);

    AlleleFrequency selectByPrimaryKey(String id);

    List<AlleleFrequency> selectAll();

    int updateByPrimaryKey(AlleleFrequency record);
}