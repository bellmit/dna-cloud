package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.ISysJobJPADao;
import com.bazl.dna.sys.entity.SysJob;
import com.bazl.dna.sys.service.ISysJobService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.google.common.base.Strings;

@Service
public class SysJobServiceImpl implements ISysJobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysJobServiceImpl.class);

	private static final String CACHE_NAME = "SysJob";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysJobJPADao jobJPADao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysJob> findJobList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysJob> criteria = QueryUtils.buildCriteria(data);
			return jobJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findJobList: ", e);
		}
		return null;
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysJob getJobInfoById(String id) {
		try {
			return jobJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getJobInfoById: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public SysJob saveJob(SysJob job) {
		try {
			if (Strings.isNullOrEmpty(job.getId())) {
				job.setStatus(PublicConstants.STATUS_YES);
			}
			job.setCreateTime(new Timestamp(System.currentTimeMillis()));
			job.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			return jobJPADao.save(job);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public int deleteById(String id) {
		try {
			return jobJPADao.updateById(id);
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

}
