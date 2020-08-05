package com.bazl.dna.mix.connector.nation.service;


import com.bazl.dna.mix.connector.nation.model.po.CaseInfo;
import com.bazl.dna.mix.connector.nation.model.vo.CaseInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CaseInfoService {

    /**
     * 混合库获取案件信息
     * @param page
     * @return
     */
    List<CaseInfo> selectCaseInfoListData(CaseInfoVo caseInfoVo, Integer page);


    /**
     * 根据案件编号获取信息
     * @param caseNo
     * @return
     */
    public CaseInfo selectByCaseNo(String caseNo);
    //根据案件id查询案件信息
    CaseInfo selectByCaseId(String caseId);
    //根据样本id获取
    CaseInfo selectBySampleId(String sampleId);
}
