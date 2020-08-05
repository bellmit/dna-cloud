package com.bazl.dna.lims.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.po.LimsConsignmentInfo;

@Repository
public interface LimsConsignmentInfoMapper {

    void insertConsignatioInfo(LimsConsignmentInfo consignatioInfo);

    void deleteConsignationInfo(LimsConsignmentInfo consignatioInfo);

    LimsConsignmentInfo selectByConsignmentId(String consignmentId);

    void updateConsignatioInfo(LimsConsignmentInfo limsConsignmentInfo);

    /**
     * 根据案件id查询委托信息
     * @param caseId
     * @return
     */
    public List<LimsConsignmentInfo> selectByCaseId(String caseId);

    /**
     * 根据编号查重
     * @param consignmentNo
     * @return
     */
    public LimsConsignmentInfo selectByConsignmentNo(String consignmentNo);

    public LimsConsignmentInfo getCaseidByAppendflag(String caseId);

    /**
     * 根据案件编号查询委托信息
     * @param caseNo
     * @return
     */
    public List<LimsConsignmentInfo> selectByCaseNo(String caseNo);
    
    public LimsConsignmentInfo selectByConsignmentNo(@Param("consignmentNo")String consignmentNo, @Param("delegateOrgCode")String delegateOrgCode);
    
    //查询补送第几次数
    LimsConsignmentInfo selectMaxReplacementNum(String caseId);
    //更新补送次数
    void updateReplacementNum(LimsConsignmentInfo limsConsignmentInfo);

    List<LimsConsignmentInfo> selectByCaseXkNoTwo(String caseXkNo);
}