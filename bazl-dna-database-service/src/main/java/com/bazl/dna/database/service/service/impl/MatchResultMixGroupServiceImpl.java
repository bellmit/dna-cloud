package com.bazl.dna.database.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.MatchResultMixGroupMapper;
import com.bazl.dna.database.service.model.po.MatchResultMixGroup;
import com.bazl.dna.database.service.service.MatchResultMixGroupService;

/**
 * <p>
 * dna混合比对结果分组信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultMixGroupServiceImpl extends ServiceImpl<MatchResultMixGroupMapper, MatchResultMixGroup> implements MatchResultMixGroupService {

	@Autowired
    private MatchResultMixGroupMapper matchResultMixGroupMapper;

	@Override
    public Integer getNextval() {
    		return matchResultMixGroupMapper.getNextval();
    }
}
