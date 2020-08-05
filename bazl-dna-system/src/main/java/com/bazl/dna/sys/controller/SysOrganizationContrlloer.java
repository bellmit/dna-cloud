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

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.service.ISysOrganizationService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 机构管理
 */
@RestController
@RequestMapping("/org")
public class SysOrganizationContrlloer {

	@Autowired
	public ISysOrganizationService sysOrgService;

	/**
	 * 机构信息保存
	 * 
	 * @param org
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData save(@CurrentUser AuthUser user, @RequestBody SysOrganization org) {
		org.setCreateUser(user.getId());
		SysOrganization entity = sysOrgService.saveOrg(org);
		return new ResponseData(entity);
	}

	/**
	 * id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysOrganization entity = sysOrgService.getById(id);
		return new ResponseData(entity);
	}

	/**
	 * 直接测试数据源是否连接
	 * 
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/isConnection")
	public ResponseData isConnection(@RequestBody JSONObject paramJson) {
		Map<String, Object> result = sysOrgService.isConnection(paramJson);
		return new ResponseData(result);
	}

	/**
	 * 判断保存后数据源是否连接
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/checkConnection/{id}")
	public ResponseData checkConnection(@PathVariable String id) {
		Map<String, Object> result = sysOrgService.checkConnection(id);
		return new ResponseData(result);
	}

	/**
	 * 查询列表
	 * 
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData findList(@RequestBody JSONObject paramJson) {
		Page<SysOrganization> page = sysOrgService.findList(paramJson);
		if (page != null) {
			List<SysOrganization> list = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		}
		return new ResponseData();
	}
	
	@GetMapping(value = "/findListByParentId")
	public ResponseData findListByParentId(@RequestParam("parentId") String parentId) {
		List<Map<String, Object>> result = sysOrgService.findListByParentId(parentId);
		return new ResponseData(result);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ResponseData delete(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysOrgService.deleteById(id);
		return new ResponseData(result);
	}

	/**
	 * 判断机构编号是否存在
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/checkCode")
	public ResponseData checkCode(@RequestBody JSONObject json) {
		String type = json.getString("type");
		if (Strings.isNullOrEmpty(type)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String code = json.getString(SysConstants.CODE);
		if (Strings.isNullOrEmpty(code)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String orgId = json.getString(SysConstants.ORG_ID);
		long result = sysOrgService.checkCode(orgId, code, type);

		if (result > 0) {
			return new ResponseData(ErrorCodes.ERR_IS_EXISTS, ErrorInfo.ERR_IS_EXISTS);
		}
		return new ResponseData();
	}
}
