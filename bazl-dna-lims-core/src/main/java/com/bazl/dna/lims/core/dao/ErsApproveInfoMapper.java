package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.ErsApproveInfo;
import java.util.List;

public interface ErsApproveInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ErsApproveInfo record);

    ErsApproveInfo selectByPrimaryKey(String id);

    List<ErsApproveInfo> selectAll();

    int updateByPrimaryKey(ErsApproveInfo record);
}