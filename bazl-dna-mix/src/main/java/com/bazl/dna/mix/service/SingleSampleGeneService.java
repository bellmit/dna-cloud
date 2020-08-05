package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface SingleSampleGeneService {

    /**
     * 查询单一样本总数
     * @return
     */
    public int selectSingleSampleGeneCount();

    /**
     * 查看单一样本基因分型及图谱
     * @param geneID
     * @return
     */
    List<SingleSampleGene> selectSingleSampleGeneDetails(String geneID);
    /**
     * 根据id查询单一审核通过基因型信息
     * @param id
     * @return
     */
    public SingleSampleGene selectById(String id);

    /**
     * 修改单一审核通过基因型信息
     * @param singleSampleGene
     */
    public void updateSingleSampleGene(SingleSampleGene singleSampleGene);

    /**
     * 添加单一审核通过基因型信息
     * @param singleSampleGene
     */
    public void insert(SingleSampleGene singleSampleGene);

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
    List<SingleSampleGene> selectSingleSampleGene(String ratiogeneId);

    /**
     * 查询单一样本基因表
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGene> selectSingleSampleGeneAll(int page, int size);

    /**
     * 查询物证信息list
     * @param sampleflag
     * @param caseId
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeCaseIdById(String sampleflag, String caseId, int page, int size);

    /**
     * 1:人员查询物证信息list
     * @param sampleflag
     * @param caseId
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGeneVo> selectPersonTypeCaseIdById(String sampleflag, String caseId, int page, int size);

    /**
     * 提交全库 0：物证信息list
     * @param sampleflag
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeSampleflag(String sampleflag, int page, int size);

    /**
     * 提交全库 0：物证信息list
     * @param sampleflag
     * @return
     */
    List<SingleSampleGeneVo> selectSampleTypeSample(String sampleflag);

    /**
     * 提交本库人员查询物证信息list
     * @param sampleflag
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGeneVo> selectPersonSampleflag(String sampleflag, int page, int size);
    /**
     * 提交本库人员查询物证信息list
     * @param sampleflag
     * @return
     */
    List<SingleSampleGeneVo> selectPersonSample(String sampleflag);

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
