package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysJob;

public interface ISysJobJPADao extends JpaRepository<SysJob, String>,JpaSpecificationExecutor<SysJob>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_job set del_status='1' where id=?1",nativeQuery=true)
	public int updateById(String id);
}
