package com.bazl.dna.mix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.ContributorInfoMapper;
import com.bazl.dna.mix.model.po.ContributorInfo;
import com.bazl.dna.mix.service.ContributorInfoService;

@Service
public class ContributorInfoServiceImpl extends MixBaseService implements ContributorInfoService {

    @Autowired
    private ContributorInfoMapper contributorInfoMapper;

    @Override
    @Transactional
    public int deleteByPrimaryKey(String id) {
    	try {
    		return contributorInfoMapper.deleteByPrimaryKey(id);
    	} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insert(ContributorInfo record) {
    	try {
    		return contributorInfoMapper.insert(record);
    	} catch (Exception e) {
    		logger.error("Error insert: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insertSelective(ContributorInfo record) {
    	try {
    		return contributorInfoMapper.insertSelective(record);
    	} catch (Exception e) {
    		logger.error("Error insertSelective: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public ContributorInfo selectByPrimaryKey(String id) {
        return contributorInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(ContributorInfo record) {
    	try {
    		return contributorInfoMapper.updateByPrimaryKeySelective(record);
    	} catch (Exception e) {
    		logger.error("Error updateByPrimaryKeySelective: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(ContributorInfo record) {
    	try {
    		return contributorInfoMapper.updateByPrimaryKey(record);
    	} catch (Exception e) {
    		logger.error("Error updateByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

	@Override
	public List<ContributorInfo> findListByMixSampleGeneId(String mixedSampleGeneId) {
		return contributorInfoMapper.findListByMixSampleGeneId(mixedSampleGeneId);
	}
    
}
