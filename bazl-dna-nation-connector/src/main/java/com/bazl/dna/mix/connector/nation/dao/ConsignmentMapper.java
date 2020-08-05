package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.Consignment;

import java.util.List;

public interface ConsignmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Consignment record);

    Consignment selectByPrimaryKey(String id);

    List<Consignment> selectAll();

    int updateByPrimaryKey(Consignment record);
}