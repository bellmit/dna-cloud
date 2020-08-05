package com.bazl.dna.sys;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.service.ISysOrganizationService;
import com.bazl.dna.sys.service.ISysUserService;
import com.google.common.collect.Lists;

public class LimsSyncUser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LimsSyncUser.class);
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private ISysOrganizationService orgService;

	public void syncUser() {

		try {
			DataSource dataSource = (DataSource)ContextHolder.dataSourceMap.get("110");
			Connection connection = dataSource.getConnection();
			String sql = "select u.USER_ID, u.LOGIN_NAME, u.LOGIN_PASSWORD, u.USER_TYPE, u.ORG_ID,u.SUB_ORG_ID, pi.PERSONAL_ID, pi.FULL_NAME,pi.ID_CARD,pi.GENDER,pi.POLICE_NO,pi.PHONE_NUMBER from LOA_USER_INFO u, AM_PERSONAL_INFO pi where u.PERSONAL_ID=pi.PERSONAL_ID and u.USER_TYPE is not null";
			List<Map<String, String>> userList = DataSourceUtil.execute(connection, sql);
			connection.close();
			
			System.out.println(userList.size());
			for (Map<String, String> map : userList) {
				String pid = map.get("user_id").toString();
				String id = map.get("personal_id").toString();
				String userName = map.get("login_name") == null ? map.get("full_name"):map.get("login_name").toString();
				String password = map.get("login_password") == null ? "1":map.get("login_password").toString();
				String realName = map.get("full_name").toString();
				String idCard = map.get("id_card") == null ? null:map.get("id_card").toString();
				String gender = map.get("gender") == null ? null:map.get("gender").toString();
				String userCode = map.get("police_no") == null ? null:map.get("police_no").toString();
				String phone = map.get("phone_number") == null ? null:map.get("phone_number").toString();
				long isCheck = userService.checkUserName(null, userName);
				if (isCheck != 1l) {
					JSONObject dataJson = new JSONObject();
					
					JSONObject userInfoJson = new JSONObject();
					
					userInfoJson.put("id", id);
					userInfoJson.put("userCode", userCode);
					userInfoJson.put("userName", userName);
					userInfoJson.put("password", DigestUtils.md5Hex(password));
					userInfoJson.put("realName", realName);
					userInfoJson.put("idCard", idCard);
					userInfoJson.put("phone", phone);
					userInfoJson.put("remark", pid);
					
					int g = 0;
					if ("01".equals(gender)) {
						g = 1;
					} else if ("02".equals(gender)) {
						g = 2;
					} else {
						g = 1;
					}
					userInfoJson.put("gender", g);
					
					List<String> orgList = Lists.newArrayList();
					orgList.add(map.get("org_id").toString());
					if (map.get("sub_org_id") != null) {
						orgList.add(map.get("sub_org_id").toString());
					}
					userInfoJson.put(SysConstants.CLIENT_ORG_LIST, orgList);
					
					dataJson.put("userInfo", userInfoJson);
					
					List<String> roles = Lists.newArrayList();
					roles.add(map.get("user_type").toString());
					dataJson.put(SysConstants.ROLES, roles);
					
					
					userService.saveUser(dataJson);
				}
			}
			
			
		} catch (SQLException e) {
			LOGGER.error("sql error:", e);
		}
	
	}
	
	public void syncOrg() {
		try {
			DataSource dataSource = (DataSource)ContextHolder.dataSourceMap.get("110");
			Connection connection = dataSource.getConnection();
			String sql = "select ORG_CODE,PARENT_ID,ORG_NAME,ORG_LEVEL from ORG_INFO ORDER BY PARENT_ID";
			List<Map<String, String>> userList = DataSourceUtil.execute(connection, sql);
			connection.close();
			
			for (Map<String, String> map : userList) {
				String orgCode = map.get("org_code").toString();
				String orgName = map.get("org_name").toString();
				String parentId = map.get("parent_id").toString();
				String orgLevel = map.get("org_level").toString();
				SysOrganization orgInfo = orgService.getByCode(orgCode);
				if (orgInfo == null) {
					orgInfo = new SysOrganization();
					orgInfo.setOrgCode(orgCode);
					orgInfo.setOrgName(orgName);
					orgInfo.setParentId(parentId);
					orgInfo.setOrgType(orgLevel);
					orgService.saveOrg(orgInfo);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("sql error:", e);
		}
	}
}
