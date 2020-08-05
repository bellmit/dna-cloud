package com.bazl.dna.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysRoleMenuOper;

public interface ISysRoleMenuOperJPADao extends JpaRepository<SysRoleMenuOper, String>,JpaSpecificationExecutor<SysRoleMenuOper>{
	
	@Query(value = "select menu_id as menuId,operation_id as operationId from nt_sys_role_menu_oper where role_id=?1 and menu_type_id=?2",nativeQuery = true)
	public List<Map<String,String>> findMenuOperListByRoleId(String roleId,String menuTypeId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_role_menu_oper where role_id=?1 and menu_type_id=?2",nativeQuery = true)
	public void deleteRoleMenuOperByRoleId(String roleId,String menuTypeId);
	

	@Query(value = "select DISTINCT b.id as operId,b.operation_name as operName,b.operation_type as operType from nt_sys_role_menu_oper a,nt_sys_operation b where a.role_id in (:roles) and a.operation_id=b.id and a.menu_id=:menuId",nativeQuery = true)
	public List<Map<String, String>> findMenuOperByRoles(@Param("roles") List<String> roles, @Param("menuId") String menuId);
	
}
