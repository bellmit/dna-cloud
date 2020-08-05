package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import org.apache.ibatis.annotations.Param;

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

    List<SampleInfo> selectByPrimaryKeys(SampleInfo getSampleInfo);


    List<SampleInfo> selectSelfSampleByConsignmentId(@Param("consignmentId") String consignmentId,
                                                     @Param("selfRelation") String selfRelation,
                                                     @Param("selfPossibleRelation") String selfPossibleRelation);

    /**
     * 根据委托id查询所有的样本列表
     * @param consignmentId
     * @return
     */
    public List<SampleInfo> selectAllSampleByConsignmentId(String consignmentId);
}