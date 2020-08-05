package com.bazl.dna.sys.service;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysMenuType;

/**
 * 菜单类型接口
 */
public interface ISysMenuTypeService {

	/**
	 * 查询列表
	 */
	public Page<SysMenuType> findList(JSONObject paramJson);

	/**
	 * 查询详情
	 */
	public SysMenuType getById(String id);

	/**
	 * 保存
	 */
	public SysMenuType save(SysMenuType menuType);

	/**
	 * 修改状态
	 */
	public int editStatusById(String id, String status);

	/**
	 * 检测code
	 */
	public int checkCode(String id, String code);

	/**
	 * 按菜单类型查询
	 * @param menuType
	 * @return
	 */
	public SysMenuType getMenuTypeByTypeCode(String menuType);

}
