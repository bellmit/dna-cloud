package com.bazl.dna.mix.service.impl;

import com.bazl.dna.mix.service.PersonInfoService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.PersonInfoDAO;
import com.bazl.dna.mix.model.po.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class PersonInfoServiceImpl extends MixBaseService implements PersonInfoService {
    @Autowired
    PersonInfoDAO personInfoDAO;

    /**
     * 根据人员id查询人员信息
     * @return
     */
    @Override
    public PersonInfo selectByPersonId(String personId) {
        PersonInfo personInfo = new PersonInfo();
        try {
            personInfo = personInfoDAO.selectByPersonId(personId);
        } catch (Exception ex) {
            logger.error("根据人员id查询人员信息失败！！！", ex.getMessage());
        }
        return personInfo;
    }

    /**
     * 修改人员信息
     * @param personInfo
     */
    @Override
    @Transactional
    public void updatePersonInfo(PersonInfo personInfo) {
        try {
            personInfoDAO.updatePersonInfo(personInfo);
        } catch (Exception e) {
    		logger.error("Error updatePersonInfo: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 添加人员信息
     * @param personInfo
     */
    @Override
    @Transactional
    public void insert(PersonInfo personInfo) {
        try {
            personInfoDAO.insert(personInfo);
        } catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
    }

    @Override
    public PersonInfo selectByPersonType(String id) {
        return personInfoDAO.selectByPersonType(id);
    }
}
