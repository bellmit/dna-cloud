package com.bazl.dna.dpznsy.dao;

import com.bazl.dna.dpznsy.model.po.CaseInfo;

import java.util.List;

public interface CaseInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseInfo record);

    CaseInfo selectByPrimaryKey(String id);

    List<CaseInfo> selectAll();

    int updateByPrimaryKey(CaseInfo record);
}