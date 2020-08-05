package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.PhysicalEvidence;
import java.util.List;

public interface PhysicalEvidenceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PhysicalEvidence record);

    PhysicalEvidence selectByPrimaryKey(String id);

    List<PhysicalEvidence> selectAll();

    int updateByPrimaryKey(PhysicalEvidence record);
}