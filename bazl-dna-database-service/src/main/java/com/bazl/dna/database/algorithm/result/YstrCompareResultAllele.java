package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;

/**
 * YSTR比中等位基因
 * Created by lizhihua on 2019-07-29.
 */
public class YstrCompareResultAllele implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 基因座名称
     */
    private String locusName;

    /**
     * 源样本等位基因
     */
    private String srcGeneAllele;

    /**
     * 目标样本等位基因
     */
    private String targetGeneAllele;

    /**
     * 标记该基因座是否为差异等位基因
     */
    private boolean diffAllele;

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public String getSrcGeneAllele() {
        return srcGeneAllele;
    }

    public void setSrcGeneAllele(String srcGeneAllele) {
        this.srcGeneAllele = srcGeneAllele;
    }

    public String getTargetGeneAllele() {
        return targetGeneAllele;
    }

    public void setTargetGeneAllele(String targetGeneAllele) {
        this.targetGeneAllele = targetGeneAllele;
    }

    public boolean isDiffAllele() {
        return diffAllele;
    }

    public void setDiffAllele(boolean diffAllele) {
        this.diffAllele = diffAllele;
    }

}
