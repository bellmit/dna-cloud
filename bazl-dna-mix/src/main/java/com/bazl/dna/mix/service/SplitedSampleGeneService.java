package com.bazl.dna.mix.service;

import java.util.List;

import com.bazl.dna.mix.model.po.SplitedSampleGene;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface SplitedSampleGeneService {

    int deleteByPrimaryKey(String id);

    SplitedSampleGene selectByPrimaryKey(String id);

    /**
     * 查询已拆分样本总数
     * @return
     */
    public int selectSplitedSampleGeneCount();

    /**
     * 入库拆分单一基因信息
     * @param splitedSampleGene
     */
    public int insert(SplitedSampleGene splitedSampleGene);

    /**
     * 根据混合id查询拆分基因表
     * @param mixedSampleGeneId
     * @return
     */
    public List<SplitedSampleGene> selectSplitedSampleGeneByMixedSampleGeneId(String mixedSampleGeneId);

    /**
     * 修改拆分基因信息
     * @param splitedSampleGene
     */
    void update(SplitedSampleGene splitedSampleGene);

    /**
     * 查询拆分样本基因信息
     * @return
     * @param page
     * @param size
     */
    List<SplitedSampleGene> selectAll(int page, int size);

    /**
     * 根据拆分id查询拆分基因表信息
     * @param sampleGeneId
     * @return
     */
    List<SplitedSampleGene> selectSplitedSampleGeneList(String sampleGeneId);

    /*
    *   根据队列id查询
    * */
    SplitedSampleGene selectByCompareQueueId(String compareQueueId);
}
