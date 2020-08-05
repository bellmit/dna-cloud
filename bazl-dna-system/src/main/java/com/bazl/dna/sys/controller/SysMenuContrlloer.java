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
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysMenu;
import com.bazl.dna.sys.entity.SysMenuOperation;
import com.bazl.dna.sys.service.ISysMenuOperationService;
import com.bazl.dna.sys.service.ISysMenuService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/menu")
public class SysMenuContrlloer {

	@Autowired
	public ISysMenuService menuService;

	@Autowired
	public ISysMenuOperationService operationService;

	/**
	 * 保存菜单
	 * 
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData save(@RequestBody JSONObject json) {
		if (json != null) {
			if (json.getJSONObject("sysMenu") == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
			JSONObject type = json.getJSONObject("sysMenuType");
			if (Strings.isNullOrEmpty(type.getString(SysConstants.ID))) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
			SysMenu result = menuService.save(json);
			return new ResponseData(result);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 * 获取菜单
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		Map<String, Object> result = Maps.newHashMap();
		result.put("sysMenu", menuService.getById(id));
		List<String> menuOperList = operationService.selectByMenuId(id);
		result.put("menuOperList", menuOperList);
		List<SysMenuOperation> operationList = operationService.findAll();
		result.put("operationList", operationList);
		return new ResponseData(result);

	}

	/**
	 * 菜单列表
	 * 
	 * @param menuType
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData list(@RequestBody String menuType) {
		Page<SysMenu> page = menuService.findList(menuType);
		if (page != null) {
			List<SysMenu> menuList = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE,
					new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, menuList);
			return new ResponseData(result);
		}
		return new ResponseData();
	}

	/**
	 * 修改菜单状态
	 * 
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/editStatusById")
	public ResponseData editStatusById(@RequestBody String dataJson) {
		JSONObject data = JSON.parseObject(dataJson);
		String status = data.getString(SysConstants.STATUS);
		if (Strings.isNullOrEmpty(status)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		String id = data.getString(SysConstants.ID);
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = menuService.editStatusById(id, status);
		return new ResponseData(result);
	}

}
