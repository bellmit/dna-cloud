package com.bazl.dna.mix.connector.nation.service.impl;

import com.bazl.dna.mix.connector.nation.dao.SampleInfoMapper;
import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;
import com.bazl.dna.mix.connector.nation.service.SampleInfoService;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleInfoServiceImpl extends BaseService implements SampleInfoService {

	@Autowired
	private SampleInfoMapper sampleInfoMapper;

	@Override
	public List<SampleInfo> selectAuditSampleByCaseId(String caseId) {
		try {
			return sampleInfoMapper.selectAuditSampleByCaseId(caseId);
		} catch (Exception e) {
			logger.error("Error selectAuditSampleByCaseId: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public SampleInfo selectByPrimaryKey(String id) {
		return sampleInfoMapper.selectByPrimaryKey(id);
	}
}
