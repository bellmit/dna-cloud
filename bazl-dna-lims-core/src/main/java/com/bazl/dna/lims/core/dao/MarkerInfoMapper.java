package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.MarkerInfo;

import java.util.List;

public interface MarkerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MarkerInfo record);

    MarkerInfo selectByPrimaryKey(String id);

    List<MarkerInfo> selectAll();

    int updateByPrimaryKey(MarkerInfo record);
}