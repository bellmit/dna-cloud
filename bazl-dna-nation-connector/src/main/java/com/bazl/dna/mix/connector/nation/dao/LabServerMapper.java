package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.LabServer;

import java.util.List;

public interface LabServerMapper {

    int deleteByPrimaryKey(String id);

    int insert(LabServer record);

    LabServer selectByPrimaryKey(String id);

    List<LabServer> selectAll();

    int updateByPrimaryKey(LabServer record);

    //查询实验室服务器表 通过时间倒叙
    List<LabServer> queryLabServerByCreateTime();

    //根据服务器编号查询信息
    LabServer selectByLabServerNo(String labServerNo);
}