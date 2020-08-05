package com.bazl.dna.lims.core.dao;


import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.core.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.core.model.vo.LimsSampleInfoDnaVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface LimsSampleInfoDnaMapper {

    void insertSampleInfoDna(LimsSampleInfoDna sampleInfoDna);

    LimsSampleInfoDna selectSampleInfoDnaById(String sampleId);

    void updateSampleInfoDna(LimsSampleInfoDna sampleInfoDna);

    LinkedList<LimsSampleInfoDna> selectByCaseIdAndRy(LimsSampleInfoDna sampleInfoDna);

    LinkedList<LimsSampleInfoDna> selectByCaseIdAndWz(LimsSampleInfoDna sampleInfoDna);

    List<LimsSampleInfoDna> selectChuanBinBySampleId(LimsSampleInfoDnaVo limsSampleInfoDnaVo);

    int selectSampleCountByCaseId(LimsSampleInfoDna sampleInfoDna);

    void deleteByCaseIdAndConsignmentId(LimsSampleInfoDna sampleInfoDna);

    void deleteBySampleId(String sampleId);

    LimsSampleInfoDna selectById(String sampleId);

    List<LimsSampleInfoDna> selectByCaseId(String caseId);

    List<LimsSampleInfoDna> selectAcceptedByCaseId(String caseId);

    List<LimsSampleInfoDna> selectListByConsignmentId(String consignmentId);

    List<LimsSampleInfoDna> selectsampleListRefuseConsignmentId(String consignmentId);

    List<LimsSampleInfoDna> selectPersonListRefuseConsignmentId(String consignmentId);

    int selectCount(LimsCaseInfoVo query);

    int selectNotCount(@Param("acceptOrgId")String acceptOrgId);

    public List<HashMap<String, String>> selectCountGroupSampleType(@Param("acceptOrgId")String acceptOrgId);

    public String selectMaxSampleNoByCaseId(String caseId);

    List<LimsSampleInfoDna> selectByCaseNo(String caseNo);

    List<LimsSampleInfoDna> selectBySampleNo(String sampleNo);

    List<LimsSampleInfoDna> selectBySampleNameAndConsignmentId(@Param("sampleName")String sampleName, @Param("consignmentId")String consignmentId);
    /**
     * 文书打印-检材流转专用方法
     * @param consignmentId
     * @return
     */
    List<LimsSampleInfoDna> selectSampleListByConsignmentId(String consignmentId);

    /**
     * 根据案件id以及委托id查询样本信息分页
     * @param limsSampleInfoDna
     * @return
     */
    List<LimsSampleInfoDnaVo> selectByCaseIdAndRyPagination(LimsSampleInfoDnaVo limsSampleInfoDna);

    /**
     * 根据案件id以及委托id查询样本信息count
     * @param LimsSampleInfoDna
     * @return
     */
    int selectByCaseIdAndRyCount(LimsSampleInfoDnaVo LimsSampleInfoDna);

    /**
     * 根据条件查询信息
     * @param limsSampleInfoDna
     * @return
     */
    public List<LimsSampleInfoDna> selectList(LimsSampleInfoDna limsSampleInfoDna);

    /**
     * 根据条件查询信息
     * @param limsSampleInfoDnaVo
     * @return
     */
    public List<LimsSampleInfoDnaVo> selectVoList(LimsSampleInfoDnaVo limsSampleInfoDnaVo);

    public void updateInstoredFlag(LimsSampleInfoDna sampleInfo);

    /**
     * 根据案件条件查询检材信息
     * @param caseId
     * @return
     */
    public List<LimsSampleInfoDnaVo> selectNotDetected(String caseId);

    public List<LimsSampleInfoDnaVo> selectSampleInfos(String caseId);

    /**
     * 根据委托id
     * @param consignmentId
     * @return
     */
    public List<LimsSampleInfoDna> selectByConsignmentId(String consignmentId);

    /**
     * 根据检材id获取检材数据
     * @param sampleId
     * @return
     */
    LimsSampleInfoDna selectBySampleId(String sampleId);

    /**
     * 获取审核基因通过的检材
     * @param caseId
     * @return
     */
    List<LimsSampleInfoDna> selectSampleDnaByCaseId(String caseId);

    /**
     * 根据编号查询已经入库的检材信息
     * @param sampleNo
     * @return
     */
    public List<LimsSampleInfoDna> selectBySampleNoAlreadyStorage(String sampleNo);

    /**
     * 判断检材是否入库
     * @param sampleId
     * @return
     */
    public LimsSampleInfoDna isStoredBySampleId(String sampleId);

    /**
     * 根据案件id查询已经上报国家库检材信息
     * @param caseId
     * @return
     */
    public List<LimsSampleInfoDna> selectListByCaseIdInstored(String caseId);

    /**
     * 根据案件id查询物证检材
     * @param caseId
     * @return
     */
    List<LimsSampleInfoDna> selectMatchCaseId(String caseId);

    /**   统计受理检材数*/
    int selectAcceptCount(LimsCaseInfoVo query);
    /*
   *   统计复核检材数
   * */
    int selectAuditCount(LimsCaseInfoVo query);
    /*
   *   统计已入库检材数
   * */
    int selectInstoredSampleCount(LimsCaseInfoVo query);
    /**
     * 查询受理检材
     * @return
     */
    List<LimsSampleInfoDna> selectAccept(LimsCaseInfoVo query);

    /**
     * 根据案件id获取以及审核通过的检材
     */
    public List<LimsSampleInfoDna> selectAuditSampleByCaseId(String caseId);

    /**
     * 混合库获取检材信息
     * @return
     */
    public List<LimsSampleInfoDna> selectSampleInfoListData(LimsSampleInfoDnaVo query);

    /**
     * 更新预实验记录
     * @param sampleInfoDna
     * @return
     */
    int updatePre(LimsSampleInfoDna sampleInfoDna);

    /**
     * 根据物证编号查询检材信息
     * @param evidenceNo
     * @return
     */
    List<LimsSampleInfoDna> selectCaseInfoListData(String evidenceNo);

    int updateSamples(LimsSampleInfoDna limsSampleInfoDna);
}