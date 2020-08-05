package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysMenuType;

public interface ISysMenuTypeJPADao extends JpaRepository<SysMenuType, String>,JpaSpecificationExecutor<SysMenuType>{
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_menu_type set status=?2 where id=?1",nativeQuery=true)
	public int updateById(String typeId,String status);
	
	@Query(value = "select count(id) as countRow from nt_sys_menu_type where id<>?1 and type_code=?2",nativeQuery = true)
	public int countTypeById(String id,String typeCode);
	
	@Query(value = "select * from nt_sys_menu_type where type_code=?1",nativeQuery = true)
	public SysMenuType getMenuTypeByTypeCode(String typeCode);
}
