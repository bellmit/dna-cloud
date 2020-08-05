package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaStrGeneInfoMapper;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
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
 * str基因信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Service
class DnaStrGeneInfoServiceImpl extends ServiceImpl<DnaStrGeneInfoMapper, DnaStrGeneInfo>
		implements DnaStrGeneInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String CACHE_NAME = "DnaStrGeneInfo";

	@Autowired
	private DnaStrGeneInfoMapper mapper;

	/**
	 * 队列使用 自动比对
	 */
	@Override
	public List<Map<String, Object>> findCompareList(int geneId, List<String> serverNo, List<String> dataType,
			Map<String, Object> params) {
		try {
			return mapper.findCompareList(geneId, serverNo, dataType, params);
		} catch (Exception e) {
			logger.error("根据比对目标条件获取str样本信息失败！", e);
		}
		return Lists.newArrayList();
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":findRelativeCompareList", key = "#offset+':'+#rows")
	public List<Map<String, Object>> findRelativeCompareList(long offset, long rows) {
		try {
			return mapper.findRelativeCompareList(Lists.newArrayList(), Lists.newArrayList(), offset, rows);
		} catch (Exception e) {
			logger.error("根据比对目标条件获取亲缘样本信息失败！", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public DnaSampleInfoVo selectBySampleId(Integer sampleId) {
		try {
			return mapper.selectBySampleId(sampleId);
		} catch (Exception e) {
			logger.error("根据样本id查询STR基因信息错误！" + e.getMessage());
		}
		return null;
	}

	@Override
	public DnaSampleInfoVo selectSampleByDataSource(Integer sampleId, Integer dataSource) {
		try {
			return mapper.selectSampleByDataSource(sampleId, dataSource);
		} catch (Exception ex) {
			log.error("根据样本ID,样本类型查询STR基因信息错误!" + ex.getMessage());
		}
		return null;
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
//	@Cacheable(value = CACHE_NAME + ":selectBySampleLabNo", key = "#sampleLabNo")
	public DnaSampleInfoVo selectBySampleLabNo(String sampleLabNo) {
		try {
			return mapper.selectBySampleLabNo(sampleLabNo);
		} catch (Exception e) {
			logger.error("根据检材编号查询STR基因信息错误！" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<DnaStrGeneInfo> selectListByLabServerNo(String labServerNo) {
		try {
			return mapper.selectListByLabServerNo(labServerNo);
		} catch (Exception ex) {
			log.error("根据实验室编号查询STR基因信息错误" + ex.getMessage());
		}
		return Lists.newArrayList();
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":selectAllGeneCount", keyGenerator = "keyGenerator")
	public int selectAllGeneCount() {
		try {
			return mapper.selectAllGeneCount();
		} catch (Exception ex) {
			log.error("查询全部STR基因分型数据 error" + ex.getMessage());
		}
		return 0;
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":findList", key = "#offset+':'+#rows")
	public List<Map<String, Object>> findList(long offset, long rows) {
		return mapper.findList(offset, rows);
	}
}
