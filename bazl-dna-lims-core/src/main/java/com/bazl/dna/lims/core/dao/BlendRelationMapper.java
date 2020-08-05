package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AlleleFrequency;
import com.bazl.dna.lims.core.model.po.BlendRelationResult;

import java.util.List;

public interface BlendRelationMapper {

    int insert(BlendRelationResult record);

    BlendRelationResult selectById(String id);

    int update(BlendRelationResult blendRelationResult);
}