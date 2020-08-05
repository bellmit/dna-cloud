package com.bazl.dna.lims.connector.model.vo;


import com.bazl.dna.lims.connector.model.po.LimsSampleGene;

import java.io.Serializable;

public class LimsSampleGeneVo extends AbstractBaseVo<LimsSampleGene> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public LimsSampleGeneVo() {
        super();
        this.entity = new LimsSampleGene();
    }

    public LimsSampleGeneVo(LimsSampleGene entity) {
        super();
        this.entity = entity;
    }

    /* 案件编号*/
    private String caseNo;

    /* 案件名称*/
    private String caseName;

    /* 检材类型*/
    private String sampleType;

    /** 样本编号 */
    private String sampleNo;

    /** 样本类型名称 */
    private String sampleTypeName;

    /** 样本名称 */
    private String sampleName;

    /** 颜色标记 */
    private String colorMark;

    /**关联检材编号*/
    private String relationSampleNo;
    /**是否关联*/
    private String isRelation;
    /**检材来源*/
    private String sampleSource;

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getRelationSampleNo() {
        return relationSampleNo;
    }

    public void setRelationSampleNo(String relationSampleNo) {
        this.relationSampleNo = relationSampleNo;
    }

    public String getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(String isRelation) {
        this.isRelation = isRelation;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getColorMark() {
        return colorMark;
    }

    public void setColorMark(String colorMark) {
        this.colorMark = colorMark;
    }
}
