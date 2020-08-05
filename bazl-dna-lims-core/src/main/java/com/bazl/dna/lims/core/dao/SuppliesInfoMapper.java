package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.SuppliesInfo;
import java.util.List;

public interface SuppliesInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SuppliesInfo record);

    SuppliesInfo selectByPrimaryKey(String id);

    List<SuppliesInfo> selectAll();

    int updateByPrimaryKey(SuppliesInfo record);

    //根据分局查看耗材信息
    List<SuppliesInfo> selectOrgId(SuppliesInfo suppliesInfo);
}