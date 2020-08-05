package com.bazl.dna.sys.dao;

import java.util.List;

import com.bazl.dna.sys.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ISysRoleJPADao extends JpaRepository<SysRole, String>,JpaSpecificationExecutor<SysRole>{
	
	@Query(value = "select * from nt_sys_role where status=?1",nativeQuery = true)
	public List<SysRole> findRoleList(String status);
	
	@Query(value = "select count(id) as countRow from nt_sys_role where id<>?1 and role_code=?2",nativeQuery = true)
	public int countCodeById(String id,String code);
}
