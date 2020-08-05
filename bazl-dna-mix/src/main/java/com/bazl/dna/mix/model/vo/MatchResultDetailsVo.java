package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/4/29.
 */
public class MatchResultDetailsVo implements Serializable {

	private static final long serialVersionUID = 1L;
    private String markerName;
    private String geneStr1;
    private String geneStr2;

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getGeneStr1() {
        return geneStr1;
    }

    public void setGeneStr1(String geneStr1) {
        this.geneStr1 = geneStr1;
    }

    public String getGeneStr2() {
        return geneStr2;
    }

    public void setGeneStr2(String geneStr2) {
        this.geneStr2 = geneStr2;
    }
}
