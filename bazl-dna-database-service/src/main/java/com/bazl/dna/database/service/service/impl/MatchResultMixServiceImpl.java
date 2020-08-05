package com.bazl.dna.database.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.MatchResultMixMapper;
import com.bazl.dna.database.service.model.po.MatchResultMix;
import com.bazl.dna.database.service.service.MatchResultMixService;

/**
 * <p>
 * dna混合比对结果信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultMixServiceImpl extends ServiceImpl<MatchResultMixMapper, MatchResultMix> implements MatchResultMixService {

	@Autowired
	private MatchResultMixMapper matchResultMixMapper;
	
	@Override
	public Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId) {
		return matchResultMixMapper.getMatchResultGroupNo(sampleAId, sampleBId);
	}
}
