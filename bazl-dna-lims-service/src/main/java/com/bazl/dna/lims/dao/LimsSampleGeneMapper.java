package com.bazl.dna.lims.dao;

import java.util.List;

import com.bazl.dna.lims.model.po.LimsSampleGene;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.model.vo.LimsSampleGeneVo;

/**
 * @author wanghaiyang
 * @date 2019/2/26.
 */
public interface LimsSampleGeneMapper {

    /**
     * 根据geneId删除基因型信息
     * @param geneId
     * @return
     */
    public int deleteByPrimaryKey(String geneId);

    /**
     * 插入基因型信息
     * @param record
     * @return
     */
    public int insert(LimsSampleGene record);

    /**
     * 根据geneId查询基因型信息
     * @param geneId
     * @return
     */
    public LimsSampleGene selectByPrimaryKey(String geneId);

    /**
     * 查询所有的基因型信息
     * @return
     */
    public List<LimsSampleGene> selectAll();

    /**
     * 更新基因型信息
     * @param record
     * @return
     */
    public int updateByPrimaryKey(LimsSampleGene record);

    /**
     * 根据caseId查询基因型信息
     * @param caseId
     * @return
     */
    public List<LimsSampleGene> selectListByCaseId (String caseId);

    /**
     * 根据条件查询基因型信息
     * @param limsSampleGeneVo
     * @return
     */
    public List<LimsSampleGeneVo> selecVOtList (LimsSampleGeneVo limsSampleGeneVo);


    /**
     * 根据sampleId更新基因型信息
     * @param sampleId
     * @return
     */
    public int updateBySampleId(String sampleId);

    /**
     * 根据条件更新基因型审核状态
     * @param limsSampleGene
     * @return
     */
    public void updateAuditStatus(LimsSampleGene limsSampleGene);

    /**
     * 删除标记
     * @param limsSampleGene
     */
    public void updateDeleteFlag(LimsSampleGene limsSampleGene);

    /**
     * 更新复检状态
     * @param limsSampleGene
     */
    public void updateReviewStatus(LimsSampleGene limsSampleGene);

    public List<LimsSampleGene> selectAuditedBySampleId(String sampleId);

    /**
     * 根据检材id查询检材信息
     * @param sampleId
     * @return
     */
    public List<LimsSampleGene> selectListBySampleId(String sampleId);

    /**
     * 根据分析id查询基因型信息
     * @param analysisInfoId
     * @return
     */
    public List<LimsSampleGene> selectListByAnalysisInfoId(String analysisInfoId);

    //混合库查数据
    public List<LimsSampleGene> selectByGeneList(LimsSampleGene limsSampleGene);
    
    /**
     * 查询检出数（统计）
     * @param limsCaseInfoVo
     * @return
     */
   int selectDetectionGeneCountByOrgCode(LimsCaseInfoVo limsCaseInfoVo);
   
   /**
    * 根据样本编号查询lims库样本的基因型
    * @param sampleNo
    * @return
    */
   List<LimsSampleGeneVo> selectSampleGeneBySampleNo(String sampleNo);

   /**
    * 通过样本编号查询alims基因型id
    * @param sampleNo
    * @return
    */
   List<LimsSampleGeneVo> selectGeneIdBySampleNo(String sampleNo);

   /**
    * 更新lims库基因型信息
    * @param limsSampleGeneVo
    */
   void updateMarkerallel(LimsSampleGeneVo limsSampleGeneVo);
   
   /**
    * 更新Alims库基因型信息
    * @param limsSampleGeneVo
    */
   void updateGeneInfo(LimsSampleGeneVo limsSampleGeneVo);
   
}