package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.dao.ISysMenuJPADao;
import com.bazl.dna.sys.dao.ISysMenuOperJPADao;
import com.bazl.dna.sys.entity.SysMenu;
import com.bazl.dna.sys.entity.SysMenuOper;
import com.bazl.dna.sys.entity.SysMenuOperation;
import com.bazl.dna.sys.entity.SysMenuType;
import com.bazl.dna.sys.service.ISysMenuService;
import com.bazl.dna.sys.service.ISysMenuTypeService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.AttributeBean;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuServiceImpl.class);
	
	private static final String CACHE_NAME = "SysMenu";
	private static final String CACHE_ROLE_NAME = "Auth:SysRole";
	
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private ISysMenuJPADao menuJPADao;

	@Autowired
	private ISysMenuOperJPADao menuOperJPADao;

	@Autowired
	private ISysMenuTypeService menuTypeService;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysMenu> findList(String paramJson) {
		try {
			QueryCriteriaBean data = new QueryCriteriaBean();
			data.setPageIndex(1);
			data.setPageSize(Integer.MAX_VALUE);
			AttributeBean order = new AttributeBean();
			order.setKey("menuOrder");
			order.setVal("asc");
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysMenu> criteria = new Criteria<>();
			JSONObject json = JSON.parseObject(paramJson);
			String type = json.getString(SysConstants.MENU_TYPE);
			if (Strings.isNullOrEmpty(type)) {
				type = "nt_sys";
			}
			criteria.add(Restrictions.eq("sysMenuType.typeCode", type));
			return menuJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}

	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysMenu getById(String id) {
		try {
			return menuJPADao.getOne(id);
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
		@CacheEvict(value = CACHE_ROLE_NAME, allEntries = true)
	})
	public SysMenu save(JSONObject data) {
		try {
			SysMenu menu = JSON.parseObject(data.getJSONObject("sysMenu").toJSONString(), SysMenu.class);
			JSONArray operArray = data.getJSONArray("operList");
			if (Strings.isNullOrEmpty(menu.getId())) {
				menu.setStatus(PublicConstants.STATUS_YES);
			}
			menu.setCreateTime(new Timestamp(System.currentTimeMillis()));
			menu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			JSONObject type = data.getJSONObject("sysMenuType");
			
			SysMenuType sysMenuType = menuTypeService.getById(type.getString(SysConstants.ID));
			menu.setSysMenuType(sysMenuType);
			menu = menuJPADao.save(menu);
			menuOperJPADao.deleteByMenuId(menu.getId());
			if (operArray != null) {
				for (int i = 0; i < operArray.size(); i++) {
					JSONObject json = operArray.getJSONObject(i);
					SysMenuOperation operation = new SysMenuOperation();
					operation.setId(json.getString("operId"));
					SysMenuOper menuOper = new SysMenuOper();
					menuOper.setMenuId(menu.getId());
					menuOper.setSysMenuOperation(operation);
					menuOperJPADao.save(menuOper);
				}
			}
			return menu;
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_ROLE_NAME, allEntries = true)
	})
	public int editStatusById(String id, String status) {
		try {
			return menuJPADao.updateById(id, status);
		} catch (Exception e) {
			LOGGER.error("Error editStatusById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public List<SysMenu> findMenuByTypeCode(String typeCode) {
		try {
			return menuJPADao.findMenuByTypeCode(typeCode);
		} catch (Exception e) {
			LOGGER.error("Error findMenuByTypeCode: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<Map<String, String>> findMenuByRoles(List<String> roles, String menuTypeId) {
		try {
			return menuJPADao.findMenuByRoles(roles, menuTypeId);
		} catch (Exception e) {
			LOGGER.error("Error findMenuByRoles: ", e);
		}
		return Lists.newArrayList();
	}
	
	@Override
	public List<Map<String, String>> findMenuByRoles(List<String> roles, String menuTypeId, String parentId) {
		try {
			return menuJPADao.findMenuByRoles(roles, menuTypeId, parentId);
		} catch (Exception e) {
			LOGGER.error("Error findMenuByRoles: ", e);
		}
		return Lists.newArrayList();
	}
	
	@Override
	public List<Map<String, String>> findMenu(String menuTypeId, String parentId) {
		try {
			return menuJPADao.findMenu(menuTypeId, parentId);
		} catch (Exception e) {
			LOGGER.error("Error findMenu: ", e);
		}
		return Lists.newArrayList();
	}

}
