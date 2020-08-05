package com.bazl.dna.lims.core.datamodel;


import com.bazl.dna.lims.core.model.po.LimsSampleGene;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/2/27.
 */
public class CheckGeneDataModel implements Serializable {

    private List<LimsSampleGene> sampleGeneList;

    public List<LimsSampleGene> getSampleGeneList() {
        return sampleGeneList;
    }

    public void setSampleGeneList(List<LimsSampleGene> sampleGeneList) {
        this.sampleGeneList = sampleGeneList;
    }
}
