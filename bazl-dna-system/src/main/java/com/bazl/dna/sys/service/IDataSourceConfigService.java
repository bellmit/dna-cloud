package com.bazl.dna.sys.service;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.DataSourceConfig;

public interface IDataSourceConfigService {

	/**
	 * 查询列表
	 */
	public Page<DataSourceConfig> findList(JSONObject paramJson); 
	
	/**
	 * 查询详情
	 */
	public DataSourceConfig getById(String id);
	
	public DataSourceConfig getConnectName(String connectName);
	
	/**
	 * 保存
	 */
	public DataSourceConfig save(DataSourceConfig entity);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
}
