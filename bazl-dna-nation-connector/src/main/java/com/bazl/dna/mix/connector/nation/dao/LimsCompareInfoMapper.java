package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.LimsCompareInfo;

import java.util.List;

public interface LimsCompareInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LimsCompareInfo record);

    LimsCompareInfo selectByPrimaryKey(String id);

    List<LimsCompareInfo> selectAll();

    int updateByPrimaryKey(LimsCompareInfo record);
}