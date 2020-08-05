package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.DictInfo;

import java.util.List;

public interface DictInfoMapper {
    int deleteByPrimaryKey(String dictInfoId);

    int insert(DictInfo record);

    DictInfo selectByPrimaryKey(String dictInfoId);

    List<DictInfo> selectAll();

    int updateByPrimaryKey(DictInfo record);

    DictInfo selectByDictTypeCode(String dicttypecode);
}