package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LabDetails;

import java.util.List;

public interface LabDetailsMapper {

    public List<LabDetails> querylabDetailsList(String labId);
}