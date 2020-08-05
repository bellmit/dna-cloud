package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
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
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.dao.IDataSourceJPADao;
import com.bazl.dna.sys.entity.DataSourceConfig;
import com.bazl.dna.sys.service.IDataSourceConfigService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;

@Service
public class DataSourceConfigServiceImpl implements IDataSourceConfigService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigServiceImpl.class);
	
	private static final String CACHE_NAME = "DataSourceConfig";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + "findlist";
	
	@Autowired
	private IDataSourceJPADao dao;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<DataSourceConfig> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			if (paramJson.isEmpty()) {
				data.setPageSize(Integer.MAX_VALUE);
			}
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<DataSourceConfig> criteria = QueryUtils.buildCriteria(data);
			return dao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList:", e);
		}
		return null;
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public DataSourceConfig getById(String id) {
		try {
			return dao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getById:", e);
		}
		return null;	
	}
	
	@Override
	public DataSourceConfig getConnectName(String connectName) {
		return dao.getConnectName(connectName);
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	})
	public DataSourceConfig save(DataSourceConfig entity) {
		try {
			if (StringUtils.trimToNull(entity.getId()) == null) {
				entity.setCreateTime(new Timestamp(new Date().getTime()));
			}
			String driverName = null;
			String url = null;
			if (DataSourceConstants.DB_TYPE_MYSQL.equals(entity.getDbType())) {
				driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
				url = "jdbc:mysql://" + entity.getIp() + ":" + entity.getPort() + "/" + entity.getDbName();
			} else if (DataSourceConstants.DB_TYPE_ORACLE.equals(entity.getDbType())) {
				driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
				url = "jdbc:oracle:thin:@" + entity.getIp() + ":" + entity.getPort() + ":" + entity.getDbName();
			}
			entity.setDriverName(driverName);
			entity.setUrl(url);
			entity.setUpdateTime(new Timestamp(new Date().getTime()));
			return dao.save(entity);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	})
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
