package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmProficiencyTesting;
import java.util.List;

public interface AmProficiencyTestingMapper {
    int insert(AmProficiencyTesting record);

    List<AmProficiencyTesting> selectAll();
}