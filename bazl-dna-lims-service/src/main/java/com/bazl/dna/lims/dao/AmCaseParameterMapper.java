package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmCaseParameter;
import java.util.List;

public interface AmCaseParameterMapper {
    int insert(AmCaseParameter record);

    List<AmCaseParameter> selectAll();
}