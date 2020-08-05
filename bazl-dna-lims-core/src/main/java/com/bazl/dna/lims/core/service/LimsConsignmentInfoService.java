package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.bo.DelegateDataModel;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LimsConsignmentInfoService {

    public void delCaseAndBring(String consignmentId, String caseId, LoaUserInfo operateUser);

    public LimsConsignmentInfo selectByConsignmentId(String consignmentId);

    public Map<String, String> submitNonDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIds, String evaluationCenterId);

    /**
     * 根据案件id查询委托信息
     * @param caseId
     * @return
     */
    public List<LimsConsignmentInfo> selectByCaseId(String caseId);

    /**
     * 根据委托书编号判断是否重复
     * @param consignmentNo
     * @return
     */
    LimsConsignmentInfo selectByConsignmentNo(String consignmentNo);

    LimsConsignmentInfo getMainConsignmentByCaseId(String caseId);

    /**
     * 根据案件编号查询委托信息
     * @param caseNo
     * @return
     */
    public LimsConsignmentInfo selectByCaseNo(String caseNo);
}