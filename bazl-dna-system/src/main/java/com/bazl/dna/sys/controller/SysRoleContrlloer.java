package com.bazl.dna.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysRole;
import com.bazl.dna.sys.service.ISysMenuService;
import com.bazl.dna.sys.service.ISysRoleService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/role")
public class SysRoleContrlloer {
	
	@Autowired
	public ISysRoleService roleService;
	
	@Autowired
	public ISysMenuService sysMenuService;

	/**
	 * 保存
	 * @param role
	 * @return
	 */
	@PostMapping("/save")
    public ResponseData save(@RequestBody SysRole role){
		SysRole result = roleService.save(role);
		return new ResponseData(result);
    }
	
	/**
	 * 获取信息
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
    public ResponseData get(@PathVariable String id) {
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        SysRole result = roleService.getById(id);
        return new ResponseData(result);
    }
	
	/**
	 * 列表
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData list(@RequestBody JSONObject paramJson){
	    Page<SysRole> page = roleService.findList(paramJson);
	    if (page != null) {
	    	List<SysRole> typeList = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, typeList);
			return new ResponseData(result);
	    }
	    return new ResponseData();
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
    public ResponseData delete(@PathVariable String id) {
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        int result = roleService.deleteById(id);
        return new ResponseData(result);
    }
	
	/**
	 * 判断是否存在
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/checkRoleName")
    public ResponseData checkRoleName(@RequestBody JSONObject json) {
		String id = json.getString(SysConstants.ID);
		String roleName = json.getString(SysConstants.ROLE_NAME);
		if(Strings.isNullOrEmpty(roleName)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        long result = roleService.checkRoleName(id,roleName);
        return new ResponseData(result);
    }
	
	/**
	 * 角色权限列表
	 * @param paramJson
	 * @return
	 */
	@GetMapping("/authorityList")
	public ResponseData authorityList(String id, String menuType) {
		SysRole sysRole = null;
		List<String> menuList = Lists.newArrayList();
		if (StringUtils.trimToNull(id) != null) {
			sysRole = roleService.getById(id);
			menuList = roleService.findMenuByRoleId(menuType, id);
		}
		Map<String, Object> result = Maps.newHashMap();
		result.put(SysConstants.SYS_ROLE, sysRole);
		result.put("menuList", menuList);
		result.put("treeList", roleService.findTreeList(menuType));
		
		return new ResponseData(result);
	}
	
	/**
	 * 角色权限保存
	 * @param paramJson
	 * @return
	 */
	@PostMapping("/saveAuthority")
	public ResponseData saveAuthority(@RequestBody JSONObject paramJson) {
		if (paramJson.get(SysConstants.MENU_TYPE) == null || paramJson.get("menuIds") == null || paramJson.get(SysConstants.SYS_ROLE) == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysRole sysRole = JSON.toJavaObject(paramJson.getJSONObject(SysConstants.SYS_ROLE), SysRole.class);
		SysRole entity = roleService.save(sysRole);
		
		JSONArray menuIds = paramJson.getJSONArray("menuIds");
		int result = roleService.saveAuthority(paramJson.getString(SysConstants.MENU_TYPE), menuIds, entity.getId());
		return new ResponseData(result);
	}
	
	/**
	 * 角色和操作权限列表
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/roleGrantList")
    public ResponseData roleGrantList(@RequestBody JSONObject json) {
		String id = json.getString(SysConstants.ID);
		String menuType = json.getString(SysConstants.MENU_TYPE);
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        return roleService.roleGrantList(id,menuType);
    }
	
	/**
	 * 角色和操作权限保存
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/saveRoleGrant")
    public ResponseData saveRoleGrant(@RequestBody JSONObject json) {
		String roleId = json.getString(SysConstants.ROLE_ID);
		String menuType = json.getString(SysConstants.MENU_TYPE);
		if (Strings.isNullOrEmpty(roleId)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		if (Strings.isNullOrEmpty(menuType)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
        int result = roleService.saveRoleGrant(json);
        return new ResponseData(result);
    }
}
