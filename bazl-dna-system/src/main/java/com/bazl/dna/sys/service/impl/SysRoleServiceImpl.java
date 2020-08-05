package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.dao.ISysMenuOperJPADao;
import com.bazl.dna.sys.dao.ISysRoleJPADao;
import com.bazl.dna.sys.dao.ISysRoleMenuJPADao;
import com.bazl.dna.sys.dao.ISysRoleMenuOperJPADao;
import com.bazl.dna.sys.dao.ISysUserRoleJPADao;
import com.bazl.dna.sys.entity.SysMenu;
import com.bazl.dna.sys.entity.SysMenuOper;
import com.bazl.dna.sys.entity.SysMenuType;
import com.bazl.dna.sys.entity.SysRole;
import com.bazl.dna.sys.entity.SysRoleMenu;
import com.bazl.dna.sys.entity.SysRoleMenuOper;
import com.bazl.dna.sys.service.ISysMenuService;
import com.bazl.dna.sys.service.ISysMenuTypeService;
import com.bazl.dna.sys.service.ISysRoleService;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.AttributeBean;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	private static final String CACHE_NAME = "Auth:SysRole";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + "findlist";
	private static final String CACHE_VALUE_FIND_MENU_LIST = CACHE_NAME + "_" + "findMenuList";
	
	@Autowired
	private ISysRoleJPADao roleJPADao;

	@Autowired
	private ISysRoleMenuJPADao roleMenuJPADao;

	@Autowired
	private ISysRoleMenuOperJPADao roleMenuOperJPADao;

	@Autowired
	private ISysUserRoleJPADao userRoleJPADao;

	@Autowired
	private ISysMenuService sysMenuService;

	@Autowired
	private ISysMenuOperJPADao menuOperJPADao;

	@Autowired
	private ISysMenuTypeService sysMenuTypeService;

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_LIST, keyGenerator = "keyGenerator")
	public Page<SysRole> findList(JSONObject paramJson) {
		try {
			
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			if (paramJson.isEmpty()) {
				data.setPageSize(Integer.MAX_VALUE);
			}
			Pageable pageable = QueryUtils.buildPageRequest(data);
			Criteria<SysRole> criteria = QueryUtils.buildCriteria(data);
			return roleJPADao.findAll(criteria, pageable);
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}

	@Override
	public SysRole getById(String id) {
		try {
			return roleJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error getInfoById: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public SysRole save(SysRole role) {
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			if (Strings.isNullOrEmpty(role.getId())) {
				role.setStatus(PublicConstants.STATUS_YES);
				role.setCreateTime(time);
			}
			if (Strings.isNullOrEmpty(role.getRoleCode())) {
				role.setRoleCode(String.valueOf(System.currentTimeMillis()));
			}
			role.setUpdateTime(time);
			return roleJPADao.save(role);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public int deleteById(String id) {
		try {
			// 删除用户与角色关联
			userRoleJPADao.deleteUserRoleByRoleId(id);
			// 删除角色与菜单操作关联
			// 删除角色与菜单关联
			// 删除角色
			roleJPADao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public long checkRoleName(String id, String roleName) {
		try {
			Criteria<SysRole> criteria = new Criteria<>();
			criteria.add(Restrictions.eq(SysConstants.ROLE_NAME, StringUtils.trimToNull(roleName)));
			if (!Strings.isNullOrEmpty(id)) {
				criteria.add(Restrictions.ne(SysConstants.ID, id));
			}
			return roleJPADao.count(criteria);
		} catch (Exception e) {
			LOGGER.error("Error checkRoleName: ", e);
		}
		return 0L;
	}

	@Override
	public ResponseData roleGrantList(String id, String menuType) {
		try {
			QueryCriteriaBean data = new QueryCriteriaBean();
			data.setPageIndex(1);
			data.setPageSize(Integer.MAX_VALUE);
			AttributeBean order = new AttributeBean();
			order.setKey("menuOrder");
			order.setVal("asc");
			Pageable pageable = QueryUtils.buildPageRequest(data);
			if (Strings.isNullOrEmpty(menuType)) {
				menuType = "nt_sys";
			}
			SysMenuType sysMenuType = sysMenuTypeService.getMenuTypeByTypeCode(menuType);
			List<SysMenu> menuList = sysMenuService.findMenuByTypeCode(menuType);
			List<Map<String, Object>> list = Lists.newArrayList();
			menuList.stream().forEach(menu -> {
				Map<String, Object> map = Maps.newHashMap();
				Criteria<SysMenuOper> criteriaOper = new Criteria<>();
				criteriaOper.add(Restrictions.eq("menuId", menu.getId()));
				Page<SysMenuOper> pageOper = menuOperJPADao.findAll(criteriaOper, pageable);
				List<SysMenuOper> menuOperList = pageOper.getContent();
				map.put("sysMenu", menu);
				map.put("operationList", menuOperList);
				list.add(map);
			});
			Map<String, Object> result = Maps.newHashMap();
			List<String> roleMenuList = roleMenuJPADao.findMenuListByRoleId(id, sysMenuType.getId());
			List<Map<String, String>> roleMenuOperList = roleMenuOperJPADao.findMenuOperListByRoleId(id,
					sysMenuType.getId());
			result.put(PublicConstants.PARAM_LIST, list);
			result.put("roleMenuList", roleMenuList);
			result.put("roleMenuOperList", roleMenuOperList);
			return new ResponseData(result);
		} catch (Exception e) {
			LOGGER.error("Error roleGrantList: ", e);
			return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, e.getMessage());
		}
	}

	@Override
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public int saveRoleGrant(JSONObject data) {
		try {
			String roleId = data.getString(SysConstants.ROLE_ID);
			String menuType = data.getString(SysConstants.MENU_TYPE);
			JSONArray roleMenuArray = data.getJSONArray("roleMenuArray");
			JSONArray roleMenuOperArray = data.getJSONArray("roleMenuOperArray");
			SysMenuType sysMenuType = sysMenuTypeService.getMenuTypeByTypeCode(menuType);
			roleMenuJPADao.deleteRoleMenuByRoleId(roleId, sysMenuType.getId());
			for (int i = 0; i < roleMenuArray.size(); i++) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(roleMenuArray.getString(i));
				roleMenu.setMenuTypeId(sysMenuType.getId());
				roleMenu.setId(roleId + sysMenuType.getId() + roleMenuArray.getString(i));
				roleMenuJPADao.save(roleMenu);
			}
			roleMenuOperJPADao.deleteRoleMenuOperByRoleId(roleId, sysMenuType.getId());
			for (int i = 0; i < roleMenuOperArray.size(); i++) {
				JSONObject json = roleMenuOperArray.getJSONObject(i);
				SysRoleMenuOper roleMenuOper = new SysRoleMenuOper();
				roleMenuOper.setRoleId(roleId);
				roleMenuOper.setMenuId(json.getString("menuId"));
				roleMenuOper.setMenuTypeId(sysMenuType.getId());
				roleMenuOper.setOperationId(json.getString("operationId"));
				roleMenuOperJPADao.save(roleMenuOper);
			}
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error saveRoleGrant: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	@Override
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true),
		@CacheEvict(value = CACHE_VALUE_FIND_MENU_LIST, allEntries = true)
	})
	public int saveAuthority(String menuType, JSONArray menuIds, String roleId) {
		try {
			roleMenuJPADao.deleteRoleMenuByRoleId(roleId, menuType);
			for (int i = 0; i < menuIds.size(); i++) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuIds.getString(i));
				roleMenu.setMenuTypeId(menuType);
				roleMenu.setId(roleId + menuType + menuIds.getString(i));
				roleMenuJPADao.save(roleMenu);
			}
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error saveAuthority: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	public List<String> findMenuByRoleId(String menuTypeId, String roleId) {
		return roleMenuJPADao.findMenuListByRoleId(roleId, menuTypeId);
	}

	public JSONArray findTreeList(String menuType) {
		JSONArray jsonArray = new JSONArray();
		List<Map<String, String>> list = sysMenuService.findMenu(menuType, "-1");
		list.stream().forEach(map -> {
			JSONObject jsonObject = setMenu(map);
			JSONArray childArray = new JSONArray();
			List<Map<String, String>> childList = sysMenuService.findMenu(menuType, map.get(SysConstants.ID));
			childList.stream().forEach(childMap -> {
				JSONObject childJsonObject = setMenu(childMap);
				JSONArray aArray = new JSONArray();
				List<Map<String, String>> aList = sysMenuService.findMenu(menuType, childMap.get(SysConstants.ID));
				aList.stream().forEach(cMap -> {
					JSONObject j = setMenu(cMap);
					aArray.add(j);
				});
				if (!aArray.isEmpty()) {
					childJsonObject.put(SysConstants.EXPAND, true);
					childJsonObject.put(SysConstants.CHILDREN, aArray);
				}
				childArray.add(childJsonObject);
			});
			if (!childArray.isEmpty()) {
				jsonObject.put(SysConstants.EXPAND, true);
				jsonObject.put(SysConstants.CHILDREN, childArray);
			}
			jsonArray.add(jsonObject);
		});

		return jsonArray;
	}

	@Override
	@Cacheable(value = CACHE_VALUE_FIND_MENU_LIST, keyGenerator = "keyGenerator")
	public JSONArray findMenuByRoles(List<String> roles, String menuType) {
		JSONArray jsonArray = new JSONArray();
		List<Map<String, String>> list = sysMenuService.findMenuByRoles(roles, menuType, "-1");
		list.stream().forEach(map -> {
			JSONObject jsonObject = setMenu(map);
			JSONArray childArray = new JSONArray();
			List<Map<String, String>> childList = sysMenuService.findMenuByRoles(roles, menuType, map.get(SysConstants.ID));
			childList.stream().forEach(childMap -> {
				JSONObject childJsonObject = setMenu(childMap);
				JSONArray aArray = new JSONArray();
				List<Map<String, String>> aList = sysMenuService.findMenuByRoles(roles, menuType, childMap.get(SysConstants.ID));
				aList.stream().forEach(cMap -> {
					JSONObject j = setMenu(cMap);
					aArray.add(j);
				});
				if (!aArray.isEmpty()) {
					childJsonObject.put(SysConstants.EXPAND, true);
					childJsonObject.put(SysConstants.CHILDREN, aArray);
				}
				childArray.add(childJsonObject);
			});
			if (!childArray.isEmpty()) {
				jsonObject.put(SysConstants.EXPAND, true);
				jsonObject.put(SysConstants.CHILDREN, childArray);
			}
			jsonArray.add(jsonObject);
		});

		return jsonArray;
	}
	
	private JSONObject setMenu(Map<String, String> map) {
		JSONObject json = new JSONObject();
		json.put("id", map.get("id"));
		json.put("title", map.get("menuDescription"));
		json.put("name", map.get("menuName"));
		json.put("path", map.get("menuAction"));
		json.put("icon", map.get("icon"));
		return json;
	}

	@Override
	public List<Map<String, String>> findMenuOperByRoles(List<String> roles, String menuId) {
		try {
			return roleMenuOperJPADao.findMenuOperByRoles(roles, menuId);
		} catch (Exception e) {
			LOGGER.error("Error findMenuOperByRoles: ", e);
		}
		return Lists.newArrayList();
	}

}
