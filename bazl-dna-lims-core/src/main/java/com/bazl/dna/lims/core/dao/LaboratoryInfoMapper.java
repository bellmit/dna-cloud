package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LaboratoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryInfoMapper {
    List<LaboratoryInfo> queryList();

    LaboratoryInfo queryByLab(LaboratoryInfo laboratoryInfo);

    void updateLabInfo(LaboratoryInfo laboratoryInfo);
}
