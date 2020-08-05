package com.bazl.dna.lims.dao;


import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.PersonDetail;
import com.bazl.dna.lims.model.vo.LimsPersonInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimsPersonInfoMapper {

    void insertPersonDetail(PersonDetail personDetail);

    void insertPersonInfo(LimsPersonInfo limsPersonInfo);

    PersonDetail selectPersonDetailById(String personDetailId);

    LimsPersonInfo selectPersonInfoById(String personId);

    void updatePersonInfo(LimsPersonInfo limsPersonInfo);

    List<LimsPersonInfo> selectByCaseId(String caseId);

    void deleteByCaseIdAndConsignmentId(LimsPersonInfo limsPersonInfo);

    List<LimsPersonInfo> selectByCaseIdAndConsignmentId(LimsPersonInfo limsPersonInfo);

    void deleteByPersonId(String personId);

    List<LimsPersonInfo> selectListByConsignmentId(String consignmentId);

    /**
     * 更加案件id查询人员信息
     * @param caseId
     * @return
     */
    public List<LimsPersonInfo> selectAuditByCaseId(String caseId);

    /**
     * 根据检材编号获取人员信息
     * @param sampleNo
     * @return
     */
    public List<LimsPersonInfo> selectPersonInfoBySampleNo(String sampleNo);


    /**
     * 混合库获取案件人员信息
     * @return
     */
    List<LimsPersonInfo> selectPersonInfoListData(LimsPersonInfoVo query);
}