package com.bazl.dna.database.nation.converter.service;

import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleInfoService {

    /**
     * 根据委托id查询所有的对象自身样本列表
     * @param consignmentId
     * @return
     */
    public List<SampleInfo> selectSelfSampleByConsignmentId(String consignmentId);

    /**
     * 根据委托id查询所有的样本列表
     * @param consignmentId
     * @return
     */
    public List<SampleInfo> selectAllSampleByConsignmentId(String consignmentId);

    /**
     * 根据案件id获取审核通过的检材
     */
    List<SampleInfo> selectAuditSampleByCaseId(String caseId);
    
    SampleInfo selectByPrimaryKey(String id);
}
