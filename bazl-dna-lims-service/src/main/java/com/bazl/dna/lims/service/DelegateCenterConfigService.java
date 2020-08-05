package com.bazl.dna.lims.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.po.DelegateCenterConfig;

/**
 * Created by LX on 2019/9/12.
 */
@Repository
public interface DelegateCenterConfigService {

    int insert(DelegateCenterConfig record);

    List<DelegateCenterConfig> selectAll();

    //查询鉴定机构
    List<DelegateCenterConfig> selectQualification(String delegateOrgCodePrefix);
}
