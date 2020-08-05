package com.bazl.dna.mix.model.po;

import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;

import java.io.Serializable;
import java.util.List;

public class MainpageBean implements Serializable {

	private static final long serialVersionUID = 1L;

    private CaseInfo caseInfo;

    private List<SingleSampleGeneVo> singleSampleGenesList;

    private List<MixedSampleGeneVo> mixedSampleGeneList;

    public CaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(CaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public List<SingleSampleGeneVo> getSingleSampleGenesList() {
        return singleSampleGenesList;
    }

    public void setSingleSampleGenesList(List<SingleSampleGeneVo> singleSampleGenesList) {
        this.singleSampleGenesList = singleSampleGenesList;
    }

    public List<MixedSampleGeneVo> getMixedSampleGeneList() {
        return mixedSampleGeneList;
    }

    public void setMixedSampleGeneList(List<MixedSampleGeneVo> mixedSampleGeneList) {
        this.mixedSampleGeneList = mixedSampleGeneList;
    }
}
