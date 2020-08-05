package com.bazl.dna.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.entity.SysUser;

/**
 * 用户接口
 */
public interface ISysUserService {
	
	/**
	 * 登陆
	 */
	public Map<String, Object> login(String userName, String password);
	
	/**
	 * 查询列表
	 */
	public Map<String, Object> findList(JSONObject paramJson);
	
	/**
	 * 根据编号查询详情
	 */
	public Map<String, Object> getById(String id);
	
	/**
	 * 根据编号查询对象
	 * @param id
	 * @return
	 */
	public SysUser selectByPrimaryKey(String id);
	
	/**
	 * 保存
	 */
	public SysUser saveUser(JSONObject dataJson);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
	
	/**
	 * 检测用户名
	 */
	public long checkUserName(String userId,String userName);
	
	/**
	 * 修改密码
	 */
	public int editPassword(String id,String password);
	
	/**
	 * 修改状态
	 */
	public int editStatus(String id,String status);
	
	/**
	 * 查询授权列表
	 */
	public ResponseData findGrantList(JSONObject paramJson);
	
	/**
	 * 获取用户授权
	 */
	public ResponseData getUserGrant(String userId);
	
	/**
	 * 保存用户授权
	 */
	public ResponseData saveUserGrant(JSONObject dataJson);

	/**
	 * 用户列表
	 * @param json
	 * @return
	 */
	public Page<SysUser> findUserDataSourceList(JSONObject json);

	/**
	 * 按机构查询用户
	 * @param orgList
	 * @return
	 */
	public List<Map<String, Object>> findUserByOrgList(List<String> orgList);
	
}
