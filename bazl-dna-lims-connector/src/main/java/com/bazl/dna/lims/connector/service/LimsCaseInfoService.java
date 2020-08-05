package com.bazl.dna.lims.connector.service;

import com.bazl.dna.lims.connector.model.po.LimsCaseInfo;

public interface LimsCaseInfoService {

    LimsCaseInfo selectByCaseId(String caseId);

    public LimsCaseInfo selectByConsignmentId(String consignmentId);

    /**
     * 根据案件编号获取信息
     * @param caseNo
     * @return
     */
    public LimsCaseInfo selectByCaseNo(String caseNo);

    
}