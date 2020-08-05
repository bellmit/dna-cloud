package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LoaPermissionRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaPermissionRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoaPermissionRelation record);

    LoaPermissionRelation selectByPrimaryKey(String id);

    List<LoaPermissionRelation> selectAll();

    int updateByPrimaryKey(LoaPermissionRelation record);
}