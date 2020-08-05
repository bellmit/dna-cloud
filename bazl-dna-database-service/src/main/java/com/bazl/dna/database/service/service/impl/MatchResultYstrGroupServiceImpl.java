package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.MatchResultYstrGroupMapper;
import com.bazl.dna.database.service.model.po.MatchResultYstrGroup;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrResultQuery;
import com.bazl.dna.database.service.service.MatchResultYstrGroupService;
import com.google.common.collect.Lists;

/**
 * <p>
 * Y-STR比中结果信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultYstrGroupServiceImpl extends ServiceImpl<MatchResultYstrGroupMapper, MatchResultYstrGroup> implements MatchResultYstrGroupService {
	
	@Autowired
    private MatchResultYstrGroupMapper mapper;

	@Override
    public Integer getNextval() {
    		return mapper.getNextval();
    }

	@Override
	public int resultCount(MatchYstrResultQuery query) {
		return mapper.resultCount(query);
	}

	@Override
	public List<MatchYstrResultQuery> resultInfo(MatchYstrResultQuery query) {
		try {
			return mapper.resultInfo(query);
		} catch (Exception e) {
			log.error("resultInfo error:", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public int detailCount(MatchYstrDetailQuery query) {
		return mapper.detailCount(query);
	}

	@Override
	public List<MatchYstrDetailResultQuery> detailInfo(MatchYstrDetailQuery query) {
		try {
			return mapper.detailInfo(query);
		} catch (Exception e) {
			log.error("detailInfo error:", e);
		}
		return Lists.newArrayList();
	}
}
