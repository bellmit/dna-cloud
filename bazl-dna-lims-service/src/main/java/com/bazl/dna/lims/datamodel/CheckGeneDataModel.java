package com.bazl.dna.lims.datamodel;


import com.bazl.dna.lims.model.po.LimsSampleGene;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/2/27.
 */
public class CheckGeneDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LimsSampleGene> sampleGeneList;

    public List<LimsSampleGene> getSampleGeneList() {
        return sampleGeneList;
    }

    public void setSampleGeneList(List<LimsSampleGene> sampleGeneList) {
        this.sampleGeneList = sampleGeneList;
    }
}
