package com.bazl.dna.database.transfer.mapper;

import com.bazl.dna.database.transfer.model.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface SampleInfoMapper {

    //查询dna库中所有样本的入库类型
    List<String> selectSampleTypeByCaseId(String caseId);

    List<PhysicalEvidenceModel> selectPhysicalEvidenceByQueueId(String queueId);

    List<SampleInfoModel> selectSampleInfoByQueueId(String queueId);

    List<SampleInfoModel> selectSampleInfoByBarcode(String barcode);

    /**
     * 根据编号查询newlims检材信息
     * @param barcode
     * @return
     */
    public List<SampleInfoModel> selectNewSampleInfoByBarcode(String barcode);

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
     * @param instoreSampleInfo
     */
    public void updateSampleNoAndStateByBarcode(InstoreSampleInfo instoreSampleInfo);

    /**
     * 查询Ystr信息
     * @param offset
     * @param rows
     * @return
     */
    public List<YstrDnaLibView> selectYstrDnaLibViewList(@Param("offset") int offset, @Param("rows") int rows);

    /**
     * 查询已经入库的刑事检材列表
     * @param offset
     * @param rows
     * @return
     */
    public List<SampleInfoModel> selectInstoreSampleList(@Param("offset") int offset, @Param("rows") int rows);

    /**
     * 根据案件id和人员类型查询人员信息
     * @param caseId
     * @param personType
     * @return
     */
    public List<PersonInfoModel> getMemberList(@Param("caseId") String caseId, @Param("personType") String personType);

    HttpSampleInfoModel getSampleinfo(String caseId);

    void updateSampleNoAndStateByBarcode2(InstoreSampleInfo instoreSampleInfo);

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
    public List<SampleInfoModel> selectCriminalSampleInfoByPersonId(String personId);

    /**
     * 根据实验室编号更新国家库编号
     * @param instoreSampleInfo
     */
    public void updateCriminalSampleNo(InstoreSampleInfo instoreSampleInfo);
    /**
     * 获取根据人员id获取检材的入库类型
     * @param personId
     * @return
     */
    public List<String> selectCriminalSampleTypeByPersonId(String personId);
}
