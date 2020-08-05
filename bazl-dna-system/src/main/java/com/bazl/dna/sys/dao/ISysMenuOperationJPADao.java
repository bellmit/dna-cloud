package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bazl.dna.sys.entity.SysMenuOperation;

public interface ISysMenuOperationJPADao extends JpaRepository<SysMenuOperation, String>,JpaSpecificationExecutor<SysMenuOperation>{
	
	
	@Query(value = "select count(id) as countRow from nt_sys_operation where id<>?1 and operation_type=?2",nativeQuery = true)
	public int countTypeById(String id,String typeCode);
	
	
}
