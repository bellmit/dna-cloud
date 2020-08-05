package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.dao.ISysOrganizationJPADao;
import com.bazl.dna.sys.entity.DataSourceConfig;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.service.ISysOrganizationService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class SysOrganizationServiceImpl implements ISysOrganizationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);
	
	private static final String CACHE_NAME = "SysOrganization";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + "findlist";
	private static final String CACHE_VALUE_FIND_PARENT_ID_LIST = CACHE_NAME + "_" + "findParentIdList";
	private static final String CACHE_VALUE_FIND_ORG_CODE_LIST = CACHE_NAME + "_" + "findOrgCodeList";
	
	@Autowired
	private ISysOrganizationJPADao orgJPADao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysOrganization> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			if (paramJson.isEmpty()) {
				data.setPageSize(Integer.MAX_VALUE);
			}
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysOrganization> criteria = new Criteria<>();
			criteria.add(Restrictions.eq("parentId", paramJson.getString("parentId")));
			criteria.add(Restrictions.eq("orgCode", paramJson.getString("orgCode")));
			criteria.add(Restrictions.like("orgName", paramJson.getString("orgName")));
			criteria.add(Restrictions.eq("delStatus", PublicConstants.STATUS_NO));
			return orgJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}

	@Override
	public SysOrganization getById(String id) {
		try{
			return orgJPADao.getOne(id);
		}catch(Exception e){
			LOGGER.error("Error getOne: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_PARENT_ID_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_ORG_CODE_LIST, allEntries = true),
	})
	public int deleteById(String id) {
		try{
			return orgJPADao.updateById(id);
		}catch(Exception e){
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public long checkCode(String orgId,String code,String type) {
		try{
			Criteria<SysOrganization> criteria = new Criteria<>() ;
			criteria.add(Restrictions.eq(type, code));
			if(!Strings.isNullOrEmpty(orgId)){
				criteria.add(Restrictions.ne(SysConstants.ID, orgId));
			}
			return orgJPADao.count(criteria);
		}catch(Exception e){
			LOGGER.error("Error checkCode: ", e);
		}
		return 0L;
	}

	@Override
	public Map<String,Object> checkConnection(String id) {
		try{
			Map<String,Object> result = Maps.newHashMap();
			
			SysOrganization org = getById(id);
			DataSourceConfig config = org.getDataSourceConfig();
			if (config != null) {
				boolean isConnection = DataSourceUtil.isConnection(config.getDriverName(), config.getUrl(), config.getUserName(), config.getPassword());
				result.put(SysConstants.STATUS, isConnection);
			} else {
				result.put(SysConstants.STATUS, false);
			}
			return result;
		}catch(Exception e){
			LOGGER.error("Error checkConnection: ", e);
		}
		return null;
	}
	
	@Override
	public Map<String,Object> isConnection(JSONObject json) {
		if (json == null) {
			return null;
		}
		
		String driverName = null;
		String url = null;
		if (DataSourceConstants.DB_TYPE_MYSQL.equals(json.getString("dsType"))) {
			driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
			url = "jdbc:mysql://" + json.getString("ip") + ":" + json.getIntValue("port") + "/" + json.getString("dbName");
		} else {
			driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
			url = "jdbc:oracle:thin:@" + json.getString("ip") + ":" + json.getIntValue("port") + ":" + json.getString("dbName");
		}
		boolean isConnection = DataSourceUtil.isConnection(driverName, url, 
				json.getString(SysConstants.USER_NAME), json.getString(SysConstants.PASS));
		Map<String,Object> result = Maps.newHashMap();
		result.put(SysConstants.STATUS, isConnection);
		return result;
	}
	
	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_PARENT_ID_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_ORG_CODE_LIST, allEntries = true),
	})
	public SysOrganization saveOrg(SysOrganization org) {
		try{
			if(Strings.isNullOrEmpty(org.getId())){
				org.setStatus(PublicConstants.STATUS_YES);
				org.setCreateTime(new Timestamp(System.currentTimeMillis()));
				org.setId(org.getOrgCode());
			}
			
			org.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			if (org.getParentId() == null) {
				org.setParentId("-1");
			}
			org.setOrgOrder(1);
			org.setDelStatus("0");
			
			DataSourceConfig dataSourceConfig = org.getDataSourceConfig();
			if (dataSourceConfig != null) {
				String driverName = null;
				String url = null;
				if (DataSourceConstants.DB_TYPE_MYSQL.equals(dataSourceConfig.getDbType())) {
					driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
					url = "jdbc:mysql://" + dataSourceConfig.getIp() + ":" + dataSourceConfig.getPort() + "/" + dataSourceConfig.getDbName();
				} else if (DataSourceConstants.DB_TYPE_ORACLE.equals(dataSourceConfig.getDbType())) {
					driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
					url = "jdbc:oracle:thin:@" + dataSourceConfig.getIp() + ":" + dataSourceConfig.getPort() + ":" + dataSourceConfig.getDbName();
				}
				dataSourceConfig.setDsType(DataSourceConstants.DS_TYPE_DB);
				dataSourceConfig.setDriverName(driverName);
				dataSourceConfig.setUrl(url);
				dataSourceConfig.setCreateTime(new Timestamp(new Date().getTime()));
				dataSourceConfig.setUpdateTime(new Timestamp(new Date().getTime()));
				org.setDataSourceConfig(dataSourceConfig);
			}
			org = orgJPADao.save(org);
			return org;
		}catch(Exception e){
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public List<Map<String, Object>> findListByParentId(String parentId) {
		try {
			return orgJPADao.findListByParentId(parentId);
		} catch (Exception e) {
			LOGGER.error("Error findListByParentId: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public SysOrganization getByCode(String orgCode) {
		try {
			return orgJPADao.getByCode(orgCode);
		} catch (Exception e) {
			LOGGER.error("Error getByCode: ", e);
		}
		return null;
	}

	@Override
	public List<String> findOrgCodeList(String orgCode, String id) {
		try {
			List<String> result = Lists.newArrayList();
			result.add(orgCode);
			directList(id, result);
			return Lists.reverse(result);
		} catch (Exception e) {
			LOGGER.error("Error findOrgCodeList: ", e);
		}
		return Lists.newArrayList();
		
	}
	
	private void directList(String id, List<String> result) {
		SysOrganization organization = orgJPADao.get(id);
		if (organization != null) {
			result.add(organization.getOrgCode());
			directList(organization.getParentId(), result);
		}
	}

}
