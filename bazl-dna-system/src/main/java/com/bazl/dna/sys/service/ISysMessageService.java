package com.bazl.dna.sys.service;

import java.util.List;

import com.bazl.dna.sys.entity.SysMessage;

/**
 * 消息接口
 */
public interface ISysMessageService {
	
	/**
	 * 添加
	 */
	public SysMessage save(SysMessage sysMessage);
	
	/**
	 * 按编号查询详情
	 */
	public SysMessage getById(String id);
	
	/**
	 * 按分类查询详情
	 */
	public List<SysMessage> findList(String messageType);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
}
