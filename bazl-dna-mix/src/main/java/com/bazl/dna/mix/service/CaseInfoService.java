package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.CaseInfo;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface CaseInfoService {

    /**
     * 根据案件id查询案件信息
     * @return
     */
    public CaseInfo selectByCaseId(String caseId);

    /**
     * 修改案件信息
     * @param caseInfo
     */
    public void updateCaseInfo(CaseInfo caseInfo);

    /**
     * 添加案件信息
     * @param caseInfo
     */
    public void insertCaseInfo(CaseInfo caseInfo);
}
