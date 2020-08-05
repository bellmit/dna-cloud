package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaYstrGeneInfoMapper;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;
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
 * ystr基因信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Service
public class DnaYstrGeneInfoServiceImpl extends ServiceImpl<DnaYstrGeneInfoMapper, DnaYstrGeneInfo>
		implements DnaYstrGeneInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String CACHE_NAME = "DnaYstrGeneInfo";

	@Autowired
	private DnaYstrGeneInfoMapper mapper;

	/**
	 * 队列使用 自动比对
	 */
	@Override
	public List<Map<String, Object>> findCompareList(int geneId, List<String> serverNo, List<String> dataType,
			Map<String, Object> params) {
		try {
			return mapper.findCompareList(geneId, serverNo, dataType, params);
		} catch (Exception e) {
			logger.error("根据比对目标条件获取Ystr样本信息失败！", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public DnaSampleInfoVo selectBySampleId(Integer sampleId) {
		DnaSampleInfoVo dnaSampleInfoVo = null;
		try {
			dnaSampleInfoVo = mapper.selectBySampleId(sampleId);
		} catch (Exception e) {
			logger.error("根据样本id查询YSTR基因信息错误！" + e.getMessage());
		}
		return dnaSampleInfoVo;
	}

	/**
	 * 队列使用 快速比对
	 */
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
	@Cacheable(value = CACHE_NAME + ":selectBySampleLabNo", key = "#sampleLabNo")
	public DnaSampleInfoVo selectBySampleLabNo(String sampleLabNo) {
		try {
			return mapper.selectBySampleLabNo(sampleLabNo);
		} catch (Exception ex) {
			log.error("invoke DnaYstrGeneInfoService.selectBySampleLabNo", ex);
		}
		return null;
	}

	@Override
	public DnaSampleInfoVo selectSampleByDataSource(Integer sampleId, Integer dataSource) {
		try {
			return mapper.selectSampleByDataSource(sampleId, dataSource);
		} catch (Exception ex) {
			log.error("根据样本ID,样本类型查询YSTR基因信息错误!" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<DnaYstrGeneInfo> selectListByLabServerNo(String labServerNo) {
		try {
			return mapper.selectListByLabServerNo(labServerNo);
		} catch (Exception ex) {
			log.error("invoke DnaYstrGeneInfoService.selectListByLabServerNo", ex);
		}
		return Lists.newArrayList();
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":selectAllGeneCount", keyGenerator = "keyGenerator")
	public int selectAllGeneCount() {
		try {
			return mapper.selectAllGeneCount();
		} catch (Exception ex) {
			log.error("invoke DnaYstrGeneInfoService.selectAllGeneCount", ex);
		}
		return 0;
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":findList", key = "#offset+':'+#rows")
	public List<Map<String, Object>> findList(long offset, long rows) {
		return mapper.findList(offset, rows);
	}
}
