package com.bazl.dna.mix.model;

import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SampleInfoVo;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/4/3.
 */
public class SampleGeneCompareResult {

    private List<SampleInfoVo> sampleInfoVoList;

    private List<MixedSampleGeneVo> mixedSampleGeneList;

//    private List<MatchResultMixSingle> matchResultMixSingleList;

    public List<SampleInfoVo> getSampleInfoVoList() {
        return sampleInfoVoList;
    }

    public void setSampleInfoVoList(List<SampleInfoVo> sampleInfoVoList) {
        this.sampleInfoVoList = sampleInfoVoList;
    }

    public List<MixedSampleGeneVo> getMixedSampleGeneList() {
        return mixedSampleGeneList;
    }

    public void setMixedSampleGeneList(List<MixedSampleGeneVo> mixedSampleGeneList) {
        this.mixedSampleGeneList = mixedSampleGeneList;
    }

}
