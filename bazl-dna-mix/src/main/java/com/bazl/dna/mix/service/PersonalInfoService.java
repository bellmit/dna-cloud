package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.PersonalInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2019/10/28.
 */
@Repository
public interface PersonalInfoService {

    int deleteByPrimaryKey(String personalId);

    int insert(PersonalInfo record);

    PersonalInfo selectByPrimaryKey(String personalId);

    List<PersonalInfo> selectAll();

    int updateByPrimaryKey(PersonalInfo record);
}
