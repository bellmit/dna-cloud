package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bazl.dna.sys.entity.SysLabUser;

public interface ISysLabUserJPADao extends JpaRepository<SysLabUser, String>, JpaSpecificationExecutor<SysLabUser> {

}
