package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.PersonInfo;

import java.util.List;

public interface PersonInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonInfo record);

    PersonInfo selectByPrimaryKey(String id);

    List<PersonInfo> selectAll();

    int updateByPrimaryKey(PersonInfo record);

    /**
     * 根据案件id查询人员信息
     * @param caseId
     * @return
     */
    List<PersonInfo> selectAuditPersonByCaseId(String caseId);
}