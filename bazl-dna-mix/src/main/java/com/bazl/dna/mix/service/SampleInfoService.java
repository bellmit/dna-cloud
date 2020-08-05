package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.model.vo.SampleInfoVo;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface SampleInfoService {

    /**
     * lims根据样本id查询样本信息
     * @return
     */
    public SampleInfo selectBySampleId(String sampleId);

    /**
     * 修改样本信息
     * @param sampleInfo
     */
    public void updateSampleInfo(SampleInfo sampleInfo);

    /**
     * 添加样本信息
     * @param sampleInfo
     */
    public void insertSampleInfo(SampleInfo sampleInfo);

    /**
     * 根据混合id查询样本信息
     * @param mixedSampleGeneId
     * @return
     */
    public List<SampleInfoVo> selectSampleInfoList(String mixedSampleGeneId);

    List<SampleInfoVo> selectSampleInfoListBySingleGeneId(String singleSampleGeneId);
}
