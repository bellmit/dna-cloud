package com.bazl.dna.mix.connector.nation.dao;


import com.bazl.dna.mix.connector.nation.model.po.CaseInfo;
import com.bazl.dna.mix.connector.nation.model.vo.CaseInfoVo;

import java.util.List;

public interface CaseInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseInfo record);

    CaseInfo selectByPrimaryKey(String id);

    List<CaseInfo> selectAll();

    int updateByPrimaryKey(CaseInfo record);


    /**
     * 混合库获取案件信息
     * @return
     */
    public List<CaseInfo> selectCaseInfoListData(CaseInfoVo query);


    /**
     * 根据案件编号查询案件信息
     * @param caseNo
     * @return
     */
    public List<CaseInfo> selectByCaseNo(String caseNo);
    //根据案件id查询案件信息
    CaseInfo selectByCaseId(String caseId);
    //根据样本id获取案件信息
    CaseInfo selectBySampleId(String sampleId);
}