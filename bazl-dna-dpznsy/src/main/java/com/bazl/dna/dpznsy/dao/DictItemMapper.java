package com.bazl.dna.dpznsy.dao;


import com.bazl.dna.dpznsy.model.po.DictItem;

import java.util.List;

public interface DictItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(DictItem record);

    DictItem selectByPrimaryKey(String id);

    List<DictItem> selectAll();

    int updateByPrimaryKey(DictItem record);

    List<DictItem> selectByDictTypeCode(String dictTypeCode);
}