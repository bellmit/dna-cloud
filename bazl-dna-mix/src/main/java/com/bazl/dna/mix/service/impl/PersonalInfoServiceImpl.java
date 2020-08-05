package com.bazl.dna.mix.service.impl;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.PersonalInfoMapper;
import com.bazl.dna.mix.model.po.PersonalInfo;
import com.bazl.dna.mix.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2019/10/28.
 */
@Service
public class PersonalInfoServiceImpl extends MixBaseService implements PersonalInfoService {

    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    @Override
    @Transactional
    public int deleteByPrimaryKey(String personalId) {
    	try {
    		return personalInfoMapper.deleteByPrimaryKey(personalId);
    	} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
            throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insert(PersonalInfo record) {
    	try {
    		return personalInfoMapper.insert(record);
    	} catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
    }

    @Override
    public PersonalInfo selectByPrimaryKey(String personalId) {
        return personalInfoMapper.selectByPrimaryKey(personalId);
    }

    @Override
    public List<PersonalInfo> selectAll() {
        return personalInfoMapper.selectAll();
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(PersonalInfo record) {
    	try {
    		return personalInfoMapper.updateByPrimaryKey(record);
    	} catch (Exception e) {
    		logger.error("Error updateByPrimaryKey: ", e);
            throw new DnaRuntimeException();
    	}
    }
}
