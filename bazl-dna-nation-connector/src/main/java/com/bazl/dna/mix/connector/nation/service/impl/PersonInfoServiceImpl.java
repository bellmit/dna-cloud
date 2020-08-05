package com.bazl.dna.mix.connector.nation.service.impl;

import com.bazl.dna.mix.connector.nation.model.po.PersonInfo;
import com.bazl.dna.mix.connector.nation.service.PersonInfoService;
import com.google.common.collect.Lists;
import com.bazl.dna.mix.connector.nation.dao.PersonInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoServiceImpl extends BaseService implements PersonInfoService {

	@Autowired
	private PersonInfoMapper personInfoMapper;

	@Override
	public List<PersonInfo> selectAuditPersonByCaseId(String caseId) {
		try {
			return personInfoMapper.selectAuditPersonByCaseId(caseId);
		} catch (Exception e) {
			logger.error("Error selectAuditPersonByCaseId: ", e);
		}
		return Lists.newArrayList();
	}
}
