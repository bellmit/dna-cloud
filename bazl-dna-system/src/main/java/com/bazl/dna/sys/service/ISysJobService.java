package com.bazl.dna.sys.service;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysJob;

/**
 * 职位接口
 */
public interface ISysJobService {
	
	/**
	 * 查询职位列表
	 */
	public Page<SysJob> findJobList(JSONObject paramJson);
	
	/**
	 * 查询职位详情
	 */
	public SysJob getJobInfoById(String id);
	
	/**
	 * 保存职位
	 */
	public SysJob saveJob(SysJob job);
	
	/**
	 * 删除职位
	 */
	public int deleteById(String id);
	
}
