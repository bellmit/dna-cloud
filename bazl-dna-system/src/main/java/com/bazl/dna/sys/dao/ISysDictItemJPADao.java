package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.sys.entity.SysDictItem;

public interface ISysDictItemJPADao extends JpaRepository<SysDictItem, String>,JpaSpecificationExecutor<SysDictItem>{
	
	@Query(value = "select * from nt_sys_dict_item where  del_status = 0 and dict_item_type_id=?1",nativeQuery = true)
	public List<SysDictItem> findItemByTypeId(String typeId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update nt_sys_dict_item set del_status='1' where id=?1",nativeQuery=true)
	public int updateByItemId(String itemId);

}
