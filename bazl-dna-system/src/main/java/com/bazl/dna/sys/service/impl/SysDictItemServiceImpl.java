package com.bazl.dna.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.ISysDictItemJPADao;
import com.bazl.dna.sys.dao.ISysDictItemTypeJPADao;
import com.bazl.dna.sys.entity.SysDictItem;
import com.bazl.dna.sys.entity.SysDictItemType;
import com.bazl.dna.sys.service.ISysDictItemService;
import com.google.common.collect.Lists;

@Service
public class SysDictItemServiceImpl implements ISysDictItemService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemServiceImpl.class);

	private static final String CACHE_NAME = "SysDictItem";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;
	private static final String CACHE_VALUE_FIND_TYPE_LIST = "SysDictItemType" + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysDictItemJPADao itemJPADao;

	@Autowired
	private ISysDictItemTypeJPADao itemTypeJPADao;

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_TYPE_LIST, allEntries = true)
	})
	public SysDictItem save(SysDictItem item) {
		try {
			return itemJPADao.save(item);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysDictItem getById(String id) {
		try {
			return itemJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, key = "#typeId")
	public List<SysDictItem> findTypeInfoById(String typeId) {
		try {
			SysDictItemType type = itemTypeJPADao.getDictTypeByTypeCode(typeId);
			if (type != null) {
				return itemJPADao.findItemByTypeId(type.getId());
			}
		} catch (Exception e) {
			LOGGER.error("Error findTypeInfoById: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_TYPE_LIST, allEntries = true)
	})
	public int deleteById(String id) {
		try {
			return itemJPADao.updateByItemId(id);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

}
