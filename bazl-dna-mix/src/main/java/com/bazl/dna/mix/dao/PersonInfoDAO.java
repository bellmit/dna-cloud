package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.PersonInfo;

public interface PersonInfoDAO {
    int insert(PersonInfo record);

    int insertSelective(PersonInfo record);

    /**
     * 根据人员id查询人员信息
     * @param personId
     * @return
     */
    PersonInfo selectByPersonId(String personId);

    void updatePersonInfo(PersonInfo personInfo);

    /**
     * 根据人员id查询人员类型信息
     * @param id
     * @return
     */
    PersonInfo selectByPersonType(String id);
}