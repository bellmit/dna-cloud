package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.PersonalInfo;

import java.util.List;

public interface PersonalInfoMapper {
    int deleteByPrimaryKey(String personalId);

    int insert(PersonalInfo record);

    PersonalInfo selectByPrimaryKey(String personalId);

    List<PersonalInfo> selectAll();

    int updateByPrimaryKey(PersonalInfo record);
}