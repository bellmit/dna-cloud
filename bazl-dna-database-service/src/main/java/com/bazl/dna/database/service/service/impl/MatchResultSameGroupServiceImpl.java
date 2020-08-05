package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.database.service.mapper.MatchResultRelativeMapper;
import com.bazl.dna.database.service.mapper.MatchResultSameGroupMapper;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.model.po.MatchResultSameGroup;
import com.bazl.dna.database.service.model.qo.MatchStrDatailQuery;
import com.bazl.dna.database.service.model.qo.MatchStrDatailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchStrResultQuery;
import com.bazl.dna.database.service.service.MatchResultSameGroupService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 同型比对结果分组信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultSameGroupServiceImpl extends ServiceImpl<MatchResultSameGroupMapper, MatchResultSameGroup>
		implements MatchResultSameGroupService {
	
	@Autowired
	MatchResultSameGroupMapper matchResultSameGroupMapper;

	@Autowired
	MatchResultRelativeMapper matchResultRelativeMapper;

	@Override
	public List<CompareResult> queryStrCompareResult(PageInfo pageInfo, CompareResult compareResult) {

		if (pageInfo != null) {
			compareResult.setOffset(pageInfo.getCurPage());
			compareResult.setSize(pageInfo.getEvePageRecordCnt());
			return matchResultSameGroupMapper.queryStrCompareResult(compareResult);
		}
		return Lists.newArrayList();

	}

	public List<CompareResult> queryRelativeCompareResult(PageInfo pageInfo, CompareResult compareResult) {
		if (pageInfo != null) {
			compareResult.setOffset(pageInfo.getCurPage());
			compareResult.setSize(pageInfo.getEvePageRecordCnt());
			return matchResultRelativeMapper.queryRelativeCompareResult(compareResult);
		}
		return Lists.newArrayList();
	}


	public CompareResult queryRelativeCompareinfo(String id) {
		return matchResultRelativeMapper.queryRelativeCompareinfo(id);
	}

	@Override
	public CompareResult queryStrCompareinfo(String id) {
		return matchResultSameGroupMapper.queryStrCompareinfo(id);
	}

	@Override
	public int resultCount(MatchStrResultQuery query) {
		return matchResultSameGroupMapper.resultCount(query);
	}

	@Override
	public List<MatchStrResultQuery> resultInfo(MatchStrResultQuery query) {
		try {
			return matchResultSameGroupMapper.resultInfo(query);
		} catch (Exception e) {
			log.error("resultInfo error:", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public int detailCount(MatchStrDatailQuery query) {
		try {
			return matchResultSameGroupMapper.detailCount(query);
		} catch (Exception e) {
			log.error("detailCount error:", e);
		}
		return 0;
	}

	@Override
	public List<MatchStrDatailResultQuery> detailInfo(MatchStrDatailQuery query) {
		try {
			return matchResultSameGroupMapper.detailInfo(query);
		} catch (Exception e) {
			log.error("detailInfo error:", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public Integer getNextval() {
		return matchResultSameGroupMapper.getNextval();
	}

	@Override
	public List<MatchResultSameGroup> selectListByGroupType(String groupType) {
		try {
			return matchResultSameGroupMapper.selectListByGroupType(groupType);
		}catch (Exception e){
			log.error("selectListByGroupType error:", e);
		}
		return Lists.newArrayList();
	}
}
