package com.bazl.dna.dpznsy.dao;

import java.util.List;

import com.bazl.dna.dpznsy.model.po.MemberInfo;

public interface MemberInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MemberInfo record);

    MemberInfo selectByPrimaryKey(String id);

    List<MemberInfo> selectAll();

    int updateByPrimaryKey(MemberInfo record);
}