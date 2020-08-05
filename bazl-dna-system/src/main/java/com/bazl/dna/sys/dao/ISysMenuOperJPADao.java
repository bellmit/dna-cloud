package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysMenuOper;

public interface ISysMenuOperJPADao extends JpaRepository<SysMenuOper, String>,JpaSpecificationExecutor<SysMenuOper>{
	
	
	@Query(value = "select operation_id from nt_sys_menu_oper  where menu_id=?1",nativeQuery=true)
	public List<String> selectByMenuId(String menuId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_menu_oper where menu_id=?1 ",nativeQuery = true)
	public void deleteByMenuId(String menuId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_menu_oper where operation_id=?1 ",nativeQuery = true)
	public void deleteByOperationId(String operationId);
}
