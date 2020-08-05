package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.database.service.mapper.MatchResultRelativeMapper;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeResultQuery;
import com.bazl.dna.database.service.service.MatchResultRelativeService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.google.common.collect.Lists;

/**
 * <p>
 * 亲缘比对结果表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultRelativeServiceImpl extends ServiceImpl<MatchResultRelativeMapper, MatchResultRelative>
		implements MatchResultRelativeService {

	@Autowired
	private MatchResultRelativeMapper mapper;

	@Override
	public int relativeResultCount(MatchRelativeResultQuery query) {
		try {
			return mapper.relativeResultCount(query);
		} catch (Exception e) {
			log.error("relativeResultCount error:", e);
		}
		return 0;
	}

	@Override
	public List<MatchRelativeResultQuery> relativeResultInfo(MatchRelativeResultQuery query) {
		try {
			return mapper.relativeResultInfo(query);
		} catch (Exception e) {
			log.error("relativeResultInfo error:", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public int relativeDatailCount(MatchRelativeDetailQuery query) {
		try {
			return mapper.relativeDatailCount(query);
		} catch (Exception e) {
			log.error("relativeDatailCount error:", e);
		}
		return 0;
	}

	@Override
	public List<MatchRelativeDetailResultQuery> relativeDatailInfo(MatchRelativeDetailQuery query) {
		try {
			return mapper.relativeDatailInfo(query);
		} catch (Exception e) {
			log.error("relativeDatailInfo error:", e);
		}
		return Lists.newArrayList();
	}
	
	@Override
	public List<CompareResult> queryRelativeCompareResult(PageInfo pageInfo, CompareResult compareResult) {
		if (pageInfo != null) {
			compareResult.setOffset(pageInfo.getCurPage());
			compareResult.setSize(pageInfo.getEvePageRecordCnt());
			return mapper.queryRelativeCompareResult(compareResult);
		}
		return Lists.newArrayList();
	}

	@Override
	public CompareResult queryRelativeCompareinfo(String id) {
		return mapper.queryRelativeCompareinfo(id);
	}

	@Override
	@Transactional
	public void updateMatchResultRelative(MatchResultRelative entity) {
		try {
			mapper.updateById(entity);
		} catch (Exception e) {
			log.error("update error:", e);
			throw new DnaRuntimeException();
		}
	}
}
