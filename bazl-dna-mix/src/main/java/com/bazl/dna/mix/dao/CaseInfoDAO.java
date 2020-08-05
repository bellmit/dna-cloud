package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.CaseInfo;

public interface CaseInfoDAO {
    int insert(CaseInfo record);

    int insertSelective(CaseInfo record);

    /**
     * lims根据案件id查询案件信息
     * @param caseId
     * @return
     */
    CaseInfo selectByCaseId(String caseId);

    void updateCaseInfo(CaseInfo caseInfo);

}