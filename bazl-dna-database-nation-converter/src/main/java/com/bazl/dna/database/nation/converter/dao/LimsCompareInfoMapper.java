package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.LimsCompareInfo;

import java.util.List;

public interface LimsCompareInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LimsCompareInfo record);

    LimsCompareInfo selectByPrimaryKey(String id);

    List<LimsCompareInfo> selectAll();

    int updateByPrimaryKey(LimsCompareInfo record);
}