package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.mixContributorBean;
import com.bazl.dna.mix.model.vo.CompareQueueVo;

import java.util.List;

public interface CompareQueueMapper {
    int deleteByPrimaryKey(String id);

    int insert(CompareQueue record);

    CompareQueue selectByPrimaryKey(String id);

    List<CompareQueue> selectAll();

    int updateByPrimaryKey(CompareQueue record);

    /*
    *   条件查询
    * */
    List<CompareQueue> selectQueueList(CompareQueue compareQueue);

    List<CompareQueue> selectBySampleNo(String sampleNo);

    List<CompareQueue>  selectByMixedSampleId(CompareQueue compareQueue);

    /*
   *   条件查询  入库时间查询
   * */
    List<CompareQueue>  selectByQueueVoList(CompareQueueVo query);
    //查询条数
    int  selectByQueueVoCount(CompareQueueVo query);

    /*
    *   比对定时任务
    * */
    List<CompareQueue> selectCompareQueueList(String queueType);

    /*
     *   比对定时任务
     * */
    List<CompareQueue> selectCompareQueueAlloList();


    String selectCompareQueueGeneInfo(String id);

    List<String> contributorGene(String id);

    mixContributorBean getMixSampleInfo(String id);
}