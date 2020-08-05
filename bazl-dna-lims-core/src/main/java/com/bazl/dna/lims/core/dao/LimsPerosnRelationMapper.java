package com.bazl.dna.lims.core.dao;


import com.bazl.dna.lims.core.model.po.LimsPerosnRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface LimsPerosnRelationMapper {

    void insertPersonRelation(LimsPerosnRelation limsPerosnRelation);

    void deleteBySourcePersonId(LimsPerosnRelation limsPerosnRelation);

    LimsPerosnRelation selectBySourcePersonId(String sourcePersonId);

    void updatePerosnRelation(LimsPerosnRelation limsPerosnRelation);
}