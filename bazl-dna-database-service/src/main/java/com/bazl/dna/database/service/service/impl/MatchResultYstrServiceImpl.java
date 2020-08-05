package com.bazl.dna.database.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.MatchResultYstrMapper;
import com.bazl.dna.database.service.model.po.MatchResultYstr;
import com.bazl.dna.database.service.service.MatchResultYstrService;
import com.bazl.dna.exception.DnaRuntimeException;

/**
 * <p>
 * ystr样本比中结果信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultYstrServiceImpl extends ServiceImpl<MatchResultYstrMapper, MatchResultYstr> implements MatchResultYstrService {
	
	@Autowired
    private MatchResultYstrMapper matchResultYstrMapper;
	
	@Override
	public Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId) {
		return matchResultYstrMapper.getMatchResultGroupNo(sampleAId, sampleBId);
	}

	@Override
	@Transactional
	public void updateMatchResultYstr(MatchResultYstr entity) {
		try {
			matchResultYstrMapper.updateById(entity);
		} catch (Exception e) {
			log.error("update error:", e);
			throw new DnaRuntimeException();
		}
	}
}
