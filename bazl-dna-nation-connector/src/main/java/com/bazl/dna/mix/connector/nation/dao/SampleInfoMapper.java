package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;

import java.util.List;

public interface SampleInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SampleInfo record);

    SampleInfo selectByPrimaryKey(String id);

    List<SampleInfo> selectAll();

    int updateByPrimaryKey(SampleInfo record);

    /**
     * 根据案件id获取以及审核通过的检材
     */
    List<SampleInfo> selectAuditSampleByCaseId(String caseId);
}