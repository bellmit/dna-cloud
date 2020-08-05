package com.bazl.dna.mix.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SampleInfo;

/**
 * Created by Administrator on 2017/1/4.
 */
public class SampleInfoVo extends AbstractBaseVO<SampleInfo> implements Serializable {

	private static final long serialVersionUID = 1L;
    public SampleInfoVo() {
        super();
        this.entity = new SampleInfo();
    }

    public SampleInfoVo(SampleInfo entity) {
        super();
        this.entity = entity;
    }

    private String mixedSampleGeneId;

    private List<MixedSampleGene> matchResultMixSingleList;

    private MixedSampleGeneVo mixedSampleGeneVo;

    private String singleSampleGeneId;

    private int singleSampleCount;

    public List<MixedSampleGene> getMatchResultMixSingleList() {
        return matchResultMixSingleList;
    }

    public void setMatchResultMixSingleList(List<MixedSampleGene> matchResultMixSingleList) {
        this.matchResultMixSingleList = matchResultMixSingleList;
    }

    public int getSingleSampleCount() {
        return singleSampleCount;
    }

    public void setSingleSampleCount(int singleSampleCount) {
        this.singleSampleCount = singleSampleCount;
    }

    public String getSingleSampleGeneId() {
        return singleSampleGeneId;
    }

    public void setSingleSampleGeneId(String singleSampleGeneId) {
        this.singleSampleGeneId = singleSampleGeneId;
    }

    public MixedSampleGeneVo getMixedSampleGeneVo() {
        return mixedSampleGeneVo;
    }

    public void setMixedSampleGeneVo(MixedSampleGeneVo mixedSampleGeneVo) {
        this.mixedSampleGeneVo = mixedSampleGeneVo;
    }

    public String getMixedSampleGeneId() {
        return mixedSampleGeneId;
    }

    public void setMixedSampleGeneId(String mixedSampleGeneId) {
        this.mixedSampleGeneId = mixedSampleGeneId;
    }
}
