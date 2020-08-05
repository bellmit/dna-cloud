package com.bazl.dna.sys.service.impl;

import java.util.List;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.ISysDictItemTypeJPADao;
import com.bazl.dna.sys.entity.SysDictItemType;
import com.bazl.dna.sys.service.ISysDictItemTypeService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.google.common.collect.Lists;

@Service
public class SysDictItemTypeServiceImpl implements ISysDictItemTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemTypeServiceImpl.class);

	private static final String CACHE_NAME = "SysDictItemType";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;
	private static final String CACHE_VALUE_FIND_ITEM_LIST = "SysDictItem" + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysDictItemTypeJPADao typeJPADao;

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_ITEM_LIST, allEntries = true)
	})
	public SysDictItemType save(SysDictItemType type) {
		try {
			return typeJPADao.save(type);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysDictItemType getById(String id) {
		try {
			return typeJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysDictItemType> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysDictItemType> criteria = QueryUtils.buildCriteria(data);
			return typeJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;

	}

	@Override
	public int checkTypeId(String id, String code) {
		try {
			return typeJPADao.countTypeById(id, code);
		} catch (Exception e) {
			LOGGER.error("Error checkTypeId: ", e);
		}
		return 0;
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_ITEM_LIST, allEntries = true)
	})
	public int deleteById(String id) {
		try {
			return typeJPADao.updateByTypeId(id);
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, key = "#json")
	public List<SysDictItemType> findListByCodes(JSONObject json) {
		try {
			JSONArray codeArray = json.getJSONArray("codes");
			if (!codeArray.isEmpty()) {
				List<String> codes = JSON.parseArray(codeArray.toJSONString(), String.class);
				return typeJPADao.findListByCode(codes);
			}
		} catch (Exception e) {
			LOGGER.error("Error findListByCodes: ", e);
		}
		return Lists.newArrayList();
	}

}
