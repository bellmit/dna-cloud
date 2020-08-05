package com.bazl.dna.mix.connector.nation.dao;

import java.util.List;
import java.util.Map;

import com.bazl.dna.mix.connector.nation.model.po.SysDict;

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