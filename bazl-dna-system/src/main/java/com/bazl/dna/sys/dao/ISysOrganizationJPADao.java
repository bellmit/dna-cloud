package com.bazl.dna.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysOrganization;

public interface ISysOrganizationJPADao extends JpaRepository<SysOrganization, String>,JpaSpecificationExecutor<SysOrganization>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_organization set del_status='1' where id=?1",nativeQuery=true)
	public int updateById(String id);
	
	@Query(value = "select id, org_name as orgName, org_code as orgCode from nt_sys_organization where parent_id=?1 order by org_order",nativeQuery = true)
	public List<Map<String,Object>> findListByParentId(String parentId);

	@Query(value = "from SysOrganization where orgCode=?1 ")
	public SysOrganization getByCode(String orgCode);
	
	@Query(value = "from SysOrganization where id=?1 ")
	public SysOrganization get(String id);
	
}
