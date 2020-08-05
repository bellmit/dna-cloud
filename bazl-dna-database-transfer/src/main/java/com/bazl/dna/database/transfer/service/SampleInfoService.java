package com.bazl.dna.database.transfer.service;


import com.bazl.dna.database.transfer.model.po.*;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface SampleInfoService {

    /**
     * 查询dna库中所有样本的入库类型
     */
     public List<String> selectSampleTypeByCaseId(String caseId);


    /***
     * 根据案件队列ID获取物证列表
     * @param queueId
     * @return
     */
    public List<PhysicalEvidenceModel> selectPhysicalEvidenceByCaseQueueId(String queueId);

    /***
     * 根据案件队列ID获取样本列表
     * @param queueId
     * @return
     */
    public List<SampleInfoModel> selectSampleInfoListByCaseQueueId(String queueId);


    SampleInfoModel selectSampleInfoByBarcode(String barcode);

    InstoreSampleInfo selectInstoreSampleInfoBySampleBarcode(String sampleBarcode);

    String selectSingleRelation(String sampleId);

    List<RelativeSampleInfo> selectRelativeSampleBySampleBarcode(String sampleBarcode);

    List<RelativeSampleInfo> selectRelativeSampleByTargetPersonId(String id);

    List<StrDnaLibView> selectStrDnaLibViewBySampleBarcode(String sampleBarcode);

    List<YstrDnaLibView> selectYstrDnaLibViewBySampleBarcode(String sampleBarcode);

    List<LimsMarkernames> selectStrMarkernames(String geneType);

    RelativeDefModel selectRelationByBarcode(String barcode);

     /**
     * 根据检材编号更新状态和国家库编号
     * @param nationSampleNo
     * @param state
     * @param barcode
     */
    public void updateSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode);

    /**
     * 查询Ystr信息
     * @param startCount
     * @param endCount
     * @return
     */
    public List<YstrDnaLibView> selectYstrDnaLibViewList(int startCount, int endCount);

    /**
     * 查询已经入库的刑事检材列表
     * @param startCount
     * @param endCount
     * @return
     */
    public List<SampleInfoModel> selectInstoreSampleList(int startCount, int endCount);

    /**
     * 根据案件id和人员类型查询人员信息
     * @param caseId
     * @param personType
     * @return
     */
    public List<PersonInfoModel> getMemberList(String caseId, String personType);

    public HttpSampleInfoModel getSampleinfo(String caseId);

    void updateSampleNoAndStateByBarcode2(String nationSampleNo, int nationSampleStateTrue, String sampleLabNo);

    /**
     * 根据队列id查询基因型信息
     * @param queueId
     * @return
     */
    public List<SampleGeneInfoModel> getSampleGeneListByQueueId(String queueId);

    /**
     * 根据队列id查询基因型信息
     * @param queueId
     * @return
     */
    public List<SampleGeneInfoModel> getCriminalSampleGeneListByQueueId(String queueId);

    /**
     * 查询违法犯罪人员信息
     * @param personId
     * @return
     */
    public SampleInfoModel selectCriminalSampleInfoByPersonId(String personId);

    /**
     * 根据实验室编号更新国家库编号
     * @param nationSampleNo
     * @param nationSampleStateTrue
     * @param sampleLabNo
     */
    public void updateCriminalSampleNo(String nationSampleNo, int nationSampleStateTrue, String sampleLabNo);

    /**
     * 获取根据人员id获取检材的入库类型
     * @param personId
     * @return
     */
    public List<String> selectCriminalSampleTypeByPersonId(String personId);
}
