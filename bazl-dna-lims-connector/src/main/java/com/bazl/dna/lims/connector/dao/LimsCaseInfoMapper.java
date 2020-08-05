package com.bazl.dna.lims.connector.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.connector.model.po.LimsCaseInfo;

@Repository
public interface LimsCaseInfoMapper {

    void insertCaseInfo(LimsCaseInfo caseInfo);

    void deleteCaseInfo(LimsCaseInfo caseInfo);

    LimsCaseInfo selectByCaseId(String caseId);

    void updateCaseInfoDna(LimsCaseInfo limsCaseInfo);

    /**
     * 根据案件编号查询案件信息
     * @param caseNo
     * @return
     */
    public List<LimsCaseInfo> selectByCaseNo(String caseNo);

    /**
     * 查询已入库的所有案件
     */
    List<LimsCaseInfo> selectAllCase();

    public LimsCaseInfo selectByConsignmentId(String consignmentId);
}