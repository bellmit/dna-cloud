package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.BlendRelationResult;

public interface BlendRelationMapper {

    int insert(BlendRelationResult record);

    BlendRelationResult selectById(String id);

    int update(BlendRelationResult blendRelationResult);
}