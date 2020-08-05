package com.bazl.dna.mix.dao;


import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.model.vo.SampleInfoVo;

import java.util.List;

public interface SampleInfoDAO {
    int insert(SampleInfo record);

    int insertSelective(SampleInfo record);

    /**
     * lims
     * @param sampleId
     * @return
     */
    SampleInfo selectBySampleId(String sampleId);

    void updateSampleInfo(SampleInfo sampleInfo);

    List<SampleInfoVo> selectSampleInfoList(String mixedSampleGeneId);

    List<SampleInfoVo> selectSampleInfoListBySingleGeneId(String singleSampleGeneId);
}