package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.SysDictCategory;

import java.util.List;

public interface SysDictCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDictCategory record);

    SysDictCategory selectByPrimaryKey(String id);

    List<SysDictCategory> selectAll();

    int updateByPrimaryKey(SysDictCategory record);
}