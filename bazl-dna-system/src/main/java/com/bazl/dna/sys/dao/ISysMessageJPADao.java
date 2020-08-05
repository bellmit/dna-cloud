package com.bazl.dna.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bazl.dna.sys.entity.SysMessage;

/**
 * 消息接口
 */
public interface ISysMessageJPADao extends JpaRepository<SysMessage, String>, JpaSpecificationExecutor<SysMessage> {

	@Query(value = "from SysMessage where messageType=?1")
	public List<SysMessage> findList(String messageType);

}
