package com.bazl.dna.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.DataSourceConfig;
import com.bazl.dna.sys.entity.SysUser;
import com.bazl.dna.sys.service.IDataSourceConfigService;
import com.bazl.dna.sys.service.ISysUserService;

@RestController
@RequestMapping("/register")
public class RegisterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IDataSourceConfigService dataSourceService;
	
	@PostMapping("save")
	public ResponseData save(@RequestBody JSONObject json) {
		LOGGER.info("register:{}", json);
		
		// 用户信息
		JSONObject userinfoJson = json.getJSONObject("userInfo");
		// 数据源信息
		JSONObject dataSourceJson = json.getJSONObject("dataSource");
		
		if (userinfoJson == null || dataSourceJson == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		
		long isExists = sysUserService.checkUserName(null, userinfoJson.getString(SysConstants.USER_NAME));
		if (isExists == PublicConstants.SUCCESS_CODE) {
			return new ResponseData(ErrorCodes.ERR_IS_EXISTS, ErrorInfo.ERR_IS_EXISTS);
		}
		
		String connectName = userinfoJson.getString("connectName");
		DataSourceConfig dataSourceConfig = dataSourceService.getConnectName(connectName);
		if (dataSourceConfig == null) {
			// 保存数据源
			dataSourceConfig = dataSourceJson.toJavaObject(DataSourceConfig.class);
			dataSourceConfig.setDbType(DataSourceConstants.DB_TYPE_ORACLE);
			dataSourceConfig.setDsType(DataSourceConstants.DS_TYPE_DB);
			dataSourceConfig.setConnectName(connectName);
			dataSourceService.save(dataSourceConfig);
		}
		
		// 保存用户信息
		JSONArray roles = new JSONArray();
		roles.add(SysConstants.DEFAULT_ROLE);
		json.put(SysConstants.ROLES, roles);
		
		JSONArray orgList = new JSONArray();
		orgList.add(connectName);
		userinfoJson.put(SysConstants.CLIENT_ORG_LIST, orgList);
		SysUser sysUser = sysUserService.saveUser(json);
		return new ResponseData(sysUser);
	}
}
