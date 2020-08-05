package com.bazl.dna.sys.dao;

import com.bazl.dna.sys.entity.SysLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ISysLabJPADao extends JpaRepository<SysLab, Integer>, JpaSpecificationExecutor<SysLab> {

    @Query(value = "select  id as id,org_id as orgCode,lab_name as labServerName, server_number as labServerNo from nt_sys_lab ", nativeQuery = true)
    public List<Map<String,String>> findListAll();
    
    @Query(value = "from SysLab where labUser=?1")
    public SysLab getByUser(String userName);
}
