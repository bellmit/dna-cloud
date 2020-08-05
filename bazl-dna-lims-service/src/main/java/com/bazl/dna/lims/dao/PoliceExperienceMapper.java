package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.PoliceExperience;
import java.util.List;

public interface PoliceExperienceMapper {
    int insert(PoliceExperience record);

    List<PoliceExperience> selectAll();
}