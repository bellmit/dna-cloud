package com.bazl.dna.lims.connector.dao;


import com.bazl.dna.lims.connector.model.po.LimsSampleGene;
import com.bazl.dna.lims.connector.model.vo.LimsSampleGeneVo;

import java.util.List;

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
    public List<LimsSampleGeneVo> selectListVoByCaseId(String caseId);


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
     */
    public LimsSampleGeneVo selectVoListByGeneId(String geneId);

}