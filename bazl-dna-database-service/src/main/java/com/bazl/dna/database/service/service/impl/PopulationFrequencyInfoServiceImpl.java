package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.PopulationFrequencyInfoMapper;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.qo.PopulationFrequencyQuery;
import com.bazl.dna.database.service.model.vo.PopulationFrequencyInfoVo;
import com.bazl.dna.database.service.service.PopulationFrequencyInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 种群基因频率信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class PopulationFrequencyInfoServiceImpl extends ServiceImpl<PopulationFrequencyInfoMapper, PopulationFrequencyInfo> implements PopulationFrequencyInfoService {
	
	private static final String CACHE_NAME = "PopulationFrequencyInfo";

	@Autowired
	private PopulationFrequencyInfoMapper mapper;
	
	@Override
	@Cacheable(value = CACHE_NAME + ":findList", keyGenerator="keyGenerator")
	public List<PopulationFrequencyInfo> findList(Integer id, String geneType, String populationName) {
		return mapper.findList(id, geneType, populationName);
	}

	@Override
	public List<PopulationFrequencyInfoVo> populationPaginationQuery(PopulationFrequencyQuery populationFrequencyQuery) {
		try {
			return mapper.populationPaginationQuery(populationFrequencyQuery);
		} catch (Exception ex) {
			log.error("invoke PopulationFrequencyInfoService.populationPaginationQuery error.", ex);
		}
		return Lists.newArrayList();
	}

	@Override
	public int populationPaginationCount(PopulationFrequencyQuery populationFrequencyQuery) {
		try{
			return mapper.populationPaginationCount(populationFrequencyQuery);
		}catch (Exception ex) {
			log.error("invoke PopulationFrequencyInfoService.populationPaginationCount error.", ex);
		}
		return 0;
	}

	@Override
	public List<PopulationFrequencyInfoVo> listByPopulationId(Integer populationId) {
		try {
			return mapper.listByPopulationId(populationId);
		} catch (Exception ex) {
			log.error("invoke PopulationFrequencyInfoService.listByPopulationId error.", ex);
		}
		return Lists.newArrayList();
	}

	@Override
	@CacheEvict(value = CACHE_NAME, allEntries = true)
	public void deleteAlleleFrequencyByPopulationId(Integer populationId) {
		try {
            mapper.deleteAlleleFrequencyByPopulationId(populationId);
		}catch (Exception ex){
			log.error("invoke PopulationFrequencyInfoService.deleteAlleleFrequencyByPopulationId error.",ex);
		}
	}

	@Override
	public List<PopulationFrequencyInfo> selectGeneTypeList(String geneType) {
		try {
			Map<String, Object> columnMap = Maps.newHashMap();
			columnMap.put("GENE_TYPE", geneType);
			return mapper.selectByMap(columnMap);
		} catch (Exception e) {
			log.error("查询种群频率下拉框失败！" + e.getMessage());
		}
		return Lists.newArrayList();
	}
}
