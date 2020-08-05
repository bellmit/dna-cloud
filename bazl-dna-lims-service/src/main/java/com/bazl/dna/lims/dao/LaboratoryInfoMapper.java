package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.LaboratoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryInfoMapper {
    List<LaboratoryInfo> queryList();

    LaboratoryInfo queryByLab(LaboratoryInfo laboratoryInfo);

    void updateLabInfo(LaboratoryInfo laboratoryInfo);
    
    List<LaboratoryInfo> findListByMonitorType(Integer monitorType);

	LaboratoryInfo queryById(String id);
}
