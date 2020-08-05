package com.bazl.dna.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysUserOrgJob;

public interface ISysUserOrgJobJPADao extends JpaRepository<SysUserOrgJob, String>,JpaSpecificationExecutor<SysUserOrgJob>{
	
	@Query(value = "select sysJob.id as jobId, sysJob.jobName as jobName, sysOrganization.id as orgId, sysOrganization.orgCode as orgCode, sysOrganization.orgName as orgName from SysUserOrgJob where sysUser.id=?1 ")
	public List<JSONObject> findUserOrgJobByUserId(String userId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from nt_sys_user_org_job where user_id=?1 ",nativeQuery = true)
	public void deleteUserOrgJobByUserId(String userId);

	@Query(value = "select u.id, u.user_name as userName, u.real_name as realName from nt_sys_user_org_job j, nt_sys_user u where u.id=j.user_id and j.org_id in (?1)", nativeQuery = true)
	public List<Map<String, Object>> findUserByOrgList(List<String> orgList);
}
