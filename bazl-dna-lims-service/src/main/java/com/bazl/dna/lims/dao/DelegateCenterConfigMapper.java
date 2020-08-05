package com.bazl.dna.lims.dao;

import java.util.List;

import com.bazl.dna.lims.model.po.DelegateCenterConfig;

public interface DelegateCenterConfigMapper {
    int insert(DelegateCenterConfig record);

    List<DelegateCenterConfig> selectAll();

    //查询鉴定机构
    List<DelegateCenterConfig> selectQualification(String delegateOrgCodePrefix);
}