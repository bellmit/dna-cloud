package com.bazl.dna.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysDictItemType;

/**
 * 字典类型接口
 */
public interface ISysDictItemTypeService {
	
	/**
	 * 添加
	 */
	public SysDictItemType save(SysDictItemType type);
	
	/**
	 * 按编号查询详情
	 */
	public SysDictItemType getById(String id);
	
	/**
	 * 查询列表
	 */
	public Page<SysDictItemType> findList(JSONObject paramJson);
	
	/**
	 * 检测typeId是否存在
	 */
	public int checkTypeId(String id,String code);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
	/**
	 * 查询列表
	 */
	public List<SysDictItemType> findListByCodes(JSONObject json);
	
}
