package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bazl.dna.sys.entity.SysUser;

public interface ISysUserJPADao extends JpaRepository<SysUser, String>,JpaSpecificationExecutor<SysUser>{
	
	@Query(value = "select * from nt_sys_user where user_name=?1 and password=?2 and status='1'",nativeQuery = true)
	public SysUser getUserByUserName(String userName,String password);
}
