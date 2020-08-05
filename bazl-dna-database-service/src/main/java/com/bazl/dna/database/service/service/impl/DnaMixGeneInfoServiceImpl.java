package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaMixGeneInfoMapper;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 混合基因信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Service
public class DnaMixGeneInfoServiceImpl extends ServiceImpl<DnaMixGeneInfoMapper, DnaMixGeneInfo>
		implements DnaMixGeneInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String CACHE_NAME = "DnaMixGeneInfo";

	@Autowired
	private DnaMixGeneInfoMapper mapper;

	@Override
	public List<Map<String, Object>> findCompareList(int geneId, List<String> serverNo, List<String> dataType,
			Map<String, Object> params) {
		try {
			return mapper.findCompareList(geneId, serverNo, dataType, params);
		} catch (Exception e) {
			logger.error("根据比对目标条件获取Mix样本信息失败！", e);
		}
		return Lists.newArrayList();
	}

	@Override
//	@Cacheable(value = CACHE_NAME + ":selectBySampleLabNo", key = "#sampleLabNo")
	public DnaSampleInfoVo selectBySampleLabNo(String sampleLabNo) {
		try {
			return mapper.selectBySampleLabNo(sampleLabNo);
		} catch (Exception e) {
			logger.error("根据比对目标条件获取Ystr样本信息失败！", e);
		}
		return null;
	}

	@Override
//	@Cacheable(value = CACHE_NAME + ":selectBySampleId", key = "#sampleId")
	public DnaSampleInfoVo selectBySampleId(Integer sampleId) {
		try {
			return mapper.selectBySampleId(sampleId);
		} catch (Exception e) {
			logger.error("根据样本id查询基因信息报错！" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findQuickCompareList(Map<String, Object> params) {
		try {
			return mapper.findQuickCompareList(params);
		} catch (Exception e) {
			logger.error("快速比对同一样本基因失败！", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public void insertVirtualColumn(String key) {
		mapper.insertVirtualColumn(key);
	}

	@Override
	public int checkVirtualColumn(String columnName) {
		return mapper.checkVirtualColumn(columnName);
	}

	@Override
	public void insertVirtualIndex(String key) {
		mapper.insertVirtualIndex(key);
	}

	@Override
	public int checkVirtualIndex(String indexName) {
		return mapper.checkVirtualIndex(indexName);
	}

	@Override
	public List<String> findVirtualIndexList() {
		return mapper.findVirtualIndexList();
	}

	@Override
	public void deleteVirtualIndex(String key) {
		mapper.deleteVirtualIndex(key);
	}

	@Override
	public List<DnaMixGeneInfo> selectListByLabServerNo(String labServerNo) {
		try {
			return mapper.selectListByLabServerNo(labServerNo);
		} catch (Exception ex) {
			log.error("DnaMixGeneInfoService.selectListByLabServerNo error!" + ex.getMessage());
		}
		return Lists.newArrayList();
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":selectAllGeneCount", keyGenerator = "keyGenerator")
	public int selectAllGeneCount() {
		try {
			return mapper.selectAllGeneCount();
		} catch (Exception ex) {
			log.error("DnaMixGeneInfoService.selectAllGeneCount error!" + ex.getMessage());
		}
		return 0;
	}
}
