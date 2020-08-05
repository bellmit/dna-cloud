package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysDictItemType;

public interface ISysDictItemTypeJPADao extends JpaRepository<SysDictItemType, String>,JpaSpecificationExecutor<SysDictItemType>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_dict_item_type set del_status='1' where id=?1",nativeQuery=true)
	public int updateByTypeId(String typeId);
	
	@Query(value = "select count(id) as countRow from nt_sys_dict_item_type where id<>?1 and dict_type_code=?2",nativeQuery = true)
	public int countTypeById(String id,String typeCode);
	
	@Query(value = "select * from nt_sys_dict_item_type where dict_type_code=?1",nativeQuery = true)
	public SysDictItemType getDictTypeByTypeCode(String typeCode);
	
	@Query(value = "select * from nt_sys_dict_item_type where dict_type_code in (:codes)",nativeQuery = true)
	public List<SysDictItemType> findListByCode(@Param("codes")List<String> codes);
	
}
