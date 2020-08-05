package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmProficiencyTesting;
import java.util.List;

public interface AmProficiencyTestingMapper {
    int insert(AmProficiencyTesting record);

    List<AmProficiencyTesting> selectAll();
}