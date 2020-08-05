package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.LabServer;

import java.util.List;

public interface LabServerMapper {

    int deleteByPrimaryKey(String id);

    int insert(LabServer record);

    LabServer selectByPrimaryKey(String id);

    List<LabServer> selectAll();

    int updateByPrimaryKey(LabServer record);

    //查询实验室服务器表 通过时间倒叙
    List<LabServer> queryLabServerByCreateTime();
}