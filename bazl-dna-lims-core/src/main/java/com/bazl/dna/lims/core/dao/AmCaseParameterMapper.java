package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmCaseParameter;
import java.util.List;

public interface AmCaseParameterMapper {
    int insert(AmCaseParameter record);

    List<AmCaseParameter> selectAll();
}