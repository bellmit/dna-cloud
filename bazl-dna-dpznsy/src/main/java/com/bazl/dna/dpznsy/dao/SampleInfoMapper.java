package com.bazl.dna.dpznsy.dao;

import java.util.List;

import com.bazl.dna.dpznsy.model.po.SampleInfo;

public interface SampleInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SampleInfo record);

    SampleInfo selectByPrimaryKey(String id);

    List<SampleInfo> selectAll();

    int updateByPrimaryKey(SampleInfo record);
}