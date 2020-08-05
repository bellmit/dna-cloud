package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.PersonInfo;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface PersonInfoService {

    /**
     * 根据人员id查询人员信息
     * @return
     */
    public PersonInfo selectByPersonId(String personId);

    /**
     * 修改人员信息
     * @param personInfo
     */
    public void updatePersonInfo(PersonInfo personInfo);

    /**
     * 添加人员信息
     * @param personInfo
     */
    public void insert(PersonInfo personInfo);

    /**
     * 根据人员id查询人员类型信息
     * @param id
     * @return
     */
    PersonInfo selectByPersonType(String id);
}
