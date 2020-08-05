package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SingleSampleGeneDAO {
    int insert(SingleSampleGene record);

    int insertSelective(SingleSampleGene record);

    /**
     * 查询单一基因总数
     * @return
     */
    int selectSingleSampleGeneCount();

    /**
     * 根据id查询单一审核通过基因型信息
     * @param id
     * @return
     */
    SingleSampleGene selectById(String id);

    void updateSingleSampleGene(SingleSampleGene singleSampleGene);

    /**
     * 查看单一样本基因分型及图谱
     * @param geneID
     * @return
     */
    List<SingleSampleGene> selectSingleSampleGeneDetails(String geneID);

    /**
     * 根据案件id查询单一样本
     * @param caseId
     * @return
     */
    List<SingleSampleGeneVo> selectSingleSampleGeneList(String caseId);

    /**
     * 查询单一样本信息
     * @param id
     * @return
     */
    List<SingleSampleGeneVo> selectSingleSampleGeneVoList(String id);

    /**
     * 根据比中id查询单一样本
     * @param ratiogeneId
     * @return
     */
    List<SingleSampleGene> selectSingleSampleGeneBySampleNo(String ratiogeneId);

    /**
     * 查询单一样本基因表
     * @return
     * @param startRum
     * @param endRum
     */
    List<SingleSampleGene> selectSingleSampleGeneAll(@Param("startRum") int startRum, @Param("endRum") int endRum);

    /**
     * 提交本案查询物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeCaseIdById(Map<String, Object> map);

    /**
     * 人员查询物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectPersonTypeCaseIdById(Map<String, Object> map);

    /**
     * 根据案件id和单一id查询单一样本信息
     * @param id
     * @return
     */
    SingleSampleGeneVo selectSingleById(@Param("id") String id);


    SingleSampleGeneVo selectSampleTypeGenneIdByIds(String singlegeneId);

    /**
     * 提交全库物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeSampleflag(Map<String, Object> map);
    /**
     * 提交全库物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeSample(String sampleflag);

    /**
     * 提交本库人员查询物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectPersonSampleflag(Map<String, Object> map);
    /**
     * 提交本库人员查询物证信息list
     * @return
     */
    List<SingleSampleGeneVo> selectPersonSample(String sampleflag);

    /**
     * 提交本案查询物证信息list
     * @param sampleflag
     * @param caseId
     * @param startRum
     * @param endRum
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeCaseIdById(@Param("sampleflag") String sampleflag, @Param("caseId") String caseId, @Param("startRum") int startRum, @Param("endRum") int endRum);

    /**
     * 人员查询物证信息list
     * @param sampleflag
     * @param caseId
     * @param startRum
     * @param endRum
     * @return
     */
    List<SingleSampleGeneVo> selectPersonTypeCaseIdById(@Param("sampleflag") String sampleflag, @Param("caseId") String caseId, @Param("startRum") int startRum, @Param("endRum") int endRum);

    /**
     * 查询比对结果
     * @return
     */
    List<SingleSampleGeneVo> selectComparisonAll(SingleSampleGeneVo qurey);

    /**
     * 根据检材id查询单一信息
     * @param sampleId
     * @return
     */
    SingleSampleGene selectBySampleId(String sampleId);

}