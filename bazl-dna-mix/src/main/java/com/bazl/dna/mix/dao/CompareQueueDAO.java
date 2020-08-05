package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.CompareQueue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompareQueueDAO {

    void insertCompareQueue(CompareQueue compareQueue);

    List<CompareQueue> selectBySampleId(CompareQueue compareQueue);

    List<CompareQueue> selectNotMixedSampleGeneCompareQueue(@Param("status") String status, @Param("queueType") String queueType, @Param("startRum") int startRum, @Param("endRum") int endRum);

    void updateStatus(CompareQueue compareQueue);

    /**
     * 通过匹配下线查询拆分信息
     */
    List<CompareQueue> selectByMixSameCount(CompareQueue compareQueue);
}