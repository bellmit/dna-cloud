package com.bazl.dna.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysMenu;

/**
 * 菜单接口
 */
public interface ISysMenuService {
	
	/**
	 * 查询列表
	 */
	public Page<SysMenu> findList(String menuType);
	
	/**
	 * 查询详情
	 */
	public SysMenu getById(String id);
	
	/**
	 * 保存
	 */
	public SysMenu save(JSONObject dataJson);
	
	/**
	 * 修改状态
	 */
	public int editStatusById(String id,String status);
	
	/**
	 * 按菜单类型查询
	 * @param typeCode
	 * @return
	 */
	public List<SysMenu> findMenuByTypeCode(String typeCode);
	
	/**
	 * 按菜单类型和角色查询
	 * @param roles
	 * @param menuTypeId
	 * @return
	 */
	public List<Map<String,String>> findMenuByRoles(List<String> roles, String menuTypeId);
	public List<Map<String,String>> findMenuByRoles(List<String> roles, String menuTypeId, String parentId);
	
	public List<Map<String,String>> findMenu(String menuTypeId, String parentId);
	
}
