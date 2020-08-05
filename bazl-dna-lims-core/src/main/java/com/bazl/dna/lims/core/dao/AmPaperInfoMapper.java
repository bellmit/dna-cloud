package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmPaperInfo;
import java.util.List;

public interface AmPaperInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmPaperInfo record);

    AmPaperInfo selectByPrimaryKey(String id);

    List<AmPaperInfo> selectAll();

    int updateByPrimaryKey(AmPaperInfo record);
}