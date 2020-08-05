package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.List;

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
import com.bazl.dna.sys.dao.ISysMenuOperJPADao;
import com.bazl.dna.sys.dao.ISysMenuOperationJPADao;
import com.bazl.dna.sys.entity.SysMenuOperation;
import com.bazl.dna.sys.service.ISysMenuOperationService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Service
public class SysMenuOperationServiceImpl implements ISysMenuOperationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuOperationServiceImpl.class);

	private static final String CACHE_NAME = "SysMenuOperation";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysMenuOperationJPADao menuOperationJPADao;

	@Autowired
	private ISysMenuOperJPADao menuOperJPADao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysMenuOperation> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysMenuOperation> criteria = QueryUtils.buildCriteria(data);
			return menuOperationJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}
	
	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST)
	public List<SysMenuOperation> findAll() {
		try {
			return menuOperationJPADao.findAll();
		} catch (Exception e) {
			LOGGER.error("Error findAll: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysMenuOperation getInfoById(String id) {
		try {
			return menuOperationJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getInfoById: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public SysMenuOperation save(SysMenuOperation menuOperation) {
		try {
			if (Strings.isNullOrEmpty(menuOperation.getId())) {
				menuOperation.setStatus(PublicConstants.STATUS_YES);
			}
			menuOperation.setCreateTime(new Timestamp(System.currentTimeMillis()));
			menuOperation.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			return menuOperationJPADao.save(menuOperation);
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
			// 删除菜单与操作关联
			menuOperJPADao.deleteByOperationId(id);
			// 删除操作与角色关联
			// 删除操作
			menuOperationJPADao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	@Override
	public List<String> selectByMenuId(String menuId) {
		try {
			return menuOperJPADao.selectByMenuId(menuId);
		} catch (Exception e) {
			LOGGER.error("Error selectByMenuId: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public int checkType(String id, String type) {
		try {
			return menuOperationJPADao.countTypeById(id, type);
		} catch (Exception e) {
			LOGGER.error("Error checkType: ", e);
		}
		return 0;
	}

}
