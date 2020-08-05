package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.ISysLabUserJPADao;
import com.bazl.dna.sys.entity.SysLabUser;
import com.bazl.dna.sys.service.ISysLabUserService;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Service
public class SysLabUserServiceImpl implements ISysLabUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysLabUserServiceImpl.class);

	private static final String CACHE_NAME = "SysLabUser";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysLabUserJPADao dao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public List<SysLabUser> findList(Integer labId) {
		try {
			Criteria<SysLabUser> criteria = new Criteria<>();
			criteria.add(Restrictions.eq("labId", labId));
			return dao.findAll(criteria);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return Lists.newArrayList();
	}
	
	@Override
	public long countList(Integer labId) {
		try {
			Criteria<SysLabUser> criteria = new Criteria<>();
			criteria.add(Restrictions.eq("labId", labId));
			return dao.count(criteria);
		} catch (Exception e) {
			LOGGER.error("Error countList: ", e);
		}
		return 0;
	}

	@Override
	public SysLabUser getById(String id) {
		try{
			return dao.getOne(id);
		}catch(Exception e){
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public SysLabUser save(SysLabUser entity) {
		try {
			if(Strings.isNullOrEmpty(entity.getId())){
				entity.setStatus(PublicConstants.STATUS_YES);
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			entity = dao.save(entity);
			return entity;
		}catch(Exception e){
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
			dao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

}
