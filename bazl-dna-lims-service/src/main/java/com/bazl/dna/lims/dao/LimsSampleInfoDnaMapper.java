package com.bazl.dna.lims.dao;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.model.vo.LimsSampleInfoDnaVo;

@Repository
public interface LimsSampleInfoDnaMapper {
	
	/**
     * 根据案件编号查询样本条数
     * @param caseNo
     * @return
     */
    int selectSampleCountByCaseNo(String caseNo);

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

    List<LimsSampleInfoDna> selectListByConsignmentId(String consignmentId);

    List<LimsSampleInfoDna> selectsampleListRefuseConsignmentId(String consignmentId);

    List<LimsSampleInfoDna> selectPersonListRefuseConsignmentId(String consignmentId);

    int selectCount(LimsCaseInfoVo query);

    int selectNotCount(@Param("acceptOrgId")String acceptOrgId);

    public List<HashMap<String, String>> selectCountGroupSampleType(@Param("acceptOrgId")String acceptOrgId);

    public String selectMaxSampleNoByCaseId(String caseId);
    
    List<LimsSampleInfoDna> selectYbByCaseId(String caseId);

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
    List<LimsSampleInfoDna> selectBySampleId(String sampleId);

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
    
    /**
     * 根据样本编号删除dnadata库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除dcdna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteDcdnaSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除cydna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteCydnaSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除ftdna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteFtdnaSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除sjsdna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteSjsdnaSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除hddna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteHddnaSampleBySampleNo(String sampleNo);
    /**
     * 根据样本编号删除tzdna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteTZdnaSampleBySampleNo(String sampleNo);

    /**
     * 根据样本编号删除cpdna库的入库样本
     * @param sampleNo
     * @return
     */
    void deleteCpdnaSampleBySampleNo(String sampleNo);

    /**
     * 检材送检单位统计
     * @param limsCaseInfoVo
     * @return
     */
    Map<String, Object> selectSampleTypeCountByOrgCode(LimsCaseInfoVo limsCaseInfoVo);

    /**
     * 查询送检数（统计）
     * @param limsCaseInfoVo
     * @return
     */
    int selectDelegateCountByOrgCode(LimsCaseInfoVo limsCaseInfoVo);

    /*
     *   查询各分局检材数量
     * */
    List<CasePropertyStatsVo> selectSampleStatsByOrg(Map<String, Object> queryParams);
    
    /**
     * 根据样本编号查询dnadata库的入库样本
     * @param sampleNo
     * @return
     */
    List<LimsSampleInfoDna> selectSampleBySampleNoList(String sampleNo);
    /**
     * 根据样本编号查询lims库的样本信息
     * @param sampleNo
     * @return
     */
    List<LimsSampleInfoDna> selectLimsSampleBySampleNoList(String sampleNo);
    
    /**
     * 更新样本信息（lims）
     * @param limsSampleInfoDna
     * @return
     */
    int updateLimsSampleInfo(LimsSampleInfoDna limsSampleInfoDna);
    /**
     * 更新样本信息（alims）
     * @param limsSampleInfoDna
     * @return
     */
    int updateALimsSampleInfo(LimsSampleInfoDna limsSampleInfoDna);
    /**
     * 更新样本信息（dnadata）
     * @param limsSampleInfoDna
     * @return
     */
    int updateDnadataSampleInfo(LimsSampleInfoDna limsSampleInfoDna);
    
    //查询非现场案件检材
    List<LimsSampleInfoDna> selectByCaseIdAndWzNon(LimsSampleInfoDna sampleInfoDna);
    
    /**
     * 查询物证检材样本数
     */
    public int selectCountByOrgId(String orgId);
}