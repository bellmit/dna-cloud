package com.bazl.dna.sys.service;

import java.util.List;

import com.bazl.dna.sys.entity.SysDictItem;

/**
 * 字典接口
 */
public interface ISysDictItemService {
	
	/**
	 * 添加
	 */
	public SysDictItem save(SysDictItem item);
	
	/**
	 * 按编号查询详情
	 */
	public SysDictItem getById(String id);
	
	/**
	 * 按分类编号编号查询详情
	 */
	public List<SysDictItem> findTypeInfoById(String typeId);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
}
