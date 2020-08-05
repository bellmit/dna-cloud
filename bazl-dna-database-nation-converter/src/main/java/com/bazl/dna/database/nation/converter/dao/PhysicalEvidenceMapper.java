package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.PhysicalEvidence;

import java.util.List;

public interface PhysicalEvidenceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PhysicalEvidence record);

    PhysicalEvidence selectByPrimaryKey(String id);

    List<PhysicalEvidence> selectAll();

    int updateByPrimaryKey(PhysicalEvidence record);
}