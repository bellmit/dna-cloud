package com.bazl.dna.mix.connector.nation.service;

import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleInfoService {

    /**
     * 根据案件id获取审核通过的检材
     */
    List<SampleInfo> selectAuditSampleByCaseId(String caseId);
    
    SampleInfo selectByPrimaryKey(String id);
}
