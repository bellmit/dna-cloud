package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.SysDict;

import java.util.List;
import java.util.Map;

public interface SysDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    SysDict selectByPrimaryKey(String id);

    List<SysDict> selectAll();

    int updateByPrimaryKey(SysDict record);

    //查询所有的人员类型
    List<Map<String, Object>> selectPersonCategory();

    SysDict selectByPrimaryKeyBean(SysDict bean);

}