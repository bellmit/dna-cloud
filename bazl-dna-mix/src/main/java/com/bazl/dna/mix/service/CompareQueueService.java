package com.bazl.dna.mix.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.mixContributorBean;
import com.bazl.dna.mix.model.vo.CompareQueueVo;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface CompareQueueService {

    void insertCompareQueue(CompareQueue compareQueue);

    /**
     * 根据混合样本id查询比对队列
     * @param compareQueue
     * @return
     */
    List<CompareQueue> selectBySampleId(CompareQueue compareQueue);

    /**
     * 查询比对队列表中未比对的数据
     * @param status
     *
     */
    List<CompareQueue> selectNotMixedSampleGeneCompareQueue(String status, String queueType, int page, int size);

    /**
     * 更改比对状态
     * @param compareQueue
     */
    void updateStatus(CompareQueue compareQueue);

    /**
     * 通过匹配下线查询拆分信息
     */
    List<CompareQueue> selectByMixSameCount(CompareQueue compareQueue);



//=======================新加==================================
    int deleteByPrimaryKey(String id);

    int insert(CompareQueue record);

    CompareQueue selectByPrimaryKey(String id);

    List<CompareQueue> selectAll();

    int updateByPrimaryKey(CompareQueue record);

    /*
    *   条件查询
    * */
    List<CompareQueue> selectQueueList(CompareQueue compareQueue);

    void addLocusListInfoForkitId(CompareQueue compareQueue);

    List<CompareQueue> selectBySampleNo(String sampleNo);

    List<CompareQueue>  selectByMixedSampleId(CompareQueue compareQueue);

    /*
    *   条件查询  入库时间查询
    * */
    List<CompareQueue>  selectByQueueVoList(CompareQueueVo query, PageInfo pageInfo);
    //查询条数
    int  selectByQueueVoCount(CompareQueueVo query);

    /*
    *   比对定时任务
    * */
    List<CompareQueue> selectCompareQueueList(String queueType);

    /*
     *   比对全部定时任务
     * */
    List<CompareQueue> selectCompareQueueAlloList();

    List<String> contributorGene(String id);

    String  selectCompareQueueGeneInfo(String id);

    mixContributorBean getMixSampleInfo(String mixedSampleId);
}
