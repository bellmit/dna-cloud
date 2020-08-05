package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonInfo record);

    PersonInfo selectByPrimaryKey(PersonInfo getPersonInfo);

    List<PersonInfo> selectAll();

    int updateByPrimaryKey(PersonInfo record);

    /**
     * 根据案件id查询人员信息
     * @param caseId
     * @return
     */
    List<PersonInfo> selectAuditPersonByCaseId(String caseId);


    List<PersonInfo> selectRealPersonByConsignmentId(@Param("consignmentId") String consignmentId,
                                                     @Param("selfRelation") String selfRelation,
                                                     @Param("selfPossibleRelation") String selfPossibleRelation);

    List<PersonInfo> selectAllPersonByConsignmentId(String consignmentId);

}