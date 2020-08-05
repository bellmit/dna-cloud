package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;

/**
 * 亲缘比中等位基因详情
 * Created by lizhihua on 2019-07-29.
 */
public class StrRelativeCompareResultAllele implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 基因座名称
     */
    private String locusName;

    /**
     * 孩子等位基因
     */
    private String childGeneAllele;

    /**
     * 父亲等位基因
     */
    private String fatherGeneAllele;

    /**
     * 母亲等位基因
     */
    private String motherGeneAllele;

    /**
     * 单亲（父/母）等位基因
     */
    private String singleParentGeneAllele;


    /**
     * 标记该基因座是否为差异等位基因
     */
    private boolean diffAllele;

    /**
     * 亲权指数
      */
    private Double piVal;

    /**
     * 父子亲权指数
     */
    private Double fatherPiVal;

    /**
     * 母子亲权指数
     */
    private Double motherPiVal;

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public String getChildGeneAllele() {
        return childGeneAllele;
    }

    public void setChildGeneAllele(String childGeneAllele) {
        this.childGeneAllele = childGeneAllele;
    }

    public String getFatherGeneAllele() {
        return fatherGeneAllele;
    }

    public void setFatherGeneAllele(String fatherGeneAllele) {
        this.fatherGeneAllele = fatherGeneAllele;
    }

    public String getMotherGeneAllele() {
        return motherGeneAllele;
    }

    public void setMotherGeneAllele(String motherGeneAllele) {
        this.motherGeneAllele = motherGeneAllele;
    }

    public String getSingleParentGeneAllele() {
        return singleParentGeneAllele;
    }

    public void setSingleParentGeneAllele(String singleParentGeneAllele) {
        this.singleParentGeneAllele = singleParentGeneAllele;
    }

    public boolean isDiffAllele() {
        return diffAllele;
    }

    public void setDiffAllele(boolean diffAllele) {
        this.diffAllele = diffAllele;
    }

    public Double getPiVal() {
        return piVal;
    }

    public void setPiVal(Double piVal) {
        this.piVal = piVal;
    }

    public Double getFatherPiVal() {
        return fatherPiVal;
    }

    public void setFatherPiVal(Double fatherPiVal) {
        this.fatherPiVal = fatherPiVal;
    }

    public Double getMotherPiVal() {
        return motherPiVal;
    }

    public void setMotherPiVal(Double motherPiVal) {
        this.motherPiVal = motherPiVal;
    }
}
