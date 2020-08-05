package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysUserRole;

public interface ISysUserRoleJPADao extends JpaRepository<SysUserRole, String>,JpaSpecificationExecutor<SysUserRole>{
	
	@Query(value = "select r.id,r.role_name as roleName,r.role_des as roleDesc from nt_sys_user_role ur, nt_sys_role r where ur.role_id=r.id and user_id=?1",nativeQuery = true)
	public List<JSONObject> findUserRoleByUserId(String userId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_user_role where user_id=?1 ",nativeQuery = true)
	public void deleteUserRoleByUserId(String userId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_user_role where role_id=?1 ",nativeQuery = true)
	public void deleteUserRoleByRoleId(String roleId);
}
