package com.bazl.dna.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysOrganization;

/**
 * 机构接口
 */
public interface ISysOrganizationService {
	
	/**
	 * 查询列表
	 */
	public Page<SysOrganization> findList(JSONObject paramJson);
	
	/**
	 * 查询详情
	 */
	public SysOrganization getById(String id);
	
	/**
	 * 保存
	 */
	public SysOrganization saveOrg(SysOrganization org);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
	/**
	 * 检测代码
	 */
	public long checkCode(String orgId,String code,String type);
	
	/**
	 * 直接测试数据源是否连接
	 * @param paramJson
	 * @return
	 */
	public Map<String,Object> isConnection(JSONObject paramJson);
	
	/**
	 * 判断保存后数据源是否连接
	 * @param id
	 * @return
	 */
	public Map<String,Object> checkConnection(String id);

	public List<Map<String, Object>> findListByParentId(String parentId);

	public SysOrganization getByCode(String orgCode);
	
	public List<String> findOrgCodeList(String orgCode, String id);
	
}
