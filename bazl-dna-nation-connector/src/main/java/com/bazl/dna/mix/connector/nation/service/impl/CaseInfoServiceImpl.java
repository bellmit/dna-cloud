package com.bazl.dna.mix.connector.nation.service.impl;

import com.bazl.dna.mix.connector.nation.model.po.CaseInfo;
import com.bazl.dna.mix.connector.nation.service.CaseInfoService;
import com.bazl.dna.mix.connector.nation.dao.CaseInfoMapper;
import com.bazl.dna.mix.connector.nation.model.vo.CaseInfoVo;
import com.bazl.dna.mix.connector.nation.utils.ListUtils;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseInfoServiceImpl extends BaseService implements CaseInfoService {

	@Autowired
	private CaseInfoMapper caseInfoMapper;

	@Override
	public List<CaseInfo> selectCaseInfoListData(CaseInfoVo query, Integer page) {
		int pageNo;
		int pageSize;
		try {
			pageNo = page;
			pageSize = 1000;
			query.setOffset((pageNo - 1) * pageSize);
			query.setRows(query.getOffset() + pageSize);
			return caseInfoMapper.selectCaseInfoListData(query);
		} catch (Exception e) {
			logger.error("Error selectCaseInfoListData: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public CaseInfo selectByCaseNo(String caseNo) {
		try {
			List<CaseInfo> caseInfoList = caseInfoMapper.selectByCaseNo(caseNo);
			CaseInfo caseInfo = null;
			if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
				caseInfo = caseInfoList.get(0);
			}
			return caseInfo;
		} catch (Exception e) {
			logger.error("Error selectByCaseNo: ", e);
		}
		return null;
	}

	@Override
	public CaseInfo selectByCaseId(String caseId) {
		try {
			return caseInfoMapper.selectByCaseId(caseId);
		} catch (Exception e) {
			logger.error("Error selectByCaseId: ", e);
		}
		return null;
	}

	@Override
	public CaseInfo selectBySampleId(String sampleId) {
		try {
			return caseInfoMapper.selectBySampleId(sampleId);
		} catch (Exception e) {
			logger.error("Error selectBySampleId: ", e);
		}
		return null;
	}

}
