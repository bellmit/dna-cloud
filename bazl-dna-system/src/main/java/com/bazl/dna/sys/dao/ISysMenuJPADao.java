package com.bazl.dna.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysMenu;

public interface ISysMenuJPADao extends JpaRepository<SysMenu, String>,JpaSpecificationExecutor<SysMenu>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_menu set status=?2 where id=?1",nativeQuery=true)
	public int updateById(String typeId,String status);
	
	@Query(value = "select DISTINCT b.menu_name as menuName, b.id, b.icon, b.menu_action as menuAction,b.menu_description as menuDescription,b.another_name as anotherName,b.parent_id as parentId,b.id as menuId from nt_sys_role_menu a,nt_sys_menu b where a.role_id in (:roles) and a.menu_id=b.id and a.menu_type_id=:menuTypeId",nativeQuery = true)
	public List<Map<String, String>> findMenuByRoles(@Param("roles") List<String> roles, @Param("menuTypeId") String menuTypeId);
	@Query(value = "select DISTINCT b.menu_name as menuName, b.id, b.icon, b.menu_action as menuAction,b.menu_description as menuDescription,b.another_name as anotherName,b.parent_id as parentId,b.id as menuId from nt_sys_role_menu a,nt_sys_menu b where a.role_id in (:roles) and a.menu_id=b.id and b.parent_id = :parentId and a.menu_type_id=:menuTypeId",nativeQuery = true)
	public List<Map<String, String>> findMenuByRoles(@Param("roles") List<String> roles, @Param("menuTypeId") String menuTypeId, @Param("parentId") String parentId);
	
	@Query(value = "select DISTINCT b.menu_name as menuName, b.id, b.icon, b.menu_action as menuAction,b.menu_description as menuDescription,b.another_name as anotherName,b.parent_id as parentId,b.id as menuId from nt_sys_menu b where b.parent_id = :parentId and b.menu_type_id=:menuTypeId",nativeQuery = true)
	public List<Map<String, String>> findMenu(@Param("menuTypeId") String menuTypeId, @Param("parentId") String parentId);
	
	@Query(value = "select m.* from nt_sys_menu	m, nt_sys_menu_type t where m.menu_type_id=t.id and t.type_code=?1",nativeQuery = true)
	public List<SysMenu> findMenuByTypeCode(String typeCode);
	
}
