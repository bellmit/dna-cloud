package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.PoliceExperience;
import java.util.List;

public interface PoliceExperienceMapper {
    int insert(PoliceExperience record);

    List<PoliceExperience> selectAll();
}