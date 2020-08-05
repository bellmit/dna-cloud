package com.bazl.dna.mix.model.po;

import java.io.Serializable;

public class SampleDnaGeneBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private  String sampleId;
    private  String sampleGene;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleGene() {
        return sampleGene;
    }

    public void setSampleGene(String sampleGene) {
        this.sampleGene = sampleGene;
    }
}
