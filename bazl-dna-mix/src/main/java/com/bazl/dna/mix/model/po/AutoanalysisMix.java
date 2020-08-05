package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;

public class AutoanalysisMix implements Serializable {

	private static final long serialVersionUID = 1L;

    private MixedSampleGeneVo mixedSampleGeneVo;

    private List<SingleSampleGeneVo> singleSampleGeneVos;

    public MixedSampleGeneVo getMixedSampleGeneVo() {
        return mixedSampleGeneVo;
    }

    public void setMixedSampleGeneVo(MixedSampleGeneVo mixedSampleGeneVo) {
        this.mixedSampleGeneVo = mixedSampleGeneVo;
    }

    public List<SingleSampleGeneVo> getSingleSampleGeneVos() {
        return singleSampleGeneVos;
    }

    public void setSingleSampleGeneVos(List<SingleSampleGeneVo> singleSampleGeneVos) {
        this.singleSampleGeneVos = singleSampleGeneVos;
    }
}
