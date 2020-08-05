package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LimsEvidenceInfo;
import java.util.List;

public interface LimsEvidenceInfoMapper {
    int deleteByPrimaryKey(String evidenceId);

    int insert(LimsEvidenceInfo record);

    LimsEvidenceInfo selectByPrimaryKey(String evidenceId);

    List<LimsEvidenceInfo> selectAll();

    int updateByPrimaryKey(LimsEvidenceInfo record);
}