package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.MarkerInfo;

import java.util.List;

public interface MarkerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MarkerInfo record);

    MarkerInfo selectByPrimaryKey(String id);

    List<MarkerInfo> selectAll();

    int updateByPrimaryKey(MarkerInfo record);
}