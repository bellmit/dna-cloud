package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysRoleMenu;

public interface ISysRoleMenuJPADao extends JpaRepository<SysRoleMenu, String>,JpaSpecificationExecutor<SysRoleMenu>{
	
	@Query(value = "select menu_id from nt_sys_role_menu where role_id=?1 and menu_type_id=?2",nativeQuery = true)
	public List<String> findMenuListByRoleId(String roleId,String menuTypeId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_role_menu where role_id=?1 and menu_type_id=?2",nativeQuery = true)
	public void deleteRoleMenuByRoleId(String roleId,String menuTypeId);
	
}
