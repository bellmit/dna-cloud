package com.bazl.dna.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.entity.SysRole;

/**
 * 角色接口
 */
public interface ISysRoleService {
	
	/**
	 * 查询列表
	 */
	public Page<SysRole> findList(JSONObject paramJson);
	
	/**
	 * 查询详情
	 */
	public SysRole getById(String id);
	
	/**
	 * 保存
	 */
	public SysRole save(SysRole role);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
	/**
	 * 检测roleName
	 */
	public long checkRoleName(String id,String roleName);
	
	/**
	 * 角色授权页面
	 */
	public ResponseData roleGrantList(String id,String menuType);
	
	/**
	 * 角色授权
	 */
	public int saveRoleGrant(JSONObject data);
	
	/**
	 * 根据用户角色获取菜单
	 */
	public JSONArray findMenuByRoles(List<String> roles, String menuType);
	
	/**
	 * 根据用户角色菜单获取菜单操作
	 */
	public List<Map<String, String>> findMenuOperByRoles(List<String> roles, String menuId);

	/**
	 * 角色授权 不带操作权限
	 */
	public int saveAuthority(String string, JSONArray menuIds, String roleId);
	
	public List<String> findMenuByRoleId(String menuType, String roleId);

	public JSONArray findTreeList(String menuType);
}
