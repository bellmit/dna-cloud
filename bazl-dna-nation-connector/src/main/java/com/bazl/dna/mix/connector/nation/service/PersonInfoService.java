package com.bazl.dna.mix.connector.nation.service;

import com.bazl.dna.mix.connector.nation.model.po.PersonInfo;
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
}
