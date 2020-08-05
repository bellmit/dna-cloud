package com.bazl.dna.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysMenuOperation;

/**
 * 菜单操作权限接口
 */
public interface ISysMenuOperationService {
	
	/**
	 * 查询列表
	 */
	public Page<SysMenuOperation> findList(JSONObject paramJson);
	
	/**
	 * 查询列表
	 */
	public List<SysMenuOperation> findAll();
	
	/**
	 * 查询菜单
	 */
	public List<String> selectByMenuId(String menuId);
	
	/**
	 * 查询详情
	 */
	public SysMenuOperation getInfoById(String id);
	
	/**
	 * 保存
	 */
	public SysMenuOperation save(SysMenuOperation menuOperation);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
	/**
	 * 检测type
	 */
	public int checkType(String id, String type);
	
}
