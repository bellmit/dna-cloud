package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.SysDictCategory;

import java.util.List;

public interface SysDictCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDictCategory record);

    SysDictCategory selectByPrimaryKey(String id);

    List<SysDictCategory> selectAll();

    int updateByPrimaryKey(SysDictCategory record);
}