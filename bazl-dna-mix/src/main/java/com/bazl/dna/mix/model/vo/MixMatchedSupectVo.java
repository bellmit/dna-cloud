package com.bazl.dna.mix.model.vo;


import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lzn
 * @Date: 2019/7/5 14:55
 * @Version: 1.0
 */
public class MixMatchedSupectVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 案件id
     */
    private String id;
    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 比中嫌疑人样本编号
     */
    private String matchedSampleNo;

    /**
     * 样本名称
     */
    private String sampleName;
    /**
     * 混合id
     */
    private String sampleGeneId;
    /**
     * 比中id
     */
    private String ratiogeneId;
    /**
     * 比中数
     */
    private Short ratio;

    /**
     * 差异数
     */
    private Short splitDigit;

    /**
     * 比中时间
     */
    private Date comparisonTime;

    private String sampleType;

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public Date getComparisonTime() {
        return comparisonTime;
    }

    public void setComparisonTime(Date comparisonTime) {
        this.comparisonTime = comparisonTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

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

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }


    public Short getRatio() {
        return ratio;
    }

    public void setRatio(Short ratio) {
        this.ratio = ratio;
    }

    public Short getSplitDigit() {
        return splitDigit;
    }

    public void setSplitDigit(Short splitDigit) {
        this.splitDigit = splitDigit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchedSampleNo() {
        return matchedSampleNo;
    }

    public void setMatchedSampleNo(String matchedSampleNo) {
        this.matchedSampleNo = matchedSampleNo;
    }

    public String getSampleGeneId() {
        return sampleGeneId;
    }

    public void setSampleGeneId(String sampleGeneId) {
        this.sampleGeneId = sampleGeneId;
    }

    public String getRatiogeneId() {
        return ratiogeneId;
    }

    public void setRatiogeneId(String ratiogeneId) {
        this.ratiogeneId = ratiogeneId;
    }
}
