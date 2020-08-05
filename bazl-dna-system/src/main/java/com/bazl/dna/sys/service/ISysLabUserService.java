package com.bazl.dna.sys.service;

import java.util.List;

import com.bazl.dna.sys.entity.SysLabUser;

public interface ISysLabUserService {

	/**
	 * 查询列表
	 */
	public List<SysLabUser> findList(Integer labId);
	
	/**
	 * 查询详情
	 */
	public SysLabUser getById(String id);
	
	/**
	 * 保存
	 */
	public SysLabUser save(SysLabUser entity);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);

	/**
	 * 查询人员数
	 * @param id
	 * @return
	 */
	public long countList(Integer id);
}
