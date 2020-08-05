package com.bazl.dna.mix.model.po;

import java.io.Serializable;

public class GeneInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

   private String sampleGeneInfo;
   private String mixSampleGeneInfo;

    public String getSampleGeneInfo() {
        return sampleGeneInfo;
    }

    public void setSampleGeneInfo(String sampleGeneInfo) {
        this.sampleGeneInfo = sampleGeneInfo;
    }

    public String getMixSampleGeneInfo() {
        return mixSampleGeneInfo;
    }

    public void setMixSampleGeneInfo(String mixSampleGeneInfo) {
        this.mixSampleGeneInfo = mixSampleGeneInfo;
    }
}
