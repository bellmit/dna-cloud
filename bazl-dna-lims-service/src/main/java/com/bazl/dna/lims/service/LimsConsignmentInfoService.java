package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.bo.DelegateDataModel;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LimsConsignmentInfoService {
	
	public Map<String, String> submitDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds, String evaluationCenterId);

    public void delCaseAndBring(String consignmentId, String caseId, LoaUserInfo operateUser);

    public LimsConsignmentInfo selectByConsignmentId(String consignmentId);

    public Map<String, String> submitNonDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIds, String evaluationCenterId, String sampleIdWzs);

    /**
     * 保存在逃委托信息
     * @param delegateDataModel
     * @param operateUser
     * @param personIds
     * @param sampleIds
     * @param evaluationCenterId
     * @param sampleIdWzs
     * @return
     */
    public Map<String, String> submitFugitivesDelegate(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIds, String evaluationCenterId, String sampleIdWzs, String appendFlag);

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

    LimsConsignmentInfo getCaseidByAppendflag(String caseId);

    /**
     * 根据案件编号查询委托信息
     * @param caseNo
     * @return
     */
    public LimsConsignmentInfo selectByCaseNo(String caseNo);
    
    List<LimsConsignmentInfo> selectByCaseXkNoTwo(String caseXkNo);
    
    public LimsConsignmentInfo selectByConsignmentNo(String consignmentNo, String delegateOrgCode);
}