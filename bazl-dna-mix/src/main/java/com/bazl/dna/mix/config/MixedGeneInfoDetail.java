package com.bazl.dna.mix.config;

import java.io.Serializable;

/**
 * 基因信息详情
 * Created by lizhihua on 2020-03-10.
 */
public class MixedGeneInfoDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String markerName;
    private String allele;

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getAllele() {
        return allele;
    }

    public void setAllele(String allele) {
        this.allele = allele;
    }
}
