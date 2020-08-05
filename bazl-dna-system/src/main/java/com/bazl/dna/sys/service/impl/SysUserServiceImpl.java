package com.bazl.dna.sys.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.RedisCacheAble;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.annotation.RedisCachePut;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.dao.ISysRoleJPADao;
import com.bazl.dna.sys.dao.ISysUserJPADao;
import com.bazl.dna.sys.dao.ISysUserOrgJobJPADao;
import com.bazl.dna.sys.dao.ISysUserRoleJPADao;
import com.bazl.dna.sys.entity.SysJob;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.entity.SysRole;
import com.bazl.dna.sys.entity.SysUser;
import com.bazl.dna.sys.entity.SysUserOrgJob;
import com.bazl.dna.sys.entity.SysUserRole;
import com.bazl.dna.sys.mapper.SysUserMapper;
import com.bazl.dna.sys.service.ISysOrganizationService;
import com.bazl.dna.sys.service.ISysUserService;
import com.bazl.dna.util.DateUtil;
import com.bazl.dna.util.JwtUtils;
import com.bazl.dna.util.QueryUtils;
import com.bazl.dna.util.query.Criteria;
import com.bazl.dna.util.query.QueryCriteriaBean;
import com.bazl.dna.util.query.Restrictions;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

@Service
public class SysUserServiceImpl implements ISysUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

	private static final String CACHE_LOGIN_NAME = "Auth:Login";
	private static final String CACHE_NAME = "SysUser";
	private static final String CACHE_KEY_ID = "#id";
	private static final String CACHE_VALUE_LIST = "findlist";
	private static final String CACHE_VALUE_FIND_LIST = CACHE_NAME + "_" + CACHE_VALUE_LIST;

	@Autowired
	private RedisTemplate<String, Object> jsonRedisTemplate;
	
	@Autowired
	private ISysUserJPADao userJPADao;

	@Autowired
	private ISysUserRoleJPADao userRoleJPADao;

	@Autowired
	private ISysRoleJPADao roleJPADao;

	@Autowired
	private ISysOrganizationService organizationService;
	
	@Autowired
	private ISysUserOrgJobJPADao userOrgJobJPADao;

	@Autowired
	private SysUserMapper userMapper;

	@Value("${ACCESS_TOKEN_SECRET}")
	public String accessTokenSecret;

	@Override
	public JSONObject login(String userName, String p) {
		try {
			String password = DigestUtils.md5Hex(p).toUpperCase();
			SysUser user = userJPADao.getUserByUserName(userName, password);
			if (user != null) {
				String key = CACHE_LOGIN_NAME + ":" + userName;
				String cache = (String) jsonRedisTemplate.opsForValue().get(key);
				if (cache != null) {
					return JSON.parseObject(cache);
				}
				
				Date time = DateUtil.after(new Date(), 30 * 24, Calendar.HOUR);
				JSONObject tokenJson = new JSONObject();
				tokenJson.put(SysConstants.USER_ID, user.getId());
				tokenJson.put(SysConstants.USER_NAME, user.getUserName());
				tokenJson.put(SysConstants.PASS, user.getPassword());
				List<JSONObject> roleList = userRoleJPADao.findUserRoleByUserId(user.getId());
				List<String> jsonList = Lists.newArrayList();
				for (JSONObject json : roleList) {
					jsonList.add(json.getString(SysConstants.ID));
				}
				List<JSONObject> jobList = userOrgJobJPADao.findUserOrgJobByUserId(user.getId());
				tokenJson.put(SysConstants.ROLE_LIST, jsonList);
				tokenJson.put("jobList", jobList);
				tokenJson.put("isAdmin", user.getIsAdmin());
				tokenJson.put("userType", user.getUserType());
				tokenJson.put("tokenType", "login");
				
				JSONObject result = new JSONObject();
				result.put("Expired", time.getTime());
				result.put(SysConstants.REAL_NAME, user.getRealName());
				result.put("isAdmin", user.getIsAdmin());
				result.put(SysConstants.USER_ID, user.getId());
				List<String> orgList = Lists.newArrayList();
				List<String> clientOrgList = Lists.newArrayList();
				for (JSONObject json : jobList) {
					orgList.add(json.getString("orgName"));
					clientOrgList.add(json.getString("orgCode"));
					result.put("parentId", json.getString(SysConstants.ORG_ID));
				}
				tokenJson.put("orgId", Joiner.on(",").join(clientOrgList));
				result.put("orgList", orgList);
				result.put(SysConstants.CLIENT_ORG_LIST, clientOrgList);
				
				String accessToken = JwtUtils.generateToken(tokenJson.toJSONString(), time, accessTokenSecret);
				result.put("accessToken", accessToken);
				jsonRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);
				
				return result;
			}
		} catch (Exception e) {
			LOGGER.error("Error login: ", e);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> findList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			
			int totalCount = userMapper.countList(paramJson.getString(SysConstants.USER_NAME),
					paramJson.getString(SysConstants.REAL_NAME), paramJson.getString("phone"), paramJson.getString(SysConstants.ORG_ID));
			List<SysUser> list = userMapper.findList(paramJson.getString(SysConstants.USER_NAME), 
					paramJson.getString(SysConstants.REAL_NAME), paramJson.getString("phone"), paramJson.getString(SysConstants.ORG_ID), 
					data.getOffset(), data.getPageSize());
			
			list.stream().forEach(sysUser -> {
				// 机构信息
				SysUserOrgJob org = new SysUserOrgJob();
				org.setSysUser(sysUser);
		        Example<SysUserOrgJob> example = Example.of(org);
				Optional<SysUserOrgJob> entity = userOrgJobJPADao.findOne(example);
				if (entity.isPresent()) {
					SysOrganization sysrOrganization = entity.get().getSysOrganization();
					
					if (sysrOrganization != null) {
						sysUser.setSysOrganization(sysrOrganization);
						sysUser.setOrgName(sysrOrganization.getOrgName());
					}
				}
				
				// 角色信息
				List<JSONObject> userRoleList = userRoleJPADao.findUserRoleByUserId(sysUser.getId());
				List<String> roles = Lists.newArrayList();
				for (JSONObject json : userRoleList) {
					roles.add(json.getString(SysConstants.ROLE_NAME));
				}
				sysUser.setRoleNames(String.join(",", roles));
			});
			
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(data.getPageIndex(), totalCount));
			result.put(PublicConstants.PARAM_LIST, list);
			return result;
		} catch (Exception e) {
			LOGGER.error("Error findList: ", e);
		}
		return null;
	}
	
	@Override
	public Page<SysUser> findUserDataSourceList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);
			
			Criteria<SysUser> criteria = new Criteria<>();
			criteria.add(Restrictions.eq(SysConstants.USER_NAME, paramJson.getString(SysConstants.USER_NAME)));
			criteria.add(Restrictions.like(SysConstants.REAL_NAME, paramJson.getString(SysConstants.REAL_NAME)));
			Page<SysUser> pageList = userJPADao.findAll(criteria, pageable);
			
			pageList.getContent().stream().forEach(sysUser -> {
				SysUserOrgJob org = new SysUserOrgJob();
				org.setSysUser(sysUser);
		        Example<SysUserOrgJob> example = Example.of(org);
				Optional<SysUserOrgJob> entity = userOrgJobJPADao.findOne(example);
				if (entity.isPresent())
					sysUser.setSysOrganization(entity.get().getSysOrganization());
			});
			return pageList;
		} catch (Exception e) {
			LOGGER.error("Error findUserDataSourceList: ", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> getById(String id) {
		try {
			SysUser user = userJPADao.getOne(id);
			List<JSONObject> jobList = userOrgJobJPADao.findUserOrgJobByUserId(user.getId());
			List<JSONObject> roleList = userRoleJPADao.findUserRoleByUserId(user.getId());
			List<String> roles = Lists.newArrayList();
			roleList.stream().forEach(json -> 
				roles.add(json.getString(SysConstants.ID))
			);
			user.setRoles(roles);
			Map<String, Object> result = Maps.newHashMap();
			result.put("userInfo", user);
			result.put("userJobList", jobList);
			if (!jobList.isEmpty()) {
				SysOrganization organization = organizationService.getById(jobList.get(0).getString(SysConstants.ORG_ID));
				List<String> clientOrgList = organizationService.findOrgCodeList(
						organization.getOrgCode(), organization.getParentId());
				user.setClientOrgList(clientOrgList);
				user.setOrgName(organization.getOrgName());
			}
			
			return result;
		} catch (Exception e) {
			LOGGER.error("Error getById: ", e);
		}
		return null;
	}
	
	@Override
	@RedisCacheAble(value = CACHE_NAME, key = CACHE_KEY_ID)
	public SysUser selectByPrimaryKey(String id) {
		try {
			return userJPADao.getOne(id);
		} catch (Exception e) {
			LOGGER.error("Error selectByPrimaryKey: ", e);
		}
		return null;	
	}

	@Override
	@Transactional
	@RedisCachePut(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public SysUser saveUser(JSONObject json) {
		try {
			JSONObject user = json.getJSONObject("userInfo");
			SysUser entity = JSON.parseObject(user.toJSONString(), SysUser.class);
			if (StringUtils.trimToNull(entity.getId()) == null) {
				entity.setId(UUID.randomUUID().toString().replace("-", ""));
			}
			entity.setActiveStatus(PublicConstants.STATUS_YES);
			entity.setCreateTime(new Timestamp(new Date().getTime()));
			entity = userJPADao.save(entity);
			
			// 删除角色关联
			userRoleJPADao.deleteUserRoleByUserId(entity.getId());
			@SuppressWarnings("unchecked")
			List<String> roles = json.getObject(SysConstants.ROLES, List.class);
			for (int i = 0; i < roles.size(); i++) {
				SysRole sysRole = roleJPADao.getOne(roles.get(i));
				SysUserRole sysUserRole = new SysUserRole();
				
				sysUserRole.setSysRole(sysRole);
				sysUserRole.setSysUser(entity);
				userRoleJPADao.save(sysUserRole);
			}
			
			// 以免误删除 删除机构职位关联
			userOrgJobJPADao.deleteUserOrgJobByUserId(entity.getId());
			@SuppressWarnings("unchecked")
			List<String> orgList = user.getObject(SysConstants.CLIENT_ORG_LIST, List.class);
			for(int i = 0; i < orgList.size();i++){
				SysUserOrgJob userJob = new SysUserOrgJob();
				userJob.setSysUser(entity);
				SysOrganization sysOrganization = organizationService.getByCode(orgList.get(i));
				userJob.setSysOrganization(sysOrganization);
				SysJob sysJob = new SysJob();
				sysJob.setId(SysConstants.DEFAULT_ROLE); // 默认职位
				userJob.setSysJob(sysJob);
				userJob.setStatus(PublicConstants.STATUS_YES);
				userJob.setCreateTime(new Timestamp(System.currentTimeMillis()));
				userJob.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				userJob.setPositionOrder(1);
				userOrgJobJPADao.save(userJob);
			}
			
			return entity;
		} catch (Exception e) {
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
			userOrgJobJPADao.deleteUserOrgJobByUserId(id);
			userRoleJPADao.deleteUserRoleByUserId(id);
			userJPADao.deleteById(id);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error deleteById: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public long checkUserName(String userId, String userName) {
		try {
			Criteria<SysUser> criteria = new Criteria<>();
			criteria.add(Restrictions.eq(SysConstants.USER_NAME, StringUtils.trimToNull(userName)));
			if (!Strings.isNullOrEmpty(userId)) {
				criteria.add(Restrictions.ne(SysConstants.ID, userId));
			}
			return userJPADao.count(criteria);
		} catch (Exception e) {
			LOGGER.error("Error checkUserName: ", e);
		}
		return 0L;
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public int editPassword(String id, String p) {
		try {
			SysUser user = userJPADao.getOne(id);
			user.setPassword(p);
			userJPADao.save(user);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error editPassword: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	@RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_VALUE_FIND_LIST, allEntries = true)
	public int editStatus(String id, String status) {
		try {
			SysUser user = userJPADao.getOne(id);
			user.setStatus(status);
			userJPADao.save(user);
			return 1;
		} catch (Exception e) {
			LOGGER.error("Error editStatus: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public ResponseData findGrantList(JSONObject paramJson) {
		try {
			QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
			Pageable pageable = QueryUtils.buildPageRequest(data);

			String userName = null;
			String roleName = null;
			String realName = null;
			JSONArray array = paramJson.getJSONArray("whereList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject where = array.getJSONObject(i);
				String key = where.getString("key");
				String val = where.getString("val");
				if (SysConstants.USER_NAME.equals(key)) {
					userName = val;
				}
				if (SysConstants.ROLE_NAME.equals(key)) {
					roleName = val;
				}
				if (SysConstants.REAL_NAME.equals(key)) {
					realName = val;
				}
			}

			List<Map<String, String>> list = userMapper.findGrantList(pageable.getOffset(), pageable.getPageSize(),
					userName, roleName, realName);
			Map<String, Object> result = Maps.newHashMap();
			result.put("count", userMapper.countGrantList(pageable.getOffset(),
					pageable.getPageSize(), userName, roleName, realName));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		} catch (Exception e) {
			LOGGER.error("Error findGrantList: ", e);
			return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, e.getMessage());
		}
	}

	@Override
	public ResponseData getUserGrant(String userId) {
		try {
			List<SysRole> roleList = roleJPADao.findRoleList(PublicConstants.STATUS_YES);
			List<JSONObject> userRoleList = userRoleJPADao.findUserRoleByUserId(userId);
			Map<String, Object> result = Maps.newHashMap();
			result.put(SysConstants.ROLE_LIST, roleList);
			result.put("userRoleList", userRoleList);
			return new ResponseData(result);
		} catch (Exception e) {
			LOGGER.error("Error getUserGrant: ", e);
			return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, e.getMessage());
		}
	}

	@Override
	@Transactional
	public ResponseData saveUserGrant(JSONObject dataJson) {
		try {
			String userId = dataJson.getString(SysConstants.USER_ID);
			if (Strings.isNullOrEmpty(userId)) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
			userRoleJPADao.deleteUserRoleByUserId(userId);
			JSONArray roleArray = dataJson.getJSONArray(SysConstants.ROLE_LIST);
			SysUser user = new SysUser();
			user.setId(userId);
			for (int i = 0; i < roleArray.size(); i++) {
				JSONObject roleJson = roleArray.getJSONObject(i);
				SysUserRole userRole = new SysUserRole();
				userRole.setSysUser(user);
				if (Strings.isNullOrEmpty(roleJson.getString(SysConstants.ROLE_ID))) {
					return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
				}
				SysRole role = new SysRole();
				role.setId(roleJson.getString(SysConstants.ROLE_ID));
				userRole.setSysRole(role);
				userRoleJPADao.save(userRole);
			}
			return new ResponseData();
		} catch (Exception e) {
			LOGGER.error("Error saveUserGrant: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public List<Map<String, Object>> findUserByOrgList(List<String> orgList) {
		try {
			return userOrgJobJPADao.findUserByOrgList(orgList);
		} catch (Exception e) {
			LOGGER.error("Error findUserByOrgList: ", e);
		}
		return Lists.newArrayList();
	}

}
