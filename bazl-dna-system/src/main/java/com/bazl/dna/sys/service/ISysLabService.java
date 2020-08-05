package com.bazl.dna.sys.service;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysLab;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ISysLabService {

	/**
	 * 查询列表
	 */
	public Page<SysLab> findList(JSONObject paramJson);
	
	/**
	 * 查询详情
	 */
	public SysLab getById(Integer id);
	
	/**
	 * 保存
	 */
	public SysLab save(SysLab entity);
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);

    /**
	 * 查询全部
	 * @return
     */
	List<Map<String,String>> findListAll();

	public SysLab getByUser(String userName);
}
