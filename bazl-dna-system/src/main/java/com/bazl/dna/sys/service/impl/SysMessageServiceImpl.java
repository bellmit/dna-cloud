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
import com.bazl.dna.sys.dao.ISysMessageJPADao;
import com.bazl.dna.sys.entity.SysMessage;
import com.bazl.dna.sys.service.ISysMessageService;
import com.google.common.collect.Lists;

@Service
public class SysMessageServiceImpl implements ISysMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysMessageServiceImpl.class);

	private static final String CACHE_NAME = "SysMessage";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysMessageJPADao sysMessageJPADao;

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	})
	public SysMessage save(SysMessage sysMessage) {
		try {
			return sysMessageJPADao.save(sysMessage);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysMessage getById(String id) {
		try {
			return sysMessageJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, key = "#typeId")
	public List<SysMessage> findList(String messageType) {
		try {
			return sysMessageJPADao.findList(messageType);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	})
	public int deleteById(String id) {
		try {
			sysMessageJPADao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

}
