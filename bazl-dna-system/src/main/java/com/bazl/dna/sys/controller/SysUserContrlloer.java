package com.bazl.dna.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysUser;
import com.bazl.dna.sys.service.ISysUserService;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class SysUserContrlloer {
	
	@Autowired
	public ISysUserService sysUserService;

	/**
	 * 用户列表
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/list")
	public ResponseData findUserList(@RequestBody JSONObject json) {
		Map<String, Object> result = sysUserService.findList(json);
		return new ResponseData(result);
	}
	
	/**
	 * 用户列表
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/findUserDataSourceList")
	public ResponseData findUserDataSourceList(@RequestBody JSONObject json) {
		Page<SysUser> page = sysUserService.findUserDataSourceList(json);
		if (page != null) {
			List<SysUser> list = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE,
					new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		}
		return new ResponseData();
	}
	
	@GetMapping("findUserByOrgList")
	public ResponseData findUserByOrgList(@CurrentUser AuthUser authUser) {
		if (authUser.getOrgId() == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		List<String> orgList = Splitter.on(",").trimResults().splitToList(authUser.getOrgId());
		List<Map<String, Object>> result = sysUserService.findUserByOrgList(orgList);
		return new ResponseData(result);
	}

	/**
	 * 用户保存
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData save(@RequestBody JSONObject json) {
		if (json.get(SysConstants.ROLES) == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysUser result = sysUserService.saveUser(json);
		return new ResponseData(result);
	}

	/**
	 * 获取信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		Map<String, Object> result = sysUserService.getById(id);
		if (result != null) {
			return new ResponseData(result);
		}
		return new ResponseData(ErrorCodes.ERR_USER_NOT_FOUND, ErrorInfo.ERR_USER_NOT_FOUND);
	}
	
	@GetMapping("/selectByPrimaryKey")
	public ResponseData selectByPrimaryKey(@RequestParam("id") String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysUser sysUser = sysUserService.selectByPrimaryKey(id);
		return new ResponseData(sysUser);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ResponseData delete(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysUserService.deleteById(id);
		return new ResponseData(result);
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param dataJson
	 * @return
	 */
	@PostMapping(value = "/checkUserName")
	public ResponseData checkUserName(@RequestBody JSONObject json) {
		String userName = json.getString(SysConstants.USER_NAME);
		if (Strings.isNullOrEmpty(userName)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String userId = json.getString(SysConstants.ID);
		long result = sysUserService.checkUserName(userId, userName);
		return new ResponseData(result);
	}

	/**
	 * 修改密码
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("/editPassword")
	public ResponseData editPassword(@RequestBody String req) {
		JSONObject json = JSON.parseObject(req);
		String password = json.getString(SysConstants.P);
		if (Strings.isNullOrEmpty(password)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String userId = json.getString(SysConstants.USER_ID);
		if (Strings.isNullOrEmpty(userId)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysUserService.editPassword(userId, password);
		if (result == 0) {
			return new ResponseData(ErrorCodes.ERR_USER_NOT_FOUND, ErrorInfo.ERR_USER_NOT_FOUND);
		}
		return new ResponseData();
	}

	/**
	 * 修改用户状态
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/editStatus")
	public ResponseData editStatus(@RequestBody JSONObject json) {
		String status = json.getString(SysConstants.STATUS);
		if (Strings.isNullOrEmpty(status)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String userId = json.getString(SysConstants.USER_ID);
		if (Strings.isNullOrEmpty(userId)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysUserService.editStatus(userId, status);
		if (result == 0) {
			return new ResponseData(ErrorCodes.ERR_USER_NOT_FOUND, ErrorInfo.ERR_USER_NOT_FOUND);
		}
		return new ResponseData();
	}

}
