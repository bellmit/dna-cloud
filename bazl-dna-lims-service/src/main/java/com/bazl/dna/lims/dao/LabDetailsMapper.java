package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.LabDetails;

import java.util.List;

public interface LabDetailsMapper {

    public List<LabDetails> querylabDetailsList(String labId);
}