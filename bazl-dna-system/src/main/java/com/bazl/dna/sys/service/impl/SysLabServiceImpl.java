package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.utils.Lists;
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
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.dao.ISysLabJPADao;
import com.bazl.dna.sys.dao.ISysOrganizationJPADao;
import com.bazl.dna.sys.entity.SysLab;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.service.ISysLabService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.collect.Sets;

@Service
public class SysLabServiceImpl implements ISysLabService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysLabServiceImpl.class);

	private static final String CACHE_NAME = "SysLab";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysLabJPADao dao;
	
	@Autowired
	private ISysOrganizationJPADao orgDao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysLab> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysLab> criteria = new Criteria<>();
			
			@SuppressWarnings("unchecked")
			List<String> clientOrgList = paramJson.getObject(SysConstants.CLIENT_ORG_LIST, List.class);
			Set<String> orgList = Sets.newHashSet();
			if (clientOrgList != null) {
				clientOrgList.stream().forEach(s -> {
					SysOrganization entity = orgDao.getByCode(s);
					orgList.add(s);
					List<Map<String, Object>> list = orgDao.findListByParentId(entity.getId());
					list.stream().forEach(l ->
						orgList.add(l.get("orgCode").toString())
					);
				});
			}
			
			if (!orgList.isEmpty()) {
				criteria.add(Restrictions.in(SysConstants.ORG_ID, orgList, true));
			}
			
			criteria.add(Restrictions.eq("serverNumber", paramJson.getString("serverNumber")));
			criteria.add(Restrictions.like("labName", paramJson.getString("labName")));
			return dao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}


	@Override
	public List<Map<String,String>> findListAll( ) {
		try {
			return dao.findListAll();
		} catch (Exception e) {
			LOGGER.error("Error findListAll: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public SysLab getById(Integer id) {
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
	public SysLab save(SysLab entity) {
		try {
			if(entity.getId() == null){
				entity.setStatus(PublicConstants.STATUS_YES);
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			entity.setServerIpAddr(entity.getServerIpLeft() + "-" + entity.getServerIpRight());
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
	public int deleteById(Integer id) {
		try {
			dao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}


	@Override
	public SysLab getByUser(String userName) {
		return dao.getByUser(userName);
	}
}
