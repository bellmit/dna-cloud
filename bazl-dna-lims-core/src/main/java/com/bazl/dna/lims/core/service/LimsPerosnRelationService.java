package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.LimsPerosnRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface LimsPerosnRelationService {


    LimsPerosnRelation selectPersonInfo(String sampleFlag);
}