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
import com.bazl.dna.sys.entity.SysLab;
import com.bazl.dna.sys.entity.SysLabUser;
import com.bazl.dna.sys.entity.SysOrganization;
import com.bazl.dna.sys.rabbitmq.ISendLabService;
import com.bazl.dna.sys.service.ISysLabService;
import com.bazl.dna.sys.service.ISysLabUserService;
import com.bazl.dna.sys.service.ISysOrganizationService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 实验室管理
 */
@RestController
@RequestMapping("/lab")
public class SysLabController {

	@Autowired
	private ISysLabService labService;

	@Autowired
	private ISysLabUserService labUserService;

	@Autowired
	private ISysOrganizationService organizationService;

	@Autowired
	private ISendLabService sendLabService;

	@PostMapping("/list")
	public ResponseData list(@RequestBody JSONObject paramJson) {
		Page<SysLab> page = labService.findList(paramJson);
		if (page != null) {
			List<SysLab> list = page.getContent();
			list.stream().forEach(entity -> {
				long count = labUserService.countList(entity.getId());
				entity.setCountLabUser(count);
				SysOrganization org = organizationService.getByCode(entity.getOrgId());
				entity.setOrgName(org.getOrgName());
			});
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE,
					new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		}
		return new ResponseData();
	}

	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable Integer id) {
		if (id == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysLab sysLab = labService.getById(id);
		SysOrganization organization = organizationService.getByCode(sysLab.getOrgId());
		List<String> clientOrgList = organizationService.findOrgCodeList(
				organization.getOrgCode(), organization.getParentId());
		sysLab.setClientOrgList(clientOrgList);
		sysLab.setOrgName(organization.getOrgName());
		List<SysLabUser> syslabUserList = labUserService.findList(sysLab.getId());

		Map<String, Object> result = Maps.newHashMap();
		result.put("sysLab", sysLab);
		result.put("syslabUserList", syslabUserList);

		return new ResponseData(result);
	}

	@GetMapping("/getLabUser/{id}")
	public ResponseData getLabUser(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysLabUser result = labUserService.getById(id);
		return new ResponseData(result);
	}

	@PostMapping("/save")
	public ResponseData save(@CurrentUser AuthUser user, @RequestBody SysLab sysLab) {
		if (sysLab.getLabName() == null || sysLab.getServerNumber() == null || sysLab.getClientOrgList().isEmpty()) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		sysLab.setOrgId(sysLab.getClientOrgList().get(0));
		sysLab.setCreateUser(user.getId());
		SysLab result = labService.save(sysLab);

		sendLabService.send(JSON.toJSONString(result));
		return new ResponseData(result);
	}

	@PostMapping("/saveLabUser")
	public ResponseData saveLabUser(@CurrentUser AuthUser user, @RequestBody SysLabUser sysLabUser) {
		sysLabUser.setCreateUser(user.getId());
		SysLabUser result = labUserService.save(sysLabUser);
		return new ResponseData(result);
	}

	@GetMapping("/delete/{id}")
	public ResponseData delete(@PathVariable Integer id) {
		if (id == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		long count = labUserService.countList(id);
		if (count > 0) {
			return new ResponseData(ErrorCodes.ERR_USER_LAB_USER, ErrorInfo.ERR_USER_LAB_USER);
		}
		int result = labService.deleteById(id);
		return new ResponseData(result);
	}

	@GetMapping("/deleteLabUser/{id}")
	public ResponseData deleteLabUser(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = labUserService.deleteById(id);
		return new ResponseData(result);
	}

	/**
	 * 实验室下拉框展示
	 */
	@GetMapping("/selectLabName")
	public ResponseData selectLabName() {
		List<Map<String, String>> page = labService.findListAll();

		if (page != null) {
			return new ResponseData(page);
		} else {
			return new ResponseData();
		}
	}
}
