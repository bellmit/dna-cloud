package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.CriminalPersonInfoMapper;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.qo.CriminalPersonInfoQuery;
import com.bazl.dna.database.service.model.qo.CriminalReportQuery;
import com.bazl.dna.database.service.model.vo.CriminalPersonInfoVo;
import com.bazl.dna.database.service.service.CriminalPersonInfoService;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 建库人员信息表（违法犯罪/前科人员） 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class CriminalPersonInfoServiceImpl extends ServiceImpl<CriminalPersonInfoMapper, CriminalPersonInfo>
		implements CriminalPersonInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CriminalPersonInfoMapper criminalPersonInfoMapper;

	/**
	 * 根据人员id查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public CriminalPersonInfoVo selectVoById(Integer id) {
		try {
			return criminalPersonInfoMapper.selectById(id);
		} catch (Exception ex) {
			logger.error("invoke CriminalPersonInfoService.selectVoById error.", ex);
		}
		return null;
	}

	/**
	 * 通用查询 - 建库人员信息分页结果
	 * 
	 * @param criminalPersonInfoQuery
	 * @return
	 */
	@Override
	public List<CriminalPersonInfoVo> criminalPaginationQuery(CriminalPersonInfoQuery criminalPersonInfoQuery) {
		try {
			return criminalPersonInfoMapper.paginationQuery(criminalPersonInfoQuery);
		} catch (Exception ex) {
			logger.error("invoke CriminalPersonInfoService.criminalPaginationQuery error.", ex);
		}
		return Lists.newArrayList();
	}

	/**
	 * 通用查询 - 建库人员信息总数
	 * 
	 * @param criminalPersonInfoQuery
	 * @return
	 */
	@Override
	public int criminalPaginationCount(CriminalPersonInfoQuery criminalPersonInfoQuery) {
		try {
			return criminalPersonInfoMapper.paginationCount(criminalPersonInfoQuery);
		} catch (Exception ex) {
			logger.error("invoke CriminalPersonInfoService.criminalPaginationCount error.", ex);
		}
		return 0;
	}

	@Override
	public List<CriminalReportQuery> selectCriminalReportList(CriminalReportQuery query) {
		try {
			return criminalPersonInfoMapper.selectCriminalReportList(query);
		} catch (Exception ex) {
			logger.error("invoke CriminalPersonInfoService.selectCriminalReportList error.", ex);
		}
		return Lists.newArrayList();
	}
}
