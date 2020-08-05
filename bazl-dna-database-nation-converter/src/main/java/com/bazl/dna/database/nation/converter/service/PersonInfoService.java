package com.bazl.dna.database.nation.converter.service;

import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonInfoService {

    /**
     * 根据案件id查询人员信息
     * @param caseId
     * @return
     */
    public List<PersonInfo> selectAuditPersonByCaseId(String caseId);


    /**
     * 根据委托id查询真实的人员列表（排除掉物证对象）
     * @param consignmentId
     * @return
     */
    public List<PersonInfo> selectRealPersonByConsignmentId(String consignmentId);

    /**
     * 根据委托id查询全部的人员对象列表
     * @param consignmentId
     * @return
     */
    public List<PersonInfo> selectAllPersonByConsignmentId(String consignmentId);
}
