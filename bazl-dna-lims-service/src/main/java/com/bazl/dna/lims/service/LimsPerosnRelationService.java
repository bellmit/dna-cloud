package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.LimsPerosnRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface LimsPerosnRelationService {


    LimsPerosnRelation selectPersonInfo(String sampleFlag);
}