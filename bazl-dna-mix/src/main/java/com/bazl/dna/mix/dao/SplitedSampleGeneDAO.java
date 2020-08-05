package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.vo.SplitedSampleGeneVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SplitedSampleGeneDAO {

    int deleteByPrimaryKey(String id);

    SplitedSampleGene selectByPrimaryKey(String id);

    int insert(SplitedSampleGene record);

    int insertSelective(SplitedSampleGene record);

    /**
     * 查询已拆分样本总数
     * @return
     */
    int selectSplitedSampleGeneCount();

    /**
     * 根据混合id查询拆分基因信息
     * @param mixedSampleGeneId
     * @return
     */
    List<SplitedSampleGene> selectSplitedSampleGeneByMixedSampleGeneId(String mixedSampleGeneId);

    void update(SplitedSampleGene splitedSampleGene);

    List<SplitedSampleGene> selectAll(@Param("startRum") int startRum, @Param("endRum") int endRum);

    SplitedSampleGeneVo selectBySplitedId(@Param("id") String id);

    List<SplitedSampleGene> selectSplitedSampleGeneList(String sampleGeneId);

    //根据队列id查询
    SplitedSampleGene selectByCompareQueueId(String compareQueueId);
    //根据id获取拆分信息
    SplitedSampleGene selectById(String id);
}