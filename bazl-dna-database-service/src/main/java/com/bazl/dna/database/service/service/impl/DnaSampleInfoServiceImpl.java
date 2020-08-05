package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaSampleInfoMapper;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * DNA样本信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class DnaSampleInfoServiceImpl extends ServiceImpl<DnaSampleInfoMapper, DnaSampleInfo>
		implements DnaSampleInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DnaSampleInfoMapper dnaSampleInfoMapper;

	/**
	 * 根据案件id查询所有的样本信息
	 * 
	 * @param caseId
	 * @return
	 */
	@Override
	public List<DnaSampleInfoVo> selectByCaseId(Integer caseId) {
		try {
			return dnaSampleInfoMapper.selectVoListByCaseId(caseId);
		} catch (Exception ex) {
			logger.error("根据案件id查询所有的样本信息失败！", ex);
		}
		return Lists.newArrayList();
	}

	@Override
	public DnaSampleInfoVo selectById(Integer id) {
		try {
			return dnaSampleInfoMapper.selectByIdVoList(id);
		} catch (Exception e) {
			logger.error("根据id查询样本信息失败！" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<DnaSampleInfoVo> selectByPersonId(Integer personId) {
		try {
			return dnaSampleInfoMapper.selectVoListPersonId(personId);
		} catch (Exception e) {
			logger.error("根据人员id查询检材信息错误！" + e.getMessage());
		}
		return Lists.newArrayList();
	}

	@Override
//    @Cacheable(value = CACHE_NAME + ":selectSampleByLabServerNo", key = "#labServerNo")
	public List<DnaSampleInfo> selectSampleByLabServerNo(String labServerNo) {
		try {
			return dnaSampleInfoMapper.selectSampleByLabServerNo(labServerNo);
		} catch (Exception e) {
			logger.error("根据实验室编号查询！" + e.getMessage());
		}
		return Lists.newArrayList();
	}

	@Override
//    @Cacheable(value = CACHE_NAME + ":selectAllSampleCount", keyGenerator="keyGenerator")
	public int selectAllSampleCount() {
		try {
			return dnaSampleInfoMapper.selectAllSampleCount();
		} catch (Exception ex) {
			log.error("查询全部样本数异常!" + ex.getMessage());
		}
		return 0;
	}
}
