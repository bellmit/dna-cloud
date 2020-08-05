package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.ISysMenuTypeJPADao;
import com.bazl.dna.sys.entity.SysMenuType;
import com.bazl.dna.sys.service.ISysMenuTypeService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.google.common.base.Strings;

@Service
public class SysMenuTypeServiceImpl implements ISysMenuTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuTypeServiceImpl.class);

	private static final String CACHE_NAME = "SysMenuType";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;
	private static final String CACHE_VALUE_FIND_MENU_LIST = "SysMenu" + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysMenuTypeJPADao menuTypeJPADao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysMenuType> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysMenuType> criteria = QueryUtils.buildCriteria(data);
			return menuTypeJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysMenuType getById(String id) {
		try {
			return menuTypeJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public SysMenuType save(SysMenuType menuType) {
		try {
			if (Strings.isNullOrEmpty(menuType.getId())) {
				menuType.setStatus(PublicConstants.STATUS_YES);
				menuType.setCreateTime(new Timestamp(System.currentTimeMillis()));
				menuType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			} else {
				menuType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				menuType.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			return menuTypeJPADao.save(menuType);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public int editStatusById(String id, String status) {
		try {
			return menuTypeJPADao.updateById(id, status);
		} catch (Exception e) {
			LOGGER.error("Error editStatusById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public int checkCode(String id, String code) {
		try {
			return menuTypeJPADao.countTypeById(id, code);
		} catch (Exception e) {
			LOGGER.error("Error checkCode: ", e);
		}
		return 0;
	}

	@Override
	public SysMenuType getMenuTypeByTypeCode(String menuType) {
		try {
			return menuTypeJPADao.getMenuTypeByTypeCode(menuType);
		} catch (Exception e) {
			LOGGER.error("Error getMenuTypeByTypeCode: ", e);
		}
		return null;
	}

}
